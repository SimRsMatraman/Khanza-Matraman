/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * kontribusi dari dokter Salim Mulyana
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;



public final class RMSkriningTB extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String tgl,finger="";
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    
    /** Creates new form SuratKeteranganBebasTBC
     * @param parent
     * @param modal */
    public RMSkriningTB(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        pasangListenerSkor();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tanggal","Kode Dokter","Dokter","Status"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(70);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        Jk.setDocument(new batasInput((byte)17).getKata(Jk));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));  
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));     
        
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }   
                KdDok.requestFocus();
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
        
        KontakTB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SkorKontakTB.setText(String.valueOf(KontakTB.getSelectedIndex()));
                SkorMantoux.setText(String.valueOf(Mantoux.getSelectedIndex()));
                SkorGizi.setText(String.valueOf(Gizi.getSelectedIndex()));
                SkorDemam.setText(String.valueOf(Demam.getSelectedIndex()));
                SkorBatuk.setText(String.valueOf(Batuk.getSelectedIndex()));
                SkorKelenjar.setText(String.valueOf(Kelenjar.getSelectedIndex()));
                SkorTulang.setText(String.valueOf(Tulang.getSelectedIndex()));
                SkorFotoToraks.setText(String.valueOf(FotoToraks.getSelectedIndex()));
            }
        });

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
        MnCetakSuratBebasTBC = new javax.swing.JMenuItem();
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
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
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
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        Jk = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TanggalSurat = new widget.Tanggal();
        jLabel8 = new widget.Label();
        KdDok = new widget.TextBox();
        TDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel10 = new widget.Label();
        Stts = new widget.TextBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        FormInput1 = new widget.PanelBiasa();
        jLabel15 = new widget.Label();
        KontakTB = new widget.ComboBox();
        Mantoux = new widget.ComboBox();
        Gizi = new widget.ComboBox();
        Demam = new widget.ComboBox();
        Batuk = new widget.ComboBox();
        Kelenjar = new widget.ComboBox();
        Tulang = new widget.ComboBox();
        FotoToraks = new widget.ComboBox();
        Hasil = new widget.TextBox();
        SkorKontakTB = new widget.TextBox();
        SkorMantoux = new widget.TextBox();
        SkorGizi = new widget.TextBox();
        SkorDemam = new widget.TextBox();
        SkorBatuk = new widget.TextBox();
        SkorKelenjar = new widget.TextBox();
        SkorTulang = new widget.TextBox();
        SkorFotoToraks = new widget.TextBox();
        jLabel16 = new widget.Label();
        TotalSkor = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        FormInput2 = new widget.PanelBiasa();
        jLabel28 = new widget.Label();
        jLabel26 = new widget.Label();
        DBatukMingguan = new widget.ComboBox();
        DBatuk = new widget.ComboBox();
        DDemam = new widget.ComboBox();
        DKeringat = new widget.ComboBox();
        DTurunBB = new widget.ComboBox();
        DBesarKelenjar = new widget.ComboBox();
        DSesak = new widget.ComboBox();
        DObat = new widget.ComboBox();
        DAda = new widget.ComboBox();
        DAsma = new widget.ComboBox();
        DDM = new widget.ComboBox();
        jLabel27 = new widget.Label();
        DBukanTB = new widget.ComboBox();
        DSuspekTB = new widget.ComboBox();
        DTB = new widget.ComboBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratBebasTBC.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratBebasTBC.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratBebasTBC.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratBebasTBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratBebasTBC.setText("Cetak Bebas TBC");
        MnCetakSuratBebasTBC.setName("MnCetakSuratBebasTBC"); // NOI18N
        MnCetakSuratBebasTBC.setPreferredSize(new java.awt.Dimension(190, 26));
        MnCetakSuratBebasTBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratBebasTBCActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratBebasTBC);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Form Skrining TB ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
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

        jLabel19.setText("Tgl. Surat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 126));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setMinimumSize(new java.awt.Dimension(400, 265));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(400, 265));
        FormInput.setLayout(null);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        Jk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JkActionPerformed(evt);
            }
        });
        Jk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JkKeyPressed(evt);
            }
        });
        FormInput.add(Jk);
        Jk.setBounds(700, 10, 110, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(15, 10, 60, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 150, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(335, 10, 360, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(232, 10, 100, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2026" }));
        TanggalSurat.setDisplayFormat("dd-MM-yyyy");
        TanggalSurat.setName("TanggalSurat"); // NOI18N
        TanggalSurat.setOpaque(false);
        TanggalSurat.setPreferredSize(new java.awt.Dimension(141, 18));
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
        TanggalSurat.setBounds(80, 40, 90, 23);

        jLabel8.setText("Dokter :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(180, 40, 70, 23);

        KdDok.setEditable(false);
        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        FormInput.add(KdDok);
        KdDok.setBounds(250, 40, 129, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        TDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDokterKeyPressed(evt);
            }
        });
        FormInput.add(TDokter);
        TDokter.setBounds(380, 40, 274, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("Alt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(660, 40, 28, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(15, 40, 60, 20);

        Stts.setEditable(false);
        Stts.setHighlighter(null);
        Stts.setName("Stts"); // NOI18N
        Stts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SttsActionPerformed(evt);
            }
        });
        Stts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SttsKeyPressed(evt);
            }
        });
        FormInput.add(Stts);
        Stts.setBounds(700, 40, 110, 23);

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(400, 265));

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(400, 265));
        FormInput1.setLayout(null);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Hasil :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput1.add(jLabel15);
        jLabel15.setBounds(210, 230, 40, 20);

        KontakTB.setBorder(javax.swing.BorderFactory.createTitledBorder("Kontak TB"));
        KontakTB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Jelas", "-", "Laporan  keluarga, BTA (-) atau BTA tidak jelas/tidak tahu", "BTA (+)" }));
        KontakTB.setName("KontakTB"); // NOI18N
        KontakTB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontakTBItemStateChanged(evt);
            }
        });
        KontakTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KontakTBActionPerformed(evt);
            }
        });
        KontakTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontakTBKeyPressed(evt);
            }
        });
        FormInput1.add(KontakTB);
        KontakTB.setBounds(30, 30, 280, 40);

        Mantoux.setBorder(javax.swing.BorderFactory.createTitledBorder("Uji Tuberkulin (Mantoux)"));
        Mantoux.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "-", "-", "(+)  (>10mm, atau >5mm pada keadaan imunokompromais" }));
        Mantoux.setName("Mantoux"); // NOI18N
        Mantoux.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MantouxItemStateChanged(evt);
            }
        });
        Mantoux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MantouxActionPerformed(evt);
            }
        });
        Mantoux.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MantouxKeyPressed(evt);
            }
        });
        FormInput1.add(Mantoux);
        Mantoux.setBounds(30, 80, 280, 40);

        Gizi.setBorder(javax.swing.BorderFactory.createTitledBorder("Berat badan/keadaan gizi"));
        Gizi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "BB/TB < 90% atau BB/U <80%", "Klinis gizi buruk atau BB/TB < 70% atau BB/U <60%", "-" }));
        Gizi.setName("Gizi"); // NOI18N
        Gizi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GiziItemStateChanged(evt);
            }
        });
        Gizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GiziActionPerformed(evt);
            }
        });
        Gizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GiziKeyPressed(evt);
            }
        });
        FormInput1.add(Gizi);
        Gizi.setBounds(30, 130, 280, 40);

        Demam.setBorder(javax.swing.BorderFactory.createTitledBorder("Demam yang tidak diketahui penyebabnya"));
        Demam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", ">2 Minggu", "-", "-" }));
        Demam.setName("Demam"); // NOI18N
        Demam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DemamItemStateChanged(evt);
            }
        });
        Demam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DemamActionPerformed(evt);
            }
        });
        Demam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DemamKeyPressed(evt);
            }
        });
        FormInput1.add(Demam);
        Demam.setBounds(30, 180, 280, 40);

        Batuk.setBorder(javax.swing.BorderFactory.createTitledBorder("Batuk kronik"));
        Batuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", ">3 Minggu", "-", "-" }));
        Batuk.setName("Batuk"); // NOI18N
        Batuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BatukItemStateChanged(evt);
            }
        });
        Batuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatukActionPerformed(evt);
            }
        });
        Batuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatukKeyPressed(evt);
            }
        });
        FormInput1.add(Batuk);
        Batuk.setBounds(410, 30, 300, 40);

        Kelenjar.setBorder(javax.swing.BorderFactory.createTitledBorder("Pembesaran kelenjar limfe kolli, aksila, inguinal"));
        Kelenjar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ada Pembengkakan", "-", "-" }));
        Kelenjar.setName("Kelenjar"); // NOI18N
        Kelenjar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KelenjarItemStateChanged(evt);
            }
        });
        Kelenjar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KelenjarActionPerformed(evt);
            }
        });
        Kelenjar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelenjarKeyPressed(evt);
            }
        });
        FormInput1.add(Kelenjar);
        Kelenjar.setBounds(410, 80, 300, 40);

        Tulang.setBorder(javax.swing.BorderFactory.createTitledBorder("Pembengkakan tulang/sendi pinggul, lutut, falang"));
        Tulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", ">1 cm, Lebih dari 1 KGB, tidak nyeri", "-", "-" }));
        Tulang.setName("Tulang"); // NOI18N
        Tulang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TulangItemStateChanged(evt);
            }
        });
        Tulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TulangActionPerformed(evt);
            }
        });
        Tulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TulangKeyPressed(evt);
            }
        });
        FormInput1.add(Tulang);
        Tulang.setBounds(410, 130, 300, 40);

        FotoToraks.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto toraks"));
        FotoToraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal kelainan tidak jelas", "Gambaran sugesrif TB", "-", "-" }));
        FotoToraks.setName("FotoToraks"); // NOI18N
        FotoToraks.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FotoToraksItemStateChanged(evt);
            }
        });
        FotoToraks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FotoToraksActionPerformed(evt);
            }
        });
        FotoToraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FotoToraksKeyPressed(evt);
            }
        });
        FormInput1.add(FotoToraks);
        FotoToraks.setBounds(410, 180, 300, 40);

        Hasil.setEditable(false);
        Hasil.setHighlighter(null);
        Hasil.setName("Hasil"); // NOI18N
        Hasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HasilActionPerformed(evt);
            }
        });
        Hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKeyPressed(evt);
            }
        });
        FormInput1.add(Hasil);
        Hasil.setBounds(250, 230, 140, 23);

        SkorKontakTB.setEditable(false);
        SkorKontakTB.setHighlighter(null);
        SkorKontakTB.setName("SkorKontakTB"); // NOI18N
        SkorKontakTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorKontakTBActionPerformed(evt);
            }
        });
        SkorKontakTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorKontakTBKeyPressed(evt);
            }
        });
        FormInput1.add(SkorKontakTB);
        SkorKontakTB.setBounds(320, 40, 60, 23);

        SkorMantoux.setEditable(false);
        SkorMantoux.setHighlighter(null);
        SkorMantoux.setName("SkorMantoux"); // NOI18N
        SkorMantoux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorMantouxActionPerformed(evt);
            }
        });
        SkorMantoux.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorMantouxKeyPressed(evt);
            }
        });
        FormInput1.add(SkorMantoux);
        SkorMantoux.setBounds(320, 90, 60, 23);

        SkorGizi.setEditable(false);
        SkorGizi.setHighlighter(null);
        SkorGizi.setName("SkorGizi"); // NOI18N
        SkorGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorGiziActionPerformed(evt);
            }
        });
        SkorGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorGiziKeyPressed(evt);
            }
        });
        FormInput1.add(SkorGizi);
        SkorGizi.setBounds(320, 140, 60, 23);

        SkorDemam.setEditable(false);
        SkorDemam.setHighlighter(null);
        SkorDemam.setName("SkorDemam"); // NOI18N
        SkorDemam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorDemamActionPerformed(evt);
            }
        });
        SkorDemam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorDemamKeyPressed(evt);
            }
        });
        FormInput1.add(SkorDemam);
        SkorDemam.setBounds(320, 190, 60, 23);

        SkorBatuk.setEditable(false);
        SkorBatuk.setHighlighter(null);
        SkorBatuk.setName("SkorBatuk"); // NOI18N
        SkorBatuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorBatukActionPerformed(evt);
            }
        });
        SkorBatuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorBatukKeyPressed(evt);
            }
        });
        FormInput1.add(SkorBatuk);
        SkorBatuk.setBounds(720, 40, 60, 23);

        SkorKelenjar.setEditable(false);
        SkorKelenjar.setHighlighter(null);
        SkorKelenjar.setName("SkorKelenjar"); // NOI18N
        SkorKelenjar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorKelenjarActionPerformed(evt);
            }
        });
        SkorKelenjar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorKelenjarKeyPressed(evt);
            }
        });
        FormInput1.add(SkorKelenjar);
        SkorKelenjar.setBounds(720, 90, 60, 23);

        SkorTulang.setEditable(false);
        SkorTulang.setHighlighter(null);
        SkorTulang.setName("SkorTulang"); // NOI18N
        SkorTulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorTulangActionPerformed(evt);
            }
        });
        SkorTulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorTulangKeyPressed(evt);
            }
        });
        FormInput1.add(SkorTulang);
        SkorTulang.setBounds(720, 140, 60, 23);

        SkorFotoToraks.setEditable(false);
        SkorFotoToraks.setHighlighter(null);
        SkorFotoToraks.setName("SkorFotoToraks"); // NOI18N
        SkorFotoToraks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkorFotoToraksActionPerformed(evt);
            }
        });
        SkorFotoToraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkorFotoToraksKeyPressed(evt);
            }
        });
        FormInput1.add(SkorFotoToraks);
        SkorFotoToraks.setBounds(720, 190, 60, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Skor");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput1.add(jLabel16);
        jLabel16.setBounds(730, 10, 40, 20);

        TotalSkor.setEditable(false);
        TotalSkor.setHighlighter(null);
        TotalSkor.setName("TotalSkor"); // NOI18N
        TotalSkor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalSkorActionPerformed(evt);
            }
        });
        TotalSkor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalSkorKeyPressed(evt);
            }
        });
        FormInput1.add(TotalSkor);
        TotalSkor.setBounds(90, 230, 80, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Total Skor :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput1.add(jLabel17);
        jLabel17.setBounds(20, 230, 60, 20);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Parameter");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput1.add(jLabel18);
        jLabel18.setBounds(410, 10, 70, 20);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Parameter");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput1.add(jLabel20);
        jLabel20.setBounds(30, 10, 70, 20);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Skor");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput1.add(jLabel22);
        jLabel22.setBounds(330, 10, 40, 20);

        jLabel11.setText("Keterangan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput1.add(jLabel11);
        jLabel11.setBounds(10, 260, 70, 20);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("1.   Anak dinyatakan positif TB bila skor mencapai nilai 6 atau lebih");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput1.add(jLabel12);
        jLabel12.setBounds(30, 280, 340, 20);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("2.   Bila anak ada riwayat denganpenderita TB dewasa yang aktif, uji tuberkulin positif,tapi tidak didapatkan gejala,");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput1.add(jLabel13);
        jLabel13.setBounds(30, 300, 650, 20);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("maka akan cukup mendapat profilaksis INH, terutama anak usia <5 tahun.");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput1.add(jLabel14);
        jLabel14.setBounds(50, 320, 640, 20);

        jTabbedPane1.addTab("Anak", FormInput1);

        FormInput2.setName("FormInput2"); // NOI18N
        FormInput2.setPreferredSize(new java.awt.Dimension(430, 305));
        FormInput2.setLayout(null);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Parameter");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput2.add(jLabel28);
        jLabel28.setBounds(30, 10, 70, 20);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Parameter");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput2.add(jLabel26);
        jLabel26.setBounds(410, 10, 70, 20);

        DBatukMingguan.setBorder(javax.swing.BorderFactory.createTitledBorder("Batuk Berdahak selama > 2-3 minggu"));
        DBatukMingguan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DBatukMingguan.setName("DBatukMingguan"); // NOI18N
        DBatukMingguan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DBatukMingguanItemStateChanged(evt);
            }
        });
        DBatukMingguan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBatukMingguanActionPerformed(evt);
            }
        });
        DBatukMingguan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DBatukMingguanKeyPressed(evt);
            }
        });
        FormInput2.add(DBatukMingguan);
        DBatukMingguan.setBounds(30, 30, 340, 40);

        DBatuk.setBorder(javax.swing.BorderFactory.createTitledBorder("Batuk berdarah"));
        DBatuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DBatuk.setName("DBatuk"); // NOI18N
        DBatuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DBatukItemStateChanged(evt);
            }
        });
        DBatuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBatukActionPerformed(evt);
            }
        });
        DBatuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DBatukKeyPressed(evt);
            }
        });
        FormInput2.add(DBatuk);
        DBatuk.setBounds(30, 80, 340, 40);

        DDemam.setBorder(javax.swing.BorderFactory.createTitledBorder("Demam hilang timbul > 1 bulan"));
        DDemam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DDemam.setName("DDemam"); // NOI18N
        DDemam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DDemamItemStateChanged(evt);
            }
        });
        DDemam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDemamActionPerformed(evt);
            }
        });
        DDemam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DDemamKeyPressed(evt);
            }
        });
        FormInput2.add(DDemam);
        DDemam.setBounds(30, 130, 340, 40);

        DKeringat.setBorder(javax.swing.BorderFactory.createTitledBorder("Keringat malam tanpa aktivitas"));
        DKeringat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DKeringat.setName("DKeringat"); // NOI18N
        DKeringat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DKeringatItemStateChanged(evt);
            }
        });
        DKeringat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DKeringatActionPerformed(evt);
            }
        });
        DKeringat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DKeringatKeyPressed(evt);
            }
        });
        FormInput2.add(DKeringat);
        DKeringat.setBounds(30, 180, 340, 40);

        DTurunBB.setBorder(javax.swing.BorderFactory.createTitledBorder("Penurunan berat badan tanpa penyebab yang jelas"));
        DTurunBB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DTurunBB.setName("DTurunBB"); // NOI18N
        DTurunBB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTurunBBItemStateChanged(evt);
            }
        });
        DTurunBB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTurunBBActionPerformed(evt);
            }
        });
        DTurunBB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTurunBBKeyPressed(evt);
            }
        });
        FormInput2.add(DTurunBB);
        DTurunBB.setBounds(30, 230, 340, 40);

        DBesarKelenjar.setBorder(javax.swing.BorderFactory.createTitledBorder("Pembesaran kelenjar getah bening (benjolan di daerah leher) dengan ukuran >2 cm"));
        DBesarKelenjar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DBesarKelenjar.setName("DBesarKelenjar"); // NOI18N
        DBesarKelenjar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DBesarKelenjarItemStateChanged(evt);
            }
        });
        DBesarKelenjar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBesarKelenjarActionPerformed(evt);
            }
        });
        DBesarKelenjar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DBesarKelenjarKeyPressed(evt);
            }
        });
        FormInput2.add(DBesarKelenjar);
        DBesarKelenjar.setBounds(410, 30, 470, 40);

        DSesak.setBorder(javax.swing.BorderFactory.createTitledBorder("Sesak nafas dan nyeri dada"));
        DSesak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DSesak.setName("DSesak"); // NOI18N
        DSesak.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DSesakItemStateChanged(evt);
            }
        });
        DSesak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DSesakActionPerformed(evt);
            }
        });
        DSesak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DSesakKeyPressed(evt);
            }
        });
        FormInput2.add(DSesak);
        DSesak.setBounds(410, 80, 470, 40);

        DObat.setBorder(javax.swing.BorderFactory.createTitledBorder("Pernah minum obat paru dalam waktu lama sebelumnya"));
        DObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DObat.setName("DObat"); // NOI18N
        DObat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DObatItemStateChanged(evt);
            }
        });
        DObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DObatActionPerformed(evt);
            }
        });
        DObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DObatKeyPressed(evt);
            }
        });
        FormInput2.add(DObat);
        DObat.setBounds(410, 130, 470, 40);

        DAda.setBorder(javax.swing.BorderFactory.createTitledBorder("Ada keluarga/tetangga yang pernah sakit paru-paru/TB/pengobatan paru lama"));
        DAda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DAda.setName("DAda"); // NOI18N
        DAda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DAdaItemStateChanged(evt);
            }
        });
        DAda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAdaActionPerformed(evt);
            }
        });
        DAda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DAdaKeyPressed(evt);
            }
        });
        FormInput2.add(DAda);
        DAda.setBounds(410, 180, 470, 40);

        DAsma.setBorder(javax.swing.BorderFactory.createTitledBorder("Penyakit lain - Asma"));
        DAsma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DAsma.setName("DAsma"); // NOI18N
        DAsma.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DAsmaItemStateChanged(evt);
            }
        });
        DAsma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAsmaActionPerformed(evt);
            }
        });
        DAsma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DAsmaKeyPressed(evt);
            }
        });
        FormInput2.add(DAsma);
        DAsma.setBounds(410, 230, 230, 40);

        DDM.setBorder(javax.swing.BorderFactory.createTitledBorder("Penyakit lain - DM"));
        DDM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DDM.setName("DDM"); // NOI18N
        DDM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DDMItemStateChanged(evt);
            }
        });
        DDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDMActionPerformed(evt);
            }
        });
        DDM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DDMKeyPressed(evt);
            }
        });
        FormInput2.add(DDM);
        DDM.setBounds(650, 230, 230, 40);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kesimpulan");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput2.add(jLabel27);
        jLabel27.setBounds(30, 290, 70, 20);

        DBukanTB.setBorder(javax.swing.BorderFactory.createTitledBorder("Bukan TB"));
        DBukanTB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DBukanTB.setName("DBukanTB"); // NOI18N
        DBukanTB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DBukanTBItemStateChanged(evt);
            }
        });
        DBukanTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBukanTBActionPerformed(evt);
            }
        });
        DBukanTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DBukanTBKeyPressed(evt);
            }
        });
        FormInput2.add(DBukanTB);
        DBukanTB.setBounds(30, 310, 280, 40);

        DSuspekTB.setBorder(javax.swing.BorderFactory.createTitledBorder("Suspek TB"));
        DSuspekTB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DSuspekTB.setName("DSuspekTB"); // NOI18N
        DSuspekTB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DSuspekTBItemStateChanged(evt);
            }
        });
        DSuspekTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DSuspekTBActionPerformed(evt);
            }
        });
        DSuspekTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DSuspekTBKeyPressed(evt);
            }
        });
        FormInput2.add(DSuspekTB);
        DSuspekTB.setBounds(320, 310, 270, 40);

        DTB.setBorder(javax.swing.BorderFactory.createTitledBorder("TB"));
        DTB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        DTB.setName("DTB"); // NOI18N
        DTB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTBItemStateChanged(evt);
            }
        });
        DTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTBActionPerformed(evt);
            }
        });
        DTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTBKeyPressed(evt);
            }
        });
        FormInput2.add(DTB);
        DTB.setBounds(600, 310, 280, 40);

        jTabbedPane1.addTab("Dewasa", FormInput2);

        FormInput.add(jTabbedPane1);
        jTabbedPane1.setBounds(20, 80, 990, 400);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void JkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JkKeyPressed

}//GEN-LAST:event_JkKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Jk);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if (jTabbedPane1.getSelectedIndex() == 0) {
            SimpanAnak(evt);
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            SimpanDewasa(evt);
        } 
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void SimpanAnak(java.awt.event.ActionEvent evt) {                                          
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");    
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"Dokter yang memeriksa");
        }else{
            if(Sequel.menyimpantf("skrining_tb_anak","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat",22,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),KdDok.getText(),Stts.getText(),
                    KontakTB.getSelectedItem()+"",
                    SkorKontakTB.getText(),
                    Mantoux.getSelectedItem()+"",
                    SkorMantoux.getText(),
                    Gizi.getSelectedItem()+"",
                    SkorGizi.getText(),
                    Demam.getSelectedItem()+"",
                    SkorDemam.getText(),
                    Batuk.getSelectedItem()+"",
                    SkorBatuk.getText(),
                    Kelenjar.getSelectedItem()+"",
                    SkorKelenjar.getText(),
                    Tulang.getSelectedItem()+"",
                    SkorTulang.getText(),
                    FotoToraks.getSelectedItem()+"",
                    SkorFotoToraks.getText(),
                    TotalSkor.getText(),
                    Hasil.getText()
                })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),
                    TNoRM.getText(),
                    TPasien.getText(),
                    Valid.SetTgl(TanggalSurat.getSelectedItem()+""),
                    KdDok.getText(),
                    TDokter.getText(),
                    Stts.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
    }
    
    private void SimpanDewasa(java.awt.event.ActionEvent evt) {                                          
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");    
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"Dokter yang memeriksa");
        }else{
            if(Sequel.menyimpantf("skrining_tb_dewasa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat",18,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),KdDok.getText(),Stts.getText(),
                    DBatukMingguan.getSelectedItem()+"",
                    DBatuk.getSelectedItem()+"",
                    DDemam.getSelectedItem()+"",
                    DKeringat.getSelectedItem()+"",
                    DTurunBB.getSelectedItem()+"",
                    DBesarKelenjar.getSelectedItem()+"",
                    DSesak.getSelectedItem()+"",
                    DObat.getSelectedItem()+"",
                    DAda.getSelectedItem()+"",
                    DAsma.getSelectedItem()+"",
                    DDM.getSelectedItem()+"",
                    DBukanTB.getSelectedItem()+"",
                    DSuspekTB.getSelectedItem()+"",
                    DTB.getSelectedItem()+""
                })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),
                    TNoRM.getText(),
                    TPasien.getText(),
                    Valid.SetTgl(TanggalSurat.getSelectedItem()+""),
                    KdDok.getText(),
                    TDokter.getText(),
                    Stts.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
    }
    
    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TanggalSurat,BtnBatal);
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
        if (jTabbedPane1.getSelectedIndex() == 0) {
            if(Valid.hapusTabletf(tabMode,TNoRw,"skrining_tb_anak","no_rawat")==true){
                if(tbObat.getSelectedRow()!= -1){
                    tabMode.removeRow(tbObat.getSelectedRow());
                    emptTeks();
                    LCount.setText(""+tabMode.getRowCount());
                }
            }
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            if(Valid.hapusTabletf(tabMode,TNoRw,"skrining_tb_dewasa","no_rawat")==true){
                if(tbObat.getSelectedRow()!= -1){
                    tabMode.removeRow(tbObat.getSelectedRow());
                    emptTeks();
                    LCount.setText(""+tabMode.getRowCount());
                }
            }
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
        if (jTabbedPane1.getSelectedIndex() == 0) {
            GantiAnak(evt);
        } else if (jTabbedPane1.getSelectedIndex() == 1) {
            GantiDewasa(evt);
        } 
}//GEN-LAST:event_BtnEditActionPerformed

        private void GantiAnak(java.awt.event.ActionEvent evt) {                                        
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");    
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"Dokter yang memeriksa");
        }else{  
            if(tbObat.getSelectedRow()!= -1){
                if(Sequel.mengedittf("skrining_tb_anak","no_rawat=? and tanggal=?",
                    "no_rawat=?,tanggal=?,kd_dokter=?,stts=?,kontaktb=?,skorkontaktb=?,mantoux=?,skormantoux=?,berat=?,skorberat=?,demam=?,skordemam=?,"+
                    "batuk=?,skorbatuk=?,kelenjar=?,skorkelenjar=?,tulang=?,skortulang=?,fototoraks=?,skorfototoraks=?,total=?,hasil=?",
                    24,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),KdDok.getText(),Stts.getText(),
                    KontakTB.getSelectedItem()+"",
                    SkorKontakTB.getText(),
                    Mantoux.getSelectedItem()+"",
                    SkorMantoux.getText(),
                    Gizi.getSelectedItem()+"",
                    SkorGizi.getText(),
                    Demam.getSelectedItem()+"",
                    SkorDemam.getText(),
                    Batuk.getSelectedItem()+"",
                    SkorBatuk.getText(),
                    Kelenjar.getSelectedItem()+"",
                    SkorKelenjar.getText(),
                    Tulang.getSelectedItem()+"",
                    SkorTulang.getText(),
                    FotoToraks.getSelectedItem()+"",
                    SkorFotoToraks.getText(),
                    TotalSkor.getText(),
                    Hasil.getText(),
                    TNoRw.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()
                })==true){
                    tabMode.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 0);
                    tabMode.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 1);
                    tabMode.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 2);
                    tabMode.setValueAt(Valid.SetTgl(TanggalSurat.getSelectedItem()+""), tbObat.getSelectedRow(), 3);
                    tabMode.setValueAt(KdDok.getText(), tbObat.getSelectedRow(), 4);
                    tabMode.setValueAt(TDokter.getText(), tbObat.getSelectedRow(), 5);
                    tabMode.setValueAt(Stts.getText(), tbObat.getSelectedRow(), 6);

                    LCount.setText(""+tabMode.getRowCount());
                    emptTeks();
                }
            }
        }
    }                                       

        private void GantiDewasa(java.awt.event.ActionEvent evt) {                                        
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");    
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"Dokter yang memeriksa");
        }else{  
            if(tbObat.getSelectedRow()!= -1){
                if(Sequel.mengedittf("skrining_tb_dewasa","no_rawat=? and tanggal=?",
                    "no_rawat=?,tanggal=?,kd_dokter=?,stts=?,batukminggu=?,batuk=?,demam=?,keringat=?,turunbb=?,besarkelenjar=?,sesak=?,obat=?,ada=?,"+
                    "asma=?,dm=?,bukantb=?,suspektb=?,tb=?",
                    20,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),KdDok.getText(),Stts.getText(),
                    DBatukMingguan.getSelectedItem()+"",
                    DBatuk.getSelectedItem()+"",
                    DDemam.getSelectedItem()+"",
                    DKeringat.getSelectedItem()+"",
                    DTurunBB.getSelectedItem()+"",
                    DBesarKelenjar.getSelectedItem()+"",
                    DSesak.getSelectedItem()+"",
                    DObat.getSelectedItem()+"",
                    DAda.getSelectedItem()+"",
                    DAsma.getSelectedItem()+"",
                    DDM.getSelectedItem()+"",
                    DBukanTB.getSelectedItem()+"",
                    DSuspekTB.getSelectedItem()+"",
                    DTB.getSelectedItem()+"",
                    TNoRw.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()
                })==true){
                    tabMode.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 0);
                    tabMode.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 1);
                    tabMode.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 2);
                    tabMode.setValueAt(Valid.SetTgl(TanggalSurat.getSelectedItem()+""), tbObat.getSelectedRow(), 3);
                    tabMode.setValueAt(KdDok.getText(), tbObat.getSelectedRow(), 4);
                    tabMode.setValueAt(TDokter.getText(), tbObat.getSelectedRow(), 5);
                    tabMode.setValueAt(Stts.getText(), tbObat.getSelectedRow(), 6);

                    LCount.setText(""+tabMode.getRowCount());
                    emptTeks();
                }
            }
        }
    }
        
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
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            tgl=" surat_bebas_tbc.tanggalsurat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataSuratBebasTBC.jasper","report","::[ Data Surat Bebas TBC ]::",
                     "select surat_bebas_tbc.no_surat,surat_bebas_tbc.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                     "surat_bebas_tbc.tanggalsurat,surat_bebas_tbc.kd_dokter,dokter.nm_dokter,surat_bebas_tbc.keperluan "+                   
                     "from surat_bebas_tbc inner join reg_periksa on surat_bebas_tbc.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join dokter on surat_bebas_tbc.kd_dokter=dokter.kd_dokter "+
                     "where "+tgl+"order by surat_bebas_tbc.no_surat",param);
            }else{
                Valid.MyReportqry("rptDataSuratBebasTBC.jasper","report","::[ Data Surat Bebas TBC ]::",
                     "select surat_bebas_tbc.no_surat,surat_bebas_tbc.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                     "surat_bebas_tbc.tanggalsurat,surat_bebas_tbc.kd_dokter,dokter.nm_dokter,surat_bebas_tbc.keperluan "+                   
                     "from surat_bebas_tbc inner join reg_periksa on surat_bebas_tbc.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join dokter on surat_bebas_tbc.kd_dokter=dokter.kd_dokter "+
                     "where "+tgl+"and no_surat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_bebas_tbc.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_bebas_tbc.tanggalsurat like '%"+TCari.getText().trim()+"%' "+
                     "order by surat_bebas_tbc.no_surat",param);
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void MnCetakSuratBebasTBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratBebasTBCActionPerformed
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
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDok.getText());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+TDokter.getText()+"\nID "+(finger.equals("")?KdDok.getText():finger)+"\n"+TanggalSurat.getSelectedItem());  
                Valid.MyReportqry("rptSuratKeteranganBebasTBC.jasper","report","::[ Surat Keterangan Bebas TBC ]::",
                              " select surat_bebas_tbc.no_surat,DATE_FORMAT(surat_bebas_tbc.tanggalsurat,'%d-%m-%Y')as tanggalsurat,surat_bebas_tbc.keperluan,dokter.nm_dokter,pasien.jk," +
                              " pasien.nm_pasien,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y')as tgl_lahir,pasien.tmp_lahir,pasien.pekerjaan,dokter.kd_dokter,"+
                              " concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                              " from surat_bebas_tbc inner join reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel and "+
                              " pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rawat=surat_bebas_tbc.no_rawat "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());  
       }
    }//GEN-LAST:event_MnCetakSuratBebasTBCActionPerformed

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed

    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void JkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JkActionPerformed

    private void TDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDokterKeyPressed
        //Valid.pindah(evt,TKd,TSpek);
    }//GEN-LAST:event_TDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed

    }//GEN-LAST:event_btnDokterKeyPressed

    private void FotoToraksItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FotoToraksItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            
        }
    }//GEN-LAST:event_FotoToraksItemStateChanged

    private void FotoToraksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FotoToraksActionPerformed
        int poin = FotoToraks.getSelectedIndex();
        SkorFotoToraks.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_FotoToraksActionPerformed

    private void FotoToraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FotoToraksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FotoToraksKeyPressed

    private void KontakTBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontakTBItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakTBItemStateChanged

    private void KontakTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KontakTBActionPerformed
        int poin = KontakTB.getSelectedIndex();
        SkorKontakTB.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_KontakTBActionPerformed

    private void KontakTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontakTBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakTBKeyPressed

    private void MantouxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MantouxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_MantouxItemStateChanged

    private void MantouxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MantouxActionPerformed
        int poin = Mantoux.getSelectedIndex();
        SkorMantoux.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_MantouxActionPerformed

    private void MantouxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MantouxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MantouxKeyPressed

    private void GiziItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GiziItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_GiziItemStateChanged

    private void GiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GiziActionPerformed
        int poin =  Gizi.getSelectedIndex();
        SkorGizi.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_GiziActionPerformed

    private void GiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GiziKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GiziKeyPressed

    private void DemamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DemamItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DemamItemStateChanged

    private void DemamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DemamActionPerformed
        int poin = Demam.getSelectedIndex();
        SkorDemam.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_DemamActionPerformed

    private void DemamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DemamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DemamKeyPressed

    private void BatukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BatukItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_BatukItemStateChanged

    private void BatukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatukActionPerformed
        int poin = Batuk.getSelectedIndex();
        SkorBatuk.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_BatukActionPerformed

    private void BatukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BatukKeyPressed

    private void KelenjarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KelenjarItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_KelenjarItemStateChanged

    private void KelenjarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KelenjarActionPerformed
        int poin = Kelenjar.getSelectedIndex();
        SkorKelenjar.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_KelenjarActionPerformed

    private void KelenjarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelenjarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelenjarKeyPressed

    private void TulangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TulangItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TulangItemStateChanged

    private void TulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TulangActionPerformed
        int poin = Tulang.getSelectedIndex();
        SkorTulang.setText(String.valueOf(poin));
        hitungTotalDanHasil();
    }//GEN-LAST:event_TulangActionPerformed

    private void TulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TulangKeyPressed

    private void HasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HasilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilActionPerformed

    private void HasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilKeyPressed

    private void SkorKontakTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorKontakTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorKontakTBActionPerformed

    private void SkorKontakTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorKontakTBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorKontakTBKeyPressed

    private void SkorMantouxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorMantouxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorMantouxActionPerformed

    private void SkorMantouxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorMantouxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorMantouxKeyPressed

    private void SkorGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorGiziActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorGiziActionPerformed

    private void SkorGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorGiziKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorGiziKeyPressed

    private void SkorDemamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorDemamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorDemamActionPerformed

    private void SkorDemamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorDemamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorDemamKeyPressed

    private void SkorBatukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorBatukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorBatukActionPerformed

    private void SkorBatukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorBatukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorBatukKeyPressed

    private void SkorKelenjarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorKelenjarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorKelenjarActionPerformed

    private void SkorKelenjarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorKelenjarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorKelenjarKeyPressed

    private void SkorTulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorTulangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorTulangActionPerformed

    private void SkorTulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorTulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorTulangKeyPressed

    private void SkorFotoToraksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkorFotoToraksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorFotoToraksActionPerformed

    private void SkorFotoToraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkorFotoToraksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkorFotoToraksKeyPressed

    private void TotalSkorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalSkorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalSkorActionPerformed

    private void TotalSkorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalSkorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalSkorKeyPressed

    private void DBatukMingguanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DBatukMingguanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DBatukMingguanItemStateChanged

    private void DBatukMingguanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBatukMingguanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBatukMingguanActionPerformed

    private void DBatukMingguanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DBatukMingguanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBatukMingguanKeyPressed

    private void DBatukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DBatukItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DBatukItemStateChanged

    private void DBatukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBatukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBatukActionPerformed

    private void DBatukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DBatukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBatukKeyPressed

    private void DDemamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DDemamItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DDemamItemStateChanged

    private void DDemamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDemamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DDemamActionPerformed

    private void DDemamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DDemamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DDemamKeyPressed

    private void DTBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTBItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DTBItemStateChanged

    private void DTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTBActionPerformed

    private void DTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTBKeyPressed

    private void DBesarKelenjarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DBesarKelenjarItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DBesarKelenjarItemStateChanged

    private void DBesarKelenjarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBesarKelenjarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBesarKelenjarActionPerformed

    private void DBesarKelenjarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DBesarKelenjarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBesarKelenjarKeyPressed

    private void DSesakItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DSesakItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DSesakItemStateChanged

    private void DSesakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DSesakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DSesakActionPerformed

    private void DSesakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DSesakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DSesakKeyPressed

    private void DObatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DObatItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DObatItemStateChanged

    private void DObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DObatActionPerformed

    private void DObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DObatKeyPressed

    private void DDMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DDMItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DDMItemStateChanged

    private void DDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DDMActionPerformed

    private void DDMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DDMKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DDMKeyPressed

    private void SttsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SttsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SttsActionPerformed

    private void SttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SttsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SttsKeyPressed

    private void DKeringatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DKeringatItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DKeringatItemStateChanged

    private void DKeringatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DKeringatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DKeringatActionPerformed

    private void DKeringatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DKeringatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DKeringatKeyPressed

    private void DAdaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DAdaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DAdaItemStateChanged

    private void DAdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DAdaActionPerformed

    private void DAdaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DAdaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DAdaKeyPressed

    private void DAsmaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DAsmaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DAsmaItemStateChanged

    private void DAsmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAsmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DAsmaActionPerformed

    private void DAsmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DAsmaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DAsmaKeyPressed

    private void DTurunBBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTurunBBItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DTurunBBItemStateChanged

    private void DTurunBBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTurunBBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTurunBBActionPerformed

    private void DTurunBBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTurunBBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTurunBBKeyPressed

    private void DBukanTBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DBukanTBItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DBukanTBItemStateChanged

    private void DBukanTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBukanTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBukanTBActionPerformed

    private void DBukanTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DBukanTBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DBukanTBKeyPressed

    private void DSuspekTBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DSuspekTBItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DSuspekTBItemStateChanged

    private void DSuspekTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DSuspekTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DSuspekTBActionPerformed

    private void DSuspekTBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DSuspekTBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DSuspekTBKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningTB dialog = new RMSkriningTB(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Batuk;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.ComboBox DAda;
    private widget.ComboBox DAsma;
    private widget.ComboBox DBatuk;
    private widget.ComboBox DBatukMingguan;
    private widget.ComboBox DBesarKelenjar;
    private widget.ComboBox DBukanTB;
    private widget.ComboBox DDM;
    private widget.ComboBox DDemam;
    private widget.ComboBox DKeringat;
    private widget.ComboBox DObat;
    private widget.ComboBox DSesak;
    private widget.ComboBox DSuspekTB;
    private widget.ComboBox DTB;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DTurunBB;
    private widget.ComboBox Demam;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.PanelBiasa FormInput2;
    private widget.ComboBox FotoToraks;
    private widget.ComboBox Gizi;
    private widget.TextBox Hasil;
    private widget.TextBox Jk;
    private widget.TextBox KdDok;
    private widget.ComboBox Kelenjar;
    private widget.ComboBox KontakTB;
    private widget.Label LCount;
    private widget.ComboBox Mantoux;
    private javax.swing.JMenuItem MnCetakSuratBebasTBC;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox SkorBatuk;
    private widget.TextBox SkorDemam;
    private widget.TextBox SkorFotoToraks;
    private widget.TextBox SkorGizi;
    private widget.TextBox SkorKelenjar;
    private widget.TextBox SkorKontakTB;
    private widget.TextBox SkorMantoux;
    private widget.TextBox SkorTulang;
    private widget.TextBox Stts;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.TextBox TotalSkor;
    private widget.ComboBox Tulang;
    private widget.Button btnDokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" a.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "SELECT " +
                    "a.*, " +
                    "c.no_rkm_medis, " +
                    "b.nama, " +
                    "d.nm_pasien, " +
                    "c.umurdaftar " +
                    "FROM v_skrining_tb a " +
                    "INNER JOIN pegawai b ON a.kd_dokter=b.nik " +
                    "INNER JOIN reg_periksa c ON a.no_rawat=c.no_rawat " +
                    "INNER JOIN pasien d ON c.no_rkm_medis=d.no_rkm_medis " +
                    "WHERE "+tgl+"ORDER BY a.no_rawat");
            }else{
                ps=koneksi.prepareStatement(
                    "SELECT " +
                    "a.*, " +
                    "c.no_rkm_medis, " +
                    "b.nama, " +
                    "d.nm_pasien, " +
                    "c.umurdaftar " +
                    "FROM v_skrining_tb a " +
                    "INNER JOIN pegawai b ON a.kd_dokter=b.nik " +
                    "INNER JOIN reg_periksa c ON a.no_rawat=c.no_rawat " +
                    "INNER JOIN pasien d ON c.no_rkm_medis=d.no_rkm_medis " +
                    "WHERE "+tgl+"and a.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and b.nama like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and c.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and d.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                     "order by a.tanggal");
            }
                
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString(2),rs.getString(24),rs.getString(26)+"("+rs.getString(27)+" Th)",
                        rs.getString(3),rs.getString(4),rs.getString(25),
                        rs.getString(5)
                        
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
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        Jk.setText("");
        TanggalSurat.setDate(new Date());
        SkorKontakTB.setText("0");
        SkorMantoux.setText("0");
        SkorGizi.setText("0");
        SkorDemam.setText("0");
        SkorBatuk.setText("0");
        SkorKelenjar.setText("0");
        SkorTulang.setText("0");
        SkorFotoToraks.setText("0");
        KontakTB.setSelectedIndex(0);
        Mantoux.setSelectedIndex(0);
        Gizi.setSelectedIndex(0);
        Demam.setSelectedIndex(0);
        Batuk.setSelectedIndex(0);
        Kelenjar.setSelectedIndex(0);
        Tulang.setSelectedIndex(0);
        FotoToraks.setSelectedIndex(0);
        
        hitungTotalDanHasil();
        
        DBatukMingguan.setSelectedIndex(0);
        DBatuk.setSelectedIndex(0);
        DDemam.setSelectedIndex(0);
        DKeringat.setSelectedIndex(0);
        DTurunBB.setSelectedIndex(0);
        DBesarKelenjar.setSelectedIndex(0);
        DSesak.setSelectedIndex(0);
        DObat.setSelectedIndex(0);
        DAda.setSelectedIndex(0);
        DAsma.setSelectedIndex(0);
        DDM.setSelectedIndex(0);
        DBukanTB.setSelectedIndex(0);
        DSuspekTB.setSelectedIndex(0);
        DTB.setSelectedIndex(0);
    }

    private void hitungTotalDanHasil() {
        try {
            int total =
                    Integer.parseInt(SkorKontakTB.getText().trim()) +
                    Integer.parseInt(SkorMantoux.getText().trim()) +
                    Integer.parseInt(SkorGizi.getText().trim()) +
                    Integer.parseInt(SkorDemam.getText().trim()) +
                    Integer.parseInt(SkorBatuk.getText().trim()) +
                    Integer.parseInt(SkorKelenjar.getText().trim()) +
                    Integer.parseInt(SkorTulang.getText().trim()) +
                    Integer.parseInt(SkorFotoToraks.getText().trim());

            TotalSkor.setText(String.valueOf(total));
            Hasil.setText(total >= 6 ? "Positif TB" : "Negatif TB");
        } catch (Exception e) {
            TotalSkor.setText("0");
            Hasil.setText("-");
        }
    }
    
    private void pasangListenerSkor() {
        javax.swing.event.DocumentListener dl = new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                hitungTotalDanHasil();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                hitungTotalDanHasil();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                hitungTotalDanHasil();
            }
        };

        SkorKontakTB.getDocument().addDocumentListener(dl);
        SkorMantoux.getDocument().addDocumentListener(dl);
        SkorGizi.getDocument().addDocumentListener(dl);
        SkorDemam.getDocument().addDocumentListener(dl);
        SkorBatuk.getDocument().addDocumentListener(dl);
        SkorKelenjar.getDocument().addDocumentListener(dl);
        SkorTulang.getDocument().addDocumentListener(dl);
        SkorFotoToraks.getDocument().addDocumentListener(dl);
    }
 
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            KdDok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Stts.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            
            Sequel.cariIsi(
                "select case when pasien.jk='L' then 'Laki-laki' when pasien.jk='P' then 'Perempuan' else '' end " +
                "from pasien where pasien.no_rkm_medis='" + TNoRM.getText() + "'",
                Jk
            );
            
            ambilDataAnak();
            ambilDataDewasa();
            
            if ("Anak".equalsIgnoreCase(Stts.getText().trim())) {
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1.setEnabledAt(0, true);
                jTabbedPane1.setEnabledAt(1, false);
            } else {
                jTabbedPane1.setSelectedIndex(1);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, true);
            }
        }
    }
    
    private void ambilDataAnak() {
    String sql = "select * from skrining_tb_anak where no_rawat=? and tanggal=? and stts=?";

    try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
        String tgl = Valid.SetTgl(TanggalSurat.getSelectedItem() + "");
        ps.setString(1, TNoRw.getText());
        ps.setString(2, tgl);
        ps.setString(3, Stts.getText());

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                KontakTB.setSelectedItem(rs.getString("kontaktb"));
                SkorKontakTB.setText(rs.getString("skorkontaktb"));
                Mantoux.setSelectedItem(rs.getString("mantoux"));
                SkorMantoux.setText(rs.getString("skormantoux"));
                Gizi.setSelectedItem(rs.getString("berat"));
                SkorGizi.setText(rs.getString("skorberat"));
                Demam.setSelectedItem(rs.getString("demam"));
                SkorDemam.setText(rs.getString("skordemam"));
                Batuk.setSelectedItem(rs.getString("batuk"));
                SkorBatuk.setText(rs.getString("skorbatuk"));
                Kelenjar.setSelectedItem(rs.getString("kelenjar"));
                SkorKelenjar.setText(rs.getString("skorkelenjar"));
                Tulang.setSelectedItem(rs.getString("tulang"));
                SkorTulang.setText(rs.getString("skortulang"));
                FotoToraks.setSelectedItem(rs.getString("fototoraks"));
                SkorFotoToraks.setText(rs.getString("skorfototoraks"));
                TotalSkor.setText(rs.getString("total"));
                Hasil.setText(rs.getString("hasil"));
            }
        }
    } catch (Exception e) {
        System.out.println("Notif : " + e);
    }
}
    
