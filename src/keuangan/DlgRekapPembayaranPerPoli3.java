/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package keuangan;

import laporan.*;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author perpustakaan
 */
public final class DlgRekapPembayaranPerPoli3 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4,tabMode5,tabMode6,tabMode7;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,pstindakan,pstindakan2;
    private ResultSet rs,rstindakan,rstindakan2;
    private Object x;
    private String judul,jam_masuk,jam_pulang;
    private int i=0,a=0,ttl=0,tth=0 ,xrs=0,dr=0,drpr=0,pr=0; 
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgRekapPembayaranPerPoli3(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        Object[] rowRwJlDr={"No.","Unit Layanan"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLapTindakan.setModel(tabMode);
        tbLapTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLapTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbLapTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        
        
        tabMode2=new DefaultTableModel(null,new Object[] {"No.","Unit Layanan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLapPasien.setModel(tabMode2);
        tbLapPasien.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLapPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbLapPasien.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new Object[] {"No.","Unit Layanan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbranapkamar.setModel(tabMode3);
        tbranapkamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbranapkamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbranapkamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode4=new DefaultTableModel(null,new Object[] {"No.","Unit Layanan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbFarmasi.setModel(tabMode4);
        tbFarmasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbFarmasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbFarmasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode5=new DefaultTableModel(null,new Object[] {"No.","Unit Layanan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLaborat.setModel(tabMode5);
        tbLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode6=new DefaultTableModel(null,new Object[] {"No.","Unit Layanan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRad.setModel(tabMode6);
        tbRad.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRad.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbRad.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode7=new DefaultTableModel(null,new Object[] {"No.","Unit Layanan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbOperasi.setModel(tabMode7);
        tbOperasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbOperasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbOperasi.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
                   
 try {
            ps=koneksi.prepareStatement("SELECT png_jawab FROM penjab "); 
            try{  
                 
                 ttl=0;
                rs=ps.executeQuery();
                while(rs.next()){
                     tabMode.addColumn(rs.getString("png_jawab"));
                     tabMode2.addColumn(rs.getString("png_jawab"));
                     tabMode3.addColumn(rs.getString("png_jawab"));
                     tabMode4.addColumn(rs.getString("png_jawab"));
                     tabMode5.addColumn(rs.getString("png_jawab"));
                     tabMode6.addColumn(rs.getString("png_jawab"));
                     tabMode7.addColumn(rs.getString("png_jawab"));
                    ttl=ttl+1; 
                }
                tabMode.addColumn("Jumlah");
                tabMode2.addColumn("Jumlah");
                tabMode3.addColumn("Jumlah");
                tabMode4.addColumn("Jumlah");
                tabMode5.addColumn("Jumlah");
                tabMode6.addColumn("Jumlah");
                tabMode7.addColumn("Jumlah");
               
                
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbLapTindakan.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
            }  
        }
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbLapPasien.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
            }  
        }
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbranapkamar.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
            }  
        }
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbFarmasi.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
            }
        }
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbLaborat.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
            }
        }
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbRad.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
            }
        }
                for (i=0 ; i < ttl+3; i++) {
            TableColumn column = tbOperasi.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(150);
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
        
        try {            
            pstindakan=koneksi.prepareStatement("select jns_perawatan_lab.nm_perawatan,count(jns_perawatan_lab.nm_perawatan),jns_perawatan_lab.kd_jenis_prw from periksa_lab "+
                    "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                    "where periksa_lab.tgl_periksa between ? and ? and jns_perawatan_lab.nm_perawatan like ? group by jns_perawatan_lab.nm_perawatan ");
            pstindakan2=koneksi.prepareStatement("select template_laboratorium.Pemeriksaan,count(template_laboratorium.Pemeriksaan) from detail_periksa_lab "+
                    "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                    "where detail_periksa_lab.tgl_periksa between ? and ? and template_laboratorium.Pemeriksaan like ? and template_laboratorium.kd_jenis_prw=? group by template_laboratorium.Pemeriksaan ");
            
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

        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        jLabel9 = new widget.Label();
        CmbStatus = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        jLabel7 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        tb_rajal = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbLapTindakan = new widget.Table();
        tb_ranapasal = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbLapPasien = new widget.Table();
        tb_ranapkamar = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbranapkamar = new widget.Table();
        tb_farmasi = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbFarmasi = new widget.Table();
        tb_laborat = new widget.InternalFrame();
        Scroll7 = new widget.ScrollPane();
        tbLaborat = new widget.Table();
        tb_rad = new widget.InternalFrame();
        Scroll8 = new widget.ScrollPane();
        tbRad = new widget.Table();
        tb_operasi = new widget.InternalFrame();
        Scroll9 = new widget.ScrollPane();
        tbOperasi = new widget.Table();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), ".:[ LRekap Pembayaran Per Bagian/Unit Berdasarkan Tanggal Registrasi 3 ]:.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        jLabel9.setText("Shift :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(jLabel9);

        CmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Pagi", "Siang", "Sore", "Malam" }));
        CmbStatus.setName("CmbStatus"); // NOI18N
        CmbStatus.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(CmbStatus);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

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
        panelGlass5.add(BtnCari);

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
        panelGlass5.add(BtnAll);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(jLabel7);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        tb_rajal.setBackground(new java.awt.Color(235, 255, 235));
        tb_rajal.setBorder(null);
        tb_rajal.setName("tb_rajal"); // NOI18N
        tb_rajal.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbLapTindakan.setAutoCreateRowSorter(true);
        tbLapTindakan.setName("tbLapTindakan"); // NOI18N
        Scroll.setViewportView(tbLapTindakan);

        tb_rajal.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("RAWAT JALAN", tb_rajal);

        tb_ranapasal.setBackground(new java.awt.Color(235, 255, 235));
        tb_ranapasal.setBorder(null);
        tb_ranapasal.setName("tb_ranapasal"); // NOI18N
        tb_ranapasal.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbLapPasien.setAutoCreateRowSorter(true);
        tbLapPasien.setName("tbLapPasien"); // NOI18N
        Scroll1.setViewportView(tbLapPasien);

        tb_ranapasal.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("RAWAT INAP [Asal  Pasien]", tb_ranapasal);

        tb_ranapkamar.setBackground(new java.awt.Color(235, 255, 235));
        tb_ranapkamar.setBorder(null);
        tb_ranapkamar.setEnabled(false);
        tb_ranapkamar.setName("tb_ranapkamar"); // NOI18N
        tb_ranapkamar.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbranapkamar.setAutoCreateRowSorter(true);
        tbranapkamar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbranapkamar.setName("tbranapkamar"); // NOI18N
        Scroll2.setViewportView(tbranapkamar);

        tb_ranapkamar.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("RAWAT INAP [Ruang Rawat]", tb_ranapkamar);

        tb_farmasi.setBackground(new java.awt.Color(235, 255, 235));
        tb_farmasi.setBorder(null);
        tb_farmasi.setName("tb_farmasi"); // NOI18N
        tb_farmasi.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbFarmasi.setAutoCreateRowSorter(true);
        tbFarmasi.setName("tbFarmasi"); // NOI18N
        Scroll3.setViewportView(tbFarmasi);

        tb_farmasi.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("FARMASI", tb_farmasi);

        tb_laborat.setBackground(new java.awt.Color(235, 255, 235));
        tb_laborat.setBorder(null);
        tb_laborat.setName("tb_laborat"); // NOI18N
        tb_laborat.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbLaborat.setName("tbLaborat"); // NOI18N
        Scroll7.setViewportView(tbLaborat);

        tb_laborat.add(Scroll7, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("LABORATORIUM", tb_laborat);

        tb_rad.setBackground(new java.awt.Color(235, 255, 235));
        tb_rad.setBorder(null);
        tb_rad.setName("tb_rad"); // NOI18N
        tb_rad.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbRad.setName("tbRad"); // NOI18N
        Scroll8.setViewportView(tbRad);

        tb_rad.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("RADIOLOGI", tb_rad);

        tb_operasi.setBackground(new java.awt.Color(235, 255, 235));
        tb_operasi.setBorder(null);
        tb_operasi.setName("tb_operasi"); // NOI18N
        tb_operasi.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbOperasi.setName("tbOperasi"); // NOI18N
        Scroll9.setViewportView(tbOperasi);

        tb_operasi.add(Scroll9, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("OPERASI", tb_operasi);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Map<String, Object> param = new HashMap<>();         
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("periode",Tgl1.getSelectedItem());   
//            param.put("tanggal",Tgl2.getDate());  
            param.put("logo",Sequel.cariGambar("select logo from setting"));  
            Sequel.queryu("delete from temporary");
            for(int r=0;r<tabMode.getRowCount();r++){ 
                if(!tbLapTindakan.getValueAt(r,0).toString().contains(">>")){
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString()+"','"+
                                    tabMode.getValueAt(r,1).toString()+"','"+
                                    tabMode.getValueAt(r,2).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
                }                    
            }
               
            Valid.MyReport("rptRl38.jasper","report","::[ Formulir RL 3.8 ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
       if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
            JOptionPane.showMessageDialog(null,"Maaf, page ini masih dalam tahap developing");
//            tampil3();
        }else if(TabRawat.getSelectedIndex()==3){
            tampil4();
        }else if(TabRawat.getSelectedIndex()==4){
            tampil5();
        }else if(TabRawat.getSelectedIndex()==5){
            tampil6();
        }else if(TabRawat.getSelectedIndex()==6){
            tampil7();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
           TCari.setText("");
           tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tampil();

    }//GEN-LAST:event_formWindowActivated

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
       if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
            JOptionPane.showMessageDialog(null,"Maaf, page ini masih dalam tahap developing");
//            tampil3();
        }else if(TabRawat.getSelectedIndex()==3){
            tampil4();
        }else if(TabRawat.getSelectedIndex()==4){
            tampil5();
        }else if(TabRawat.getSelectedIndex()==5){
            tampil6();
        }else if(TabRawat.getSelectedIndex()==6){
            tampil7();
        }
       
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRekapPembayaranPerPoli3 dialog = new DlgRekapPembayaranPerPoli3(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.ComboBox CmbStatus;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private widget.Label label11;
    private widget.panelisi panelGlass5;
    private widget.Table tbFarmasi;
    private widget.Table tbLaborat;
    private widget.Table tbLapPasien;
    private widget.Table tbLapTindakan;
    private widget.Table tbOperasi;
    private widget.Table tbRad;
    private widget.InternalFrame tb_farmasi;
    private widget.InternalFrame tb_laborat;
    private widget.InternalFrame tb_operasi;
    private widget.InternalFrame tb_rad;
    private widget.InternalFrame tb_rajal;
    private widget.InternalFrame tb_ranapasal;
    private widget.InternalFrame tb_ranapkamar;
    private widget.Table tbranapkamar;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);
            if (CmbStatus.getSelectedItem().equals("Semua")   )
            {
            jam_masuk="00:00:00";
            jam_pulang="23:59:59";
            }else{
            jam_masuk=Sequel.cariIsi("select jam_masuk from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            jam_pulang=Sequel.cariIsi("select jam_pulang from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            }
            pstindakan=koneksi.prepareStatement("select nm_poli, kd_poli from poliklinik where nm_poli like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("nm_poli")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(rawat_jl_dr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN rawat_jl_dr ON reg_periksa.no_rawat=rawat_jl_dr.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
                   drpr= Sequel.cariInteger("select sum(rawat_jl_drpr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN rawat_jl_drpr ON reg_periksa.no_rawat=rawat_jl_drpr.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar' group by kd_poli");
                   pr= Sequel.cariInteger("select sum(rawat_jl_pr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN rawat_jl_pr ON reg_periksa.no_rawat=rawat_jl_pr.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar' group by kd_poli");
                   
                   xrs=dr+drpr+pr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(rawat_jl_dr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN rawat_jl_dr ON reg_periksa.no_rawat=rawat_jl_dr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   drpr= Sequel.cariInteger("select sum(rawat_jl_drpr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN rawat_jl_drpr ON reg_periksa.no_rawat=rawat_jl_drpr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%' and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   pr= Sequel.cariInteger("select sum(rawat_jl_pr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN rawat_jl_pr ON reg_periksa.no_rawat=rawat_jl_pr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and  nm_poli like '%"+TCari.getText().trim()+"%' and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr+drpr+pr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
     public void tampil2(){    
         try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode2);   
            if (CmbStatus.getSelectedItem().equals("Semua")   )
            {
            jam_masuk="00:00:00";
            jam_pulang="23:59:59";
            }else{
            jam_masuk=Sequel.cariIsi("select jam_masuk from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            jam_pulang=Sequel.cariIsi("select jam_pulang from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            }
            pstindakan=koneksi.prepareStatement("select nm_poli, kd_poli from poliklinik where nm_poli like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("nm_poli")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(rawat_inap_dr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat JOIN rawat_inap_dr ON reg_periksa.no_rawat=rawat_inap_dr.no_rawat WHERE  nota_inap.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_inap.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
                   drpr= Sequel.cariInteger("select sum(rawat_inap_drpr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat JOIN rawat_inap_drpr ON reg_periksa.no_rawat=rawat_inap_drpr.no_rawat WHERE  nota_inap.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_inap.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar' group by kd_poli");
                   pr= Sequel.cariInteger("select sum(rawat_inap_pr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat JOIN rawat_inap_pr ON reg_periksa.no_rawat=rawat_inap_pr.no_rawat WHERE  nota_inap.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_inap.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar' group by kd_poli");
                   
                   xrs=dr+drpr+pr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode2.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(rawat_inap_dr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat JOIN rawat_inap_dr ON reg_periksa.no_rawat=rawat_inap_dr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_inap.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_inap.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   drpr= Sequel.cariInteger("select sum(rawat_inap_drpr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat JOIN rawat_inap_drpr ON reg_periksa.no_rawat=rawat_inap_drpr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_inap.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_inap.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%' and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   pr= Sequel.cariInteger("select sum(rawat_inap_pr.biaya_rawat) as jumlah_tagihan from  reg_periksa inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat JOIN rawat_inap_pr ON reg_periksa.no_rawat=rawat_inap_pr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_inap.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_inap.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and  nm_poli like '%"+TCari.getText().trim()+"%' and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr+drpr+pr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode2.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
     }
     public void tampil3(){    
         try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode3);   
            pstindakan=koneksi.prepareStatement("select concat(no_bed,' ' ,nm_kamar) as kamar from kamar where no_bed like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("kamar")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
//                   dr= Sequel.cariInteger("select sum(rawat_inap_dr.biaya_rawat) as jumlah_tagihan from  reg_periksa JOIN rawat_inap_dr ON reg_periksa.no_rawat=rawat_inap_dr.no_rawat WHERE  reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
//                   drpr= Sequel.cariInteger("select sum(rawat_inap_drpr.biaya_rawat) as jumlah_tagihan from  reg_periksa JOIN rawat_inap_drpr ON reg_periksa.no_rawat=rawat_inap_drpr.no_rawat WHERE  reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar' group by kd_poli");
//                   pr= Sequel.cariInteger("select sum(rawat_inap_pr.biaya_rawat) as jumlah_tagihan from  reg_periksa JOIN rawat_inap_pr ON reg_periksa.no_rawat=rawat_inap_pr.no_rawat WHERE  reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar' group by kd_poli");
//                   
                   xrs=dr+drpr+pr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode3.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
//                   dr= Sequel.cariInteger("select sum(rawat_inap_dr.biaya_rawat) as jumlah_tagihan from  reg_periksa JOIN rawat_inap_dr ON reg_periksa.no_rawat=rawat_inap_dr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
//                   drpr= Sequel.cariInteger("select sum(rawat_inap_drpr.biaya_rawat) as jumlah_tagihan from  reg_periksa JOIN rawat_inap_drpr ON reg_periksa.no_rawat=rawat_inap_drpr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%' and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
//                   pr= Sequel.cariInteger("select sum(rawat_inap_pr.biaya_rawat) as jumlah_tagihan from  reg_periksa JOIN rawat_inap_pr ON reg_periksa.no_rawat=rawat_inap_pr.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and  nm_poli like '%"+TCari.getText().trim()+"%' and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr+drpr+pr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode3.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
     }
     public void tampil4(){    
         try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode4);   
            if (CmbStatus.getSelectedItem().equals("Semua")   )
            {
            jam_masuk="00:00:00";
            jam_pulang="23:59:59";
            }else{
            jam_masuk=Sequel.cariIsi("select jam_masuk from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            jam_pulang=Sequel.cariIsi("select jam_pulang from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            }
            pstindakan=koneksi.prepareStatement("select nm_poli, kd_poli from poliklinik where nm_poli like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("nm_poli")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(detail_pemberian_obat.total) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN detail_pemberian_obat ON reg_periksa.no_rawat=detail_pemberian_obat.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
                   
                   xrs=dr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode4.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(detail_pemberian_obat.total) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN detail_pemberian_obat ON reg_periksa.no_rawat=detail_pemberian_obat.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode4.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
     }
     public void tampil5(){    
         try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode5);   
            if (CmbStatus.getSelectedItem().equals("Semua")   )
            {
            jam_masuk="00:00:00";
            jam_pulang="23:59:59";
            }else{
            jam_masuk=Sequel.cariIsi("select jam_masuk from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            jam_pulang=Sequel.cariIsi("select jam_pulang from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            }
            pstindakan=koneksi.prepareStatement("select nm_poli, kd_poli from poliklinik where nm_poli like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("nm_poli")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(periksa_lab.biaya) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN periksa_lab ON reg_periksa.no_rawat=periksa_lab.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
                   
                   xrs=dr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode5.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(periksa_lab.biaya) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN periksa_lab ON reg_periksa.no_rawat=periksa_lab.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode5.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
     }
     public void tampil6(){    
         try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode6);   
            if (CmbStatus.getSelectedItem().equals("Semua")   )
            {
            jam_masuk="00:00:00";
            jam_pulang="23:59:59";
            }else{
            jam_masuk=Sequel.cariIsi("select jam_masuk from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            jam_pulang=Sequel.cariIsi("select jam_pulang from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            }
            pstindakan=koneksi.prepareStatement("select nm_poli, kd_poli from poliklinik where nm_poli like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("nm_poli")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(periksa_radiologi.biaya) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN periksa_radiologi ON reg_periksa.no_rawat=periksa_radiologi.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
                   
                   xrs=dr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode6.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(periksa_radiologi.biaya) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN periksa_radiologi ON reg_periksa.no_rawat=periksa_radiologi.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode6.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
     }
     public void tampil7(){    
         try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode7);   
            String jam_masuk=Sequel.cariIsi("select jam_masuk from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            String jam_pulang=Sequel.cariIsi("select jam_pulang from closing_kasir where shift='"+CmbStatus.getSelectedItem()+"'");
            pstindakan=koneksi.prepareStatement("select nm_poli, kd_poli from poliklinik where nm_poli like ?");
            pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            i=1;
            ttl=0;
           
            while(rstindakan.next()){
Object[] xs ={i,rstindakan.getString("nm_poli")};

try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                 tth=0;
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(operasi.bagian_rs) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN operasi ON reg_periksa.no_rawat=operasi.no_rawat WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and reg_periksa.kd_poli='"+rstindakan.getString("kd_poli")+"' and status_bayar='Sudah Bayar'   group by kd_poli");
                   
                   xrs=dr;
                   
                   xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                     tth=tth+xrs;
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(tth));
                
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        List<Object>  list = Arrays.asList(xs);        
        tabMode7.addRow(list.toArray());
                
                ttl=ttl+tth;
                i++;                    
            }
            
            
            if(i>1){
                try {
            ps=koneksi.prepareStatement("SELECT kd_pj FROM penjab "); 
            try{  
                rs=ps.executeQuery();
                Object[] xs ={"","Total"};
                while(rs.next()){
                  
                   dr= Sequel.cariInteger("select sum(operasi.bagian_rs) as jumlah_tagihan from  reg_periksa inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat JOIN operasi ON reg_periksa.no_rawat=operasi.no_rawat JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE  nota_jalan.tanggal='"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and nota_jalan.jam between '"+jam_masuk+"' and '"+jam_pulang+"' and  reg_periksa.kd_pj='"+rs.getString("kd_pj")+"' and nm_poli like '%"+TCari.getText().trim()+"%'  and status_bayar='Sudah Bayar' group by reg_periksa.kd_pj");
                   
                   xrs=dr;
                    
                  xs = ArrayUtils.add(xs, Valid.SetAngka4(xrs));
                    
                }
                 xs = ArrayUtils.add(xs, Valid.SetAngka4(ttl));
                  List<Object>  list = Arrays.asList(xs);  
                tabMode7.addRow(list.toArray());
                 
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
                
               
            }
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
     }

}
