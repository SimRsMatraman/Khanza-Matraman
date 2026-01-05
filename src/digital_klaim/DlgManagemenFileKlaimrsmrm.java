package digital_klaim;

import digitalsignature.DlgViewPdf;
import fungsi.WarnaTable;
import kepegawaian.DlgCariPetugas;
import kepegawaian.DlgCariDokter;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
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
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import simrskhanza.DlgUbahPeriksaLab;

/**
 *
 * @author dosen
 */
public final class DlgManagemenFileKlaimrsmrm extends javax.swing.JDialog {

    private final DefaultTableModel TabModePasienRalan, TabModePasienRanap;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String pilihtable = "", kd_pj = "", kd_poli = "", sql = "";
    private int i = 0, c = 0;
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private StringBuilder htmlContent, htmlfooter;
    private JButton button = new JButton();
    private JButton btnResume = new JButton();
    private JButton btnLaboratorium = new JButton();
    private JButton btnRadiologi = new JButton();
    private JButton btnBillingRajal = new JButton();
    
    private JScrollPane scrollPane;
    private JPanel topPanel;
    private static ZipOutputStream zos;
    private Path sourceDir;
    private List<String> filesListInDir = new ArrayList<String>();

    //private String[] columns = new String[10];
    //private String[][] data = new String[0][0];
    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgManagemenFileKlaimrsmrm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(1156, 650);
        this.sourceDir = sourceDir;