private void ambilDataDewasa() {
    String sql = "select * from skrining_tb_dewasa where no_rawat=? and tanggal=? and stts=?";

    try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
        String tgl = Valid.SetTgl(TanggalSurat.getSelectedItem() + "");
        ps.setString(1, TNoRw.getText());
        ps.setString(2, tgl);
        ps.setString(3, Stts.getText());

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                DBatukMingguan.setSelectedItem(rs.getString("batukminggu"));
                DBatuk.setSelectedItem(rs.getString("batuk"));
                DDemam.setSelectedItem(rs.getString("demam"));
                DKeringat.setSelectedItem(rs.getString("keringat"));
                DTurunBB.setSelectedItem(rs.getString("turunbb"));
                DBesarKelenjar.setSelectedItem(rs.getString("besarkelenjar"));
                DSesak.setSelectedItem(rs.getString("sesak"));
                DObat.setSelectedItem(rs.getString("obat"));
                DAda.setSelectedItem(rs.getString("ada"));
                DAsma.setSelectedItem(rs.getString("asma"));
                DDM.setSelectedItem(rs.getString("dm"));
                DBukanTB.setSelectedItem(rs.getString("bukantb"));
                DSuspekTB.setSelectedItem(rs.getString("suspektb"));
                DTB.setSelectedItem(rs.getString("tb"));
            }
        }
    } catch (Exception e) {
        System.out.println("Notif : " + e);
    }
}
    
