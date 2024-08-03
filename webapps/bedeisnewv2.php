<?php
require_once("conf/conf.php");
//$curl = curl_init();
$consid = "3172771";
$secretKey = '$2y$10$LIxJWv5Ltqt5y6n/IVw.A./61ph66BDnGvo7.LhbLcMBJXYT0ep8e';
$data = fetch_array(bukaquery("SELECT  
SUM(CASE WHEN kd_bangsal LIKE '%KA%' THEN kapasitas END) as kapasitas_anak, 
SUM(CASE WHEN kd_bangsal LIKE '%KA%' THEN tersedia END) as kosong_anak, 
SUM(CASE WHEN kd_bangsal REGEXP 'KW1|KW2|RN1|RPN1' AND kode_kelas_aplicare='KL3' THEN kapasitas END) as kapasitas_wanita, 
SUM(CASE WHEN kd_bangsal REGEXP 'KW1|KW2|RN1|RPN1' AND kode_kelas_aplicare='KL3' THEN tersedia END) as kosong_wanita, 
SUM(CASE WHEN kd_bangsal BETWEEN 'KW3' AND 'KW4' AND kode_kelas_aplicare='KL3' THEN kapasitas END) as kapasitas_pria, 
SUM(CASE WHEN kd_bangsal BETWEEN 'KW3' AND 'KW4' AND kode_kelas_aplicare='KL3' THEN tersedia END) as kosong_pria, 
SUM(CASE WHEN kode_kelas_aplicare='HCU' THEN kapasitas END) as kapasitas_hcu, 
SUM(CASE WHEN kode_kelas_aplicare='HCU' THEN tersedia END) as kosong_hcu, 
SUM(CASE WHEN kode_kelas_aplicare='NON' THEN kapasitas END) as kapasitas_perina, 
SUM(CASE WHEN kode_kelas_aplicare='NON' THEN tersedia END) as kosong_perina, 
SUM(CASE WHEN kd_bangsal='RI1' THEN kapasitas END) as kapasitas_iso_non_covid, 
SUM(CASE WHEN kd_bangsal='RI1' THEN tersedia END) as kosong_iso_non_covid, 
SUM(CASE WHEN kd_bangsal BETWEEN 'KP4' AND 'KP5' THEN kapasitas END) as kapasitas_covid_pria, 
SUM(CASE WHEN kd_bangsal BETWEEN 'KP4' AND 'KP5' THEN tersedia END) as kosong_covid_pria, 
SUM(CASE WHEN kd_bangsal='KP6' THEN kapasitas END) as kapasitas_covid_wanita, 
SUM(CASE WHEN kd_bangsal='KP6' THEN tersedia END) as kosong_covid_wanita 
FROM aplicare_ketersediaan_kamar"));
$item = array(
    "covid" => array(
        "kapasitas" => array(
            "kapasitas_icu_tekanan_negatif_dengan_ventilator_covid" => "",
            "kapasitas_icu_tekanan_negatif_tanpa_ventilator_covid" => "",
            "kapasitas_icu_tanpa_tekanan_negatif_dengan_ventilator_covid" => "",
            "kapasitas_icu_tanpa_tekanan_negatif_tanpa_ventilator_covid" => "",
            "kapasitas_isolasi_tekanan_negatif_covid_pria" => "",
            "kapasitas_isolasi_tekanan_negatif_covid_wanita" => "",
            "kapasitas_isolasi_tekanan_negatif_covid_anak" => "",
            "kapasitas_isolasi_tanpa_tekanan_negatif_covid_pria" => $data['kapasitas_covid_pria'],
            "kapasitas_isolasi_tanpa_tekanan_negatif_covid_wanita" => $data['kapasitas_covid_wanita'],
            "kapasitas_isolasi_tanpa_tekanan_negatif_covid_anak" => "0",
            "kapasitas_nicu_covid" => "",
            "kapasitas_picu_covid" => "",
            "kapasitas_perina_covid" => "",
            "kapasitas_ok_covid" => "",
            "kapasitas_hd_covid" => "",
            "kapasitas_igd_covid" => ""

        ),
        "kosong" => array( 
            "kosong_icu_tekanan_negatif_dengan_ventilator_covid" => "", 
            "kosong_icu_tekanan_negatif_tanpa_ventilator_covid" => "", 
            "kosong_icu_tanpa_tekanan_negatif_dengan_ventilator_covid" => "", 
            "kosong_icu_tanpa_tekanan_negatif_tanpa_ventilator_covid" => "", 
            "kosong_isolasi_tekanan_negatif_covid_pria" => "", 
            "kosong_isolasi_tekanan_negatif_covid_wanita" => "", 
            "kosong_isolasi_tekanan_negatif_covid_anak" => "", 
            "kosong_isolasi_tanpa_tekanan_negatif_covid_pria" => $data['kosong_covid_pria'], 
            "kosong_isolasi_tanpa_tekanan_negatif_covid_wanita" => $data['kosong_covid_wanita'], 
            "kosong_isolasi_tanpa_tekanan_negatif_covid_anak" => "0", 
            "kosong_nicu_covid" => "", 
            "kosong_picu_covid" => "", 
            "kosong_perina_covid" => "", 
            "kosong_ok_covid" => "", 
            "kosong_hd_covid" => "", 
            "kosong_igd_covid" => ""

        )
    ),
    "non_covid" => array(
        "kapasitas" => array(
            "kapasitas_vip_non_covid" => "",
            "kapasitas_kelas_1_non_covid_pria" => "",
            "kapasitas_kelas_1_non_covid_wanita" => "",
            "kapasitas_kelas_1_non_covid_anak" => "",
            "kapasitas_kelas_2_non_covid_pria" => "",
            "kapasitas_kelas_2_non_covid_wanita" => "",
            "kapasitas_kelas_2_non_covid_anak" => "",
            "kapasitas_kelas_3_non_covid_pria" => $data['kapasitas_pria'],
            "kapasitas_kelas_3_non_covid_wanita" =>  $data['kapasitas_wanita'],
            "kapasitas_kelas_3_non_covid_anak" => $data['kapasitas_anak'],
            "kapasitas_hcu_non_covid" => $data['kapasitas_hcu'],
            "kapasitas_iccu_non_covid" => "",
            "kapasitas_icu_non_covid" => "",
            "kapasitas_nicu_non_covid" => "",
            "kapasitas_picu_non_covid" => "",
            "kapasitas_perina_non_covid" => $data['kapasitas_perina'],
            "kapasitas_ok_non_covid" => "",
            "kapasitas_hd_non_covid" => "",
            "kapasitas_isolasi_non_covid" => $data['kapasitas_iso_non_covid'],
            "kapasitas_igd_non_covid" => ""

        ),
        "kosong" => array(
            "kosong_vip_non_covid" => "",
            "kosong_kelas_1_non_covid_pria" => "",
            "kosong_kelas_1_non_covid_wanita" => "",
            "kosong_kelas_1_non_covid_anak" => "",
            "kosong_kelas_2_non_covid_pria" => "",
            "kosong_kelas_2_non_covid_wanita" => "",
            "kosong_kelas_2_non_covid_anak" => "",
            "kosong_kelas_3_non_covid_pria" => $data['kosong_pria'],
            "kosong_kelas_3_non_covid_wanita" => $data['kosong_wanita'],
            "kosong_kelas_3_non_covid_anak" => $data['kosong_anak'],
            "kosong_hcu_non_covid" => $data['kosong_hcu'],
            "kosong_iccu_non_covid" => "",
            "kosong_icu_non_covid" => "",
            "kosong_nicu_non_covid" => "",
            "kosong_picu_non_covid" => "",
            "kosong_perina_non_covid" => $data['kosong_perina'],
            "kosong_ok_non_covid" => "",
            "kosong_hd_non_covid" => "",
            "kosong_isolasi_non_covid" => $data['kosong_iso_non_covid'],
            "kosong_igd_non_covid" => ""

        )
    )

);
// $json = '{
//     "covid": {
//          "kapasitas": {
//               "kapasitas_icu_tekanan_negatif_dengan_ventilator_covid"=> "",
//               "kapasitas_icu_tekanan_negatif_tanpa_ventilator_covid"=> "",
//               "kapasitas_icu_tanpa_tekanan_negatif_dengan_ventilator_covid"=> "",
//               "kapasitas_icu_tanpa_tekanan_negatif_tanpa_ventilator_covid"=> "",
//               "kapasitas_isolasi_tekanan_negatif_covid_pria"=> "",
//               "kapasitas_isolasi_tekanan_negatif_covid_wanita"=> "",
//               "kapasitas_isolasi_tekanan_negatif_covid_anak"=> "",
//               "kapasitas_isolasi_tanpa_tekanan_negatif_covid_pria"=> "KP6",
//               "kapasitas_isolasi_tanpa_tekanan_negatif_covid_wanita"=> "KP5",
//               "kapasitas_isolasi_tanpa_tekanan_negatif_covid_anak"=> "KP4",
//               "kapasitas_nicu_covid"=> "",
//               "kapasitas_picu_covid"=> "",
//               "kapasitas_perina_covid"=> "",
//               "kapasitas_ok_covid"=> "",
//               "kapasitas_hd_covid"=> "",
//               "kapasitas_igd_covid"=> ""
//          },
//          "kosong": {
//               "kosong_icu_tekanan_negatif_dengan_ventilator_covid"=> "",
//               "kosong_icu_tekanan_negatif_tanpa_ventilator_covid"=> "",
//               "kosong_icu_tanpa_tekanan_negatif_dengan_ventilator_covid"=> "",
//               "kosong_icu_tanpa_tekanan_negatif_tanpa_ventilator_covid"=> "",
//               "kosong_isolasi_tekanan_negatif_covid_pria"=> "",
//               "kosong_isolasi_tekanan_negatif_covid_wanita"=> "",
//               "kosong_isolasi_tekanan_negatif_covid_anak"=> "",
//               "kosong_isolasi_tanpa_tekanan_negatif_covid_pria"=> "KP6",
//               "kosong_isolasi_tanpa_tekanan_negatif_covid_wanita"=> "KP5",
//               "kosong_isolasi_tanpa_tekanan_negatif_covid_anak"=> "KP4",
//               "kosong_nicu_covid"=> "",
//               "kosong_picu_covid"=> "",
//               "kosong_perina_covid"=> "",
//               "kosong_ok_covid"=> "",
//               "kosong_hd_covid"=> "",
//               "kosong_igd_covid"=> ""
//          }
//     },
//     "non_covid": {
//          "kapasitas": {
//               "kapasitas_vip_non_covid"=> "",
//               "kapasitas_kelas_1_non_covid_pria"=> "",
//               "kapasitas_kelas_1_non_covid_wanita"=> "",
//               "kapasitas_kelas_1_non_covid_anak"=> "",
//               "kapasitas_kelas_2_non_covid_pria"=> "",
//               "kapasitas_kelas_2_non_covid_wanita"=> "",
//               "kapasitas_kelas_2_non_covid_anak"=> "",
//               "kapasitas_kelas_3_non_covid_pria"=> "77",
//               "kapasitas_kelas_3_non_covid_wanita"=> "77",
//               "kapasitas_kelas_3_non_covid_anak"=> "KA1 - KA2",
//               "kapasitas_hcu_non_covid"=> "HCU1",
//               "kapasitas_iccu_non_covid"=> "",
//               "kapasitas_icu_non_covid"=> "",
//               "kapasitas_nicu_non_covid"=> "",
//               "kapasitas_picu_non_covid"=> "",
//               "kapasitas_perina_non_covid"=> "RK1 - RP1",
//               "kapasitas_ok_non_covid"=> "",
//               "kapasitas_hd_non_covid"=> "",
//               "kapasitas_isolasi_non_covid"=> "RI1",
//               "kapasitas_igd_non_covid"=> ""
//          },
//          "kosong": {
//               "kosong_vip_non_covid"=> "",
//               "kosong_kelas_1_non_covid_pria"=> "",
//               "kosong_kelas_1_non_covid_wanita"=> "",
//               "kosong_kelas_1_non_covid_anak"=> "",
//               "kosong_kelas_2_non_covid_pria"=> "",
//               "kosong_kelas_2_non_covid_wanita"=> "",
//               "kosong_kelas_2_non_covid_anak"=> "",
//               "kosong_kelas_3_non_covid_pria"=> "66",
//               "kosong_kelas_3_non_covid_wanita"=> "66",
//               "kosong_kelas_3_non_covid_anak"=> "66",
//               "kosong_hcu_non_covid"=> "HCU1",
//               "kosong_iccu_non_covid"=> "",
//               "kosong_icu_non_covid"=> "",
//               "kosong_nicu_non_covid"=> "",
//               "kosong_picu_non_covid"=> "",
//               "kosong_perina_non_covid"=> "RK1 - RP1",
//               "kosong_ok_non_covid"=> "",
//               "kosong_hd_non_covid"=> "",
//               "kosong_isolasi_non_covid"=> "RI1",
//               "kosong_igd_non_covid"=> ""
//          }
//     }
// }';
//curl_setopt_array($curl, array(
    //CURLOPT_URL => 'http://eis.dinkes.jakarta.go.id/apibedv2/bed',
    //CURLOPT_RETURNTRANSFER => true,
    //CURLOPT_ENCODING => '',
    //CURLOPT_MAXREDIRS => 10,
    //CURLOPT_TIMEOUT => 0,
    //CURLOPT_FOLLOWLOCATION => true,
    //CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
    //CURLOPT_CUSTOMREQUEST => 'POST',
    //CURLOPT_POSTFIELDS => "",
    //CURLOPT_HTTPHEADER => array(
        //'Api-Bed-User: 3172771',
        //'Api-Bed-Key: $2y$10$LIxJWv5Ltqt5y6n/IVw.A./61ph66BDnGvo7.LhbLcMBJXYT0ep8e',
        //'Content-Type: text/plain'
    //),
//));

//$response = curl_exec($curl);

//curl_close($curl);
//echo $response;

$myvars = json_encode($item);
echo $myvars;
$headers = array(
    "Api-Bed-User: " . $consid . "",
    "Api-Bed-Key: " . $secretKey . "",
    "Content-Type: application/json"
);
$ch = curl_init();

curl_setopt($ch, CURLOPT_URL, "http://eis.dinkes.jakarta.go.id/apibedv2/bed");
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
