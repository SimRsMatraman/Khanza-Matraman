/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;

/**
 *
 * @author dosen
 */
public final class MasterCariTemplateRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDiagnosa,tabModeProsedur,tabModeRadiologi,tabModePK,tabModeDetailPK,
                tabModePA,tabModeMB,tabModeDetailMB,tabModeObatUmum,tabModeObatRacikan,tabModeDetailObatRacikan,
                TabModeTindakan;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0;
    private String la="",ld="",pa="",pd="",kodedokter="",tanggaldilakukan="",jamdilakukan="",noperawatan="",norm="",nomor="",
            Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",
            HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="",namaObat="",ListObat="",ListObat1="",ListObat2="",ListObat3="";
    private boolean sukses=true;
    private double ttljmdokter=0,ttlkso=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0,ttlpendapatan=0;
    private Jurnal jur=new Jurnal();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public MasterCariTemplateRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"Nama Template","Kode Dokter","Nama Dokter"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDiagnosa=new DefaultTableModel(null,new Object[]{"Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Ktg.Penyakit","Ciri-ciri Umum"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i= 0; i < 6; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(280);
            }else if(i==2){
                column.setPreferredWidth(285);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(75);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeProsedur=new DefaultTableModel(null,new Object[]{"Kode","Deskripsi Panjang","Deskripsi Pendek"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(350);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRadiologi=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanRadiologi.setModel(tabModeRadiologi);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaanRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(490);
            }
        }
        tbPermintaanRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePK=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPK.setModel(tabModePK);

        tbPermintaanPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPK=new DefaultTableModel(null,new Object[]{"Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailPK.setModel(tabModeDetailPK);
        tbDetailPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDetailPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(326);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(315);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        
        tbDetailPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePA=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPA.setModel(tabModePA);

        tbPermintaanPA.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanPA.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPA.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMB=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanMB.setModel(tabModeMB);

        tbPermintaanMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMB=new DefaultTableModel(null,new Object[]{"Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailMB.setModel(tabModeDetailMB);
        tbDetailMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDetailMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(326);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);  
            }else if(i==2){
                column.setPreferredWidth(315);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        tbDetailMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObatUmum=new DefaultTableModel(null,new Object[]{
                "Jumlah","Aturan Pakai","Kode Barang","Nama Barang","Satuan","Komposisi","Jenis Obat","I.F.","Kapasitas"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObatNonRacikan.setModel(tabModeObatUmum);
        tbObatNonRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatNonRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 9; i++) {
            TableColumn column = tbObatNonRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(75);
            }else if(i==3){
                column.setPreferredWidth(240);
            }else if(i==4){
//                column.setPreferredWidth(110);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
//                column.setPreferredWidth(110);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
//                column.setPreferredWidth(130);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }                
        }
        tbObatNonRacikan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{"No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik","Aturan Pakai","Keterangan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }
        tbObatRacikan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Satuan","Jenis Obat","Kps","P1","/","P2","Kandungan","Jml","I.F.","Komposisi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}            
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 13; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setMinWidth(11);
                column.setMaxWidth(11);
            }else if(i==8){
                column.setPreferredWidth(25);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }

        tbDetailObatRacikan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakan=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Perawatan/Tindakan","Kategori","Tarif/Biaya","Bagian RS","BHP","JM Dokter","KSO","Menejemen"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 9; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(380);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        
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
                if(akses.getform().equals("DlgRawatJalan")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                            KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDokter.requestFocus();
                    }                      
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(petugas.getTable().getSelectedRow()!= -1){ 
                            KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            KdPetugas.requestFocus();                       
                    }            
                    
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
        
        try {
            ps=koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,set_akun_ralan.Utang_KSO_Tindakan_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    Suspen_Piutang_Tindakan_Ralan=rs.getString("Suspen_Piutang_Tindakan_Ralan");
                    Tindakan_Ralan=rs.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rs.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rs.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan=rs.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan=rs.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan=rs.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan=rs.getString("Persediaan_BHP_Tindakan_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
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

        jLabel40 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Subjek = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Objek = new widget.TextArea();
        jLabel42 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Asesmen = new widget.TextArea();
        jLabel43 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Plan = new widget.TextArea();
        jLabel44 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Instruksi = new widget.TextArea();
        jLabel45 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        Evaluasi = new widget.TextArea();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbPermintaanRadiologi = new widget.Table();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Scroll4 = new widget.ScrollPane();
        tbPermintaanPK = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbDetailPK = new widget.Table();
        jLabel17 = new widget.Label();
        Scroll6 = new widget.ScrollPane();
        tbPermintaanPA = new widget.Table();
        jLabel18 = new widget.Label();
        Scroll7 = new widget.ScrollPane();
        tbPermintaanMB = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbDetailMB = new widget.Table();
        jLabel20 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnTambah = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane2 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel19 = new widget.Label();
        Scroll9 = new widget.ScrollPane();
        tbObatNonRacikan = new widget.Table();
        jLabel21 = new widget.Label();
        Scroll12 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnSeekPetugas = new widget.Button();
        NmPetugas = new widget.TextBox();
        KdPetugas = new widget.TextBox();
        jLabel22 = new widget.Label();

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Subjek :");
        jLabel40.setName("jLabel40"); // NOI18N

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Subjek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Subjek.setColumns(20);
        Subjek.setRows(7);
        Subjek.setName("Subjek"); // NOI18N
        scrollPane3.setViewportView(Subjek);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Objek :");
        jLabel41.setName("jLabel41"); // NOI18N

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Objek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Objek.setColumns(20);
        Objek.setRows(7);
        Objek.setName("Objek"); // NOI18N
        scrollPane4.setViewportView(Objek);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Asesmen :");
        jLabel42.setName("jLabel42"); // NOI18N

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Asesmen.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Asesmen.setColumns(20);
        Asesmen.setRows(7);
        Asesmen.setName("Asesmen"); // NOI18N
        scrollPane5.setViewportView(Asesmen);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Plan :");
        jLabel43.setName("jLabel43"); // NOI18N

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Plan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Plan.setColumns(20);
        Plan.setRows(7);
        Plan.setName("Plan"); // NOI18N
        scrollPane6.setViewportView(Plan);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Instruksi :");
        jLabel44.setName("jLabel44"); // NOI18N

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Instruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Instruksi.setColumns(20);
        Instruksi.setRows(7);
        Instruksi.setName("Instruksi"); // NOI18N
        scrollPane7.setViewportView(Instruksi);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Evaluasi :");
        jLabel45.setName("jLabel45"); // NOI18N

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Evaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Evaluasi.setColumns(20);
        Evaluasi.setRows(7);
        Evaluasi.setName("Evaluasi"); // NOI18N
        scrollPane8.setViewportView(Evaluasi);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        Scroll1.setViewportView(tbDiagnosa);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Diagnosa :");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Prosedur :");
        jLabel14.setName("jLabel14"); // NOI18N

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbProsedur.setName("tbProsedur"); // NOI18N
        Scroll2.setViewportView(tbProsedur);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPermintaanRadiologi.setName("tbPermintaanRadiologi"); // NOI18N
        Scroll3.setViewportView(tbPermintaanRadiologi);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Permintaan Radiologi :");
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Permintaan Laborat Patologi Klinis :");
        jLabel16.setName("jLabel16"); // NOI18N

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPermintaanPK.setName("tbPermintaanPK"); // NOI18N
        Scroll4.setViewportView(tbPermintaanPK);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbDetailPK.setName("tbDetailPK"); // NOI18N
        Scroll5.setViewportView(tbDetailPK);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Permintaan Laborat Patologi Anatomi :");
        jLabel17.setName("jLabel17"); // NOI18N

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbPermintaanPA.setName("tbPermintaanPA"); // NOI18N
        Scroll6.setViewportView(tbPermintaanPA);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Permintaan Laborat Mikrobiologi & Bio Molekuler :");
        jLabel18.setName("jLabel18"); // NOI18N

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbPermintaanMB.setName("tbPermintaanMB"); // NOI18N
        Scroll7.setViewportView(tbPermintaanMB);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbDetailMB.setName("tbDetailMB"); // NOI18N
        Scroll8.setViewportView(tbDetailMB);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Obat Racikan :");
        jLabel20.setName("jLabel20"); // NOI18N

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        Scroll10.setViewportView(tbObatRacikan);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        Scroll11.setViewportView(tbDetailObatRacikan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Pemeriksaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(310, 402));

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbDokter);

        internalFrame1.add(Scroll, java.awt.BorderLayout.WEST);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi3.add(BtnAll);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 23));
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
        panelisi3.add(BtnSimpan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "Detail Template :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setPreferredSize(new java.awt.Dimension(740, 797));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(730, 797));
        FormInput.setLayout(null);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Obat Non Racikan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(40, 80, 270, 23);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbObatNonRacikan.setName("tbObatNonRacikan"); // NOI18N
        Scroll9.setViewportView(tbObatNonRacikan);

        FormInput.add(Scroll9);
        Scroll9.setBounds(40, 100, 700, 220);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Tindakan :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(40, 340, 120, 23);

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbTindakan.setName("tbTindakan"); // NOI18N
        Scroll12.setViewportView(tbTindakan);

        FormInput.add(Scroll12);
        Scroll12.setBounds(40, 360, 700, 300);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(10, 20, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(90, 20, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(220, 20, 330, 23);

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
        BtnDokter.setBounds(560, 20, 28, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(560, 50, 28, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setBackground(new java.awt.Color(202, 202, 202));
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmPetugasActionPerformed(evt);
            }
        });
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(220, 50, 330, 23);

        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPetugasActionPerformed(evt);
            }
        });
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(90, 50, 120, 23);

        jLabel22.setText("Petugas :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(10, 50, 70, 23);

        scrollPane2.setViewportView(FormInput);

        internalFrame1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        MasterTemplatePemeriksaanDokter form=new MasterTemplatePemeriksaanDokter(null,false);
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.emptTeks();
        form.isCek();
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
        
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbDokter.getSelectedRow()>-1){
                try {
//                    Valid.tabelKosong(tabModeDiagnosa);
//                    ps=koneksi.prepareStatement(
//                            "select template_pemeriksaan_dokter_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
//                            "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum from template_pemeriksaan_dokter_penyakit "+
//                            "inner join penyakit on penyakit.kd_penyakit=template_pemeriksaan_dokter_penyakit.kd_penyakit "+
//                            "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg where "+
//                            "template_pemeriksaan_dokter_penyakit.no_template=? order by template_pemeriksaan_dokter_penyakit.urut");
//                    try {
//                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
//                        rs=ps.executeQuery();
//                        while(rs.next()){
//                            tabModeDiagnosa.addRow(new Object[]{
//                                rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)
//                            });
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : "+e);
//                    } finally{
//                        if(rs!=null){
//                            rs.close();
//                        }
//                        if(ps!=null){
//                            ps.close();
//                        }
//                    }
//                    
//                    Valid.tabelKosong(tabModeProsedur);
//                    ps=koneksi.prepareStatement(
//                            "select template_pemeriksaan_dokter_prosedur.kode,icd9.deskripsi_panjang,icd9.deskripsi_pendek from template_pemeriksaan_dokter_prosedur "+
//                            "inner join icd9 on template_pemeriksaan_dokter_prosedur.kode=icd9.kode where template_pemeriksaan_dokter_prosedur.no_template=? "+
//                            "order by template_pemeriksaan_dokter_prosedur.urut");
//                    try {
//                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
//                        rs=ps.executeQuery();
//                        while(rs.next()){
//                            tabModeProsedur.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3)});
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : "+e);
//                    } finally{
//                        if(rs!=null){
//                            rs.close();
//                        }
//                        if(ps!=null){
//                            ps.close();
//                        }
//                    }
                    
                    Valid.tabelKosong(tabModeRadiologi);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan from template_pemeriksaan_dokter_permintaan_radiologi "+
                            "inner join jns_perawatan_radiologi on template_pemeriksaan_dokter_permintaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_radiologi.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeRadiologi.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
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
                    
                    Valid.tabelKosong(tabModePK);
                    Valid.tabelKosong(tabModeDetailPK);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PK'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModePK.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
                            try {
                                tabModeDetailPK.addRow(new Object[]{rs.getString("nm_perawatan"),"","","",""});
                                ps2=koneksi.prepareStatement(
                                        "select template_pemeriksaan_dokter_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                        "from template_pemeriksaan_dokter_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_dokter_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_dokter_detail_permintaan_lab.no_template=? and "+
                                        "template_pemeriksaan_dokter_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                ps2.setString(2,rs.getString("kd_jenis_prw"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    la="";ld="";pa="";pd="";
                                    if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                        ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                    }
                                    if(!rs2.getString("nilai_rujukan_la").equals("")){
                                        la=", LA : "+rs2.getString("nilai_rujukan_la");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                        pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                        pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                    }
                                    tabModeDetailPK.addRow(new Object[]{
                                        "   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
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
                    
                    Valid.tabelKosong(tabModePA);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PA'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModePA.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
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
                    
                    Valid.tabelKosong(tabModeMB);
                    Valid.tabelKosong(tabModeDetailMB);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='MB'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeMB.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
                            try {
                                tabModeDetailMB.addRow(new Object[]{rs.getString("nm_perawatan"),"","","",""});
                                ps2=koneksi.prepareStatement(
                                        "select template_pemeriksaan_dokter_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                        "from template_pemeriksaan_dokter_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_dokter_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_dokter_detail_permintaan_lab.no_template=? and "+
                                        "template_pemeriksaan_dokter_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                ps2.setString(2,rs.getString("kd_jenis_prw"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    la="";ld="";pa="";pd="";
                                    if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                        ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                    }
                                    if(!rs2.getString("nilai_rujukan_la").equals("")){
                                        la=", LA : "+rs2.getString("nilai_rujukan_la");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                        pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                        pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                    }
                                    tabModeDetailMB.addRow(new Object[]{
                                        "   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
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
                    
                    Valid.tabelKosong(tabModeObatUmum);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_resep.kode_brng,databarang.nama_brng,kodesatuan.satuan,template_pemeriksaan_dokter_resep.jml,template_pemeriksaan_dokter_resep.aturan_pakai,jenis.nama,industrifarmasi.nama_industri, "+
                            "databarang.kapasitas,databarang.letak_barang from template_pemeriksaan_dokter_resep inner join databarang on template_pemeriksaan_dokter_resep.kode_brng=databarang.kode_brng inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat "+
                            "inner join jenis on databarang.kdjns=jenis.kdjns inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri where template_pemeriksaan_dokter_resep.no_template=? order by databarang.nama_brng");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeObatUmum.addRow(new Object[]{
                                rs.getString("jml"),rs.getString("aturan_pakai"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("letak_barang"),rs.getString("nama"),rs.getString("nama_industri"),rs.getDouble("kapasitas")
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
                    
                    Valid.tabelKosong(tabModeObatRacikan);
                    Valid.tabelKosong(tabModeDetailObatRacikan);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_resep_racikan.no_racik,template_pemeriksaan_dokter_resep_racikan.kd_racik,template_pemeriksaan_dokter_resep_racikan.nama_racik,metode_racik.nm_racik,template_pemeriksaan_dokter_resep_racikan.jml_dr,template_pemeriksaan_dokter_resep_racikan.aturan_pakai,"+
                            "template_pemeriksaan_dokter_resep_racikan.keterangan from template_pemeriksaan_dokter_resep_racikan inner join metode_racik on metode_racik.kd_racik=template_pemeriksaan_dokter_resep_racikan.kd_racik where template_pemeriksaan_dokter_resep_racikan.no_template=? "+
                            "order by template_pemeriksaan_dokter_resep_racikan.no_racik");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeObatRacikan.addRow(new Object[]{
                                rs.getString("no_racik"),rs.getString("nama_racik"),rs.getString("kd_racik"),rs.getString("nm_racik"),rs.getString("jml_dr"),rs.getString("aturan_pakai"),rs.getString("keterangan")
                            });
                            try {
                                ps2=koneksi.prepareStatement(
                                        "select template_pemeriksaan_dokter_resep_racikan_detail.kode_brng,databarang.nama_brng,kodesatuan.satuan,template_pemeriksaan_dokter_resep_racikan_detail.jml,jenis.nama,"+
                                        "databarang.kapasitas,template_pemeriksaan_dokter_resep_racikan_detail.p1,template_pemeriksaan_dokter_resep_racikan_detail.p2,template_pemeriksaan_dokter_resep_racikan_detail.kandungan,"+
                                        "industrifarmasi.nama_industri,databarang.letak_barang from template_pemeriksaan_dokter_resep_racikan_detail inner join databarang on template_pemeriksaan_dokter_resep_racikan_detail.kode_brng=databarang.kode_brng "+
                                        "inner join jenis on databarang.kdjns=jenis.kdjns inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri "+
                                        "where template_pemeriksaan_dokter_resep_racikan_detail.no_template=? and template_pemeriksaan_dokter_resep_racikan_detail.no_racik=? order by template_pemeriksaan_dokter_resep_racikan_detail.kode_brng");
                                ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                ps2.setString(2,rs.getString("no_racik"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rs.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("satuan"),
                                        rs2.getString("nama"),rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),rs2.getString("kandungan"),
                                        rs2.getDouble("jml"),rs2.getString("nama_industri"),rs2.getString("letak_barang")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
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

                    Valid.tabelKosong(TabModeTindakan);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_tindakan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,jns_perawatan.total_byrdr,jns_perawatan.bhp,jns_perawatan.material,"+
                            "jns_perawatan.tarif_tindakandr,jns_perawatan.kso,jns_perawatan.menejemen from template_pemeriksaan_dokter_tindakan inner join jns_perawatan "+
                            "on template_pemeriksaan_dokter_tindakan.kd_jenis_prw=jns_perawatan.kd_jenis_prw inner join kategori_perawatan on kategori_perawatan.kd_kategori=jns_perawatan.kd_kategori "+
                            "where template_pemeriksaan_dokter_tindakan.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            TabModeTindakan.addRow(new Object[]{
                                rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("nm_kategori"),rs.getDouble("total_byrdr"),
                                rs.getDouble("material"),rs.getDouble("bhp"),rs.getDouble("tarif_tindakandr"),rs.getDouble("kso"),rs.getDouble("menejemen")
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
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
//        if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
//            Valid.textKosong(BtnDokter,"Dokter");
//        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
//            Valid.textKosong(BtnSeekPetugas,"Petugas");
//        }else{
        if(tbDokter.getSelectedRow()>-1){
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("pemeriksaan_ralan_igd","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                        noperawatan,tanggaldilakukan,jamdilakukan,"","","","","","","","Compos Mentis 15-14","","","","","","","","","","","",KdDokter.getText(),""}
                    )==true){
                    
//                    for(i=0;i<tbDiagnosa.getRowCount();i++){ 
//                        if(Sequel.cariInteger(
//                                "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien inner join reg_periksa on diagnosa_pasien.no_rawat=reg_periksa.no_rawat inner join pasien "+
//                                "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where pasien.no_rkm_medis='"+norm+"' and diagnosa_pasien.kd_penyakit='"+tbDiagnosa.getValueAt(i,1).toString()+"'")>0){
//                            if(Sequel.menyimpantf2("diagnosa_pasien","?,?,?,?,?","Penyakit "+tbDiagnosa.getValueAt(i,1).toString(),5,new String[]{
//                                    noperawatan,tbDiagnosa.getValueAt(i,0).toString(),"Ralan",Sequel.cariIsi("select ifnull(MAX(diagnosa_pasien.prioritas)+1,1) from diagnosa_pasien where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ralan'",noperawatan),"Lama"
//                                })==false){
//                                sukses=false;
//                            }
//                        }else{
//                            if(Sequel.menyimpantf2("diagnosa_pasien","?,?,?,?,?","Penyakit "+tbDiagnosa.getValueAt(i,1).toString(),5,new String[]{
//                                    noperawatan,tbDiagnosa.getValueAt(i,0).toString(),"Ralan",Sequel.cariIsi("select ifnull(MAX(diagnosa_pasien.prioritas)+1,1) from diagnosa_pasien where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ralan'",noperawatan),"Baru"
//                                })==false){
//                                sukses=false;
//                            }
//                        } 
//                    }
//                    
//                    for(i=0;i<tbProsedur.getRowCount();i++){ 
//                        if(Sequel.menyimpantf2("prosedur_pasien","?,?,?,?","Prosedur "+tbProsedur.getValueAt(i,1).toString(),4,new String[]{
//                                noperawatan,tbProsedur.getValueAt(i,0).toString(),"Ralan",Sequel.cariIsi("select ifnull(MAX(prosedur_pasien.prioritas)+1,1) from prosedur_pasien where prosedur_pasien.no_rawat=? and prosedur_pasien.status='Ralan'",noperawatan)
//                            })==false){
//                            sukses=false;
//                        }
//                    }
//                    
//                    if(tabModeRadiologi.getRowCount()>0){
//                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_radiologi.noorder,4),signed)),0) from permintaan_radiologi where permintaan_radiologi.tgl_permintaan='"+tanggaldilakukan+"'","PR"+tanggaldilakukan.replaceAll("-",""),4);
//                        if(Sequel.menyimpantf2("permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan Radiologi",13,new String[]{
//                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",KdDokter.getText(),"ralan","-",Asesmen.getText(),"No Alarm"
//                            })==true){
//                            for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){ 
//                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_radiologi","?,?,?","Permintaan Radiologi "+tbPermintaanRadiologi.getValueAt(i,1).toString(),3,new String[]{
//                                        nomor,tbPermintaanRadiologi.getValueAt(i,0).toString(),"Belum"
//                                    })==false){
//                                    sukses=false;
//                                }             
//                            } 
//                        }else{
//                            sukses=false;
//                        }
//                    }
//                    
//                    if(tabModePK.getRowCount()>0){
//                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_lab.noorder,4),signed)),0) from permintaan_lab where permintaan_lab.tgl_permintaan='"+tanggaldilakukan+"' ","PK"+tanggaldilakukan.replaceAll("-",""),4);   
//                        if(Sequel.menyimpantf2("permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",13,new String[]{
//                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",KdDokter.getText(),"ralan","-",Asesmen.getText(),"No Alarm"
//                            })==true){
//                            for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
//                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_lab","?,?,?","Permintaan Lab "+tbPermintaanPK.getValueAt(i,1).toString(),3,new String[]{
//                                        nomor,tbPermintaanPK.getValueAt(i,0).toString(),"Belum"
//                                    })==false){
//                                    sukses=false;
//                                }                  
//                            } 
//
//                            for(i=0;i<tbDetailPK.getRowCount();i++){ 
//                                if(!tbDetailPK.getValueAt(i,3).toString().equals("")){                                
//                                    if(Sequel.menyimpantf2("permintaan_detail_permintaan_lab","?,?,?,?","Detail Permintaan Lab "+tbDetailPK.getValueAt(i,0).toString().replaceAll("   ",""),4,new String[]{
//                                            nomor,tbDetailPK.getValueAt(i,4).toString(),tbDetailPK.getValueAt(i,3).toString(),"Belum"
//                                        })==false){
//                                        sukses=false;
//                                    }
//                                }                        
//                            }
//                        }else{
//                            sukses=false;
//                        }
//                    }
//                    
//                    if(tabModePA.getRowCount()>0){
//                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_labpa.noorder,4),signed)),0) from permintaan_labpa where permintaan_labpa.tgl_permintaan='"+tanggaldilakukan+"' ","PA"+tanggaldilakukan.replaceAll("-",""),4); 
//                        if(Sequel.menyimpantf2("permintaan_labpa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",20,new String[]{
//                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",KdDokter.getText(),"ralan","-",Asesmen.getText(),tanggaldilakukan,"-","-","-","-","0000-00-00","-","-"
//                            })==true){
//                            for(i=0;i<tbPermintaanPA.getRowCount();i++){ 
//                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_labpa","?,?,?","Pemeriksaan Lab PA "+tbPermintaanPA.getValueAt(i,1).toString(),3,new String[]{
//                                        nomor,tbPermintaanPA.getValueAt(i,0).toString(),"Belum"
//                                    })==false){
//                                    sukses=false;
//                                }                
//                            } 
//                        }else{
//                            sukses=false;
//                        }
//                    }
//                    
//                    if(tabModeMB.getRowCount()>0){
//                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_labmb.noorder,4),signed)),0) from permintaan_labmb where permintaan_labmb.tgl_permintaan='"+tanggaldilakukan+"' ","MB"+tanggaldilakukan.replaceAll("-",""),4);  
//                        if(Sequel.menyimpantf2("permintaan_labmb","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
//                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",KdDokter.getText(),"ralan","-",Asesmen.getText()
//                            })==true){
//                            for(i=0;i<tbPermintaanMB.getRowCount();i++){ 
//                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_labmb","?,?,?","Permintaan Lab MB"+tbPermintaanMB.getValueAt(i,1).toString(),3,new String[]{
//                                        nomor,tbPermintaanMB.getValueAt(i,0).toString(),"Belum"
//                                    })==false){
//                                    sukses=false;
//                                }                 
//                            } 
//
//                            for(i=0;i<tbDetailMB.getRowCount();i++){ 
//                                if(!tbDetailMB.getValueAt(i,3).toString().equals("")){                                
//                                    if(Sequel.menyimpantf2("permintaan_detail_permintaan_labmb","?,?,?,?","Detail Permintaan Lab MB "+tbDetailMB.getValueAt(i,0).toString().replaceAll("   ",""),4,new String[]{
//                                            nomor,tbDetailMB.getValueAt(i,4).toString(),tbDetailMB.getValueAt(i,3).toString(),"Belum"
//                                        })==false){
//                                        sukses=false;
//                                    }
//                                }                        
//                            }
//                        }else{
//                            sukses=false;
//                        }
//                    }
//                    
                    if((tabModeObatUmum.getRowCount()>0)||(tabModeObatRacikan.getRowCount()>0)){
                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+tanggaldilakukan+"' or resep_obat.tgl_perawatan='"+tanggaldilakukan+"' ",tanggaldilakukan.replaceAll("-",""),4);    
                        if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?,?,?","Nomer Resep",12,new String[]{
                                nomor,"0000-00-00","00:00:00",noperawatan,KdDokter.getText(),tanggaldilakukan,jamdilakukan,"ralan","0000-00-00","00:00:00","","-"
                            })==true){
                            for(i=0;i<tbObatNonRacikan.getRowCount();i++){ 
                                if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,0).toString())>0){ 
                                    if(Sequel.menyimpantf2("resep_dokter","?,?,?,?,?,?","data",6,new String[]{
                                            nomor,tbObatNonRacikan.getValueAt(i,2).toString(),tbObatNonRacikan.getValueAt(i,0).toString(),tbObatNonRacikan.getValueAt(i,1).toString(),"false","-"
                                        })==false){
                                        sukses=false;
                                    } 
                                }
                            }
                            
//                            for(i=0;i<tbObatRacikan.getRowCount();i++){ 
//                                if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
//                                    if(Sequel.menyimpantf2("resep_dokter_racikan","?,?,?,?,?,?,?","resep obat racikan",7,new String[]{
//                                            nomor,tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
//                                            tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
//                                            tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
//                                        })==false){
//                                        sukses=false;
//                                    } 
//                                }
//                            }
//                            
//                            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
//                                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
//                                    if(Sequel.menyimpantf2("resep_dokter_racikan_detail","?,?,?,?,?,?,?","resep dokter racikan detail",7,new String[]{
//                                            nomor,tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),tbDetailObatRacikan.getValueAt(i,6).toString(),
//                                            tbDetailObatRacikan.getValueAt(i,8).toString(),tbDetailObatRacikan.getValueAt(i,9).toString(),tbDetailObatRacikan.getValueAt(i,10).toString()
//                                        })==false){
//                                        sukses=false;
//                                    } 
//                                }
//                            }
                        }else{
                            sukses=false;
                        }
                    }
//                    
                    if(TabModeTindakan.getRowCount()>0){
                        ttljmdokter=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                        for(i=0;i<tbTindakan.getRowCount();i++){ 
                            if(Sequel.menyimpantf2("rawat_jl_drpr","?,?,?,?,?,?,?,?,?,?,?,?,?,?","Tindakan/Pemeriksaan "+tbTindakan.getValueAt(i,1).toString(),14,new String[]{
                                    noperawatan,tbTindakan.getValueAt(i,0).toString(),KdDokter.getText(),KdPetugas.getText(),tanggaldilakukan,jamdilakukan,tbTindakan.getValueAt(i,4).toString(), 
                                    tbTindakan.getValueAt(i,5).toString(),tbTindakan.getValueAt(i,6).toString(),tbTindakan.getValueAt(i,6).toString(),tbTindakan.getValueAt(i,7).toString(), 
                                    tbTindakan.getValueAt(i,8).toString(),tbTindakan.getValueAt(i,3).toString(),"Belum"
                                })==true){
                                ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan.getValueAt(i,3).toString());
                                ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                                ttlbhp=ttlbhp+Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                                ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                                ttlkso=ttlkso+Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                                ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan.getValueAt(i,8).toString());
                            }else{
                                sukses=false;
                            } 
                        }
                        
                        if(sukses==true){
                            Sequel.queryu("delete from tampjurnal");    
                            if(ttlpendapatan>0){
                                Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                            }
                            if(ttljmdokter>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");       
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                               
                            }
                            if(ttlkso>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                                Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                            }
                            if(ttljasasarana>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                            }
                            if(ttlbhp>0){
                                Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                                Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                            }
                            if(ttlmenejemen>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                            }
                            sukses=jur.simpanJurnal(noperawatan,"U","TINDAKAN RAWAT JALAN PASIEN "+noperawatan+" DIPOSTING OLEH "+akses.getkode());     
                        }
                    }
                }else{
                    sukses=false;
                }
                
                if(sukses==true){
                    Sequel.Commit();
                    JOptionPane.showMessageDialog(null,"Sukses");
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, pemrosesan dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
                if(sukses==true){
                    dispose();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data template pemeriksaan terlebih dahulu..!!");
            }
//        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Subjek,BtnTambah);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

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

    private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        akses.setform("DlgRawatJalan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugasActionPerformed

    private void NmPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasActionPerformed

    private void KdPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnDokter,BtnSeekPetugas);
        }
    }//GEN-LAST:event_KdPetugasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterCariTemplateRalan dialog = new MasterCariTemplateRalan(new javax.swing.JFrame(), true);
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
    private widget.TextArea Asesmen;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextArea Evaluasi;
    private widget.PanelBiasa FormInput;
    private widget.TextArea Instruksi;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextArea Objek;
    private widget.TextArea Plan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextArea Subjek;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label label10;
    private widget.Label label14;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    public widget.Table tbDetailMB;
    public widget.Table tbDetailObatRacikan;
    public widget.Table tbDetailPK;
    public widget.Table tbDiagnosa;
    private widget.Table tbDokter;
    public widget.Table tbObatNonRacikan;
    public widget.Table tbObatRacikan;
    public widget.Table tbPermintaanMB;
    public widget.Table tbPermintaanPA;
    public widget.Table tbPermintaanPK;
    public widget.Table tbPermintaanRadiologi;
    public widget.Table tbProsedur;
    public widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select template_pemeriksaan_dokter.no_template,template_pemeriksaan_dokter.kd_dokter,dokter.nm_dokter,"+
                    "template_pemeriksaan_dokter.keluhan,template_pemeriksaan_dokter.pemeriksaan,template_pemeriksaan_dokter.penilaian,"+
                    "template_pemeriksaan_dokter.rencana,template_pemeriksaan_dokter.instruksi,template_pemeriksaan_dokter.evaluasi "+
                    "from template_pemeriksaan_dokter inner join dokter on dokter.kd_dokter=template_pemeriksaan_dokter.kd_dokter "+
                    "order by template_pemeriksaan_dokter.no_template");
                
            try {              
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_template"),rs.getString("kd_dokter"),rs.getString("nm_dokter")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void setDokter(String kode,String tanggal, String jam,String norawat,String nomorrm){
        this.kodedokter=kode;
        this.tanggaldilakukan=tanggal;
        this.jamdilakukan=jam;
        this.noperawatan=norawat;
        this.norm=nomorrm;
//        KdDokter.setText(Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",norawat));
        KdDokter.setText(kodedokter);
        NmDokter.setText(dokter.tampil3(kodedokter));
    }
    
    //
    
    public void isCek(){        
        BtnTambah.setEnabled(akses.gettemplate_pemeriksaan());
    }
}
