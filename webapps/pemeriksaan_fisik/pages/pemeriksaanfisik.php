<?php if ($_GET['com'] == 'hapus') {
  error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
  error_reporting(1);

  $id = $_GET['id'];
  $norawat = $_GET['norawat'];
  $_query = "Delete FROM pemeriksaan_fisik WHERE id='$id'";
  bukaquery($_query);
?>

  <?php echo "<script>location.href='?act=PemeriksaanFisik&com=view&norawat=$norawat'</script>"; ?>

<?php } else if ($_GET['com'] == 'view') { ?>

  <div id="post">
    <div class="entry">
      <div id="kiri">



        <img class="text" id="exasmple" src="images/tubuh2.jpg" style=" border: 1px solid #000; display: none !important;
  visibility: hidden !important;">
        <div style="width: 100%; overflow: auto">
          <canvas id="canvas" width="580" height="400" style="  border: 0px solid #000;"> </canvas>

        </div>
      </div>
      <div id="kanan" style="display: none">

        <form id="frmDataPemeriksaan" action="#">
          <fieldset class="form-horizontal">
            <h4 class="modal-title">Keterangan Pemeriksaan</h4>
            <hr>
            <div class='control-group form-group'>

              <label class='col-lg-3 mb-3'>Bagian</label>
              <div class='col-lg-9 mb-9'>
                <select class="form-control" data-live-search="true" name='bagian' id="bagian">
                  <?php
                  $queryBagian = "SELECT * FROM  bagian_fisik ";
                  $_queryBagian = bukaquery($queryBagian);
                  $no = 1;
                  while ($dataBagian = mysqli_fetch_array($_queryBagian)) { ?>
                    <option value="<?php echo $dataBagian['nm_bagian'] ?>"><?php echo $dataBagian['nm_bagian'] ?></option>

                  <?php } ?>
                </select>

              </div>
            </div>
            <div class='control-group form-group'>

              <label class='col-lg-3 mb-3'>Keterangan Pemeriksaan</label>
              <div class='col-lg-9 mb-9'>
                <textarea class='form-control' id='ketPemeriksaan' name='ketPemeriksaan'></textarea>
              </div>
            </div>

            <input type='hidden' class='form-control' autocomplete='off' id='posty' name='posty'>
            <input type='hidden' class='form-control' autocomplete='off' id='postx' name='postx'>
            <input type='hidden' class='form-control' autocomplete='off' id='norawat' name='norawat' value="<?php echo $_GET['norawat'] ?>">
          </fieldset>

        </form>
        <button type="button" class="btn btn-success" id="simpanPemeriksaan">Simpan</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
      <div style="padding: 10px; float: left">
        <div>
          <button class="btn btn-info" onclick="window.location.reload();">REFRESH</button>
        </div>
        <br>
        <table id="listPemeriksaan" class="table table-bordered table-striped table-hover">
          <thead>
            <tr>
              <th style="width: 10%">No Rawat</th>
              <th style="width: 5%">Indeks</th>
              <th style="width: 10%">Bagian</th>
              <th>Keterangan</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <?php $norawat = $_GET['norawat'];
            $_sql = "SELECT * FROM  pemeriksaan_fisik Where no_rawat='$norawat'";
            $hasil = bukaquery($_sql);
            $no = 1;
            while ($data = mysqli_fetch_array($hasil)) { ?>

              <tr>
                <td><?php echo $data['no_rawat'] ?></td>
                <td><?php echo $data['indeks'] ?></td>
                <td><?php echo $data['bagian'] ?></td>
                <td><?php echo $data['keterangan'] ?></td>
                <td style="width: 10px"><a class="btn btn-danger" href="?act=PemeriksaanFisik&com=hapus&id=<?php echo $data['id'] ?>&norawat=<?php echo $data['no_rawat'] ?>"><i class="fa fa-trash"></i></a></td>
              </tr>

            <?php } ?>
          </tbody>
        </table>
      </div>
    </div>

  <?php
} ?>
  <script type="text/javascript">
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var canvasOffset = $("#canvas").offset();
    var offsetX = canvasOffset.left;
    var offsetY = canvasOffset.top;
    start_load();

    function start_load() {
      var img = document.getElementById("exasmple");
      ctx.drawImage(img, 0, 0);
      <?php $norawat = $_GET['norawat'];
      $_sql = "SELECT * FROM  pemeriksaan_fisik  where no_rawat='$norawat'";
      $hasil = bukaquery($_sql);
      while ($data = mysqli_fetch_array($hasil)) { ?>
        ctx.beginPath();
        ctx.strokeStyle = "#FF0000";
        ctx.arc(<?php echo $data['postx'] ?>, <?php echo $data['posty'] ?>, 25, 0, 2 * Math.PI);
        ctx.stroke();
        ctx.font = "30px Arial";
        ctx.fillText(<?php echo $data['indeks'] ?>, <?php echo $data['postx'] ?>, <?php echo $data['posty'] ?>);
      <?php } ?>

    }



    function handleMouseDown(e) {
      // alert("oke");
      var canvas = document.getElementById("canvas");
      var ctx = canvas.getContext("2d");
      var canvasOffset = $("#canvas").offset();
      var offsetX = canvasOffset.left;
      var offsetY = canvasOffset.top;
      mouseX = parseInt(e.clientX - offsetX);
      mouseY = parseInt(e.clientY - offsetY);
      document.getElementById("kanan").style.display = "block";
      // $('#ModalKeteranganPemeriksaan').modal("show");
      $("#postx").val(mouseX);
      $("#posty").val(mouseY);
    }
    $("#canvas").mousedown(function(e) {
      handleMouseDown(e);
    });

    function erraseCanvas() {
      document.getElementById("kanan").style.display = "none";
      // $('.modal.in').modal('hide');
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      location.reload();
    }
    $('button#simpanPemeriksaan').click(function() {
      $.ajax({
        url: "pages/proses.php?act=simpan",
        type: 'POST',
        dataType: 'JSON',
        data: $('form#frmDataPemeriksaan').serialize(),
        success: function(data) {
          var obj = data;
          if (obj.code == '200') {
            erraseCanvas();
          }
        }
      });

    });
  </script>