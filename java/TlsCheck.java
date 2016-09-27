import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class TlsCheck {

	/**
	 * Tests whether this client can make an HTTP connection with TLS 1.2.
	 *
	 * @return true if connection is successful. false otherwise.
	 */
	public static boolean isSuccessfulTLS12Connection() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, null, null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			URL url = new URL("https://tlstest.paypal.com");
			HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();

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

	public static void main(String[] args) {
		try {
			SSLParameters sslParams = SSLContext.getDefault().getSupportedSSLParameters();
			String[] protocols = sslParams.getProtocols();
			System.out.println("Supported protocol versions: " + Arrays.asList(protocols));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (isSuccessfulTLS12Connection()) {
			System.out.println("Successfully connected to TLS 1.2 endpoint.");
		} else {
			System.out.println("Failed to connect to TLS 1.2 endpoint.");
		}
	}
}
