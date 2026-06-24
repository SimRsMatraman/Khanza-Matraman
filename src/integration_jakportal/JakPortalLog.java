package integration_jakportal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.WarnaTableJakPro;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import javax.swing.JCheckBox;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.Arrays;

/**
 *
 * @author dosen
 */
public final class JakPortalLog extends javax.swing.JDialog {

    private final DefaultTableModel tabModeKunjungan, tabModeLog, tabModeResep, tabModeResepJourney, tabModeLaboratorium, tabModeLaboratoriumJourney, tabModeRadiologi, tabModeRadiologiJourney;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
//    private Connection koneksi_log = koneksiDB.condb_log();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, j = 0, iSuccess = 0, iFailed = 0;
    private SimpleDateFormat dateReg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat secondTime = new SimpleDateFormat("ss");
    private String nomorReferensi = "", jeniskunjungan = "", SuratKontrol = "", nomorRujukan = "", poliRujukan = "", noRujukan = "", jumlahSep = "", URL = "", datajam = "", utc = "", requestJson = "", keterangan = "";
    private SimpleDateFormat dateFormatMulai = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date parsedDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+07:00'");
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ApiIntegrationJakPortal api = new ApiIntegrationJakPortal();
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private boolean resepSelected = false;
    private boolean labSelected = false;
    private boolean radSelected = false;
    boolean pilihSemuaResep = false;
    boolean pilihSemuaLab = false;
    boolean pilihSemuaRad = false;
//    private BackgroundWorker bc = new BackgroundWorker();
    int a = 0, b = 0;

    /**
     * Creates new form DlgJnsPerawatanRalan
     *
     * @param parent
     * @param modal
     */
    public JakPortalLog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(1308, 674);
//        

//        tabModeKunjungan = new DefaultTableModel(null, new Object[]{
//            "P", "Date Time", "No Rawat", "Check In", "Check In End", "Nurse Station", "Nurse Station End", "Poliklinik", "Poliklinik End", "Resep Permintaan", "Check Out"
//        }) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                boolean a = false;
//                if (colIndex == 0) {
//                    a = true;
//                }
//                return a;
//            }
//            Class[] types = new Class[]{
//                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
//                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
//                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
//                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
//            };
//
//            @Override
//            public Class getColumnClass(int columnIndex) {
//                return types[columnIndex];
//            }
//        };

tabModeKunjungan = new DefaultTableModel(null, new Object[]{
    "P", "Date Time", "No Rawat", "No Resep", "No Lab", "No Radiologi",
    "Status Journey",
    "Check In", "Check In End", "Nurse Station", "Nurse Station End",
    "Poliklinik", "Poliklinik End", "Check Out"
}) {
    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return colIndex == 0;
    }

    Class[] types = new Class[]{
        Boolean.class, Object.class, Object.class, Object.class, Object.class, Object.class,
        Object.class,
        Object.class, Object.class, Object.class, Object.class,
        Object.class, Object.class, Object.class
    };

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }
};

tbKunjungan.setModel(tabModeKunjungan);

// ================== SET TAMPILAN ==================
tbKunjungan.setPreferredScrollableViewportSize(new Dimension(500, 500));
tbKunjungan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// ================== SET LEBAR KOLOM ==================
for (int i = 0; i < 14; i++) {
    TableColumn column = tbKunjungan.getColumnModel().getColumn(i);

    if (i == 0) {
        column.setPreferredWidth(30); // checkbox
    } else if (i == 1) {
        column.setPreferredWidth(130); // datetime
    } else if (i == 2) {
        column.setPreferredWidth(130); // no rawat
    } else if (i == 3 || i == 4 || i == 5) {
        column.setPreferredWidth(150); // resep, lab, rad
    } else if (i == 6) {
        column.setPreferredWidth(180); // 🔥 status journey
    } else {
        column.setPreferredWidth(200); // sisanya
    }
}

// ================== RENDERER WARNA ==================
tbKunjungan.setDefaultRenderer(Object.class, new WarnaTableJakPro());

// ================== SELECT ALL HEADER ==================
tbKunjungan.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int col = tbKunjungan.columnAtPoint(evt.getPoint());

        if (col == 0) {
            boolean semuaTerpilih = true;

            for (int i = 0; i < tbKunjungan.getRowCount(); i++) {

                Object val = tbKunjungan.getValueAt(i, 6);
                String status = (val == null) ? "" : val.toString().trim();

                if(status.equalsIgnoreCase("Batal")){
                    continue;
                }

                if (!(Boolean) tbKunjungan.getValueAt(i, 0)) {
                    semuaTerpilih = false;
                    break;
                }
            }

            pilihSemuaKunjungan(!semuaTerpilih);
        }
    }
});

        tabModeLog = new DefaultTableModel(null, new Object[]{
            "Code", "Date Time", "User", "No Rawat", "Request", "Response"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbLog.setModel(tabModeLog);

        tbLog.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbLog.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 6; i++) {
            TableColumn column = tbLog.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else if (i == 1) {
                column.setPreferredWidth(120);
            } else if (i == 2) {
                column.setPreferredWidth(100);
            } else if (i == 3) {
                column.setPreferredWidth(105);
            } else if (i == 4) {
                column.setPreferredWidth(400);
            } else if (i == 5) {
                column.setPreferredWidth(400);
            }
        }
        tbLog.setDefaultRenderer(Object.class, new WarnaTableJakPro());

//        tabModeResep = new DefaultTableModel(null, new Object[]{
//            "No Rawat", "Check In"}) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                return false;
//            }
//        };
//        tbPaneDaftarResep.setModel(tabModeResep);
//
//        tbPaneDaftarResep.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbPaneDaftarResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 2; i++) {
//            TableColumn column = tbPaneDaftarResep.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(250);
//            } else if (i == 1) {
//                column.setPreferredWidth(250);
//            }
//        }
//        tbPaneDaftarResep.setDefaultRenderer(Object.class, new WarnaTableJakPro());

tabModeResep = new DefaultTableModel(null, new Object[]{
    "P", "No Rawat", "Check In", "Permintaan", "Validasi", "Penyerahan"
}) {
    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return colIndex == 0;
    }

    Class[] types = new Class[]{
        Boolean.class, Object.class, Object.class, Object.class, Object.class, Object.class
    };

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }
};

tbPaneDaftarResep.setModel(tabModeResep);

tbPaneDaftarResep.setPreferredScrollableViewportSize(new Dimension(500, 500));
tbPaneDaftarResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

for (i = 0; i < tbPaneDaftarResep.getColumnCount(); i++) {
    TableColumn column = tbPaneDaftarResep.getColumnModel().getColumn(i);

    if (i == 0) {
        column.setPreferredWidth(20);
    } else if (i == 1) {
        column.setPreferredWidth(120);
    } else {
        column.setPreferredWidth(250);
    }
}

