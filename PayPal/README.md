## PayPal TLSv1.2 supported SDKs

This page contains PayPal SDKs that support TLSv1.2. Visit [here](/Braintree/README.md) for a list of all Braintree SDKs.

## Java

For Java environments, please verify you have the latest release below, and additionally verify your Java setup using the steps outlined [here](https://github.com/paypal/TLS-update#java).

SDK | TLSv1.2 supported version
--- | -------
[REST Java-SDK](https://github.com/paypal/PayPal-Java-SDK) | [1.4.0](https://github.com/paypal/PayPal-Java-SDK/releases)
[sdk-core](https://github.com/paypal/sdk-core-java) | [1.7.0](https://github.com/paypal/sdk-core-java/releases/tag/v1.7.0)
[adaptivepayments](https://github.com/paypal/adaptivepayments-sdk-java) | [2.9.117](https://github.com/paypal/adaptivepayments-sdk-java/releases/tag/v2.9.117)
[adaptiveaccounts](https://github.com/paypal/adaptiveaccounts-sdk-java) | [2.6.106](https://github.com/paypal/adaptiveaccounts-sdk-java/releases/tag/2.6.106)
[invoice](https://github.com/paypal/invoice-sdk-java) | [2.7.117](https://github.com/paypal/invoice-sdk-java/releases/tag/v2.7.117)
[buttonmanager](https://github.com/paypal/buttonmanager-sdk-java) | [2.7.106](https://github.com/paypal/buttonmanager-sdk-java/releases/tag/2.7.106)
[permissions](https://github.com/paypal/permissions-sdk-java) | [2.6.109](https://github.com/paypal/permissions-sdk-java/releases/tag/v2.6.109)
[merchant (merchant 2.x)](https://github.com/paypal/merchant-sdk-java) | [2.14.117](https://github.com/paypal/merchant-sdk-java/releases/tag/v2.14.117)
[legacy (merchant 1.x)](https://github.com/paypal/PayPal-Legacy-Java-SDK/) | [1.1.0](https://github.com/paypal/PayPal-Legacy-Java-SDK/releases/tag/v1.1.0)

## .NET

For .NET environments, please verify you have the latest release below, and additionally verify your .NET setup using the steps outlined [here](https://github.com/paypal/TLS-update#net).

SDK | TLSv1.2 supported version
--- | -------
[REST NET-SDK](https://github.com/paypal/PayPal-NET-SDK) | [1.7.3](https://github.com/paypal/PayPal-NET-SDK/releases)
[sdk-core](https://github.com/paypal/sdk-core-dotnet) | [1.7.1](https://github.com/paypal/sdk-core-dotnet/releases)
[adaptivepayments](https://github.com/paypal/adaptivepayments-sdk-dotnet) | Requires [sdk-core 1.7.1 or later](https://github.com/paypal/sdk-core-dotnet/releases)
[adaptiveaccounts](https://github.com/paypal/adaptiveaccounts-sdk-dotnet) | Requires [sdk-core 1.7.1 or later](https://github.com/paypal/sdk-core-dotnet/releases)
[invoice](https://github.com/paypal/invoice-sdk-dotnet) | Requires [sdk-core 1.7.1 or later](https://github.com/paypal/sdk-core-dotnet/releases)
[buttonmanager](https://github.com/paypal/buttonmanager-sdk-dotnet) | Requires [sdk-core 1.7.1 or later](https://github.com/paypal/sdk-core-dotnet/releases)
[permissions](https://github.com/paypal/permissions-sdk-dotnet) | Requires [sdk-core 1.7.1 or later](https://github.com/paypal/sdk-core-dotnet/releases)
[merchant (merchant 2.x)](https://github.com/paypal/merchant-sdk-dotnet) | Requires [sdk-core 1.7.1 or later](https://github.com/paypal/sdk-core-dotnet/releases)
legacy (merchant 1.x) | Not supported - please [upgrade to merchant 2.x](https://github.com/paypal/merchant-sdk-dotnet/wiki/Upgrade-Process-from-Legacy-Merchant-SDK)

## PHP

If your environment's default settings do not support TLS 1.2, you may specify a specific version of TLS to use starting in [1.6.4](https://github.com/paypal/PayPal-PHP-SDK/releases).

```
# See CURL_SSLVERSION_TLSv1_2 on http://php.net/manual/en/function.curl-setopt.php
PayPalHttpConfig::$defaultCurlOptions[CURLOPT_SSLVERSION] = 6;
```

## Android

For Android SDK integrations, please verify you have at least one of the latest releases below for payments to continue operating on API 16-19 devices, as described [here](https://github.com/paypal/TLS-update#android).

SDK | TLSv1.2 supported version
--- | -------
[Android SDK](https://github.com/paypal/PayPal-Android-SDK) | [2.13.0](https://github.com/paypal/PayPal-Android-SDK/releases)
[MPL](https://developer.paypal.com/docs/classic/mobile/ht_mpl-itemPayment-Android/) | [1.5.6_49](https://github.com/paypal/sdk-packages/tree/gh-pages/MPL)

## Other languages

These languages do not require any SDK upgrades, but you still need to check your production system. Please see the following sections for language-specific support:

* [PHP](https://github.com/paypal/TLS-update#php)
* [Python](https://github.com/paypal/TLS-update#python)
* [Ruby](https://github.com/paypal/TLS-update#ruby)
* [Node.js](https://github.com/paypal/TLS-update#nodejs)
* [iOS](https://github.com/paypal/TLS-update#ios)
