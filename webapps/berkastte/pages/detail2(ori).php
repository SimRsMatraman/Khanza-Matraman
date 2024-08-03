<!-- <script type="text/javascript" src="jquery.js"></script> -->
<!-- <script> alert("Pass Pharse yang anda masukan salah..!!!") </script> -->
<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data> <?php $getnik = isset($_GET['nik']) ? $_GET['nik'] : $_POST['nik']; ?> <input type="text" name="nik" value="<?= $getnik; ?>">
            <?php

            $action       = isset($_GET['action']) ? $_GET['action'] : NULL;
            $no_rawat     = isset($_GET['no_rawat']) ? $_GET['no_rawat'] : NULL;

            //$nik=isset($_GET['nik'])?$_GET['nik'] : '';

            $_sql         = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                                reg_periksa.kd_dokter,pegawai.no_ktp,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,
                                pasien.umur,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                                reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                                from reg_periksa inner join dokter inner join pegawai inner join pasien inner join poliklinik inner join penjab 
                                on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                                and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='$no_rawat' ";
            $hasil        = bukaquery($_sql);
            $baris        = mysqli_fetch_array($hasil);
            $no_rkm_medis = $baris["no_rkm_medis"];
            $nm_pasien    = $baris["nm_pasien"];
            $umurdaftar   = $baris["umurdaftar"];
            $sttsumur     = $baris["sttsumur"];
            $jk           = $baris["jk"];
            $almt_pj      = $baris["almt_pj"];
            $tgl_registrasi = $baris["tgl_registrasi"] . " " . $baris["jam_reg"];
            $nm_poli      = $baris["nm_poli"];
            $nm_dokter    = $baris["nm_dokter"];
            $status_lanjut  = $baris["status_lanjut"];
            $png_jawab    = $baris["png_jawab"];
            $nik          = $baris["no_ktp"];
            echo "<input type=hidden name=no_rawat  value=$no_rawat>
                      <input type=hidden name=action value=$action>";
            ?> <div style="width: 100%; height: 27%; overflow: auto;">

                <table width="100%" align="center">
                    <tr class="isi2">
                        <td width="25%" valign="top">Seesion NIK </td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><?php echo $getnik; ?></td>
                    </tr>
                    <tr class="isi2">
                        <td width="25%" valign="top">Nama Dokter / Petugas </td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><?php echo $nm_dokter; ?></td>
                    </tr>
                    <tr class="isi2">
                        <td width="25%" valign="top">No.Rawat </td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><?php echo $no_rawat; ?></td>
                    </tr>
                    <tr class="isi2">
                        <td width="25%" valign="top">No.RM</td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><?php echo $no_rkm_medis; ?></td>
                    </tr>
                    <tr class="isi2">
                        <td width="25%" valign="top">Nama Pasien</td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><?php echo $nm_pasien . ", " . $umurdaftar . " " . $sttsumur; ?></td>
                    </tr>
                    <tr class="isi2">
                        <td width="25%" valign="top">Berkas TTE BSSN</td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top">
                            <select name="kode" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                                <?php
                                $_sql = "SELECT kode,nama FROM master_berkas_tte ORDER BY nama";
                                $hasil = bukaquery($_sql);

                                while ($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[1]</option>";
                                }
                                ?>
                            </select>
                            <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>

                    <tr class="isi2">
                        <td width="25%" valign="top">File Berkas TTE BSSN (PDF/JPG)</td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><input name="dokumen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=file id="dokumen" size="30" maxlength="255" />
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="25%" valign="top">Pass Pharse</td>
                        <td width="" valign="top">:</td>
                        <td width="75%" valign="top"><input type="password" placeholder="Enter Pass Pharse" name="psw" required></td>
                    </tr>
                    <div>
                        <button class="btn btn-info" onclick="window.location.reload();">REFRESH</button>
                    </div>
                </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>

            <?php
            $BtnSimpan = isset($_POST['BtnSimpan']) ? $_POST['BtnSimpan'] : NULL;
            if (isset($BtnSimpan)) {
                $no_rawat           = trim($_POST['no_rawat']);
                $kode               = trim($_POST['kode']);
                $psw               = $_POST['psw'];
                $dokumen            = str_replace(" ", "_", "pages/upload/" . $_FILES['dokumen']['name']);
                $dokumen_tmp            = $_FILES['dokumen']['tmp_name'];
                //$host="https://esign.jakarta.go.id/api/sign/pdf";
                $host = "http://10.15.37.39/api/sign/pdf";
                $ch = curl_init($host);
                $arrheader = array("Authorization: Basic ZXNpZ246cXdlcnR5",);
                $fields = array(
                    'file' => new CurlFile($_FILES['dokumen']['tmp_name'], 'application/pdf'), 'nik' => $_GET['nik'], 'passphrase' => $psw, 'tampilan' => 'VISIBLE', 'page' => '1', 'image' => 'false', 'linkQR' => 'http://rsudcipayung.jakarta.go.id/rsudcipayung/', 'xAxis' => '0', 'yAxis' => '0', 'tag_koordinat' => '#', 'width' => '100', 'height' => '80', 'signed_file' => new CurlFile('C:\xampp\htdocs\webapps\berkastte\pages\Wew.pdf')
                );
                //$fp = fopen ('nyoba.pdf', 'w+');
                curl_setopt($ch, CURLOPT_URL, $host);
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
                curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
                curl_setopt($ch, CURLOPT_PROXY, "10.15.3.20:80");
                curl_setopt($ch, CURLOPT_ENCODING, "");
                curl_setopt($ch,  CURLOPT_MAXREDIRS, 10);
                curl_setopt($ch,  CURLOPT_TIMEOUT, 0);
                curl_setopt($ch,  CURLOPT_FOLLOWLOCATION, true);

                curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
                curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");

                curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
                curl_setopt($ch, CURLOPT_HTTPHEADER, $arrheader);
                curl_setopt($ch, CURLOPT_FAILONERROR, true);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_BINARYTRANSFER, true);
                $response = curl_exec($ch);
                $sukses = curl_getinfo($ch, CURLINFO_HTTP_CODE);
                $fp = $dokumen;
                file_put_contents($fp, $response);
                // print_r( $response);
                curl_close($ch);
                $nik = $_GET['nik'];
                date_default_timezone_set('Asia/Jakarta');
                $tanggal = date("Y-m-d H:i:s");

                if ($sukses == 400) {
                    Tambah3(" log_berkas_tte ", " '$nik','$tanggal','$no_rawat','$kode','-','$sukses'");
                    echo "Pass Pharse yang anda masukan salah..!!!";
                }
                if ($sukses == 200) {
                    move_uploaded_file(file_put_contents($fp, $response), $dokumen);
                    Tambah(" berkas_tte ", " '$no_rawat','$kode','$dokumen'", " Berkas TTE BSSN ");
                    Tambah3(" log_berkas_tte ", " '$nik','$tanggal','$no_rawat','$kode','$dokumen','$sukses'");


                    if ((!empty($no_rawat)) && (!empty($kode)) && (!empty($dokumen))) {
                        switch ($action) {
                            case "TAMBAH":

                                //Resume Medis
                                //'xAxis' => '1000','yAxis' => '100',
                                //3603120211930008
                                //eoog77is
                                //30122019
                                //#4321qwer*
                                // $curl = curl_init();


                                // curl_setopt_array($curl, array(
                                // CURLOPT_URL => "https://esign-dev.jakarta.go.id/api/sign/pdf",
                                // CURLOPT_RETURNTRANSFER => true,
                                // CURLOPT_ENCODING => "",
                                // CURLOPT_MAXREDIRS => 10,
                                // CURLOPT_TIMEOUT => 0,
                                // CURLOPT_FOLLOWLOCATION => true,
                                // CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                                // CURLOPT_CUSTOMREQUEST => "POST",
                                // CURLOPT_POSTFIELDS => array('file'=> new CURLFILE('../berkastte/pages/upload/'),'nik' => '$_GET["nik"]','passphrase' => '$psw','tampilan' => 'VISIBLE','page' => '1','image' => 'true','linkQR' => 'http://rsudcipayung.jakarta.go.id/rsudcipayung/','xAxis' => '0','yAxis' => '0','width' => '550','height' => '150'),
                                // CURLOPT_HTTPHEADER => array(
                                // "Authorization: Basic YWRtaW46cXdlcnR5",
                                // "Cookie: JSESSIONID=9E1CBF83FA7DDD4C78E4A8AB824379E9; TS0145b740=011bde22cd65c1654f4d6ad1900354adb16523c29f732731a0f78cd3496a2c6798bc67bf5d5a4ea8cecc9b32b05dad8d5604d4613eec23e92419f986d91d2165c923f13aa4"
                                // ),
                                // ));

                                // $response = curl_exec($curl);
                                // $sukses= curl_getinfo($curl,CURLINFO_HTTP_CODE);
                                // if($sukses==200)
                                // { move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                //     Tambah(" berkas_tte "," '$no_rawat','$kode','$dokumen'", " Berkas TTE BSSN " );

                                // }
                                // $responseData = json_encode($response, TRUE);
                                // echo $responseData;
                                // file_put_contents( '../berkastte/pages/upload/', $responseData );
                                // curl_close($curl);


                                echo "<meta http-equiv='refresh' content='1;URL=?act=Detail2&action=TAMBAH&no_rawat=$no_rawat'>";
                                break;
                        }
                    }
                } else if ((empty($no_rawat)) || (empty($kode)) || (empty($dokumen))) {
                    echo 'Semua field harus isi..!!!';
                }
            }
            ?>
            <div style="width: 100%; height: 56%; overflow: auto;">
                <?php
                $_sql = "SELECT berkas_tte.no_rawat,berkas_tte.kode, 
                        master_berkas_tte.nama,berkas_tte.lokasi_file 
                        from berkas_tte inner join master_berkas_tte
                        on berkas_tte.kode=master_berkas_tte.kode 
                        where berkas_tte.no_rawat='$no_rawat' ORDER BY master_berkas_tte.nama ASC ";
                $hasil = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                $ttllembur = 0;
                $ttlhr = 0;

                if (mysqli_num_rows($hasil) != 0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Berkas TTE BSSN</div></td>
                                <td width='65%'><div align='center'>File</div></td>
                            </tr>";
                    while ($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>
                                    <center>
                                    <a href='?act=Detail2&action=HAPUS&no_rawat=" . $baris["no_rawat"] . "&kode=" . $baris["kode"] . "&lokasi_file=" . $baris["lokasi_file"] . "'>[hapus]</a>
                                   </center>
                                </td>
                                <td>" . $baris["nama"] . "</td>
                                <td><a target=_blank href=../berkastte/pages/upload/" . $baris["lokasi_file"] . ">" . str_replace("pages/upload/", "", $baris["lokasi_file"]) . "</a></td>
                           </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Berkas TTE BSSN</div></td>
                                <td width='65%'><div align='center'>File</div></td>
                            </tr>
                          </table>";
                }
                ?>
            </div>
            <?php
            if ($action == "HAPUS") {
                unlink($_GET['lokasi_file']);
                Hapus(" berkas_tte ", " no_rawat ='" . $_GET['no_rawat'] . "' and kode ='" . $_GET['kode'] . "' and lokasi_file='" . $_GET['lokasi_file'] . "'", "?act=Detail2&action=TAMBAH&no_rawat=$no_rawat");
            }

            echo ("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td><td><input name='BtnKeluar' type='submit' class='button' value='&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;' /></td>                        
                    </tr>     
                 </table>");

            $BtnKeluar = isset($_POST['BtnKeluar']) ? $_POST['BtnKeluar'] : NULL;
            if (isset($BtnKeluar)) {
                echo "<meta http-equiv='refresh' content='1;URL=?act=List&action=Keluar'>";
            }
            ?>
        </form>
    </div>

</div>