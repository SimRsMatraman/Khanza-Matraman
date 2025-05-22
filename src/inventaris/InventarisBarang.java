/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package inventaris;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class InventarisBarang extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();

    /** Creates new form DlgJnsPerawatan */
    public InventarisBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No Urut Barang",
                      "Nama Master Inventaris",
                      "No BA",
                      "No SP",
                      "No Registrasi",
                      "Barcode",
                      "No Izin Edar",
                      "Produsen",
                      "Tipe",
                      "Harga",
                      "Thn.Produksi",
                      "Jml.Brg",
                      "LifeTime",
                      "Merk",
                      "Kategori",
                      "Jenis",
                      "Asal Perolehan",
                      "Kondisi",
                      "Ruangan",
                      "Keterangan"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 20; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(150);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        kode_barang.setDocument(new batasInput((byte)20).getKata(kode_barang));
        nama_barang.setDocument(new batasInput((byte)60).getKata(nama_barang));
        No_BA.setDocument(new batasInput((byte)50).getKata(No_BA));
        No_SP.setDocument(new batasInput((byte)50).getKata(No_SP));
        No_Register.setDocument(new batasInput((byte)50).getKata(No_Register));
        isbn.setDocument(new batasInput((byte)20).getKata(isbn));
        No_Izin.setDocument(new batasInput((byte)50).getKata(No_Izin));
        kode_produsen.setDocument(new batasInput((byte)10).getKata(kode_produsen));
        Tipe.setDocument(new batasInput((byte)100).getKata(Tipe));
        Harga.setDocument(new batasInput((byte)20).getKata(Harga));
        jml_barang.setDocument(new batasInput((byte)11).getOnlyAngka(jml_barang));
        LifeTime.setDocument(new batasInput((byte)100).getKata(LifeTime));
        id_merk.setDocument(new batasInput((byte)10).getKata(id_merk));
        id_kategori.setDocument(new batasInput((byte)10).getKata(id_kategori));
        id_jenis.setDocument(new batasInput((byte)10).getKata(id_jenis));
        id_asal.setDocument(new batasInput((byte)10).getKata(id_asal));
        id_kondisi.setDocument(new batasInput((byte)10).getKata(id_kondisi));
        id_ruang.setDocument(new batasInput((byte)10).getKata(id_ruang));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.requestFocus();        
        
      
        ChkInput.setSelected(false);
        isForm(); 
        Valid.LoadTahun(thn_produksi);
        
        produsen.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(produsen.getTable().getSelectedRow()!= -1){                   
                    kode_produsen.setText(produsen.getTable().getValueAt(produsen.getTable().getSelectedRow(),0).toString());                    
                    nama_produsen.setText(produsen.getTable().getValueAt(produsen.getTable().getSelectedRow(),1).toString());
                }   
                kode_produsen.requestFocus();
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
        
        produsen.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    produsen.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        merk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(merk.getTable().getSelectedRow()!= -1){                   
                    id_merk.setText(merk.getTable().getValueAt(merk.getTable().getSelectedRow(),0).toString());                    
                    nm_merk.setText(merk.getTable().getValueAt(merk.getTable().getSelectedRow(),1).toString());
                }   
                id_merk.requestFocus();
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
        
        merk.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    merk.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kategori.getTable().getSelectedRow()!= -1){                   
                    id_kategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),0).toString());                    
                    nm_kategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),1).toString());
                }   
                id_kategori.requestFocus();
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
        
        kategori.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kategori.dispose();
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
                if(jenis.getTable().getSelectedRow()!= -1){                   
                    id_jenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),0).toString());                    
                    nm_jenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),1).toString());
                }   
                id_jenis.requestFocus();
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
        
        asal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(asal.getTable().getSelectedRow()!= -1){                   
                    id_asal.setText(asal.getTable().getValueAt(asal.getTable().getSelectedRow(),0).toString());                    
                    nm_asal.setText(asal.getTable().getValueAt(asal.getTable().getSelectedRow(),1).toString());
                }   
                id_asal.requestFocus();