tbPaneDaftarResep.setDefaultRenderer(Object.class, new WarnaTableJakPro());
tbPaneDaftarResep.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int col = tbPaneDaftarResep.columnAtPoint(e.getPoint());

        if(col == 0){ // kolom P

            pilihSemuaResep = !pilihSemuaResep;

            for(int i=0;i<tbPaneDaftarResep.getRowCount();i++){
                tbPaneDaftarResep.setValueAt(pilihSemuaResep,i,0);
            }

        }

    }
});

        tabModeResepJourney = new DefaultTableModel(null, new Object[]{
            "P", "No Rawat", "Check In", "Permintaan", "Validasi", "Penyerahan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

//        tbPaneJourneyResep.setModel(tabModeResepJourney);
//
//        tbPaneJourneyResep.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbPaneJourneyResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 6; i++) {
//            TableColumn column = tbPaneJourneyResep.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(20);
//            } else if (i == 1) {
//                column.setPreferredWidth(120);
//            } else {
//                column.setPreferredWidth(250);
//            }
//        }
//        tbPaneJourneyResep.setDefaultRenderer(Object.class, new WarnaTableJakPro());

//        tabModeLaboratorium = new DefaultTableModel(null, new Object[]{
//            "No Rawat", "Check In"}) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                return false;
//            }
//        };
//        tbPaneDaftarLaboratorium.setModel(tabModeLaboratorium);
//
//        tbPaneDaftarLaboratorium.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbPaneDaftarLaboratorium.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 2; i++) {
//            TableColumn column = tbPaneDaftarLaboratorium.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(250);
//            } else if (i == 1) {
//                column.setPreferredWidth(250);
//            }
//        }
//        tbPaneDaftarLaboratorium.setDefaultRenderer(Object.class, new WarnaTableJakPro());

tabModeLaboratorium = new DefaultTableModel(null, new Object[]{
            "P", "No Rawat", "Check In", "Permintaan", "Sampel", "Hasil"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbPaneDaftarLaboratorium.setModel(tabModeLaboratorium);

        tbPaneDaftarLaboratorium.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPaneDaftarLaboratorium.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 6; i++) {
            TableColumn column = tbPaneDaftarLaboratorium.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(120);
            } else {
                column.setPreferredWidth(250);
            }
        }
        tbPaneDaftarLaboratorium.setDefaultRenderer(Object.class, new WarnaTableJakPro());
        tbPaneDaftarLaboratorium.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int col = tbPaneDaftarLaboratorium.columnAtPoint(e.getPoint());

        if (col == 0) { // kolom P

            pilihSemuaLab = !pilihSemuaLab;

            for (int i = 0; i < tbPaneDaftarLaboratorium.getRowCount(); i++) {
                tbPaneDaftarLaboratorium.setValueAt(pilihSemuaLab, i, 0);
            }

        }
    }
});

        tabModeLaboratoriumJourney = new DefaultTableModel(null, new Object[]{
            "P", "No Rawat", "Check In", "Permintaan", "Sampel", "Hasil"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

//        tbPaneJourneyLaboratorium.setModel(tabModeLaboratoriumJourney);
//
//        tbPaneJourneyLaboratorium.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbPaneJourneyLaboratorium.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 6; i++) {
//            TableColumn column = tbPaneJourneyLaboratorium.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(20);
//            } else if (i == 1) {
//                column.setPreferredWidth(120);
//            } else {
//                column.setPreferredWidth(250);
//            }
//        }
//        tbPaneJourneyLaboratorium.setDefaultRenderer(Object.class, new WarnaTableJakPro());

//        tabModeRadiologi = new DefaultTableModel(null, new Object[]{
//            "No Rawat", "Check In"}) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                return false;
//            }
//        };
//        tbPaneDaftarRadiologi.setModel(tabModeRadiologi);
//
//        tbPaneDaftarRadiologi.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbPaneDaftarRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 2; i++) {
//            TableColumn column = tbPaneDaftarRadiologi.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(250);
//            } else if (i == 1) {
//                column.setPreferredWidth(250);
//            }
//        }
//        tbPaneDaftarRadiologi.setDefaultRenderer(Object.class, new WarnaTableJakPro());

tabModeRadiologi = new DefaultTableModel(null, new Object[]{
            "P", "No Rawat", "Check In", "Permintaan", "Sampel", "Hasil"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbPaneDaftarRadiologi.setModel(tabModeRadiologi);

        tbPaneDaftarRadiologi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPaneDaftarRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 6; i++) {
            TableColumn column = tbPaneDaftarRadiologi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(120);
            } else {
                column.setPreferredWidth(250);
            }
        }
        tbPaneDaftarRadiologi.setDefaultRenderer(Object.class, new WarnaTableJakPro());
        tbPaneDaftarRadiologi.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int col = tbPaneDaftarRadiologi.columnAtPoint(e.getPoint());

        if (col == 0) { // kolom P

            pilihSemuaRad = !pilihSemuaRad;

            for (int i = 0; i < tbPaneDaftarRadiologi.getRowCount(); i++) {
                tbPaneDaftarRadiologi.setValueAt(pilihSemuaRad, i, 0);
            }

        }
    }
});

        tabModeRadiologiJourney = new DefaultTableModel(null, new Object[]{
            "P", "No Rawat", "Check In", "Permintaan", "Sampel", "Hasil"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

//        tbPaneJourneyRadiologi.setModel(tabModeRadiologiJourney);
//
//        tbPaneJourneyRadiologi.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbPaneJourneyRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        for (i = 0; i < 6; i++) {
//            TableColumn column = tbPaneJourneyRadiologi.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(20);
//            } else if (i == 1) {
//                column.setPreferredWidth(120);
//            } else {
//                column.setPreferredWidth(250);
//            }
//        }
//        tbPaneJourneyRadiologi.setDefaultRenderer(Object.class, new WarnaTableJakPro());
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

    }

private String safeString(Object obj){
    return obj == null ? "" : obj.toString();
}

    public class loadingProgress implements Runnable {

        @Override
        public void run() {
            DlgLoading.setSize(550, 151);
            DlgLoading.setLocationRelativeTo(internalFrame1);
            DlgLoading.setVisible(true);
//           JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");  
        }

    }
    
     private boolean kosong(String val){
    return val==null || val.trim().equals("");
}
     
     private void delay(int ms){
    try{
        Thread.sleep(ms);
    }catch(Exception e){}
}
    
    private void prosesJakportalStabil(String noRawat){

try{

String tglReg = Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+noRawat+"'");
String jamReg = Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+noRawat+"'");

String idcheckin = Sequel.cariIsi("select id_checkin from jakportal_patientjourney where no_rawat='"+noRawat+"'");

// CHECKIN
if(kosong(idcheckin)){

CheckIn(noRawat,tglReg,jamReg,"","","","","","","","","","08:00:00","12:00:00","P002");

delay(300);

idcheckin = Sequel.cariIsi("select id_checkin from jakportal_patientjourney where no_rawat='"+noRawat+"'");

}

// CHECKIN END
String idcheckinend = Sequel.cariIsi("select id_checkin_end from jakportal_patientjourney where no_rawat='"+noRawat+"'");

if(kosong(idcheckinend)){

CheckInEnd(noRawat,idcheckin,tglReg,jamReg);

delay(300);

}

// NS
String idns = Sequel.cariIsi("select id_ns from jakportal_patientjourney where no_rawat='"+noRawat+"'");

if(kosong(idns)){

NurseStation(noRawat,idcheckin,tglReg,jamReg);

delay(300);

idns = Sequel.cariIsi("select id_ns from jakportal_patientjourney where no_rawat='"+noRawat+"'");

}

// NS END
String idnsend = Sequel.cariIsi("select id_ns_end from jakportal_patientjourney where no_rawat='"+noRawat+"'");

if(kosong(idnsend)){

NurseStationEnd(noRawat,idcheckin,idns,tglReg,jamReg);

delay(300);

}

// POLI
String idpoli = Sequel.cariIsi("select id_poli from jakportal_patientjourney where no_rawat='"+noRawat+"'");

if(kosong(idpoli)){

Poliklinik(noRawat,idcheckin,tglReg,jamReg);

delay(300);

idpoli = Sequel.cariIsi("select id_poli from jakportal_patientjourney where no_rawat='"+noRawat+"'");

}

// POLI END
String idpoliend = Sequel.cariIsi("select id_poli_end from jakportal_patientjourney where no_rawat='"+noRawat+"'");

if(kosong(idpoliend)){

PoliklinikEnd(noRawat,idcheckin,idpoli,tglReg,jamReg);

delay(300);

}

// CHECKOUT
String idcheckout = Sequel.cariIsi("select id_check_out from jakportal_patientjourney where no_rawat='"+noRawat+"'");

String idnsend2 = Sequel.cariIsi("select id_ns_end from jakportal_patientjourney where no_rawat='"+noRawat+"'");
String idpoliend2 = Sequel.cariIsi("select id_poli_end from jakportal_patientjourney where no_rawat='"+noRawat+"'");

if(kosong(idcheckout)){

CheckOut(noRawat,idcheckin,idnsend2,idpoliend2,tglReg,jamReg);

delay(300);

}

}catch(Exception e){

System.out.println("Error Jakportal : "+e);

}

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
        ppSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        DlgLoading = new javax.swing.JDialog();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        jLabel8 = new widget.Label();
        LCountSuccess = new widget.Label();
        jLabel9 = new widget.Label();
        LCountFailed = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnPrint1 = new widget.Button();
        BtnKeluar1 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        BtnKeluar3 = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatusJakportal = new javax.swing.JComboBox<>();
        jLabel11 = new widget.Label();
        cmbTask = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        tbPaneKunjungan = new widget.ScrollPane();
        tbKunjungan = new widget.Table();
        TabPenunjang = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        internalFrame3 = new widget.InternalFrame();
        PaneDaftarResep = new widget.ScrollPane();
        tbPaneDaftarResep = new widget.Table();
        panelGlass13 = new widget.panelisi();
        BtnPrint6 = new widget.Button();
        internalFrame5 = new widget.InternalFrame();
        internalFrame6 = new widget.InternalFrame();
        PaneDaftarLaboratorium = new widget.ScrollPane();
        tbPaneDaftarLaboratorium = new widget.Table();
        panelGlass14 = new widget.panelisi();
        BtnPrint7 = new widget.Button();
        internalFrame8 = new widget.InternalFrame();
        internalFrame9 = new widget.InternalFrame();
        PaneDaftarRadiologi = new widget.ScrollPane();
        tbPaneDaftarRadiologi = new widget.Table();
        panelGlass15 = new widget.panelisi();
        BtnPrint8 = new widget.Button();
        tbPaneLog = new widget.ScrollPane();
        tbLog = new widget.Table();

        Popup.setName("Popup"); // NOI18N

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(70, 70, 70));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        DlgLoading.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgLoading.setName("DlgLoading"); // NOI18N
        DlgLoading.setUndecorated(true);
        DlgLoading.setResizable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Log Journey JakPortal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(52, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);

        jLabel8.setText("Success :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(52, 23));
        panelGlass8.add(jLabel8);

        LCountSuccess.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountSuccess.setText("0");
        LCountSuccess.setName("LCountSuccess"); // NOI18N
        LCountSuccess.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCountSuccess);

        jLabel9.setText("Failed :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(52, 23));
        panelGlass8.add(jLabel9);

        LCountFailed.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountFailed.setText("0");
        LCountFailed.setName("LCountFailed"); // NOI18N
        LCountFailed.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCountFailed);

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

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Posting");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint1);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Ceklis Resep");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(130, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar1);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Ceklis Lab");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(130, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        BtnKeluar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar2KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar2);

        BtnKeluar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnKeluar3.setMnemonic('K');
        BtnKeluar3.setText("Ceklis Rad");
        BtnKeluar3.setToolTipText("Alt+K");
        BtnKeluar3.setName("BtnKeluar3"); // NOI18N
        BtnKeluar3.setPreferredSize(new java.awt.Dimension(130, 30));
        BtnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar3ActionPerformed(evt);
            }
        });
        BtnKeluar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar3KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar3);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-04-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-04-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari2);

        jLabel12.setText("Status JakPortal :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(jLabel12);

        cmbStatusJakportal.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        cmbStatusJakportal.setForeground(new java.awt.Color(50, 50, 50));
        cmbStatusJakportal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Belum Checkin", "Checkin", "Checkin Selesai", "Nurse Station", "Nurse Station Selesai", "Poliklinik", "Poliklinik Selesai", "Check Out", "Batal" }));
        cmbStatusJakportal.setName("cmbStatusJakportal"); // NOI18N
        cmbStatusJakportal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusJakportalItemStateChanged(evt);
            }
        });
        cmbStatusJakportal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusJakportalActionPerformed(evt);
            }
        });
        panelGlass10.add(cmbStatusJakportal);

        jLabel11.setText("Task :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(40, 23));
        panelGlass10.add(jLabel11);

        cmbTask.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Check IN", "Nurse Station", "Poliklinik", "Resep Obat", "Laboratorium", "Radiologi", "Check Out", "Batal" }));
        cmbTask.setName("cmbTask"); // NOI18N
        cmbTask.setPreferredSize(new java.awt.Dimension(100, 20));
        cmbTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTaskActionPerformed(evt);
            }
        });
        cmbTask.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbTaskKeyPressed(evt);
            }
        });
        panelGlass10.add(cmbTask);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

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
        panelGlass10.add(BtnCari);

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
        panelGlass10.add(BtnAll);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        tbPaneKunjungan.setName("tbPaneKunjungan"); // NOI18N
        tbPaneKunjungan.setOpaque(true);

        tbKunjungan.setAutoCreateRowSorter(true);
        tbKunjungan.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbKunjungan.setName("tbKunjungan"); // NOI18N
        tbKunjungan.getTableHeader().setReorderingAllowed(false);
        tbKunjungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKunjunganMouseClicked(evt);
            }
        });
        tbKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKunjunganKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKunjunganKeyReleased(evt);
            }
        });
        tbPaneKunjungan.setViewportView(tbKunjungan);

        TabRawat.addTab("Daftar Kunjungan", tbPaneKunjungan);

        TabPenunjang.setBackground(new java.awt.Color(255, 255, 254));
        TabPenunjang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabPenunjang.setForeground(new java.awt.Color(70, 70, 70));
        TabPenunjang.setName("TabPenunjang"); // NOI18N
        TabPenunjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPenunjangMouseClicked(evt);
            }
        });

        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.GridLayout(1, 0));

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder("Daftar Kunjungan"));
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        PaneDaftarResep.setName("PaneDaftarResep"); // NOI18N
        PaneDaftarResep.setOpaque(true);

        tbPaneDaftarResep.setAutoCreateRowSorter(true);
        tbPaneDaftarResep.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPaneDaftarResep.setName("tbPaneDaftarResep"); // NOI18N
        tbPaneDaftarResep.getTableHeader().setReorderingAllowed(false);
        tbPaneDaftarResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPaneDaftarResepMouseClicked(evt);
            }
        });
        tbPaneDaftarResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPaneDaftarResepKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPaneDaftarResepKeyReleased(evt);
            }
        });
        PaneDaftarResep.setViewportView(tbPaneDaftarResep);

        internalFrame3.add(PaneDaftarResep, java.awt.BorderLayout.CENTER);

        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 26));
        panelGlass13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        BtnPrint6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnPrint6.setMnemonic('T');
        BtnPrint6.setText("Posting");
        BtnPrint6.setToolTipText("Alt+T");
        BtnPrint6.setName("BtnPrint6"); // NOI18N
        BtnPrint6.setPreferredSize(new java.awt.Dimension(100, 24));
        BtnPrint6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint6ActionPerformed(evt);
            }
        });
        BtnPrint6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint6KeyPressed(evt);
            }
        });
        panelGlass13.add(BtnPrint6);

        internalFrame3.add(panelGlass13, java.awt.BorderLayout.PAGE_END);

        internalFrame2.add(internalFrame3);

        TabPenunjang.addTab("Resep", internalFrame2);

        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.GridLayout(1, 0));

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder("Daftar Kunjungan"));
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout());

        PaneDaftarLaboratorium.setName("PaneDaftarLaboratorium"); // NOI18N
        PaneDaftarLaboratorium.setOpaque(true);

        tbPaneDaftarLaboratorium.setAutoCreateRowSorter(true);
        tbPaneDaftarLaboratorium.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPaneDaftarLaboratorium.setName("tbPaneDaftarLaboratorium"); // NOI18N
        tbPaneDaftarLaboratorium.getTableHeader().setReorderingAllowed(false);
        tbPaneDaftarLaboratorium.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPaneDaftarLaboratoriumMouseClicked(evt);
            }
        });
        tbPaneDaftarLaboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPaneDaftarLaboratoriumKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPaneDaftarLaboratoriumKeyReleased(evt);
            }
        });
        PaneDaftarLaboratorium.setViewportView(tbPaneDaftarLaboratorium);

        internalFrame6.add(PaneDaftarLaboratorium, java.awt.BorderLayout.CENTER);

        panelGlass14.setName("panelGlass14"); // NOI18N
        panelGlass14.setPreferredSize(new java.awt.Dimension(44, 26));
        panelGlass14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        BtnPrint7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnPrint7.setMnemonic('T');
        BtnPrint7.setText("Posting");
        BtnPrint7.setToolTipText("Alt+T");
        BtnPrint7.setName("BtnPrint7"); // NOI18N
        BtnPrint7.setPreferredSize(new java.awt.Dimension(100, 24));
        BtnPrint7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint7ActionPerformed(evt);
            }
        });
        BtnPrint7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint7KeyPressed(evt);
            }
        });
        panelGlass14.add(BtnPrint7);

        internalFrame6.add(panelGlass14, java.awt.BorderLayout.PAGE_END);

        internalFrame5.add(internalFrame6);

        TabPenunjang.addTab("Laboratorium", internalFrame5);

        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.GridLayout(1, 0));

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder("Daftar Kunjungan"));
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout());

        PaneDaftarRadiologi.setName("PaneDaftarRadiologi"); // NOI18N
        PaneDaftarRadiologi.setOpaque(true);

        tbPaneDaftarRadiologi.setAutoCreateRowSorter(true);
        tbPaneDaftarRadiologi.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPaneDaftarRadiologi.setName("tbPaneDaftarRadiologi"); // NOI18N
        tbPaneDaftarRadiologi.getTableHeader().setReorderingAllowed(false);
        tbPaneDaftarRadiologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPaneDaftarRadiologiMouseClicked(evt);
            }
        });
        tbPaneDaftarRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPaneDaftarRadiologiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPaneDaftarRadiologiKeyReleased(evt);
            }
        });
        PaneDaftarRadiologi.setViewportView(tbPaneDaftarRadiologi);

        internalFrame9.add(PaneDaftarRadiologi, java.awt.BorderLayout.CENTER);

        panelGlass15.setName("panelGlass15"); // NOI18N
        panelGlass15.setPreferredSize(new java.awt.Dimension(44, 26));
        panelGlass15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        BtnPrint8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnPrint8.setMnemonic('T');
        BtnPrint8.setText("Posting");
        BtnPrint8.setToolTipText("Alt+T");
        BtnPrint8.setName("BtnPrint8"); // NOI18N
        BtnPrint8.setPreferredSize(new java.awt.Dimension(100, 24));
        BtnPrint8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint8ActionPerformed(evt);
            }
        });
        BtnPrint8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint8KeyPressed(evt);
            }
        });
        panelGlass15.add(BtnPrint8);

        internalFrame9.add(panelGlass15, java.awt.BorderLayout.PAGE_END);

        internalFrame8.add(internalFrame9);

        TabPenunjang.addTab("Radiologi", internalFrame8);

        TabRawat.addTab("Daftar Penunjang", TabPenunjang);

        tbPaneLog.setName("tbPaneLog"); // NOI18N
        tbPaneLog.setOpaque(true);

        tbLog.setAutoCreateRowSorter(true);
        tbLog.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLog.setColumnSelectionAllowed(true);
        tbLog.setName("tbLog"); // NOI18N
        tbLog.getTableHeader().setReorderingAllowed(false);
        tbPaneLog.setViewportView(tbLog);

        TabRawat.addTab("Daftar Log Service", tbPaneLog);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void pilihSemuaKunjungan(boolean pilih){
    for(int i = 0; i < tbKunjungan.getRowCount(); i++){

        Object val = tbKunjungan.getValueAt(i, 6);

        String status = (val == null) ? "" : val.toString().trim();

        // ❌ skip kalau batal
        if(status.equalsIgnoreCase("Batal")){
            tbKunjungan.setValueAt(false, i, 0); // pastikan tetap tidak dicentang
            continue;
        }

        tbKunjungan.setValueAt(pilih, i, 0);
    }
}
    
    private void ceklisSemua(){
    for(int i=0;i<tbKunjungan.getRowCount();i++){
        tbKunjungan.setValueAt(true,i,0);
    }
}
    
