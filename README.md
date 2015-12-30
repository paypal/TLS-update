# TLSv1.2 Requirement

The [PCIv3.1 DSS (PDF)](https://www.pcisecuritystandards.org/documents/PCI_DSS_v3-1.pdf) mandates (p.46) that TLSv1.0 be retired from service by June 30, 2016. All organizations that handle credit card information are required to comply with this standard.

As part of this obligation, PayPal is updating its services to require TLSv1.2 for all HTTPS connections on June 17, 2016. After that date, all TLSv1.0 and TLSv1.1 API connections will be refused.

## What does this mean for PayPal merchants?

Merchants should verify that all of their systems are capable of using the TLSv1.2 protocol with a SHA-256 certificate. In most cases this means ensuring that you are up to date with security updates, including current versions of operating systems, encryption libraries, and runtime environments.

PayPal is making this upgrade alongside the rest of the payments industry. **All credit card processors must make these changes** by the deadline above, so you should expect to see similar announcements from other payment providers you might use. 

To help merchants get started, we've put together a few notes for common environments. These checks assume that you have installed all the libraries required by the PayPal REST SDKs. For these checks to be valid, they must be run on a production system or one that *exactly* matches the configuration you have in production.

### Java

* The TLS version can be set via [`SSLContext`](http://docs.oracle.com/javase/7/docs/api/javax/net/ssl/SSLContext.html).
* **The latest Java (currently 8) is preferred.** In Java 8, TLSv1.2 is used by default when a TLS version is not specified.

Java version | TLSv1.2 support
--- | ---
6 and earlier | No support. A runtime update is required. (Except possibly for IBM Java. See note below.)
7 | Available. TLSv1.2 must be explicitly enabled. A [PayPal SDK update](#paypal-java-sdk-support) or code change may be required.
8 | Default. TLSv1.2 is enabled by default. No code change is required, though it is always recommended to make sure you're using the latest [PayPal SDK](#paypal-java-sdk-support).

**NOTE for IBM Java:** [TLSv1.2 can be enabled via a system override flag in V6 service refresh 10 or higher.](http://www-01.ibm.com/support/knowledgecenter/SSYKE2_6.0.0/com.ibm.java.security.component.60.doc/security-component/jsse2Docs/overrideSSLprotocol.html)

To check Java, first verify that Java runtime 7 or higher is installed by running `java -version` from command line. If you have Java 6 or below, please upgrade it first. Then download [the provided test application](java). And in a shell on your **production system**, run:
`> java -jar TlsCheck.jar`

- On success, `PayPal_Connection_OK` is printed.
- On failure a networking exception will be thrown.

#### PayPal Java SDK support

SDK | TLSv1.2 support version
--- | -------
[REST Java-SDK](https://github.com/paypal/PayPal-Java-SDK) | [1.4.0](https://github.com/paypal/PayPal-Java-SDK/releases)
[sdk-core](https://github.com/paypal/sdk-core-java) | [1.7.0](https://github.com/paypal/sdk-core-java/releases/tag/v1.7.0)
[adaptivepayments](https://github.com/paypal/adaptivepayments-sdk-java) | [2.9.117](https://github.com/paypal/adaptivepayments-sdk-java/releases/tag/v2.9.117)
[adaptiveaccounts](https://github.com/paypal/adaptiveaccounts-sdk-java) | [2.6.106](https://github.com/paypal/adaptiveaccounts-sdk-java/releases/tag/2.6.106)
[invoice](https://github.com/paypal/invoice-sdk-java) | [2.7.117](https://github.com/paypal/invoice-sdk-java/releases/tag/v2.7.117)
[buttonmanager](https://github.com/paypal/buttonmanager-sdk-java) | [2.7.106](https://github.com/paypal/buttonmanager-sdk-java/releases/tag/2.7.106)
[permissions](https://github.com/paypal/permissions-sdk-java) | [2.6.109](https://github.com/paypal/permissions-sdk-java/releases/tag/v2.6.109)
[merchant (merchant 2.x)](https://github.com/paypal/merchant-sdk-java) | [2.14.117](https://github.com/paypal/merchant-sdk-java/releases/tag/v2.14.117)
[legacy (merchant 1.x)] | [A drop-in replacement for `paypal_base.jar`](https://github.com/paypal/TLS-update/blob/master/java/paypal_base.jar)

### .NET

The .NET 4.5 (or greater) runtime must be installed for TLSv1.2 to be enabled.
  * The TLS version can be set via [`ServicePointManager.SecurityProtocol`](https://msdn.microsoft.com/en-us/library/system.net.securityprotocoltype(v=vs.110).aspx).

To check .NET, first verify you have .NET framework 4.5 or higher by running [NetFrameworkVersions](net/NetFrameworkVersions) on the console of your production system. If you do not have .NET 4.5 or above, please upgrade it first. 

Then, run [TlsCheck](net/TlsCheck) in a shell on your **production system**:
`> TlsCheck.exe`

- On success, `PayPal_Connection_OK` is printed.

#### PayPal SDK support

SDK | TLSv1.2 support version
--- | -------
[REST NET-SDK](https://github.com/paypal/PayPal-NET-SDK) | [1.7.0](https://github.com/paypal/PayPal-NET-SDK/releases)
[sdk-core](https://github.com/paypal/sdk-core-dotnet) | Not yet available
[adaptivepayments](https://github.com/paypal/adaptivepayments-sdk-dotnet) | Not yet available
[adaptiveaccounts](https://github.com/paypal/adaptiveaccounts-sdk-dotnet) | Not yet available
[invoice](https://github.com/paypal/invoice-sdk-dotnet) | Not yet available
[buttonmanager](https://github.com/paypal/buttonmanager-sdk-dotnet) | Not yet available
[permissions](https://github.com/paypal/permissions-sdk-dotnet) | Not yet available
[merchant (merchant 2.x)](https://github.com/paypal/merchant-sdk-dotnet) | Not yet available
legacy (merchant 1.x) | Not supported

### PHP

PHP uses the system supplied CURL library. Version 7.34.0 or later is required.

To check PHP, in a shell on your **production system**, run:

`$ php -r '$ch = curl_init(); curl_setopt($ch, CURLOPT_URL, "https://tlstest.paypal.com/"); var_dump(curl_exec($ch));'`

- On success, `PayPal_Connection_OK` is printed.
- On failure, `bool(false)` will be printed.

You can get the specific error with `curl_error($ch)`:

`php -r '$ch = curl_init(); curl_setopt($ch, CURLOPT_URL, "https://tlstest.paypal.com/"); var_dump(curl_exec($ch)); var_dump(curl_error($ch));'`

### Python

Python uses the system supplied OpenSSL. TLSv1.2 requires OpenSSL 1.0.1c or higher.

To check Python, in a shell on your **production system**, run: 

`$ python  -c "import urllib2; urllib2.urlopen('https://tlstest.paypal.com/').read()"`

- On success, `PayPal_Connection_OK` is printed.
- On failure, an `URLError` will be raised: <br/>
`urllib2.URLError: <urlopen error EOF occurred in violation of protocol (_ssl.c:590)>`

### Ruby

Ruby 2.0.0 or above is required to use the TLSv1.2 capability of the system supplied OpenSSL. OpenSSL 1.0.1c is the first version that supplies TLSv1.2. That is, both `Ruby > 2.0.0` and `OpenSSL > 1.0.1c` are required. Then, run `bundle update` to update your dependencies.

*For legacy Ruby SDK (such that was packaged in `PP_Ruby_NVP_SDK.zip`), download [this SDK](https://github.com/paypal/TLS-update/blob/master/ruby/PP_Ruby_NVP_SDK.zip)*

To check Ruby, in a shell on your **production system**, run:

`$ ruby -r'net/HTTP' -e 'uri = URI("https://tlstest.paypal.com/"); puts Net::HTTP.get(uri)'`

- On success, `PayPal_Connection_OK` will be printed.
- On failure, a `OpenSSL::SSL::SSLError` or `EOFError` will be thrown.

### Node.js

Node uses OpenSSL. TLSv1.2 requires OpenSSL 1.0.1c or higher.

To check Node, in a shell on your **production system**, run:

`$ node -e "var https = require('https'); https.get('https://tlstest.paypal.com/', function(res){ console.log(res.statusCode) });"`

- On success, 200 is printed.
- On failure, a network error is printed.

## Native Mobile Apps

### Android

TLSv1.2 was made default for client connections in API 20 (Android 4.4W "KitKat - wearable extensions"). 

All Android app developers will want to make sure their code and PayPal SDK provide explicit support for TLSv1.2. Apps should be tested on Android 4.1-4.4 (API 16-19) devices to verify correct implementation. 

After the TLSv1.2 upgrade, native app support for user devices older than API 16 (Android 4.1 "Jelly Bean") will not be available. Fortunately, as of October 29, 2015, [Google reports less than 7.5% of devices accessing the Play store are API 15 or earlier](http://developer.android.com/about/dashboards/index.html#Platform).

Users of the PayPal SDK should simply update to the latest version. Outside the SDK, we've provided [an example Android app](android/) to illustrate how to support TLSv1.2. 

#### PayPal SDK support

SDK | TLSv1.2 support version
--- | -------
[Android SDK](https://github.com/paypal/PayPal-Android-SDK) | [2.12.1](https://github.com/paypal/PayPal-Android-SDK/releases)
MPL | Not yet available

### iOS

TLSv1.2 support was introduced in iOS 5. The [PayPal iOS SDK](https://github.com/paypal/PayPal-iOS-SDK) requires iOS 7 or higher. Apps built since 2013 will likely not need any updates.

### Windows

PayPal has discontinued SDK support for native Windows Phone apps. The related backend services will be shut down soon. Please update to a web browser integration. We recommend [Braintree v.zero for JavaScript](https://developers.braintreepayments.com/javascript+dotnet/guides/client-sdk). 
