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
    <title>Telemedicine</title>
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
            <img src="img/icon.png" width="200" height="200">
            <h3>
        </center>
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <!-- <div class="align-center"> -->

            <div class="box box-primary box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title"><span class="fa fa-user-circle"> Upload Bukti Bayar</span></h3>

                    <div class="box-tools pull-right">
                        <!--                                    <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>-->
                    </div>
                    <!-- /.box-tools -->
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <?php if (isset($_POST['submit'])) {
                        $autonumber = nokiamat('no_booking', 'booking_telemedicine');
                        // $tanggal = FormatTgl('Y-m-d', $_POST['tgl_lahir']);
                        // $tanggal1 = FormatTgl('Y-m-d', $_POST['tgl_str']);
                        $ekstensi_diperbolehkan = array('pdf', 'jpg', 'png');

                        //foto
                        $foto = $_FILES['foto']['name'];
                        $x_foto = explode('.', $foto);
                        $nama_foto = $autonumber  . "_" . round(microtime(true)) . '.' . end($x_foto);
                        $ekstensi_foto = strtolower(end($x_foto));
                        $ukuran_foto = $_FILES['foto']['size'];
                        $tmp_foto = $_FILES['foto']['tmp_name'];
                        $folder_foto = "upload/";

                        // echo "insert into tm_daftar set id_daftar='$autonumber', nik='$_POST[nik]',nama_pegawai='$_POST[nama_pegawai]',tempat_lahir='$_POST[tempat_lahir]',tgl_lahir='$tanggal',jk='$_POST[jk]',status_nikah='$_POST[status_nikah]',agama='$_POST[agama]',alamat='$_POST[alamat]',no_hp='$_POST[no_hp]',email='$_POST[email]',nilai='$_POST[nilai]',pendidikan='$_POST[pendidikan]',id_formasi='$_POST[id_formasi]', foto='$nama_foto',riwayat='$nama_riwayat',ktp='$nama_ktp',kk='$nama_kk', ijazah='$nama_ijazah',transkrip='$nama_transkrip',sertifikat='$nama_sertifikat',str='$nama_str',status='-',date=now()";


                        $folder = "upload/";
                        if ($foto != '' ) {
                            if (
                                in_array($ekstensi_foto, $ekstensi_diperbolehkan) === true
                            ) {
                                if ($ukuran < 5242880) {

                                    bukainputcek("update booking_telemedicine set foto='$nama_foto',upload=now() where no_booking='$_GET[nobok]'");
                                    move_uploaded_file($tmp_foto, $folder_foto . $nama_foto);
                                    // header('location:cetak.php');
                                    // echo getOne("update booking_telemedicine set foto='$nama_foto',date=now() where no_booking='20210806001'");



                                    // echo "<script>alert('Data berhasil di Simpan (HARAP SCREENSHOT SETELAH OK) !!!'); window.location = 'cetak.php?" . paramEncrypt("id='$autonumber'") . "'</script>";
                                    echo "<script>alert('Data berhasil di Simpan (HARAP SCREENSHOT SETELAH OK) !!!'); window.location = 'cetak.php?nobok=$_GET[nobok]" . "'</script>";
                                } else {
                                    echo "<script>alert('GAGAL !! Upload file, mungkin terlalu besar, tidak diperbolehkan lebih dari 5MB !!'); window.location = 'javascript:history.go(-1)'</script>";
                                }
                            } else {
                                echo "<script>alert('File hanya diperbolehkan berformat JPG / PNG !! " . $ekstensi_foto . "'); window.location = 'javascript:history.go(-1)'</script>";
                            }
                        } else {
                            echo "<script>alert('Maaf anda belum upload file !!'); window.location = 'javascript:history.go(-1)'</script>";
                        }
                    }
                    ?>
                    <div class="x_panel">
                        <form method="post" role="form" enctype="multipart/form-data" aria-labelledby="myModalLabel">
                            <fieldset>
                                <label>No Booking</label>
                                <div class="form-group">
                                    <input maxlength="15" class="form-control" placeholder="NOMOR BOOKING" value="<?= $_GET['nobok'] ?>" name="no_booking" type="number" autofocus required readonly>
                                </div>
                                <!-- <label>Nama Lengkap</label>
                                <div class="form-group">
                                    <input class="form-control" placeholder="NAMA LENGKAP" name="nama_pegawai" type="nama_pegawai" value="" required>
                                </div> -->
                                <label>Upload Bukti Transper</label> <label style="color:red;">*JPG / PNG Max 5MB</label>
                                <input type="file" class="form-control" name="foto" required>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="submit" name="submit" class="btn btn-success btn-block fa fa-sign-in"> UPLOAD </button>
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