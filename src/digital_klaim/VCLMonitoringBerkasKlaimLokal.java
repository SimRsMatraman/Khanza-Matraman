
package digital_klaim;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.koneksiDB;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 *
 * @author dosen
 */
public final class VCLMonitoringBerkasKlaimLokal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRjLengkap,tabModeRi,tabModeRiLengkap;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private String URL="",jns,stts_verif,keterangan="",json="",utc="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private int i,iTotal,j,jTotal,row,k,l,m,n;
    private PreparedStatement ps,ps2,ps3,pscaripiutang;
    private ResultSet rs;
    private Connection koneksi=koneksiDB.condb();  
    private FileFilter excelFilter = new FileNameExtensionFilter("File CSV", "csv");
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public VCLMonitoringBerkasKlaimLokal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        startShortingDate();
        this.setLocation(10,2);
        setSize(928,674);

        Object[] row={"","No Rawat","No SEP","No.RM","Nama","Tgl. Masuk","Tgl. Pulang","Pelayanan","SEP","Resume","Billing","Hasil LAB","Hasil RAD","Surat Rujukan","Status Lengkap"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                  boolean a = false;
                if (colIndex==0 || colIndex==7||colIndex==8||colIndex==9 || colIndex==10|| colIndex==11|| colIndex==12|| colIndex==13|| colIndex==14|| colIndex==15) {
                    a=true;
                }
                return a;
              }
              Class[] types = new Class[] {
                 java.lang.Boolean.class,   java.lang.Object.class, java.lang.Object.class,    java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class,    java.lang.Object.class ,    java.lang.Object.class, java.lang.Boolean.class,
                 java.lang.Boolean.class,    java.lang.Boolean.class,     java.lang.Boolean.class,java.lang.Boolean.class,
                java.lang.Boolean.class,java.lang.Boolean.class
                      
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        
        
        tbRawatJalan.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbRawatJalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatJalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (int i = 0; i < 14; i++) {
            TableColumn column = tbRawatJalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(80);
            } else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(120);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
            
        }
        tbRawatJalan.setDefaultRenderer(Object.class, new TabelSerahTerimaBerkasKlaim()); 
        tabModeRjLengkap=new DefaultTableModel(null,new Object[]{ "","No Rawat","No SEP","No RM","Nama","Tgl. Masuk","Tgl. Pulang","Pelayanan","Status"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                  boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
              }
              Class[] types = new Class[] {
                 java.lang.Boolean.class,   java.lang.Object.class,     java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class,    java.lang.Object.class ,    java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
                      
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        
        
        tbRawatJalanLengkap.setModel(tabModeRjLengkap);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbRawatJalanLengkap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatJalanLengkap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (int i = 0; i < 9; i++) {
            TableColumn column = tbRawatJalanLengkap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setPreferredWidth(170);
            }
        }
        tbRawatJalanLengkap.setDefaultRenderer(Object.class, new TabelSerahTerimaBerkasKlaim());
        tabModeRi=new DefaultTableModel(null,new Object[]{ "","No Rawat","No SEP","No RM","Nama","Tgl. Masuk","Tgl. Pulang","Pelayanan","SEP","Resume","Billing","Hasil LAB","Hasil RAD","Surat Rujukan","Lembar Observasi > 6 Jam","SPMR","Status Lengkap"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                  boolean a = false;
                if (colIndex==0 || colIndex==7||colIndex==8||colIndex==9 || colIndex==10|| colIndex==11|| colIndex==12|| colIndex==13|| colIndex==14|| colIndex==15) {
                    a=true;
                }
                return a;
              }
              Class[] types = new Class[] {
                 java.lang.Boolean.class,   java.lang.Object.class,     java.lang.Object.class,java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class,    java.lang.Object.class ,    java.lang.Object.class, java.lang.Boolean.class,
                 java.lang.Boolean.class,    java.lang.Boolean.class,     java.lang.Boolean.class,java.lang.Boolean.class,
                 java.lang.Boolean.class,    java.lang.Boolean.class,     java.lang.Boolean.class,java.lang.Boolean.class
                      
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        
        
        tbRawatInap.setModel(tabModeRi);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbRawatInap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatInap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (int i = 0; i < 17; i++) {
            TableColumn column = tbRawatInap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(50);
            }else if(i==16){
                column.setPreferredWidth(100);
            }
        }
        tbRawatInap.setDefaultRenderer(Object.class, new TabelSerahTerimaBerkasKlaim());
        tabModeRiLengkap=new DefaultTableModel(null,new Object[]{ "","No Rawat","No SEP","No RM","Nama","Tgl. Masuk","Tgl. Pulang","Pelayanan","Status"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                  boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
              }
              Class[] types = new Class[] {
                 java.lang.Boolean.class,  java.lang.Object.class, java.lang.Object.class,     java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class,    java.lang.Object.class ,    java.lang.Object.class,java.lang.Object.class
                      
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        
        
        tbRawatInapLengkap.setModel(tabModeRiLengkap);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbRawatInapLengkap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatInapLengkap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (int i = 0; i < 9; i++) {
            TableColumn column = tbRawatInapLengkap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setPreferredWidth(170);
            }
        }
        tbRawatInapLengkap.setDefaultRenderer(Object.class, new TabelSerahTerimaBerkasKlaim());
        
//            dataPasienSIMRS.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {}
//            @Override
//            public void windowClosing(WindowEvent e) {}
//            @Override
//            public void windowClosed(WindowEvent e) {
//               if(dataPasienSIMRS.getTable().getSelectedRow()!= -1){                   
////                     noRawat.setText(dataPasienSIMRS.getTable().getValueAt(dataPasienSIMRS.getTable().getSelectedRow(),1).toString());
//                } 
//            }
//            @Override
//            public void windowIconified(WindowEvent e) {}
//            @Override
//            public void windowDeiconified(WindowEvent e) {}
//            @Override
//            public void windowActivated(WindowEvent e) {}
//            @Override
//            public void windowDeactivated(WindowEvent e) {}
//        });
//             dataPasienSIMRS.getTable().addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {}
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if(akses.getform().equals("VCLMonitoringBerkasKlaim")){
//                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
//                        dataPasienSIMRS.dispose();
//                    }                
//                }
//            }
//            @Override
//            public void keyReleased(KeyEvent e) {}
//        });
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuKemkes = new javax.swing.JPopupMenu();
        mnPilihSemua = new javax.swing.JMenuItem();
        mnBatalPilihSemua = new javax.swing.JMenuItem();
        jPopupMenuBPJS = new javax.swing.JPopupMenu();
        mnPilihSemua1 = new javax.swing.JMenuItem();
        mnBatalPilihSemua1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        tabPane = new widget.TabPane();
        internalFrameRawatJalan = new widget.InternalFrame();
        TableRawatJalan = new widget.ScrollPane();
        tbRawatJalan = new widget.Table();
        internalFrameRawatJalanLengkap = new widget.InternalFrame();
        TableRawatJalanLengkap = new widget.ScrollPane();
        tbRawatJalanLengkap = new widget.Table();
        internalFrameRawatInap = new widget.InternalFrame();
        TableRawatInap = new widget.ScrollPane();
        tbRawatInap = new widget.Table();
        internalFrameRawatInapLengkap = new widget.InternalFrame();
        TableRawatInapLengkap = new widget.ScrollPane();
        tbRawatInapLengkap = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCariAwal = new widget.Tanggal();
        jLabel18 = new widget.Label();
        DTPCariAkhir = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass6 = new widget.panelisi();
        BtnKeluar = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        jLabel11 = new widget.Label();
        LCounSep = new widget.Label();
        jLabel13 = new widget.Label();
        LCountResume = new widget.Label();
        jLabel14 = new widget.Label();
        LCountBilling = new widget.Label();
        jLabel16 = new widget.Label();
        LCountLab = new widget.Label();
        jLabel17 = new widget.Label();
        LCountRad = new widget.Label();

        jPopupMenuKemkes.setName("jPopupMenuKemkes"); // NOI18N

        mnPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        mnPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnPilihSemua.setForeground(new java.awt.Color(70, 70, 70));
        mnPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnPilihSemua.setText("Pilih Semua");
        mnPilihSemua.setName("mnPilihSemua"); // NOI18N
        mnPilihSemua.setPreferredSize(new java.awt.Dimension(290, 26));
        mnPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenuKemkes.add(mnPilihSemua);

        mnBatalPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        mnBatalPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnBatalPilihSemua.setForeground(new java.awt.Color(70, 70, 70));
        mnBatalPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnBatalPilihSemua.setText("Batal Pilih Semua");
        mnBatalPilihSemua.setName("mnBatalPilihSemua"); // NOI18N
        mnBatalPilihSemua.setPreferredSize(new java.awt.Dimension(290, 26));
        mnBatalPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBatalPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenuKemkes.add(mnBatalPilihSemua);

        jPopupMenuBPJS.setName("jPopupMenuBPJS"); // NOI18N

        mnPilihSemua1.setBackground(new java.awt.Color(255, 255, 254));
        mnPilihSemua1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnPilihSemua1.setForeground(new java.awt.Color(70, 70, 70));
        mnPilihSemua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnPilihSemua1.setText("Pilih Semua");
        mnPilihSemua1.setName("mnPilihSemua1"); // NOI18N
        mnPilihSemua1.setPreferredSize(new java.awt.Dimension(290, 26));
        mnPilihSemua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPilihSemua1ActionPerformed(evt);
            }
        });
        jPopupMenuBPJS.add(mnPilihSemua1);

        mnBatalPilihSemua1.setBackground(new java.awt.Color(255, 255, 254));
        mnBatalPilihSemua1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        mnBatalPilihSemua1.setForeground(new java.awt.Color(70, 70, 70));
        mnBatalPilihSemua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        mnBatalPilihSemua1.setText("Batal Pilih Semua");
        mnBatalPilihSemua1.setName("mnBatalPilihSemua1"); // NOI18N
        mnBatalPilihSemua1.setPreferredSize(new java.awt.Dimension(290, 26));
        mnBatalPilihSemua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBatalPilihSemua1ActionPerformed(evt);
            }
        });
        jPopupMenuBPJS.add(mnBatalPilihSemua1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Monitoring Berkas Klaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        tabPane.setName("tabPane"); // NOI18N
        tabPane.setPreferredSize(new java.awt.Dimension(873, 448));
        tabPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPaneMouseClicked(evt);
            }
        });

        internalFrameRawatJalan.setName("internalFrameRawatJalan"); // NOI18N
        internalFrameRawatJalan.setLayout(new java.awt.BorderLayout());

        TableRawatJalan.setName("TableRawatJalan"); // NOI18N
        TableRawatJalan.setOpaque(true);

        tbRawatJalan.setAutoCreateRowSorter(true);
        tbRawatJalan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatJalan.setComponentPopupMenu(jPopupMenuKemkes);
        tbRawatJalan.setName("tbRawatJalan"); // NOI18N
        tbRawatJalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatJalanMouseClicked(evt);
            }
        });
        TableRawatJalan.setViewportView(tbRawatJalan);

        internalFrameRawatJalan.add(TableRawatJalan, java.awt.BorderLayout.CENTER);

        tabPane.addTab("Rawat Jalan [ Belum Lengkap ]", internalFrameRawatJalan);

        internalFrameRawatJalanLengkap.setName("internalFrameRawatJalanLengkap"); // NOI18N
        internalFrameRawatJalanLengkap.setLayout(new java.awt.BorderLayout());

        TableRawatJalanLengkap.setName("TableRawatJalanLengkap"); // NOI18N
        TableRawatJalanLengkap.setOpaque(true);

        tbRawatJalanLengkap.setAutoCreateRowSorter(true);
        tbRawatJalanLengkap.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatJalanLengkap.setComponentPopupMenu(jPopupMenuKemkes);
        tbRawatJalanLengkap.setName("tbRawatJalanLengkap"); // NOI18N
        tbRawatJalanLengkap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatJalanLengkapMouseClicked(evt);
            }
        });
        TableRawatJalanLengkap.setViewportView(tbRawatJalanLengkap);

        internalFrameRawatJalanLengkap.add(TableRawatJalanLengkap, java.awt.BorderLayout.CENTER);

        tabPane.addTab("Rawat Jalan [ Lengkap ]", internalFrameRawatJalanLengkap);

        internalFrameRawatInap.setName("internalFrameRawatInap"); // NOI18N
        internalFrameRawatInap.setLayout(new java.awt.BorderLayout());

        TableRawatInap.setName("TableRawatInap"); // NOI18N
        TableRawatInap.setOpaque(true);

        tbRawatInap.setAutoCreateRowSorter(true);
        tbRawatInap.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatInap.setComponentPopupMenu(jPopupMenuKemkes);
        tbRawatInap.setName("tbRawatInap"); // NOI18N
        tbRawatInap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatInapMouseClicked(evt);
            }
        });
        TableRawatInap.setViewportView(tbRawatInap);

        internalFrameRawatInap.add(TableRawatInap, java.awt.BorderLayout.CENTER);

        tabPane.addTab("Rawat Inap [ Belum Lengkap ]", internalFrameRawatInap);

        internalFrameRawatInapLengkap.setName("internalFrameRawatInapLengkap"); // NOI18N
        internalFrameRawatInapLengkap.setLayout(new java.awt.BorderLayout());

        TableRawatInapLengkap.setName("TableRawatInapLengkap"); // NOI18N
        TableRawatInapLengkap.setOpaque(true);

        tbRawatInapLengkap.setAutoCreateRowSorter(true);
        tbRawatInapLengkap.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatInapLengkap.setComponentPopupMenu(jPopupMenuKemkes);
        tbRawatInapLengkap.setName("tbRawatInapLengkap"); // NOI18N
        tbRawatInapLengkap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatInapLengkapMouseClicked(evt);
            }
        });
        TableRawatInapLengkap.setViewportView(tbRawatInapLengkap);

        internalFrameRawatInapLengkap.add(TableRawatInapLengkap, java.awt.BorderLayout.CENTER);

        tabPane.addTab("Rawat Inap [ Lengkap ]", internalFrameRawatInapLengkap);

        internalFrame1.add(tabPane, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setRequestFocusEnabled(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel15);

        DTPCariAwal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-09-2023" }));
        DTPCariAwal.setDisplayFormat("dd-MM-yyyy");
        DTPCariAwal.setName("DTPCariAwal"); // NOI18N
        DTPCariAwal.setOpaque(false);
        DTPCariAwal.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass9.add(DTPCariAwal);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("s.d");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel18);

        DTPCariAkhir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-09-2023" }));
        DTPCariAkhir.setDisplayFormat("dd-MM-yyyy");
        DTPCariAkhir.setName("DTPCariAkhir"); // NOI18N
        DTPCariAkhir.setOpaque(false);
        DTPCariAkhir.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass9.add(DTPCariAkhir);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnCariMouseClicked(evt);
            }
        });
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

        jPanel2.add(panelGlass9, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass6.add(BtnKeluar);

        jLabel10.setText("Total Data :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(40, 30));
        panelGlass6.add(LCount);

        jLabel11.setText("SEP (-)  :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel11);

        LCounSep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCounSep.setText("0");
        LCounSep.setName("LCounSep"); // NOI18N
        LCounSep.setPreferredSize(new java.awt.Dimension(35, 30));
        panelGlass6.add(LCounSep);

        jLabel13.setText(" Resume (-)  :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel13);

        LCountResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountResume.setText("0");
        LCountResume.setName("LCountResume"); // NOI18N
        LCountResume.setPreferredSize(new java.awt.Dimension(35, 30));
        panelGlass6.add(LCountResume);

        jLabel14.setText("Billing (-)  :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel14);

        LCountBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBilling.setText("0");
        LCountBilling.setName("LCountBilling"); // NOI18N
        LCountBilling.setPreferredSize(new java.awt.Dimension(35, 30));
        panelGlass6.add(LCountBilling);

        jLabel16.setText("Hasil Lab (-) :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 30));
        panelGlass6.add(jLabel16);

        LCountLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountLab.setText("0");
        LCountLab.setName("LCountLab"); // NOI18N
        LCountLab.setPreferredSize(new java.awt.Dimension(35, 30));
        panelGlass6.add(LCountLab);

        jLabel17.setText("Hasil Rad (-)  :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(90, 30));
        panelGlass6.add(jLabel17);

        LCountRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountRad.setText("0");
        LCountRad.setName("LCountRad"); // NOI18N
        LCountRad.setPreferredSize(new java.awt.Dimension(35, 30));
        panelGlass6.add(LCountRad);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
//            dispose();
//        }else{Valid.pindah(evt,btnImportKemkes,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        btnImportKemkes.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void mnPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPilihSemuaActionPerformed
                for(i=0;i<tbRawatJalan.getRowCount();i++){
            tbRawatJalan.setValueAt(true,i,0);
            
        }
//          LCountSelectedImportKemkes.setText(tbRawatJalan.getRowCount()+"");      
    }//GEN-LAST:event_mnPilihSemuaActionPerformed

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
        if(tabPane.getSelectedIndex()==0){
            tampil();
        }if(tabPane.getSelectedIndex()==1){
            tampil2();
        }if(tabPane.getSelectedIndex()==2){
            tampil3();
        }if(tabPane.getSelectedIndex()==3){
            tampil4();
        }
        
        
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
//            BtnCariActionPerformed(null);
//        }else{
//            Valid.pindah(evt, TCari, BtnAll);
//        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void tabPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPaneMouseClicked
     if(tabPane.getSelectedIndex()==0){
          tampil();
        }else if(tabPane.getSelectedIndex()==1){
            tampil2();
        }else if(tabPane.getSelectedIndex()==2){
            tampil3();
        }else if(tabPane.getSelectedIndex()==3){
             tampil4();
        }
    }//GEN-LAST:event_tabPaneMouseClicked

    private void tbRawatJalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatJalanMouseClicked
        if(tabMode.getRowCount()!=0){
            if(evt.getClickCount()==1){
                i=tbRawatJalan.getSelectedColumn();
                if(i==8){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set sep=? where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),8).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Belum Lengkap",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),8).toString(),"false","false","false","false","false","false","false","false"});
                         
                     }
                    
                } else if(i==9){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set resume=? where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),9).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Belum Lengkap","false",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),9).toString(),"false","false","false","false","false","false","false"});
                         
                     }
                     
                } else if(i==10){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set billing=? where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),10).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Belum Lengkap","false","false",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),10).toString(),"false","false","false","false","false","false"});
                         
                     }
                     
                }  else if(i==11){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set hasil_lab=? where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),11).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),11).toString(),"false","false","false","false","false"});
                         
                     }
                     
                }  else if(i==12){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set hasil_rad=? where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),12).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false","false",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),12).toString(),"false","false","false","false"});
                         
                     }
                     
                } else if(i==13){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set surat_rujukan=? where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),13).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false","false","false","false","false",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),13).toString(),"false"});
                         
                     }
                     
                } else if(i==14){
                     i=JOptionPane.showConfirmDialog(null, "Apakah Yakin Sudah Lengkap ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                            if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString())>0)
                     {
                         if(tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),14).toString().equals("true"))
                         {
                           Sequel.queryu2("update ceklis_klaim set lengkap=?,sep='true',resume='true',billing='true',hasil_lab='true',hasil_rad='true',spmr='false',lembar_observasi='false',surat_rujukan='true',stts_lengkap='Lengkap' where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),14).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });  
                         }else{
                             Sequel.queryu2("update ceklis_klaim set lengkap=?,sep='false',resume='false',billing='false',hasil_lab='false',hasil_rad='false',spmr='false',lembar_observasi='false',surat_rujukan='true',stts_lengkap='Belum Lengkap' where no_rawat=?",2,
                        new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),14).toString(),tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString()
                        });
                         }
                         tampil();
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),1).toString(),"Lengkap","true","true","true","true","false","false","true","true",tbRawatJalan.getValueAt(tbRawatJalan.getSelectedRow(),14).toString()});
                         tampil();
                     }
                            }else{
                                 tbRawatJalan.setValueAt(false,tbRawatJalan.getSelectedRow(),14);
//                                tbRawatJalan.setValueAt(tbRawatJalan.getSelectedRow(),1).toString();
                            }
                     
                     
                }              
            }
            
        }
    }//GEN-LAST:event_tbRawatJalanMouseClicked

    private void mnBatalPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBatalPilihSemuaActionPerformed
                for(i=0;i<tbRawatJalan.getRowCount();i++){
            tbRawatJalan.setValueAt(false,i,0);
            
        }
