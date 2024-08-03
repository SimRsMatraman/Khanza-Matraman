<?php

require_once('conf/conf.php');
$url = "http://103.252.163.64/api_dinkes/spgdt_api/infobed";
$session = curl_init($url);
$cid = 1744;
$arrheader = array(
    'X-api-key: $2y$10$pIAdlRECR1DjIQT7XKtzvuBdwKgUO3sh0',
    'Accept: application/json',
    'Content-Type: application/json'
);
$_sql = "SELECT aplicare_ketersediaan_kamar.kode_kelas_aplicare, sum(aplicare_ketersediaan_kamar.kapasitas) as total, sum(aplicare_ketersediaan_kamar.tersedia) as kosong
        FROM aplicare_ketersediaan_kamar
        GROUP BY aplicare_ketersediaan_kamar.kode_kelas_aplicare";
$hasil = bukaquery($_sql);
$myvars = array();
while ($data = mysqli_fetch_array($hasil)) {
    $kodekelas = $data['kode_kelas_aplicare'];
    $kapasitas = $data['total'];
    $tersedia = $data['kosong'];
    $isi = $kapasitas - $tersedia;
    $item = array(
        'accessid' => $cid, 'koderuangan' => $kodekelas,
        'jumlahisi' => $isi, 'jumlahkosong' => $tersedia, 'jumlahtotal' => $kapasitas
    );
    array_push($myvars, $item);
}
$myvars = json_encode($myvars);
curl_setopt($session, CURLOPT_URL, $url);
curl_setopt($session, CURLOPT_HTTPHEADER, $arrheader);
curl_setopt($session, CURLOPT_CUSTOMREQUEST, "POST");
curl_setopt($session, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($session, CURLOPT_SSL_VERIFYHOST, false);
curl_setopt($session, CURLOPT_POST, true);
curl_setopt($session, CURLOPT_POSTFIELDS, $myvars);
curl_setopt($session, CURLOPT_RETURNTRANSFER, TRUE);
$response = curl_exec($session);
echo $response;
?>