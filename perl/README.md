# Perl

To ensure that the Perl PayflowPro module will work with TLSv1.2, we
need to verify that the underlying LWP::UserAgent module will support
TLSv1.2. It requires OpenSSL 1.0.1c or later.

The simplest test is to run this command line on your *production*
server:

```
lwp-request https://tlstest.paypal.com/
```

It should output `PayPal_Connection_OK` on success, or an error
message on failure.

If your system does not have lwp-request command line program
installed as part of the LWP package, you can run the `pfp-test.perl`
program. It will output `PayPal_Connection_OK` on success, or an error
message on failure.

Sample output:

```
Perl: 5.024001
LWP: 6.22
PayPal_Connection_OK
```

You don't need to run both tests as they are equivalent.
