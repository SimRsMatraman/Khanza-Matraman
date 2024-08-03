<?php
require_once("conf/conf.php");
$consid = "3172771";
$secretKey = '$2y$10$ey.VQR16N6zN7iEi5T.QwOcOb7\/H7\/J2oEm3dv2IVdMO30\/fH10BO';
$sql = "SELECT bangsal.kd_bangsal,bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kode_kelas_aplicare,aplicare_ketersediaan_kamar.kapasitas,
(aplicare_ketersediaan_kamar.kapasitas-aplicare_ketersediaan_kamar.tersedia) as isi, aplicare_ketersediaan_kamar.tersedia as kosong
FROM aplicare_ketersediaan_kamar
INNER JOIN bangsal ON bangsal.kd_bangsal=aplicare_ketersediaan_kamar.kd_bangsal";
$hasil = bukaquery($sql);
while ($data = mysqli_fetch_array($hasil)) {
    if ($data['kode_kelas_aplicare'] == 'KL3') {
        $kl3_tersedia[] = $data['kapasitas'];
        $kl3_isi[] = $data['isi'];
        $kl3_kosong[] = $data['kosong'];
    } elseif ($data['kode_kelas_aplicare'] == 'HCU') {
        $hcu_tersedia[] = $data['kapasitas'];
        $hcu_isi[] = $data['isi'];
        $hcu_kosong[] = $data['kosong'];
    } elseif ($data['kode_kelas_aplicare'] == 'ISO') {
         if ($data['kd_bangsal'] == 'KP4' OR $data['kd_bangsal'] == 'KP5' OR $data['kd_bangsal'] == 'KP6' OR $data['kd_bangsal'] == 'KI1') {
            $nonnegatif_tersedia[] = $data['kapasitas'];
            $nonnegatif_isi[] = $data['isi'];
            $nonnegatif_kosong[] =  $data['kosong'];
        }
    }
}
// $data = fetch_array(bukaquery());
$myvars = "";
$item = array(
    "kapasitas_vip" => "0",
    "kapasitas_kelas_1" => "0",
    "kapasitas_kelas_2" => "0",
    "kapasitas_kelas_3" => array_sum($kl3_tersedia),
    "kapasitas_hcu" => "0",
    "kapasitas_iccu" => "0",
    "kapasitas_icu_negatif_ventilator" => "0",
    "kapasitas_icu_negatif_tanpa_ventilator" => "0",
    "kapasitas_icu_tanpa_negatif_ventilator" => "0",
    "kapasitas_icu_tanpa_negatif_tanpa_ventilator" => "0",
    "kapasitas_isolasi_negatif" => "0",
    "kapasitas_isolasi_tanpa_negatif" => array_sum($nonnegatif_tersedia),
    "kapasitas_nicu_covid" => "0",
    "kapasitas_perina_covid" => "0",
    "kapasitas_picu_covid" => "0",
    "kapasitas_ok_covid" => "0",
    "kapasitas_hd_covid" => "0",
    "kosong_vip" => "0",
    "kosong_kelas_1" => "0",
    "kosong_kelas_2" => "0",
    "kosong_kelas_3" => array_sum($kl3_kosong),
    "kosong_hcu" => "0",
    "kosong_iccu" => "0",
    "kosong_icu_negatif_ventilator" => "0",
    "kosong_icu_negatif_tanpa_ventilator" => "0",
    "kosong_icu_tanpa_negatif_ventilator" => "0",
    "kosong_icu_tanpa_negatif_tanpa_ventilator" => "0",
    "kosong_isolasi_negatif" => "0",
    "kosong_isolasi_tanpa_negatif" => array_sum($nonnegatif_kosong),
    "kosong_nicu_covid" => "0",
    "kosong_perina_covid" => "0",
    "kosong_picu_covid" => "0",
    "kosong_ok_covid" => "0",
    "kosong_hd_covid" => "0"

);

$myvars = json_encode($item);
echo $myvars;
$headers = array(
    "Api-Bed-User: " . $consid . "",
    "Api-Bed-Key: " . $secretKey . "",
    "Content-Type: application/json"
);
$ch = curl_init();

curl_setopt($ch, CURLOPT_URL, "http://eis.dinkes.jakarta.go.id/api-bed/bed");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_ENCODING, "");
curl_setopt($ch, CURLOPT_MAXREDIRS, 10);
curl_setopt($ch, CURLOPT_TIMEOUT, 0);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
curl_setopt($ch, CURLOPT_POSTFIELDS, $myvars);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

$response = curl_exec($ch);

curl_close($ch);
echo $response;