private void setComboByText(javax.swing.JComboBox<String> combo, String text) {
    for (int i = 0; i < combo.getItemCount(); i++) {
        if (String.valueOf(combo.getItemAt(i)).equalsIgnoreCase(text)) {
            combo.setSelectedIndex(i);
            return;
        }
    }
}    

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt,String norm,String namapasien,String kodedokter,String namadokter,Date tgl1, Date tgl2, String ad) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        TNoRM.setText(norm);
        TPasien.setText(namapasien);
        KdDok.setText(kodedokter);
        TDokter.setText(namadokter);
        ChkInput.setSelected(true);
        Stts.setText(ad);
        
        if ("Anak".equalsIgnoreCase(ad)) {
            jTabbedPane1.setSelectedIndex(0);
            jTabbedPane1.setEnabledAt(1, false);
            jTabbedPane1.setEnabledAt(0, true);
        } else if ("Dewasa".equalsIgnoreCase(ad)) {
            jTabbedPane1.setSelectedIndex(1);
            jTabbedPane1.setEnabledAt(0, false);
            jTabbedPane1.setEnabledAt(1, true);
        }
        
        Sequel.cariIsi(
            "select case when pasien.jk='L' then 'Laki-laki' when pasien.jk='P' then 'Perempuan' else '' end " +
            "from pasien where pasien.no_rkm_medis='" + TNoRM.getText() + "'",
            Jk
        );
        isForm();
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,526));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
       
    
    public void isCek(){
//        BtnSimpan.setEnabled(akses.getsurat_bebas_tbc());
//        BtnHapus.setEnabled(akses.getsurat_bebas_tbc());
//        BtnEdit.setEnabled(akses.getsurat_bebas_tbc());
    }
}