//                asal.dispose();
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
        
        Kondisi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(Kondisi.getTable().getSelectedRow()!= -1){                   
                    id_kondisi.setText(Kondisi.getTable().getValueAt(Kondisi.getTable().getSelectedRow(),0).toString());                    
                    nm_kondisi.setText(Kondisi.getTable().getValueAt(Kondisi.getTable().getSelectedRow(),1).toString());
                }   
                id_kondisi.requestFocus();
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
        
        Ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(Ruang.getTable().getSelectedRow()!= -1){                   
                    id_ruang.setText(Ruang.getTable().getValueAt(Ruang.getTable().getSelectedRow(),0).toString());                    
                    nm_ruang.setText(Ruang.getTable().getValueAt(Ruang.getTable().getSelectedRow(),1).toString());
                }   
                id_ruang.requestFocus();
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
        
        jenis.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jenis.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        asal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    asal.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        Kondisi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    Kondisi.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        Ruang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    Ruang.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
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
    }
    private InventarisProdusen produsen=new InventarisProdusen(null,false); 
    private InventarisMerk merk=new InventarisMerk(null,false); 
    private InventarisKategori kategori=new InventarisKategori(null,false);
    public InventarisJenis jenis=new InventarisJenis(null,false); 
    public InventarisAsal asal=new InventarisAsal(null,false);
    public InventarisKondisi Kondisi=new InventarisKondisi(null,false);
    public InventarisRuang Ruang=new InventarisRuang(null,false);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        FormInput = new widget.PanelBiasa();
        No_Ba = new widget.Label();
        kode_barang = new widget.TextBox();
        nama_barang = new widget.TextBox();
        jml_barang = new widget.TextBox();
        label7 = new widget.Label();
        isbn = new widget.TextBox();
        label8 = new widget.Label();
        label10 = new widget.Label();
        label9 = new widget.Label();
        label19 = new widget.Label();
        kode_produsen = new widget.TextBox();
        nama_produsen = new widget.TextBox();
        btnProdusen = new widget.Button();
        label20 = new widget.Label();
        id_merk = new widget.TextBox();
        nm_merk = new widget.TextBox();
        btnMerk = new widget.Button();
        label21 = new widget.Label();
        id_kategori = new widget.TextBox();
        nm_kategori = new widget.TextBox();
        btnKategori = new widget.Button();
        label22 = new widget.Label();
        id_jenis = new widget.TextBox();
        nm_jenis = new widget.TextBox();
        btnJenis = new widget.Button();
        label2 = new widget.Label();
        No_SP = new widget.TextBox();
        No_Sp = new widget.Label();
        No_BA = new widget.TextBox();
        No_Register1 = new widget.Label();
        No_Register = new widget.TextBox();
        label11 = new widget.Label();
        Tipe = new widget.TextBox();
        label12 = new widget.Label();
        Harga = new widget.TextBox();
        label23 = new widget.Label();
        id_asal = new widget.TextBox();
        nm_asal = new widget.TextBox();
        btnAsal = new widget.Button();
        label24 = new widget.Label();
        id_kondisi = new widget.TextBox();
        nm_kondisi = new widget.TextBox();
        btnKondisi = new widget.Button();
        label25 = new widget.Label();
        id_ruang = new widget.TextBox();
        nm_ruang = new widget.TextBox();
        label13 = new widget.Label();
        Keterangan = new widget.TextBox();
        label14 = new widget.Label();
        LifeTime = new widget.TextBox();
        label15 = new widget.Label();
        No_Izin = new widget.TextBox();
        btnRuang = new widget.Button();
        thn_produksi = new widget.Tanggal();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Inventaris Barang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
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
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 155));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(150, 180));
        FormInput.setLayout(null);

        No_Ba.setText("No BA :");
        No_Ba.setToolTipText("");
        No_Ba.setName("No_Ba"); // NOI18N
        FormInput.add(No_Ba);
        No_Ba.setBounds(70, 40, 40, 23);

        kode_barang.setName("kode_barang"); // NOI18N
        kode_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_barangKeyPressed(evt);
            }
        });
        FormInput.add(kode_barang);
        kode_barang.setBounds(120, 10, 130, 23);

        nama_barang.setName("nama_barang"); // NOI18N
        nama_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nama_barangKeyPressed(evt);
            }
        });
        FormInput.add(nama_barang);
        nama_barang.setBounds(340, 10, 400, 23);

        jml_barang.setName("jml_barang"); // NOI18N
        jml_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jml_barangKeyPressed(evt);
            }
        });
        FormInput.add(jml_barang);
        jml_barang.setBounds(540, 160, 90, 23);

        label7.setText("Tanggal Produksi :");
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(470, 100, 90, 23);

        isbn.setName("isbn"); // NOI18N
        isbn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                isbnKeyPressed(evt);
            }
        });
        FormInput.add(isbn);
        isbn.setBounds(120, 130, 340, 23);

        label8.setText("Tipe :");
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(260, 70, 70, 23);

        label10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label10.setText("Nama Barang :");
        label10.setName("label10"); // NOI18N
        FormInput.add(label10);
        label10.setBounds(260, 10, 80, 23);

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setText("Jml.Barang :");
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(470, 160, 70, 23);

        label19.setText("Produsen :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(240, 40, 90, 23);

        kode_produsen.setName("kode_produsen"); // NOI18N
        kode_produsen.setPreferredSize(new java.awt.Dimension(207, 23));
        kode_produsen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kode_produsenKeyPressed(evt);
            }
        });
        FormInput.add(kode_produsen);
        kode_produsen.setBounds(340, 40, 80, 23);

        nama_produsen.setEditable(false);
        nama_produsen.setName("nama_produsen"); // NOI18N
        nama_produsen.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nama_produsen);
        nama_produsen.setBounds(420, 40, 290, 23);

        btnProdusen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnProdusen.setMnemonic('1');
        btnProdusen.setToolTipText("Alt+1");
        btnProdusen.setName("btnProdusen"); // NOI18N
        btnProdusen.setPreferredSize(new java.awt.Dimension(28, 23));
        btnProdusen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdusenActionPerformed(evt);
            }
        });
        FormInput.add(btnProdusen);
        btnProdusen.setBounds(710, 40, 25, 23);

        label20.setText("Merk :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(760, 10, 70, 23);

        id_merk.setName("id_merk"); // NOI18N
        id_merk.setPreferredSize(new java.awt.Dimension(207, 23));
        id_merk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_merkKeyPressed(evt);
            }
        });
        FormInput.add(id_merk);
        id_merk.setBounds(840, 10, 80, 23);

        nm_merk.setEditable(false);
        nm_merk.setName("nm_merk"); // NOI18N
        nm_merk.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_merk);
        nm_merk.setBounds(930, 10, 180, 23);

        btnMerk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnMerk.setMnemonic('1');
        btnMerk.setToolTipText("Alt+1");
        btnMerk.setName("btnMerk"); // NOI18N
        btnMerk.setPreferredSize(new java.awt.Dimension(28, 23));
        btnMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMerkActionPerformed(evt);
            }
        });
        FormInput.add(btnMerk);
        btnMerk.setBounds(1110, 10, 25, 23);

        label21.setText("Kode Barang :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label21);
        label21.setBounds(760, 40, 70, 23);

        id_kategori.setName("id_kategori"); // NOI18N
        id_kategori.setPreferredSize(new java.awt.Dimension(207, 23));
        id_kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_kategoriKeyPressed(evt);
            }
        });
        FormInput.add(id_kategori);
        id_kategori.setBounds(840, 40, 80, 23);

        nm_kategori.setEditable(false);
        nm_kategori.setName("nm_kategori"); // NOI18N
        nm_kategori.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_kategori);
        nm_kategori.setBounds(930, 40, 180, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('1');
        btnKategori.setToolTipText("Alt+1");
        btnKategori.setName("btnKategori"); // NOI18N
        btnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        FormInput.add(btnKategori);
        btnKategori.setBounds(1110, 40, 25, 23);

        label22.setText("Jenis :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(760, 70, 70, 23);

        id_jenis.setName("id_jenis"); // NOI18N
        id_jenis.setPreferredSize(new java.awt.Dimension(207, 23));
        id_jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_jenisKeyPressed(evt);
            }
        });
        FormInput.add(id_jenis);
        id_jenis.setBounds(840, 70, 80, 23);

        nm_jenis.setEditable(false);
        nm_jenis.setName("nm_jenis"); // NOI18N
        nm_jenis.setPreferredSize(new java.awt.Dimension(207, 23));
        nm_jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nm_jenisActionPerformed(evt);
            }
        });
        FormInput.add(nm_jenis);
        nm_jenis.setBounds(930, 70, 180, 23);

        btnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenis.setMnemonic('1');
        btnJenis.setToolTipText("Alt+1");
        btnJenis.setName("btnJenis"); // NOI18N
        btnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        btnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisActionPerformed(evt);
            }
        });
        FormInput.add(btnJenis);
        btnJenis.setBounds(1110, 70, 25, 23);

        label2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label2.setText("Nomor Urut Barang :");
        label2.setToolTipText("");
        label2.setName("label2"); // NOI18N
        FormInput.add(label2);
        label2.setBounds(10, 10, 100, 23);

        No_SP.setName("No_SP"); // NOI18N
        No_SP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                No_SPKeyPressed(evt);
            }
        });
        FormInput.add(No_SP);
        No_SP.setBounds(120, 100, 130, 23);

        No_Sp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        No_Sp.setText("No SP :");
        No_Sp.setToolTipText("");
        No_Sp.setName("No_Sp"); // NOI18N
        FormInput.add(No_Sp);
        No_Sp.setBounds(70, 100, 40, 23);

        No_BA.setName("No_BA"); // NOI18N
        No_BA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                No_BAActionPerformed(evt);
            }
        });
        No_BA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                No_BAKeyPressed(evt);
            }
        });
        FormInput.add(No_BA);
        No_BA.setBounds(120, 40, 130, 23);

        No_Register1.setText("No Register :");
        No_Register1.setToolTipText("");
        No_Register1.setName("No_Register1"); // NOI18N
        FormInput.add(No_Register1);
        No_Register1.setBounds(40, 70, 64, 23);

        No_Register.setName("No_Register"); // NOI18N
        No_Register.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                No_RegisterKeyPressed(evt);
            }
        });
        FormInput.add(No_Register);
        No_Register.setBounds(120, 70, 130, 23);

        label11.setText("Barcode SN :");
        label11.setName("label11"); // NOI18N
        FormInput.add(label11);
        label11.setBounds(40, 130, 70, 23);

        Tipe.setName("Tipe"); // NOI18N
        Tipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TipeKeyPressed(evt);
            }
        });
        FormInput.add(Tipe);
        Tipe.setBounds(340, 70, 400, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Harga :");
        label12.setName("label12"); // NOI18N
        FormInput.add(label12);
        label12.setBounds(290, 100, 40, 23);

        Harga.setName("Harga"); // NOI18N
        Harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargaKeyPressed(evt);
            }
        });
        FormInput.add(Harga);
        Harga.setBounds(340, 100, 110, 23);

        label23.setText("Asal Perolehan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(740, 100, 90, 23);

        id_asal.setName("id_asal"); // NOI18N
        id_asal.setPreferredSize(new java.awt.Dimension(207, 23));
        id_asal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_asalKeyPressed(evt);
            }
        });
        FormInput.add(id_asal);
        id_asal.setBounds(840, 100, 80, 23);

        nm_asal.setEditable(false);
        nm_asal.setName("nm_asal"); // NOI18N
        nm_asal.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_asal);
        nm_asal.setBounds(930, 100, 180, 23);

        btnAsal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsal.setMnemonic('1');
        btnAsal.setToolTipText("Alt+1");
        btnAsal.setName("btnAsal"); // NOI18N
        btnAsal.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsalActionPerformed(evt);
            }
        });
        FormInput.add(btnAsal);
        btnAsal.setBounds(1110, 100, 25, 23);

        label24.setText("Kondisi :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label24);
        label24.setBounds(740, 130, 90, 23);

        id_kondisi.setName("id_kondisi"); // NOI18N
        id_kondisi.setPreferredSize(new java.awt.Dimension(207, 23));
        id_kondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_kondisiKeyPressed(evt);
            }
        });
        FormInput.add(id_kondisi);
        id_kondisi.setBounds(840, 130, 80, 23);

        nm_kondisi.setEditable(false);
        nm_kondisi.setName("nm_kondisi"); // NOI18N
        nm_kondisi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_kondisi);
        nm_kondisi.setBounds(930, 130, 180, 23);

        btnKondisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKondisi.setMnemonic('1');
        btnKondisi.setToolTipText("Alt+1");
        btnKondisi.setName("btnKondisi"); // NOI18N
        btnKondisi.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKondisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKondisiActionPerformed(evt);
            }
        });
        FormInput.add(btnKondisi);
        btnKondisi.setBounds(1110, 130, 25, 23);

        label25.setText("Ruangan :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label25);
        label25.setBounds(740, 160, 90, 23);

        id_ruang.setName("id_ruang"); // NOI18N
        id_ruang.setPreferredSize(new java.awt.Dimension(207, 23));
        id_ruang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_ruangActionPerformed(evt);
            }
        });
        id_ruang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                id_ruangKeyPressed(evt);
            }
        });
        FormInput.add(id_ruang);
        id_ruang.setBounds(840, 160, 80, 23);

        nm_ruang.setEditable(false);
        nm_ruang.setName("nm_ruang"); // NOI18N
        nm_ruang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nm_ruang);
        nm_ruang.setBounds(930, 160, 180, 23);

        label13.setText("Keterangan :");
        label13.setName("label13"); // NOI18N
        FormInput.add(label13);
        label13.setBounds(40, 190, 70, 23);

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeteranganActionPerformed(evt);
            }
        });
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(120, 190, 620, 23);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("LifeTime :");
        label14.setName("label14"); // NOI18N
        FormInput.add(label14);
        label14.setBounds(470, 130, 60, 23);

        LifeTime.setName("LifeTime"); // NOI18N
        LifeTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LifeTimeActionPerformed(evt);
            }
        });
        LifeTime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LifeTimeKeyPressed(evt);
            }
        });
        FormInput.add(LifeTime);
        LifeTime.setBounds(520, 130, 110, 23);

        label15.setText("No Izin Edar :");
        label15.setName("label15"); // NOI18N
        FormInput.add(label15);
        label15.setBounds(40, 160, 70, 23);

        No_Izin.setName("No_Izin"); // NOI18N
        No_Izin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                No_IzinKeyPressed(evt);
            }
        });
        FormInput.add(No_Izin);
        No_Izin.setBounds(120, 160, 340, 23);

        btnRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuang.setMnemonic('1');
        btnRuang.setToolTipText("Alt+1");
        btnRuang.setName("btnRuang"); // NOI18N
        btnRuang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuangActionPerformed(evt);
            }
        });
        FormInput.add(btnRuang);
        btnRuang.setBounds(1110, 160, 25, 23);

        thn_produksi.setForeground(new java.awt.Color(50, 70, 50));
        thn_produksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-05-2025" }));
        thn_produksi.setDisplayFormat("dd-MM-yyyy");
        thn_produksi.setName("thn_produksi"); // NOI18N
        thn_produksi.setOpaque(false);
        thn_produksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                thn_produksiKeyPressed(evt);
            }
        });
        FormInput.add(thn_produksi);
        thn_produksi.setBounds(580, 100, 160, 23);

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
        internalFrame1.getAccessibleContext().setAccessibleName("::[ Data Master Inventaris ]::");
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kode_barang.getText().trim().equals("")){
            Valid.textKosong(kode_barang,"Kode Barang");
        }else if(nama_barang.getText().trim().equals("")){
            Valid.textKosong(nama_barang,"Nama Barang");
        }else if(No_BA.getText().trim().equals("")){
            Valid.textKosong(No_BA,"No BA");
        }else if(No_SP.getText().trim().equals("")){
            Valid.textKosong(No_SP,"No SP");
        }else if(No_Register.getText().trim().equals("")){
            Valid.textKosong(No_Register,"No Registrasi");
        }else if(isbn.getText().trim().equals("")){
            Valid.textKosong(isbn,"Barcode");
        }else if(No_Izin.getText().trim().equals("")){
            Valid.textKosong(No_Izin,"No Izin Edar");
        }else if(kode_produsen.getText().trim().equals("")||nama_produsen.getText().trim().equals("")){
            Valid.textKosong(kode_produsen,"Produsen");
        }else if(Tipe.getText().trim().equals("")){
            Valid.textKosong(Tipe,"Tipe");
        }else if(Harga.getText().trim().equals("")){
            Valid.textKosong(Harga,"Harga");
        }else if(jml_barang.getText().trim().equals("")){
            Valid.textKosong(jml_barang,"Jumlah Barang");
        }else if(id_merk.getText().trim().equals("")||nm_merk.getText().trim().equals("")){
            Valid.textKosong(id_merk,"Merk");
        }else if(id_kategori.getText().trim().equals("")||nm_kategori.getText().trim().equals("")){
            Valid.textKosong(id_kategori,"Kategori");
        }else if(id_jenis.getText().trim().equals("")||nm_jenis.getText().trim().equals("")){
            Valid.textKosong(id_jenis,"Jenis");
        }else if(id_asal.getText().trim().equals("")||nm_asal.getText().trim().equals("")){
            Valid.textKosong(id_asal,"Asal Perolehan"); 
        }else if(id_kondisi.getText().trim().equals("")||nm_kondisi.getText().trim().equals("")){
            Valid.textKosong(id_kondisi,"Kondisi");
        }else if(id_ruang.getText().trim().equals("")||nm_ruang.getText().trim().equals("")){
            Valid.textKosong(id_ruang,"Ruangan");
        }else if(Keterangan.getText().trim().equals("")||Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.menyimpan("inventaris_barang","'"+kode_barang.getText()+"','"+nama_barang.getText()+"','"+No_BA.getText()+"','"+No_SP.getText()+"','"+No_Register.getText()+"','"+isbn.getText()+"','"+No_Izin.getText()+"','"
                        +kode_produsen.getText()+"','"+Tipe.getText()+"','"+Harga.getText()+"','"+Valid.SetTgl(thn_produksi.getSelectedItem()+"")+"','"+jml_barang.getText()+"','"+LifeTime.getText()+"','"+id_merk.getText()+"','"+id_kategori.getText()+"','"+
                        id_jenis.getText()+"','"+id_asal.getText()+"','"+id_kondisi.getText()+"','"+id_ruang.getText()+"','"+Keterangan.getText()+"'","Kode Barang");
                //----------------------------------------------------------
                kode_barang.requestFocus();
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,id_jenis,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,kode_barang,"inventaris_barang","kode_barang");
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(kode_barang.getText().trim().equals("")){
            Valid.textKosong(kode_barang,"Kode Barang");
        }else if(nama_barang.getText().trim().equals("")){
            Valid.textKosong(nama_barang,"Nama Barang");
        }else if(No_BA.getText().trim().equals("")){
            Valid.textKosong(No_BA,"No BA");
        }else if(No_SP.getText().trim().equals("")){
            Valid.textKosong(No_SP,"No SP");
        }else if(No_Register.getText().trim().equals("")){
            Valid.textKosong(No_Register,"No Registrasi");
        }else if(isbn.getText().trim().equals("")){
            Valid.textKosong(isbn,"Barcode");
        }else if(No_Izin.getText().trim().equals("")){
            Valid.textKosong(No_Izin,"No Izin Edar");
        }else if(kode_produsen.getText().trim().equals("")||nama_produsen.getText().trim().equals("")){
            Valid.textKosong(kode_produsen,"Produsen");
        }else if(Tipe.getText().trim().equals("")){
            Valid.textKosong(Tipe,"type_brng");
        }else if(Harga.getText().trim().equals("")){
            Valid.textKosong(Harga,"Harga");
        }else if(jml_barang.getText().trim().equals("")){
            Valid.textKosong(jml_barang,"Jumlah Barang");
        }else if(id_merk.getText().trim().equals("")||nm_merk.getText().trim().equals("")){
            Valid.textKosong(id_merk,"Merk");
        }else if(id_kategori.getText().trim().equals("")||nm_kategori.getText().trim().equals("")){
            Valid.textKosong(id_kategori,"Kategori");
        }else if(id_jenis.getText().trim().equals("")||nm_jenis.getText().trim().equals("")){
            Valid.textKosong(id_jenis,"Jenis");
        }else if(id_asal.getText().trim().equals("")||nm_asal.getText().trim().equals("")){
            Valid.textKosong(id_asal,"asal_barang"); 
        }else if(id_kondisi.getText().trim().equals("")||nm_kondisi.getText().trim().equals("")){
            Valid.textKosong(id_kondisi,"status_barang");
        }else if(id_ruang.getText().trim().equals("")||nm_ruang.getText().trim().equals("")){
            Valid.textKosong(id_ruang,"id_ruang");
        }else if(Keterangan.getText().trim().equals("")||Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else {
                //menyimpan-------------------------------------------------
                Sequel.mengedit("inventaris_barang","kode_barang='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0) +"'",
                        "kode_barang='"+kode_barang.getText()+"',nama_barang='"+nama_barang.getText()+"',No_BA='"+No_BA.getText()+"',No_SP='"+No_SP.getText()+"',No_Register='"+No_Register.getText()+"',"
                      + "isbn='"+isbn.getText()+"',No_Izin='"+No_Izin.getText()+"',kode_produsen='"+kode_produsen.getText()+"',type_brng='"+Tipe.getText()+"',Harga='"+Harga.getText()+"',thn_produksi='"+Valid.SetTgl(thn_produksi.getSelectedItem()+"")+"',jml_barang='"+jml_barang.getText()+"',"
                      + "id_merk='"+id_merk.getText()+"',id_kategori='"+id_kategori.getText()+"',id_jenis='"+id_jenis.getText()+"',asal_barang='"+id_asal.getText()+"',status_barang='"+id_kondisi.getText()+"',id_ruang='"+id_ruang.getText()+"',keterangan='"+Keterangan.getText()+"'");
                        
////            Sequel.mengedit("inventaris_barang","kode_barang='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0) +"'", tgl_pengadaan='"+Valid.SetTgl(tgl_pengadaan.getSelectedItem()+"")
//                        "kode_barang='"+kode_barang.getText()+"',nama_barang='"+nama_barang.getText()+"',jml_barang='"+jml_barang.getText()+"',kode_produsen='"+
//                        kode_produsen.getText()+"',id_merk='"+id_merk.getText()+"',thn_produksi='"+thn_produksi.getSelectedItem()+"',isbn='"+isbn.getText()+"',id_kategori='"+
//                        id_kategori.getText()+"',id_jenis='"+id_jenis.getText()+"'");
                //----------------------------------------------------------
                kode_barang.requestFocus();
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

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
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
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
                param.put("parameter","%"+TCari.getText().trim()+"%");  
                Valid.MyReport("rptBarangInventaris.jasper","report","::[ Data Master Inventaris ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbJnsPerawatan.requestFocus();
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
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }            
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void kode_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_barangKeyPressed
        Valid.pindah(evt,id_jenis,nama_barang,TCari);
}//GEN-LAST:event_kode_barangKeyPressed

private void nama_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_barangKeyPressed
        Valid.pindah(evt,kode_barang,jml_barang);
}//GEN-LAST:event_nama_barangKeyPressed

