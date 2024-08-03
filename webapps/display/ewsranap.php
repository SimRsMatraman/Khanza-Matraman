<?php



header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
header("Cache-Control: no-store, no-cache, must-revalidate");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache"); // HTTP/1.0
date_default_timezone_set("Asia/Bangkok");
$tanggal = mktime(date("m"), date("d"), date("Y"));
$jam = date("H:i");
include "koneksi.php";
?>

<!doctype html>
<html lang="en">

<head>

  <title>Alert EWS Rawat Inap</title>
  <link href="style/bootstrap.min.css" rel="stylesheet">
  <script src="style/angular.min.js"></script>
  <!-- Meta START -->
  <link rel="icon" href="asset/img/rs.png" type="image/x-icon">

  <meta charset="utf-8" />
  <meta http-equiv="refresh" content="35" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
  <link type="text/css" rel="stylesheet" href="asset/css/materialize.min.css" media="screen,projection" />
  <link type="text/css" rel="stylesheet" href="asset/css/jquery-ui.css" media="screen,projection" />
  <link rel="stylesheet" href="asset/css/marquee2.css" />
  <link rel="stylesheet" href="asset/css/example.css" />
  <link rel="stylesheet" href="asset/css/ok.css" />
  <link rel="stylesheet" href="asset/css/slide.css" />
  <style type="text/css">
    .bg::before {
      content: '';
      background-image: url('asset/img/background.jpg');
      background-size: cover;
      background-repeat: no-repeat;
      background-attachment: scroll;
      position: fixed;
      z-index: -1;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      opacity: 0.10;
      filter: alpha(opacity=10);
    }
  </style>
  <style>
    #customers {
      font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    #customers td,
    #customers th {
      border: 1px solid #ddd;
      padding: 8px;
    }

    #customers tr:nth-child(even) {
      background-color: #f2f2f2;
    }

    #customers tr:hover {
      background-color: #ddd;
    }

    #customers th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: left;
      background-color: #4CAF50;
      color: white;
    }

    #customerss {
      font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    #customerss td,
    #customerss th {
      border: 1px solid #ddd;
      padding: 8px;
    }

    #customerss tr:nth-child(even) {
      background-color: #f2f2f2;
    }

    #customerss tr:hover {
      background-color: #ddd;
    }

    #customerss th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: left;
      background-color: #FFD700;
      color: white;
    }
  </style>
  <style type="text/css">
    table {
      font-family: verdana, arial, sans-serif;
      font-size: 15px;
      color: #333333;
      border-width: 1px;
      border-color: #3A3A3A;
      border-collapse: collapse;
    }

    table th {
      border-width: 1px;
      padding: 8px;
      border-style: solid;
      border-color: #FFA6A6;
      background-color: #D56A6A;
      color: #ffffff;
    }

    table tr:hover td {
      cursor: pointer;
    }

    table tr:nth-child(even) td {
      background-color: #F7CFCF;
    }

    table td {
      border-width: 1px;
      padding: 8px;
      border-style: solid;
      border-color: #FFA6A6;
      background-color: #ffffff;
    }


    #blink {
      text-align: center;
      background: smoth Green;
      color: #F00;
      margin: 20px auto;
      padding: 5px;
      border: 1px solid green;
      width: 400px;
      box-shadow: 5px 10px 5px #00c;
      border-radius: 15px 0;
    }

    #blink span {
      font-size: 2em;
      font-weight: bold;
      display: block;
      font-family: arial;
    }
  </style>

  <!-- Global style END -->

</head>

<!-- Body START -->

