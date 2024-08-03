<?php
error_reporting(0);
require_once('conf/conf.php');
require_once('libs/aes-encrypt/function.php');
//Set URI
$URI = isset($_SERVER['REQUEST_URI']) ? $_SERVER['REQUEST_URI'] : null;
$url = decode($URI);
$alert = isset($url['alert']) ? $url['alert'] : null;
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Self Assesment</title>
    <link rel="shortcut icon" href="img/icon.png" />
    <!-- <div class="panel panel-info">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#dropdown" href="#collapse2">
            </h4>
        </div>
    </div> -->
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="libs/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="libs/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="libs/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="libs/dist/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="libs/iCheck/square/blue.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="libs/dist/css/AdminLTE.min.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="libs/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="libs/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="libs/bootstrap-daterangepicker/daterangepicker.css">
    <!-- clock picker -->
    <link rel="stylesheet" type="text/css" href="libs/clockpicker/bootstrap-clockpicker.min.css">
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="libs/iCheck/all.css">
    <!-- fullCalendar -->
    <link rel="stylesheet" href="libs/fullcalendar/dist/fullcalendar.min.css">
    <link rel="stylesheet" href="libs/fullcalendar/dist/fullcalendar.print.min.css" media="print">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    <meta name="theme-color" content="#6700DF">
    <link rel="manifest" href="manifest.json">
    <!-- iOS Support -->
    <link rel="apple-touch-icon" href="assets/icons/icon-96x96.png">
    <meta name="apple-mobile-web-app-status-bar" content="#FFFFFF">


    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    <style>
        .preloader {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 9999;
            background-color: #fff;
        }

        .preloader .loading {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            font: 14px arial;
        }
    </style>
</head>