private void jml_barangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jml_barangKeyPressed
        Valid.pindah(evt,nama_barang,thn_produksi);
}//GEN-LAST:event_jml_barangKeyPressed

private void isbnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_isbnKeyPressed
        Valid.pindah(evt,id_merk,id_kategori);
}//GEN-LAST:event_isbnKeyPressed

private void kode_produsenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kode_produsenKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_produsen.nama_produsen from inventaris_produsen where inventaris_produsen.kode_produsen=?",nama_produsen,kode_produsen.getText());        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_produsen.nama_produsen from inventaris_produsen where inventaris_produsen.kode_produsen=?",nama_produsen,kode_produsen.getText()); 
        thn_produksi.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_produsen.nama_produsen from inventaris_produsen where inventaris_produsen.kode_produsen=?",nama_produsen,kode_produsen.getText()); 
        id_merk.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnProdusenActionPerformed(null);
    }
}//GEN-LAST:event_kode_produsenKeyPressed

private void btnProdusenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdusenActionPerformed
    produsen.isCek();
    produsen.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    produsen.setLocationRelativeTo(internalFrame1);
    produsen.setVisible(true);
}//GEN-LAST:event_btnProdusenActionPerformed

private void id_merkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_merkKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_merk.nama_merk from inventaris_merk where inventaris_merk.id_merk=?",nm_merk,id_merk.getText());        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_merk.nama_merk from inventaris_merk where inventaris_merk.id_merk=?",nm_merk,id_merk.getText());   
        kode_produsen.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_merk.nama_merk from inventaris_merk where inventaris_merk.id_merk=?",nm_merk,id_merk.getText());   
        isbn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnMerkActionPerformed(null);
    }
}//GEN-LAST:event_id_merkKeyPressed

