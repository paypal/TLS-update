#!/usr/local/bin/perl
use strict;

use LWP::UserAgent;

my $ua = LWP::UserAgent->new;
my $response = $ua->get('https://tlstest.paypal.com/');

print "Perl: $]\nLWP: $LWP::UserAgent::VERSION\n";

if ($response->is_success) {
  print $response->decoded_content,"\n";
}
else {
  die $response->status_line;
}
