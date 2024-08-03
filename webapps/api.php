<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header("Access-Control-Allow-Headers: X-Requested-With");
include 'conf/conf.php';

$action = trim(isset($_REQUEST['action']) ? $_REQUEST['action'] : null);

if ($action == "test") {
    $tgl_lahir = trim($_REQUEST['tgl_lahir']);
    $no_ktp = trim($_REQUEST['no_ktp']);
    $sql = bukaquery("SELECT pasien.nm_pasien, reg_periksa.tgl_registrasi, reg_periksa.jam_reg, poliklinik.nm_poli, dokter.nm_dokter, pemeriksaan_ralan.suhu_tubuh,
        pemeriksaan_ralan.tensi, pemeriksaan_ralan.nadi, pemeriksaan_ralan.respirasi, pemeriksaan_ralan.tinggi, pemeriksaan_ralan.berat, pemeriksaan_ralan.gcs,
        pemeriksaan_ralan.keluhan, pemeriksaan_ralan.pemeriksaan, pemeriksaan_ralan.alergi, pemeriksaan_ralan.penilaian,penjab.png_jawab
        FROM reg_periksa
        INNER JOIN pasien ON pasien.no_rkm_medis=reg_periksa.no_rkm_medis
        INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
        INNER JOIN dokter ON dokter.kd_dokter=reg_periksa.kd_dokter
        INNER JOIN penjab ON penjab.kd_pj=reg_periksa.kd_pj
        LEFT JOIN pemeriksaan_ralan ON pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat
        WHERE reg_periksa.stts!='Batal' AND pasien.no_ktp='$no_ktp' AND pasien.tgl_lahir='$tgl_lahir'
        ORDER BY
        reg_periksa.tgl_registrasi DESC
        LIMIT 1");
    $response = array();
    $response["data"] = array();
    while ($row = fetch_array($sql)) {
        $data = array(
            "nama_pasien" => $row['nm_pasien'],
            "tgl_registasi" => $row['tgl_registrasi'],
            "jam_reg" => $row['jam_reg'],
            "poli" => $row['nm_poli'],
            "dokter" => $row['nm_dokter'],
            "bayar" => $row['png_jawab'],
            "suhu" => $row['suhu_tubuh'],
            "tensi" => $row['tensi'],
            "nadi" => $row['nadi'],
            "respirasi" => $row['respirasi'],
            "tinggi_badan" => $row['tinggi'],
            "berat_badan" => $row['berat'],
            "gcs" => $row['gcs'],
            "keluhan" => $row['keluhan'],
            "pemeriksaan" => $row['pemeriksaan'],
            "alergi" => $row['alergi'],            
            "penilaian" => $row['penilaian']);
        array_push($response["data"], $data);
    }
    echo  json_encode($data);
}