//          LCountSelectedImportKemkes.setText("0"); 
    }//GEN-LAST:event_mnBatalPilihSemuaActionPerformed

    private void mnPilihSemua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPilihSemua1ActionPerformed
//                       for(i=0;i<tbImportDataBpjs.getRowCount();i++){
//            tbImportDataBpjs.setValueAt(true,i,0);
//            
//        }
//          LCountSelectedImportBpjs.setText(tbImportDataBpjs.getRowCount()+""); 
    }//GEN-LAST:event_mnPilihSemua1ActionPerformed

    private void mnBatalPilihSemua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBatalPilihSemua1ActionPerformed
//          for(i=0;i<tbImportDataBpjs.getRowCount();i++){
//            tbImportDataBpjs.setValueAt(false,i,0);
//            
//        }
//          LCountSelectedImportBpjs.setText("0"); 
    }//GEN-LAST:event_mnBatalPilihSemua1ActionPerformed

    private void tbRawatJalanLengkapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatJalanLengkapMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatJalanLengkapMouseClicked

    private void tbRawatInapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatInapMouseClicked
        if(tabModeRi.getRowCount()!=0){
            if(evt.getClickCount()==1){
                i=tbRawatInap.getSelectedColumn();
                if(i==8){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set sep=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),8).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),8).toString(),"false","false","false","false","false","false","false","false"});
                         
                     }
                    
                } else if(i==9){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set resume=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),9).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),9).toString(),"false","false","false","false","false","false","false"});
                         
                     }
                     
                } else if(i==10){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set billing=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),10).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),10).toString(),"false","false","false","false","false","false"});
                         
                     }
                     
                }  else if(i==11){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set hasil_lab=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),11).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),11).toString(),"false","false","false","false","false"});
                         
                     }
                     
                }  else if(i==12){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set hasil_rad=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),12).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),12).toString(),"false","false","false","false"});
                         
                     }
                     
                } else if(i==13){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set surat_rujukan=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),13).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false","false","false","false","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),13).toString(),"false"});
                         
                     }
                     
                }else if(i==14){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set lembar_observasi=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),14).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false","false","false","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),14).toString(),"false","false"});
                         
                     }
                     
                }else if(i==15){
                     if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         Sequel.queryu2("update ceklis_klaim set spmr=? where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),15).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Belum Lengkap","false","false","false","false","false",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),15).toString(),"false","false","false"});
                         
                     }
                     
                }  else if(i==16){
                     i=JOptionPane.showConfirmDialog(null, "Apakah Yakin Sudah Lengkap ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                            if(Sequel.cariInteger("select count(no_rawat) from ceklis_klaim where no_rawat =? ",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString())>0)
                     {
                         if(tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),16).toString().equals("true"))
                         {
                           Sequel.queryu2("update ceklis_klaim set lengkap=?,sep='true',resume='true',billing='true',hasil_lab='true',hasil_rad='true',spmr='true',lembar_observasi='true',surat_rujukan='true',stts_lengkap='Lengkap' where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),16).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });  
                         }else{
                             Sequel.queryu2("update ceklis_klaim set lengkap=?,sep='false',resume='false',billing='false',hasil_lab='false',hasil_rad='false',spmr='true',lembar_observasi='true',surat_rujukan='true',stts_lengkap='Belum Lengkap' where no_rawat=?",2,
                        new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),16).toString(),tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString()
                        });
                         }
                         
                            tampil3();
                    }else{
                         Sequel.menyimpantf2("ceklis_klaim","?,?,?,?,?,?,?,?,?,?,?","No.Rawat",11,
                new String[]{tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),1).toString(),"Lengkap","true","true","true","true","true","true","true","true",tbRawatInap.getValueAt(tbRawatInap.getSelectedRow(),15).toString()});
                         tampil3();
                     }
                            }else{
                                 tbRawatInap.setValueAt(false,tbRawatInap.getSelectedRow(),15);
//                                tbRawatInap.setValueAt(tbRawatInap.getSelectedRow(),1).toString();
                            }
                     
                     
                }              
            }
            
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatInapMouseClicked

    private void tbRawatInapLengkapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatInapLengkapMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatInapLengkapMouseClicked

    private void BtnCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnCariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            VCLMonitoringBerkasKlaimLokal dialog = new VCLMonitoringBerkasKlaimLokal(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Tanggal DTPCariAkhir;
    private widget.Tanggal DTPCariAwal;
    private widget.Label LCounSep;
    private widget.Label LCount;
    private widget.Label LCountBilling;
    private widget.Label LCountLab;
    private widget.Label LCountRad;
    private widget.Label LCountResume;
    private widget.TextBox TCari;
    private widget.ScrollPane TableRawatInap;
    private widget.ScrollPane TableRawatInapLengkap;
    private widget.ScrollPane TableRawatJalan;
    private widget.ScrollPane TableRawatJalanLengkap;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrameRawatInap;
    private widget.InternalFrame internalFrameRawatInapLengkap;
    private widget.InternalFrame internalFrameRawatJalan;
    private widget.InternalFrame internalFrameRawatJalanLengkap;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenuBPJS;
    private javax.swing.JPopupMenu jPopupMenuKemkes;
    private javax.swing.JMenuItem mnBatalPilihSemua;
    private javax.swing.JMenuItem mnBatalPilihSemua1;
    private javax.swing.JMenuItem mnPilihSemua;
    private javax.swing.JMenuItem mnPilihSemua1;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass9;
    private widget.TabPane tabPane;
    private widget.Table tbRawatInap;
    private widget.Table tbRawatInapLengkap;
    private widget.Table tbRawatJalan;
    private widget.Table tbRawatJalanLengkap;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement("select nota_jalan.no_rawat,pasien.nm_pasien,nota_jalan.*,reg_periksa.kd_pj,reg_periksa.tgl_registrasi,poliklinik.nm_poli,ceklis_klaim.*,reg_periksa.no_rkm_medis from nota_jalan "
                    + "JOIN reg_periksa ON nota_jalan.no_rawat=reg_periksa.no_rawat JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli left JOIN ceklis_klaim ON reg_periksa.no_rawat=ceklis_klaim.no_rawat where tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and reg_periksa.no_rkm_medis like ? or "
                    + " tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and reg_periksa.no_rawat like ? "); 
            try{  
                ps.setString(1,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                
                
                rs=ps.executeQuery();
                k=0;l=0;m=0;n=0;j=0;
                while(rs.next()){
                    String no_sep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat ='"+rs.getString("no_rawat")+"'");
                    if( rs.getString("stts_lengkap")==null){
                       tabMode.addRow(new Object[] {false,  rs.getString("no_rawat"), no_sep, rs.getString("no_rkm_medis"), rs.getString(2), rs.getString("tgl_registrasi"),
                         rs.getString("tanggal"), rs.getString("nm_poli"), rs.getBoolean("sep"), rs.getBoolean("resume"), rs.getBoolean("billing"),rs.getBoolean("hasil_lab"),rs.getBoolean("hasil_rad"),rs.getBoolean("surat_rujukan"),rs.getBoolean("lembar_observasi"),rs.getBoolean("spmr"),rs.getBoolean("lengkap")
                    });
                     if(rs.getString("sep")==null || rs.getString("sep").equals("false")){
                        k=k+1;
                    }if(rs.getString("resume")==null || rs.getString("resume").equals("false")){
                        l=l+1;
                    }if(rs.getString("billing")==null || rs.getString("billing").equals("false")){
                        m=m+1;
                    }if(rs.getString("hasil_lab")==null || rs.getString("hasil_lab").equals("false")){
                        n=n+1;
                    }if(rs.getString("hasil_rad")==null || rs.getString("hasil_rad").equals("false")){
                        j=j+1;
                    }
                         
                    }else if(rs.getString("stts_lengkap").equals("Belum Lengkap")){
                       tabMode.addRow(new Object[] {false,  rs.getString("no_rawat"), no_sep, rs.getString("no_rkm_medis") ,rs.getString(2), rs.getString("tgl_registrasi"),
                         rs.getString("tanggal"), rs.getString("nm_poli"), rs.getBoolean("sep"), rs.getBoolean("resume"), rs.getBoolean("billing"),rs.getBoolean("hasil_lab"),rs.getBoolean("hasil_rad"),rs.getBoolean("surat_rujukan"),rs.getBoolean("lengkap")
                    });
                       if(rs.getString("sep")==null || rs.getString("sep").equals("false")){
                      
                        k=k+1;
                    }if(rs.getString("resume")==null || rs.getString("resume").equals("false")){
                        l=l+1;
                    }if(rs.getString("billing")==null || rs.getString("billing").equals("false")){
                        m=m+1;
                    }if(rs.getString("hasil_lab")==null || rs.getString("hasil_lab").equals("false")){
                        n=n+1;
                    }if(rs.getString("hasil_rad")==null || rs.getString("hasil_rad").equals("false")){
                        j=j+1;
                    }
                    }
                }                    
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
        LCountBilling.setText(m+"");
        LCountLab.setText(n+"");
        LCountRad.setText(j+"");
        LCountResume.setText(l+"");
        LCounSep.setText(k+"");
    } 
    public void tampil2() {
        Valid.tabelKosong(tabModeRjLengkap);
        try {
            ps=koneksi.prepareStatement("select nota_jalan.no_rawat,pasien.nm_pasien,nota_jalan.*,reg_periksa.kd_pj,reg_periksa.tgl_registrasi,poliklinik.nm_poli,ceklis_klaim.*,reg_periksa.no_rkm_medis from nota_jalan "
                    + "JOIN reg_periksa ON nota_jalan.no_rawat=reg_periksa.no_rawat JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli left JOIN ceklis_klaim ON reg_periksa.no_rawat=ceklis_klaim.no_rawat where tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and stts_lengkap='Lengkap' and reg_periksa.no_rkm_medis like ? or "
                    + " tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and stts_lengkap='Lengkap' and reg_periksa.no_rawat like ?  "); 
            try{  
                 ps.setString(1,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                
                rs=ps.executeQuery();
                k=0;l=0;m=0;n=0;j=0;
                while(rs.next()){
                    String no_sep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat ='"+rs.getString("no_rawat")+"'");
                   
                       tabModeRjLengkap.addRow(new Object[] {false,  rs.getString("no_rawat"), no_sep, rs.getString("no_rkm_medis"), rs.getString(2), rs.getString("tgl_registrasi"),
                         rs.getString("tanggal"), rs.getString("nm_poli"), rs.getString("stts_lengkap")
                    
                    });
                       k=k+1;l=l+1;m=m+1;n=n+1;j=j+1;
                }                    
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeRjLengkap.getRowCount());
        LCountBilling.setText(m+"");
        LCountLab.setText(n+"");
        LCountRad.setText(j+"");
        LCountResume.setText(l+"");
        LCounSep.setText(k+"");
    } 
   public void tampil3() {
        Valid.tabelKosong(tabModeRi);
        try {
            ps=koneksi.prepareStatement("select nota_inap.no_rawat,pasien.nm_pasien,nota_inap.*,reg_periksa.kd_pj,reg_periksa.tgl_registrasi,poliklinik.nm_poli,ceklis_klaim.*,reg_periksa.no_rkm_medis from nota_inap "
                    + "JOIN reg_periksa ON nota_inap.no_rawat=reg_periksa.no_rawat JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli left JOIN ceklis_klaim ON reg_periksa.no_rawat=ceklis_klaim.no_rawat where tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and reg_periksa.no_rkm_medis like ? or "
                    + " tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and reg_periksa.no_rawat like ?  "); 
            try{  
               ps.setString(1,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                
                rs=ps.executeQuery();
                k=0;l=0;m=0;n=0;j=0;
                while(rs.next()){
                    String no_sep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat ='"+rs.getString("no_rawat")+"'");
                    if( rs.getString("stts_lengkap")==null){
                       tabModeRi.addRow(new Object[] {false,  rs.getString("no_rawat"), no_sep, rs.getString("no_rkm_medis"), rs.getString(2), rs.getString("tgl_registrasi"),
                         rs.getString("tanggal"), rs.getString("nm_poli"), rs.getBoolean("sep"), rs.getBoolean("resume"), rs.getBoolean("billing"),rs.getBoolean("hasil_lab"),rs.getBoolean("hasil_rad"),rs.getBoolean("surat_rujukan"),rs.getBoolean("lembar_observasi"),rs.getBoolean("spmr"),rs.getBoolean("lengkap")
                    });
                     if(rs.getString("sep")==null || rs.getString("sep").equals("false")){
                        k=k+1;
                    }if(rs.getString("resume")==null || rs.getString("resume").equals("false")){
                        l=l+1;
                    }if(rs.getString("billing")==null || rs.getString("billing").equals("false")){
                        m=m+1;
                    }if(rs.getString("hasil_lab")==null || rs.getString("hasil_lab").equals("false")){
                        n=n+1;
                    }if(rs.getString("hasil_rad")==null || rs.getString("hasil_rad").equals("false")){
                        j=j+1;
                    }
                         
                    }else if(rs.getString("stts_lengkap").equals("Belum Lengkap")){
                       tabModeRi.addRow(new Object[] {false,  rs.getString("no_rawat"), no_sep, rs.getString("no_rkm_medis"), rs.getString(2), rs.getString("tgl_registrasi"),
                         rs.getString("tanggal"), rs.getString("nm_poli"), rs.getBoolean("sep"), rs.getBoolean("resume"), rs.getBoolean("billing"),rs.getBoolean("hasil_lab"),rs.getBoolean("hasil_rad"),rs.getBoolean("surat_rujukan"),rs.getBoolean("lembar_observasi"),rs.getBoolean("spmr"),rs.getBoolean("lengkap")
                    });
                       if(rs.getString("sep")==null || rs.getString("sep").equals("false")){
                      
                        k=k+1;
                    }if(rs.getString("resume")==null || rs.getString("resume").equals("false")){
                        l=l+1;
                    }if(rs.getString("billing")==null || rs.getString("billing").equals("false")){
                        m=m+1;
                    }if(rs.getString("hasil_lab")==null || rs.getString("hasil_lab").equals("false")){
                        n=n+1;
                    }if(rs.getString("hasil_rad")==null || rs.getString("hasil_rad").equals("false")){
                        j=j+1;
                    }
                    }
                }                    
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeRi.getRowCount());
        LCountBilling.setText(m+"");
        LCountLab.setText(n+"");
        LCountRad.setText(j+"");
        LCountResume.setText(l+"");
        LCounSep.setText(k+"");
    } 
    public void tampil4() {
        Valid.tabelKosong(tabModeRiLengkap);
        try {
            ps=koneksi.prepareStatement("select nota_inap.no_rawat,pasien.nm_pasien,nota_inap.*,reg_periksa.kd_pj,reg_periksa.tgl_registrasi,poliklinik.nm_poli,ceklis_klaim.*,reg_periksa.no_rkm_medis from nota_inap "
                    + "JOIN reg_periksa ON nota_inap.no_rawat=reg_periksa.no_rawat JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli left JOIN ceklis_klaim ON reg_periksa.no_rawat=ceklis_klaim.no_rawat where tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and stts_lengkap='Lengkap' and reg_periksa.no_rkm_medis like ? or "
                    + " tanggal between ? and ?  and  reg_periksa.kd_pj='C02' and stts_lengkap='Lengkap' and reg_periksa.no_rawat like ?  "); 
            try{  
                ps.setString(1,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCariAwal.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCariAkhir.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                
                rs=ps.executeQuery();
                k=0;l=0;m=0;n=0;j=0;
                while(rs.next()){
                    String no_sep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat ='"+rs.getString("no_rawat")+"'");
                   
                       tabModeRiLengkap.addRow(new Object[] {false,  rs.getString("no_rawat"), no_sep, rs.getString("no_rkm_medis"), rs.getString(2), rs.getString("tgl_registrasi"),
                         rs.getString("tanggal"), rs.getString("nm_poli"), rs.getString("stts_lengkap")
                    
                    });
                       k=k+1;l=l+1;m=m+1;n=n+1;j=j+1;
                }                    
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeRiLengkap.getRowCount());
        LCountBilling.setText(m+"");
        LCountLab.setText(n+"");
        LCountRad.setText(j+"");
        LCountResume.setText(l+"");
        LCounSep.setText(k+"");
    } 
     private void startShortingDate()
    {
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM");
        Date tanggal = new Date();
        Valid.SetTgl(DTPCariAwal, sdfdate.format(tanggal).toString()+"-01");
    }
    private void getData() {
//        if(tbDataMonitoringKlaim.getSelectedRow()!= -1){      
//            noIdentitas.setText(tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),2).toString());
//            nmPasien.setText(tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),4).toString());
//            tglMasuk.setText(tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),5).toString());
//            tglKeluar.setText(tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),6).toString());
//            uid.setText(tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),9).toString());
//           if(!tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),1).equals("")){
//                       noRawat.setText(tbDataMonitoringKlaim.getValueAt(tbDataMonitoringKlaim.getSelectedRow(),1).toString());
//           }
//            
//            
//        }
    }
   
   public void simpanBridge() { 
//         if(nmPasien.getText().trim().equals("")){
//            Valid.textKosong(nmPasien,"Nama Pasien");
//         }
//         else{
//             
//         Sequel.queryu2("update tt_umpanbalik set no_rawat=? where uid=?  ",2,
//                        new String[]{noRawat.getText(),uid.getText()});
//         }
//          emptTeks(); 
//          if(tabPane.getSelectedIndex()==0){
//            emptTeks();
////            tampil();
//        }else if(tabPane.getSelectedIndex()==1){
//            emptTeks();
//            tampil1();
//        }else if(tabPane.getSelectedIndex()==2){
//            emptTeks();
//            
//        }
          
         
     }
   public void importDataKemkes() { 
       for(i=0;i<tbRawatJalan.getRowCount();i++){ 
            if(tbRawatJalan.getValueAt(i,0).toString().equals("true")){
//                Sequel.queryu4("insert into tt_umpanbalik values(?,?,?,?,?,?,?,?,?,?,?,?,?)",13,new String[]{null,tbRawatJalan.getValueAt(i,1).toString(),tbRawatJalan.getValueAt(i,2).toString(),"","",tbRawatJalan.getValueAt(i,3).toString(),tbRawatJalan.getValueAt(i,4).toString(),tbRawatJalan.getValueAt(i,5).toString(),"C19",tbRawatJalan.getValueAt(i,6).toString(),tbRawatJalan.getValueAt(i,7).toString(),tbRawatJalan.getValueAt(i,8).toString(),tahapKlaimKemkes.getText()});
                
            }
           
       }
          JOptionPane.showMessageDialog(null,"Data Berhasil di Import");
     }
   public void emptTeks() {

//        noIdentitas.setText("");
//        nmPasien.setText("");
//        noRawat.setText("");
//        tglMasuk.setText("");
//        tglKeluar.setText("");
//        uid.setText("");
        
    }
 
}
