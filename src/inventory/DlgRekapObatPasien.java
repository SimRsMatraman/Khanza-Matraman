package inventory;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.batasInput;
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
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariCaraBayar;
import simrskhanza.DlgCariPoli;

public class DlgRekapObatPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psreg,pskamar;
    private ResultSet rsobat,rsreg,rskamar; 
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariBangsal asalstok=new DlgCariBangsal(null,false);
    private int i=0,a=0;
    private double jmlbiaya=0,ttlbiaya=0,jmlembalase=0,ttlembalase=0,jmltuslah=0,ttltuslah=0,jmltotal=0,ttltotal=0;
    private DlgCariPoli poli = new DlgCariPoli(null, false);
//    private widget.TextBox kdpoli; // kalau kamu ingin simpan kode poli juga

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRekapObatPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
    "No.","Nama Pasien","No.RM","No.Rawat","Dokter Peresep","Aturan Pakai",
    "Nama Obat","Jenis Obat","Cara Bayar","Kategori","Asal Stok","Golongan"
}) {
    @Override public boolean isCellEditable(int rowIndex, int colIndex){ return false; }
};
tbDokter.setModel(tabMode);

tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

for (i = 0; i < 12; i++) {
    TableColumn column = tbDokter.getColumnModel().getColumn(i);
    switch(i){
        case 0: column.setPreferredWidth(35); break;   // No
        case 1: column.setPreferredWidth(170); break;  // Nama Pasien
        case 2: column.setPreferredWidth(70); break;   // No RM
        case 3: column.setPreferredWidth(110); break;  // No Rawat
        case 4: column.setPreferredWidth(160); break;  // Dokter
        case 5: column.setPreferredWidth(160); break;  // Aturan
        case 6: column.setPreferredWidth(230); break;  // Nama Obat
        case 7: column.setPreferredWidth(120); break;  // Jenis
        case 8: column.setPreferredWidth(120); break;  // Cara Bayar
        case 9: column.setPreferredWidth(140); break;  // Kategori
        case 10: column.setPreferredWidth(140); break; // Asal Stok
        case 11: column.setPreferredWidth(140); break; // Golongan
    }
}
tbDokter.setDefaultRenderer(Object.class, new WarnaTable());  
        
tabMode2 = new DefaultTableModel(null, new Object[]{
    "No.","Nama Pasien","No.RM","No.Rawat",
    "Lama Rawat (Hari)",          // ⬅️ kolom baru
    "Dokter Peresep","Aturan Pakai",
    "Nama Obat","Jenis Obat","Cara Bayar",
    "Kategori","Asal Stok","Golongan"
}) {
    @Override public boolean isCellEditable(int rowIndex, int colIndex){ return false; }
};
tbDokter2.setModel(tabMode2);

tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

for (i = 0; i < 13; i++) {
    TableColumn column = tbDokter2.getColumnModel().getColumn(i);
    switch(i){
        case 0: column.setPreferredWidth(35); break;
        case 1: column.setPreferredWidth(170); break;
        case 2: column.setPreferredWidth(70); break;
        case 3: column.setPreferredWidth(110); break;
        case 4: column.setPreferredWidth(90); break;   // Lama Rawat
        case 5: column.setPreferredWidth(160); break;
        case 6: column.setPreferredWidth(160); break;
        case 7: column.setPreferredWidth(230); break;
        case 8: column.setPreferredWidth(120); break;
        case 9: column.setPreferredWidth(120); break;
        case 10: column.setPreferredWidth(140); break;
        case 11: column.setPreferredWidth(140); break;
        case 12: column.setPreferredWidth(140); break;
    }
}
tbDokter2.setDefaultRenderer(Object.class, new WarnaTable()); 
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        asalstok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(asalstok.getTable().getSelectedRow()!= -1){                   
                    kdasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),0).toString());                    
                    nmasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),1).toString());
                }  
                kdasal.requestFocus();
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
        
        asalstok.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    asalstok.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
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
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
    poli.addWindowListener(new WindowListener() {
    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosing(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {
        if(poli.getTable().getSelectedRow()!= -1){
            // asumsi kolom poli: 0=kd_poli, 1=nm_poli (umum di Khanza)
            // kalau beda, tinggal geser index-nya
            // kalau kamu tidak pakai kdpoli, skip saja
            // kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
            nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
        }
        BtnSeek2.requestFocus();
    }
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) { poli.emptTeks(); }
    @Override public void windowDeactivated(WindowEvent e) {}
});

