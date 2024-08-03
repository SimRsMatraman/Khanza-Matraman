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
import keuangan.DlgJnsPerawatanRadiologi;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class SatuSehatMapingLaborat extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeLoinc;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariTemplateLaborat pemeriksaan = new DlgCariTemplateLaborat(null, false);
    private String auth, authEncrypt;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ApiSatuSehat api = new ApiSatuSehat();
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root, nameNode, response;

    /**
     * Creates new form DlgJnsPerawatanRalan
     *
     * @param parent
     * @param modal
     */
    public SatuSehatMapingLaborat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "Periksa Code", "Pemeriksaan System", "ID Detail", "Detail Pemeriksaan", "Pemeriksaan Display",
            "Sampel Code", "Sampel System", "Sampel Display"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
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
                column.setPreferredWidth(85);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        tabModeLoinc = new DefaultTableModel(null, new Object[]{
            "Code", "Long Component", "Component", "Property", "Timing", "System", "Scale", "Method", "UCUM"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbListLoinc.setModel(tabModeLoinc);

        tbListLoinc.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListLoinc.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbListLoinc.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            }
        }
        tbListLoinc.setDefaultRenderer(Object.class, new WarnaTable());
        KodePemeriksaan.setDocument(new batasInput((byte) 15).getKata(KodePemeriksaan));
        Code.setDocument(new batasInput((byte) 15).getKata(Code));
        LaboratoriumSystem.setDocument(new batasInput((byte) 100).getKata(LaboratoriumSystem));
        Display.setDocument(new batasInput((byte) 80).getKata(Display));
        SampelCode.setDocument(new batasInput((byte) 15).getKata(SampelCode));
        SampelLaboratoriumSystem.setDocument(new batasInput((byte) 100).getKata(SampelLaboratoriumSystem));
        SampelDisplay.setDocument(new batasInput((byte) 80).getKata(SampelDisplay));
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

        pemeriksaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (pemeriksaan.getTable().getSelectedRow() != -1) {
                    KodePemeriksaan.setText(pemeriksaan.getTable().getValueAt(pemeriksaan.getTable().getSelectedRow(), 2).toString());
                    NamaPemeriksaan.setText(pemeriksaan.getTable().getValueAt(pemeriksaan.getTable().getSelectedRow(), 3).toString());
                }
                btnBarang.requestFocus();
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

        pemeriksaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pemeriksaan.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

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

        DlgMasterPemeriksaan = new javax.swing.JDialog();
        internalFrame23 = new widget.InternalFrame();
        internalFrame24 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbMasterPemeriksaan = new widget.Table();
        panelBiasa14 = new widget.PanelBiasa();
        jLabel135 = new widget.Label();
        TCariMasterPemeriksaan = new widget.TextBox();
        BtnKeluarMMPI13 = new widget.Button();
        BtnKeluarMMPI14 = new widget.Button();
        jLabel14 = new widget.Label();
        LCountTotal1 = new widget.Label();
        jLabel136 = new widget.Label();
        page1 = new widget.ComboBox();
        jLabel137 = new widget.Label();
        hal1 = new widget.ComboBox();
        DlgLoincRestApi = new javax.swing.JDialog();
        internalFrame19 = new widget.InternalFrame();
        internalFrame20 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbListLoinc = new widget.Table();
        panelBiasa12 = new widget.PanelBiasa();
        jLabel132 = new widget.Label();
        TCariLoincLive = new widget.TextBox();
        BtnKeluarMMPI5 = new widget.Button();
        BtnKeluarMMPI6 = new widget.Button();
        jLabel15 = new widget.Label();
        LCountTotal = new widget.Label();
        jLabel133 = new widget.Label();
        page = new widget.ComboBox();
        jLabel134 = new widget.Label();
        hal = new widget.ComboBox();
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
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        KodePemeriksaan = new widget.TextBox();
        btnBarang = new widget.Button();
        Code = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        Display = new widget.TextBox();
        LaboratoriumSystem = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        SampelCode = new widget.TextBox();
        SampelDisplay = new widget.TextBox();
        jLabel5 = new widget.Label();
        SampelLaboratoriumSystem = new widget.TextBox();
        NamaPemeriksaan = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel8 = new widget.Label();
        typeStandar = new widget.ComboBox();
        jLabel19 = new widget.Label();
        typeSampel = new widget.ComboBox();
        BtnTermminologiApi = new widget.Button();
        BtnTermminologiBrowser = new widget.Button();
        BtnSampelApi = new widget.Button();
        BtnSampelBrowser = new widget.Button();

        DlgMasterPemeriksaan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgMasterPemeriksaan.setName("DlgMasterPemeriksaan"); // NOI18N
        DlgMasterPemeriksaan.setUndecorated(true);
        DlgMasterPemeriksaan.setResizable(false);

        internalFrame23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ List Master Pemeriksaan Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame23.setName("internalFrame23"); // NOI18N
        internalFrame23.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame24.setName("internalFrame24"); // NOI18N
        internalFrame24.setLayout(new java.awt.GridLayout());

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbMasterPemeriksaan.setName("tbMasterPemeriksaan"); // NOI18N
        tbMasterPemeriksaan.getTableHeader().setReorderingAllowed(false);
        tbMasterPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMasterPemeriksaanMouseClicked(evt);
            }
        });
        Scroll2.setViewportView(tbMasterPemeriksaan);

        internalFrame24.add(Scroll2);

        internalFrame23.add(internalFrame24, java.awt.BorderLayout.CENTER);

        panelBiasa14.setName("panelBiasa14"); // NOI18N
        panelBiasa14.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel135.setText("Cari Data :");
        jLabel135.setName("jLabel135"); // NOI18N
        panelBiasa14.add(jLabel135);

        TCariMasterPemeriksaan.setName("TCariMasterPemeriksaan"); // NOI18N
        TCariMasterPemeriksaan.setPreferredSize(new java.awt.Dimension(250, 24));
        TCariMasterPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasterPemeriksaanKeyPressed(evt);
            }
        });
        panelBiasa14.add(TCariMasterPemeriksaan);

        BtnKeluarMMPI13.setBackground(new java.awt.Color(0, 102, 102));
        BtnKeluarMMPI13.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarMMPI13.setMnemonic('K');
        BtnKeluarMMPI13.setText("Cari");
        BtnKeluarMMPI13.setToolTipText("Alt+K");
        BtnKeluarMMPI13.setName("BtnKeluarMMPI13"); // NOI18N
        BtnKeluarMMPI13.setOpaque(true);
        BtnKeluarMMPI13.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMMPI13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMMPI13ActionPerformed(evt);
            }
        });
        panelBiasa14.add(BtnKeluarMMPI13);

        BtnKeluarMMPI14.setBackground(new java.awt.Color(0, 51, 102));
        BtnKeluarMMPI14.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarMMPI14.setMnemonic('K');
        BtnKeluarMMPI14.setText("Keluar");
        BtnKeluarMMPI14.setToolTipText("Alt+K");
        BtnKeluarMMPI14.setName("BtnKeluarMMPI14"); // NOI18N
        BtnKeluarMMPI14.setOpaque(true);
        BtnKeluarMMPI14.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMMPI14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMMPI14ActionPerformed(evt);
            }
        });
        panelBiasa14.add(BtnKeluarMMPI14);

        jLabel14.setText("Record :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(75, 23));
        panelBiasa14.add(jLabel14);

        LCountTotal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountTotal1.setText("0");
        LCountTotal1.setName("LCountTotal1"); // NOI18N
        LCountTotal1.setPreferredSize(new java.awt.Dimension(80, 23));
        panelBiasa14.add(LCountTotal1);

        jLabel136.setText("Jumlah Data per Halaman");
        jLabel136.setName("jLabel136"); // NOI18N
        jLabel136.setPreferredSize(new java.awt.Dimension(156, 14));
        panelBiasa14.add(jLabel136);

        page1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "50", "100", "200" }));
        page1.setName("page1"); // NOI18N
        page1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                page1ItemStateChanged(evt);
            }
        });
        page1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                page1KeyPressed(evt);
            }
        });
        panelBiasa14.add(page1);

        jLabel137.setText("Halaman :");
        jLabel137.setName("jLabel137"); // NOI18N
        panelBiasa14.add(jLabel137);

        hal1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));
        hal1.setName("hal1"); // NOI18N
        hal1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hal1ItemStateChanged(evt);
            }
        });
        hal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hal1KeyPressed(evt);
            }
        });
        panelBiasa14.add(hal1);

        internalFrame23.add(panelBiasa14, java.awt.BorderLayout.PAGE_END);

        DlgMasterPemeriksaan.getContentPane().add(internalFrame23, java.awt.BorderLayout.CENTER);

        DlgLoincRestApi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgLoincRestApi.setName("DlgLoincRestApi"); // NOI18N
        DlgLoincRestApi.setUndecorated(true);
        DlgLoincRestApi.setResizable(false);

        internalFrame19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ List Data Loinc Browser ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame19.setName("internalFrame19"); // NOI18N
        internalFrame19.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame20.setName("internalFrame20"); // NOI18N
        internalFrame20.setLayout(new java.awt.GridLayout());

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbListLoinc.setName("tbListLoinc"); // NOI18N
        tbListLoinc.getTableHeader().setReorderingAllowed(false);
        tbListLoinc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListLoincMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbListLoinc);

        internalFrame20.add(Scroll1);

        internalFrame19.add(internalFrame20, java.awt.BorderLayout.CENTER);

        panelBiasa12.setName("panelBiasa12"); // NOI18N
        panelBiasa12.setPreferredSize(new java.awt.Dimension(0, 40));
        panelBiasa12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel132.setText("Cari Data :");
        jLabel132.setName("jLabel132"); // NOI18N
        panelBiasa12.add(jLabel132);

        TCariLoincLive.setName("TCariLoincLive"); // NOI18N
        TCariLoincLive.setPreferredSize(new java.awt.Dimension(250, 24));
        TCariLoincLive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariLoincLiveKeyPressed(evt);
            }
        });
        panelBiasa12.add(TCariLoincLive);

        BtnKeluarMMPI5.setBackground(new java.awt.Color(0, 102, 102));
        BtnKeluarMMPI5.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarMMPI5.setMnemonic('K');
        BtnKeluarMMPI5.setText("Cari");
        BtnKeluarMMPI5.setToolTipText("Alt+K");
        BtnKeluarMMPI5.setName("BtnKeluarMMPI5"); // NOI18N
        BtnKeluarMMPI5.setOpaque(true);
        BtnKeluarMMPI5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMMPI5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMMPI5ActionPerformed(evt);
            }
        });
        panelBiasa12.add(BtnKeluarMMPI5);

        BtnKeluarMMPI6.setBackground(new java.awt.Color(0, 51, 102));
        BtnKeluarMMPI6.setForeground(new java.awt.Color(255, 255, 255));
        BtnKeluarMMPI6.setMnemonic('K');
        BtnKeluarMMPI6.setText("Keluar");
        BtnKeluarMMPI6.setToolTipText("Alt+K");
        BtnKeluarMMPI6.setName("BtnKeluarMMPI6"); // NOI18N
        BtnKeluarMMPI6.setOpaque(true);
        BtnKeluarMMPI6.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMMPI6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMMPI6ActionPerformed(evt);
            }
        });
        panelBiasa12.add(BtnKeluarMMPI6);

        jLabel15.setText("Record :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(75, 23));
        panelBiasa12.add(jLabel15);

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

        DlgLoincRestApi.getContentPane().add(internalFrame19, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mapping Tindakan Laboratorium PK & MB Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 135));
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

        jLabel4.setText("Periksa System :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(1190, 40, 100, 23);

        KodePemeriksaan.setEditable(false);
        KodePemeriksaan.setHighlighter(null);
        KodePemeriksaan.setName("KodePemeriksaan"); // NOI18N
        FormInput.add(KodePemeriksaan);
        KodePemeriksaan.setBounds(120, 10, 110, 23);

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
        btnBarang.setBounds(500, 10, 28, 23);

        Code.setHighlighter(null);
        Code.setName("Code"); // NOI18N
        Code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CodeKeyPressed(evt);
            }
        });
        FormInput.add(Code);
        Code.setBounds(660, 40, 120, 23);

        jLabel9.setText("Nama Pemeriksaan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 10, 120, 23);

        jLabel10.setText("Periksa Display :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(780, 40, 100, 23);

        Display.setHighlighter(null);
        Display.setName("Display"); // NOI18N
        Display.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DisplayKeyPressed(evt);
            }
        });
        FormInput.add(Display);
        Display.setBounds(880, 40, 310, 23);

        LaboratoriumSystem.setHighlighter(null);
        LaboratoriumSystem.setName("LaboratoriumSystem"); // NOI18N
        LaboratoriumSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratoriumSystemKeyPressed(evt);
            }
        });
        FormInput.add(LaboratoriumSystem);
        LaboratoriumSystem.setBounds(1290, 40, 255, 23);

        jLabel11.setText("Sampel Code :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(570, 70, 80, 23);

        jLabel12.setText("Sampel Display :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(780, 70, 100, 23);

        SampelCode.setHighlighter(null);
        SampelCode.setName("SampelCode"); // NOI18N
        SampelCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SampelCodeKeyPressed(evt);
            }
        });
        FormInput.add(SampelCode);
        SampelCode.setBounds(660, 70, 120, 23);

        SampelDisplay.setHighlighter(null);
        SampelDisplay.setName("SampelDisplay"); // NOI18N
        SampelDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SampelDisplayKeyPressed(evt);
            }
        });
        FormInput.add(SampelDisplay);
        SampelDisplay.setBounds(880, 70, 310, 23);

        jLabel5.setText("Sampel System :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(1190, 70, 100, 23);

        SampelLaboratoriumSystem.setHighlighter(null);
        SampelLaboratoriumSystem.setName("SampelLaboratoriumSystem"); // NOI18N
        SampelLaboratoriumSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SampelLaboratoriumSystemKeyPressed(evt);
            }
        });
        FormInput.add(SampelLaboratoriumSystem);
        SampelLaboratoriumSystem.setBounds(1290, 70, 260, 23);

        NamaPemeriksaan.setEditable(false);
        NamaPemeriksaan.setHighlighter(null);
        NamaPemeriksaan.setName("NamaPemeriksaan"); // NOI18N
        FormInput.add(NamaPemeriksaan);
        NamaPemeriksaan.setBounds(230, 10, 260, 24);

        jLabel13.setText("Periksa Code :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(570, 40, 80, 23);

        jLabel8.setText("Standar Terminologi :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 40, 150, 23);

        typeStandar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LOINC", "SNOMED-CT" }));
        typeStandar.setName("typeStandar"); // NOI18N
        typeStandar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                typeStandarKeyPressed(evt);
            }
        });
        FormInput.add(typeStandar);
        typeStandar.setBounds(150, 40, 140, 23);

        jLabel19.setText("Sampel :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 70, 150, 23);

        typeSampel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LOINC", "SNOMED-CT" }));
        typeSampel.setName("typeSampel"); // NOI18N
        typeSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                typeSampelKeyPressed(evt);
            }
        });
        FormInput.add(typeSampel);
        typeSampel.setBounds(150, 70, 140, 23);

        BtnTermminologiApi.setBackground(new java.awt.Color(0, 51, 102));
        BtnTermminologiApi.setForeground(new java.awt.Color(255, 255, 255));
        BtnTermminologiApi.setMnemonic('K');
        BtnTermminologiApi.setText("Cari via REST-API");
        BtnTermminologiApi.setToolTipText("Alt+K");
        BtnTermminologiApi.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnTermminologiApi.setIconTextGap(0);
        BtnTermminologiApi.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnTermminologiApi.setName("BtnTermminologiApi"); // NOI18N
        BtnTermminologiApi.setOpaque(true);
        BtnTermminologiApi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTermminologiApi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTermminologiApiActionPerformed(evt);
            }
        });
        FormInput.add(BtnTermminologiApi);
        BtnTermminologiApi.setBounds(290, 40, 130, 20);

        BtnTermminologiBrowser.setBackground(new java.awt.Color(0, 102, 102));
        BtnTermminologiBrowser.setForeground(new java.awt.Color(255, 255, 255));
        BtnTermminologiBrowser.setMnemonic('K');
        BtnTermminologiBrowser.setText("Cari via Browser");
        BtnTermminologiBrowser.setToolTipText("Alt+K");
        BtnTermminologiBrowser.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnTermminologiBrowser.setIconTextGap(0);
        BtnTermminologiBrowser.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnTermminologiBrowser.setName("BtnTermminologiBrowser"); // NOI18N
        BtnTermminologiBrowser.setOpaque(true);
        BtnTermminologiBrowser.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTermminologiBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTermminologiBrowserActionPerformed(evt);
            }
        });
        FormInput.add(BtnTermminologiBrowser);
        BtnTermminologiBrowser.setBounds(430, 40, 130, 20);

        BtnSampelApi.setBackground(new java.awt.Color(0, 51, 102));
        BtnSampelApi.setForeground(new java.awt.Color(255, 255, 255));
        BtnSampelApi.setMnemonic('K');
        BtnSampelApi.setText("Cari via REST-API");
        BtnSampelApi.setToolTipText("Alt+K");
        BtnSampelApi.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnSampelApi.setIconTextGap(0);
        BtnSampelApi.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnSampelApi.setName("BtnSampelApi"); // NOI18N
        BtnSampelApi.setOpaque(true);
        BtnSampelApi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSampelApi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSampelApiActionPerformed(evt);
            }
        });
        FormInput.add(BtnSampelApi);
        BtnSampelApi.setBounds(290, 70, 130, 20);

        BtnSampelBrowser.setBackground(new java.awt.Color(0, 102, 102));
        BtnSampelBrowser.setForeground(new java.awt.Color(255, 255, 255));
        BtnSampelBrowser.setMnemonic('K');
        BtnSampelBrowser.setText("Cari via Browser");
        BtnSampelBrowser.setToolTipText("Alt+K");
        BtnSampelBrowser.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BtnSampelBrowser.setIconTextGap(0);
        BtnSampelBrowser.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BtnSampelBrowser.setName("BtnSampelBrowser"); // NOI18N
        BtnSampelBrowser.setOpaque(true);
        BtnSampelBrowser.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSampelBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSampelBrowserActionPerformed(evt);
            }
        });
        FormInput.add(BtnSampelBrowser);
        BtnSampelBrowser.setBounds(430, 70, 130, 20);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        pemeriksaan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pemeriksaan.setLocationRelativeTo(internalFrame1);
        pemeriksaan.setVisible(true);
}//GEN-LAST:event_btnBarangActionPerformed

    private void btnBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBarangKeyPressed
        Valid.pindah(evt, LaboratoriumSystem, Display);
}//GEN-LAST:event_btnBarangKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (Code.getText().trim().equals("")) {
            Valid.textKosong(Code, "Periksa Code");
        } else if (LaboratoriumSystem.getText().trim().equals("")) {
            Valid.textKosong(LaboratoriumSystem, "Pemeriksaan System");
        } else if (NamaPemeriksaan.getText().trim().equals("")) {
            Valid.textKosong(NamaPemeriksaan, "Nama Pemeriksaan");
        } else if (Display.getText().trim().equals("")) {
            Valid.textKosong(Display, "Pemeriksaan Display");
        } else if (SampelCode.getText().trim().equals("")) {
            Valid.textKosong(SampelCode, "Sampel Code");
        } else if (SampelLaboratoriumSystem.getText().trim().equals("")) {
            Valid.textKosong(SampelLaboratoriumSystem, "Sampel System");
        } else if (SampelDisplay.getText().trim().equals("")) {
            Valid.textKosong(SampelDisplay, "Sampel Display");
        } else {
            if (Sequel.menyimpantf("satu_sehat_mapping_lab", "?,?,?,?,?,?,?", "Mapping Tindakan Radiologi", 7, new String[]{
                KodePemeriksaan.getText(), Code.getText(), LaboratoriumSystem.getText(), Display.getText(), SampelCode.getText(), SampelLaboratoriumSystem.getText(), SampelDisplay.getText()
            }) == true) {
                tabMode.addRow(new String[]{
                    Code.getText(), LaboratoriumSystem.getText(), KodePemeriksaan.getText(), NamaPemeriksaan.getText(), Display.getText(), SampelCode.getText(), SampelLaboratoriumSystem.getText(), SampelDisplay.getText()
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
            Valid.pindah(evt, SampelDisplay, BtnBatal);
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
        if (Valid.hapusTabletf(tabMode, KodePemeriksaan, "satu_sehat_mapping_lab", "id_template") == true) {
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
        if (Code.getText().trim().equals("")) {
            Valid.textKosong(Code, "Periksa Code");
        } else if (LaboratoriumSystem.getText().trim().equals("")) {
            Valid.textKosong(LaboratoriumSystem, "Pemeriksaan System");
        } else if (NamaPemeriksaan.getText().trim().equals("")) {
            Valid.textKosong(NamaPemeriksaan, "Nama Pemeriksaan");
        } else if (Display.getText().trim().equals("")) {
            Valid.textKosong(Display, "Pemeriksaan Display");
        } else if (SampelCode.getText().trim().equals("")) {
            Valid.textKosong(SampelCode, "Sampel Code");
        } else if (SampelLaboratoriumSystem.getText().trim().equals("")) {
            Valid.textKosong(SampelLaboratoriumSystem, "Sampel System");
        } else if (SampelDisplay.getText().trim().equals("")) {
            Valid.textKosong(SampelDisplay, "Sampel Display");
        } else {
            if (tbJnsPerawatan.getSelectedRow() > -1) {
                if (Sequel.mengedittf("satu_sehat_mapping_lab", "id_template=?", "id_template=?,code=?,system=?,display=?,sampel_code=?,sampel_system=?,sampel_display=?", 8, new String[]{
                    KodePemeriksaan.getText(), Code.getText(), LaboratoriumSystem.getText(), Display.getText(), SampelCode.getText(), SampelLaboratoriumSystem.getText(), SampelDisplay.getText(), tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 2).toString()
                }) == true) {
                    tabMode.setValueAt(Code.getText(), tbJnsPerawatan.getSelectedRow(), 0);
                    tabMode.setValueAt(LaboratoriumSystem.getText(), tbJnsPerawatan.getSelectedRow(), 1);
                    tabMode.setValueAt(KodePemeriksaan.getText(), tbJnsPerawatan.getSelectedRow(), 2);
                    tabMode.setValueAt(NamaPemeriksaan.getText(), tbJnsPerawatan.getSelectedRow(), 3);
                    tabMode.setValueAt(Display.getText(), tbJnsPerawatan.getSelectedRow(), 4);
                    tabMode.setValueAt(SampelCode.getText(), tbJnsPerawatan.getSelectedRow(), 5);
                    tabMode.setValueAt(SampelLaboratoriumSystem.getText(), tbJnsPerawatan.getSelectedRow(), 6);
                    tabMode.setValueAt(SampelDisplay.getText(), tbJnsPerawatan.getSelectedRow(), 7);
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
            Valid.MyReport("rptMapingPemeriksaanLaboratSatuSehat.jasper", "report", "::[ Mapping Pemeriksaan Laboratorium Satu Sehat Kemenkes ]::", param);
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

    private void CodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CodeKeyPressed
        Valid.pindah(evt, TCari, LaboratoriumSystem);
    }//GEN-LAST:event_CodeKeyPressed

    private void LaboratoriumSystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratoriumSystemKeyPressed
        Valid.pindah(evt, btnBarang, Display);
    }//GEN-LAST:event_LaboratoriumSystemKeyPressed

    private void DisplayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DisplayKeyPressed
        Valid.pindah(evt, LaboratoriumSystem, SampelCode);
    }//GEN-LAST:event_DisplayKeyPressed

    private void SampelCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SampelCodeKeyPressed
        Valid.pindah(evt, Display, SampelLaboratoriumSystem);
    }//GEN-LAST:event_SampelCodeKeyPressed

    private void SampelDisplayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SampelDisplayKeyPressed
        Valid.pindah(evt, SampelLaboratoriumSystem, BtnSimpan);
    }//GEN-LAST:event_SampelDisplayKeyPressed

    private void SampelLaboratoriumSystemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SampelLaboratoriumSystemKeyPressed
        Valid.pindah(evt, SampelCode, SampelDisplay);
    }//GEN-LAST:event_SampelLaboratoriumSystemKeyPressed

    private void typeStandarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_typeStandarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeStandarKeyPressed

    private void typeSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_typeSampelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeSampelKeyPressed

    private void BtnTermminologiApiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTermminologiApiActionPerformed
        if (typeStandar.getSelectedIndex() == 0) {
            DataLoincRestApi aplikasi = new DataLoincRestApi(null, false);
            aplikasi.setSearch(NamaPemeriksaan.getText().trim());
            aplikasi.tampil();
            aplikasi.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.setVisible(true);
            aplikasi.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    if (aplikasi.getTable().getSelectedRow() != -1) {
                        Code.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 0).toString());
                        Display.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 2).toString());
                        LaboratoriumSystem.setText("http://loinc.org");
                        String tipeHasil = aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 6).toString();
