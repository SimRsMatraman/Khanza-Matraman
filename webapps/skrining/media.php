<?php
error_reporting(0);
date_default_timezone_set('Asia/Jakarta');
require_once ('conf/conf.php');
require_once ('libs/aes-encrypt/function.php');
session_start();
//cek apakah ada session atau sudah login
if (!isset($_SESSION['superuser'])) {
    if (!isset($_SESSION['nip'])) {
        header('location:login');
    }
    if (!isset($_SESSION['id_level'])) {
        header('location:login');
    }
} else {
    
}
//setup setting waktu kinerja
$day = date('d');
$setup = mysqli_fetch_array(bukaquery("select setup.tutup_kinerja,setup.validasi_pj,setup.validasi_kasie,setup.validasi_jadwal from setup"));
//tahun dan bulan kemarin
$tgl = TanggalAkhirBulanKemarin();
$bln_sebelumnya = FormatTgl('m', $tgl);
$thn = FormatTgl('Y', $tgl);
//tahun dan bulan berjalan
$thn_now = date('Y');
$bln_now = date('m');
//ambil session dan diganti ke variabel
$spv = isset($_SESSION['superuser']) ? $_SESSION['superuser'] : null;
$nip = isset($_SESSION['nip']) ? $_SESSION['nip'] : null;
$idlevel = isset($_SESSION['id_level']) ? $_SESSION['id_level'] : null;
$status_pegawai = getOne("select status_pegawai from tm_pegawai where nip='$nip'");
$id_user = getOne("select tm_pegawai.id_user from tm_pegawai where nip='$nip'");
$nama_pegawai = getOne("select tm_pegawai.nama_pegawai from tm_pegawai where nip='$nip'");
$id_unit = getOne("select tm_pegawai.id_unit from tm_pegawai where nip='$nip'");
$kasie = getOne("select tm_pegawai.sub_bagian from tm_pegawai where nip='$nip'");
$kasatpel_pegawai = getOne("select tm_pegawai.id_kasatpel from tm_pegawai where nip='$nip'");
$kasatpel = getOne("select tm_kasatpel.id_kasatpel from tm_kasatpel where id_kasatpel='$kasatpel_pegawai'");
//hak akses
$superuser = getOne("select setup.kode_skpd from setup where nama_instansi='$spv'");
$hak_akses = mysqli_fetch_array(bukaquery("SELECT tm_level.menu_kepegawaian, tm_level.menu_diklat, tm_level.menu_keuangan, tm_level.menu_val_pj, tm_level.menu_val_kasatpel, tm_level.menu_val_kasie, tm_level.menu_laporan,tm_level.menu_surtug, tm_level.upload_perpustakaan, tm_level.menu_helpdesk  
                                    FROM tm_level where id_level='$idlevel'"));
//cek surat tugas masuk
$surat_tugas = getOne("SELECT count(tm_surat_tugas.id_surat) as jumlah FROM tm_surat_tugas 
                    inner JOIN tm_add_surtug ON tm_surat_tugas.id_surat=tm_add_surtug.id_surat
                    where tm_add_surtug.nip='$nip' and tm_add_surtug.`read`='0'");
//cek pengajuan cuti masuk
$cek_pengajuan_cuti1 = getOne("select count(tm_cuti.id_cuti) as total from tm_cuti where tm_cuti.nip_pengganti='$nip' and tm_cuti.acc_pengganti='-'");
if ($hak_akses['menu_val_pj'] == '1' and $hak_akses['menu_val_kasatpel'] == '0') {
    $cek_pengajuan_cuti2 = getOne("select count(tm_cuti.id_cuti) as total from tm_cuti "
            . "inner join tm_pegawai on tm_cuti.id_user=tm_pegawai.id_user where tm_pegawai.id_unit='$id_unit' and tm_cuti.acc_pengganti='Y' and tm_cuti.acc_pj='-'");
} elseif ($hak_akses['menu_val_kasatpel'] == '1' and $hak_akses['menu_val_pj'] == '0') {
    $cek_pengajuan_cuti2 = getOne("select count(tm_cuti.id_cuti) as total from tm_cuti "
            . "inner join tm_pegawai on tm_cuti.id_user=tm_pegawai.id_user where tm_pegawai.id_kasatpel='$kasatpel' and tm_cuti.acc_pengganti='Y' and tm_cuti.acc_pj='-'");
} else {
    $cek_pengajuan_cuti2 = 0;
}
if ($hak_akses['menu_kepegawaian'] == '1') {
    $cek_pengajuan_cuti3 = getOne("select count(tm_cuti.id_cuti) as total from tm_cuti where tm_cuti.acc_kepegawaian='-' and tm_cuti.acc_pj='Y'");
}
if ($hak_akses['menu_val_kasie'] == '1') {
    $cek_pengajuan_cuti4 = getOne("select count(tm_cuti.id_cuti) as total from tm_cuti "
            . "inner join tm_pegawai on tm_cuti.id_user=tm_pegawai.id_user where tm_pegawai.sub_bagian='$kasie' and tm_cuti.nip_kasie='$nip' and tm_cuti.acc_kepegawaian='Y' and tm_cuti.acc_kasie='-'");
}
$pengajuan_cuti = $cek_pengajuan_cuti1 + $cek_pengajuan_cuti2 + $cek_pengajuan_cuti3 + $cek_pengajuan_cuti4;
//notif ada surat tugas
if ($surat_tugas > 0) {
    $notif_tugas = "<embed name='sound_file' src='sound/surat_masuk.mp3' loop='true' hidden='true' autostart='true'/>";
    echo $notif_tugas;
}
//notid pengajuan cuti
if ($pengajuan_cuti > 0) {
    $notif_cuti = "<embed name='sound_file' src='sound/surat_masuk.mp3' loop='true' hidden='true' autostart='true'/>";
    echo $notif_cuti;
}
//foto\
$foto1 = mysqli_fetch_array(bukaquery("select tm_pegawai.jk, tm_pegawai.foto from tm_pegawai where tm_pegawai.nip='$nip'"));
if ($foto1['foto'] == '-' OR $foto1['foto'] == '') {
    if ($foto1['jk'] == 'L') {
        $foto = 'img/laki.png';
    } else {
        $foto = 'img/perempuan.png';
    }
} else {
    $foto = "img/" . $foto1['foto'];
}
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
        <title>NPiS - Non PNS Information System</title>
		<link rel="shortcut icon" href="img/icon.png" />
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.7 -->
        <link rel="stylesheet" href="libs/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="libs/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="libs/Ionicons/css/ionicons.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="libs/datatables.net-bs/css/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="libs/datatables.net-bs/Buttons-1.5.1/css/buttons.dataTables.min.css">
        <!-- bootstrap slider -->
        <link rel="stylesheet" href="libs/bootstrap-slider/slider.css">
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
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="libs/dist/css/skins/_all-skins.min.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="libs/select2/css/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="libs/dist/css/AdminLTE.min.css">
        <!-- bootstrap wysihtml5 - text editor -->
        <link rel="stylesheet" href="libs/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Google Font -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
        <style>
            .example-modal .modal {
                position: relative;
                top: auto;
                bottom: auto;
                right: auto;
                left: auto;
                display: block;
                z-index: 1;
            }

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
                transform: translate(-50%,-50%);
                font: 14px arial;
            }
            .example-modal .modal {
                background: transparent !important;
            }
            .upper { text-transform: uppercase; }
            .lower { text-transform: lowercase; }
            .cap   { text-transform: capitalize; }
            .small { font-variant:   small-caps; }
            .arial14 { font-family: arial; font-size: 14; } 
            .arial12 { font-family: arial; font-size: 12; }
        </style>
        
    </head>
    <body class="hold-transition skin-black sidebar-mini"> 
        <!-- page loader-->
<!--        <div class="preloader">
            <div class="loading">
                <center>
                    <img src="img/loading-page.gif" width="120px">
                    <p><h4 style="font-family: verdana;font-size: 18;">..Harap Tunggu..</h4></p>
                </center>
            </div>
        </div>-->
        <!-- page loader-->
        <div class="wrapper">

            <header class="main-header">
                <!-- Logo -->
                <a href="index2.html" class="logo">
                    <!-- mini logo for sidebar mini 50x50 pixels -->
                    <span class="logo-mini"><i class="fa fa-ils"><b>PiS</b></i></span>
                    <!-- logo for regular state and mobile devices -->
                    <span class="logo-lg"><i class="fa fa-ils"><b>PiS - Non PNS Information System</b></i></span>
                </a>
                <!-- Header Navbar: style can be found in header.less -->
                <nav class="navbar navbar-static-top">
                    <!-- Sidebar toggle button-->
                    <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                        <span class="sr-only">Toggle navigation</span>
                    </a>

                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- Messages: style can be found in dropdown.less-->
                            <li class="dropdown messages-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-envelope-o"></i>
                                    <span class="label label-success">
                                        <?php echo $surat_tugas; ?>
                                    </span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header"><?php echo $surat_tugas; ?> Surat Tugas Belum Anda Baca </li>
                                    <li>
                                        <!-- inner menu: contains the actual data -->
                                        <ul class="menu">
                                            <?php
                                            $tampil_surat = bukaquery("SELECT tm_surat_tugas.id_surat,tm_surat_tugas.no_surat, tm_surat_tugas.tgl_surat, tm_surat_tugas.kegiatan, tm_add_surtug.`read`   
                                                    FROM tm_surat_tugas 
                                                    inner JOIN tm_add_surtug ON tm_surat_tugas.id_surat=tm_add_surtug.id_surat
                                                    where tm_add_surtug.nip='$nip' order by tm_surat_tugas.id_surat desc");
                                            while ($row = fetch_array($tampil_surat)) {
                                                if ($row['read'] == '0') {
                                                    $icon = 'img/surat_in.jpg';
                                                    $status_baca = '<b style=color:red>New</b>';
                                                } else {
                                                    $icon = 'img/surat_out.jpg';
                                                    $status_baca = '<b style=color:green>Read</b>';
                                                }
                                                ?>
                                                <li><!-- start message -->
                                                    <a href="<?php echo 'cetak-surat-tugas-' . $row['id_surat'] . ''; ?>" target="_blank">
                                                        <div class="pull-left">
                                                            <img src="<?php echo $icon; ?>" width="50px" height="50px" class="img-circle" alt="User Image">
                                                        </div>
                                                        <h4>
                                                            <?php echo $row['no_surat']; ?>
                                                            <small><i class="fa fa-clock-o"></i> <?php echo "dibuat : " . FormatTgl('d M Y', $row['tgl_surat']) . " / $status_baca"; ?>
                                                            </small>
                                                        </h4>
                                                        <p><?php echo $row['kegiatan']; ?></p>
                                                    </a>
                                                </li>
                                                <?php
                                            }
                                            ?>
                                        </ul>
                                    </li>
                                    <li class="footer"><a href="#">See All Messages</a></li>
                                </ul>
                            </li>
                            <!-- Notifications: style can be found in dropdown.less -->
                            <li class="dropdown notifications-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-bell-o"></i>
                                    <span class="label label-warning"><?php echo $pengajuan_cuti; ?></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">Ada <?php echo $pengajuan_cuti; ?> permintaan ACC Cuti</li>
                                    <li>
                                        <!-- inner menu: contains the actual data -->
                                        <ul class="menu">                                            
                                            <li>
                                                <a href="?<?php echo paramEncrypt('module=cuti-pegawai&act=list-data-assesment-pengganti-cuti-pegawai'); ?>">
                                                    <i class="fa fa-exchange text-aqua"></i> <?php echo $cek_pengajuan_cuti1; ?> Menunggu Assesment Pengganti 
                                                </a>
                                            </li>  
                                            <?php if ($hak_akses['menu_val_pj'] == '1' and $hak_akses['menu_val_kasatpel'] == '0') { ?>
                                                <li>
                                                    <a href="?<?php echo paramEncrypt('module=cuti-pegawai&act=list-data-assesment-pj-cuti-pegawai'); ?>">
                                                        <i class="fa fa-exchange text-aqua"></i> <?php echo $cek_pengajuan_cuti2; ?> Menunggu Assesment Kasatpel
                                                    </a>
                                                </li>  
                                            <?php } elseif ($hak_akses['menu_val_kasatpel'] == '1' and $hak_akses['menu_val_pj'] == '0') { ?>
                                                <li>
                                                    <a href="?<?php echo paramEncrypt('module=cuti-pegawai&act=list-data-assesment-pj-cuti-pegawai'); ?>#">
                                                        <i class="fa fa-exchange text-aqua"></i> <?php echo $cek_pengajuan_cuti2; ?> Menunggu Assesment PJ
                                                    </a>
                                                </li>  
                                            <?php } ?>
                                            <?php if ($hak_akses['menu_kepegawaian'] == '1') { ?>
                                                <li>
                                                    <a href="?<?php echo paramEncrypt('module=cuti-pegawai&act=list-data-assesment-kepegawaian-cuti-pegawai'); ?>">
                                                        <i class="fa fa-exchange text-aqua"></i> <?php echo $cek_pengajuan_cuti3; ?> Menunggu Assesment Kepegawaian
                                                    </a>
                                                </li>   
                                            <?php } ?>
                                            <?php if ($hak_akses['menu_val_kasie'] == '1') { ?>
                                                <li>
                                                    <a href="?<?php echo paramEncrypt('module=cuti-pegawai&act=list-data-assesment-kasie-cuti-pegawai'); ?>">
                                                        <i class="fa fa-exchange text-aqua"></i> <?php echo $cek_pengajuan_cuti4; ?> Menunggu Assesment Kasie
                                                    </a>
                                                </li>

                                            <?php } ?>
                                        </ul>
                                    </li>
                                    <li class="footer"><a href="#">View all</a></li>
                                </ul>
                            </li>
                            <!-- Tasks: style can be found in dropdown.less -->
                            <!--                            <li class="dropdown tasks-menu">
                                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                                <i class="fa fa-flag-o"></i>
                                                                <span class="label label-danger">9</span>
                                                            </a>
                                                            <ul class="dropdown-menu">
                                                                <li class="header">You have 9 tasks</li>
                                                                <li>
                                                                     inner menu: contains the actual data 
                                                                    <ul class="menu">
                                                                        <li> Task item 
                                                                            <a href="#">
                                                                                <h3>
                                                                                    Design some buttons
                                                                                    <small class="pull-right">20%</small>
                                                                                </h3>
                                                                                <div class="progress xs">
                                                                                    <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar"
                                                                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                                                        <span class="sr-only">20% Complete</span>
                                                                                    </div>
                                                                                </div>
                                                                            </a>
                                                                        </li>
                                                                         end task item 
                                                                        <li> Task item 
                                                                            <a href="#">
                                                                                <h3>
                                                                                    Create a nice theme
                                                                                    <small class="pull-right">40%</small>
                                                                                </h3>
                                                                                <div class="progress xs">
                                                                                    <div class="progress-bar progress-bar-green" style="width: 40%" role="progressbar"
                                                                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                                                        <span class="sr-only">40% Complete</span>
                                                                                    </div>
                                                                                </div>
                                                                            </a>
                                                                        </li>
                                                                         end task item 
                                                                        <li> Task item 
                                                                            <a href="#">
                                                                                <h3>
                                                                                    Some task I need to do
                                                                                    <small class="pull-right">60%</small>
                                                                                </h3>
                                                                                <div class="progress xs">
                                                                                    <div class="progress-bar progress-bar-red" style="width: 60%" role="progressbar"
                                                                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                                                        <span class="sr-only">60% Complete</span>
                                                                                    </div>
                                                                                </div>
                                                                            </a>
                                                                        </li>
                                                                         end task item 
                                                                        <li> Task item 
                                                                            <a href="#">
                                                                                <h3>
                                                                                    Make beautiful transitions
                                                                                    <small class="pull-right">80%</small>
                                                                                </h3>
                                                                                <div class="progress xs">
                                                                                    <div class="progress-bar progress-bar-yellow" style="width: 80%" role="progressbar"
                                                                                         aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                                                        <span class="sr-only">80% Complete</span>
                                                                                    </div>
                                                                                </div>
                                                                            </a>
                                                                        </li>
                                                                         end task item 
                                                                    </ul>
                                                                </li>
                                                                <li class="footer">
                                                                    <a href="#">View all tasks</a>
                                                                </li>
                                                            </ul>
                                                        </li>-->
                            <!-- User Account: style can be found in dropdown.less -->
                            <li class="dropdown user user-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="<?php echo $foto; ?>" class="user-image" alt="User Image">
                                    <span class="hidden-xs"><?php echo paramDecrypt($superuser) . $nama_pegawai; ?></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- User image -->
                                    <li class="user-header">
                                        <img src="<?php echo $foto; ?>" class="img-circle" alt="User Image">

                                        <p>
                                            <?php echo $nama_pegawai . paramDecrypt($superuser); ?> 
                                            <small><?php echo $nip . $spv; ?></small>
                                        </p>
                                    </li>
                                    <!-- Menu Body -->
                                    <!--                                    <li class="user-body">
                                                                            <div class="row">
                                                                                <div class="col-xs-4 text-center">
                                                                                    <a href="#">Followers</a>
                                                                                </div>
                                                                                <div class="col-xs-4 text-center">
                                                                                    <a href="#">Sales</a>
                                                                                </div>
                                                                                <div class="col-xs-4 text-center">
                                                                                    <a href="#">Friends</a>
                                                                                </div>
                                                                            </div>
                                                                             /.row 
                                                                        </li>-->
                                    <!-- Menu Footer-->
                                    <li class="user-footer">
                                        <div class="pull-left">
                                            <?php if ($superuser == '') { ?>
                                                <a href="?<?php echo paramEncrypt('module=simpeg&act=profile-update'); ?>" class="btn btn-default btn-flat">Profile</a>
                                            <?php } else { ?>
                                                <a href="?<?php echo paramEncrypt('module=simpeg&act=ganti-password'); ?>" class="btn btn-default btn-flat">Ganti Password</a>
                                            <?php } ?>
                                        </div>
                                        <div class="pull-right">
                                            <a href="logout" class="btn btn-default btn-flat">Sign out</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                            <!-- Control Sidebar Toggle Button -->
                            <!--                            <li>
                                                            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                                                        </li>-->
                        </ul>
                    </div>
                </nav>
            </header>

            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!--Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="<?php echo $foto; ?>" class="user-image-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p><?php echo $nip . paramDecrypt($superuser); ?></p>
                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!--search form -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->
                    <!--Menu-->
                    <?php require_once ('menu.php'); ?>
                    <!--/.Menu-->
                </section>
                <!--/.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1><!--<span class="fa fa-hospital-o"></span>--> 
                        <!--<span class="text-muted"><?php echo $spv; ?></span> -->
                        <!--<small> <!--Kinerja Pegawai Non PNS <?php  echo ucwords(strtolower(getOne("select nama_instansi from setup"))); ?></small>-->
                        <center>وَمِنْ رَحْمَتِهِ جَعَلَ لَكُمُ اللَّيْلَ وَالنَّهَارَ لِتَسْكُنُوا فِيهِ وَلِتَبْتَغُوا مِنْ فَضْلِهِ وَلَعَلَّكُمْ تَشْكُرُونَ</center>
                        <marquee scrolldelay="100" direction="left">Dan karena rahmat-Nya, Dia jadikan untukmu malam dan siang, supaya kamu beristirahat pada malam itu dan supaya kamu mencari sebahagian dari karunia-Nya (pada siang hari) dan agar kamu bersyukur kepada-Nya.</marquee>
                        
                    </h1>
                    <h1>
                    <!--                    <ol class="breadcrumb">
                                            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                                            <li class="active">Dashboard</li>
                                        </ol>-->
                </section>

                <!-- Main content -->
                <section class="content">
                    <!--                     Small boxes (Stat box) 
                                        <div class="row">
                                            <div class="col-lg-3 col-xs-6">
                                                 small box 
                                                <div class="small-box bg-aqua">
                                                    <div class="inner">
                                                        <h3>150</h3>
                    
                                                        <p>New Orders</p>
                                                    </div>
                                                    <div class="icon">
                                                        <i class="ion ion-bag"></i>
                                                    </div>
                                                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                                                </div>
                                            </div>
                                             ./col 
                                            <div class="col-lg-3 col-xs-6">
                                                 small box 
                                                <div class="small-box bg-green">
                                                    <div class="inner">
                                                        <h3>53<sup style="font-size: 20px">%</sup></h3>
                    
                                                        <p>Bounce Rate</p>
                                                    </div>
                                                    <div class="icon">
                                                        <i class="ion ion-stats-bars"></i>
                                                    </div>
                                                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                                                </div>
                                            </div>
                                             ./col 
                                            <div class="col-lg-3 col-xs-6">
                                                 small box 
                                                <div class="small-box bg-yellow">
                                                    <div class="inner">
                                                        <h3>44</h3>
                    
                                                        <p>User Registrations</p>
                                                    </div>
                                                    <div class="icon">
                                                        <i class="ion ion-person-add"></i>
                                                    </div>
                                                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                                                </div>
                                            </div>
                                             ./col 
                                            <div class="col-lg-3 col-xs-6">
                                                 small box 
                                                <div class="small-box bg-red">
                                                    <div class="inner">
                                                        <h3>65</h3>
                    
                                                        <p>Unique Visitors</p>
                                                    </div>
                                                    <div class="icon">
                                                        <i class="ion ion-pie-graph"></i>
                                                    </div>
                                                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                                                </div>
                                            </div>
                                             ./col 
                                        </div>
                                         /.row -->
                    <!-- Main row -->
                    <div class="row">
                        <!-- Left col -->
                        <section class="col-lg-12 connectedSortable">
                            <!--                            <div class="box">                                -->
                            <?php
                            require_once ('page.php');
                            ?>
                            <!--</div>-->


                        </section>
                        <!-- /.Left col -->
                        <!-- right col -->
                        <section class="col-lg-5 connectedSortable">



                        </section>
                        <!-- /.right col -->

                    </div>
                    <!-- /.row (main row) -->

                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 3.0.0
                </div>
                <strong>Copyright &copy; 2019<a href=""> RSUD_CIPAYUNG</a>.</strong> All rights reserved.
            </footer>

            <!-- Control Sidebar -->
            <aside class="control-sidebar control-sidebar-dark">
                <!-- Create the tabs -->
                <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
                    <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
                    <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <!-- Home tab content -->
                    <div class="tab-pane" id="control-sidebar-home-tab">
                        <h3 class="control-sidebar-heading">Recent Activity</h3>
                        <ul class="control-sidebar-menu">
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-birthday-cake bg-red"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                                        <p>Will be 23 on April 24th</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-user bg-yellow"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

                                        <p>New phone +1(800)555-1234</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

                                        <p>nora@example.com</p>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <i class="menu-icon fa fa-file-code-o bg-green"></i>

                                    <div class="menu-info">
                                        <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

                                        <p>Execution time 5 seconds</p>
                                    </div>
                                </a>
                            </li>
                        </ul>
                        <!-- /.control-sidebar-menu -->

                        <h3 class="control-sidebar-heading">Tasks Progress</h3>
                        <ul class="control-sidebar-menu">
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Custom Template Design
                                        <span class="label label-danger pull-right">70%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Update Resume
                                        <span class="label label-success pull-right">95%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Laravel Integration
                                        <span class="label label-warning pull-right">50%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">
                                    <h4 class="control-sidebar-subheading">
                                        Back End Framework
                                        <span class="label label-primary pull-right">68%</span>
                                    </h4>

                                    <div class="progress progress-xxs">
                                        <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                                    </div>
                                </a>
                            </li>
                        </ul>
                        <!-- /.control-sidebar-menu -->

                    </div>
                    <!-- /.tab-pane -->
                    <!-- Stats tab content -->
                    <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
                    <!-- /.tab-pane -->
                    <!-- Settings tab content -->
                    <div class="tab-pane" id="control-sidebar-settings-tab">
                        <form method="post">
                            <h3 class="control-sidebar-heading">General Settings</h3>

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Report panel usage
                                    <input type="checkbox" class="pull-right" checked>
                                </label>

                                <p>
                                    Some information about this general settings option
                                </p>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Allow mail redirect
                                    <input type="checkbox" class="pull-right" checked>
                                </label>

                                <p>
                                    Other sets of options are available
                                </p>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Expose author name in posts
                                    <input type="checkbox" class="pull-right" checked>
                                </label>

                                <p>
                                    Allow the user to show his name in blog posts
                                </p>
                            </div>
                            <!-- /.form-group -->

                            <h3 class="control-sidebar-heading">Chat Settings</h3>

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Show me as online
                                    <input type="checkbox" class="pull-right" checked>
                                </label>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Turn off notifications
                                    <input type="checkbox" class="pull-right">
                                </label>
                            </div>
                            <!-- /.form-group -->

                            <div class="form-group">
                                <label class="control-sidebar-subheading">
                                    Delete chat history
                                    <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                                </label>
                            </div>
                            <!-- /.form-group -->
                        </form>
                    </div>
                    <!-- /.tab-pane -->
                </div>
            </aside>
            <!-- /.control-sidebar -->
            <!-- Add the sidebar's background. This div must be placed
                 immediately after the control sidebar -->
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- ./wrapper --> 

        <!-- jQuery 3 -->
        <script src="libs/jquery/jquery.min.js"></script>
        <!-- jQuery UI 1.11.4 -->
        <script src="libs/jquery-ui/jquery-ui.min.js"></script>
        <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
        <?php
        if ($cek_hak_akses['menu_val_pj'] == '1' OR $cek_hak_akses['menu_val_kasatpel'] == '1' OR $cek_hak_akses['menu_val_kasie'] == '1') {
            ?>        
            <script>
                        $(document).ready(function () {
                $(".tombol-search").click(function () {
                var data = $('.form-search').serialize();
                        $.ajax({
                        type: 'POST',
                                url: "aksi.php",
                                data: data,
                                success: function () {
                                $('.tampildata').load("tampil.php");
                                }
                        });
                });
                });
                        $.widget.bridge('uibutton', $.ui.button);</script>
        <?php } ?>
        <!-- Bootstrap 3.3.7 -->
        <script src="libs/bootstrap/js/bootstrap.min.js"></script>        
        <!-- Select2 -->
        <script src="libs/select2/js/select2.full.min.js"></script>
        <!-- InputMask -->
        <script src="libs/input-mask/jquery.inputmask.js"></script>
        <script src="libs/input-mask/jquery.inputmask.date.extensions.js"></script>
        <script src="libs/input-mask/jquery.inputmask.extensions.js"></script>
        <script>
                    $(function () {
                    //Datemask dd/mm/yyyy
                    $('#datemask').inputmask('dd/mm/yyyy', {'placeholder': 'dd/mm/yyyy'})
                            //Datemask2 mm/dd/yyyy
                            $('#datemask2').inputmask('mm/dd/yyyy', {'placeholder': 'mm/yyyy'})
                            //Money Euro
                            $('[data-mask]').inputmask()
                            $('[data-mask2]').inputmask()
                    })
        </script>
        <!-- daterangepicker -->
        <script src="libs/moment/min/moment.min.js"></script>
        <script src="libs/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script>
                    $(function () {
                    //Date range picker
                    $('#reservation').daterangepicker()
                            $('#reservation1').daterangepicker()
                            $('#reservation2').daterangepicker()
                            $('#reservation3').daterangepicker()
                            $('#reservation4').daterangepicker()
                            $('#reservation5').daterangepicker()
                            $('#reservation6').daterangepicker()
                            $('#reservation7').daterangepicker()
                            $('#reservation8').daterangepicker()
                            $('#reservation9').daterangepicker()
                            $('#reservation10').daterangepicker()
                            //Date range picker with time picker
                            $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'})
                            //Date range as a button
                            $('#daterange-btn').daterangepicker(
                    {
                    ranges: {
                    'Today': [moment(), moment()],
                            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                            'This Month': [moment().startOf('month'), moment().endOf('month')],
                            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    },
                            startDate: moment().subtract(29, 'days'),
                            endDate: moment()
                    },
                            function (start, end) {
                            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
                            }
                    )
                    })
        </script>
        <!-- datepicker -->
        <script src="libs/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script>
                    $(function () {
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
        <!-- clock picker-->
        <script type="text/javascript" src="libs/clockpicker/bootstrap-clockpicker.min.js"></script>
        <script>
                    $('.clockpicker').clockpicker()
                    .find('input').change(function () {
            console.log(this.value);
            });
                    var input = $('#jam-mulai-utama').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam-selesai-utama').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam1').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam2').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam-mulai-tambahan').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam-selesai-tambahan').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam-mulai-tambahan1').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });
                    var input = $('#jam-selesai-tambahan1').clockpicker({
            placement: 'top',
                    align: 'left',
                    autoclose: true,
                    donetext: 'Done',
                    //'default': 'now',

            });</script>        
        <!-- iCheck 1.0.1 -->
        <script src="libs/iCheck/icheck.min.js"></script>
        <script>
                    $(function () {

                    //Initialize Select2 Elements
                    $('.select2').select2()

                    })
        </script>
        <!-- FastClick             -->
        <script src="libs/fastclick/lib/fastclick.js"></script>
        <!-- DataTables -->
        <script src="libs/datatables.net-bs/js/jquery.dataTables.min.js"></script>
        <!--<script src="libs/datatables.net-bs/js/jquery.dataTables.js"></script>-->
        <script src="libs/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>

        <script type="text/javascript" src="libs/datatables.net-bs/JSZip-2.5.0/jszip.min.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/pdfmake-0.1.32/pdfmake.min.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/pdfmake-0.1.32/vfs_fonts.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/AutoFill-2.2.2/js/dataTables.autoFill.min.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/Buttons-1.5.1/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/Buttons-1.5.1/js/buttons.flash.min.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/Buttons-1.5.1/js/buttons.html5.min.js"></script>
        <script type="text/javascript" src="libs/datatables.net-bs/Buttons-1.5.1/js/buttons.print.min.js"></script>
        <!-- CK Editor -->
        <?php if ($ckeditor == 'yes') { ?>
            <script src="libs/ckeditor/ckeditor.js"></script>
            <script>
                        $(function () {
                        // Replace the <textarea id="editor1"> with a CKEditor
                        // instance, using default configuration.
                        CKEDITOR.replace('new')
                                //bootstrap WYSIHTML5 - text editor
                                $('.textarea').wysihtml5()
                        })
            </script>
        <?php } ?>
