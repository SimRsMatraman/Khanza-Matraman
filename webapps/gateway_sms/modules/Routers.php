<?php
namespace modules;

class Routers
{
	public function auth()
	{
		$curl = curl_init(ADDRESS."/api/webserver/SesTokInfo");
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		$content = curl_exec($curl);  
		$xml =  \simplexml_load_string($content);
		$sess_id = $xml->SesInfo;
		$tokInfo= $xml->TokInfo;
		curl_close($curl);

		$curl2 = curl_init(ADDRESS."/api/user/login");
		$headers = array(
		'Cookie:'. $sess_id,
		"X-Requested-With: XMLHttpRequest",
		'__RequestVerificationToken:'. $tokInfo,
		'Content-Type: text/xml',
		);

		$loginXml = '<?xml version="1.0" encoding="UTF-8"?><request>
			<Username>'.USERNAME.'</Username>
			<password_type>4</password_type>
			<Password>'.base64_encode(hash('sha256', USERNAME.base64_encode(hash('sha256',PASSWORD, false)).$tokInfo, false)).'</Password>
			</request>
			';
		curl_setopt($curl2, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($curl2, CURLOPT_POST, true);
		curl_setopt($curl2, CURLOPT_POSTFIELDS,$loginXml);
		curl_setopt($curl2, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($curl2, CURLOPT_HEADERFUNCTION, array($this, "curlResponseHeaderCallback"));
		$content = curl_exec($curl2);
	}

	function curlResponseHeaderCallback($curl2, $header_line) 
	{
	    global $cookies,$requestTokenOne,$requestTokenTwo,$requestToken,$manualCookieData;
	    if(strpos($header_line, '__RequestVerificationTokenOne') === 0)
		    {
		    	$token = trim(substr($header_line, strlen('__RequestVerificationTokenOne:')));
		    	$this->requestTokenOne = $token;
		    }
		    elseif(strpos($header_line, '__RequestVerificationTokenTwo') === 0)
		    {
		    	$token = trim(substr($header_line, strlen('__RequestVerificationTokenTwo:')));
		    	$this->requestTokenTwo = $token;
		    }
		    elseif(strpos($header_line, '__RequestVerificationToken') === 0)
		    {
		    	$token = trim(substr($header_line, strlen('__RequestVerificationToken:')));
		    	$this->requestToken = $token;
		    }
		    elseif(strpos($header_line, 'Set-Cookie:') === 0)
		    {
		    	$cookie = trim(substr($header_line, strlen('Set-Cookie:')));
		    	$this->manualCookieData = $cookie;
		    }
	    return strlen($header_line); // Needed by curl
	}
	function curl_get($url="")
	{	
		$curl = curl_init(ADDRESS."/api/webserver/SesTokInfo");
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		$content = curl_exec($curl);  
		$xml =  \simplexml_load_string($content);
		$sess_id = $xml->SesInfo;
		$tokInfo= $xml->TokInfo;
		curl_close($curl);
		$headers = array(
			'User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.12) Gecko/20101026 Firefox/3.6.12',
			'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8;charset=UTF-8',
			'Accept-Language: da-DK,da;q=0.8,en-US;q=0.6,en;q=0.4',
			'Accept-Charset: utf-8;q=0.7,*;q=0.7',
			'Keep-Alive: 115',
			'Connection: keep-alive',
			'Cookie: '.$sess_id,
			'__RequestVerificationToken: '.$tokInfo
		);
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		$content = curl_exec($ch);
		return $content;
	}
	function curl_post($url="",$XmlCode="")
	{	
		$this->auth();
		$headers = array(
			'User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.12) Gecko/20101026 Firefox/3.6.12',
			'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8;charset=UTF-8',
			'Accept-Language: da-DK,da;q=0.8,en-US;q=0.6,en;q=0.4',
			'Accept-Charset: utf-8;q=0.7,*;q=0.7',
			'Keep-Alive: 115',
			'Connection: keep-alive',
			'Cookie: '.$this->manualCookieData,
			'__RequestVerificationToken: '.$this->requestToken
		);
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_POSTFIELDS,$XmlCode);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		$content = curl_exec($ch);
		return $content;
	}

}