<body background="img/putih.jpg">
    <!-- page loader-->
    <!--                        <div class="preloader">
                                    <div class="loading">
                                        <center>
                                        <img src="img/loading-page.gif" width="120px">
                                        <p><h4 style="font-family: verdana;font-size: 18;">..Harap Tunggu..</h4></p>
                                        </center>
                                    </div>
                                </div>-->
    <!-- page loader-->
    <!-- Main content -->
    <section class="content">
        <center>
            <!-- <h3><b>SELAMAT DATANG</b></i>
                <p></p>
                </b>
            </h3> -->
            <img src="img/note.png" width="300" height="300">
            <h3>
        </center>
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <!-- <div class="align-center"> -->

            <div class="box box-warning box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title"><span class="fa fa-user-circle"> Form Self Assesment</span></h3>

                    <div class="box-tools pull-right">
                        <!--                                    <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>-->
                    </div>
                    <!-- /.box-tools -->
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <?php if (isset($_POST['submit'])) {
                        $tanggal = date('Y-m-d');
                        // $tanggal = '2021-09-18';
                        $cek = bukaquery("select * from pemeriksaan_mandiri where no_rawat='$_POST[no_rawat]' and tgl_perawatan='$tanggal'");
                        if ($cek->num_rows > 0) {
                            echo "<script>alert('No Rawat Sudah Mengisi Sebelumnya !!!'); window.location = 'http://localhost/webapps/skrining/" . "'</script>";
                        } else {
                            $autonumber = nokiamat('id_reg', 'pemeriksaan_mandiri');
                            // $autonumber = nokiamat('id_daftar', 'skrining_mandiri');
                            // $nourut = nourut('no_reg', 'skrining_mandiri');
                            // $no_reg = noRegWaw('skrining_mandiri', 'Vaksin', $tanggal);
                            // $nilai = 0;
                            // $deman = 0;
                            // $batuk = 0;
                            // $tenggorokan = 0;
                            // $penciuman = 0;
                            // $status = '-';
                            // $suhu = $_POST['suhu'];
                            // if ($_POST['demam'] == "YA") {
                            //     $demam = 1;
                            // } else {
                            //     $demam = 0;
                            // };
                            // if ($_POST['batuk'] == "YA") {
                            //     $batuk = 1;
                            // } else {
                            //     $batuk = 0;
                            // };
                            // $pilek = $_POST['pilek'];
                            // $menggigil = $_POST['menggigil'];
                            // $diare = $_POST['diare'];
                            // $napas = $_POST['napas'];
                            // $kepala = $_POST['kepala'];
                            // $bab = $_POST['bab'];
                            // if ($_POST['tenggorokan'] == "YA") {
                            //     $tenggorokan = 1;
                            // } else {
                            //     $tenggorokan = 0;
                            // };
                            // if ($_POST['penciuman'] == "YA") {
                            //     $penciuman = 1;
                            // } else {
                            //     $penciuman = 0;
                            // };
                            // $perasa = $_POST['perasa'];
                            // $hidung = $_POST['hidung'];
                            // $mual = $_POST['mual'];
                            // $kesulitan = $_POST['kesulitan'];
                            // $pusing = $_POST['pusing'];
                            // $menelan = $_POST['menelan'];
                            // if ($suhu > 37.5 &&  $napas == "YA") {
                            //     $nilai = 2;
                            // } else {
                            //     $nilai = 0;
                            // }

                            // $sum = 0;
                            // $sum = $nilai + $deman + $batuk + $tenggorokan + $penciuman;




                            // if ($_POST['pertanyaan_3'] == "YA") {
                            //     $no_reg = '';
                            //     $status = 'TIDAK TERIMA';
                            // } else if ($_POST['pertanyaan_4'] == "YA") {
                            //     $no_reg = '';
                            //     $status = 'TIDAK TERIMA';
                            // } else if ($sum > 2) {
                            //     $no_reg = '';
                            //     $status = 'TIDAK TERIMA';
                            // } else {
                            //     $no_reg = $no_reg;
                            //     $status = 'TERIMA';
                            // }
                            // //foto
                            // $foto = $_FILES['foto']['name'];
                            // $x_foto = explode('.', $foto);
                            // $nama_foto = $autonumber  . "_" . round(microtime(true)) . '.' . end($x_foto);
                            // $ekstensi_foto = strtolower(end($x_foto));
                            // $ukuran_foto = $_FILES['foto']['size'];
                            // $tmp_foto = $_FILES['foto']['tmp_name'];
                            // $folder_foto = "upload/";

                            bukainputcek("insert into pemeriksaan_mandiri set id_reg='$autonumber', no_rawat='$_POST[no_rawat]', keluhan='$_POST[keluhan]', rpd='$_POST[rpd]', rpk='$_POST[rpk]', rpo='$_POST[rpo]', alergi='$_POST[alergi]', tgl_perawatan=curdate(), jam_rawat=curtime()");
                            // echo "insert into pemeriksaan_mandiri set no_rawat='$_POST[no_rawat]', keluhan='$_POST[keluhan]', rpd='$_POST[rpd]', rpk='$_POST[rpk]', rpo='$_POST[rpo]', alergi='$_POST[alergi]', tgl_perawatan=curdate(), jam_rawat=curtime()";


                            // echo "<script>alert('Data berhasil di Simpan (HARAP SCREENSHOT SETELAH OK) !!!'); window.location = 'cetak.php?" . paramEncrypt("id='$autonumber'") . "'</script>";
                            // echo "<script>alert('Data berhasil di Simpan (HARAP SCREENSHOT SETELAH OK) !!!'); window.location = 'cetaksa.php?" . paramDecrypt("no_rawat='$_POST[no_rawat]'") . "'</script>";
                            echo "<script>alert('Data berhasil di Simpan (HARAP SCREENSHOT SETELAH OK) !!!'); window.location = 'cetaksa.php?nobok=$autonumber" . "'</script>";
                            //         } else {
                            //             echo "<script>alert('GAGAL !! Upload file, mungkin terlalu besar, tidak diperbolehkan lebih dari 5MB !!'); window.location = 'javascript:history.go(-1)'</script>";
                            //         }
                            //     } else {
                            //         echo "<script>alert('File hanya diperbolehkan berformat JPG / PNG !! " . $ekstensi_foto . "'); window.location = 'javascript:history.go(-1)'</script>";
                            //     }
                            // } else {
                            //     echo "<script>alert('Maaf anda belum upload file !!'); window.location = 'javascript:history.go(-1)'</script>";
                            // }
                        }
                    }
                    ?>
                    <div class="x_panel">
                        <form method="post" role="form" enctype="multipart/form-data" aria-labelledby="myModalLabel">
                            <fieldset>
                                <!-- <label>Kategori Skrining</label>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Vaksin" name="kategori" type="kategori" value="Vaksin" readonly>
                                </div> -->
                                <label>Nama Pasien</label>
                                <div class="form-group">
                                    <!-- <input class="form-control" placeholder="Nama Lengkap" name="nama" type="nama" value="" required> -->
                                    <select class="form-control select2" name="no_rawat" data-placeholder="-Pilih Nama Pasien-" style="width: 100%;" required>
                                        <option selected="selected" value="">-Pilih Nama Pasien-</option>
                                        <?php
                                        $tanggal = date('Y-m-d');
                                        $tm_pegawai = bukaquery("select reg_periksa.no_rawat,pasien.nm_pasien,poliklinik.nm_poli from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.tgl_registrasi='$tanggal'");
                                        while ($row = fetch_array($tm_pegawai)) {
                                            echo "<option value=" . $row['no_rawat'] . ">" . $row['nm_pasien'] . " " .  "(" . $row['nm_poli'] .  ")" . "</option>";
                                        }
                                        ?>
                                    </select>
                                </div>
                                <label class="control-label">Keluhan Utama</label>
                                <textarea class="form-control" rows="3" name="keluhan" placeholder="Apa keluhan Anda untuk berobat saat ini?" required></textarea>
                                <label class="control-label">Riwayat Penyakit Dahulu</label>
                                <textarea class="form-control" maxlength="2000" rows="3" name="rpd" placeholder="Apakah Anda pernah dirawat di rumah sakit/ menjalani operasi?" required></textarea>
                                <label class="control-label">Riwayat Penyakit Keluarga</label>
                                <textarea class="form-control" maxlength="2000" rows="3" name="rpk" placeholder="Apakah orang tua (Ayah dan Ibu Kandung) Anda memiliki riwayat (Contoh: sakit gula, darah tinggi, kanker, atau jantung)?" required></textarea>
                                <label class="control-label">Riwayat Pengobatan</label>
                                <textarea class="form-control" maxlength="2000" rows="3" name="rpo" placeholder="Apakah Anda mengonsumsi obat-obatan rutin yang diminum setiap hari? Obat apa yang biasa Anda minum/ obat terakhir yang Anda minum?" required></textarea>
                                <label class="control-label">Riwayat Alergi</label>
                                <textarea class="form-control" maxlength="50" rows="3" name="alergi" placeholder="Apakah Anda memiliki alergi terhadap makanan dan atau obat? Jika Ya, mohon sebutkan.(Contoh: Alergi seafood, alergi obat/ dll)." required></textarea>
                                <br>
                                <label style="color:red;">Mohon isi formulir dengan lengkap dan sebenar-benarnya. Jika ada kesulitan dalam pengisian formulir, mohon segera hubungi petugas kami.</label>
                                <!-- <label>Upload Bukti Transper</label> <label style="color:red;">*JPG / PNG Max 5MB</label>
                                <input type="file" class="form-control" name="foto" required> -->
                                <!-- Change this to a button or input when using this as a form -->
                                <br>
                                <br>
                                <button type="submit" name="submit" class="btn btn-success btn-block fa fa-sign-in"> SIMPAN </button>
                                <!--<p><a href="#" class="">Forget Password</a></p>-->
                            </fieldset>
                        </form>
                    </div>
                    <br><br><br>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- </div> -->
        </div>
        <div class="col-md-4">
        </div>
    </section>

	<!-- Select2 --> 
    <link rel="stylesheet" href="libs/select2/css/select2.min.css">
    <!-- jQuery 3 -->
    <script src="libs/jquery/jquery.min.js"></script>
    <!-- Bootstrap 3.3.7 -->
    <script src="libs/bootstrap/js/bootstrap.min.js"></script>
    <!-- iCheck -->
    <script src="libs/iCheck/icheck.min.js"></script>
    <!-- datepicker -->
    <script src="libs/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
    <script>
        $(function() {
            $('#today').datepicker({
                autoclose: true,
                startDate: "2"

            })
            $('#multidatepicker').datepicker({
                multidate: true,
                selectable: "multiple",
                weekNumber: true,
                showTrigger: '#callmg',
            })
            $('#datepicker').datepicker({
                autoclose: true
            })
            $('#datepicker1').datepicker({
                autoclose: true
            })
            $('#datepicker2').datepicker({
                autoclose: true
            })
            $('#datepicker3').datepicker({
                autoclose: true
            })
            $('#datepicker4').datepicker({
                autoclose: true
            })
            $('#datepicker5').datepicker({
                autoclose: true
            })
            $('#datepicker6').datepicker({
                autoclose: true
            })
            $('#datepicker7').datepicker({
                autoclose: true
            })
            $('#datepicker8').datepicker({
                autoclose: true
            })
            $('#datepicker9').datepicker({
                autoclose: true
            })
            $('#datepicker10').datepicker({
                autoclose: true
            })
            $('#datepicker11').datepicker({
                autoclose: true
            })
            $('#datepicker12').datepicker({
                autoclose: true
            })
            $('#tanggal_lahir').datepicker({
                autoclose: true,
            })
            $('#tanggal_lahir1').datepicker({
                autoclose: true,
            })
            $('#tanggal_lahir2').datepicker({
                autoclose: true,
            })
        })
    </script>

<!-- Select2 --> 
    <script src="libs/select2/js/select2.full.min.js"></script>
    <script> 
        $(function() { 
 
            //Initialize Select2 Elements 
            $('.select2').select2() 
 
        }) 
    </script>
    <script>
        $(function() {
            $('input').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%' // optional
            });
        });
        $(document).ready(function() {
            $(".preloader").fadeOut();
        });
    </script>
    <script src="main.js"></script>
</body>

</html>