<!--        <script type="text/javascript" src="libs/datatables.net-bs/JSZip-2.5.0/jszip.min.js"></script>
<script type="text/javascript" src="libs/datatables.net-bs/pdfmake-0.1.32/pdfmake.min.js"></script>
<script type="text/javascript" src="libs/datatables.net-bs/pdfmake-0.1.32/vfs_fonts.js"></script>
<script type="text/javascript" src="libs/datatables.net-bs/AutoFill-2.2.2/js/dataTables.autoFill.min.js"></script>-->
        <script>
                    $(function () {
                    $('#example1').DataTable()
                            $('#example_normal').DataTable()
                            $('#example').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example2').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example3').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example4').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example5').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example6').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example7').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example8').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example9').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example10').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example11').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example12').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example13').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example14').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example15').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#example16').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                    $('#example17').DataTable({
                    'lengthMenu': [[50, 100, 150, - 1], [50, 100, 150, "All"]]

                    })
                            $('#kinerja').DataTable({
                    "aLengthMenu": [[50, 100, 150, - 1], [50, 100, 150, 'All']],
                            dom: 'lBfrtip',
                            buttons: [
                            {extend: 'pdfHtml5',
                                    orientation: 'landscape',
                                    pageSize: 'LEGAL',
                                    text : '<i class="fa fa-file-pdf-o"> Export To PDF</i>',
                                    titleAttr : 'PDF',
                            
                            }

                            ]
                    })

                    $('#jadwal').DataTable({
                        "aLengthMenu": [[50, 100, 150, - 1], [50, 100, 150, 'All']],
                            dom: 'lBfrtip',
                            buttons: [
                            {extend: 'collection',
                                    text: 'Export To',
                                    buttons: [
                                            'copy',
                                            'excel'
                                    ]
                            }
                            ]
                    })
                    
                    $('#absensi').DataTable({
                        "aLengthMenu": [[50, 100, 150, - 1], [50, 100, 150, 'All']],
                            dom: 'lBfrtip',
                            buttons: [
                            {extend: 'collection',
                                    text: 'Export To',
                                    buttons: [
                                            'copy',
                                            'excel'
                                    ]
                            }
                            ]
                    })

                            $('#all').DataTable({
                    'paging': true,
                            'lengthChange': false,
                            'searching': false,
                            'ordering': false,
                            'info': false,
                            'autoWidth': true,
                            'responsive': true
                    })
                            $('#laporan').DataTable({
                    "aLengthMenu": [[50, 100, 150, - 1], [50, 100, 150, 'All']],
                    //"scrollX": true,
            //"scrollY": "500px",
            //"scrollCollapse": true,
            //"autoWidth": true,
        //fixedColumns:   {
            //leftColumns: 1,
            //rightColumns: 1
        //},
                            dom: 'lBfrtip',
                            buttons: [
                            {extend: 'collection',
                                    text: 'Export To',
                                    buttons: [
                                            'copy',
                                            'excel'
                                    ]
                            }
                            ]
                    })