<body class="bg">

  <!-- Header START -->
  <header>

    <nav class="pink lighten-1">
      <div class="nav-wrapper">
        <ul class="center hide-on-med-and-down" id="nv">
          <li>
            <a href="./" class="ams hide-on-med-and-down"><img src="asset/img/logors.png" alt="" width="50"> <b>ALERT EWS PASIEN RAWAT INAP</b> </a>
          </li>
          <li class="right" style="margin-right: 50px;">
            <i class="material-icons">perm_contact_calendar</i>
            <a href="" class="white-text">
              <?php
              //menentukan hari
              $a_hari = array(1 => "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu");
              $hari = $a_hari[date("N")];

              //menentukan tanggal
              $tanggal = date("j");

              //menentukan bulan
              $a_bulan = array(1 => "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember");
              $bulan = $a_bulan[date("n")];

              //menentukan tahun
              $tahun = date("Y");

              //dan untuk menampilkan nya dengan format contoh Jumat, 22 Februari 2013
              echo $hari . ", " . $tanggal . " " . $bulan . " " . $tahun;

              ?>
            </a>
            <i class="material-icons md-12">query_builder</i>
            <a href="" class="white-text" id="jam"></a>
          </li>
        </ul>
      </div>
    </nav>




  </header>
  <!-- Header END -->

  <!-- Main START -->
  <main>


    <!-- container END -->
    <div class="container-fluid">
      <div class="col s12 row">
        <div class="col s4">
          <h5 class="center"><b>PASIEN EWS RENDAH</b></h5>
          <table id="customers">
            <thead>
              <tr>
                <th><b>No Rawat</b></th>
                <th><b>Nama Pasien</b></th>
                <th><b>Nama Kamar</b></th>
                <th><b>Klasifikasi</b></th>
              </tr>
            </thead>
            <tbody>

              <?php
              $no = 1;
              $data = mysqli_query($koneksi, " SELECT
              ews_ranap.no_rawat ,
              GROUP_CONCAT(ews_ranap.tanggal ORDER BY ews_ranap.tanggal DESC,ews_ranap.jam DESC  LIMIT 1) as tanggal,
              GROUP_CONCAT(ews_ranap.jam ORDER BY ews_ranap.tanggal DESC,ews_ranap.jam DESC  LIMIT 1) as jam,
              GROUP_CONCAT(ews_ranap.klasifikasi ORDER BY ews_ranap.tanggal DEsc,ews_ranap.jam DESC LIMIT 1) as klasifikasi,
              pasien.no_rkm_medis,
              pasien.nm_pasien,
              kamar_inap.kd_kamar,
              bangsal.nm_bangsal
              FROM
              ews_ranap
              INNER JOIN kamar_inap ON kamar_inap.no_rawat=ews_ranap.no_rawat
              INNER JOIN reg_periksa ON ews_ranap.no_rawat = reg_periksa.no_rawat
              INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis
              INNER JOIN kamar ON kamar_inap.kd_kamar = kamar.kd_kamar
              INNER JOIN bangsal ON kamar.kd_bangsal = bangsal.kd_bangsal
              WHERE kamar_inap.stts_pulang='-'
              GROUP BY
              ews_ranap.no_rawat");
              // $cek = mysqli_num_rows($data);
              // $cek1 = mysqli_fetch_array($data);
              while ($row = mysqli_fetch_array($data)) {
                if ($row['klasifikasi'] == 'Sangat Rendah' && $row['klasifikasi'] == 'Sedang' && $row['klasifikasi'] == 'Tinggi') {
              ?>
                  <tr>
                    <td colspan="3">--</td>
                  </tr>
                <?php } else if ($row['klasifikasi'] == 'Rendah') {
                ?>
                  <tr>
                    <td>
                      <?php echo $row['no_rawat']; ?>
                    </td>
                    <td>
                      <?php echo $row['nm_pasien']; ?>
                    </td>
                    <td>
                      <?php echo "(" . $row['kd_kamar'] . ")" . " " . $row['nm_bangsal']; ?>
                    </td>
                    <td>
                      <?php echo $row['klasifikasi']; ?>
                    </td>
                  </tr>

                  </tr>
              <?php
                }
              }
              ?>
            </tbody>
          </table>
        </div>
        <div class="col s4">
          <h5 class="center"><b>PASIEN EWS SEDANG</b></h5>
          <table id="customerss">
            <thead>
              <tr>
                <th><b>No Rawat</b></th>
                <th><b>Nama Pasien</b></th>
                <th><b>Nama Kamar</b></th>
                <th><b>Klasifikasi</b></th>
              </tr>
            </thead>
            <tbody>

              <?php
              $no = 1;
              $data = mysqli_query($koneksi, " SELECT
              ews_ranap.no_rawat ,
              GROUP_CONCAT(ews_ranap.tanggal ORDER BY ews_ranap.tanggal DESC,ews_ranap.jam DESC  LIMIT 1) as tanggal,
              GROUP_CONCAT(ews_ranap.jam ORDER BY ews_ranap.tanggal DESC,ews_ranap.jam DESC  LIMIT 1) as jam,
              GROUP_CONCAT(ews_ranap.klasifikasi ORDER BY ews_ranap.tanggal DEsc,ews_ranap.jam DESC LIMIT 1) as klasifikasi,
              pasien.no_rkm_medis,
              pasien.nm_pasien,
              kamar_inap.kd_kamar,
              bangsal.nm_bangsal
              FROM
              ews_ranap
              INNER JOIN kamar_inap ON kamar_inap.no_rawat=ews_ranap.no_rawat
              INNER JOIN reg_periksa ON ews_ranap.no_rawat = reg_periksa.no_rawat
              INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis
              INNER JOIN kamar ON kamar_inap.kd_kamar = kamar.kd_kamar
              INNER JOIN bangsal ON kamar.kd_bangsal = bangsal.kd_bangsal
              WHERE kamar_inap.stts_pulang='-'
              GROUP BY
              ews_ranap.no_rawat");
              // $cek = mysqli_num_rows($data);
              // $cek1 = mysqli_fetch_array($data);
              while ($row = mysqli_fetch_array($data)) {
                if ($row['klasifikasi'] == 'Sangat Rendah' && $row['klasifikasi'] == 'Rendah' && $row['klasifikasi'] == 'Tinggi') {
              ?>
                  <tr>
                    <td colspan="3">--</td>
                  </tr>
                <?php } else if ($row['klasifikasi'] == 'Sedang') {
                ?>
                  <tr>
                    <td>
                      <?php echo $row['no_rawat']; ?>
                    </td>
                    <td>
                      <?php echo $row['nm_pasien']; ?>
                    </td>
                    <td>
                      <?php echo "(" . $row['kd_kamar'] . ")" . " " . $row['nm_bangsal']; ?>
                    </td>
                    <td>
                      <?php echo $row['klasifikasi']; ?>
                    </td>
                  </tr>

                  </tr>
              <?php
                }
              }
              ?>
            </tbody>
          </table>
        </div>


        <div class="col s4">

          <h5 class="center"><b>PASIEN EWS TINGGI</b></h5>


          <?php
          $data = mysqli_query($koneksi, " SELECT
					ews_ranap.no_rawat ,
					GROUP_CONCAT(ews_ranap.tanggal ORDER BY ews_ranap.tanggal DESC,ews_ranap.jam DESC  LIMIT 1) as tanggal,
					GROUP_CONCAT(ews_ranap.jam ORDER BY ews_ranap.tanggal DESC,ews_ranap.jam DESC  LIMIT 1) as jam,
					GROUP_CONCAT(ews_ranap.klasifikasi ORDER BY ews_ranap.tanggal DEsc,ews_ranap.jam DESC LIMIT 1) as klasifikasi,
          pasien.no_rkm_medis,
          pasien.nm_pasien,
          kamar_inap.kd_kamar,
          bangsal.nm_bangsal
          FROM
          ews_ranap
          INNER JOIN kamar_inap ON kamar_inap.no_rawat=ews_ranap.no_rawat
          INNER JOIN reg_periksa ON ews_ranap.no_rawat = reg_periksa.no_rawat
          INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis
          INNER JOIN kamar ON kamar_inap.kd_kamar = kamar.kd_kamar
          INNER JOIN bangsal ON kamar.kd_bangsal = bangsal.kd_bangsal
          WHERE kamar_inap.stts_pulang='-'
          GROUP BY
          ews_ranap.no_rawat");
          // $cek = mysqli_num_rows($data);
          // $cek1 = mysqli_fetch_array($data);
          $x = 0;
          while ($row = mysqli_fetch_array($data)) {
            if ($row['klasifikasi'] == 'Tinggi') {
              $x = $x + 1;
          ?>
              <tr bgcolor='#EE6868'>
                <td>
                  <div id="blink">
                    PASIEN
                    <audio autoplay>
                      <source src="audio/ews.wav" type="audio/wav">
                    </audio>
                    <span>
                      <?php echo $row['no_rawat'] . " <br> " . $row['nm_pasien'] . " <br> " . "(" . $row['kd_kamar'] . ")" . " " . $row['nm_bangsal']; ?>
                    </span>
                  </div>
                </td>
                <!-- <td>
                    <div id="blink">NAMA PASIEN
                      <span>
                        <?php echo $row['nm_pasien']; ?>
                      </span>
                    </div>
                  </td>
                  <td>
                    <div id="blink">NAMA KAMAR
                      <span>
                        <?php echo "(" . $row['kd_kamar'] . ")" . " " . $row['nm_bangsal']; ?>
                      </span>
                    </div>
                  </td> -->
              </tr>
            <?php
            } else { ?>


          <?php
            }
          }

          ?>
          <?php if ($x == 0) { ?>
            <tr>
              <td>
                <div id="blink">
                  <span>
                    <?php echo "BELUM ADA" ?>
                  </span>
                </div>
              </td>
            </tr>
          <?php } ?>
        </div>
      </div>
  </main>

  <!-- Main END -->

  <!-- Include Footer START -->

  <!-- Footer START -->
  <!--<marquee class="marquee" scrollamount="4">
                  Hubungi Kami di | Customer Care - (021) 000 - 0000 | IGD - (021) 000 - 0001 | Instagram / Facebook  | "Aplikasi ini 100 % Gratis tidak dipungut Biaya dalam Penggunaanya"
            </marquee>-->
  <footer class="page-footer">
    <div class="footer-copyright pink lighten-1">
      &nbsp Copyright ©2020 IT RSUD CIPAYUNG | www.rsudcipayung.jakarta.go.id - (021) 85506588
    </div>


  </footer>
  <!-- Footer END -->

  <!-- Javascript START -->
  <!-- Footer END -->

  <!-- Javascript START -->
  <script type="text/javascript" src="asset/js/jquery-2.1.1.min.js"></script>
  <script type="text/javascript" src="asset/js/materialize.min.js"></script>
  <script type="text/javascript" src="asset/js/jquery-ui.min.js"></script>
  <script type="text/javascript" src="asset/js/bootstrap.min.js"></script>
  <script data-pace-options='{ "ajax": false }' src='asset/js/pace.min.js'></script>
  <script type="text/javascript" src="asset/js/marquee.js"></script>
  <script type="text/javascript">
    window.onload = function() {
      jam();
    }

    function jam() {
      var e = document.getElementById('jam'),
        d = new Date(),
        h, m, s;
      h = d.getHours();
      m = set(d.getMinutes());
      s = set(d.getSeconds());

      e.innerHTML = h + ':' + m + ':' + s;

      setTimeout('jam()', 100);
    }

    function set(e) {
      e = e < 10 ? '0' + e : e;
      return e;
    }


    $(document).ready(function() {
      blinkFont();
    });

    function blinkFont() {
      document.getElementById("blink").style.color = "red"
      document.getElementById("blink").style.background = "white"
      setTimeout("setblinkFont()", 500)
    }

    function setblinkFont() {
      document.getElementById("blink").style.color = "white"
      document.getElementById("blink").style.background = "red"
      setTimeout("blinkFont()", 500)
    }
  </script>

</body>
<!-- Body END -->


</html>