//                        switch (tipeHasil) {
//                            case "Qn":
//                                cmbTipeHasil.setSelectedItem("Quantitative");
//                                break;
//                            case "Ord":
//                                cmbTipeHasil.setSelectedItem("Ordinal");
//                                break;
//                            default:
//                                cmbTipeHasil.setSelectedItem("Quantitative");
//                                break;
//                        }
                    }
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
        } else {
            DataSnomedCTRestApi aplikasi = new DataSnomedCTRestApi(null, false);
            aplikasi.setSearch(NamaPemeriksaan.getText().trim());
            aplikasi.tampil();
            aplikasi.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.setVisible(true);
            aplikasi.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    if (aplikasi.getTable().getSelectedRow() != -1) {
                        Code.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 0).toString());
                        Display.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 1).toString());
                        LaboratoriumSystem.setText("http://snomed.info/sct");
                    }
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
        }
    }//GEN-LAST:event_BtnTermminologiApiActionPerformed

    private void BtnTermminologiBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTermminologiBrowserActionPerformed
        Browser aplikasi = new Browser(null, false);
        String urlBrowser;
        if (typeStandar.getSelectedIndex() == 0) {
            urlBrowser = "https://loinc.org/tree/";
        } else {
            urlBrowser = "https://browser.ihtsdotools.org/?perspective=full&conceptId1=404684003&edition=MAIN/2023-12-01&release=&languages=en";
        }
        aplikasi.loadURL(urlBrowser);
        aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        aplikasi.setLocationRelativeTo(internalFrame1);
        aplikasi.setVisible(true);

    }//GEN-LAST:event_BtnTermminologiBrowserActionPerformed

    private void BtnSampelApiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSampelApiActionPerformed
        if (typeSampel.getSelectedIndex() == 0) {
            DataLoincRestApi aplikasi = new DataLoincRestApi(null, false);
            aplikasi.setSearch("");
            aplikasi.tampil();
            aplikasi.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.setVisible(true);
            aplikasi.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    if (aplikasi.getTable().getSelectedRow() != -1) {
                        SampelCode.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 0).toString());
                        SampelDisplay.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 2).toString());
                        SampelLaboratoriumSystem.setText("http://loinc.org");
                    }
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
        } else {
            DataSnomedCTRestApi aplikasi = new DataSnomedCTRestApi(null, false);
            aplikasi.setSearch(NamaPemeriksaan.getText().trim());
            aplikasi.tampil();
            aplikasi.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.setVisible(true);
            aplikasi.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {;
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    if (aplikasi.getTable().getSelectedRow() != -1) {
                        SampelCode.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 0).toString());
                        SampelDisplay.setText(aplikasi.getTable().getValueAt(aplikasi.getTable().getSelectedRow(), 1).toString());
                        SampelLaboratoriumSystem.setText("http://snomed.info/sct");
                    }
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
        }
    }//GEN-LAST:event_BtnSampelApiActionPerformed

    private void BtnSampelBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSampelBrowserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSampelBrowserActionPerformed

    private void tbMasterPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMasterPemeriksaanMouseClicked
        if (evt.getClickCount() == 2) {
            KodePemeriksaan.setText(tbMasterPemeriksaan.getValueAt(tbMasterPemeriksaan.getSelectedRow(), 0).toString());
            NamaPemeriksaan.setText(tbMasterPemeriksaan.getValueAt(tbMasterPemeriksaan.getSelectedRow(), 2).toString());
            DlgMasterPemeriksaan.dispose();
        }
    }//GEN-LAST:event_tbMasterPemeriksaanMouseClicked

    private void TCariMasterPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasterPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariMasterPemeriksaanKeyPressed

    private void BtnKeluarMMPI13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMMPI13ActionPerformed
