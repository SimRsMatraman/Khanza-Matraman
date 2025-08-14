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

package freehand;

//import custom.*;
import fungsi.akses;
import simrskhanza.*;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import inventory.DlgCariKonversi;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import java.awt.BasicStroke;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.AlphaComposite;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.Image;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author perpustakaan
 */
public class DlgMarkingOperasiAlternatif extends javax.swing.JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private String username="",urlImage="";
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int index = 0;
    private Point[] arr = new Point[100000];
    private BufferedImage img;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private List<Point> titik = new ArrayList<>();
    private int radius = 50;
    private List<Lingkaran> lingkaranList = new ArrayList<>();
    /** Creates new form DlgPemberianObat
     * @param parent
     * @param modal */
    public DlgMarkingOperasiAlternatif (java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
   

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
//        final Dimension screenSize = toolkit.getScreenSize();
//        setSize(screenSize.width,screenSize.height);
        setResizable(false);
        this.setLocation(0,0);
        setSize(780,1000);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRawat = new widget.TextBox();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass9 = new widget.panelisi();
        PanelWall = new usu.widget.glass.PanelGlass();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        internalFrame4 = new widget.InternalFrame();
        panelGlass11 = new widget.panelisi();
        PanelWall2 = new usu.widget.glass.PanelGlass();
        internalFrame5 = new widget.InternalFrame();
        panelGlass12 = new widget.panelisi();
        PanelWall3 = new usu.widget.glass.PanelGlass();
        internalFrame6 = new widget.InternalFrame();
        panelGlass13 = new widget.panelisi();
        PanelWall4 = new usu.widget.glass.PanelGlass();
        internalFrame7 = new widget.InternalFrame();
        panelGlass14 = new widget.panelisi();
        PanelWall5 = new usu.widget.glass.PanelGlass();
        internalFrame8 = new widget.InternalFrame();
        panelGlass15 = new widget.panelisi();
        PanelWall6 = new usu.widget.glass.PanelGlass();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnHapus1 = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Marking Lokalis Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 60));
        FormInput.setLayout(null);

        jLabel3.setText("No. Rawat");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 65, 23);

        TNoRawat.setEditable(false);
        TNoRawat.setHighlighter(null);
        TNoRawat.setName("TNoRawat"); // NOI18N
        FormInput.add(TNoRawat);
        TNoRawat.setBounds(70, 10, 470, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);
        FormInput.getAccessibleContext().setAccessibleName("");
        FormInput.getAccessibleContext().setAccessibleDescription("");

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass9.setBorder(null);
        panelGlass9.setAlignmentX(0.0F);
        panelGlass9.setAlignmentY(0.0F);
        panelGlass9.setMinimumSize(new java.awt.Dimension(0, 0));
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall.setRound(false);
        PanelWall.setToolTipText("");
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWallMouseDragged(evt);
            }
        });
        PanelWall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWallMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWallMouseReleased(evt);
            }
        });
        PanelWall.setLayout(null);
        panelGlass9.add(PanelWall);

        internalFrame2.add(panelGlass9, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Form Default", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setBorder(null);
        panelGlass10.setAlignmentX(0.0F);
        panelGlass10.setAlignmentY(0.0F);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall1.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall1.setRound(false);
        PanelWall1.setToolTipText("");
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWall1MouseDragged(evt);
            }
        });
        PanelWall1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWall1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWall1MouseReleased(evt);
            }
        });
        PanelWall1.setLayout(null);
        panelGlass10.add(PanelWall1);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tarso", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setBorder(null);
        panelGlass11.setAlignmentX(0.0F);
        panelGlass11.setAlignmentY(0.0F);
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall2.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall2.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall2.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall2.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall2.setRound(false);
        PanelWall2.setToolTipText("");
        PanelWall2.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWall2MouseDragged(evt);
            }
        });
        PanelWall2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWall2MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWall2MouseReleased(evt);
            }
        });
        PanelWall2.setLayout(null);
        panelGlass11.add(PanelWall2);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Head & Neck", internalFrame4);

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass12.setBorder(null);
        panelGlass12.setAlignmentX(0.0F);
        panelGlass12.setAlignmentY(0.0F);
        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall3.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall3.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall3.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall3.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall3.setRound(false);
        PanelWall3.setToolTipText("");
        PanelWall3.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWall3MouseDragged(evt);
            }
        });
        PanelWall3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWall3MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWall3MouseReleased(evt);
            }
        });
        PanelWall3.setLayout(null);
        panelGlass12.add(PanelWall3);

        internalFrame5.add(panelGlass12, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Chest & Heart", internalFrame5);

        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass13.setBorder(null);
        panelGlass13.setAlignmentX(0.0F);
        panelGlass13.setAlignmentY(0.0F);
        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall4.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall4.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall4.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall4.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall4.setRound(false);
        PanelWall4.setToolTipText("");
        PanelWall4.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWall4MouseDragged(evt);
            }
        });
        PanelWall4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWall4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWall4MouseReleased(evt);
            }
        });
        PanelWall4.setLayout(null);
        panelGlass13.add(PanelWall4);

        internalFrame6.add(panelGlass13, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Genitourinary", internalFrame6);

        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass14.setBorder(null);
        panelGlass14.setAlignmentX(0.0F);
        panelGlass14.setAlignmentY(0.0F);
        panelGlass14.setName("panelGlass14"); // NOI18N
        panelGlass14.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall5.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall5.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall5.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall5.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall5.setRound(false);
        PanelWall5.setToolTipText("");
        PanelWall5.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWall5MouseDragged(evt);
            }
        });
        PanelWall5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWall5MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWall5MouseReleased(evt);
            }
        });
        PanelWall5.setLayout(null);
        panelGlass14.add(PanelWall5);

        internalFrame7.add(panelGlass14, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Bones or Joints 1", internalFrame7);

        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass15.setBorder(null);
        panelGlass15.setAlignmentX(0.0F);
        panelGlass15.setAlignmentY(0.0F);
        panelGlass15.setName("panelGlass15"); // NOI18N
        panelGlass15.setPreferredSize(new java.awt.Dimension(800, 500));
        panelGlass15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        PanelWall6.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall6.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall6.setMaximumSize(new java.awt.Dimension(800, 1000));
        PanelWall6.setPreferredSize(new java.awt.Dimension(730, 900));
        PanelWall6.setRound(false);
        PanelWall6.setToolTipText("");
        PanelWall6.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PanelWall6MouseDragged(evt);
            }
        });
        PanelWall6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelWall6MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PanelWall6MouseReleased(evt);
            }
        });
        PanelWall6.setLayout(null);
        panelGlass15.add(PanelWall6);

        internalFrame8.add(panelGlass15, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Bones or Joints 2", internalFrame8);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(100, 56));
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus Marking");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(150, 30));
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

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnHapus1.setMnemonic('H');
        BtnHapus1.setText("Gambar Baru");
        BtnHapus1.setToolTipText("Alt+H");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        BtnHapus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapus1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus1);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar.setMnemonic('T');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+T");
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                simpanMarkingOperasi(panelGlass9,  "opAlternatifForm",   "Form Default");
                break;
            case 1:
                simpanMarkingOperasi(panelGlass10, "opAlternatifBadan",  "Tarso");
                break;
            case 2:
                simpanMarkingOperasi(panelGlass11, "opAlternatifKepala", "Head & Neck");
                break;
            case 3:
                simpanMarkingOperasi(panelGlass12, "opAlternatifOrgan",  "Chest & Heart");
                break;
            case 4:
                simpanMarkingOperasi(panelGlass13, "opAlternatifAlat",   "Genitourinary");
                break;
            case 5:
                simpanMarkingOperasi(panelGlass14, "opAlternatifTulang", "Bones or Joints 1");
                break;
            case 6:
                simpanMarkingOperasi(panelGlass15, "opAlternatifSendi","Bones or Joints 2");
                break;
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
       
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       
    }//GEN-LAST:event_formWindowActivated

    private void PanelWallMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWallMouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWallMouseDragged

    private void PanelWallMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWallMouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWallMouseReleased

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                hapusMarkingOperasi("opAlternatifForm",   "Form Default");
                break;
            case 1:
                hapusMarkingOperasi("opAlternatifBadan",  "Tarso");
                break;
            case 2:
                hapusMarkingOperasi("opAlternatifKepala", "Head & Neck");
                break;
            case 3:
                hapusMarkingOperasi("opAlternatifOrgan",  "Chest & Heart");
                break;
            case 4:
                hapusMarkingOperasi("opAlternatifAlat",   "Genitourinary");
                break;
            case 5:
                hapusMarkingOperasi("opAlternatifTulang", "Bones or Joints 1");
                break;
            case 6:
                hapusMarkingOperasi("opAlternatifSendi","Bones or Joints 2");
                break;
        }
        repaint();
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed

    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapus1ActionPerformed
        lingkaranList.clear();
        arr = new Point[100000];
        index = 0;
        PanelWall.repaint();

        if (TabRawat.getSelectedIndex()==0) {
            imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/operasi.png");
        }
        if (TabRawat.getSelectedIndex()==1) {
            imageAssesment1("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Badan.png");
        }
        if (TabRawat.getSelectedIndex()==2) {
            imageAssesment2("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Kepala.png");
        }
        if (TabRawat.getSelectedIndex()==3) {
            imageAssesment3("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Organ.png");
        }
        if (TabRawat.getSelectedIndex()==4) {
            imageAssesment4("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Alat.png");
        }
        if (TabRawat.getSelectedIndex()==5) {
            imageAssesment5("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Tulang.png");
        }
        if (TabRawat.getSelectedIndex()==6) {
            imageAssesment6("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Tulang1.png");
        }
    }//GEN-LAST:event_BtnHapus1ActionPerformed

    private void BtnHapus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapus1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapus1KeyPressed

    private void PanelWallMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWallMouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWallMouseClicked

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
         tampil();  
        }
        else if(TabRawat.getSelectedIndex()==2){
         tampil1();  
        }
        else if(TabRawat.getSelectedIndex()==3){
         tampil2();  
        }
        else if(TabRawat.getSelectedIndex()==4){
         tampil3();  
        }
        else if(TabRawat.getSelectedIndex()==5){
         tampil4();  
        }
        else if(TabRawat.getSelectedIndex()==6){
         tampil5();  
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void PanelWall1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall1MouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWall1MouseDragged

    private void PanelWall1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall1MouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWall1MouseClicked

    private void PanelWall1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall1MouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWall1MouseReleased

    private void PanelWall2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall2MouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWall2MouseDragged

    private void PanelWall2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall2MouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWall2MouseClicked

    private void PanelWall2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall2MouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWall2MouseReleased

    private void PanelWall3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall3MouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWall3MouseDragged

    private void PanelWall3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall3MouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWall3MouseClicked

    private void PanelWall3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall3MouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWall3MouseReleased

    private void PanelWall4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall4MouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWall4MouseDragged

    private void PanelWall4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall4MouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWall4MouseClicked

    private void PanelWall4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall4MouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWall4MouseReleased

    private void PanelWall5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall5MouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWall5MouseDragged

    private void PanelWall5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall5MouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWall5MouseClicked

    private void PanelWall5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall5MouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWall5MouseReleased

    private void PanelWall6MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall6MouseDragged
        arr[index] = new Point(evt.getX(), evt.getY());
        index++;

        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics(); 
        g2.setColor(new Color(255, 0, 0, 180));
        g2.setStroke(new BasicStroke(5));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 

        for (int i = 0; i < index - 1; i++) {
            g2.drawLine(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
    }//GEN-LAST:event_PanelWall6MouseDragged

    private void PanelWall6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall6MouseClicked
        Point p = evt.getPoint();
        boolean diperbesar = false;

        for (Lingkaran l : lingkaranList) {
            if (l.titik.distance(p) < l.radius) {
                l.radius += 10; // Perbesar
                gambarLingkaran(l.titik.x, l.titik.y, l.radius); // Gambar ulang
                diperbesar = true;
                break;
            }
        }

        if (!diperbesar) {
            Lingkaran lBaru = new Lingkaran(p, 50); // radius default
            lingkaranList.add(lBaru);
            gambarLingkaran(p.x, p.y, 50);
        }
    }//GEN-LAST:event_PanelWall6MouseClicked

    private void PanelWall6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelWall6MouseReleased
        arr = new Point[100000];
        index = 0;
    }//GEN-LAST:event_PanelWall6MouseReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgMarkingOperasiAlternatif dialog = new DlgMarkingOperasiAlternatif(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private usu.widget.glass.PanelGlass PanelWall2;
    private usu.widget.glass.PanelGlass PanelWall3;
    private usu.widget.glass.PanelGlass PanelWall4;
    private usu.widget.glass.PanelGlass PanelWall5;
    private usu.widget.glass.PanelGlass PanelWall6;
    private widget.TextBox TNoRawat;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel3;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    // End of variables declaration//GEN-END:variables
    

    private void isPsien() {
//        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    public void setNoRw(String norw) {
       
        TNoRawat.setText(norw); 
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Form Default' and no_rawat='"+norw+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/operasi.png");
       }else{
             imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    public void tampil() {
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Tarso' and no_rawat='"+TNoRawat.getText()+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment1("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Badan.png");
       }else{
             imageAssesment1("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    public void tampil1() {
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Head & Neck' and no_rawat='"+TNoRawat.getText()+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment2("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Kepala.png");
       }else{
             imageAssesment2("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    public void tampil2() { 
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Chest & Heart' and no_rawat='"+TNoRawat.getText()+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment3("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Organ.png");
       }else{
             imageAssesment3("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    public void tampil3() {
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Genitourinary' and no_rawat='"+TNoRawat.getText()+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment4("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Alat.png");
       }else{
             imageAssesment4("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    public void tampil4() {
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Bones or Joints 1' and no_rawat='"+TNoRawat.getText()+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment5("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Tulang.png");
       }else{
             imageAssesment5("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    public void tampil5() {
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif_dummy where posisi='Bones or Joints 2' and no_rawat='"+TNoRawat.getText()+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment6("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/Tulang1.png");
       }else{
             imageAssesment6("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }    
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(true);

       
        
    }
    void uploadImage(String FileName,String docpath){
        try{
        File file =new File("tmpImageFreehand/"+FileName);
        byte[] data = new byte[(int) file.length()];
        data = FileUtils.readFileToByteArray(file);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/upload.php?doc="+docpath);
        ByteArrayBody fileData = new ByteArrayBody(data, FileName);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("file", fileData); 
        postRequest.setEntity(reqEntity);
        httpClient.execute(postRequest); 
//        HttpResponse response = (HttpResponse) httpClient.execute(postRequest); 
        deleteFile();
        
        }catch (Exception e){
            System.out.println("Upload error"+e);
        }
    }
    
    void deleteFile(){
       File file = new File("tmpImageFreehand");      
        String[] myFiles;    
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]); 
                myFile.delete();
            }
        }
    }
    
    void imageAssesment(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                PanelWall.setBackgroundImage(new javax.swing.ImageIcon(img));
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    void imageAssesment1(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                // Ambil ukuran panel
                int panelWidth = PanelWall1.getWidth();
                int panelHeight = PanelWall1.getHeight();

                // Resize gambar sesuai ukuran panel
                Image scaledImage = img.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

                // Set layout dan tambahkan JLabel gambar
                PanelWall1.removeAll();
                PanelWall1.setLayout(new BorderLayout());
                JLabel labelGambar = new JLabel(new ImageIcon(scaledImage));
                labelGambar.setHorizontalAlignment(SwingConstants.CENTER);
                PanelWall1.add(labelGambar, BorderLayout.CENTER);
                PanelWall1.revalidate();
                PanelWall1.repaint();
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    void imageAssesment2(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                PanelWall2.setBackgroundImage(new javax.swing.ImageIcon(img));
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    void imageAssesment3(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                PanelWall3.setBackgroundImage(new javax.swing.ImageIcon(img));
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    void imageAssesment4(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                PanelWall4.setBackgroundImage(new javax.swing.ImageIcon(img));
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    void imageAssesment5(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                PanelWall5.setBackgroundImage(new javax.swing.ImageIcon(img));
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    void imageAssesment6(String url) {  
        if (url == null || url.trim().isEmpty()) {
            System.err.println("URL marking kosong atau null");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new URL(url.trim()));
            if (img != null) {
                PanelWall6.setBackgroundImage(new javax.swing.ImageIcon(img));
            } else {
                System.err.println("Marking tidak berhasil dimuat dari URL: " + url);
            }
        } catch (IOException ex) {
            System.err.println("Gagal membaca marking dari URL: " + url);
            ex.printStackTrace();
        }
    }
    
    private void gambarLingkaran(int x, int y, int radius) {
        Graphics2D g2 = (Graphics2D) PanelWall.getGraphics();

        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.7f)); // transparan
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(6));
        g2.drawOval(x - radius / 2, y - radius / 2, radius, radius);
    }
    
    private class Lingkaran {
        Point titik;
        int radius;

        Lingkaran(Point titik, int radius) {
            this.titik = titik;
            this.radius = radius;
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }
    
    public void setTampil1(){
       TabRawat.setSelectedIndex(2);
       tampil1();
    }
    
    public void setTampil2(){
       TabRawat.setSelectedIndex(3);
       tampil2();
    }
    
    public void setTampil3(){
       TabRawat.setSelectedIndex(4);
       tampil3();
    }
    
    public void setTampil4(){
       TabRawat.setSelectedIndex(5);
       tampil4();
    }
    
    public void setTampil5(){
       TabRawat.setSelectedIndex(6);
       tampil5();
    }
    
    private void simpanMarkingOperasi(JPanel panelTarget, String prefixFile, String posisi) {
        try {
            // Siapkan nama file
            String rawat = TNoRawat.getText().replaceAll("/", "");
            String folderPath = "tmpImageFreehand";
            String fileName = prefixFile + rawat + ".png";
            File outputFile = new File(folderPath, fileName);

            // Pastikan folder ada
            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                Logger.getLogger(DlgMarkingOperasiAlternatif.class.getName())
                      .log(Level.SEVERE, "Gagal membuat direktori: " + parentDir.getAbsolutePath());
                JOptionPane.showMessageDialog(null,
                        "Gagal membuat folder penyimpanan marking.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ambil gambar panel
            Robot r = new Robot();
            Point p = panelTarget.getLocationOnScreen();
            Rectangle capture = new Rectangle(p.x, p.y, panelTarget.getWidth(), panelTarget.getHeight());
            BufferedImage image = r.createScreenCapture(capture);
            ImageIO.write(image, "png", outputFile);

            // Upload gambar
            String urlImage = "penandalokasioperasimetodealternatif/imagemarking/" + fileName;
            uploadImage(fileName, "penandalokasioperasimetodealternatif/imagemarking");

            boolean success;
            // Simpan atau update ke database berdasarkan no_rawat + posisi
            if (Sequel.cariInteger(
                    "SELECT COUNT(*) FROM marking_operasi_alternatif_dummy " +
                    "WHERE no_rawat='" + TNoRawat.getText() + "' AND posisi='" + posisi + "'") > 0) {

                success = Sequel.mengedittf("marking_operasi_alternatif_dummy",
                        "no_rawat=? AND posisi=?",
                        "tanggal=?, jam=?, url_image=?",
                        5, new String[]{
                            tanggalNow.format(new Date()),
                            jamNow.format(new Date()),
                            urlImage,
                            TNoRawat.getText(),
                            posisi
                        });
            } else {
                success = Sequel.menyimpantf("marking_operasi_alternatif_dummy",
                        "?,?,?,?,?",
                        "No.Rawat",
                        5, new String[]{
                            TNoRawat.getText(),
                            tanggalNow.format(new Date()),
                            jamNow.format(new Date()),
                            urlImage,
                            posisi
                        });
            }

            // Notifikasi hasil
            if (success) {
                JOptionPane.showMessageDialog(null,
                        "Marking " + posisi + " berhasil disimpan",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Marking " + posisi + " berhasil disimpan, tetapi database gagal diperbarui",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            }

        } catch (AWTException | IOException ex) {
            Logger.getLogger(DlgMarkingOperasiAlternatif.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,
                    "Terjadi kesalahan saat mengambil atau menyimpan marking.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusMarkingOperasi(String prefixFile, String posisi) {
        try {
            String rawat = TNoRawat.getText().replaceAll("/", "");

            // Ambil nama file dari DB
            String urlImage = Sequel.cariIsi(
                "SELECT url_image FROM marking_operasi_alternatif_dummy " +
                "WHERE no_rawat='" + TNoRawat.getText() + "' AND posisi='" + posisi + "'"
            );

            if (urlImage != null && !urlImage.trim().isEmpty()) {
                // Lokasi file lokal (sesuaikan path jika perlu)
                File fileGambar = new File("tmpImageFreehand", prefixFile + rawat + ".png");
                if (fileGambar.exists()) {
                    if (fileGambar.delete()) {
                        System.out.println("File lokal terhapus: " + fileGambar.getAbsolutePath());
                    } else {
                        System.out.println("Gagal hapus file lokal: " + fileGambar.getAbsolutePath());
                    }
                }

                // TODO: Jika mau hapus file di server, bisa tambahkan request HTTP ke server di sini
            }

            // Hapus dari database
            boolean success = Sequel.queryu2tf(
                "DELETE FROM marking_operasi_alternatif_dummy " +
                "WHERE no_rawat=? AND posisi=?",
                2, new String[]{ TNoRawat.getText(), posisi }
            );

            if (success) {
                JOptionPane.showMessageDialog(null,
                    "Data dan file untuk posisi '" + posisi + "' berhasil dihapus.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Gagal menghapus data untuk posisi '" + posisi + "'.",
                    "Gagal", JOptionPane.ERROR_MESSAGE);
            }

            // Refresh tampilan
            tampil();
            tampil1();
            tampil2();
            tampil3();
            tampil4();
            tampil5();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Terjadi kesalahan saat menghapus data/marking: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
