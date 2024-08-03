<?php
namespace modules;
use modules\Routers;

class Services extends Routers
{

	public function getInbox()
	{
		
		$url=ADDRESS."/api/sms/sms-list";
		
		$XmlContent = '<?xml version="1.0" encoding="UTF-8"?><request>
			<PageIndex>1</PageIndex>
			<ReadCount>10</ReadCount>
			<BoxType>2</BoxType>
			<SortType>0</SortType>
			<Ascending>0</Ascending>
			<UnreadPreferred>0</UnreadPreferred>
			</request>
		';
		return $this->curl_post($url,$XmlContent);
	}
	public function sendSms($no_hp,$pesan)
	{
		
		$url=ADDRESS."/api/sms/send-sms";
		$dateTime = date("Y-m-d H:i:s");
		$XmlContent = '<?xml version="1.0" encoding="UTF-8"?><request>
				<Index>-1</Index>
				<Phones>
					<Phone>'.$no_hp.'</Phone>
				</Phones>
				<Sca/>
				<Content>'.$pesan.'</Content>
				<Length>'.strlen($pesan).'</Length>
				<Reserved>1</Reserved>
				<Date>'.date('Y-m-d H:i:s').'</Date>
				<SendType>0</SendType>
				</request>
			';
		return $this->curl_post($url,$XmlContent);
	}
	public function getStatus()
	{
		$url=ADDRESS."/api/monitoring/status";
		return $this->curl_get($url);
	}
	public function getProvider() 
	{
		$url=ADDRESS."/api/net/current-plmn";
		return $this->curl_get($url);	
	}
	public function getTrafficStats()
	{
		$url=ADDRESS."/api/monitoring/traffic-statistics";
		return $this->curl_get($url);	
	}
	public function getMonthStats()
	{
		$url=ADDRESS."/api/monitoring/month_statistics";
		return $this->curl_get($url);	
	}
	public function getCraddleStatus()
	{
		$url=ADDRESS."/api/cradle/status-info";
		return $this->curl_get($url);	
	}
	public function getSmsCount()
	{
		$url=ADDRESS."/api/sms/sms-count";
		return $this->curl_get($url);
	}
	public function getWlanClients()
	{
		$url=ADDRESS."/api/wlan/host-list";
		return $this->curl_get($url);
	}
	public function getNotifications()
	{
		$url=ADDRESS."/api/monitoring/check-notifications";
		return $this->curl_get($url);
	}
}