        Object[] columns = new String[]{"P", "No Rawat", "No RM", "Nama Pasien", "Poli", "No SEP", "Tgl SEP", "File SEP", "File Resume", "File Laboratorium", "File Radiologi", "File Billing Rajal", "SEP", "Resume", "Laboratorium", "Radiologi", "BillingRajal"};
        TabModePasienRalan = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0 || colIndex == 12 || colIndex == 13 || colIndex == 14 || colIndex == 15 || colIndex == 16) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbListPasienRajal.setModel(TabModePasienRalan);
        tbListPasienRajal.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListPasienRajal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 17; i++) {
            TableColumn column = tbListPasienRajal.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(60);
            } else if (i == 3) {
                column.setPreferredWidth(300);
            } else if (i == 7) {
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
                column.setPreferredWidth(150);
            } else if (i == 8) {
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
                column.setPreferredWidth(150);
            } else if (i == 9) {
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
                column.setPreferredWidth(150);
            } else if (i == 10) {
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
                column.setPreferredWidth(150);
            } else if (i == 11) {
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
                column.setPreferredWidth(150);
            } else {
                column.setPreferredWidth(150);
            }
        }
        tbListPasienRajal.getColumn("SEP").setCellRenderer(new ButtonRenderer());
        tbListPasienRajal.getColumn("SEP").setCellEditor(new ButtonEditor(new JCheckBox()));
        tbListPasienRajal.getColumn("Resume").setCellRenderer(new ButtonRendererResume());
        tbListPasienRajal.getColumn("Resume").setCellEditor(new ButtonResume(new JCheckBox()));
        tbListPasienRajal.getColumn("Laboratorium").setCellRenderer(new ButtonRendererLaboratorium());
        tbListPasienRajal.getColumn("Laboratorium").setCellEditor(new ButtonLaboratorium(new JCheckBox()));
        tbListPasienRajal.getColumn("Radiologi").setCellRenderer(new ButtonRendererRadiologi());
        tbListPasienRajal.getColumn("Radiologi").setCellEditor(new ButtonRadiologi(new JCheckBox()));
        tbListPasienRajal.getColumn("BillingRajal").setCellRenderer(new ButtonRendererBillingRajal());
        tbListPasienRajal.getColumn("BillingRajal").setCellEditor(new ButtonBillingRajal(new JCheckBox()));
        tbListPasienRajal.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] columnsRanap = new String[]{"P", "No Rawat", "No RM", "Nama Pasien", "Poli", "No SEP", "Tgl SEP", "File SEP", "File Resume", "SEP", "Resume"};
        TabModePasienRanap = new DefaultTableModel(null, columnsRanap) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0 || colIndex == 9 || colIndex == 10) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbListPasienRanap.setModel(TabModePasienRanap);
        tbListPasienRanap.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListPasienRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 11; i++) {
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
                column.setMinWidth(0);
                column.setMaxWidth(0);
//            column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
//            column.setPreferredWidth(150);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
//            column.setPreferredWidth(150);
            } else {
                column.setPreferredWidth(150);
            }
        }
        tbListPasienRanap.getColumn("SEP").setCellRenderer(new ButtonRenderer());
        tbListPasienRanap.getColumn("SEP").setCellEditor(new ButtonEditor(new JCheckBox()));
        tbListPasienRanap.getColumn("Resume").setCellRenderer(new ButtonRendererResume());
        tbListPasienRanap.getColumn("Resume").setCellEditor(new ButtonResume(new JCheckBox()));
        tbListPasienRanap.setDefaultRenderer(Object.class, new WarnaTable());

        btnResume.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (TabRawat.getSelectedIndex() == 0) {
                    if (tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 8).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "Resume Belum Dibuat");
                    } else {
                        openFile(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 8).toString());
                    }
                } else if (TabRawat.getSelectedIndex() == 1) {
                    if (tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 8).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "Resume Belum Dibuat");
                    } else {
                        openFile(tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 8).toString());
                    }
                }
            }
        }
        );
        button.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (TabRawat.getSelectedIndex() == 0) {
                    if (tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 7).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "SEP Belum Dibuat");
                    } else {
                        openFile(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 7).toString());
                    }
                } else if (TabRawat.getSelectedIndex() == 1) {
                    if (tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 7).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "SEP Belum Dibuat");
                    } else {
                        openFile(tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 7).toString());
                    }
                }

            }
        }
        );

        btnLaboratorium.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (TabRawat.getSelectedIndex() == 0) {
                    if (tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 9).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "Laboratorium Belum Dibuat");
                    } else {
                        openFile(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 9).toString());
                    }
                } else if (TabRawat.getSelectedIndex() == 1) {
                    if (tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 9).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "Laboratorium Belum Dibuat");
                    } else {
                        openFile(tbListPasienRanap.getValueAt(tbListPasienRanap.getSelectedRow(), 9).toString());
                    }
                }
            }
        }
        );

        btnRadiologi.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (TabRawat.getSelectedIndex() == 0) {
                    if (tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 10).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "Radiologi Belum Dibuat");
                    } else {
                        openFile(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 10).toString());
                    }
                }
            }
        }
        );

        btnBillingRajal.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (TabRawat.getSelectedIndex() == 0) {
                    if (tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 11).toString().equals("-")) {
                        JOptionPane.showMessageDialog(null, "Billing Belum Dibuat");
                    } else {
                        openFile(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 11).toString());
                    }
                }
            }
        }
        );

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
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        jLabel7 = new widget.Label();
        DTPTglAwal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        DTPTglAkhir = new widget.Tanggal();
        label9 = new widget.Label();
        TCariTindakan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnMerger1 = new widget.Button();
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
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel7.setText("Tanggal :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelisi3.add(jLabel7);

        DTPTglAwal.setForeground(new java.awt.Color(50, 70, 50));
        DTPTglAwal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2024" }));
        DTPTglAwal.setDisplayFormat("dd-MM-yyyy");
        DTPTglAwal.setName("DTPTglAwal"); // NOI18N
        DTPTglAwal.setOpaque(false);
        DTPTglAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglAwalKeyPressed(evt);
            }
        });
        panelisi3.add(DTPTglAwal);

        jLabel8.setText("s/d");
        jLabel8.setName("jLabel8"); // NOI18N
        panelisi3.add(jLabel8);

        DTPTglAkhir.setForeground(new java.awt.Color(50, 70, 50));
        DTPTglAkhir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2024" }));
        DTPTglAkhir.setDisplayFormat("dd-MM-yyyy");
        DTPTglAkhir.setName("DTPTglAkhir"); // NOI18N
        DTPTglAkhir.setOpaque(false);
        DTPTglAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglAkhirKeyPressed(evt);
            }
        });
        panelisi3.add(DTPTglAkhir);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCariTindakan.setName("TCariTindakan"); // NOI18N
        TCariTindakan.setPreferredSize(new java.awt.Dimension(200, 23));
        TCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(TCariTindakan);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setName("BtnCariTindakan"); // NOI18N
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnCariTindakan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(LCount);

        BtnMerger1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/download1-24.png"))); // NOI18N
        BtnMerger1.setMnemonic('4');
        BtnMerger1.setText("Download");
        BtnMerger1.setToolTipText("Alt+4");
        BtnMerger1.setName("BtnMerger1"); // NOI18N
        BtnMerger1.setPreferredSize(new java.awt.Dimension(120, 23));
        BtnMerger1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMerger1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnMerger1);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
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


    private void TCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariTindakanActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            tbListPasienRalan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCariTindakan.requestFocus();
        }
}//GEN-LAST:event_TCariTindakanKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            tampilRalan();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilRanap();
        }
}//GEN-LAST:event_BtnCariTindakanActionPerformed

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
        TCariTindakan.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void DTPTglAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglAwalKeyPressed
//        Valid.pindah(evt,TCariTindakan,cmbJam);
    }//GEN-LAST:event_DTPTglAwalKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//       xw tampil();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_tbListPasienRanapMouseClicked

    private void tbListPasienRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListPasienRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbListPasienRanapKeyPressed

    private void tbListPasienRajalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListPasienRajalMouseClicked
        // TODO add your handling code here:
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
       if(TabRawat.getSelectedIndex()==0){
         if (TabModePasienRalan.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCariTindakan.requestFocus();
        }  else {
                ViewerKoding form = new ViewerKoding(null, false);
//                form.isCek();
                form.setSize(this.getWidth() - 20, this.getHeight() - 20);
                form.setDataPasien(tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 1).toString(),tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 2).toString(),tbListPasienRajal.getValueAt(tbListPasienRajal.getSelectedRow(), 3).toString(),"");
                form.setLocationRelativeTo(this);
                form.setVisible(true);
            }
          
       }else if(TabRawat.getSelectedIndex()==1){
           
       }
       
       
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppKodingBerkasActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgManagemenFileKlaimrsmrm dialog = new DlgManagemenFileKlaimrsmrm(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariTindakan;
    private widget.Button BtnKeluar;
    private widget.Button BtnMerger1;
    private widget.Tanggal DTPTglAkhir;
    private widget.Tanggal DTPTglAwal;
    private widget.Label LCount;
    private javax.swing.JMenu MnPilihCeklis;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCariTindakan;
    private widget.TextBox TNoRw;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppKodingBerkas;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbListPasienRajal;
    private widget.Table tbListPasienRanap;
    // End of variables declaration//GEN-END:variables

    private void tampilRalan() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            Valid.tabelKosong(TabModePasienRalan);
            sql = "select * from reg_periksa JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli JOIN bridging_sep ON reg_periksa.no_rawat=bridging_sep.no_rawat  where status_lanjut='Ralan'  and  tgl_registrasi BETWEEN ? and ? ";
            ps = koneksi.prepareStatement(sql);

            try {
                ps.setString(1, Valid.SetTgl(DTPTglAwal.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPTglAkhir.getSelectedItem() + ""));
                rs = ps.executeQuery();
                while (rs.next()) {
                    String fileSep, fileResume, noSep, tglSep, fileLaboratorium, fileRadiologi,fileBilling;
                    int sep = Sequel.cariInteger("select count(no_rawat) as total from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");

                    if (sep > 0) {
                        noSep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");
                        tglSep = Sequel.cariIsi("select tglsep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        noSep = "-";
                        tglSep = "-";
                    }
                    int berkassep = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='sep' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkassep > 0) {
                        fileSep = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='sep' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileSep = "-";
                    }
                    int berkasresume = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='resume' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasresume > 0) {
                        fileResume = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='resume' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileResume = "-";
                    }
                    int berkaslaboratorium = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='laboratorium' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkaslaboratorium > 0) {
                        fileLaboratorium = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='laboratorium' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileLaboratorium = "-";
                    }
                    int berkasradiologi = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='radiologi' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasradiologi > 0) {
                        fileRadiologi = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='radiologi' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileRadiologi = "-";
                    }
                    int berkasbilling = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='billing_ralan' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasbilling > 0) {
                        fileBilling = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='billing_ralan' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileBilling = "-";
                    }

                    TabModePasienRalan.addRow(new Object[]{
                        false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("nm_poli"), noSep, tglSep, fileSep, fileResume, fileLaboratorium, fileRadiologi,fileBilling
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
            sql = "select * from reg_periksa JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli  where status_lanjut='Ranap' and  tgl_registrasi BETWEEN ? and ? ";
            ps = koneksi.prepareStatement(sql);
            try {
                ps.setString(1, Valid.SetTgl(DTPTglAwal.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPTglAkhir.getSelectedItem() + ""));
                rs = ps.executeQuery();
                while (rs.next()) {
                    String fileSep, fileResume, noSep, tglSep;
                    int sep = Sequel.cariInteger("select count(no_rawat) as total from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");

                    if (sep > 0) {
                        noSep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");
                        tglSep = Sequel.cariIsi("select tglsep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        noSep = "-";
                        tglSep = "-";
                    }
                    int berkassep = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='sep_rajal' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkassep > 0) {
                        fileSep = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='sep_rajal' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileSep = "-";
                    }
                    int berkasresume = Sequel.cariInteger("select count(no_rawat) as total from tt_berkasdigital where jenis_file='resume_rajal' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    if (berkasresume > 0) {
                        fileResume = Sequel.cariIsi("select nama_file from tt_berkasdigital where jenis_file='resume_rajal' and  no_rawat='" + rs.getString("no_rawat") + "'");
                    } else {
                        fileResume = "-";
                    }

                    TabModePasienRanap.addRow(new Object[]{
                        false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("nm_poli"), noSep, tglSep, fileSep, fileResume
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
            Logger.getLogger(DlgManagemenFileKlaimrsmrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar start = Calendar.getInstance();
        start.setTime(DTPTglAwal.getDate());
        Calendar end = Calendar.getInstance();
        end.setTime(DTPTglAkhir.getDate());
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            if (TabRawat.getSelectedIndex() == 0) {
                for (i = 0; i < tbListPasienRajal.getRowCount(); i++) {
                    if (tbListPasienRajal.getValueAt(i, 0).toString().equals("true")) {
                        if (!tbListPasienRajal.getValueAt(i, 5).toString().equals("-")) {
                            if (tbListPasienRajal.getValueAt(i, 6).toString().equals(formattedDate)) {
                                try {
                                    sql = "select * from tt_berkasdigital where no_rawat='" + tbListPasienRajal.getValueAt(i, 1).toString() + "' order by (case when jenis_file ='sep' then 1 when jenis_file ='resume' then 2 when jenis_file ='billing_ralan'   then 2  else 3 END) ASC";
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
                                        pathFile = "berkasklaim/" + formattedDate + "/" + tbListPasienRajal.getValueAt(i, 5).toString();
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
                                    sql = "select * from tt_berkasdigital where no_rawat='" + tbListPasienRanap.getValueAt(i, 1).toString() + "' order by (case when jenis_file ='sep_rajal' then 1 when jenis_file ='resume_rajal'  then 2  else 3 END) ASC";
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
                                        pathFile = "berkasklaim/" + formattedDate + "/" + tbListPasienRanap.getValueAt(i, 5).toString();
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
            Logger.getLogger(DlgManagemenFileKlaimrsmrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