private void btnMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMerkActionPerformed
    merk.isCek();
    merk.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    merk.setLocationRelativeTo(internalFrame1);
    merk.setVisible(true);
}//GEN-LAST:event_btnMerkActionPerformed

private void id_kategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_kategoriKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_kategori.nama_kategori from inventaris_kategori where inventaris_kategori.id_kategori='"+id_kategori.getText()+"'",nm_kategori);       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_kategori.nama_kategori from inventaris_kategori where inventaris_kategori.id_kategori='"+id_kategori.getText()+"'",nm_kategori);
        isbn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_kategori.nama_kategori from inventaris_kategori where inventaris_kategori.id_kategori='"+id_kategori.getText()+"'",nm_kategori);
        id_jenis.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnKategoriActionPerformed(null);
    }
}//GEN-LAST:event_id_kategoriKeyPressed

private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
    kategori.isCek();
    kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    kategori.setLocationRelativeTo(internalFrame1);
    kategori.setAlwaysOnTop(false);
    kategori.setVisible(true);
}//GEN-LAST:event_btnKategoriActionPerformed

private void id_jenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_jenisKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_jenis.nama_jenis from inventaris_jenis where inventaris_jenis.id_jenis=?",nm_jenis,id_jenis.getText());       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_jenis.nama_jenis from inventaris_jenis where inventaris_jenis.id_jenis=?",nm_jenis,id_jenis.getText());  
        id_kategori.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_jenis.nama_jenis from inventaris_jenis where inventaris_jenis.id_jenis=?",nm_jenis,id_jenis.getText());  
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnJenisActionPerformed(null);
    }
}//GEN-LAST:event_id_jenisKeyPressed

