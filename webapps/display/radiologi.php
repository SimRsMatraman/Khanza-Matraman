<?php
 //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Tirta dari RSUK Ciracas Jakarta Timur


 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
 include"koneksi.php";
?>
<!doctype html>
<html lang="en">
<head>

    <title>Layar Informasi</title>

    <!-- Meta START -->
    <link rel="icon" href="asset/img/rs.png" type="image/x-icon">

    <meta http-equiv="refresh" content="10" /> 
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <link type="text/css" rel="stylesheet" href="asset/css/materialize.min.css"  media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="asset/css/jquery-ui.css"  media="screen,projection"/>
    <link rel="stylesheet" href="asset/css/marquee.css" />
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
            filter:alpha(opacity=10);
        }
    </style>
    <style>
#customers {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
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
    table tr:nth-child(even) td{
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
text-align:center;
background: smoth Green;
color:#F00;
margin: 20px auto;
padding:5px;
border: 1px solid green;
width: 400px;
box-shadow: 5px 10px 5px #00c;
border-radius :15px 0;
}

#blink span {
font-size:2em;
font-weight:bold;
display:block;
font-family: arial;
}


</style>

    <!-- Global style END -->

</head>

<!-- Body START -->
<body class="bg">

<!-- Header START -->
<header>

<nav class="materialize-red lighten-1">
    <div class="nav-wrapper">
        <ul class="center hide-on-med-and-down" id="nv">
            <li>
                <a href="./" class="ams hide-on-med-and-down"><img src="asset/img/logors.png" alt="" width="91"> <b>INFORMASI ANTRIAN RADIOLOGI</b> </a>
            </li>
            <li class="right" style="margin-right: 10px;">
                <i class="material-icons">perm_contact_calendar</i>
                <a href="" class="white-text">
                    <?php
                      //menentukan hari
                      $a_hari = array(1=>"Senin","Selasa","Rabu","Kamis","Jumat", "Sabtu","Minggu");
                      $hari = $a_hari[date("N")];

                      //menentukan tanggal
                      $tanggal = date ("j");

                      //menentukan bulan
                      $a_bulan = array(1=>"Januari","Februari","Maret", "April", "Mei", "Juni","Juli","Agustus","September","Oktober", "November","Desember");
                      $bulan = $a_bulan[date("n")];

                      //menentukan tahun
                      $tahun = date("Y"); 

                      //dan untuk menampilkan nya dengan format contoh Jumat, 22 Februari 2013
                      echo $hari . ", " . $tanggal ." ". $bulan ." ". $tahun;

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
                <h5 class="center">Dalam Antrian</h5>
                <table id="customers">
                    <thead>
                      <tr>
                          <th><b>No Rawat</b></th>
                          <th><b>No Order</b></th>
						  <th><b>Penjamin</b></th>
                          
                      </tr>
                    </thead>
                    <tbody>
            <?php
            $data = mysqli_query ($koneksi, " select a.no_rawat,a.noorder, b.kd_pj,d.png_jawab from permintaan_radiologi a 
									inner join reg_periksa b inner join pasien c  inner join penjab d
									where a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis and b.kd_pj=d.kd_pj and a.tgl_permintaan ='$date' 
									order by a.jam_permintaan DESC	LIMIT 15");
            $cek = mysqli_num_rows($data);
            
            if($cek==0){ ?>
              <tr>
                <td colspan="3">Kosong</td>
              </tr>
            <?php }else{ 
              while ($row = mysqli_fetch_array ($data))
              {
                ?>
                <tr>
                
                <td>
                  <?php echo $row['0']; ?>
                </td>
                <td>
                  <?php echo $row['1']; ?>
                </td>
				<td>
                  <?php echo $row['3']; ?>
                </td>
                
              </tr>
              <?php
                }
              }
              ?>
              
                 
                    </tbody>
                </table>
            </div>
            <div class="col s4">
               
                <h5 class="center">Sedang di Proses</h5>
                <table id="customers">
                    <thead>
                      <tr>
                          <th><b>No Rawat</b></th>
                          <th><b>No Order</b></th>
						  <th><b>Penjamin</b></th>
                          
                      </tr>
                    </thead>
                    <tbody>
            
                  <?php
            
            
           $data = mysqli_query ($koneksi, "select a.no_rawat,a.noorder, b.kd_pj,d.png_jawab from permintaan_radiologi a 
									inner join reg_periksa b inner join pasien c  inner join penjab d
									where a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis and b.kd_pj=d.kd_pj and a.tgl_sampel ='$date'
									order by a.jam_permintaan DESC	LIMIT 15");
            $cek = mysqli_num_rows($data);
            
            if($cek==0){ ?>
              <tr>
                <td colspan="4">Kosong</td>
              </tr>
            <?php }else{ 
              while ($row = mysqli_fetch_array ($data))
              {
                ?>
                <tr>
                
                <td>
                  <?php echo $row['0']; ?>
                </td>
                <td>
                  <?php echo $row['1']; ?>
                </td>
				<td>
                  <?php echo $row['3']; ?>
                </td>
                
               
              </tr>
              <?php
                }
              }
              ?>
                    </tbody>
                </table>

                
            </div>
            <div class="col s4">
                <h5 class="center">Sudah Selesai</h5>
                
            <?php
            $data = mysqli_query ($koneksi, " select a.no_rawat,a.noorder, b.kd_pj,d.png_jawab from permintaan_radiologi a 
									inner join reg_periksa b inner join pasien c  inner join penjab d
									where a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis and b.kd_pj=d.kd_pj and a.tgl_hasil ='$date' 
									order by a.jam_permintaan desc	LIMIT 2");
            $result = mysqli_num_rows($data);
            
            if($cek==0){ ?>
             <tr>
                <td>
				<div id="blink">
				
				  <span>
					<?php echo "BELUM ADA" ?>
				  </span>
				</div>
                  
                </td>
              </tr>
            <?php }else{ 
              while ($row = mysqli_fetch_array ($data))
              {
                ?>
                <tr>
                <td>
				<div id="blink">
				NO. RAWAT
				<audio autoplay>
					 <source src="audio/antrian_rad.wav" type="audio/wav">
					 
				</audio>
				  <span>
					<?php echo $row['0']; ?>
				  </span>
				</div>
                  
                </td>
				<td>
				<div id="blink">
				NO. ORDER
				  <span>
					<?php echo $row['1']; ?>
				  </span>
				</div>
                  
                </td>
				<td>
				<div id="blink">
				PENJAMIN
				  <span>
					<?php echo $row['3']; ?>
				  </span>
				</div>
                  
                </td>
                
               
                
              </tr>
              <?php
                }
              }
              ?>
                    
                   


        </div>        
    </div>

</main>

<!-- Main END -->

<!-- Include Footer START -->

<!-- Footer START -->
<footer class="page-footer">

	<div class="footer-copyright materialize-red lighten-1">
      &nbsp Copyright ©2020 IT RSUD CIPAYUNG | www.rsudcipayung.jakarta.go.id - (021) 85506588 
    </div>
	 
			  
</footer>
<!-- Footer END -->

<!-- Javascript START -->
<script type="text/javascript" src="asset/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="asset/js/materialize.min.js"></script>
<script type="text/javascript" src="asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="asset/js/bootstrap.min.js"></script>
<script data-pace-options='{ "ajax": false }' src='asset/js/pace.min.js'></script>
<script type="text/javascript" src="asset/js/marquee.js"></script>
<script type="text/javascript">
   window.onload = function() { jam(); }

   function jam() {
    var e = document.getElementById('jam'),
    d = new Date(), h, m, s;
    h = d.getHours();
    m = set(d.getMinutes());
    s = set(d.getSeconds());

    e.innerHTML = h +':'+ m +':'+ s;

    setTimeout('jam()', 1000);
   }

   function set(e) {
    e = e < 10 ? '0'+ e : e;
    return e;
  }
  $(document).ready(function(){
blinkFont();
});

function blinkFont()
{
document.getElementById("blink").style.color="red"
document.getElementById("blink").style.background="white"
setTimeout("setblinkFont()",500)
}

function setblinkFont()
{
document.getElementById("blink").style.color="white"
document.getElementById("blink").style.background="green"
setTimeout("blinkFont()",500)
}
</script>


<script type="text/javascript" src="asset/js/jquery.min.js"></script> 


</body>
<!-- Body END -->

</html>
