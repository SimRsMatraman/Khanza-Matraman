/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */


package rekammedis;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import freehand.DlgMarkingOperasiAlternatif;
import java.util.HashMap;
import java.util.Map;
import setting.DlgCariRuangOperasi;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.net.URL;
import java.io.IOException;




/**
 *
 * @author perpustakaan
 */
public final class RMMarkingOperasiAlternatif extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan="",urlImage,finger="",kodepetugas="",namapetugas=""; 
    private StringBuilder htmlContent;
    public DlgMasterRencanaKeperawatan masterr=new DlgMasterRencanaKeperawatan(null,false);
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private DlgCariRuangOperasi ruangok=new DlgCariRuangOperasi(null,false);
    private DlgCariBangsal ruangokranap=new DlgCariBangsal(null,false);
    private DlgCariPoli ruangokralan=new DlgCariPoli(null,false);
    private JPopupMenu popupMenu;
    private JMenuItem menuLihatData;
    private JMenuItem menuHapusData;
    private JLabel labelGambar = new JLabel();
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMMarkingOperasiAlternatif(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        popupMenu = new JPopupMenu();
        menuLihatData = new JMenuItem("Cetak Penandaan Lokasi Operasi Metode Alternatif");

        popupMenu.add(menuLihatData);

        // Aksi ketika menu diklik
        menuLihatData.addActionListener(e -> cetak());

        // Tampilkan popup saat klik kanan pada tabel
        tbObat.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                int row = tbObat.rowAtPoint(e.getPoint());
                if (row >= 0 && row < tbObat.getRowCount()) {
                    tbObat.setRowSelectionInterval(row, row);
                } else {
                    tbObat.clearSelection();
                }

                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/operasi.png");
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","Tanggal","Jam","NIP","Nama Dokter","Kode","Nama Ruangan","Jenis Operasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(60);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(120);
            }else if(i==11){
                column.setPreferredWidth(120);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        JenisOperasi.setDocument(new batasInput((int)100).getKata(JenisOperasi));
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
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
                    KdPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdPetugas.requestFocus();
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
        
        ruangok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruangok.getTable().getSelectedRow()!= -1){                    
                    KdRuangan.setText(ruangok.getTable().getValueAt(ruangok.getTable().getSelectedRow(),0).toString());
                    NmRuangan.setText(ruangok.getTable().getValueAt(ruangok.getTable().getSelectedRow(),1).toString());
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
        
        ruangokranap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruangokranap.getTable().getSelectedRow()!= -1){                    
                    KdRuangan.setText(ruangokranap.getTable().getValueAt(ruangokranap.getTable().getSelectedRow(),0).toString());
                    NmRuangan.setText(ruangokranap.getTable().getValueAt(ruangokranap.getTable().getSelectedRow(),1).toString());
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
        
        ruangokralan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruangokralan.getTable().getSelectedRow()!= -1){                    
                    KdRuangan.setText(ruangokralan.getTable().getValueAt(ruangokralan.getTable().getSelectedRow(),0).toString());
                    NmRuangan.setText(ruangokralan.getTable().getValueAt(ruangokralan.getTable().getSelectedRow(),1).toString());
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
                if(akses.getform().equals("RMPenilaianAwalKeperawatanRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        masterr.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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
        
        
//        ChkAccor.setSelected(false);
//        isMenu();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
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
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel38 = new widget.Label();
        JenisOperasi = new widget.TextBox();
        jSeparator13 = new javax.swing.JSeparator();
        BtnEdit1 = new widget.Button();
        PanelWall = new usu.widget.glass.PanelGlass();
        jLabel53 = new widget.Label();
        KdRuangan = new widget.TextBox();
        NmRuangan = new widget.TextBox();
        BtnBangsal = new widget.Button();
        DTPTgl = new widget.Tanggal();
        jLabel12 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();
        BtnBangsalRanap = new widget.Button();
        BtnBangsalRajal = new widget.Button();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penanda Lokasi Operasi Metode ALternatif ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1100));
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
        TPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPasienActionPerformed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
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
        KdPetugas.setBounds(74, 40, 140, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(215, 40, 220, 23);

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
        BtnDokter.setBounds(440, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 120, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(800, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(770, 10, 30, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("LOKASI OPERASI METODE ALTERNATIF");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(40, 140, 220, 23);

        jLabel38.setText("Ruangan :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 70, 70, 23);

        JenisOperasi.setName("JenisOperasi"); // NOI18N
        JenisOperasi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(JenisOperasi);
        JenisOperasi.setBounds(74, 100, 400, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 130, 880, 1);

        BtnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit1.setMnemonic('G');
        BtnEdit1.setText("MARKING LOKALIS");
        BtnEdit1.setToolTipText("Alt+G");
        BtnEdit1.setName("BtnEdit1"); // NOI18N
        BtnEdit1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit1ActionPerformed(evt);
            }
        });
        BtnEdit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit1KeyPressed(evt);
            }
        });
        FormInput.add(BtnEdit1);
        BtnEdit1.setBounds(240, 140, 160, 30);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(30, 180, 910, 950);
        PanelWall.getAccessibleContext().setAccessibleName("");

        jLabel53.setText("Jenis Operasi :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(-10, 100, 80, 23);

        KdRuangan.setName("KdRuangan"); // NOI18N
        KdRuangan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(KdRuangan);
        KdRuangan.setBounds(74, 70, 140, 23);

        NmRuangan.setName("NmRuangan"); // NOI18N
        NmRuangan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmRuangan);
        NmRuangan.setBounds(215, 70, 220, 23);

        BtnBangsal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBangsal.setMnemonic('2');
        BtnBangsal.setText("Operasi");
        BtnBangsal.setToolTipText("Alt+2");
        BtnBangsal.setName("BtnBangsal"); // NOI18N
        BtnBangsal.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnBangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBangsalActionPerformed(evt);
            }
        });
        BtnBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBangsalKeyPressed(evt);
            }
        });
        FormInput.add(BtnBangsal);
        BtnBangsal.setBounds(440, 70, 90, 20);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-07-2025" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(550, 40, 90, 23);

        jLabel12.setText("Tanggal :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(470, 40, 70, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(650, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(710, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(770, 40, 62, 23);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TTE Pasien : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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

        LoadHTML1.setBorder(null);
        LoadHTML1.setName("LoadHTML1"); // NOI18N
        Scroll4.setViewportView(LoadHTML1);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        FormInput.add(FormPhoto);
        FormPhoto.setBounds(960, 160, 380, 380);

        BtnBangsalRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBangsalRanap.setMnemonic('2');
        BtnBangsalRanap.setText("Ranap");
        BtnBangsalRanap.setToolTipText("Alt+2");
        BtnBangsalRanap.setName("BtnBangsalRanap"); // NOI18N
        BtnBangsalRanap.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnBangsalRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBangsalRanapActionPerformed(evt);
            }
        });
        BtnBangsalRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBangsalRanapKeyPressed(evt);
            }
        });
        FormInput.add(BtnBangsalRanap);
        BtnBangsalRanap.setBounds(530, 70, 90, 20);

        BtnBangsalRajal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBangsalRajal.setMnemonic('2');
        BtnBangsalRajal.setText("Rajal");
        BtnBangsalRajal.setToolTipText("Alt+2");
        BtnBangsalRajal.setName("BtnBangsalRajal"); // NOI18N
        BtnBangsalRajal.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnBangsalRajal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBangsalRajalActionPerformed(evt);
            }
        });
        BtnBangsalRajal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBangsalRajalKeyPressed(evt);
            }
        });
        FormInput.add(BtnBangsalRajal);
        BtnBangsalRajal.setBounds(620, 70, 90, 20);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-07-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-07-2025" }));
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(NmRuangan.getText().trim().equals("")){
            Valid.textKosong(NmRuangan,"Ruangan");
        }else if(JenisOperasi.getText().trim().equals("")){
            Valid.textKosong(JenisOperasi,"Jenis Operasi");
        }else{
            if(Sequel.menyimpantf("ttd_marking_operasi_alternatif","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,new String[]{
                    TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuangan.getText(),
                    JenisOperasi.getText(),KdPetugas.getText(),"","","","","-"
                })==true){
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(Sequel.queryu2tf("delete from ttd_marking_operasi_alternatif where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
            }
        }
        
        if(tbObat.getSelectedRow()>-1){
            String noRawat = tbObat.getValueAt(tbObat.getSelectedRow(),0).toString();

            // Ambil nama file image dari DB sebelum dihapus
            String urlImage = Sequel.cariIsi("SELECT url_image FROM marking_operasi_alternatif WHERE no_rawat=?", noRawat);

            // Proses hapus dari DB
            if(Sequel.queryu2tf("delete from marking_operasi_alternatif where no_rawat=?", 1, new String[]{ noRawat }) == true){
                // Proses hapus file gambar
                if(!urlImage.equals("")){
                    try {
                        String imageDeleteUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/delete_image.php";

                        URL url = new URL(imageDeleteUrl);
                        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                        httpCon.setRequestMethod("POST");
                        httpCon.setDoOutput(true);
                        httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        String postData = "filename=" + URLEncoder.encode(urlImage.trim(), "UTF-8");
                        OutputStream os = httpCon.getOutputStream();
                        os.write(postData.getBytes());
                        os.flush();
                        os.close();

                        int responseCode = httpCon.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            System.out.println("File berhasil dihapus dari server.");
                        } else {
                            System.out.println("Gagal menghapus file dari server. Code: " + responseCode);
                        }
                    } catch (Exception e) {
                        System.err.println("Error saat menghapus file: " + e.toString());
                    }
                }

                tampil();
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
        } else {
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(NmRuangan.getText().trim().equals("")){
            Valid.textKosong(NmRuangan,"Ruangan");
        }else if(JenisOperasi.getText().trim().equals("")){
            Valid.textKosong(JenisOperasi,"Jenis Operasi");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("ttd_marking_operasi_alternatif","no_rawat=?","no_rawat=?,tanggal=?,jam=?,kd_ruangan=?,jns_op=?,nik=?",7,new String[]{
                    TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuangan.getText(),
                    JenisOperasi.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                       tampil();
                       emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
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
//                ChkAccor.setSelected(true);
//                isMenu();
//                getMasalah();
                getData();
//                TabRawat.setSelectedIndex(0);
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_SPACE)){
                try {
//                    ChkAccor.setSelected(true);
//                    isMenu();
//                    getMasalah();
                    TabRawat.setSelectedIndex(0);
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        tampilMasalah();
    }//GEN-LAST:event_formWindowOpened

    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasienActionPerformed

    private void BtnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit1ActionPerformed
        DlgMarkingOperasiAlternatif form=new DlgMarkingOperasiAlternatif(null,false);
        form.setNoRw(TNoRw.getText());
        form.setVisible(true);
        form.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif where no_rawat='"+TNoRw.getText()+"' ");
                imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage+"");
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

    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void BtnBangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBangsalActionPerformed
        ruangok.isCek();
        ruangok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruangok.setLocationRelativeTo(internalFrame1);
        ruangok.setVisible(true);
    }//GEN-LAST:event_BtnBangsalActionPerformed

    private void BtnBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBangsalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBangsalKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,TCari,Jam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,DTPTgl,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
  
    }//GEN-LAST:event_DetikKeyPressed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void BtnBangsalRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBangsalRanapActionPerformed
        ruangokranap.isCek();
        ruangokranap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruangokranap.setLocationRelativeTo(internalFrame1);
        ruangokranap.setVisible(true);
    }//GEN-LAST:event_BtnBangsalRanapActionPerformed

    private void BtnBangsalRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBangsalRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBangsalRanapKeyPressed

    private void BtnBangsalRajalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBangsalRajalActionPerformed
        ruangokralan.isCek();
        ruangokralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruangokralan.setLocationRelativeTo(internalFrame1);
        ruangokralan.setVisible(true);
    }//GEN-LAST:event_BtnBangsalRajalActionPerformed

    private void BtnBangsalRajalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBangsalRajalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBangsalRajalKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            AsesmenAwalMedisIGD dialog = new AsesmenAwalMedisIGD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBangsal;
    private widget.Button BtnBangsalRajal;
    private widget.Button BtnBangsalRanap;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox Jam;
    private widget.TextBox JenisOperasi;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdRuangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML1;
    private widget.ComboBox Menit;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmRuangan;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel36;
    private widget.Label jLabel38;
    private widget.Label jLabel53;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator13;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "SELECT "+
                        "ttd_marking_operasi_alternatif.*, "+
                        "pasien.nm_pasien, "+
                        "pasien.tgl_lahir, "+
                        "pasien.jk, "+
                        "pasien.no_rkm_medis, "+
                        "pegawai.nama, "+
                        "CASE "+
                        "    WHEN ruang_ok.nm_ruang_ok IS NOT NULL THEN ruang_ok.nm_ruang_ok "+
                        "    WHEN bangsal.nm_bangsal IS NOT NULL THEN bangsal.nm_bangsal "+
                        "    ELSE poliklinik.nm_poli "+
                        "END AS nama_ruangan "+
                        "FROM ttd_marking_operasi_alternatif "+
                        "INNER JOIN reg_periksa ON reg_periksa.no_rawat = ttd_marking_operasi_alternatif.no_rawat "+
                        "LEFT JOIN kamar_inap ON kamar_inap.no_rawat = reg_periksa.no_rawat  "+
                        "LEFT JOIN pasien ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis "+
                        "LEFT JOIN pegawai ON pegawai.nik = ttd_marking_operasi_alternatif.nik "+
                        "LEFT JOIN ruang_ok ON ruang_ok.kd_ruang_ok = ttd_marking_operasi_alternatif.kd_ruangan "+
                        "LEFT JOIN poliklinik ON poliklinik.kd_poli = ttd_marking_operasi_alternatif.kd_ruangan "+
                        "LEFT JOIN bangsal ON bangsal.kd_bangsal = ttd_marking_operasi_alternatif.kd_ruangan "+
                        "LEFT JOIN kamar ON kamar.kd_kamar = kamar_inap.kd_kamar WHERE "+
                        "ttd_marking_operasi_alternatif.tanggal BETWEEN ? AND ? ORDER BY ttd_marking_operasi_alternatif.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "SELECT "+
                        "ttd_marking_operasi_alternatif.*, "+
                        "pasien.nm_pasien, "+
                        "pasien.tgl_lahir, "+
                        "pasien.jk, "+
                        "pasien.no_rkm_medis, "+
                        "pegawai.nama, "+
                        "CASE "+
                        "    WHEN ruang_ok.nm_ruang_ok IS NOT NULL THEN ruang_ok.nm_ruang_ok "+
                        "    WHEN bangsal.nm_bangsal IS NOT NULL THEN bangsal.nm_bangsal "+
                        "    ELSE poliklinik.nm_poli "+
                        "END AS nama_ruangan "+
                        "FROM ttd_marking_operasi_alternatif "+
                        "INNER JOIN reg_periksa ON reg_periksa.no_rawat = ttd_marking_operasi_alternatif.no_rawat "+
                        "LEFT JOIN kamar_inap ON kamar_inap.no_rawat = reg_periksa.no_rawat  "+
                        "LEFT JOIN pasien ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis "+
                        "LEFT JOIN pegawai ON pegawai.nik = ttd_marking_operasi_alternatif.nik "+
                        "LEFT JOIN ruang_ok ON ruang_ok.kd_ruang_ok = ttd_marking_operasi_alternatif.kd_ruangan "+
                        "LEFT JOIN poliklinik ON poliklinik.kd_poli = ttd_marking_operasi_alternatif.kd_ruangan "+
                        "LEFT JOIN bangsal ON bangsal.kd_bangsal = ttd_marking_operasi_alternatif.kd_ruangan "+
                        "LEFT JOIN kamar ON kamar.kd_kamar = kamar_inap.kd_kamar WHERE "+
                        "ttd_marking_operasi_alternatif.tanggal BETWEEN ? AND ? AND reg_periksa.no_rawat LIKE ? OR "+
                        "ttd_marking_operasi_alternatif.tanggal BETWEEN ? AND ? AND pasien.no_rkm_medis LIKE ? OR "+
                        "ttd_marking_operasi_alternatif.tanggal BETWEEN ? AND ? AND pasien.nm_pasien LIKE ? OR "+
                        "ttd_marking_operasi_alternatif.tanggal BETWEEN ? AND ? AND ttd_marking_operasi_alternatif.nik LIKE ? OR "+
                        "ttd_marking_operasi_alternatif.tanggal BETWEEN ? AND ? AND pegawai.nama LIKE ? ORDER BY ttd_marking_operasi_alternatif.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),rs.getString("jam"),
                        rs.getString("nik"),rs.getString("nama"),rs.getString("kd_ruangan"),rs.getString("nama_ruangan"),rs.getString("jns_op")
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
        KdPetugas.setText("");
        NmPetugas.setText("");
        JenisOperasi.setText("-");
        KdRuangan.setText("");
        NmRuangan.setText("");
        Jam.setSelectedItem("00");
        Menit.setSelectedItem("00");
        Detik.setSelectedItem("00"); 
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
        imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/operasi.png");
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Valid.SetTgl(DTPTgl,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(0,2));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(3,5));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(6,8));
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KdRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NmRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            JenisOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' ");
            imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage+"");
            panggilPhoto();
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        try {
            ps=koneksi.prepareStatement(
                    "select nm_pasien, if(jk='L','Laki-Laki','Perempuan') as jk,tgl_lahir,agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
        
//        Tampil Ruang OK
        try {
            ps=koneksi.prepareStatement(
                    "SELECT booking_operasi.kd_ruang_ok, "+
                    "ruang_ok.nm_ruang_ok "+
                    "FROM booking_operasi "+
                    "INNER JOIN ruang_ok on booking_operasi.kd_ruang_ok=ruang_ok.kd_ruang_ok "+
                    "WHERE booking_operasi.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    KdRuangan.setText(rs.getString("kd_ruang_ok"));
                    NmRuangan.setText(rs.getString("nm_ruang_ok"));
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
        
//        Tampil Ranap
        try {
            ps=koneksi.prepareStatement(
                    "SELECT kamar.kd_bangsal, "+
                    "bangsal.nm_bangsal "+
                    "FROM kamar_inap "+
                    "INNER JOIN kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "INNER JOIN bangsal on bangsal.kd_bangsal=kamar.kd_bangsal "+
                    "WHERE kamar_inap.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    KdRuangan.setText(rs.getString("kd_bangsal"));
                    NmRuangan.setText(rs.getString("nm_bangsal"));
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
        
//        Tampil Ralan
        try {
            ps=koneksi.prepareStatement(
                    "SELECT reg_periksa.kd_poli, "+
                    "poliklinik.nm_poli "+
                    "FROM reg_periksa "+
                    "INNER JOIN poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "WHERE reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    KdRuangan.setText(rs.getString("kd_poli"));
                    NmRuangan.setText(rs.getString("nm_poli"));
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
    
    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPetugas.setText(akses.getkode());
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmPetugas,KdPetugas.getText());
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);   
        urlImage=Sequel.cariIsi("select url_image from marking_operasi_alternatif where no_rawat='"+norwt+"' ");
        if(urlImage.toString().equals(null)||urlImage.toString().equals("")){
           imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/masterimage/operasi.png");
       }else{
             imageAssesment("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/imagefreehand/"+urlImage.trim()+"");
       }  
        isRawat(); 
        isPsien();
        panggilPhoto();
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdata_resume_pasien());
        BtnHapus.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
//        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
//            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }
    
//    void imageAssesment(String url){
//        try {
//            BufferedImage img = ImageIO.read(new URL(url.trim()));
//            PanelWall.setBackgroundImage(new javax.swing.ImageIcon(img));
//        }
//        catch(IOException ex) {
//
//        }
//    }
    
    public void imageAssesment(String imagePath) {
        try {
            if (imagePath != null && !imagePath.trim().isEmpty()) {
                // Membaca gambar dari URL
                BufferedImage img = ImageIO.read(new URL(imagePath.trim()));

                // Set ke PanelWall (pastikan PanelWall bukan null)
                PanelWall.setBackgroundImage(new ImageIcon(img));

                // Tampilkan juga ke labelGambar
                ImageIcon icon = new ImageIcon(img);
                labelGambar.setIcon(icon);
                labelGambar.setToolTipText("✔ Gambar berhasil dimuat.");
            } else {
                System.err.println("⛔ imagePath kosong/null: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("⛔ Gagal membaca gambar dari URL: " + imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("⛔ Terjadi error lain saat memuat gambar.");
        }
    }


    
        private void panggilPhoto() {
//        if(FormPhotoPass.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select ttd_marking_operasi_alternatif.ttd from ttd_marking_operasi_alternatif where ttd_marking_operasi_alternatif.no_rawat=?");
                try {
                    ps.setString(1,TNoRw.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("ttd").equals("")||rs.getString("ttd").equals("-")){
                            LoadHTML1.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML1.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+rs.getString("ttd")+"' alt='photo' width='300' height='280'/></center></body></html>");
                        }  
//                        PasswordPasien.setText(rs.getString("password"));
                    }else{
                        LoadHTML1.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
//                        PasswordPasien.setText("");
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
    private void cetak() {
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                kodepetugas=Sequel.cariIsi("select ttd_marking_operasi_alternatif.nik from ttd_marking_operasi_alternatif where ttd_marking_operasi_alternatif.no_rawat=?",TNoRw.getText());
                namapetugas=Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",kodepetugas);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodepetugas);
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+" Ditandatangani secara elektronik oleh petugas "+namapetugas+" dengan ID "+(finger.equals("")?kodepetugas:finger)+" "+Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptOperasiAlternatif.jasper","report","::[ Penandaan Lokasi Operasi Metode Alternatif ]::",
                                "SELECT "+
                                "ttd_marking_operasi_alternatif.*, "+
                                "pasien.nm_pasien, "+
                                "pasien.tgl_lahir, "+
                                "pasien.jk, "+
                                "pasien.no_rkm_medis, "+
                                "pegawai.nama, "+
                                "CASE "+
                                "    WHEN ruang_ok.nm_ruang_ok IS NOT NULL THEN ruang_ok.nm_ruang_ok "+
                                "    WHEN bangsal.nm_bangsal IS NOT NULL THEN bangsal.nm_bangsal "+
                                "    ELSE poliklinik.nm_poli "+
                                "END AS nama_ruangan, "+
                                "marking_operasi_alternatif.url_image "+
                                "FROM ttd_marking_operasi_alternatif "+
                                "INNER JOIN reg_periksa ON reg_periksa.no_rawat = ttd_marking_operasi_alternatif.no_rawat "+
                                "LEFT JOIN kamar_inap ON kamar_inap.no_rawat = reg_periksa.no_rawat  "+
                                "LEFT JOIN pasien ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis "+
                                "LEFT JOIN pegawai ON pegawai.nik = ttd_marking_operasi_alternatif.nik "+
                                "LEFT JOIN ruang_ok ON ruang_ok.kd_ruang_ok = ttd_marking_operasi_alternatif.kd_ruangan "+
                                "LEFT JOIN poliklinik ON poliklinik.kd_poli = ttd_marking_operasi_alternatif.kd_ruangan "+
                                "LEFT JOIN bangsal ON bangsal.kd_bangsal = ttd_marking_operasi_alternatif.kd_ruangan "+
                                "LEFT JOIN kamar ON kamar.kd_kamar = kamar_inap.kd_kamar "+
                                "LEFT JOIN marking_operasi_alternatif ON ttd_marking_operasi_alternatif.no_rawat = marking_operasi_alternatif.no_rawat "+
                                "WHERE ttd_marking_operasi_alternatif.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());  
       }
    }
}
