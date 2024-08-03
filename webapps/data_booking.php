 <?php
    require_once('conf/conf.php');
    header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
    header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
    header("Cache-Control: no-store, no-cache, must-revalidate");
    header("Cache-Control: post-check=0, pre-check=0", false);
    header("Pragma: no-cache"); // HTTP/1.0
    date_default_timezone_set("Asia/Bangkok");
    $tanggal = mktime(date("m"), date("d"), date("Y"));
    $jam = date("H:i");
    ?>
 <div class="col s12 row">
     <div class="col s12">
         <h5 class="center"><i class="material-icons md-36">group</i> Data Pasien SiBolen</h5>
         <table class="default">
             <thead>
                 <tr class='head4'>
                     <td>
                         <div align='left'><b>Tanggal Periksa</b>
                     </td>
                     <td>
                         <div align='left'><b>Tanggal Booking</b>
                     </td>
                     <td>
                         <div align='left'><b>Jam Booking</b>
                     </td>
                     <td>
                         <div align='left'><b>No. Booking</b>
                     </td>
                     <td>
                         <div align='left'><b>No. RM</b>
                     </td>
                     <td>
                         <div align='left'><b>Nama Pasien</b>
                     </td>
                     <td>
                         <div align='left'><b>Nama Dokter</b>
                     </td>
                     <td>
                         <div align='left'><b>Nama Poli</b>
                     </td>
                     <td>
                         <div align='left'><b>Cara Bayar</b>
                     </td>
                     <td>
                         <div align='left'><b>Status</b>
                     </td>
                 </tr>
             </thead>
             <tbody>
                 <?php
                    // $_sql = "select booking_operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,booking_operasi.tanggal, booking_operasi.jam_mulai,booking_operasi.jam_selesai,booking_operasi.status,booking_operasi.kd_dokter, dokter.nm_dokter,booking_operasi.kode_paket,paket_operasi.nm_perawatan,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk from booking_operasi inner join reg_periksa inner join pasien inner join paket_operasi inner join dokter on booking_operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and booking_operasi.kd_dokter=dokter.kd_dokter and booking_operasi.kode_paket=paket_operasi.kode_paket where tanggal='" . date("Y-m-d", $tanggal) . "' order by booking_operasi.tanggal,booking_operasi.jam_mulai";
                    $_sql = "SELECT
                    booking_registrasi.tanggal_booking,
                    booking_registrasi.jam_booking,
                    booking_registrasi.no_rkm_medis,
                    booking_registrasi.tanggal_periksa,
                    booking_registrasi.kd_dokter,
                    booking_registrasi.kd_poli,
                    booking_registrasi.no_reg,
                    booking_registrasi.kd_pj,
                    booking_registrasi.limit_reg,
                    booking_registrasi.waktu_kunjungan,
                    booking_registrasi.`status`,
                    pasien.no_rkm_medis,
                    pasien.nm_pasien,
                    dokter.kd_dokter,
                    dokter.nm_dokter,
                    poliklinik.kd_poli,
                    poliklinik.nm_poli,
                    penjab.kd_pj,
                    penjab.png_jawab
                    FROM
                    booking_registrasi
                    INNER JOIN pasien ON booking_registrasi.no_rkm_medis = pasien.no_rkm_medis
                    INNER JOIN dokter ON booking_registrasi.kd_dokter = dokter.kd_dokter
                    INNER JOIN poliklinik ON booking_registrasi.kd_poli = poliklinik.kd_poli
                    INNER JOIN penjab ON booking_registrasi.kd_pj = penjab.kd_pj
                    where tanggal_periksa='" . date("Y-m-d", $tanggal) . "' order by booking_registrasi.tanggal_periksa";
                    $hasil = bukaquery($_sql);

                    while ($data = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi7' >
                <td align='center'>" . $data['tanggal_periksa'] . "</td>
                <td align='center'>" . $data['tanggal_booking'] . "</td>
                <td align='center'>" . $data['jam_booking'] . "</td>
                <td align='center'>" . $data['no_reg'] . "</td>
                <td align='center'>" . $data['no_rkm_medis'] . "</td>
                <td align='center'>" . $data['nm_pasien'] . "</td>
                <td align='center'>" . $data['nm_dokter'] . "</td>
                <td align='center'>" . $data['nm_poli'] . "</td>
                <td align='center'>" . $data['png_jawab'] . "</td>
                <td align='center'>" . $data['status'] . "</td>
            </tr> ";
                    }
                    ?>
             </tbody>
         </table>
     </div>
 </div>