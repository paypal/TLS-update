import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;


/**
 * http://stackoverflow.com/a/11024200
 *
 */
class Version implements Comparable<Version> {

    private String version;
    private String protocol;

    public final String get() {
        return this.version;
    }
    
    public final String getProtocol() {
      return this.protocol;
    }

    public Version(String version) {
        if(version == null)
            throw new IllegalArgumentException("Version can not be null");
        if(!version.matches(".*v[0-9].*"))
            throw new IllegalArgumentException("Invalid version format");
        String[] tokens= version.split("v");
        this.version = tokens[1];
        this.protocol = tokens[0];
    }

    public int compareTo(Version that) {
        if(that == null)
            return 1;
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for(int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ?
                Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ?
                Integer.parseInt(thatParts[i]) : 0;
            if(thisPart < thatPart)
                return -1;
            if(thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override 
    public boolean equals(Object that) {
        if(this == that)
            return true;
        if(that == null)
            return false;
        if(this.getClass() != that.getClass())
            return false;
        return this.compareTo((Version) that) == 0;
    }
    
    public String toString() {
      return this.protocol + "v" + this.version;
    }

}

public class TlsCheck {
  
  public static final String ENDPOINT = "tlstest.paypal.com";
  public static final int PORT = 443;
  
  /**
   * Get a <code>List</code> of available <code>Version</code> objects. The list will be sorted in ascending order, so currently "TLSv1.2" will be the last element. 
   * 
   * @return a sorted list of supported <code>Version</code>s
   */
  private static List<Version> getVersions() {
    SSLParameters sslParams;
    List<Version> versions = new ArrayList<Version>();
    try {
      sslParams = SSLContext.getDefault().getSupportedSSLParameters();
      String[] protocols = sslParams.getProtocols();
      for (int i = 0; i < protocols.length; ++i) {
        if (protocols[i].startsWith("TLS"))
          versions.add(new Version(protocols[i]));
      }
      
      Collections.sort(versions);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      System.err.println("this client does not support TLS 1.2");
    }
    
    return versions;
  }
  
  /**
   * Tests whether this client can make an HTTP connection with TLS 1.2.
   * 
   * @return  true if connection is successful. false otherwise.
   */
  public static boolean testTls12Connection() {
    String protocol = "N/A";
    try {
      SSLContext sslContext = SSLContext.getInstance(getLatestProtocol().toString());
      protocol = sslContext.getProtocol();
      sslContext.init(null, null, null);
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
      
      URL url = new URL("https://" + ENDPOINT);
      HttpsURLConnection httpsConnection=(HttpsURLConnection)url.openConnection();
      
      httpsConnection.connect();
      BufferedReader reader = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
      StringBuilder body = new StringBuilder();
      while (reader.ready()) {
        body.append(reader.readLine());
      }
      httpsConnection.disconnect();
      if (body.toString().equals("PayPal_Connection_OK")) {
        return true;
      }
        
    } catch (NoSuchAlgorithmException e) {
    } catch (UnknownHostException e) {
    } catch (IOException e) {
    } catch (KeyManagementException e) {
    }
    return false;
  }
  
  /**
   * Returns the latest supported protocol.
   * @return  the latest supported protocol
   */
  public static Version getLatestProtocol() {
    List<Version> versions = getVersions();
    Version latest = versions.get(versions.size() - 1);
    return latest;
  }
  
  /**
   * Checks whether this client supports TLS 1.2.
   * @return  true if supported, false if not.
   */
  public static boolean supportTls12() {
    Version latest = getLatestProtocol();
    
    if (latest.getProtocol().equals("TLS") && latest.compareTo(new Version("TLSv1.2")) >= 0) {
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    if (supportTls12())
      System.out.println("this client supports TLS 1.2");
    else
      System.out.println("this client does not support TLS 1.2");
    
    if (testTls12Connection()) {
      System.out.println("Successfully connected to TLS 1.2 endpoint");
    } else {
      System.out.println("Failed to connect to TLS 1.2 endpoint");
    }
  }

}
