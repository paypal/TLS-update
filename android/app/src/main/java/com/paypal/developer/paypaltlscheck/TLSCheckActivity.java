package com.paypal.developer.paypaltlscheck;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;


public class TLSCheckActivity extends AppCompatActivity {
    private static final String TAG = TLSCheckActivity.class.getSimpleName();
    private static final String URL = "https://tlstest.paypal.com";
    private static final String EXPECTED_RESPONSE = "PayPal_Connection_OK";

    private TextView resultText1;
    private TextView resultText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlscheck);

        resultText1 = (TextView)findViewById(R.id.resultText1);
        resultText2 = (TextView)findViewById(R.id.resultText2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doCheck1();
        doCheck2();
    }

    public void doCheck1() {
        String statusText = "Checking " + URL;
        Log.i(TAG, statusText);
        resultText1.setText(statusText);

        new OkHttpTask().execute(URL);
    }

    public void doCheck2() {
        String statusText = "Checking " + URL;
        Log.i(TAG, statusText);
        resultText2.setText(statusText);

        new HttpsURLConnectionTask().execute(URL);
    }


    public void retry1(View sender) {
        doCheck1();
    }

    public void retry2(View sender) {
        doCheck2();
    }

    private class OkHttpTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";

            for (int i=0; i<2; i++ ) {
                // OK HTTP library is used by the SDK and is a more modern library.
                OkHttpClient client = new OkHttpClient().setConnectionSpecs(
                        Arrays.asList(ConnectionSpec.MODERN_TLS));

                try {
                    if(i%2 == 0) {
                        result += "result with TlsSocketFactory:";
                        client.setSslSocketFactory(new TlsSocketFactory());
                    } else {
                        result += "\nresult with default SocketFactory:";
                    }

                    Request request = new Request.Builder()
                            .url(urls[0])
                            .build();

                    Response response = client.newCall(request).execute();
                    result += response.body().string().trim();
                } catch (Exception e) {
                    result += "FAIL: " + e.getLocalizedMessage().trim();
                    Log.e(TAG, result, e);
                }
            }


            Log.i(TAG, "background result:" + result);
            return result;
        }

        protected void onPostExecute(String result) {
            setResult(resultText1, result);
        }
    }

    private class HttpsURLConnectionTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";

            // DIY HTTP. For debugging/curiosity satisfaction.
            for (int i=0; i<2; i++ ) {
                try {
                    HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urls[0]).openConnection();
                    if (i % 2 == 0) {
                        result += "result with TlsSocketFactory:";
                        urlConnection.setSSLSocketFactory(new TlsSocketFactory());
                    } else {
                        SSLContext sc = SSLContext.getInstance("TLS");
                        sc.init(null, null, new java.security.SecureRandom());
                        urlConnection.setSSLSocketFactory(sc.getSocketFactory());

                        result += "\nresult with default 'TLS' SocketFactory:";
                    }
                    result += getContentString(urlConnection);
                } catch (Exception e) {
                    result += "FAIL: " + e.getLocalizedMessage().trim();
                    Log.e(TAG, result, e);
                }
            }

            Log.i(TAG, "background result:" + result);
            return result;
        }

        @NonNull
        private String getContentString(HttpsURLConnection urlConnection) throws IOException {
            InputStreamReader in = new InputStreamReader((InputStream) urlConnection.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line;
            StringBuilder sb = new StringBuilder();
            do {
                line = buff.readLine();
                if (line != null) sb.append(line + "\n");
            } while (line != null);
            String result = sb.toString().trim();
            return result;
        }

        protected void onPostExecute(String result) {
            setResult(resultText2, result);
        }
    }

    private void setResult(TextView resultTextView, String result) {
        if (null == result) {
            Log.w(TAG, "result was null");
            resultTextView.setText("result was null");
        } else {
            Log.w(TAG, "result:" + result);
            resultTextView.setText(result);
        }
    }
}
