<script type="text/JavaScript">
	function AutoRefresh( t ) {
		setTimeout("location.reload(true);", t);
	}
</script>
<?php
require_once('conf/conf.php');
date_default_timezone_set("Asia/Jakarta");
$timestamp = date("Y-m-d H:i:s");
$query="SELECT
setting.kode_ppk,
aplicare_ketersediaan_kamar.kode_kelas_aplicare,
SUM(kapasitas) AS kapasitas,
sum(tersedia) as tersedia,
sum(tersediapria) as tersediapria,
sum(tersediawanita) as tersediawanita,
sum(tersediapriawanita) as tersediapriawanita
FROM
aplicare_ketersediaan_kamar ,
setting
GROUP BY
aplicare_ketersediaan_kamar.kode_kelas_aplicare";
$hasil=bukaquery($query);
while ($data1 = mysqli_fetch_array ($hasil)){
$kosong=$data1['tersedia'];
$isi=$data1['kapasitas']-$kosong;
$myvars="";
$item[] = array(
				'koders'=> $data1['kode_ppk'],	
				'koderuangan'=> $data1['kode_kelas_aplicare'],
				'jumlahisi'=> $isi,
				'jumlahkosong'=> $kosong,
				'jumlahtotal'=> $data1['kapasitas'],
				'updatetime'=> "$timestamp",
	);

}	
					
	$myvars = json_encode($item);
	
 ?>
 <input id = 'content' type='hidden' name='content' value='<?php echo $myvars; ?>'>
<div id="result"></div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $.ajax({
	        url: "http://eis.dinkes.jakarta.go.id/api/wsketersediaanbed.php",
	        type: "POST",
	        data: {
				content: $("#content").val()
			},
	        dataType: "JSON",
	        success: function (jsonStr) {
	            $("#result").text(JSON.stringify(jsonStr));
	        }
	    });
	});
</script>
