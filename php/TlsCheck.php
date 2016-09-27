<?php

$ch = curl_init(); 

curl_setopt($ch, CURLOPT_URL, "https://tlstest.paypal.com/"); 
curl_setopt($ch, CURLOPT_CAINFO, dirname(__FILE__) . '/cacert.pem');

curl_exec($ch);
echo "\n";

if ($err = curl_error($ch)) {
	var_dump($err);
	echo "DEBUG INFORMATION:\n###########\n";
	echo "CURL VERSION:\n";
	echo json_encode(curl_version(), JSON_PRETTY_PRINT);
}