private void ceklisResep(){

    resepSelected = !resepSelected;

 for(int i=0;i<tbKunjungan.getRowCount();i++){

    String resep = safeString(tbKunjungan.getValueAt(i,3));
    String idPoli = safeString(tbKunjungan.getValueAt(i,10));
    String idPoliEnd = safeString(tbKunjungan.getValueAt(i,11));

    System.out.println("resep="+resep+" | poli="+idPoli+" | poliEnd="+idPoliEnd);

if(resep != null && !resep.trim().isEmpty()){
    tbKunjungan.setValueAt(resepSelected,i,0);
}
}
}
    
private void ceklisLab(){

    labSelected = !labSelected;

    for(int i=0;i<tbKunjungan.getRowCount();i++){

        Object labObj = tbKunjungan.getValueAt(i,4);

        if(labObj != null){
            String lab = labObj.toString().trim();

            if(!lab.isEmpty()){
                tbKunjungan.setValueAt(labSelected,i,0);
            }
        }
    }
}
    
private void ceklisRadiologi(){

    radSelected = !radSelected;

    for(int i=0;i<tbKunjungan.getRowCount();i++){

        Object radObj = tbKunjungan.getValueAt(i,5);

        if(radObj != null){
            String rad = radObj.toString().trim();

            if(!rad.isEmpty()){
                tbKunjungan.setValueAt(radSelected,i,0);
            }
        }
    }
}
    
    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         System.out.println("randomNomor : " + randomNomor());
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, BtnKeluar);
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
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();

        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilPenunjang();
        }
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
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();

//        } else if (TabRawat.getSelectedIndex() == 1) {
//            tampil2();
        }

    }//GEN-LAST:event_TabRawatMouseClicked
// di dalam class JakPortalLog, tapi DI LUAR BtnPrint1ActionPerformed
// Ambil waktu aman, return null kalau datetime kosong atau 0000-00-00
private String getSafeTime(String noRawat, String kolom) {

    if(noRawat==null || noRawat.equals("")){
        return null;
    }

    String datetime = Sequel.cariIsi(
        "SELECT jp." + kolom + " FROM jakportal_patientjourney jp WHERE jp.no_rawat='" + noRawat + "'"
    );

    if (datetime == null || datetime.equals("") || datetime.startsWith("0000-00-00")) {
        return null;
    }

    try {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date dt = sdf.parse(datetime);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(dt);
        cal.add(java.util.Calendar.MINUTE, randomNomor());

        return sdf.format(cal.getTime());

    } catch (Exception e) {
        System.out.println("Error parsing datetime: " + datetime + " no_rawat: " + noRawat);
        return null;
    }
}
    
    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    for (int i = 0; i < tbKunjungan.getRowCount(); i++) {
        if (tbKunjungan.getValueAt(i, 0).toString().equals("true")) {

            String noRawat = tbKunjungan.getValueAt(i, 2).toString();
            String noRkmMedis = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='" + noRawat + "'");
            String tglReg = Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='" + noRawat + "'");
            String jamReg = Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='" + noRawat + "'");
            String nik = Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis='" + noRkmMedis + "'");
            String namaPasien = Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + noRkmMedis + "'");
            String noHp = Sequel.cariIsi("select no_tlp from pasien where no_rkm_medis='" + noRkmMedis + "'");
            String jk = Sequel.cariIsi("select jk from pasien where no_rkm_medis='" + noRkmMedis + "'");
            String kodeDokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + noRawat + "'");
            String tanggalLahir = Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis='" + noRkmMedis + "'");

            // mapping kd_poli
            String kdPoliAsli = Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat='" + noRawat + "'");
            java.util.Map<String, String> map = new java.util.HashMap<>();
            map.put("U0024", "INT");
            map.put("U0025", "ANA");
            map.put("U0027", "OBG");
            map.put("U0013", "PAR");
            map.put("U0033", "PAR");
            map.put("U0034", "PAR");
            map.put("U0037", "PAR");
            map.put("U0034", "BED");
            map.put("U0026", "SAR");
            map.put("U0057", "GIG");
            map.put("U0002", "GIG");
            map.put("U0040", "THT");
            map.put("U0031", "MCU");
            map.put("U0058", "MHJ");
            map.put("U0045", "IRM");
            map.put("U0047", "UMM");
            map.put("U0028", "MAT");
            map.put("U0050", "HIV");
            map.put("U0046", "ANT");
            map.put("U0021", "FST");
            map.put("U0052", "VIN");
            map.put("U0059", "JIW");
            map.put("U0060", "JIW");
            map.put("U0033", "OBG"); //poli neonatus di masukan ke obgyn di jakportalnya
            map.put("U0012", "GIZ");
            map.put("U0036", "GIZ");
            
