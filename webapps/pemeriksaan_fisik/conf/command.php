<?php
	function title(){
 		$judul ="Marking Gambar Pemeriksaan";
		$judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
		$judul = str_replace(array('.','-','/',',')," ",$judul);
		$judul = trim($judul);
		echo "$judul";	
 	}
 
	function cekSessiAdmin() {
            if (isset($_SESSION['ses_admin'])) {
                return true;
            } else {
                return false;
            }
        }


        function cekUser() {
            if (isset($_SESSION['ses_admin'])) {
                return true;
            } else {
                return false;
            }
        }
	
	function adminAktif() {
            if (cekSessiAdmin()) {
                return $_SESSION['ses_admin'];
            }
        }

        
    
	function isGuest() {
            if (cekSessiAdmin()) {
                return false;
            } else {
                return true;
            }
        }	
		
	
	function formProtek() {
                $aksi=isset($_GET['act'])?$_GET['act']:NULL;
		if (!cekUser()) {
                    $form = array ('HomeAdmin','PemeriksaanFisik','Odontogram');
                        foreach ($form as $page) {
                            if ($aksi==$page) {
                                echo "<META HTTP-EQUIV = 'Refresh' Content = '0; URL = ?act=Home'>";
                                exit;
                                break;
                            }
                        }
                }		
		
	}
	
	function actionPages() {
            $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:NULL;
		formProtek();
		switch ($aksi) {
                    case 'HomeAdmin'            : include_once('pages/kontak.php'); break;
                    case 'PemeriksaanFisik'    : include_once('pages/pemeriksaanfisik.php'); break;
                    case 'Odontogram'      : include_once('pages/odontogram.php'); break;
                    
                    default                     : include_once('pages/kontak.php');
			
		}
	}
	
	
	 
 
?>