poli.getTable().addKeyListener(new KeyListener() {
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            poli.dispose();
        }
    }
    @Override public void keyReleased(KeyEvent e) {}
});

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
if (koneksiDB.CARICEPAT().equals("aktif")) {
    TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override public void insertUpdate(DocumentEvent e) { cariCepat(); }
        @Override public void removeUpdate(DocumentEvent e) { cariCepat(); }
        @Override public void changedUpdate(DocumentEvent e) { cariCepat(); }

        private void cariCepat() {
            if (TCari.getText().trim().length() > 2) {
                if (TabRawat1.getSelectedIndex() == 0) {
                    prosesCari();
                } else {
                    prosesCari2();
                }
            }
        }
    });
}
        
        ChkInput.setSelected(false);
        isForm();
     
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        kdasal = new widget.TextBox();
        kdpenjab = new widget.TextBox();
        kdjenis = new widget.TextBox();
        kdkategori = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        labelTotalHari = new widget.Label();
        TotalHariRanap = new widget.TextBox();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        label19 = new widget.Label();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label20 = new widget.Label();
        nmasal = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        status = new widget.ComboBox();
        nmpoli = new widget.TextBox();
        label24 = new widget.Label();
        BtnSeek2 = new widget.Button();
        TabRawat1 = new javax.swing.JTabbedPane();
        scrollPane4 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        kdasal.setEditable(false);
        kdasal.setName("kdasal"); // NOI18N
        kdasal.setPreferredSize(new java.awt.Dimension(75, 23));

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(60, 23));

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(75, 23));

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(75, 23));

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(75, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Penggunaan Obat Per Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi1.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(135, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi1.add(BtnAll);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(jLabel7);

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

        labelTotalHari.setText("Total Hari Ranap :");
        labelTotalHari.setName("labelTotalHari"); // NOI18N
        labelTotalHari.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi1.add(labelTotalHari);

        TotalHariRanap.setEditable(false);
        TotalHariRanap.setToolTipText("Alt+C");
        TotalHariRanap.setName("TotalHariRanap"); // NOI18N
        TotalHariRanap.setPreferredSize(new java.awt.Dimension(70, 23));
        TotalHariRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHariRanapKeyPressed(evt);
            }
        });
        panelisi1.add(TotalHariRanap);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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
        FormInput.setPreferredSize(new java.awt.Dimension(150, 100));
        FormInput.setLayout(null);

        label17.setText("Status :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(37, 23));
        FormInput.add(label17);
        label17.setBounds(10, 10, 43, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label19);
        label19.setBounds(265, 10, 65, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(168, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(333, 10, 150, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(486, 10, 28, 23);

        label20.setText("Asal Stok :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(538, 10, 60, 23);

        nmasal.setEditable(false);
        nmasal.setName("nmasal"); // NOI18N
        nmasal.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmasal);
        nmasal.setBounds(601, 10, 150, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(754, 10, 28, 23);

        label21.setText("Jenis :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(label21);
        label21.setBounds(10, 40, 43, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmjns);
        nmjns.setBounds(56, 40, 150, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        FormInput.add(BtnJenis);
        BtnJenis.setBounds(209, 40, 28, 23);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(265, 40, 65, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(333, 40, 150, 23);

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
        FormInput.add(BtnKategori);
        BtnKategori.setBounds(486, 40, 28, 23);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(538, 40, 60, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(601, 40, 150, 23);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        FormInput.add(BtnGolongan);
        BtnGolongan.setBounds(754, 40, 28, 23);

        status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Status", "Obat Rawat Jalan", "Obat Rawat Inap" }));
        status.setName("status"); // NOI18N
        FormInput.add(status);
        status.setBounds(56, 10, 181, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(168, 23));
        FormInput.add(nmpoli);
        nmpoli.setBounds(60, 70, 150, 23);

        label24.setText("Poli :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(37, 23));
        FormInput.add(label24);
        label24.setBounds(10, 70, 43, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(210, 70, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat1.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat1.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat1.setName("TabRawat1"); // NOI18N
        TabRawat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawat1MouseClicked(evt);
            }
        });

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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
        scrollPane4.setViewportView(tbDokter);

        TabRawat1.addTab("Rawat Jalan", scrollPane4);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbDokter2.setAutoCreateRowSorter(true);
        tbDokter2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter2.setName("tbDokter2"); // NOI18N
        scrollPane5.setViewportView(tbDokter2);

        TabRawat1.addTab("Rawat Inap", scrollPane5);

        internalFrame1.add(TabRawat1, java.awt.BorderLayout.CENTER);
        TabRawat1.getAccessibleContext().setAccessibleName("Rawat Jalan");

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat1.getSelectedIndex()==0){
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                int row=tabMode.getRowCount();
for(int r=0;r<tabMode.getRowCount();r++){
    String[] temp = new String[]{
        tabMode.getValueAt(r,1).toString(),  // temp1  Nama Pasien
        tabMode.getValueAt(r,2).toString(),  // temp2  No.RM
        tabMode.getValueAt(r,3).toString(),  // temp3  No.Rawat
        "-",                                 // temp4  Lama Rawat (ralan kosong)
        tabMode.getValueAt(r,4).toString(),  // temp5  Dokter
        tabMode.getValueAt(r,5).toString(),  // temp6  Aturan
        tabMode.getValueAt(r,6).toString(),  // temp7  Nama Obat
        tabMode.getValueAt(r,7).toString(),  // temp8  Jenis
        tabMode.getValueAt(r,8).toString(),  // temp9  Cara Bayar
        tabMode.getValueAt(r,9).toString(),  // temp10 Kategori
        tabMode.getValueAt(r,10).toString(), // temp11 Asal Stok
        tabMode.getValueAt(r,11).toString()  // temp12 Golongan
    };

    simpanTemporary(r, temp, akses.getalamatip(), "Rekap Obat Pasien Ralan");
}

                Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptRekapObatPasien_Ralan.jasper","report","[ Rekap Penggunaan Obat Per Pasien ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat1.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode2.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                int row=tabMode2.getRowCount();
for(int r=0;r<tabMode2.getRowCount();r++){
    String[] temp = new String[]{
        tabMode2.getValueAt(r,1).toString(),  // temp1  Nama Pasien
        tabMode2.getValueAt(r,2).toString(),  // temp2  No.RM
        tabMode2.getValueAt(r,3).toString(),  // temp3  No.Rawat
        tabMode2.getValueAt(r,4).toString(),  // temp4  Lama Rawat
        tabMode2.getValueAt(r,5).toString(),  // temp5  Dokter
        tabMode2.getValueAt(r,6).toString(),  // temp6  Aturan
        tabMode2.getValueAt(r,7).toString(),  // temp7  Nama Obat
        tabMode2.getValueAt(r,8).toString(),  // temp8  Jenis (kamu isi "-")
        tabMode2.getValueAt(r,9).toString(),  // temp9  Cara Bayar
        tabMode2.getValueAt(r,10).toString(), // temp10 Kategori
        tabMode2.getValueAt(r,11).toString(), // temp11 Asal Stok
        tabMode2.getValueAt(r,12).toString()  // temp12 Golongan
    };

    simpanTemporary(r, temp, akses.getalamatip(), "Rekap Obat Pasien Ranap");
}

                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptRekapObatPasien_Ranap.jasper","report","[ Rekap Penggunaan Obat Per Pasien ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
            
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpenjab.setText("");
        nmpenjab.setText("");
        kdasal.setText("");
        nmasal.setText("");
        kdjenis.setText("");
        nmjns.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");
        TCari.setText("");
        nmpoli.setText("");
        status.setSelectedIndex(0);
        setFooterTotalHariRanap(0);
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
            
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, status, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,status);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        asalstok.isCek();
        asalstok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        asalstok.setLocationRelativeTo(internalFrame1);
        asalstok.setAlwaysOnTop(false);
        asalstok.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void TabRawat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawat1MouseClicked
        if(TabRawat1.getSelectedIndex()==0){
           prosesCari();
        }else if(TabRawat1.getSelectedIndex()==1){
           prosesCari2();
        }
    }//GEN-LAST:event_TabRawat1MouseClicked

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            if(TabRawat1.getSelectedIndex()==0){
                tbDokter.requestFocus();
            }else{
                tbDokter2.requestFocus();
            }   
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void TotalHariRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHariRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHariRanapKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRekapObatPasien dialog = new DlgRekapObatPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnGolongan;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat1;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox TotalHariRanap;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private widget.TextBox kdasal;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.TextBox kdpenjab;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label9;
    private widget.Label labelTotalHari;
    private widget.TextBox nmasal;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ComboBox status;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

private void prosesCari() {
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        Valid.tabelKosong(tabMode);

        // =========================
        // QUERY REG (TANPA TCari)
        // =========================
        psreg = koneksi.prepareStatement(
            "select reg_periksa.no_rawat " +
            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
            "left join penjab on reg_periksa.kd_pj=penjab.kd_pj " +
            "where reg_periksa.stts<>'Batal' " +
            "and reg_periksa.tgl_registrasi between ? and ? " +
            "and reg_periksa.status_lanjut like ? " +
            "and concat(reg_periksa.kd_pj,ifnull(penjab.png_jawab,'')) like ? " +
            "and reg_periksa.kd_poli like ? " +
            "order by reg_periksa.tgl_registrasi"
        );

        psreg.setString(1, Valid.SetTgl(Tgl1.getSelectedItem()+""));
        psreg.setString(2, Valid.SetTgl(Tgl2.getSelectedItem()+""));
        psreg.setString(3, "%"+status.getSelectedItem().toString()
                .replaceAll("Obat Rawat Jalan","Ralan")
                .replaceAll("Obat Rawat Inap","Ranap")
                .replaceAll("Semua Status","")+"%");
        psreg.setString(4, "%"+kdpenjab.getText()+nmpenjab.getText()+"%");
        psreg.setString(5, getKdPoliFilter());

        rsreg = psreg.executeQuery();
        i = 1;

        // =========================
        // LOOP PER NO_RAWAT
        // =========================
        while (rsreg.next()) {

String sqlObat =
"select " +
" reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, " +
" ifnull(dokter.nm_dokter,'-') as nm_dokter, " +
" ifnull(aplast.aturan,'') as aturan_pakai, " +
" databarang.nama_brng, ifnull(jenis.nama,'-') as jenis_obat, " +
" ifnull(penjab.png_jawab,'-') as cara_bayar, " +
" ifnull(kategori_barang.nama,'-') as kategori, " +
" ifnull(bangsal.nm_bangsal,'-') as asal_stok, " +
" ifnull(golongan_barang.nama,'-') as golongan " +

"from detail_pemberian_obat " +
"inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat " +
"inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
"left join penjab on reg_periksa.kd_pj=penjab.kd_pj " +
"left join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng " +
"left join jenis on databarang.kdjns=jenis.kdjns " +

"left join ( " +
"   select ap1.no_rawat, ap1.kode_brng, ap1.aturan " +
"   from aturan_pakai ap1 " +
"   inner join ( " +
"       select no_rawat, kode_brng, max(timestamp(tgl_perawatan,jam)) as tmax " +
"       from aturan_pakai where no_rawat=? group by no_rawat, kode_brng " +
"   ) ap2 on ap2.no_rawat=ap1.no_rawat and ap2.kode_brng=ap1.kode_brng " +
"        and timestamp(ap1.tgl_perawatan,ap1.jam)=ap2.tmax " +
") aplast on aplast.no_rawat=detail_pemberian_obat.no_rawat " +
"        and aplast.kode_brng=detail_pemberian_obat.kode_brng " +

"left join kategori_barang on kategori_barang.kode=databarang.kode_kategori " +
"left join golongan_barang on golongan_barang.kode=databarang.kode_golongan " +
"left join bangsal on detail_pemberian_obat.kd_bangsal=bangsal.kd_bangsal " +

"left join ( " +
"   select ro.no_rawat, ro.kd_dokter " +
"   from resep_obat ro where ro.no_rawat=? " +
"   order by ro.tgl_perawatan desc, ro.jam desc limit 1 " +
") rlast on rlast.no_rawat=reg_periksa.no_rawat " +

"left join dokter on dokter.kd_dokter=rlast.kd_dokter " +

"where detail_pemberian_obat.no_rawat=? " +
"and detail_pemberian_obat.status like ? " +
"and concat(databarang.kdjns,ifnull(jenis.nama,'')) like ? " +
"and concat(databarang.kode_kategori,ifnull(kategori_barang.nama,'')) like ? " +
"and concat(databarang.kode_golongan,ifnull(golongan_barang.nama,'')) like ? " +
"and concat(detail_pemberian_obat.kd_bangsal,ifnull(bangsal.nm_bangsal,'')) like ? " +

"and ( pasien.nm_pasien like ? or reg_periksa.no_rkm_medis like ? or reg_periksa.no_rawat like ? " +
"or dokter.nm_dokter like ? or aplast.aturan like ? or databarang.nama_brng like ? " +
"or jenis.nama like ? or penjab.png_jawab like ? or kategori_barang.nama like ? " +
"or bangsal.nm_bangsal like ? or golongan_barang.nama like ? ) " +

"group by detail_pemberian_obat.kode_brng, aplast.aturan " +
"order by pasien.nm_pasien, databarang.nama_brng";

psobat = koneksi.prepareStatement(sqlObat);

psobat.setString(1, rsreg.getString("no_rawat"));
psobat.setString(2, rsreg.getString("no_rawat"));
psobat.setString(3, rsreg.getString("no_rawat"));

psobat.setString(4, "%"+status.getSelectedItem().toString()
        .replaceAll("Obat Rawat Jalan","Ralan")
        .replaceAll("Obat Rawat Inap","Ranap")
        .replaceAll("Semua Status","")+"%");
psobat.setString(5, "%"+kdjenis.getText()+nmjns.getText()+"%");
psobat.setString(6, "%"+kdkategori.getText()+nmkategori.getText()+"%");
psobat.setString(7, "%"+kdgolongan.getText()+nmgolongan.getText()+"%");
psobat.setString(8, "%"+kdasal.getText()+nmasal.getText()+"%");

String key = "%"+TCari.getText().trim()+"%";
psobat.setString(9, key);
psobat.setString(10, key);
psobat.setString(11, key);
psobat.setString(12, key);
psobat.setString(13, key);
psobat.setString(14, key);
psobat.setString(15, key);
psobat.setString(16, key);
psobat.setString(17, key);
psobat.setString(18, key);
psobat.setString(19, key);

rsobat = psobat.executeQuery();
while (rsobat.next()) {
    tabMode.addRow(new Object[]{
        i++,
        rsobat.getString("nm_pasien"),
        rsobat.getString("no_rkm_medis"),
        rsobat.getString("no_rawat"),
        rsobat.getString("nm_dokter"),
        rsobat.getString("aturan_pakai"),
        rsobat.getString("nama_brng"),
        rsobat.getString("jenis_obat"),
        rsobat.getString("cara_bayar"),
        rsobat.getString("kategori"),
        rsobat.getString("asal_stok"),
        rsobat.getString("golongan")
    });
}

if (rsobat != null) rsobat.close();
if (psobat != null) psobat.close();
        }

    } catch (Exception e) {
        System.out.println("Catatan prosesCari: " + e);
    } finally {
        try { if (rsreg != null) rsreg.close(); } catch (Exception ex) {}
        try { if (psreg != null) psreg.close(); } catch (Exception ex) {}
        this.setCursor(Cursor.getDefaultCursor());
    }
}
    
private void prosesCari2() {
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        Valid.tabelKosong(tabMode2);

        String sql =
            "select " +
            " rp.no_rawat, rp.no_rkm_medis, p.nm_pasien, " +
            " ifnull((select d2.nm_dokter from resep_obat ro2 " +
            "         left join dokter d2 on d2.kd_dokter=ro2.kd_dokter " +
            "         where ro2.no_rawat=rp.no_rawat " +
            "         order by ro2.tgl_perawatan desc, ro2.jam desc limit 1),'-') as nm_dokter, " +
            " ifnull(pj.png_jawab,'-') as cara_bayar, " +
            " ifnull((select sum(datediff(ifnull(ki2.tgl_keluar,curdate()), ki2.tgl_masuk) + 1) " +
            "        from kamar_inap ki2 where ki2.no_rawat = rp.no_rawat), 1) as lama_rawat, " +
            " ifnull(group_concat(distinct db.nama_brng order by db.nama_brng separator ', '), '-') as daftar_obat, " +
            " ifnull(group_concat(distinct ap.aturan separator ', '), '-') as aturan_pakai, " +
            " ifnull(kb.nama,'-') as kategori, " +
            " ifnull(b.nm_bangsal,'-') as asal_stok, " +
            " ifnull(gb.nama,'-') as golongan " +
            "from kamar_inap ki " +
            "inner join reg_periksa rp on ki.no_rawat = rp.no_rawat " +
            "inner join pasien p on rp.no_rkm_medis = p.no_rkm_medis " +
            "left join penjab pj on rp.kd_pj = pj.kd_pj " +
            "left join detail_pemberian_obat dpo " +
            "  on dpo.no_rawat = rp.no_rawat " +
            " and dpo.kd_bangsal like ? " +
            "left join databarang db " +
            "  on db.kode_brng = dpo.kode_brng " +
            " and db.kdjns like ? " +
            " and db.kode_kategori like ? " +
            " and db.kode_golongan like ? " +
            "left join aturan_pakai ap on ap.no_rawat = rp.no_rawat and ap.kode_brng = dpo.kode_brng " +
            "left join kategori_barang kb on kb.kode = db.kode_kategori " +
            "left join golongan_barang gb on gb.kode = db.kode_golongan " +
            "left join bangsal b on b.kd_bangsal = dpo.kd_bangsal " +
            "where ki.stts_pulang <> 'Pindah Kamar' " +
            "and ki.tgl_keluar between ? and ? " +
            "and rp.kd_poli like ? " +
            "and concat(rp.kd_pj,ifnull(pj.png_jawab,'')) like ? " +
            "and (rp.no_rkm_medis like ? or p.nm_pasien like ?) " +
            "group by rp.no_rawat " +
            "order by p.nm_pasien";

        pskamar = koneksi.prepareStatement(sql);

        // filter join (bangsal, jenis, kategori, golongan)
        pskamar.setString(1, "%" + kdasal.getText().trim() + "%");
        pskamar.setString(2, "%" + kdjenis.getText().trim() + "%");
        pskamar.setString(3, "%" + kdkategori.getText().trim() + "%");
        pskamar.setString(4, "%" + kdgolongan.getText().trim() + "%");

        // tanggal
        pskamar.setString(5, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
        pskamar.setString(6, Valid.SetTgl(Tgl2.getSelectedItem() + ""));

        // poli
        String kdPoliFilter = getKdPoliFilter();
        pskamar.setString(7, kdPoliFilter);

        // cara bayar + keyword
        pskamar.setString(8, "%" + kdpenjab.getText() + nmpenjab.getText() + "%");
        pskamar.setString(9, "%" + TCari.getText().trim() + "%");
        pskamar.setString(10, "%" + TCari.getText().trim() + "%");

        rskamar = pskamar.executeQuery();

        int no = 1;
        int totalHari = 0;

        while (rskamar.next()) {
            int lama = rskamar.getInt("lama_rawat");
            totalHari += lama;

String daftarObat = potong(rskamar.getString("daftar_obat"), 1000);
String aturanPakai = potong(rskamar.getString("aturan_pakai"), 1000); // opsional biar aman juga

tabMode2.addRow(new Object[]{
    no++,
    rskamar.getString("nm_pasien"),
    rskamar.getString("no_rkm_medis"),
    rskamar.getString("no_rawat"),
    lama,
    rskamar.getString("nm_dokter"),
    aturanPakai,
    daftarObat,
    "-",
    rskamar.getString("cara_bayar"),
    rskamar.getString("kategori"),
    rskamar.getString("asal_stok"),
    rskamar.getString("golongan")
});
        }

        setFooterTotalHariRanap(totalHari);

    } catch (Exception e) {
        System.out.println("Catatan prosesCari2: " + e);
    } finally {
        try { if (rskamar != null) rskamar.close(); } catch (Exception ex) {}
        try { if (pskamar != null) pskamar.close(); } catch (Exception ex) {}
        this.setCursor(Cursor.getDefaultCursor());
    }
}

private void setFooterTotalHariRanap(int totalHari){
    if(TotalHariRanap != null){
        TotalHariRanap.setText(String.valueOf(totalHari));
    }
}

private void isForm(){
    int lebar = internalFrame1.getWidth()-20; // atau getWidth()
    if(ChkInput.isSelected()){
        ChkInput.setVisible(false);
        PanelInput.setPreferredSize(new Dimension(lebar, 96));
        FormInput.setVisible(true);
        ChkInput.setVisible(true);
    }else{
        ChkInput.setVisible(false);
        PanelInput.setPreferredSize(new Dimension(lebar, 20));
        FormInput.setVisible(false);
        ChkInput.setVisible(true);
    }
    PanelInput.revalidate();
    PanelInput.repaint();
}

private String getKdPoliFilter(){
    String nm = (nmpoli == null) ? "" : nmpoli.getText().trim();
    if(nm.equals("")){
        return "%";
    }
    String kd = Sequel.cariIsi("select kd_poli from poliklinik where nm_poli=?", nm);
    if(kd == null || kd.trim().equals("")){
        return "%"; // fallback: jangan bikin hasil kosong
    }
    return kd.trim();
}

private int getJumlahKolomTemporary() {
    try {
        // hitung kolom tabel temporary di database aktif
        String jml = Sequel.cariIsi(
            "select count(*) from information_schema.columns " +
            "where table_schema=database() and table_name='temporary'"
        );
        return Integer.parseInt(jml);
    } catch (Exception e) {
        System.out.println("Gagal hitung kolom temporary: " + e);
        // fallback (umum di Khanza: no + temp1..temp37 = 38 kolom)
        return 38;
    }
}

private void simpanTemporary(int noUrut, String[] temp, String ip, String pesan) {
    // asumsi struktur kolom: no, temp1, temp2, ... tempN (temp terakhir biasanya temp37)
    int totalKolom = getJumlahKolomTemporary();
    int totalTemp  = totalKolom - 1; // selain kolom "no"

    StringBuilder values = new StringBuilder();
    values.append("'").append(noUrut).append("'");

    for (int t = 1; t <= totalTemp; t++) {
        String isi = "";

        // kalau ada data temp[t-1]
        if (t <= temp.length) {
            isi = temp[t - 1] == null ? "" : temp[t - 1];
        }

        // paksa kolom temp terakhir = ip (di code kamu dipakai temp37)
        if (t == totalTemp) {
            isi = ip;
        }

        // escape kutip
        isi = isi.replace("'", "`");
        values.append(",'").append(isi).append("'");
    }

    Sequel.menyimpan("temporary", values.toString(), pesan);
}

private String potong(String s, int max){
    if(s == null) return "";
    s = s.trim();
    return (s.length() > max) ? s.substring(0, max) : s;
}
    }
    