//            map.put("IGDK", "IGD"); map.put("U0001", "IGD"); map.put("U0002", "GIG");
//            map.put("U0057", "GIG"); map.put("U0012", "GIZ"); map.put("U0036", "GIZ");
//            map.put("U0013", "PAR"); map.put("U0015", "168"); map.put("U0016", "187");
//            map.put("U0021", "FST"); map.put("U0024", "INT"); map.put("U0054", "INT");
//            map.put("U0055", "INT"); map.put("U0025", "ANA"); map.put("U0026", "SAR");
//            map.put("U0027", "OBG"); map.put("U0028", "MAT"); map.put("U0034", "BED");
//            map.put("U0040", "THT"); map.put("U0045", "IRM"); map.put("U0046", "ANT");
//            map.put("U0051", "006"); map.put("U0060", "JIW"); map.put("U0015", "RDL");
//            map.put("U0016", "LAB"); map.put("U0031", "MCU"); map.put("U0033", "TRO");
//            map.put("U0037", "TPT"); map.put("U0041", "TBS"); map.put("U0047", "UMM");
//            map.put("U0050", "HIV"); map.put("U0047", "UMM"); map.put("U0052", "VIN");
//            map.put("U0058", "MHJ"); map.put("U0059", "JIW"); map.put("U0052", "VIN");
//            map.put("U0061", "MCU");

            String kodePoli = map.getOrDefault(kdPoliAsli, kdPoliAsli);

            String namaDokter = Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='" + kodeDokter + "'");

            // jam Mulai dan Selesai dari jadwal
            String jamMulai = Sequel.cariIsi(
                "SELECT jam_mulai FROM jadwal WHERE kd_dokter='" + kodeDokter + "' AND hari_kerja = " +
                "CASE DAYNAME('" + tglReg + "') " +
                "WHEN 'Monday' THEN 'SENIN' " +
                "WHEN 'Tuesday' THEN 'SELASA' " +
                "WHEN 'Wednesday' THEN 'RABU' " +
                "WHEN 'Thursday' THEN 'KAMIS' " +
                "WHEN 'Friday' THEN 'JUMAT' " +
                "WHEN 'Saturday' THEN 'SABTU' " +
                "WHEN 'Sunday' THEN 'AKHAD' END"
            );

            String jamSelesai = Sequel.cariIsi(
                "SELECT jam_selesai FROM jadwal WHERE kd_dokter='" + kodeDokter + "' AND hari_kerja = " +
                "CASE DAYNAME('" + tglReg + "') " +
                "WHEN 'Monday' THEN 'SENIN' " +
                "WHEN 'Tuesday' THEN 'SELASA' " +
                "WHEN 'Wednesday' THEN 'RABU' " +
                "WHEN 'Thursday' THEN 'KAMIS' " +
                "WHEN 'Friday' THEN 'JUMAT' " +
                "WHEN 'Saturday' THEN 'SABTU' " +
                "WHEN 'Sunday' THEN 'AKHAD' END"
            );

            String kodebiaya = Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat='" + noRawat + "'");
            String kodeBiaya = "", interval = "2", time = "";
            String idcheckin = Sequel.cariIsi("select id_checkin from jakportal_patientjourney where no_rawat='" + noRawat + "'");

            // =========================
            // cmbTask 1 (CheckIn)
            // =========================
            if (cmbTask.getSelectedIndex() == 1) {
                if (",A01,A13,".contains("," + kodebiaya + ",")) {kodeBiaya = "P001";} 
                else if (kodebiaya.equals("BPJ")) {kodeBiaya = "P002";} 
                else if (",A03,A04,A05,A06,A07,A08,A10,A11,A12,K01,K02,K03,PET,".contains("," + kodebiaya + ",")) {kodeBiaya = "P003";} 
                else {kodeBiaya = "";}

                if (idcheckin == null || idcheckin.equals("")) {
CheckIn(
    noRawat,
    tglReg,
    jamReg,
    noRkmMedis,
    nik,
    namaPasien,
    tanggalLahir,
    noHp,
    jk,
    kodePoli,
    kodeDokter,
    namaDokter,
    jamMulai,
    jamSelesai,
    kodeBiaya
);
                } else {
                    String datetime = getSafeTime(noRawat, "id_checkin_datetime");
                    if (datetime == null) continue;
                    jamReg = datetime.split(" ")[1];
                    CheckInEnd(noRawat, idcheckin, tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));
                }

            // =========================
            // cmbTask 2 (NurseStation)
            // =========================
            } else if (cmbTask.getSelectedIndex() == 2) {
                String idns = Sequel.cariIsi("select id_ns from jakportal_patientjourney where no_rawat='" + noRawat + "'");
                String datetime = null;
                if (idns == null || idns.equals("")) {
                    datetime = getSafeTime(noRawat, "id_checkin_end_datetime");
                    if (datetime == null) continue;
                    jamReg = datetime.split(" ")[1];
                    NurseStation(noRawat, idcheckin, tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));
                } else {
                    datetime = getSafeTime(noRawat, "id_ns_datetime");
                    if (datetime == null) continue;
                    jamReg = datetime.split(" ")[1];
                    NurseStationEnd(noRawat, idcheckin, idns, tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));
                }

            // =========================
            // cmbTask 3–7, pakai cara sama: getSafeTime + cek null
            // =========================
            } else if (cmbTask.getSelectedIndex() == 3) {
String idPoli = Sequel.cariIsi("SELECT jp.id_poli FROM jakportal_patientjourney jp WHERE jp.no_rawat='" + noRawat + "'");
    
    // Ambil waktu aman
    time = getSafeTime(noRawat, (idPoli == null || idPoli.equals("")) ? "id_ns_end_datetime" : "id_poli_datetime");

    // fallback pakai jam_reg jika null
    if (time == null) {
        time = Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='" + noRawat + "'");
        if (!time.contains(" ")) {
            // gabungkan dengan tgl_registrasi
            String tgl = Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='" + noRawat + "'");
            time = tgl + " " + time;
        }
    }

    jamReg = time.split(" ")[1];

    if (idPoli == null || idPoli.equals("")) {
        Poliklinik(noRawat, idcheckin, tglReg, jamReg.split(":")[0]+":"+jamReg.split(":")[1]+":"+secondTime.format(new Date()));
    } else {
        PoliklinikEnd(noRawat, idcheckin, idPoli, tglReg, jamReg.split(":")[0]+":"+jamReg.split(":")[1]+":"+secondTime.format(new Date()));
    }
    continue;

            } else if (cmbTask.getSelectedIndex() == 4) {
                String idPoli = Sequel.cariIsi("SELECT jp.id_poli FROM jakportal_patientjourney jp WHERE jp.no_rawat='" + noRawat + "'");
                if (idPoli == null || idPoli.equals("")) { JOptionPane.showMessageDialog(internalFrame1, "Pasien Belum Masuk Poliklinik"); continue; }
                String datetime = getSafeTime(noRawat, "id_poli_datetime");
                if (datetime == null) continue;
                jamReg = datetime.split(" ")[1];
                ResepPermintaan(noRawat, idcheckin, "Non Racikan", tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));

            } else if (cmbTask.getSelectedIndex() == 5) {
                String idPoli = Sequel.cariIsi("SELECT jp.id_poli FROM jakportal_patientjourney jp WHERE jp.no_rawat='" + noRawat + "'");
                if (idPoli == null || idPoli.equals("")) { JOptionPane.showMessageDialog(internalFrame1, "Pasien Belum Masuk Poliklinik"); continue; }
                String datetime = getSafeTime(noRawat, "id_poli_datetime");
                if (datetime == null) continue;
                jamReg = datetime.split(" ")[1];
                LaboratoriumPermintaan(noRawat, idcheckin, "PK", tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));

            } else if (cmbTask.getSelectedIndex() == 6) {
                String idPoli = Sequel.cariIsi("SELECT jp.id_poli FROM jakportal_patientjourney jp WHERE jp.no_rawat='" + noRawat + "'");
                if (idPoli == null || idPoli.equals("")) { JOptionPane.showMessageDialog(internalFrame1, "Pasien Belum Masuk Poliklinik"); continue; }
                String datetime = getSafeTime(noRawat, "id_poli_datetime");
                if (datetime == null) continue;
                jamReg = datetime.split(" ")[1];
                RadiologiPermintaan(noRawat, idcheckin, tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));

            } else if (cmbTask.getSelectedIndex() == 7) {
                String idns = Sequel.cariIsi("select id_ns_end from jakportal_patientjourney where no_rawat='" + noRawat + "'");
                String idPoli = Sequel.cariIsi("select id_poli_end from jakportal_patientjourney where no_rawat='" + noRawat + "'");
                String datetime = getSafeTime(noRawat, "id_poli_end_datetime");
                if (datetime == null) continue;
                jamReg = datetime.split(" ")[1];
                CheckOut(noRawat, idcheckin, idns, idPoli, tglReg, jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date()));
            }
        }
    }

    tampil();
    this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for (i = 0; i < tbKunjungan.getRowCount(); i++) {
            tbKunjungan.setValueAt(false, i, 0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for (i = 0; i < tbKunjungan.getRowCount(); i++) {
            tbKunjungan.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void cmbTaskKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTaskKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTaskKeyPressed

    private void TabPenunjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPenunjangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabPenunjangMouseClicked

    private void tbPaneDaftarResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPaneDaftarResepMouseClicked
        tampilResepJourney(tbPaneDaftarResep.getValueAt(tbPaneDaftarResep.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbPaneDaftarResepMouseClicked

    private void tbPaneDaftarResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPaneDaftarResepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPaneDaftarResepKeyPressed

    private void tbPaneDaftarResepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPaneDaftarResepKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPaneDaftarResepKeyReleased

    private void tbKunjunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKunjunganMouseClicked

    }//GEN-LAST:event_tbKunjunganMouseClicked

    private void tbKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKunjunganKeyPressed

    }//GEN-LAST:event_tbKunjunganKeyPressed

    private void tbKunjunganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKunjunganKeyReleased

    }//GEN-LAST:event_tbKunjunganKeyReleased

    private void tbPaneDaftarLaboratoriumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPaneDaftarLaboratoriumMouseClicked
        tampilLaboratoriumJourney(tbPaneDaftarLaboratorium.getValueAt(tbPaneDaftarLaboratorium.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbPaneDaftarLaboratoriumMouseClicked

    private void tbPaneDaftarLaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPaneDaftarLaboratoriumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPaneDaftarLaboratoriumKeyPressed

    private void tbPaneDaftarLaboratoriumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPaneDaftarLaboratoriumKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPaneDaftarLaboratoriumKeyReleased

    private void tbPaneDaftarRadiologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPaneDaftarRadiologiMouseClicked
        tampilRadiologiJourney(tbPaneDaftarRadiologi.getValueAt(tbPaneDaftarRadiologi.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbPaneDaftarRadiologiMouseClicked

    private void tbPaneDaftarRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPaneDaftarRadiologiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPaneDaftarRadiologiKeyPressed

    private void tbPaneDaftarRadiologiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPaneDaftarRadiologiKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPaneDaftarRadiologiKeyReleased

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        ceklisResep();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void BtnPrint6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint6ActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    for (int i = 0; i < tbPaneDaftarResep.getRowCount(); i++) {
        if (tbPaneDaftarResep.getValueAt(i, 0).toString().equals("true")) {

            String noRawat = tbPaneDaftarResep.getValueAt(i, 1).toString();
            String idPermintaan = tbPaneDaftarResep.getValueAt(i, 3).toString();

            String noRkmMedis = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='" + noRawat + "'");
            String tglReg = Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='" + noRawat + "'");
            String idcheckin = Sequel.cariIsi("select id_checkin from jakportal_patientjourney where no_rawat='" + noRawat + "'");

            String time = "", jamReg = "";

            String idValidasi = Sequel.cariIsi(
                "select id_validasi from jakportal_patientjourney_resep where id_permintaan='" + idPermintaan + "'"
            );

            if (idValidasi.equals("") || idValidasi.isEmpty()) {

                String datetime = Sequel.cariIsi(
                    "select id_permintaan_datetime from jakportal_patientjourney_resep where id_permintaan='" + idPermintaan + "'"
                );

                time = Sequel.cariIsi(
                    "select DATE_ADD('" + datetime + "',INTERVAL " + randomNomor() + " MINUTE)"
                );

                jamReg = time.split(" ")[1];

                ResepValidasi(
                    noRawat, idcheckin, idPermintaan, tglReg,
                    jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date())
                );

            } else {

                String datetime = Sequel.cariIsi(
                    "select id_validasi_datetime from jakportal_patientjourney_resep where id_permintaan='" + idPermintaan + "'"
                );

                time = Sequel.cariIsi(
                    "select DATE_ADD('" + datetime + "',INTERVAL " + randomNomor() + " MINUTE)"
                );

                jamReg = time.split(" ")[1];

                ResepPenyerahan(
                    noRawat, idcheckin, idValidasi, tglReg,
                    jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date())
                );
            }
        }
    }

    // refresh tabel
    tampilResep();

    // tampil journey jika ada row dipilih
    int row = tbPaneDaftarResep.getSelectedRow();
    if(row > -1){
        tampilResepJourney(tbPaneDaftarResep.getValueAt(row,1).toString());
    }

    this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint6ActionPerformed

    private void BtnPrint6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint6KeyPressed

    private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
        ceklisLab();
    }//GEN-LAST:event_BtnKeluar2ActionPerformed

    private void BtnKeluar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar2KeyPressed

    private void BtnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar3ActionPerformed
        ceklisRadiologi();
    }//GEN-LAST:event_BtnKeluar3ActionPerformed

    private void BtnKeluar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar3KeyPressed

    private void BtnPrint7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint7ActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    for (int i = 0; i < tbPaneDaftarLaboratorium.getRowCount(); i++) {
        if (tbPaneDaftarLaboratorium.getValueAt(i, 0).toString().equals("true")) {

            String noRawat = tbPaneDaftarLaboratorium.getValueAt(i, 1).toString();
            String idPermintaan = tbPaneDaftarLaboratorium.getValueAt(i, 3).toString();

            String tglReg = Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='" + noRawat + "'");
            String idcheckin = Sequel.cariIsi("select id_checkin from jakportal_patientjourney where no_rawat='" + noRawat + "'");

            String time = "", jamReg = "";

            String idSampel = Sequel.cariIsi(
                "select id_sampel from jakportal_patientjourney_laboratorium where id_permintaan='" + idPermintaan + "'"
            );

            if (idSampel.equals("") || idSampel.isEmpty()) {

                String datetime = Sequel.cariIsi(
                    "select id_permintaan_datetime from jakportal_patientjourney_laboratorium where id_permintaan='" + idPermintaan + "'"
                );

                time = Sequel.cariIsi(
                    "select DATE_ADD('" + datetime + "',INTERVAL " + randomNomor() + " MINUTE)"
                );

                jamReg = time.split(" ")[1];

                LaboratoriumSampel(
                    noRawat, idcheckin, idPermintaan, tglReg,
                    jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date())
                );

            } else {

                String datetime = Sequel.cariIsi(
                    "select id_sampel_datetime from jakportal_patientjourney_laboratorium where id_permintaan='" + idPermintaan + "'"
                );

                time = Sequel.cariIsi(
                    "select DATE_ADD('" + datetime + "',INTERVAL " + randomNomor() + " MINUTE)"
                );

                jamReg = time.split(" ")[1];

                LaboratoriumHasil(
                    noRawat, idcheckin, idSampel, tglReg,
                    jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date())
                );
            }
        }
    }

    // refresh tabel
    tampilLaboratorium();

    // tampil journey jika ada row dipilih
    int row = tbPaneDaftarLaboratorium.getSelectedRow();
    if(row > -1){
        tampilLaboratoriumJourney(tbPaneDaftarLaboratorium.getValueAt(row,1).toString());
    }

    this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint7ActionPerformed

    private void BtnPrint7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint7KeyPressed

    private void BtnPrint8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint8ActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    for (int i = 0; i < tbPaneDaftarRadiologi.getRowCount(); i++) {
        if (tbPaneDaftarRadiologi.getValueAt(i, 0).toString().equals("true")) {

            String noRawat = tbPaneDaftarRadiologi.getValueAt(i, 1).toString();
            String idPermintaan = tbPaneDaftarRadiologi.getValueAt(i, 3).toString();

            String tglReg = Sequel.cariIsi(
                "select tgl_registrasi from reg_periksa where no_rawat='" + noRawat + "'"
            );

            String idcheckin = Sequel.cariIsi(
                "select id_checkin from jakportal_patientjourney where no_rawat='" + noRawat + "'"
            );

            String time = "", jamReg = "";

            String idSampel = Sequel.cariIsi(
                "select id_sampel from jakportal_patientjourney_radiologi where id_permintaan='" + idPermintaan + "'"
            );

            if (idSampel.equals("") || idSampel.isEmpty()) {

                String datetime = Sequel.cariIsi(
                    "select id_permintaan_datetime from jakportal_patientjourney_radiologi where id_permintaan='" + idPermintaan + "'"
                );

                time = Sequel.cariIsi(
                    "select DATE_ADD('" + datetime + "',INTERVAL " + randomNomor() + " MINUTE)"
                );

                jamReg = time.split(" ")[1];

                RadiologiSampel(
                    noRawat,
                    idcheckin,
                    idPermintaan,
                    tglReg,
                    jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date())
                );

            } else {

                String datetime = Sequel.cariIsi(
                    "select id_sampel_datetime from jakportal_patientjourney_radiologi where id_permintaan='" + idPermintaan + "'"
                );

                time = Sequel.cariIsi(
                    "select DATE_ADD('" + datetime + "',INTERVAL " + randomNomor() + " MINUTE)"
                );

                jamReg = time.split(" ")[1];

                RadiologiHasil(
                    noRawat,
                    idcheckin,
                    idSampel,
                    tglReg,
                    jamReg.split(":")[0] + ":" + jamReg.split(":")[1] + ":" + secondTime.format(new Date())
                );
            }
        }
    }

    // refresh tabel radiologi
    tampilRadiologi();

    // tampil journey jika ada row dipilih
    int row = tbPaneDaftarRadiologi.getSelectedRow();
    if (row > -1) {
        tampilRadiologiJourney(tbPaneDaftarRadiologi.getValueAt(row, 1).toString());
    }

    this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint8ActionPerformed

    private void BtnPrint8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint8KeyPressed

    private void cmbTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTaskActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTaskActionPerformed

    private void cmbStatusJakportalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusJakportalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusJakportalActionPerformed

    private void cmbStatusJakportalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusJakportalItemStateChanged
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
        new Thread(){
            public void run(){
                tampil();
            }
        }.start();
    }
    }//GEN-LAST:event_cmbStatusJakportalItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            JakPortalLog dialog = new JakPortalLog(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKeluar3;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnPrint6;
    private widget.Button BtnPrint7;
    private widget.Button BtnPrint8;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgLoading;
    private widget.Label LCount;
    private widget.Label LCountFailed;
    private widget.Label LCountSuccess;
    private widget.ScrollPane PaneDaftarLaboratorium;
    private widget.ScrollPane PaneDaftarRadiologi;
    private widget.ScrollPane PaneDaftarResep;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabPenunjang;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JComboBox<String> cmbStatusJakportal;
    private widget.ComboBox cmbTask;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.Table tbKunjungan;
    private widget.Table tbLog;
    private widget.Table tbPaneDaftarLaboratorium;
    private widget.Table tbPaneDaftarRadiologi;
    private widget.Table tbPaneDaftarResep;
    private widget.ScrollPane tbPaneKunjungan;
    private widget.ScrollPane tbPaneLog;
    // End of variables declaration//GEN-END:variables

