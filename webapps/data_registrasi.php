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
         <h5 class="center"><i class="material-icons md-36">group</i> Data Pasien Hari Ini</h5>
         <table class="default">
             <thead>
                 <tr class='head4'>
                     <td>
                         <div align='left'><b>Poli</b>
                     </td>
                     <td>
                         <div align='left'><b>Nama Dokter</b>
                     </td>
                     <td>
                         <div align='left'><b>Jam Mulai</b>
                     </td>
                     <td>
                         <div align='left'><b>Jam Selesai</b>
                     </td>
                     <td>
                         <div align='left'><b>Kunjungan/Hari</b>
                     </td>
                     <td>
                         <div align='left'><b>Onsite</b>
                     </td>
                     <td>
                         <div align='left'><b>Online</b>
                     </td>
                     <td>
                         <div align='left'><b>Kontrol</b>
                     </td>
                 </tr>
             </thead>
             <tbody>
                 <?php
                    $hari = getOne("select DAYNAME(current_date())");
                    $namahari = "";
                    if ($hari == "Sunday") {
                        $namahari = "AKHAD";
                    } else if ($hari == "Monday") {
                        $namahari = "SENIN";
                    } else if ($hari == "Tuesday") {
                        $namahari = "SELASA";
                    } else if ($hari == "Wednesday") {
                        $namahari = "RABU";
                    } else if ($hari == "Thursday") {
                        $namahari = "KAMIS";
                    } else if ($hari == "Friday") {
                        $namahari = "JUMAT";
                    } else if ($hari == "Saturday") {
                        $namahari = "SABTU";
                    }
                    // $_sql = "select booking_operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,booking_operasi.tanggal, booking_operasi.jam_mulai,booking_operasi.jam_selesai,booking_operasi.status,booking_operasi.kd_dokter, dokter.nm_dokter,booking_operasi.kode_paket,paket_operasi.nm_perawatan,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk from booking_operasi inner join reg_periksa inner join pasien inner join paket_operasi inner join dokter on booking_operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and booking_operasi.kd_dokter=dokter.kd_dokter and booking_operasi.kode_paket=paket_operasi.kode_paket where tanggal='" . date("Y-m-d", $tanggal) . "' order by booking_operasi.tanggal,booking_operasi.jam_mulai";
                    // $_sql = "Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai,jadwal.kuota,poliklinik.kd_poli, 
                    // dokter.kd_dokter from jadwal inner join dokter inner join poliklinik on dokter.kd_dokter=jadwal.kd_dokter 
                    // and jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja='$namahari'";
                    $_sql = "Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai,jadwal.kuota,poliklinik.kd_poli, 
                    dokter.kd_dokter from jadwal inner join dokter inner join poliklinik on dokter.kd_dokter=jadwal.kd_dokter 
                    and jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja='$namahari'";
                    $hasil = bukaquery($_sql);

                    while ($data = mysqli_fetch_array($hasil)) {
                        $onsite = getOne("select count(*) from reg_periksa where kd_poli='" . $data['kd_poli'] . "' and kd_dokter='" . $data['kd_dokter'] . "' and kd_dokter='" . $data['kd_dokter'] . "' and tgl_registrasi='" . date("Y-m-d", $tanggal) . "'");
                        $jogres = getOne("select count(*) from booking_registrasi where kd_poli='" . $data['kd_poli'] . "' and kd_dokter='" . $data['kd_dokter'] . "' and status='Terdaftar' and tanggal_periksa='" . date("Y-m-d", $tanggal) . "'");
                        $onsitefix = $onsite - $jogres;
                        echo "<tr class='isi7' >
                <td align='center'>" . $data['nm_poli'] . "</td>
                <td align='center'>" . $data['nm_dokter'] . "</td>
                <td align='center'>" . $data['jam_mulai'] . "</td>
                <td align='center'>" . $data['jam_selesai'] . "</td>
                <td align='center'>" . $data['kuota'] . "</td>
                <td align='center'>$onsitefix</td>
                <td align='center'>" . getOne("select count(*) from booking_registrasi where kd_poli='" . $data['kd_poli'] . "' and kd_dokter='" . $data['kd_dokter'] . "' and limit_reg='1' and status='Terdaftar' and tanggal_periksa='" . date("Y-m-d", $tanggal) . "'") .  "</td>
                <td align='center'>" . getOne("select count(*) from booking_registrasi where kd_poli='" . $data['kd_poli'] . "' and kd_dokter='" . $data['kd_dokter'] . "' and limit_reg='0' and status='Terdaftar' and tanggal_periksa='" . date("Y-m-d", $tanggal) . "'") .  "</td>
            </tr> ";
                    }
                    ?>
             </tbody>
         </table>
     </div>
 </div>
