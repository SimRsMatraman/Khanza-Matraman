package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import grafikanalisa.grafikpembelianterbanyak;
import grafikanalisa.grafikpembeliantersedikit;
import grafikanalisa.grafikpenjualanterbanyak;
import grafikanalisa.grafikpenjualantersedikit;
import grafikanalisa.grafikpiutangterbanyak;
import grafikanalisa.grafikpiutangtersedikit;
import grafikanalisa.grafikresepterbanyak;
import grafikanalisa.grafikreseptersedikit;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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
import simrskhanza.DlgCariBangsal;
import fungsi.WarnaTableLPLPO;
import fungsi.WarnaTable;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class DlgSirkulasiBarang7 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);
    private String lokasi="";
    private double ttltotaljual=0,totaljual=0,jumlahjual=0,ttltotalbeli=0,totalbeli=0,jumlahbeli=0,jumlahbeli1=0,jumlahbeli2=0,jumlahbeli3=0,totalbeli1=0,totalbeli2=0,totalbeli3=0,
                   ttltotalpesan=0,totalpesan=0,jumlahpesan=0,jumlahutd,totalutd,ttltotalutd,jumlahkeluar,totalkeluar,ttltotalkeluar,
                   ttltotalpiutang=0,totalpiutang=0,jumlahpiutang=0,ttltotalretbeli=0,totalretbeli=0,jumlahretbeli=0,
                   ttltotalretjual=0,totalretjual=0,jumlahretjual=0,ttltotalretpiut=0,totalretpiut=0,jumlahretpiut=0,
                   jumlahpasin=0,totalpasien=0,ttltotalpasien=0,stok=0,aset=0,ttlaset=0,jumlahrespulang=0,totalrespulang=0,
                   ttltotalrespulang=0,jumlahmutasimasuk=0,jumlahmutasikeluar=0,totalmutasimasuk=0,totalmutasikeluar=0,
                   ttltotalmutasimasuk=0,ttltotalmutasikeluar=0,jumlahhibah=0,totalhibah=0,ttltotalhibah=0;
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String qrystok="",aktifkanbatch="no",hppfarmasi="";
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);

    /** 
     * @param parent
     * @param modal */
    public DlgSirkulasiBarang7(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Barang","Nama Barang","Satuan","Stok Terakhir","Rawat Jalan","Harga Rawat Jalan","IGD","Harga IGD","Rawat Inap","Harga Rawat Inap","Total Jual","Harga Total Jual"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(110);
            }
        }
        
        try {
            if(koneksiDB.AKTIFKANWARNARALAN().equals("yes")){
                tbDokter.setDefaultRenderer(Object.class, new WarnaTableLPLPO());
            }else{
                tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
            }
        } catch (Exception e) {
            tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        }         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        }   
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (bangsal.getTable().getSelectedRow()> -1) {
                    lokasi=bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString();
                    prosesCari2(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
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
                bangsal.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
     
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            aktifkanbatch = "no";
        }
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
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
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
                
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        try {
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
        }
    }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu = new javax.swing.JPopupMenu();
        MnCetak = new javax.swing.JMenuItem();
        ppGrafikbeliBanyak = new javax.swing.JMenuItem();
        ppGrafikbelidikit = new javax.swing.JMenuItem();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        nmjns = new widget.TextBox();
        label20 = new widget.Label();
        BtnGolongan = new widget.Button();
        BtnJenis = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        btnAmbil = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu.setName("jPopupMenu"); // NOI18N

        MnCetak.setBackground(new java.awt.Color(255, 255, 254));
        MnCetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetak.setForeground(java.awt.Color.darkGray);
        MnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetak.setText("Cetak");
        MnCetak.setName("MnCetak"); // NOI18N
        MnCetak.setPreferredSize(new java.awt.Dimension(150, 28));
        MnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakActionPerformed(evt);
            }
        });
        jPopupMenu.add(MnCetak);

        ppGrafikbeliBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikbeliBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbeliBanyak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikbeliBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbeliBanyak.setText("Grafik 10 Barang Pembelian Terbanyak");
        ppGrafikbeliBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbeliBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbeliBanyak.setName("ppGrafikbeliBanyak"); // NOI18N
        ppGrafikbeliBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbeliBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbeliBanyakActionPerformed(evt);
            }
        });
        jPopupMenu.add(ppGrafikbeliBanyak);

        ppGrafikbelidikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikbelidikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbelidikit.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikbelidikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbelidikit.setText("Grafik 10 Barang Pembelian Tersedikit");
        ppGrafikbelidikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbelidikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbelidikit.setName("ppGrafikbelidikit"); // NOI18N
        ppGrafikbelidikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbelidikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbelidikitActionPerformed(evt);
            }
        });
        jPopupMenu.add(ppGrafikbelidikit);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(60, 23));

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(150, 23));

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(60, 23));

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(150, 23));

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(150, 23));

        label20.setText("Jenis :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(40, 23));

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.setRequestFocusEnabled(false);
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.setRequestFocusEnabled(false);
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Lapporan Pemakaian Dan Lembar Permintaan Obat (LPLPO) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label11.setText("Tgl.Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(label11);
        label11.setBounds(0, 10, 90, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(Tgl1);
        Tgl1.setBounds(95, 10, 110, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);
        label18.setBounds(216, 10, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(Tgl2);
        Tgl2.setBounds(257, 10, 110, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

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
        panelisi1.add(BtnCari);

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(39, 30));
        panelisi1.add(label9);

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        panelisi1.add(btnAmbil);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelisi1.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelisi1.add(LCount);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        lokasi="";
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());

        if(tabMode == null){
            JOptionPane.showMessageDialog(null,"Table model belum diinisialisasi!");
            return;
        }

        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,
                "Maaf, data sudah habis. Tidak ada data yang bisa anda tampilkan...!!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'"+i+"',?,?,?,?,?,?,?,?,?,?,?,?,'','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'",12,new String[]{
                    String.valueOf(tabMode.getValueAt(i,0)),
                    String.valueOf(tabMode.getValueAt(i,1)),
                    String.valueOf(tabMode.getValueAt(i,2)),
                    String.valueOf(tabMode.getValueAt(i,3)),
                    String.valueOf(tabMode.getValueAt(i,4)),
                    String.valueOf(tabMode.getValueAt(i,5)),
                    String.valueOf(tabMode.getValueAt(i,6)),
                    String.valueOf(tabMode.getValueAt(i,7)),
                    String.valueOf(tabMode.getValueAt(i,8)),
                    String.valueOf(tabMode.getValueAt(i,9)),
                    String.valueOf(tabMode.getValueAt(i,10)),
                    String.valueOf(tabMode.getValueAt(i,11))
                }); 
            }
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      
    }//GEN-LAST:event_formWindowOpened

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void ppGrafikbeliBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbeliBanyakActionPerformed
        grafikpembelianterbanyak grafik=new grafikpembelianterbanyak("Grafik 10 Barang Pembelian Terbanyak"," pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
            "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
        grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        grafik.setLocationRelativeTo(internalFrame1);
        grafik.setAlwaysOnTop(false);
        grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikbeliBanyakActionPerformed

    private void ppGrafikbelidikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbelidikitActionPerformed
        grafikpembeliantersedikit grafik=new grafikpembeliantersedikit("Grafik 10 Barang Pembelian Tersedikit"," pembelian.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
            "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ");
        grafik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        grafik.setLocationRelativeTo(internalFrame1);
        grafik.setAlwaysOnTop(false);
        grafik.setVisible(true);
    }//GEN-LAST:event_ppGrafikbelidikitActionPerformed

    private void MnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLPLPO.jasper","report","::[ Lapporan Pemakaian Dan Lembar Permintaan Obat (LPLPO) ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
        }
    }//GEN-LAST:event_MnCetakActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLPLPO.jasper","report","::[ Lapporan Pemakaian Dan Lembar Permintaan Obat (LPLPO) ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
             if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("obat/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&alamat="+akses.getalamatip()+"&tanggal="+akses.getalamatip());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAmbilActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBarang dialog = new DlgSirkulasiBarang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnGolongan;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetak;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JPopupMenu jPopupMenu;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label20;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppGrafikbeliBanyak;
    private javax.swing.JMenuItem ppGrafikbelidikit;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            ps=koneksi.prepareStatement(
                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,kodesatuan.satuan "+
                    "from detail_pemberian_obat "+
                    "inner join databarang on databarang.kode_brng=detail_pemberian_obat.kode_brng "+
                    "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "where  detail_pemberian_obat.tgl_perawatan between ? and ? "+
                    "group by detail_pemberian_obat.kode_brng");
            try {
                ttltotaljual=0;ttltotalbeli=0;ttltotalpesan=0;
                ttltotalpiutang=0;ttltotalretbeli=0;ttltotalretjual=0;
                ttltotalretpiut=0;ttltotalpasien=0;ttlaset=0;
                ttltotalutd=0;ttltotalkeluar=0;ttltotalrespulang=0;
                ttltotalmutasikeluar=0;ttltotalmutasimasuk=0;
                ttltotalhibah=0;
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rs=ps.executeQuery(); 
                
                if(aktifkanbatch.equals("yes")){
                    qrystok="select sum(gudangbarang.stok),(sum(gudangbarang.stok)*databarang."+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>''";
                }else{
                    qrystok="select sum(gudangbarang.stok),(sum(gudangbarang.stok)*databarang."+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''";
                }
                while(rs.next()){
                    totaljual=0;jumlahjual=0;totalbeli=0;jumlahbeli=0;totalpiutang=0;jumlahpiutang=0;
                    totalpesan=0;jumlahpesan=0;jumlahrespulang=0;jumlahhibah=0;totalhibah=0;
                    totalretbeli=0;jumlahretbeli=0;totalretjual=0;jumlahretjual=0;totalretpiut=0;jumlahretpiut=0;
                    jumlahpasin=0;stok=0;aset=0;totalrespulang=0;
                    jumlahutd=0;jumlahkeluar=0;totalkeluar=0;totalutd=0;
                    jumlahmutasikeluar=0;totalmutasikeluar=0;jumlahmutasimasuk=0;totalmutasimasuk=0;

                    ps2=koneksi.prepareStatement(qrystok);
                    try {
                        ps2.setString(1,rs.getString(1));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stok=rs2.getDouble(1);
                            aset=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //Rajal  
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat "+
                        "inner join reg_periksa on reg_periksa.no_rawat=detail_pemberian_obat.no_rawat "+
                        "where detail_pemberian_obat.kode_brng=? and detail_pemberian_obat.status='Ralan' and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ? and not reg_periksa.kd_poli='IGDK'");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli=rs2.getDouble(1);
                            totalbeli=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }          
                    
                    //IGD
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat "+
                        "inner join reg_periksa on reg_periksa.no_rawat=detail_pemberian_obat.no_rawat "+
                        "where detail_pemberian_obat.kode_brng=? and reg_periksa.kd_poli='IGDK' and detail_pemberian_obat.status='Ralan' and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli1=rs2.getDouble(1);
                            totalbeli1=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //Ranap
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and  detail_pemberian_obat.status='Ranap' and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli2=rs2.getDouble(1);
                            totalbeli2=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //Total  
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli3=rs2.getDouble(1);
                            totalbeli3=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    if((aset>0)||(jumlahbeli>0)||(jumlahbeli1>0)||(jumlahbeli1>0)||(jumlahbeli3>0)||(totalbeli>0)||(totalbeli1>0)||(totalbeli2>0)||(totalbeli3>0)){
                        tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),
                                   rs.getString(3),Valid.SetAngka(stok),
                                   Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
                                   Valid.SetAngka(jumlahbeli1),Valid.SetAngka(totalbeli1),
                                   Valid.SetAngka(jumlahbeli2),Valid.SetAngka(totalbeli2),
                                   Valid.SetAngka(jumlahbeli3),Valid.SetAngka(totalbeli3)
                                   }); 
                        ttlaset=ttlaset+aset;
                        ttltotalbeli=ttltotalbeli+jumlahbeli;
                        ttltotalpesan=ttltotalpesan+totalbeli;
                        ttltotaljual=ttltotaljual+jumlahbeli1;
                        ttltotalpasien=ttltotalpasien+totalbeli1;
                        ttltotalpiutang=ttltotalpiutang+jumlahbeli2;
                        ttltotalretbeli=ttltotalretbeli+totalbeli2;
                        ttltotalretjual=ttltotalretjual+jumlahbeli3;
                        ttltotalretpiut=ttltotalretpiut+totalbeli3;
                    }

                        
                }   
                tabMode.addRow(new Object[]{"","","","","","","","","","",""}); 
                tabMode.addRow(new Object[]{
                    "<>>","Total :","",Valid.SetAngka(ttlaset),
                    Valid.SetAngka(ttltotalbeli),Valid.SetAngka(ttltotalpesan),
                    Valid.SetAngka(ttltotaljual),Valid.SetAngka(ttltotalpasien),
                    Valid.SetAngka(ttltotalpiutang),Valid.SetAngka(ttltotalretbeli),
                    Valid.SetAngka(ttltotalretjual),Valid.SetAngka(ttltotalretpiut)
                }); 
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : "+e);
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
    
    private void prosesCari2(String lokasi) {
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       Valid.tabelKosong(tabMode);     
       try{   
            ps=koneksi.prepareStatement(
                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,kodesatuan.satuan "+
                    "from detail_pemberian_obat "+
                    "inner join databarang on databarang.kode_brng=detail_pemberian_obat.kode_brng "+
                    "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    "where  detail_pemberian_obat.tgl_perawatan between ? and ? "+
                    "group by detail_pemberian_obat.kode_brng");
            try {
                ttltotaljual=0;ttltotalbeli=0;ttltotalpesan=0;
                ttltotalpiutang=0;ttltotalretbeli=0;ttltotalretjual=0;
                ttltotalretpiut=0;ttltotalpasien=0;ttlaset=0;
                ttltotalutd=0;ttltotalkeluar=0;ttltotalrespulang=0;
                ttltotalmutasikeluar=0;ttltotalmutasimasuk=0;
                ttltotalhibah=0;
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rs=ps.executeQuery();  
                
                if(aktifkanbatch.equals("yes")){
                    qrystok="select sum(gudangbarang.stok),(sum(gudangbarang.stok)*databarang."+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>''";
                }else{
                    qrystok="select sum(gudangbarang.stok),(sum(gudangbarang.stok)*databarang."+hppfarmasi+") as aset "+
                            "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                            "where gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''";
                }
                while(rs.next()){
                    totaljual=0;jumlahjual=0;totalbeli=0;jumlahbeli=0;totalpiutang=0;jumlahpiutang=0;
                    totalpesan=0;jumlahpesan=0;jumlahrespulang=0;jumlahhibah=0;totalhibah=0;
                    totalretbeli=0;jumlahretbeli=0;totalretjual=0;jumlahretjual=0;totalretpiut=0;jumlahretpiut=0;
                    jumlahpasin=0;stok=0;aset=0;totalrespulang=0;
                    jumlahutd=0;jumlahkeluar=0;totalkeluar=0;totalutd=0;
                    jumlahmutasikeluar=0;totalmutasikeluar=0;jumlahmutasimasuk=0;totalmutasimasuk=0;

                    ps2=koneksi.prepareStatement(qrystok);
                    try {
                        ps2.setString(1,rs.getString(1));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stok=rs2.getDouble(1);
                            aset=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //Rajal  
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat "+
                        "inner join reg_periksa on reg_periksa.no_rawat=detail_pemberian_obat.no_rawat "+
                        "where detail_pemberian_obat.kode_brng=? and detail_pemberian_obat.status='Ralan' and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ? and not reg_periksa.kd_poli='U0026'");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli=rs2.getDouble(1);
                            totalbeli=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }          
                    
                    //IGD
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat "+
                        "inner join reg_periksa on reg_periksa.no_rawat=detail_pemberian_obat.no_rawat "+
                        "where detail_pemberian_obat.kode_brng=? and reg_periksa.kd_poli='U0026' and detail_pemberian_obat.status='Ralan' and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli1=rs2.getDouble(1);
                            totalbeli1=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //Ranap
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and  detail_pemberian_obat.status='Ranap' and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli2=rs2.getDouble(1);
                            totalbeli2=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //Total  
                    ps2=koneksi.prepareStatement(
                        "select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){ 
                            
                            jumlahbeli3=rs2.getDouble(1);
                            totalbeli3=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    if((aset>0)||(jumlahbeli>0)||(jumlahbeli1>0)||(jumlahbeli1>0)||(jumlahbeli3>0)||(totalbeli>0)||(totalbeli1>0)||(totalbeli2>0)||(totalbeli3>0)){
                        tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),
                                   rs.getString(3),Valid.SetAngka(stok),
                                   Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
                                   Valid.SetAngka(jumlahbeli1),Valid.SetAngka(totalbeli1),
                                   Valid.SetAngka(jumlahbeli2),Valid.SetAngka(totalbeli2),
                                   Valid.SetAngka(jumlahbeli3),Valid.SetAngka(totalbeli3)
                                   }); 
                        ttlaset=ttlaset+aset;
                        ttltotalbeli=ttltotalbeli+jumlahbeli;
                        ttltotalpesan=ttltotalpesan+totalbeli;
                        ttltotaljual=ttltotaljual+jumlahbeli1;
                        ttltotalpasien=ttltotalpasien+totalbeli1;
                        ttltotalpiutang=ttltotalpiutang+jumlahbeli2;
                        ttltotalretbeli=ttltotalretbeli+totalbeli2;
                        ttltotalretjual=ttltotalretjual+jumlahbeli3;
                        ttltotalretpiut=ttltotalretpiut+totalbeli3;
                    }

                        
                }   
                tabMode.addRow(new Object[]{"","","","","","","","","","",""}); 
                tabMode.addRow(new Object[]{
                    "<>>","Total :","",Valid.SetAngka(ttlaset),
                    Valid.SetAngka(ttltotalbeli),Valid.SetAngka(ttltotalpesan),
                    Valid.SetAngka(ttltotaljual),Valid.SetAngka(ttltotalpasien),
                    Valid.SetAngka(ttltotalpiutang),Valid.SetAngka(ttltotalretbeli),
                    Valid.SetAngka(ttltotalretjual),Valid.SetAngka(ttltotalretpiut)
                }); 
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : "+e);
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
    
    public void isCek(){
//         BtnPrint.setEnabled(akses.getsirkulasi_obat());
    }
    
//    public void setTampil(){
//       tabMode.setSelectedIndex(0);
//    }
    
}