//    public void tampil() {
////        Valid.tabelKosong(tabModeKunjungan);
////        try {
////            ps = koneksi.prepareStatement(
////                    "SELECT * from reg_periksa  LEFT Join jakportal_patientjourney ON  reg_periksa.no_rawat=jakportal_patientjourney.no_rawat where  tgl_registrasi between ? and ?");
////            i = 0;
////            j = 0;
////            try {
////                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
////                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
////                rs = ps.executeQuery();
////                iSuccess = 0;
////                iFailed = 0;
////                while (rs.next()) {
////                    tabModeKunjungan.addRow(new Object[]{
////                        false, rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"), rs.getString("no_rawat"), rs.getString("id_checkin"), rs.getString("id_checkin_end"),
////                        rs.getString("id_ns"), rs.getString("id_ns_end"), rs.getString("id_poli"), rs.getString("id_poli_end"), rs.getString("id_check_out")
////                    });
////                }
////            } catch (Exception e) {
////                System.out.println("Notif : " + e);
////            } finally {
////                if (rs != null) {
////                    rs.close();
////                }
////                if (ps != null) {
////                    ps.close();
////                }
////            }
////        } catch (Exception e) {
////            System.out.println("Notifikasi : " + e);
////        }
////        LCount.setText("" + tabModeKunjungan.getRowCount());
////        LCountSuccess.setText("" + iSuccess);
////        LCountFailed.setText("" + iFailed);
//    Valid.tabelKosong(tabModeKunjungan);
//    try {
//        ps = koneksi.prepareStatement(
//            "SELECT * " +
//            "FROM reg_periksa rp " +
//            "LEFT JOIN jakportal_patientjourney jp ON rp.no_rawat = jp.no_rawat " +
//            "WHERE rp.tgl_registrasi BETWEEN ? AND ? " +
//            "AND rp.kd_poli IN (" +
//            "'IGDK','U0001','U0002','U0057','U0012','U0036','U0013','U0015','U0016'," +
//            "'U0021','U0024','U0054','U0055','U0025','U0026','U0027','U0028','U0034'," +
//            "'U0040','U0045','U0046','U0051','U0060')" 
//        );
//
//        try {
//            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
//            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
//            rs = ps.executeQuery();
//
//            iSuccess = 0;
//            iFailed = 0;
//
//            while (rs.next()) {
//                tabModeKunjungan.addRow(new Object[]{
//                    false,
//                    rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"),
//                    rs.getString("no_rawat"),
//                    rs.getString("id_checkin"),
//                    rs.getString("id_checkin_end"),
//                    rs.getString("id_ns"),
//                    rs.getString("id_ns_end"),
//                    rs.getString("id_poli"),
//                    rs.getString("id_poli_end"),
//                    rs.getString("id_check_out")
//                });
//            }
//
//        } catch (Exception e) {
//            System.out.println("Notif : " + e);
//        } finally {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//        }
//
//    } catch (Exception e) {
//        System.out.println("Notifikasi : " + e);
//    }
//
//    LCount.setText("" + tabModeKunjungan.getRowCount());
//    LCountSuccess.setText("" + iSuccess);
//    LCountFailed.setText("" + iFailed);
//    }
public void tampil() {
    Valid.tabelKosong(tabModeKunjungan);

    try {

        String statusDipilih = cmbStatusJakportal.getSelectedItem().toString();

        String filterStatus = "";

        if (!statusDipilih.equals("Semua")) {

            filterStatus = " HAVING status_journey = ? ";

        }

        ps = koneksi.prepareStatement(

            "SELECT rp.no_rawat, rp.tgl_registrasi, rp.jam_reg, " +

            "jp.id_checkin, jp.id_checkin_end, jp.id_ns, jp.id_ns_end, " +

            "jp.id_poli, jp.id_poli_end, jp.id_check_out, " +

            // ================== STATUS ==================

            "CASE " +

            "WHEN LOWER(rp.stts)='batal' THEN 'Batal' " +

            "WHEN jp.id_check_out IS NOT NULL AND jp.id_check_out<>'' THEN 'Check Out' " +

            "WHEN jp.id_poli_end IS NOT NULL AND jp.id_poli_end<>'' THEN 'Poliklinik Selesai' " +

            "WHEN jp.id_poli IS NOT NULL AND jp.id_poli<>'' THEN 'Poliklinik' " +

            "WHEN jp.id_ns_end IS NOT NULL AND jp.id_ns_end<>'' THEN 'Nurse Station Selesai' " +

            "WHEN jp.id_ns IS NOT NULL AND jp.id_ns<>'' THEN 'Nurse Station' " +

            "WHEN jp.id_checkin_end IS NOT NULL AND jp.id_checkin_end<>'' THEN 'Checkin Selesai' " +

            "WHEN jp.id_checkin IS NOT NULL AND jp.id_checkin<>'' THEN 'Checkin' " +

            "ELSE 'Belum Checkin' END AS status_journey, " +

            "(SELECT GROUP_CONCAT(ro.no_resep SEPARATOR ', ') FROM resep_obat ro WHERE ro.no_rawat=rp.no_rawat) AS no_resep, " +

            "(SELECT GROUP_CONCAT(pl.noorder SEPARATOR ', ') FROM permintaan_lab pl WHERE pl.no_rawat=rp.no_rawat) AS no_lab, " +

            "(SELECT GROUP_CONCAT(pr.noorder SEPARATOR ', ') FROM permintaan_radiologi pr WHERE pr.no_rawat=rp.no_rawat) AS no_rad " +

            "FROM reg_periksa rp " +

            "LEFT JOIN jakportal_patientjourney jp ON rp.no_rawat=jp.no_rawat " +

            "INNER JOIN poliklinik p ON rp.kd_poli=p.kd_poli " +

            "INNER JOIN maping_poli_bpjs mp ON mp.kd_poli_rs = rp.kd_poli " +

            "WHERE rp.tgl_registrasi BETWEEN ? AND ? " +

            "AND p.status='1' " +

            // ================== FILTER POLI ==================

            "AND rp.kd_poli IN ( " +

            "'U0025','U0034','U0002','U0057','U0028','U0031','U0058','U0027'," +

            "'U0041','U0013','U0024','U0050','U0026','U0040','U0033','U0047'," +

            "'U0045','U0012','U0021','U0052','U0039','U0046','U0059','U0037' " +

            ") " +

            filterStatus +

            " ORDER BY rp.tgl_registrasi, rp.jam_reg"

        );

        int paramIndex = 1;

        ps.setString(paramIndex++, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));

        ps.setString(paramIndex++, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

        if (!statusDipilih.equals("Semua")) {

            ps.setString(paramIndex++, statusDipilih);

        }

        rs = ps.executeQuery();

        // ================== LOAD DATA ==================

        while (rs.next()) {

            tabModeKunjungan.addRow(new Object[]{

                false,

                rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"),

                rs.getString("no_rawat"),

                rs.getString("no_resep"),

                rs.getString("no_lab"),

                rs.getString("no_rad"),

                rs.getString("status_journey"),

                rs.getString("id_checkin"),

                rs.getString("id_checkin_end"),

                rs.getString("id_ns"),

                rs.getString("id_ns_end"),

                rs.getString("id_poli"),

                rs.getString("id_poli_end"),

                rs.getString("id_check_out")

            });

        }

        // ================== AUTO CHECKLIST ==================

        if (!statusDipilih.equals("Semua")) {

            for (int i = 0; i < tbKunjungan.getRowCount(); i++) {

                String statusRow = tbKunjungan.getValueAt(i, 6).toString().trim();

                // skip batal kalau bukan filter batal

                if (!statusDipilih.equals("Batal") &&

                    statusRow.equalsIgnoreCase("Batal")) {

                    tbKunjungan.setValueAt(false, i, 0);

                    continue;

                }

                tbKunjungan.setValueAt(

                    statusRow.equalsIgnoreCase(statusDipilih),

                    i, 0

                );

            }

        }

    } catch (Exception e) {

        System.out.println("Notifikasi tampil(): " + e);

    } finally {

        try {

            if (rs != null) rs.close();

            if (ps != null) ps.close();

        } catch (Exception e) {}

    }

    LCount.setText("Total: " + tabModeKunjungan.getRowCount());
}

    public void tampilPenunjang() {
        if (TabPenunjang.getSelectedIndex() == 0) {
            tampilResep();

        } else if (TabPenunjang.getSelectedIndex() == 1) {
            tampilLaboratorium();
        } else if (TabPenunjang.getSelectedIndex() == 2) {
            tampilRadiologi();
        }
    }

