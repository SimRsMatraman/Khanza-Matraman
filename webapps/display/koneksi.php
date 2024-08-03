<?php
$koneksi = mysqli_connect 
			(
				"10.9.10.10:7777",
				"usercipayung",
				"c0b4d1b4c4",
				"mastercipayung"
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
