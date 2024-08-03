<?php
require_once('../conf/command.php');
require_once('../../conf/conf.php');
if ($_GET['act'] == 'simpan') {
    $no_rawat  = $_POST['norawat'];
    $bagian  = $_POST['bagian'];
    $gigi  = $_POST['gigi'];
    $diagnosa  = $_POST['kd_penyakit'];
    $keterangan  = $_POST['ketPemeriksaan'];
    $postx = $_POST['postx'];
    $posty = $_POST['posty'];

    $_query = "SELECT indeks FROM  pemeriksaan_gigi where no_rawat='$no_rawat' order by indeks DESC limit 1 ";
    $dtquery = bukaquery($_query);
    if ($dtquery->num_rows > 0) {
        while ($data = mysqli_fetch_array($dtquery)) {
            $index = $data['indeks'] + 1;
        }
    } else {
        $index = 1;
    }
    $sql = "INSERT INTO pemeriksaan_gigi(no_rawat,bagian,gigi,kd_penyakit,keterangan,postx,posty,indeks) VALUES('$no_rawat','$bagian','$gigi','$diagnosa','$keterangan','$postx','$posty','$index')";
    mysqli_query(bukakoneksi(), $sql);
    // Tambah("gambar_pemeriksaan","'2','$no_rawat','$bagian','$keterangan','$postx','$posty','2'", " Pemeriksaan Digital");
    $data = array(
        'success' => true,
        'code' => 200,
        'messages' => "sukses"
    );

    echo json_encode($data);
} else if ($_GET['act'] == 'getData') {
    $query = mysqli_query($mysqli, "SELECT * FROM  pemeriksaan_gigi  ") or die('Ada kesalahan pada query tampil Data  ' . mysqli_error($mysqli));
    if ($query->num_rows > 0) {
        while ($data = mysqli_fetch_array($query)) {
            $hasil[] = array(
                "postx" => $data['postx'],
                "posty" => $data['posty'],
                "indeks" => $data['indeks'],
                "gigi" => $data['gigi'],
                "kd_penyakit" => $data['kd_penyakit'],
                "keterangan" => $data['keterangan'],
            );
        }
        $data = array(
            "metaData" => array("code" => 200, "message" => 'sukses'),
            "hasil" => $hasil
        );
    } else {
        $data = array("metaData" => array("code" => 400, "message" => 'sukses'), "hasil" => "");
    }
    echo json_encode($data);
} else {
}
