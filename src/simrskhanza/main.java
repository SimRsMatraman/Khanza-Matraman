package simrskhanza;

import java.awt.EventQueue;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class main extends javax.swing.JFrame {

    private Timer progressTimer;

    public main() {
        initComponents();

        setLocationRelativeTo(null);

        setIconImage(
            new ImageIcon(
                getClass().getResource("/picture/yaski24.png")
            ).getImage()
        );

        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("Memulai aplikasi...");

        mulaiLoading();
    }

    private void mulaiLoading() {

        /*
         * Timer berjalan di EDT sehingga splash screen tetap bergerak
         * selama frmUtama dibuat oleh SwingWorker.
         *
         * Angka 0-90 merupakan progress visual.
         * Saat proses sebenarnya selesai, nilainya langsung menjadi 100.
         */
        progressTimer = new Timer(80, e -> {
            int nilai = progressBar.getValue();

            if (nilai < 90) {
                int tambahan;

                /*
                 * Bergerak agak cepat pada awal,
                 * lalu melambat menjelang selesai.
                 */
                if (nilai < 35) {
                    tambahan = 2;
                } else {
                    tambahan = 1;
                }

                nilai = Math.min(nilai + tambahan, 90);

                progressBar.setValue(nilai);
                progressBar.setString(
                    "Memuat aplikasi... " + nilai + "%"
                );
            }
        });

        progressTimer.start();

        new StartupWorker().execute();
    }

    private class StartupWorker
            extends SwingWorker<frmUtama, Void> {

        @Override
        protected frmUtama doInBackground() throws Exception {

            long mulai = System.currentTimeMillis();

            /*
             * Seluruh dialog penting yang merupakan field frmUtama
             * tetap akan dibuat pada proses startup.
             */
            frmUtama utama = frmUtama.getInstance();

            long selesai = System.currentTimeMillis();

            System.out.println(
                "Waktu loading frmUtama: "
                + (selesai - mulai)
                + " ms"
            );

            return utama;
        }

        @Override
        protected void done() {
            try {
                frmUtama utama = get();

                if (progressTimer != null) {
                    progressTimer.stop();
                }

                progressBar.setValue(95);
                progressBar.setString(
                    "Menyiapkan tampilan utama..."
                );

                /*
                 * Memaksa tulisan 95% tergambar sebelum isWall().
                 */
                progressBar.paintImmediately(
                    0,
                    0,
                    progressBar.getWidth(),
                    progressBar.getHeight()
                );

                /*
                 * Method yang memodifikasi komponen Swing
                 * dijalankan di done(), yaitu di EDT.
                 */
                utama.isWall();

                progressBar.setValue(100);
                progressBar.setString("Selesai");

                progressBar.paintImmediately(
                    0,
                    0,
                    progressBar.getWidth(),
                    progressBar.getHeight()
                );

                /*
                 * Beri waktu singkat agar angka 100% terlihat.
                 */
                Timer selesaiTimer = new Timer(150, e -> {
                    ((Timer) e.getSource()).stop();

                    utama.setVisible(true);
                    dispose();
                });

                selesaiTimer.setRepeats(false);
                selesaiTimer.start();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                tampilkanErrorLoading(e);

            } catch (ExecutionException e) {
                tampilkanErrorLoading(e.getCause());
            }
        }
    }

    private void tampilkanErrorLoading(Throwable error) {
        if (progressTimer != null) {
            progressTimer.stop();
        }

        progressBar.setString("Gagal memuat aplikasi");

        error.printStackTrace();

        JOptionPane.showMessageDialog(
            this,
            "Aplikasi gagal dimuat.\n"
            + error.getMessage(),
            "Kesalahan Startup",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {

        /*
         * Set Nimbus sebelum membuat komponen Swing.
         */
        try {
            for (
                javax.swing.UIManager.LookAndFeelInfo info :
                javax.swing.UIManager
                    .getInstalledLookAndFeels()
            ) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(
                        info.getClassName()
                    );
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(
                "Gagal menggunakan Nimbus: " + e
            );
        }

        EventQueue.invokeLater(() -> {
            main splash = new main();
            splash.setVisible(true);
        });
    }

    /*
     * initComponents() dan variables declaration
     * dari NetBeans tetap diletakkan di sini.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        progressBar.setForeground(new java.awt.Color(0, 153, 0));
        progressBar.setFocusable(false);
        progressBar.setPreferredSize(new java.awt.Dimension(146, 20));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/up.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(215, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        progressBar.getAccessibleContext().setAccessibleParent(progressBar);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}