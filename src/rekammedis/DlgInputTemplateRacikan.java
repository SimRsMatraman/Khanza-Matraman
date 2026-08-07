package rekammedis;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public final class DlgInputTemplateRacikan extends JDialog {
    public static final class Detail {
        public String kodeBarang="",namaBarang="",satuan="",jenis="",industri="",komposisi="",
                kategori="",golongan="",noBatch="",noFaktur="",kadaluarsa="";
        public double kapasitas=0,p1=1,p2=1,kandungan=0,jumlah=0,harga=0,hargaBeli=0,stok=0,
                embalase=0,tuslah=0;
    }

    private static final class Metode {
        private final String kode;
        private final String nama;
        private Metode(String kode,String nama){this.kode=kode;this.nama=nama;}
        @Override public String toString(){return nama;}
    }

    private final Connection koneksi=koneksiDB.condb();
    private final CardLayout kartu=new CardLayout();
    private final JPanel isi=new JPanel(kartu);
    private final JTextField txtNama=new JTextField();
    private final JComboBox<Metode> cmbMetode=new JComboBox<>();
    private final JTextField txtJumlah=new JTextField();
    private final JTextField txtAturan=new JTextField();
    private final JTextField txtKeterangan=new JTextField();
    private final JTextField txtCari=new JTextField();
    private final JLabel lblRumus=new JLabel("Jumlah terpakai = Jumlah Racik × Kandungan ÷ Kapasitas");
    private final DefaultTableModel modelObat;
    private final JTable tbObat;
    private boolean memperbarui=false;
    private boolean disimpan=false;
    private boolean tambahLagi=false;
    private String noRacik="";
    private final List<Detail> hasilDetail=new ArrayList<>();
    private List<Detail> katalogKhusus=null;
    private static final Color WARNA_UTAMA=new Color(41,98,120);
    private static final Color WARNA_AKSEN=new Color(0,137,123);
    private static final Color WARNA_LATAR=new Color(245,248,250);
    private static final Color WARNA_KANDUNGAN=new Color(255,247,194);

    public DlgInputTemplateRacikan(java.awt.Frame parent,boolean modal){
        super(parent,modal);
        setTitle("Input Racikan Template Resep");
        setIconImage(new ImageIcon(getClass().getResource("/picture/rumahsehat.png")).getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(WARNA_LATAR);
        setLayout(new BorderLayout(8,8));

        modelObat=new DefaultTableModel(null,new Object[]{
            "Kode Barang","Nama Barang","Satuan","Jenis Obat","Kapasitas","Kandungan","Jumlah Obat",
            "I.F.","Komposisi","Harga","H.Beli","Stok","Kategori","Golongan","No.Batch",
            "No.Faktur","Kadaluarsa","Embalase","Tuslah"
        }){
            @Override public boolean isCellEditable(int row,int col){return col==5;}
        };
        tbObat=new JTable(modelObat);
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbObat.setDefaultRenderer(Object.class,new WarnaTable());
        aturKolom(new int[]{0,320,75,150,85,105,105,0,0,0,0,0,0,0,0,0,0,0,0});
        aturTampilanTabel();
        modelObat.addTableModelListener((TableModelEvent e)->{
            if(!memperbarui&&e.getType()==TableModelEvent.UPDATE&&e.getFirstRow()>=0&&
                    e.getColumn()==5){
                hitungBaris(e.getFirstRow());
            }
        });

        aturTampilanInput();
        isi.add(buatLangkahIdentitas(),"identitas");
        isi.add(buatLangkahObat(),"obat");
        isi.setBorder(javax.swing.BorderFactory.createEmptyBorder(12,12,12,12));
        isi.setBackground(WARNA_LATAR);
        add(isi,BorderLayout.CENTER);
        setMinimumSize(new Dimension(900,580));
        setSize(980,650);
        setLocationRelativeTo(parent);
        muatMetode();
    }

    private JPanel buatLangkahIdentitas(){
        JPanel panel=new JPanel(new BorderLayout(5,5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,228,232)));
        JPanel form=new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(24,28,20,28));
        GridBagConstraints c=new GridBagConstraints();
        c.insets=new Insets(7,8,7,8);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weightx=0;
        tambahBaris(form,c,0,"Nama Racikan :",txtNama);
        tambahBaris(form,c,1,"Metode Racik :",cmbMetode);
        tambahBaris(form,c,2,"Jumlah Racik :",txtJumlah);
        tambahBaris(form,c,3,"Aturan Pakai :",txtAturan);
        tambahBaris(form,c,4,"Keterangan :",txtKeterangan);
        JPanel tombol=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tombol.setBackground(Color.WHITE);
        JButton batal=buatTombol("Batal",new Color(108,117,125));
        JButton lanjut=buatTombol("Lanjut",WARNA_AKSEN);
        batal.addActionListener(e->dispose());
        lanjut.addActionListener(e->lanjutKeObat());
        txtKeterangan.addActionListener(e->lanjutKeObat());
        txtNama.addActionListener(e->txtJumlah.requestFocus());
        txtJumlah.addActionListener(e->txtAturan.requestFocus());
        txtAturan.addActionListener(e->txtKeterangan.requestFocus());
        tombol.add(batal);
        tombol.add(lanjut);
        panel.add(buatJudul("1","Identitas Racikan","Isi informasi dasar racikan"),BorderLayout.NORTH);
        panel.add(form,BorderLayout.CENTER);
        panel.add(tombol,BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buatLangkahObat(){
        JPanel panel=new JPanel(new BorderLayout(5,5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(220,228,232)));
        JPanel pencarian=new JPanel(new BorderLayout(5,5));
        pencarian.setBackground(Color.WHITE);
        pencarian.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,12,6,12));
        JButton cari=buatTombol("Cari",WARNA_UTAMA);
        pencarian.add(new JLabel("Cari Obat :"),BorderLayout.WEST);
        pencarian.add(txtCari,BorderLayout.CENTER);
        pencarian.add(cari,BorderLayout.EAST);
        cari.addActionListener(e->muatObat());
        txtCari.addActionListener(e->muatObat());
        txtCari.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){muatJikaCukup();}
            public void removeUpdate(DocumentEvent e){muatJikaCukup();}
            public void changedUpdate(DocumentEvent e){}
        });

        JPanel atas=new JPanel(new BorderLayout(5,5));
        atas.setBackground(Color.WHITE);
        atas.add(buatJudul("2","Komposisi Racikan","Cari obat lalu isi kolom Kandungan"),BorderLayout.NORTH);
        atas.add(pencarian,BorderLayout.CENTER);
        lblRumus.setHorizontalAlignment(SwingConstants.LEFT);
        atas.add(lblRumus,BorderLayout.SOUTH);

        JPanel tombol=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tombol.setBackground(Color.WHITE);
        JButton kembali=buatTombol("Kembali",new Color(96,125,139));
        JButton batal=buatTombol("Batal",new Color(108,117,125));
        JButton tambah=buatTombol("Simpan & Tambah Racikan",WARNA_UTAMA);
        JButton simpan=buatTombol("Simpan Racikan",WARNA_AKSEN);
        kembali.addActionListener(e->kartu.show(isi,"identitas"));
        batal.addActionListener(e->dispose());
        tambah.addActionListener(e->selesai(true));
        simpan.addActionListener(e->selesai(false));
        tombol.add(kembali);
        tombol.add(batal);
        tombol.add(tambah);
        tombol.add(simpan);

        panel.add(atas,BorderLayout.NORTH);
        panel.add(new JScrollPane(tbObat),BorderLayout.CENTER);
        panel.add(tombol,BorderLayout.SOUTH);
        return panel;
    }

    private void tambahBaris(JPanel panel,GridBagConstraints c,int baris,String label,java.awt.Component input){
        c.gridy=baris;c.gridx=0;c.weightx=0;
        JLabel l=new JLabel(label,SwingConstants.RIGHT);
        l.setPreferredSize(new Dimension(130,25));
        panel.add(l,c);
        c.gridx=1;c.weightx=1;
        panel.add(input,c);
    }

    private void aturTampilanInput(){
        Font font=new Font("Segoe UI",Font.PLAIN,13);
        JTextField[] input={txtNama,txtJumlah,txtAturan,txtKeterangan,txtCari};
        for(JTextField field:input){
            field.setFont(font);
            field.setPreferredSize(new Dimension(400,32));
        }
        cmbMetode.setFont(font);
        cmbMetode.setPreferredSize(new Dimension(400,32));
        lblRumus.setFont(new Font("Segoe UI",Font.ITALIC,12));
        lblRumus.setForeground(new Color(85,100,110));
        lblRumus.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,12,8,12));
    }

    private JPanel buatJudul(String nomor,String judul,String subjudul){
        JPanel panel=new JPanel(new BorderLayout(10,0));
        panel.setBackground(WARNA_UTAMA);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12,16,12,16));
        JLabel langkah=new JLabel(nomor,SwingConstants.CENTER);
        langkah.setOpaque(true);
        langkah.setBackground(Color.WHITE);
        langkah.setForeground(WARNA_UTAMA);
        langkah.setFont(new Font("Segoe UI",Font.BOLD,16));
        langkah.setPreferredSize(new Dimension(38,38));
        JLabel teks=new JLabel("<html><b>"+judul+"</b><br><span style='font-size:10px'>"+subjudul+"</span></html>");
        teks.setForeground(Color.WHITE);
        teks.setFont(new Font("Segoe UI",Font.PLAIN,14));
        panel.add(langkah,BorderLayout.WEST);
        panel.add(teks,BorderLayout.CENTER);
        return panel;
    }

    private JButton buatTombol(String teks,Color warna){
        JButton tombol=new JButton(teks);
        tombol.setFont(new Font("Segoe UI",Font.BOLD,12));
        tombol.setForeground(Color.WHITE);
        tombol.setBackground(warna);
        tombol.setFocusPainted(false);
        tombol.setBorder(javax.swing.BorderFactory.createEmptyBorder(8,16,8,16));
        tombol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return tombol;
    }

    private void aturTampilanTabel(){
        tbObat.setFont(new Font("Segoe UI",Font.PLAIN,12));
        tbObat.setRowHeight(28);
        tbObat.setShowVerticalLines(false);
        tbObat.setGridColor(new Color(232,237,240));
        tbObat.setSelectionBackground(new Color(214,236,240));
        tbObat.setSelectionForeground(new Color(30,45,50));
        tbObat.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,12));
        tbObat.getTableHeader().setReorderingAllowed(false);
        tbObat.getTableHeader().setPreferredSize(new Dimension(0,32));
        tbObat.getTableHeader().setBackground(new Color(232,240,243));
        tbObat.getTableHeader().setForeground(new Color(45,65,75));
        tbObat.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,
                    boolean focus,int row,int column){
                Component c=super.getTableCellRendererComponent(table,value,selected,focus,row,column);
                if(!selected)c.setBackground(WARNA_KANDUNGAN);
                c.setFont(new Font("Segoe UI",Font.BOLD,12));
                return c;
            }
        });
    }

    private void aturKolom(int[] lebar){
        for(int i=0;i<lebar.length;i++){
            TableColumn kolom=tbObat.getColumnModel().getColumn(i);
            if(lebar[i]==0){
                kolom.setMinWidth(0);
                kolom.setMaxWidth(0);
                kolom.setPreferredWidth(0);
            }else{
                kolom.setPreferredWidth(lebar[i]);
            }
        }
    }

    private void muatMetode(){
        cmbMetode.removeAllItems();
        try(PreparedStatement ps=koneksi.prepareStatement(
                "select kd_racik,nm_racik from metode_racik order by nm_racik");
            ResultSet rs=ps.executeQuery()){
            while(rs.next())cmbMetode.addItem(new Metode(rs.getString(1),rs.getString(2)));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Gagal memuat metode racik: "+e.getMessage());
        }
    }

    private void lanjutKeObat(){
        if(txtNama.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,"Nama racikan wajib diisi.");
            txtNama.requestFocus();
            return;
        }
        if(cmbMetode.getSelectedItem()==null){
            JOptionPane.showMessageDialog(this,"Metode racik wajib dipilih.");
            cmbMetode.requestFocus();
            return;
        }
        if(angka(txtJumlah.getText())<=0){
            JOptionPane.showMessageDialog(this,"Jumlah racik harus lebih dari 0.");
            txtJumlah.requestFocus();
            return;
        }
        kartu.show(isi,"obat");
        if(modelObat.getRowCount()==0){
            muatObat();
        }else{
            for(int i=0;i<modelObat.getRowCount();i++)hitungBaris(i);
        }
        txtCari.requestFocus();
    }

    private void muatJikaCukup(){
        if(txtCari.getText().trim().isEmpty()||txtCari.getText().trim().length()>2)muatObat();
    }

    private void muatObat(){
        Map<String,Object[]> terpilih=new LinkedHashMap<>();
        for(int i=0;i<modelObat.getRowCount();i++){
            if(angka(modelObat.getValueAt(i,5))>0)terpilih.put(nilai(i,0),salinBaris(i));
        }
        memperbarui=true;
        modelObat.setRowCount(0);
        for(Object[] baris:terpilih.values())modelObat.addRow(baris);
        String q="%"+txtCari.getText().trim()+"%";
        if(katalogKhusus!=null){
            String filter=txtCari.getText().trim().toLowerCase();
            for(Detail d:katalogKhusus){
                if(!terpilih.containsKey(d.kodeBarang)&&
                        (filter.isEmpty()||d.kodeBarang.toLowerCase().contains(filter)||
                        d.namaBarang.toLowerCase().contains(filter)||d.jenis.toLowerCase().contains(filter)||
                        d.komposisi.toLowerCase().contains(filter))){
                    modelObat.addRow(new Object[]{d.kodeBarang,d.namaBarang,d.satuan,d.jenis,d.kapasitas,
                        "",0D,d.industri,d.komposisi,d.harga,d.hargaBeli,d.stok,d.kategori,d.golongan,
                        d.noBatch,d.noFaktur,d.kadaluarsa,d.embalase,d.tuslah});
                }
            }
            memperbarui=false;
            return;
        }
        try(PreparedStatement ps=koneksi.prepareStatement(
                "select b.kode_brng,b.nama_brng,s.satuan,j.nama,b.kapasitas,f.nama_industri,b.letak_barang "+
                "from databarang b inner join kodesatuan s on s.kode_sat=b.kode_sat "+
                "inner join jenis j on j.kdjns=b.kdjns "+
                "inner join industrifarmasi f on f.kode_industri=b.kode_industri "+
                "where b.status='1' and (b.kode_brng like ? or b.nama_brng like ? or "+
                "b.letak_barang like ? or j.nama like ?) order by b.nama_brng limit 300")){
            for(int x=1;x<=4;x++)ps.setString(x,q);
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    if(!terpilih.containsKey(rs.getString(1))){
                        modelObat.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getDouble(5),"",0D,rs.getString(6),rs.getString(7),0D,0D,0D,
                            "","","","","",0D,0D});
                    }
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Gagal memuat obat: "+e.getMessage());
        }finally{
            memperbarui=false;
        }
    }

    private void hitungBaris(int baris){
        if(baris<0||baris>=modelObat.getRowCount())return;
        memperbarui=true;
        try{
            double kandungan=angka(modelObat.getValueAt(baris,5));
            double kapasitas=angka(modelObat.getValueAt(baris,4));
            double jumlah=kapasitas>0?bulatSatuDesimal((getJumlahRacik()*kandungan)/kapasitas):0;
            modelObat.setValueAt(jumlah,baris,6);
        }finally{
            memperbarui=false;
        }
    }

    private void selesai(boolean lanjutTambah){
        if(!validasiDetail())return;
        hasilDetail.clear();
        for(int i=0;i<modelObat.getRowCount();i++){
            if(angka(modelObat.getValueAt(i,5))>0){
                Detail d=new Detail();
                d.kodeBarang=nilai(i,0);d.namaBarang=nilai(i,1);d.satuan=nilai(i,2);
                d.jenis=nilai(i,3);d.kapasitas=angka(modelObat.getValueAt(i,4));
                d.kandungan=angka(modelObat.getValueAt(i,5));d.jumlah=angka(modelObat.getValueAt(i,6));
                d.industri=nilai(i,7);d.komposisi=nilai(i,8);
                d.harga=angka(modelObat.getValueAt(i,9));d.hargaBeli=angka(modelObat.getValueAt(i,10));
                d.stok=angka(modelObat.getValueAt(i,11));
                d.kategori=nilai(i,12);d.golongan=nilai(i,13);d.noBatch=nilai(i,14);
                d.noFaktur=nilai(i,15);d.kadaluarsa=nilai(i,16);
                d.embalase=angka(modelObat.getValueAt(i,17));d.tuslah=angka(modelObat.getValueAt(i,18));
                hasilDetail.add(d);
            }
        }
        disimpan=true;
        tambahLagi=lanjutTambah;
        dispose();
    }

    private boolean validasiDetail(){
        int terisi=0;
        for(int i=0;i<modelObat.getRowCount();i++){
            if(angka(modelObat.getValueAt(i,5))>0){
                if(angka(modelObat.getValueAt(i,4))<=0){
                    JOptionPane.showMessageDialog(this,"Kapasitas "+nilai(i,1)+" masih kosong.");
                    return false;
                }
                hitungBaris(i);
                if(angka(modelObat.getValueAt(i,6))<=0){
                    JOptionPane.showMessageDialog(this,"Kandungan "+nilai(i,1)+" harus lebih dari 0.");
                    return false;
                }
                terisi++;
            }
        }
        if(terisi==0){
            JOptionPane.showMessageDialog(this,"Pilih minimal satu obat dan isi kandungannya.");
            return false;
        }
        return true;
    }

    public void setData(String no,String nama,String kodeMetode,double jumlah,String aturan,String keterangan,
            List<Detail> detail){
        noRacik=no;txtNama.setText(nama);txtJumlah.setText(format(jumlah));txtAturan.setText(aturan);
        txtKeterangan.setText(keterangan);
        for(int i=0;i<cmbMetode.getItemCount();i++){
            if(cmbMetode.getItemAt(i).kode.equals(kodeMetode)){cmbMetode.setSelectedIndex(i);break;}
        }
        memperbarui=true;
        modelObat.setRowCount(0);
        if(detail!=null)for(Detail d:detail){
            modelObat.addRow(new Object[]{d.kodeBarang,d.namaBarang,d.satuan,d.jenis,d.kapasitas,
                d.kandungan,d.jumlah,d.industri,d.komposisi,d.harga,d.hargaBeli,d.stok,d.kategori,
                d.golongan,d.noBatch,d.noFaktur,d.kadaluarsa,d.embalase,d.tuslah});
        }
        memperbarui=false;
    }

    public void setKatalogObat(List<Detail> katalog){katalogKhusus=katalog==null?null:new ArrayList<>(katalog);}
    public void setJudulDialog(String judul){setTitle(judul);}
    public boolean isDisimpan(){return disimpan;}
    public boolean isTambahLagi(){return tambahLagi;}
    public String getNoRacik(){return noRacik;}
    public String getNamaRacik(){return txtNama.getText().trim();}
    public String getKodeMetode(){Metode m=(Metode)cmbMetode.getSelectedItem();return m==null?"":m.kode;}
    public String getNamaMetode(){Metode m=(Metode)cmbMetode.getSelectedItem();return m==null?"":m.nama;}
    public double getJumlahRacik(){return angka(txtJumlah.getText());}
    public String getAturanPakai(){return txtAturan.getText().trim();}
    public String getKeterangan(){return txtKeterangan.getText().trim();}
    public List<Detail> getDetail(){return new ArrayList<>(hasilDetail);}

    private Object[] salinBaris(int row){Object[] x=new Object[modelObat.getColumnCount()];for(int i=0;i<x.length;i++)x[i]=modelObat.getValueAt(row,i);return x;}
    private String nilai(int row,int col){Object x=modelObat.getValueAt(row,col);return x==null?"":x.toString();}
    private double angka(Object x){try{return Double.parseDouble(x==null?"0":x.toString().replace(",","."));}catch(Exception e){return 0;}}
    private double bulatSatuDesimal(double x){return Math.round(x*10D)/10D;}
    private String format(double x){return x==(long)x?String.valueOf((long)x):String.valueOf(x);}
}
