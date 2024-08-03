<?php
require_once 'autoload.php';
$service = new modules\Services;

$url = isset($_GET['url']) ? $_GET['url'] : '/';
$url = explode("/", $url);
$method = $_SERVER['REQUEST_METHOD'];
if($method=="GET")
{
	switch ($url[0]) 
	{ 
	    case "getsmscount":
	    header("Access-Control-Allow-Origin: *");
		header("Content-Type: application/json");
		header("Access-Control-Allow-Methods: POST, GET");
		header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
	    	$xml=\simplexml_load_string($service->getSmsCount());
	    	$response = array(
                        'metadata' => array(
                            'message' => 'Success',
                            'code' => 200
                        ),
                        'list'=> array(
                        	'belumDibaca' => $xml->LocalUnread,
                        	'inbox' => $xml->LocalInbox,
                        	'outbox' => $xml->LocalOutbox,
                        	'draft' => $xml->LocalDraft,
                        	'maxinbox' => $xml->LocalMax
                        )
                    );
             http_response_code(200);
	        
	         echo json_encode($response);
	    break;
	    default : echo "Service Tidak Tersedia";
	    break;
	}	
}else if($method=="POST")
{
	switch ($url[0]) 
	{
	    case "sendsms":
	    	$konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $no_hp=$decode['no_hp'];
            $pesan=$decode['pesan'];
			// echo $no_hp;
	        
	        $xml=\simplexml_load_string($service->sendSms($no_hp,$pesan));
	        if((string) $xml[0]=='OK')
	        {
	        	$response = array(
                        'metadata' => array(
                            'message' => 'Pesan Terkirim',
                            'code' => 200
                        )
                    );
             http_response_code(200);
	        }else
	        {
	        	$response = array(
                        'metadata' => array(
                            'message' => 'Gagal Mengirim',
                            'code' => 201
                        )
                    );
             http_response_code(201);
	        }
	         

	         echo json_encode($response);
	    break;
	    default : echo "Service Tidak Tersedia";
	    break;
	    
	}	
}
