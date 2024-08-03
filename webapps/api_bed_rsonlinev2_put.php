<?php
require_once("conf/conf.php");
date_default_timezone_set("Asia/Jakarta");
$date = date_create();
$timestamp = strtotime(date("Y/m/d H:i:s"));
$myvars = "";
// RIC1A	KP4
// RIC1B	KP4
// RIC1C	KP5
// RIC1D	KP5
// RIC1E	KP5
// RIC1F	KP6
// RIC1G	KP6
// $sql = "SELECT if(bangsal.kd_bangsal='KP4',28,29) as id_tt,if(if(bangsal.kd_bangsal='B0039',28,29)=28,1,3) as jumlah_ruang,
// bangsal.kd_bangsal,bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kode_kelas_aplicare,sum(aplicare_ketersediaan_kamar.kapasitas) as kapasitas,
// (sum(aplicare_ketersediaan_kamar.kapasitas)-sum(aplicare_ketersediaan_kamar.tersedia)) as isi, sum(aplicare_ketersediaan_kamar.tersedia) as kosong
// FROM aplicare_ketersediaan_kamar
// INNER JOIN bangsal ON bangsal.kd_bangsal=aplicare_ketersediaan_kamar.kd_bangsal
// WHERE aplicare_ketersediaan_kamar.kode_kelas_aplicare='ISO'
// GROUP BY id_tt ";
$sql = "SELECT '29' as id_tt,COUNT(*) as jumlah_ruang,
aplicare_ketersediaan_kamar.kode_kelas_aplicare,sum(aplicare_ketersediaan_kamar.kapasitas) as kapasitas,
(sum(aplicare_ketersediaan_kamar.kapasitas)-sum(aplicare_ketersediaan_kamar.tersedia)) as isi, sum(aplicare_ketersediaan_kamar.tersedia) as kosong
FROM aplicare_ketersediaan_kamar
INNER JOIN bangsal ON bangsal.kd_bangsal=aplicare_ketersediaan_kamar.kd_bangsal
WHERE aplicare_ketersediaan_kamar.kode_kelas_aplicare='ISO' AND bangsal.kd_bangsal!='RI1'
GROUP BY id_tt";
$hasil = bukaquery($sql);
while ($data = mysqli_fetch_array($hasil)) {
    $item = array(
        "id_tt" => $data['id_tt'],
        "jumlah_ruang" => $data['jumlah_ruang'],
        "jumlah" => $data['kapasitas'],
        "terpakai" => $data['isi']
    );

    $myvars = json_encode($item);
    // echo $myvars;
    $headers = array(
        "X-rs-id:3172771",
        "X-Timestamp:" . $timestamp . "",
        "X-pass:S!rs2020!!"
    );
    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, "http://sirs.kemkes.go.id/fo/index.php/Fasyankes");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_ENCODING, "");
    curl_setopt($ch, CURLOPT_MAXREDIRS, 10);
    curl_setopt($ch, CURLOPT_TIMEOUT, 0);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
    curl_setopt($ch, CURLOPT_POSTFIELDS, $myvars);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

    $response = curl_exec($ch);

    curl_close($ch);
    //echo $response;
}