//        getMasterPemeriksaan();
    }//GEN-LAST:event_BtnKeluarMMPI13ActionPerformed

    private void BtnKeluarMMPI14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMMPI14ActionPerformed
        DlgMasterPemeriksaan.dispose();
    }//GEN-LAST:event_BtnKeluarMMPI14ActionPerformed

    private void page1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_page1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_page1ItemStateChanged

    private void page1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_page1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_page1KeyPressed

    private void hal1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hal1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_hal1ItemStateChanged

    private void hal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_hal1KeyPressed

    private void tbListLoincMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListLoincMouseClicked

        if (evt.getClickCount() == 2) {
            //            KdKfa91.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 5).toString());
            //            KdKfa92.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 3).toString());
            //            KdKfa93.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 1).toString());
            //            NmKfa91.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 4).toString());
            //            NmKfa92.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 2).toString());
            //            NmKfa93.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 0).toString());
            //            KdSatuanIhs.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 7).toString());
            //            NmSatuanIhs.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 8).toString());
            //            komposisi.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 6).toString().split(" ")[0]);
            //            satuanKomposisi.setText(tbListKfa.getValueAt(tbListKfa.getSelectedRow(), 6).toString().split(" ")[1]);
            DlgLoincRestApi.dispose();
        }
    }//GEN-LAST:event_tbListLoincMouseClicked

    private void TCariLoincLiveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariLoincLiveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariLoincLiveKeyPressed

    private void BtnKeluarMMPI5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMMPI5ActionPerformed
        tampilListLoinc(TCariLoincLive.getText().trim());
    }//GEN-LAST:event_BtnKeluarMMPI5ActionPerformed

    private void BtnKeluarMMPI6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMMPI6ActionPerformed
        DlgLoincRestApi.dispose();
    }//GEN-LAST:event_BtnKeluarMMPI6ActionPerformed

    private void pageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pageItemStateChanged
        //        tampilListKfaSearchPage(TCariObatLive.getText().trim());
    }//GEN-LAST:event_pageItemStateChanged

    private void pageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pageKeyPressed

    }//GEN-LAST:event_pageKeyPressed

    private void halItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_halItemStateChanged
        //        tampilListKfaSearchPage(TCariObatLive.getText().trim());
    }//GEN-LAST:event_halItemStateChanged

    private void halKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_halKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_halKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatMapingLaborat dialog = new SatuSehatMapingLaborat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarMMPI13;
    private widget.Button BtnKeluarMMPI14;
    private widget.Button BtnKeluarMMPI5;
    private widget.Button BtnKeluarMMPI6;
    private widget.Button BtnPrint;
    private widget.Button BtnSampelApi;
    private widget.Button BtnSampelBrowser;
    private widget.Button BtnSimpan;
    private widget.Button BtnTermminologiApi;
    private widget.Button BtnTermminologiBrowser;
    private widget.CekBox ChkInput;
    private widget.TextBox Code;
    private widget.TextBox Display;
    private javax.swing.JDialog DlgLoincRestApi;
    private javax.swing.JDialog DlgMasterPemeriksaan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KodePemeriksaan;
    private widget.Label LCount;
    private widget.Label LCountTotal;
    private widget.Label LCountTotal1;
    private widget.TextBox LaboratoriumSystem;
    private widget.TextBox NamaPemeriksaan;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox SampelCode;
    private widget.TextBox SampelDisplay;
    private widget.TextBox SampelLaboratoriumSystem;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TCariLoincLive;
    private widget.TextBox TCariMasterPemeriksaan;
    private widget.Button btnBarang;
    private widget.ComboBox hal;
    private widget.ComboBox hal1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame19;
    private widget.InternalFrame internalFrame20;
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
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.ComboBox page;
    private widget.ComboBox page1;
    private widget.PanelBiasa panelBiasa12;
    private widget.PanelBiasa panelBiasa14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    public widget.Table tbListLoinc;
    public widget.Table tbMasterPemeriksaan;
    private widget.ComboBox typeSampel;
    private widget.ComboBox typeStandar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select satu_sehat_mapping_lab.id_template,template_laboratorium.Pemeriksaan,satu_sehat_mapping_lab.code,satu_sehat_mapping_lab.system,"
                    + "satu_sehat_mapping_lab.display,satu_sehat_mapping_lab.sampel_code,satu_sehat_mapping_lab.sampel_system,satu_sehat_mapping_lab.sampel_display "
                    + "from satu_sehat_mapping_lab inner join template_laboratorium on satu_sehat_mapping_lab.id_template=template_laboratorium.id_template "
                    + (TCari.getText().equals("") ? "" : "where satu_sehat_mapping_lab.id_template like ? or template_laboratorium.Pemeriksaan like ? or "
                    + "satu_sehat_mapping_lab.code like ? or satu_sehat_mapping_lab.display like ? ")
                    + " order by satu_sehat_mapping_lab.code");
            try {
                if (!TCari.getText().equals("")) {
                    ps.setString(1, "%" + TCari.getText() + "%");
                    ps.setString(2, "%" + TCari.getText() + "%");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString("code"), rs.getString("system"), rs.getString("id_template"), rs.getString("Pemeriksaan"), rs.getString("display"), rs.getString("sampel_code"), rs.getString("sampel_system"), rs.getString("sampel_display")
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
    }

    public void emptTeks() {
        Code.setText("");
        LaboratoriumSystem.setText("");
        KodePemeriksaan.setText("");
        NamaPemeriksaan.setText("");
        Display.setText("");
        SampelCode.setText("");
        SampelLaboratoriumSystem.setText("");
        SampelDisplay.setText("");
        ChkInput.setSelected(true);
        isForm();
        Code.requestFocus();
    }

    private void getData() {
        if (tbJnsPerawatan.getSelectedRow() != -1) {
            Code.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString());
            LaboratoriumSystem.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 1).toString());
            KodePemeriksaan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 2).toString());
            NamaPemeriksaan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 3).toString());
            Display.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 4).toString());
            SampelCode.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 5).toString());
            SampelLaboratoriumSystem.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 6).toString());
            SampelDisplay.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 7).toString());
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getsatu_sehat_mapping_lab());
        BtnHapus.setEnabled(akses.getsatu_sehat_mapping_lab());
        BtnEdit.setEnabled(akses.getsatu_sehat_mapping_lab());
        BtnPrint.setEnabled(akses.getsatu_sehat_mapping_lab());
    }

    public JTable getTable() {
        return tbJnsPerawatan;
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 135));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    private void tampilListLoinc(String componen) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        auth = "fanji99:Kji99&&!!##";
        byte[] encodedBytes = Base64.encodeBase64(auth.getBytes());
        authEncrypt = new String(encodedBytes);
        try {
            int hasil, total, perpage;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange("https://loinc.regenstrief.org/searchapi/loincs?query=" + componen + "&rows=100&offset=0&sortorder=loinc_num", HttpMethod.GET, requestEntity, String.class).getBody());
