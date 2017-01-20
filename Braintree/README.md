## Braintree TLSv1.2 supported SDKs

This page contains Braintree SDKs that supports TLSv1.2. Visit [here](/PayPal/README.md) for a list of all PayPal SDKs.

## Java

For Java environments, please verify you have the latest release below, and additionally verify your Java setup using the steps outlined [here](https://github.com/paypal/TLS-update#java).

SDK | TLSv1.2 supported version
--- | -------
[braintree_java](https://github.com/braintree/braintree_java/) | [2.67.0](https://github.com/braintree/braintree_java/releases/)

## .NET

For .NET environments, please verify you have the latest release below, and additionally verify your .NET setup using the steps outlined [here](https://github.com/paypal/TLS-update#net).

SDK | TLSv1.2 supported version
--- | -------
[braintree_dotnet](https://github.com/braintree/braintree_dotnet) | [3.1.0](https://github.com/braintree/braintree_dotnet/releases/)

## PHP

If your environment's default settings do not support TLS 1.2, you may specify a specific version of TLS to use starting in [3.21.0](https://github.com/braintree/braintree_php/releases/).

```
Braintree\Configuration::sslVersion(6);
```

## Ruby

If your environment's default settings do not support TLS 1.2, you may specify a specific version of TLS to use starting in [2.71.0](https://github.com/braintree/braintree_ruby/releases/).

```
Braintree::Configuration.ssl_version = :TLSv1_2
```

## Android

For Android SDK integrations, please verify you have at least one of the latest releases below for payments to continue operating on API 16-19 devices, as described [here](https://github.com/paypal/TLS-update#android).

SDK | TLS v1.2 supported version
--- | --------
[Android 2.x SDK](https://github.com/braintree/braintree_android/) | [2.1.0](https://github.com/braintree/braintree_android/releases/)
[Android 1.x SDK](https://github.com/braintree/braintree_android/) | [1.7.6](https://github.com/braintree/braintree_android/releases/)

## Other languages

These languages do not require any SDK upgrades, but you still need to check your production system. Please see the following sections for language-specific support:

* [PHP](https://github.com/paypal/TLS-update#php)
* [Python](https://github.com/paypal/TLS-update#python)
* [Ruby](https://github.com/paypal/TLS-update#ruby)
* [Node.js](https://github.com/paypal/TLS-update#nodejs)
* [iOS](https://github.com/paypal/TLS-update#ios)
