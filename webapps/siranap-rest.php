<?php
    //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Fauzan dari RSUK Kemayoran Jakarta Pusat
    require_once('conf/conf.php');
        $cons_id=isset($_GET['consid']) ? $_GET['consid'] : null ;
        $key=isset($_GET['key']) ? $_GET['key'] : null ;
	function  updateSiranap(){
            $kodekelas=0;
            $koderuang=0;
            $namaruang=0;
            $kapasitas=0;
            $tersedia=0;
            $tersediapria=0;
            $tersediawanita=0;
            $tersediapriawanita=0;
            
            # seting koneksi webservices #
            $xrsid = "3172771";  # ID Rumah Sakit #
            $xpass = md5("12345"); # Password #
            $strURLSiranap = "http://sirs.yankes.kemkes.go.id/sirsservice/ranap";  
            date_default_timezone_set("Asia/Jakarta");
            $timestamp = date("Y-m-d h:i:s");
            
            $_sql="SELECT aplicare_ketersediaan_kamar.kode_kelas_aplicare,sum(kapasitas) as kapasitas,sum(tersedia) as kosong
				FROM aplicare_ketersediaan_kamar where aplicare_ketersediaan_kamar.kode_kelas_aplicare='ISO' OR aplicare_ketersediaan_kamar.kode_kelas_aplicare='KL3' GROUP BY aplicare_ketersediaan_kamar.kode_kelas_aplicare" ;  
		$hasil=bukaquery($_sql);
            $xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xml>\n";                    
            while ($data = mysqli_fetch_array ($hasil)){
                $kodekelas=$data['kode_kelas_aplicare'];
				if($kodekelas =='KL3'){
					$koderuang='0005';
				}elseif ($kodekelas =='ISO'){
					$koderuang='0007';
				}
                //$koderuang=$data['kd_bangsal'];

                $xmlStr .= "<data>\n";
                $xmlStr .= "<kode_ruang>".$koderuang."</kode_ruang>\n";
                $xmlStr .= "<tipe_pasien>0000</tipe_pasien>\n";
                $xmlStr .= "<total_TT>".$data['kapasitas']."</total_TT>\n";
                $xmlStr .= "<terpakai_male>".($data['kapasitas']-$data['kosong'])."</terpakai_male>\n";
                $xmlStr .= "<terpakai_female>".$tersediawanita."</terpakai_female>\n";
                $xmlStr .= "<kosong_male>".$data['kosong']."</kosong_male>\n";
                $xmlStr .= "<kosong_female>".$tersediawanita."</kosong_female>\n";
                $xmlStr .= "<waiting>".$tersediawanita."</waiting>\n";
                $xmlStr .= "<tgl_update>".$timestamp."</tgl_update>\n";
                $xmlStr .= "</data>\n";
            }
            $xmlStr .="</xml>\n";
            header('Content-type: text/xml');
			echo $xmlStr;
	}

if ($cons_id == '3172771'){
	updateSiranap();
}else {
header('Location:http://467a0285adff.sn.mynetname.net:8080');
	exit;
}


 ?>
