package com.paypal.developer.paypaltlscheck;

import android.graphics.Color;
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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;


public class TLSCheckActivity extends AppCompatActivity {
    private static final String TAG = TLSCheckActivity.class.getSimpleName();
    private static final String URL = "https://tlstest.paypal.com";
    private static final String EXPECTED_RESPONSE = "PayPal_Connection_OK";

    private TextView resultText1;
    private TextView resultText2;
    private TextView resultText3;
    private TextView resultText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlscheck);

        resultText1 = (TextView)findViewById(R.id.resultText1);
        resultText2 = (TextView)findViewById(R.id.resultText2);
        resultText3 = (TextView)findViewById(R.id.resultText3);
        resultText4 = (TextView)findViewById(R.id.resultText4);

        doCheck1();
        doCheck2();
        doCheck3();
        doCheck4();
    }

    public void doCheck1() {
        setCheckingText(resultText1);
        new OkHttpTaskWithTlsSocketFactory().execute(URL);
    }


    public void doCheck2() {
        setCheckingText(resultText2);
        new HttpsURLConnectionTaskWithTlsSocketFactory().execute(URL);
    }

    public void doCheck3() {
        setCheckingText(resultText3);
        new OkHttpTaskWithDefaultSocketFactory().execute(URL);
    }

    public void doCheck4() {
        setCheckingText(resultText4);
        new HttpsURLConnectionTaskWithDefaultSSLSocketFactory().execute(URL);
    }

    private void setCheckingText(TextView resultTextView) {
        String statusText = "Checking " + URL;
        Log.i(TAG, statusText);
        resultTextView.setText(statusText);
        resultTextView.setTextColor(Color.GRAY);
    }


    public void retry1(View sender) {
        doCheck1();
    }

    public void retry2(View sender) {
        doCheck2();
    }

    public void retry3(View sender) {
        doCheck3();
    }

    public void retry4(View sender) {
        doCheck4();
    }

    private class OkHttpTaskWithTlsSocketFactory extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                result += "result with TlsSocketFactory:";
                Response response = getOkhttpResponse(urls[0], true);
                result += response.body().string().trim();
            } catch (Exception e) {
                result += "FAIL: " + e.getLocalizedMessage().trim();
                Log.e(TAG, result, e);
            }
            Log.i(TAG, "background result:" + result);
            return result;
        }

        protected void onPostExecute(String result) {
            setResult(resultText1, result);
        }
    }

    private class OkHttpTaskWithDefaultSocketFactory extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                result += "result with default SSLSocketFactory:";
                Response response = getOkhttpResponse(urls[0], false);
                result += response.body().string().trim();
            } catch (Exception e) {
                result += "FAIL: " + e.getLocalizedMessage().trim();
                Log.e(TAG, result, e);
            }
            Log.i(TAG, "background result:" + result);
            return result;
        }

        protected void onPostExecute(String result) {
            setResult(resultText3, result);
        }
    }

    private Response getOkhttpResponse(String url, boolean useTlsSocketFactory) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        // OK HTTP library is used by the SDK and is a more modern library.
        OkHttpClient client = new OkHttpClient().setConnectionSpecs(
                Arrays.asList(ConnectionSpec.MODERN_TLS));

        if (useTlsSocketFactory) {
            client.setSslSocketFactory(new TlsSocketFactory());
        }

        return client.newCall(request).execute();
    }

    /**
     * DIY HTTP. For debugging/curiosity satisfaction.
     */
    private class HttpsURLConnectionTaskWithTlsSocketFactory extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urls[0]).openConnection();
                result += "result with TlsSocketFactory:";
                urlConnection.setSSLSocketFactory(new TlsSocketFactory());
                result += getContentString(urlConnection);
            } catch (Exception e) {
                result += "FAIL: " + e.getLocalizedMessage().trim();
                Log.e(TAG, result, e);
            }

            Log.i(TAG, "background result:" + result);
            return result;
        }

        protected void onPostExecute(String result) {
            setResult(resultText2, result);
        }
    }

    private class HttpsURLConnectionTaskWithDefaultSSLSocketFactory extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String result = "";

            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urls[0]).openConnection();
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, null, new java.security.SecureRandom());
                urlConnection.setSSLSocketFactory(sc.getSocketFactory());

                result += "result with default SSLSocketFactory:";
                result += getContentString(urlConnection);
            } catch (Exception e) {
                result += "FAIL: " + e.getLocalizedMessage().trim();
                Log.e(TAG, result, e);
            }

            Log.i(TAG, "background result:" + result);
            return result;
        }

        protected void onPostExecute(String result) {
            setResult(resultText4, result);
        }
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

    private void setResult(TextView resultTextView, String result) {
        if (null == result) {
            result = "FAIL: result was null";
        }

        Log.w(TAG, "result:" + result);
        resultTextView.setText(result);
        resultTextView.setTextColor(result.contains("FAIL") ? Color.RED : Color.GREEN);
    }
}
