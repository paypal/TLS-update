<?php

$ch = curl_init(); 

curl_setopt($ch, CURLOPT_URL, "https://tlstest.paypal.com/"); 
curl_setopt($ch, CURLOPT_CAINFO, dirname(__FILE__) . '/cacert.pem');

var_dump(curl_exec($ch));

if ($err = curl_error($ch)) {
var_dump($err);
echo "DEBUG INFORMATION:\n###########";
echo "CURL VERSION";
echo json_encode(curl_version(), JSON_PRETTY_PRINT);
}