//            System.out.println("response : " + root);
            LCountTotal.setText(root.path("ResponseSummary").path("RecordsFound").asText());

//            j = 1;
//            total = Integer.parseInt(root.path("total").asText());
//            perpage = Integer.parseInt(page.getSelectedItem().toString());
//            hasil = total / perpage;
//            LCountTotal.setText(root.path("total").asText());
//            hal.removeAllItems();
//            for (j = 1; j <= hasil; j++) {
//                hal.addItem(j);
//            }
////            System.out.println("response : " + root);
//            Valid.tabelKosong(tabModeListKFA);
//            i = 1;   "Code", "Long Component", "Component", "Property", "Timing", "System", "Scale", "Method", "UCUM"}) {
            for (JsonNode list : root.path("Results")) {
                tabModeLoinc.addRow(new String[]{
                    list.path("LOINC_NUM").asText(), list.path("LONG_COMMON_NAME").asText(), list.path("COMPONENT").asText(),
                    list.path("PROPERTY").asText(), list.path("TIME_ASPCT").asText(), list.path("SYSTEM").asText(),
                    list.path("SCALE_TYP").asText(), list.path("METHOD_TYP").asText(), list.path("EXAMPLE_UCUM_UNITS").asText()
                });
//                responsename = list.path("active_ingredients");
//                for (JsonNode responsename : responsename) {
//                    tabModeListKFA.addRow(new String[]{
//                        list.path("name").asText(), list.path("kfa_code").asText(), list.path("product_template").path("name").asText(), list.path("product_template").path("kfa_code").asText(), responsename.path("zat_aktif").asText(), responsename.path("kfa_code").asText(), responsename.path("kekuatan_zat_aktif").asText(), list.path("dosage_form").path("code").asText(), list.path("dosage_form").path("name").asText()
//                    });
//                }

                i++;
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
//        LCount.setText("" + tabModeListKFA.getRowCount());
        this.setCursor(Cursor.getDefaultCursor());
    }
}
