<?php
$koneksi = mysqli_connect 
			(
				"localhost",
				"root",
				"",
				"sik"
			);
if (mysqli_connect_errno())
	{
		echo "Koneksi Gagal"
		.mysqli_connect_error();
	}
	date_default_timezone_set('Asia/Jakarta');
$year       = date('Y');
$curr_month = date('m');
$month      = date('Y-m');
$date       = date('Y-m-d');
$time       = date('H:i:s');
$date_time  = date('Y-m-d H:i:s');
?>