//                $('#absensi-bulan').DataTable({
//                    "aLengthMenu": [[50, 100, 150, -1], [50, 100, 150, 'All']],                  
//                    "ajax": "api/api-absensi?nip=<?php //echo $nip; ?>&unit=<?php //echo $id_unit; ?>",
//                    "columns": [
//                        {"data": "tanggal"},
//                        {"data": "in"},
//                        {"data": "out"},
//                        {"data": "telat"},
//                        {"data": "status"}
//                    ],
//                    dom: 'lBfrtip',
//                    buttons: [
//                        {extend: 'collection',
//                            text: 'Export To',
//                            buttons: [
//                                'copy',
//                                'excel'
//                            ]
//                        }
//                    ]
//
//
//                })
                    })
        </script>
        <!-- Bootstrap WYSIHT                ML5         -->
        <script src="libs/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
        <!-- AdminLTE App -->
        <script src="libs/dist/js/adminlte.min.js"></script>
        <script>
                    $('#alert-simpan').click(function () {
            alert('simpandata');
            })
        </script>
        <script>
                    $(document).ready(function () {
            // Sembunyikan alert validasi kosong
            $("#kosong").hide();
            });</script>
        <!-- Bootstrap slider -->
        <script src="libs/bootstrap-slider/bootstrap-slider.js"></script>
        <script>
                    $(function () {
                    /* BOOTSTRAP SLIDER */
                    $('.slider').slider()
                    })
                    //loader page
                    $(document).ready(function () {
            $(".preloader").fadeOut();
            })
        </script>
        <script>
                    $('#myModal').modal('show');</script>
        <?php if ($tampil_grafik == 'yes') { ?>
            <script src="libs/hightChart/highcharts.js" type="text/javascript"></script>
            <script src="libs/hightChart/exporting.js" type="text/javascript"></script>
            <script type="text/javascript">
                        var chart1; // globally available
                        $(document).ready(function () {
                chart1 = new Highcharts.Chart({
                chart: {
                renderTo: 'container',
                        type: 'column'
                },
                        title: {
                        text: 'Grafik Pencapaian Kinerja '
                        },
                        xAxis: {
                        categories: ['Bulan']
                        },
                        yAxis: {
                        title: {
                        text: 'Nilai'
                        }
                        },
                        series:
                        [
    <?php
    $sql_pencapaian = bukaquery("SELECT (nskp+nprilaku+(penyerapan*0.2)) as jumlah,tanggal_penilaian  FROM tm_penilaian inner join tm_penyerapan on tm_penilaian.id_penyerapan=tm_penyerapan.id_penyerapan where year(tm_penilaian.tanggal_penilaian)='$thn_now' and id_user='$id_user'");
    while ($get = fetch_array($sql_pencapaian)) {
        ?>
                            {
                            name: '<?php echo konversiBulanTahun($get['tanggal_penilaian']); ?>',
                                    data: [<?php echo $get['jumlah']; ?>]
                            },
    <?php } ?>
                        ]
                });
                });</script>
        <?php } ?>    
        <script src="libs/fullcalendar/dist/fullcalendar.min.js"></script>
        <?php if ($tampil_cuti == 'yes') { ?>
            <script>
                        $(function () {

                        /* initialize the external events
                         -----------------------------------------------------------------*/
                        function init_events(ele) {
                        ele.each(function () {

                        // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
                        // it doesn't need to have a start or end
                        var eventObject = {
                        title: $.trim($(this).text()) // use the element's text as the event title
                        }

                        // store the Event Object in the DOM element so we can get to it later
                        $(this).data('eventObject', eventObject)

                                // make the event draggable using jQuery UI
                                $(this).draggable({
                        zIndex: 1070,
                                revert: true, // will cause the event to go back to its
                                revertDuration: 0  //  original position after the drag
                        })

                        })
                        }

                        init_events($('#external-events div.external-event'))

                                /* initialize the calendar
                                 -----------------------------------------------------------------*/
                                //Date for the calendar events (dummy data)
                                var date = new Date()
                                var d = date.getDate(),
                                m = date.getMonth(),
                                y = date.getFullYear()
                                $('#calendar').fullCalendar({
                        header: {
                        left: 'prev,next today',
                                center: 'title',
    //                            right: 'month,agendaWeek,agendaDay'
                        },
                                buttonText: {
                                today: 'today',
                                        month: 'month',
                                        week: 'week',
                                        day: 'day'
                                },
                                //Random default events
                                events: [
    <?php
    while ($row = fetch_array($cuti)) {
        ?>
                                    {
                                    title: '<?php echo $row['nama_pegawai']; ?>',
                                            start: '<?php echo $row['tanggal']; ?>',
                                            backgroundColor: '#f56954', //red
                                            borderColor: '#f56954' //red
                                    },
    <?php } ?>
                                ],
                        })

                                /* ADDING EVENTS */
                                var currColor = '#3c8dbc' //Red by default
                                //Color chooser button
                                var colorChooser = $('#color-chooser-btn')
                                $('#color-chooser > li > a').click(function (e) {
                        e.preventDefault()
                                //Save color
                                currColor = $(this).css('color')
                                //Add color effect to button
                                $('#add-new-event').css({'background-color': currColor, 'border-color': currColor})
                        })

                        })
            </script>
        <?php } ?>
            
            <?php if ($tampil_agenda_rapat == 'yes') { ?>
            <script>
                        $(function () {

                        /* initialize the external events
                         -----------------------------------------------------------------*/
                        function init_events(ele) {
                        ele.each(function () {

                        // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
                        // it doesn't need to have a start or end
                        var eventObject = {
                        title: $.trim($(this).text()) // use the element's text as the event title
                        }

                        // store the Event Object in the DOM element so we can get to it later
                        $(this).data('eventObject', eventObject)

                                // make the event draggable using jQuery UI
                                $(this).draggable({
                        zIndex: 1070,
                                revert: true, // will cause the event to go back to its
                                revertDuration: 0  //  original position after the drag
                        })

                        })
                        }

                        init_events($('#external-events div.external-event'))

                                /* initialize the calendar
                                 -----------------------------------------------------------------*/
                                //Date for the calendar events (dummy data)
                                var date = new Date()
                                var d = date.getDate(),
                                m = date.getMonth(),
                                y = date.getFullYear()
                                $('#agenda').fullCalendar({
                        header: {
                        left: 'prev,next today',
                                center: 'title',
    //                            right: 'month,agendaWeek,agendaDay'
                        },
                                buttonText: {
                                today: 'today',
                                        month: 'month',
                                        week: 'week',
                                        day: 'day'
                                },
                                //Random default events
                                events: [
    <?php
function limit_words($string, $word_limit){
    $words = explode(" ",$string);
    return implode(" ",array_splice($words,0,$word_limit));
}
    $sql_agenda=  bukaquery("SELECT tanggal, kegiatan from tt_agenda");
    while ($ag = fetch_array($sql_agenda)) {
        ?>
                                    {
                                    title: '<?php echo $ag['kegiatan']; ?>',
                                            start: '<?php echo $ag['tanggal']; ?>',
                                            backgroundColor: 'bluelight', //red
                                            borderColor: '#f56954' //red
                                    },
    <?php } ?>
                                ],
                        })

                                /* ADDING EVENTS */
                                var currColor = '#3c8dbc' //Red by default
                                //Color chooser button
                                var colorChooser = $('#color-chooser-btn')
                                $('#color-chooser > li > a').click(function (e) {
                        e.preventDefault()
                                //Save color
                                currColor = $(this).css('color')
                                //Add color effect to button
                                $('#add-new-event').css({'background-color': currColor, 'border-color': currColor})
                        })

                        })
            </script>
        <?php } ?>
    </body>
</html>
