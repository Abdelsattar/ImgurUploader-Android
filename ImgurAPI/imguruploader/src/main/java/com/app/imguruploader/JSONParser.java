package com.app.imguruploader;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class JSONParser {

    private String charset = "UTF-8";
    private HttpURLConnection conn;
    private StringBuilder result = new StringBuilder();
    private URL urlObj;
    private JSONObject jObj = null;

    JSONObject makeObjectHttptRequest(String URL, String IMGR_CLIENT_ID,
                                      String IMGR_CLIENT_SECRET, byte[] image) {
        try {
            urlObj = new URL(URL);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Authorization", "Client-ID " + IMGR_CLIENT_ID);

            Log.d("URI 0 ", "");
            conn.connect();
            Log.d("URI 2 ", "");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("image", Base64.encodeToString(image, 0))
                    .appendQueryParameter("key", IMGR_CLIENT_SECRET);

            String query = builder.build().getEncodedQuery();
            Log.d("URI", query);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }


    JSONObject makeHttptRequest(String URL, String IMGR_CLIENT_ID,
                                String IMGR_CLIENT_SECRET, byte[] image) {


        Log.d("hopa : ", "hh 1");
        URL url = null;
        try {
            url = new URL(URL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Client-ID " + IMGR_CLIENT_ID);


            Log.d("hopa : ", "hh 2");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("image", Base64.encodeToString(image, 0));
                   // .appendQueryParameter("key", IMGR_CLIENT_SECRET);
            String query = builder.build().getEncodedQuery();

//            conn.connect();
            Log.d("hopa : ", "hh 3");
            OutputStream os = conn.getOutputStream();
            Log.d("hopa : ", "hh 4");
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            os.close();


            Log.d("hopa : ", "hh 4");
            int responseCode = conn.getResponseCode();
            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

                Log.d("response : ", response);
                JSONObject jsonObject = new JSONObject(response);
                String imagePath = jsonObject.getJSONObject("data").getString("link");
                Log.d("link : ", imagePath);
                Log.d("hopa : ", "hhhhhh");

                return new JSONObject(response);
            }
          //  conn.connect();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}