private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
    jenis.isCek();
    jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    jenis.setLocationRelativeTo(internalFrame1);
    jenis.setAlwaysOnTop(false);
    jenis.setVisible(true);
}//GEN-LAST:event_btnJenisActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    private void No_SPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_No_SPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_No_SPKeyPressed

    private void No_BAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_No_BAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_No_BAKeyPressed

    private void No_RegisterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_No_RegisterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_No_RegisterKeyPressed

    private void TipeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeKeyPressed

    private void No_BAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_No_BAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_No_BAActionPerformed

    private void HargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HargaKeyPressed

    private void id_asalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_asalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_asal.nama_asal from inventaris_asal where inventaris_asal.id_asal=?",nm_asal,id_asal.getText());       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_asal.nama_asal from inventaris_asal where inventaris_asal.id_asal=?",nm_asal,id_asal.getText());  
        id_asal.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_asal.nama_asal from inventaris_asal where inventaris_asal.id_asal=?",nm_asal,id_asal.getText());  
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnAsalActionPerformed(null);
    }
    }//GEN-LAST:event_id_asalKeyPressed

    private void btnAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsalActionPerformed
    asal.isCek();
    asal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    asal.setLocationRelativeTo(internalFrame1);
    asal.setAlwaysOnTop(false);
    asal.setVisible(true);
    }//GEN-LAST:event_btnAsalActionPerformed

    private void id_kondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_kondisiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_kondisi.nama_kondisi from inventaris_kondisi where inventaris_kondisi.id_kondisi=?",nm_kondisi,id_kondisi.getText());       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_kondisi.nama_kondisi from inventaris_kondisi where inventaris_kondisi.id_kondisi=?",nm_kondisi,id_kondisi.getText());  
        id_kondisi.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_kondisi.nama_kondisi from inventaris_kondisi where inventaris_kondisi.id_kondisi=?",nm_kondisi,id_kondisi.getText());  
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnKondisiActionPerformed(null);
    }
    }//GEN-LAST:event_id_kondisiKeyPressed

    private void btnKondisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKondisiActionPerformed
    Kondisi.isCek();
    Kondisi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    Kondisi.setLocationRelativeTo(internalFrame1);
    Kondisi.setAlwaysOnTop(false);
    Kondisi.setVisible(true);
    }//GEN-LAST:event_btnKondisiActionPerformed

    private void id_ruangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_ruangKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang);       
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang); 
        Ruang.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang='"+id_ruang.getText()+"'",nm_ruang); 
        id_ruang.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnRuangActionPerformed(null);
    }
    }//GEN-LAST:event_id_ruangKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKeyPressed

    private void LifeTimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LifeTimeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LifeTimeKeyPressed

    private void LifeTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LifeTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LifeTimeActionPerformed

    private void No_IzinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_No_IzinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_No_IzinKeyPressed

    private void btnRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuangActionPerformed
    Ruang.isCek();
    Ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    Ruang.setLocationRelativeTo(internalFrame1);
    Ruang.setAlwaysOnTop(false);
    Ruang.setVisible(true);
    }//GEN-LAST:event_btnRuangActionPerformed

    private void id_ruangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_ruangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_ruangActionPerformed

    private void nm_jenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nm_jenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nm_jenisActionPerformed

    private void KeteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganActionPerformed

    private void thn_produksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_thn_produksiKeyPressed

    }//GEN-LAST:event_thn_produksiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisBarang dialog = new InventarisBarang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Harga;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.TextBox LifeTime;
    private widget.TextBox No_BA;
    private widget.Label No_Ba;
    private widget.TextBox No_Izin;
    private widget.TextBox No_Register;
    private widget.Label No_Register1;
    private widget.TextBox No_SP;
    private widget.Label No_Sp;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox Tipe;
    private widget.Button btnAsal;
    private widget.Button btnJenis;
    private widget.Button btnKategori;
    private widget.Button btnKondisi;
    private widget.Button btnMerk;
    private widget.Button btnProdusen;
    private widget.Button btnRuang;
    private widget.TextBox id_asal;
    private widget.TextBox id_jenis;
    private widget.TextBox id_kategori;
    private widget.TextBox id_kondisi;
    private widget.TextBox id_merk;
    private widget.TextBox id_ruang;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox isbn;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox jml_barang;
    private widget.TextBox kode_barang;
    private widget.TextBox kode_produsen;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label19;
    private widget.Label label2;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox nama_barang;
    private widget.TextBox nama_produsen;
    private widget.TextBox nm_asal;
    private widget.TextBox nm_jenis;
    private widget.TextBox nm_kategori;
    private widget.TextBox nm_kondisi;
    private widget.TextBox nm_merk;
    private widget.TextBox nm_ruang;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    private widget.Tanggal thn_produksi;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        String sql="select inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                    "inventaris_barang.No_BA, inventaris_barang.No_SP, inventaris_barang.No_Register, inventaris_barang.isbn, "+
                    "inventaris_barang.No_Izin, inventaris_produsen.nama_produsen, "+
                    "inventaris_barang.type_brng, inventaris_barang.Harga, inventaris_barang.thn_produksi, "+ 
                    "inventaris_barang.jml_barang, inventaris_barang.Lifetime, inventaris_merk.nama_merk, inventaris_kategori.nama_kategori, inventaris_jenis.nama_jenis, "+
                    "inventaris_asal.nama_asal, inventaris_kondisi.nama_kondisi, inventaris_ruang.nama_ruang, inventaris_barang.Keterangan from inventaris_barang "+
                    "inner join inventaris_produsen inner join inventaris_jenis inner join inventaris_kategori inner join inventaris_merk inner join inventaris_asal inner join inventaris_kondisi inner join inventaris_ruang "+
                    "on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                    "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori and inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                    "and inventaris_barang.asal_barang=inventaris_asal.id_asal and inventaris_barang.status_barang=inventaris_kondisi.id_kondisi "+
                    "and inventaris_barang.id_ruang=inventaris_ruang.id_ruang "+
                   "where inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.No_BA like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.No_SP like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.No_Register like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.isbn like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.No_Izin like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.type_brng like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.Harga like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.thn_produksi like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.jml_barang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.Lifetime like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_jenis.nama_jenis like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_asal.nama_asal like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_kondisi.nama_kondisi like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_ruang.nama_ruang like '%"+TCari.getText().trim()+"%' "+
                    "or inventaris_barang.Keterangan like '%"+TCari.getText().trim()+"%' order by inventaris_barang.kode_barang";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            ResultSet rs=koneksiDB.condb().prepareStatement(sql).executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString("kode_barang"),
                               rs.getString("nama_barang"),
                               rs.getString("No_BA"),
                               rs.getString("No_SP"),
                               rs.getString("No_Register"),
                               rs.getString("isbn"),
                               rs.getString("No_Izin"),
                               rs.getString("nama_produsen"),
                               rs.getString("type_brng"),
                               rs.getString("Harga"),