public void tampilResep() {
    Valid.tabelKosong(tabModeResep);
    try {
        ps = koneksi.prepareStatement(
            "SELECT * FROM jakportal_patientjourney_resep " +
            "JOIN reg_periksa ON jakportal_patientjourney_resep.no_rawat=reg_periksa.no_rawat " +
            "WHERE tgl_registrasi BETWEEN ? AND ? " +
            "AND (IFNULL(id_validasi,'')='' OR IFNULL(id_penyerahan,'')='')"
        );

        try {
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
            rs = ps.executeQuery();

            while (rs.next()) {
                tabModeResep.addRow(new Object[]{
                    false,
                    rs.getString("no_rawat"),
                    rs.getString("id_checkin"),
                    rs.getString("id_permintaan"),
                    rs.getString("id_validasi"),
                    rs.getString("id_penyerahan")
                });
            }

        } catch (Exception e) {
            System.out.println("Notif : " + e);
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

    } catch (Exception e) {
        System.out.println("Notifikasi : " + e);
        e.printStackTrace();
    }

    LCount.setText("" + tabModeResep.getRowCount());
}

    public void tampilResepJourney(String noRawat) {
        Valid.tabelKosong(tabModeResepJourney);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT * from  jakportal_patientjourney_resep  where jakportal_patientjourney_resep.no_rawat='" + noRawat + "'");

            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeResepJourney.addRow(new Object[]{
                        false, rs.getString("no_rawat"), rs.getString("id_checkin"), rs.getString("id_permintaan"), rs.getString("id_validasi"), rs.getString("id_penyerahan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
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
        LCount.setText("" + tabModeResepJourney.getRowCount());
    }

    public void tampilLaboratorium() {
//        Valid.tabelKosong(tabModeLaboratorium);
//        try {
//            ps = koneksi.prepareStatement(
//                    "SELECT * from  jakportal_patientjourney_laboratorium join reg_periksa ON  jakportal_patientjourney_laboratorium.no_rawat=reg_periksa.no_rawat where  tgl_registrasi between ? and ? group by jakportal_patientjourney_laboratorium.no_rawat");
//
//            try {
//                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
//                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
//                rs = ps.executeQuery();
//
//                while (rs.next()) {
//                    tabModeLaboratorium.addRow(new Object[]{
//                        rs.getString("no_rawat"), rs.getString("id_checkin")
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        LCount.setText("" + tabModeLaboratorium.getRowCount());
Valid.tabelKosong(tabModeLaboratorium);
try {
    ps = koneksi.prepareStatement(
        "SELECT * FROM jakportal_patientjourney_laboratorium " +
        "JOIN reg_periksa ON jakportal_patientjourney_laboratorium.no_rawat=reg_periksa.no_rawat " +
        "WHERE tgl_registrasi BETWEEN ? AND ? " +
        "AND (IFNULL(id_sampel,'')='' OR IFNULL(id_hasil,'')='')"
    );

    try {
        ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
        ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
        rs = ps.executeQuery();

        while (rs.next()) {
            tabModeLaboratorium.addRow(new Object[]{
                false,
                rs.getString("no_rawat"),
                rs.getString("id_checkin"),
                rs.getString("id_permintaan"),
                rs.getString("id_sampel"),
                rs.getString("id_hasil")
            });
        }

    } catch (Exception e) {
        System.out.println("Notif : " + e);
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }

} catch (Exception e) {
    System.out.println("Notifikasi : " + e);
    e.printStackTrace();
}

LCount.setText("" + tabModeLaboratorium.getRowCount());
    }

    public void tampilLaboratoriumJourney(String noRawat) {
        Valid.tabelKosong(tabModeLaboratoriumJourney);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT * from  jakportal_patientjourney_laboratorium  where jakportal_patientjourney_laboratorium.no_rawat='" + noRawat + "'");

            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeLaboratoriumJourney.addRow(new Object[]{
                        false, rs.getString("no_rawat"), rs.getString("id_checkin"), rs.getString("id_permintaan"), rs.getString("id_sampel"), rs.getString("id_hasil")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
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
        LCount.setText("" + tabModeLaboratoriumJourney.getRowCount());
    }

    public void tampilRadiologi() {
//        Valid.tabelKosong(tabModeRadiologi);
//        try {
//            ps = koneksi.prepareStatement(
//                    "SELECT * from  jakportal_patientjourney_radiologi join reg_periksa ON  jakportal_patientjourney_radiologi.no_rawat=reg_periksa.no_rawat where  tgl_registrasi between ? and ? group by jakportal_patientjourney_radiologi.no_rawat");
//
//            try {
//                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
//                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
//                rs = ps.executeQuery();
//
//                while (rs.next()) {
//                    tabModeRadiologi.addRow(new Object[]{
//                        rs.getString("no_rawat"), rs.getString("id_checkin")
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        LCount.setText("" + tabModeRadiologi.getRowCount());
Valid.tabelKosong(tabModeRadiologi);
try {
    ps = koneksi.prepareStatement(
        "SELECT * FROM jakportal_patientjourney_radiologi " +
        "JOIN reg_periksa ON jakportal_patientjourney_radiologi.no_rawat=reg_periksa.no_rawat " +
        "WHERE tgl_registrasi BETWEEN ? AND ? " +
        "AND (IFNULL(id_sampel,'')='' OR IFNULL(id_hasil,'')='')"
    );

    try {
        ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
        ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
        rs = ps.executeQuery();

        while (rs.next()) {
            tabModeRadiologi.addRow(new Object[]{
                false,
                rs.getString("no_rawat"),
                rs.getString("id_checkin"),
                rs.getString("id_permintaan"),
                rs.getString("id_sampel"),
                rs.getString("id_hasil")
            });
        }

    } catch (Exception e) {
        System.out.println("Notif : " + e);
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }

} catch (Exception e) {
    System.out.println("Notifikasi : " + e);
    e.printStackTrace();
}

LCount.setText("" + tabModeRadiologi.getRowCount());
    }

    public void tampilRadiologiJourney(String noRawat) {
        Valid.tabelKosong(tabModeRadiologiJourney);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT * from  jakportal_patientjourney_radiologi  where jakportal_patientjourney_radiologi.no_rawat='" + noRawat + "'");

            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeRadiologiJourney.addRow(new Object[]{
                        false, rs.getString("no_rawat"), rs.getString("id_checkin"), rs.getString("id_permintaan"), rs.getString("id_sampel"), rs.getString("id_hasil")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
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
        LCount.setText("" + tabModeRadiologiJourney.getRowCount());
    }

//    public void tampil2() {
//        Valid.tabelKosong(tabModeLog);
//        try {
//            ps = koneksi_log.prepareStatement(
//                    "SELECT * from  log_bpjs_antrian_online where  date(datetime) between ? and ?");
//
//            try {
//                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
//                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
////                if(!TCari.getText().trim().equals("")){
////                    ps.setString(3,"%"+TCari.getText()+"%");
////                    ps.setString(4,"%"+TCari.getText()+"%");
////                    ps.setString(5,"%"+TCari.getText()+"%");
////                    ps.setString(6,"%"+TCari.getText()+"%");
////                    ps.setString(7,"%"+TCari.getText()+"%");
////                    ps.setString(8,"%"+TCari.getText()+"%");
////                    ps.setString(9,"%"+TCari.getText()+"%");
////                    ps.setString(10,"%"+TCari.getText()+"%");
////                    ps.setString(11,"%"+TCari.getText()+"%");
////                }
//
//                rs = ps.executeQuery();
//
//                while (rs.next()) {
//                    tabModeLog.addRow(new Object[]{
//                        rs.getString("code"), rs.getString("datetime"), rs.getString("user"), rs.getString("no_rawat"), rs.getString("request"), rs.getString("response")
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        LCount.setText("" + tabModeLog.getRowCount());
//    }

//    private void CheckIn(String noRawatpasien, String tglReg, String jamReg, String noRkmMedis, String nik, String namaPasien, String noHp, String jk, String kodePoli, String kodeDokter, String namaDokter, String jamMulai, String jamSelesai, String kodeBiaya) {
//        try {
//            headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
//            headers.add("x-token", api.Token());
//            requestJson = "{"
//                    + "\"kode\": \"" + koneksiDB.JAKPORTALUSERNAME() + "\","
//                    + "\"tanggal\": \"" + tglReg + "\","
//                    + "\"jam\": \"" + jamReg + "\","
//                    + "\"norm\": \"" + noRkmMedis + "\","
//                    + "\"nik\": \"" + nik + "\","
//                    + "\"namapasien\": \"" + namaPasien + "\","
//                    + "\"nohp\": \"" + noHp + "\","
//                    + "\"jk\": \"" + jk + "\","
//                    + "\"kodepoli\": \"" + kodePoli + "\","
//                    + "\"kodedokter\": \"" + kodeDokter + "\","
//                    + "\"namadokter\": \"" + namaDokter + "\","
//                    + "\"jammulai\": \"" + jamMulai + "\","
//                    + "\"jamselesai\": \"" + jamSelesai + "\","
//                    + "\"kodebiaya\": \"" + kodeBiaya + "\","
//                    + "\"noregistrasi\": \"" + noRawatpasien + "\""
//                    + "}";
////            System.out.println("Notifikasi : " + requestJson);
//            requestEntity = new HttpEntity(requestJson, headers);
//            URL = koneksiDB.JAKPORTALURL();
//            root = mapper.readTree(api.getRest().exchange(URL + "/checkin", HttpMethod.POST, requestEntity, String.class).getBody());
//            response = root.path("response");
//            Sequel.menyimpantf2("jakportal_patientjourney", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 15,
//                    new String[]{noRawatpasien, response.path("idcheckin").asText(), tglReg + " " + jamReg, "", "0000-00-00 00:00:00", "", "0000-00-00 00:00:00", "", "0000-00-00 00:00:00", "", "0000-00-00 00:00:00", "", "0000-00-00 00:00:00", "", "0000-00-00 00:00:00"});
//
//        } catch (Exception ex) {
//            System.out.println("Notifikasi : " + ex);
//            if (ex.toString().contains("UnknownHostException")) {
//                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
//            }
//        }
//    }

    private void CheckIn(
        String noRawatpasien,
        String tglReg,
        String jamReg,
        String noRkmMedis,
        String nik,
        String namaPasien,
        String tanggalLahir,
        String noHp,
        String jk,
        String kodePoli,
        String kodeDokter,
        String namaDokter,
        String jamMulai,
        String jamSelesai,
        String kodeBiaya) {

    try {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
        headers.add("x-token", api.Token());

        requestJson = "{"
                + "\"kode\":\"" + koneksiDB.JAKPORTALUSERNAME() + "\","
                + "\"tanggal\":\"" + tglReg + "\","
                + "\"jam\":\"" + jamReg + "\","
                + "\"norm\":\"" + noRkmMedis + "\","
                + "\"nik\":\"" + nik + "\","
                + "\"namapasien\":\"" + namaPasien + "\","
                + "\"tanggallahir\":\"" + tanggalLahir + "\","
                + "\"nohp\":\"" + noHp + "\","
                + "\"jk\":\"" + jk + "\","
                + "\"kodepoli\":\"" + kodePoli + "\","
                + "\"kodedokter\":\"" + kodeDokter + "\","
                + "\"namadokter\":\"" + namaDokter + "\","
                + "\"jammulai\":\"" + jamMulai + "\","
                + "\"jamselesai\":\"" + jamSelesai + "\","
                + "\"kodebiaya\":\"" + kodeBiaya + "\","
                + "\"noregistrasi\":\"" + noRawatpasien + "\""
                + "}";

        System.out.println("Request : " + requestJson);

        requestEntity = new HttpEntity<>(requestJson, headers);

        URL = koneksiDB.JAKPORTALURL();

        root = mapper.readTree(
                api.getRest().exchange(
                        URL + "/checkin",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                ).getBody()
        );

        response = root.path("response");

        Sequel.menyimpantf2(
                "jakportal_patientjourney",
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                "No.Rawat",
                15,
                new String[]{
                    noRawatpasien,
                    response.path("idcheckin").asText(),
                    tglReg + " " + jamReg,
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00"
                }
        );

    } catch (Exception ex) {
        System.out.println("Notifikasi : " + ex);

        if (ex.toString().contains("UnknownHostException")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Koneksi ke server JAKPORTAL terputus...!");
        }
    }
}
    
    private void CheckInEnd(String noRawatpasien, String idCheckin, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/checkinselesai", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + response);
            Sequel.mengedit("jakportal_patientjourney", "no_rawat=?", " id_checkin_end=?,id_checkin_end_datetime=?", 3, new String[]{response.path("idcheckinselesai").asText(), tglReg + " " + jamReg, noRawatpasien});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void NurseStation(String noRawatpasien, String idCheckin, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/ns", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + response);
            Sequel.mengedit("jakportal_patientjourney", "no_rawat=?", " id_ns=?,id_ns_datetime=?", 3, new String[]{response.path("idns").asText(), tglReg + " " + jamReg, noRawatpasien});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void NurseStationEnd(String noRawatpasien, String idCheckin, String idNs, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idns\": \"" + idNs + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/nsselesai", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + response);
            Sequel.mengedit("jakportal_patientjourney", "no_rawat=?", " id_ns_end=?,id_ns_end_datetime=?", 3, new String[]{response.path("idnsselesai").asText(), tglReg + " " + jamReg, noRawatpasien});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void Poliklinik(String noRawatpasien, String idCheckin, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/poli", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + response);
            Sequel.mengedit("jakportal_patientjourney", "no_rawat=?", " id_poli=?,id_poli_datetime=?", 3, new String[]{response.path("idpoli").asText(), tglReg + " " + jamReg, noRawatpasien});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void PoliklinikEnd(String noRawatpasien, String idCheckin, String idPoli, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idpoli\": \"" + idPoli + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/poliselesai", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + response);
            Sequel.mengedit("jakportal_patientjourney", "no_rawat=?", " id_poli_end=?,id_poli_end_datetime=?", 3, new String[]{response.path("idpoliselesai").asText(), tglReg + " " + jamReg, noRawatpasien});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void ResepPermintaan(String noRawatpasien, String idCheckin, String JenisResep, String tglReg, String jamReg) {
try {

        // Ambil No Resep dari tabel resep_dokter
        String noResep = Sequel.cariIsi(
    "SELECT no_resep FROM resep_dokter WHERE no_rawat=? ORDER BY tgl_perawatan DESC, jam DESC LIMIT 1",
    noRawatpasien
);

if(noResep == null || noResep.equals("")){
    noResep = Sequel.cariIsi(
        "SELECT no_resep FROM resep_obat WHERE no_rawat=? ORDER BY tgl_perawatan DESC, jam DESC LIMIT 1",
        noRawatpasien
    );
}

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
        headers.add("x-token", api.Token());

        requestJson = "{"
                + "\"idcheckin\": \"" + idCheckin + "\","
                + "\"noresep\": \"" + noResep + "\","
                + "\"jenis\": \"" + JenisResep + "\","
                + "\"tanggal\": \"" + tglReg + "\","
                + "\"jam\": \"" + jamReg + "\""
                + "}";

        // System.out.println("Notifikasi : " + requestJson);

        requestEntity = new HttpEntity(requestJson, headers);
        URL = koneksiDB.JAKPORTALURL();

        root = mapper.readTree(
                api.getRest().exchange(
                        URL + "/reseppermintaan",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                ).getBody()
        );

        response = root.path("response");

        // Simpan hasil ke tabel lokal
        Sequel.menyimpantf2(
                "jakportal_patientjourney_resep",
                "?,?,?,?,?,?,?,?",
                "No.Rawat",
                8,
                new String[]{
                    noRawatpasien,
                    idCheckin,
                    response.path("idreseppermintaan").asText(),
                    tglReg + " " + jamReg,
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00"
                }
        );

    } catch (Exception ex) {

        System.out.println("Notifikasi : " + ex);

        if (ex.toString().contains("UnknownHostException")) {
            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server Dinkes terputus...!");
        }
    }
    }

    private void ResepValidasi(String noRawatpasien, String idCheckin, String idPermintaan, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idreseppermintaan\": \"" + idPermintaan + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/resepvalidasi", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + root);
            Sequel.mengedit("jakportal_patientjourney_resep", "id_permintaan=?", " id_validasi=?,id_validasi_datetime=?", 3, new String[]{response.path("idresepvalidasi").asText(), tglReg + " " + jamReg, idPermintaan});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void ResepPenyerahan(String noRawatpasien, String idCheckin, String idValidasi, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idresepvalidasi\": \"" + idValidasi + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/reseppenyerahan", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + root);
            Sequel.mengedit("jakportal_patientjourney_resep", "id_validasi=?", " id_penyerahan=?,id_penyerahan_datetime=?", 3, new String[]{response.path("idreseppenyerahan").asText(), tglReg + " " + jamReg, idValidasi});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void LaboratoriumPermintaan(String noRawatpasien, String idCheckin, String JenisLab, String tglReg, String jamReg) {
try {

        // Ambil No Order Lab dari SIMRS
        String noOrder = Sequel.cariIsi(
            "SELECT noorder FROM permintaan_lab WHERE no_rawat=? ORDER BY tgl_permintaan DESC, jam_permintaan DESC LIMIT 1",
            noRawatpasien
        );

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
        headers.add("x-token", api.Token());

        requestJson = "{"
                + "\"idcheckin\": \"" + idCheckin + "\","
                + "\"noorder\": \"" + noOrder + "\","
                + "\"jenis\": \"" + JenisLab + "\","
                + "\"tanggal\": \"" + tglReg + "\","
                + "\"jam\": \"" + jamReg + "\""
                + "}";

        requestEntity = new HttpEntity(requestJson, headers);
        URL = koneksiDB.JAKPORTALURL();

        root = mapper.readTree(
                api.getRest().exchange(
                        URL + "/labpermintaan",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                ).getBody()
        );

        response = root.path("response");

        Sequel.menyimpantf2(
                "jakportal_patientjourney_laboratorium",
                "?,?,?,?,?,?,?,?",
                "No.Rawat",
                8,
                new String[]{
                    noRawatpasien,
                    idCheckin,
                    response.path("idlabpermintaan").asText(),
                    tglReg + " " + jamReg,
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00"
                }
        );

    } catch (Exception ex) {
        System.out.println("Notifikasi : " + ex);

        if (ex.toString().contains("UnknownHostException")) {
            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server Dinkes terputus...!");
        }
    }
    }

    private void LaboratoriumSampel(String noRawatpasien, String idCheckin, String idPermintaan, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idlabpermintaan\": \"" + idPermintaan + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/labsampel", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + root);
            Sequel.mengedit("jakportal_patientjourney_laboratorium", "id_permintaan=?", " id_sampel=?,id_sampel_datetime=?", 3, new String[]{response.path("idlabsampel").asText(), tglReg + " " + jamReg, idPermintaan});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void LaboratoriumHasil(String noRawatpasien, String idCheckin, String idSampel, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idlabsampel\": \"" + idSampel + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/labhasil", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + root);
            Sequel.mengedit("jakportal_patientjourney_laboratorium", "id_sampel=?", " id_hasil=?,id_hasil_datetime=?", 3, new String[]{response.path("idlabhasil").asText(), tglReg + " " + jamReg, idSampel});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void RadiologiPermintaan(String noRawatpasien, String idCheckin, String tglReg, String jamReg) {
try {

        // Ambil No Order Radiologi dari SIMRS
        String noOrder = Sequel.cariIsi(
            "SELECT noorder FROM permintaan_radiologi WHERE no_rawat=? ORDER BY tgl_permintaan DESC, jam_permintaan DESC LIMIT 1",
            noRawatpasien
        );

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
        headers.add("x-token", api.Token());

        requestJson = "{"
                + "\"idcheckin\": \"" + idCheckin + "\","
                + "\"noorder\": \"" + noOrder + "\","
                + "\"tanggal\": \"" + tglReg + "\","
                + "\"jam\": \"" + jamReg + "\""
                + "}";

        requestEntity = new HttpEntity(requestJson, headers);
        URL = koneksiDB.JAKPORTALURL();

        root = mapper.readTree(
                api.getRest().exchange(
                        URL + "/radiologipermintaan",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                ).getBody()
        );

        response = root.path("response");

        Sequel.menyimpantf2(
                "jakportal_patientjourney_radiologi",
                "?,?,?,?,?,?,?,?",
                "No.Rawat",
                8,
                new String[]{
                    noRawatpasien,
                    idCheckin,
                    response.path("idradiologipermintaan").asText(),
                    tglReg + " " + jamReg,
                    "",
                    "0000-00-00 00:00:00",
                    "",
                    "0000-00-00 00:00:00"
                }
        );

    } catch (Exception ex) {
        System.out.println("Notifikasi : " + ex);

        if (ex.toString().contains("UnknownHostException")) {
            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server Dinkes terputus...!");
        }
    }
    }

    private void RadiologiSampel(String noRawatpasien, String idCheckin, String idPermintaan, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idradiologipermintaan\": \"" + idPermintaan + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/radiologisampel", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + root);
            Sequel.mengedit("jakportal_patientjourney_radiologi", "id_permintaan=?", " id_sampel=?,id_sampel_datetime=?", 3, new String[]{response.path("idradiologisampel").asText(), tglReg + " " + jamReg, idPermintaan});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void RadiologiHasil(String noRawatpasien, String idCheckin, String idSampel, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idradiologisampel\": \"" + idSampel + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/radiologihasil", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + root);
            Sequel.mengedit("jakportal_patientjourney_radiologi", "id_sampel=?", " id_hasil=?,id_hasil_datetime=?", 3, new String[]{response.path("idradiologihasil").asText(), tglReg + " " + jamReg, idSampel});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void CheckOut(String noRawatpasien, String idCheckin, String idNsSelesai, String idPoliSelesai, String tglReg, String jamReg) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-username", koneksiDB.JAKPORTALUSERNAME());
            headers.add("x-token", api.Token());
            requestJson = "{"
                    + "\"idcheckin\": \"" + idCheckin + "\","
                    + "\"idnsselesai\": \"" + idNsSelesai + "\","
                    + "\"idpoliselesai\": \"" + idPoliSelesai + "\","
                    + "\"tanggal\": \"" + tglReg + "\","
                    + "\"jam\": \"" + jamReg + "\""
                    + "}";
//            System.out.println("Notifikasi : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            URL = koneksiDB.JAKPORTALURL();
            root = mapper.readTree(api.getRest().exchange(URL + "/checkout", HttpMethod.POST, requestEntity, String.class).getBody());
            response = root.path("response");
//            System.out.println("Notifikasi : " + response);
            Sequel.mengedit("jakportal_patientjourney", "no_rawat=?", " id_check_out=?,id_check_out_datetime=?", 3, new String[]{response.path("idcheckout").asText(), tglReg + " " + jamReg, noRawatpasien});

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }
    

//    private class BackgroundWorker extends SwingWorker<String, Integer> {
//
//        @Override
//        protected String doInBackground() throws Exception {
//            DlgLoading.setSize(550, 14);
//            DlgLoading.setLocationRelativeTo(internalFrame1);
//            DlgLoading.setVisible(true);
//            DlgLoading.setAlwaysOnTop(true);
//            jPanel3.setVisible(false);
//            TabRawat.setVisible(false);
//            return "finished";
//        }
//
//        protected void process(List<Integer> chunks) {
////            this.loadReg="";
//            progressBar.setValue(chunks.get(chunks.size() - 1));
//        }
//        
//    }
    public static int randomNomor() {
    return (int) (Math.random() * 7) + 1;
}
}
