<?php
require_once("conf/conf.php");
date_default_timezone_set("Asia/Jakarta");
$date = date_create();
$myvars = "";
// $sql = "SELECT 
// CASE
// WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='HCU' THEN '7'
// WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='SAL' AND bangsal.kd_bangsal!='B0033' THEN '5'
// WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='KL3' AND bangsal.kd_bangsal!='B0033' THEN '5'
// WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='KL3' AND bangsal.kd_bangsal='B0033' THEN '14'
// ELSE ''
// END as id_tt,
// bangsal.kd_bangsal,bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kode_kelas_aplicare,sum(aplicare_ketersediaan_kamar.kapasitas) as kapasitas,
// (sum(aplicare_ketersediaan_kamar.kapasitas)-sum(aplicare_ketersediaan_kamar.tersedia)) as isi, sum(aplicare_ketersediaan_kamar.tersedia) as kosong
// FROM aplicare_ketersediaan_kamar
// INNER JOIN bangsal ON bangsal.kd_bangsal=aplicare_ketersediaan_kamar.kd_bangsal
// WHERE aplicare_ketersediaan_kamar.kode_kelas_aplicare!='ISO'
// GROUP BY id_tt ";

//$sql = "SELECT 
//CASE
//WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='ISO' AND bangsal.kd_bangsal='RI1' THEN '12'
//WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='KL3' THEN '5'
//ELSE ''
//END as id_tt,
//bangsal.kd_bangsal,bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kode_kelas_aplicare,sum(aplicare_ketersediaan_kamar.kapasitas) as kapasitas,
//(sum(aplicare_ketersediaan_kamar.kapasitas)-sum(aplicare_ketersediaan_kamar.tersedia)) as isi, sum(aplicare_ketersediaan_kamar.tersedia) as kosong
//FROM aplicare_ketersediaan_kamar
//INNER JOIN bangsal ON bangsal.kd_bangsal=aplicare_ketersediaan_kamar.kd_bangsal
//WHERE bangsal.kd_bangsal!='KP4' OR bangsal.kd_bangsal!='KP5' OR bangsal.kd_bangsal!='KP6'
//GROUP BY id_tt";

$sql="SELECT 
CASE
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='ISO' THEN '12'
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='KL3' THEN '5'
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='NON' THEN '13'
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='NON' THEN '13'
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='NON' THEN '13'
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='NON' THEN '13'
WHEN aplicare_ketersediaan_kamar.kode_kelas_aplicare ='SAL' THEN '13'
ELSE ''
END as id_tt,
bangsal.kd_bangsal,bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kode_kelas_aplicare,sum(aplicare_ketersediaan_kamar.kapasitas) as kapasitas,
(sum(aplicare_ketersediaan_kamar.kapasitas)-sum(aplicare_ketersediaan_kamar.tersedia)) as isi, sum(aplicare_ketersediaan_kamar.tersedia) as kosong
FROM aplicare_ketersediaan_kamar
INNER JOIN bangsal ON bangsal.kd_bangsal=aplicare_ketersediaan_kamar.kd_bangsal
WHERE bangsal.kd_bangsal!='KP4' AND bangsal.kd_bangsal!='KP5' AND bangsal.kd_bangsal!='KP6' AND bangsal.kd_bangsal!='KI1'
GROUP BY id_tt";
$hasil = bukaquery($sql);
while ($data = mysqli_fetch_array($hasil)) {
    $item = array(
        "id_tt" => $data['id_tt'],
        "jumlah_ruang" => '1',
        "jumlah" => $data['kapasitas'],
        "terpakai" => $data['isi'],
        "prepare" => "0",
        "prepare_plan" => "0",
        "covid" => 1
    );

    $myvars = json_encode($item);
    // echo $myvars;
    $headers = array(
        "X-rs-id:3172771",
        "X-Timestamp:" . date_timestamp_get($date) . "",
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
    echo $response;
    //echo $myvars;
}