//                               rs.getString("thn_produksi").substring(0,4),
                               rs.getString("thn_produksi"),
                               rs.getString("jml_barang"),
                               rs.getString("Lifetime"),
                               rs.getString("nama_merk"),
                               rs.getString("nama_kategori"),
                               rs.getString("nama_jenis"),
                               rs.getString("nama_asal"),
                               rs.getString("nama_kondisi"),
                               rs.getString("nama_ruang"),
                               rs.getString("keterangan")});
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        kode_barang.setText("");
        nama_barang.setText("-");
        jml_barang.setText("0");
        kode_produsen.setText("");
        nama_produsen.setText("");
        id_merk.setText("");
        nm_merk.setText("");
        thn_produksi.setDate(new Date());
        isbn.setText("-");
        id_kategori.setText("");
        nm_kategori.setText("");
        id_jenis.setText("");
        nm_jenis.setText("");
        id_asal.setText("");
        nm_asal.setText("");
        id_kondisi.setText("");
        nm_kondisi.setText("");
        id_ruang.setText("");
        nm_ruang.setText("");
        TCari.setText("");
        No_BA.setText("-");
        No_Register.setText("-");
        No_SP.setText("-");
        No_Izin.setText("-");
        Keterangan.setText("-");
        Tipe.setText("-");
        Harga.setText("-");
        LifeTime.setText("-");
        
        kode_barang.requestFocus();
        //Valid.autoNomer(" jns_perawatan ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_barang,4),signed)),0) from inventaris_barang  ","BI",8,kode_barang);
        kode_barang.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            try {
                PreparedStatement ps=koneksiDB.condb().prepareStatement(
                    "select inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                    "inventaris_barang.No_BA, inventaris_barang.No_SP, inventaris_barang.No_Register, inventaris_barang.isbn, "+
                    "inventaris_barang.No_Izin, inventaris_barang.kode_produsen, "+
                    "inventaris_barang.type_brng, inventaris_barang.Harga, inventaris_barang.thn_produksi, "+ 
                    "inventaris_barang.jml_barang, inventaris_barang.Lifetime, inventaris_barang.id_merk, inventaris_barang.id_kategori, inventaris_barang.id_jenis, "+
                    "inventaris_barang.asal_barang, inventaris_barang.status_barang, inventaris_barang.id_ruang, inventaris_barang.Keterangan from inventaris_barang where inventaris_barang.kode_barang=? ");
                ps.setString(1,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    kode_barang.setText(rs.getString("kode_barang"));
                    nama_barang.setText(rs.getString("nama_barang"));
                    No_BA.setText(rs.getString("No_BA"));
                    No_SP.setText(rs.getString("No_SP"));
                    No_Register.setText(rs.getString("No_Register"));
                    isbn.setText(rs.getString("isbn"));
                    No_Izin.setText(rs.getString("No_Izin"));
                    kode_produsen.setText(rs.getString("kode_produsen"));
                    Tipe.setText(rs.getString("Type_brng"));
                    Harga.setText(rs.getString("Harga"));
                    jml_barang.setText(rs.getString("jml_barang"));
                    LifeTime.setText(rs.getString("LifeTime"));
                    id_merk.setText(rs.getString("id_merk"));
                    id_kategori.setText(rs.getString("id_kategori"));
                    id_jenis.setText(rs.getString("id_jenis"));
                    id_asal.setText(rs.getString("asal_barang"));
                    id_kondisi.setText(rs.getString("status_barang"));
                    id_ruang.setText(rs.getString("id_ruang"));
                    Keterangan.setText(rs.getString("Keterangan"));
                    }
                nama_produsen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
                Valid.SetTgl(thn_produksi,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString());
                nm_merk.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),13).toString());
                nm_kategori.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),14).toString());
                nm_jenis.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),15).toString());
                nm_asal.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),16).toString());
                nm_kondisi.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),17).toString());
                nm_ruang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),18).toString());
            } catch (SQLException ex) {
                System.out.println("Notifikasi : "+ex);
            }
        }
    }

    

    public JTable getTable(){
        return tbJnsPerawatan;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,250));
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
        BtnSimpan.setEnabled(akses.getinventaris_koleksi());
        BtnHapus.setEnabled(akses.getinventaris_koleksi());
        BtnEdit.setEnabled(akses.getinventaris_koleksi());
        BtnPrint.setEnabled(akses.getinventaris_koleksi());
        TCari.requestFocus();
    }
    
   

    
}
