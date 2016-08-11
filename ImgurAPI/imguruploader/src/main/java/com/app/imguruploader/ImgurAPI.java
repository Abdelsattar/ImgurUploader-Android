package com.app.imguruploader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.app.imguruploader.Response.Image;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Abd El-Sattar on 8/11/2016.
 */

public class ImgurAPI {

    Context context;
    private String IMGR_CLIENT_ID;
    private String IMGR_CLIENT_SECRET;
    private UploadImageListener mListener;

    public ImgurAPI(UploadImageListener listener) {
        mListener = listener;
    }

    public void init(String IMGR_CLIENT_ID, String IMGR_CLIENT_SECRT) {
        this.IMGR_CLIENT_ID = IMGR_CLIENT_ID;
        this.IMGR_CLIENT_SECRET = IMGR_CLIENT_SECRT;

    }

    public void uploadImage(byte[] bytes) {

        ImageUploaderTask imageUploaderTask = new ImageUploaderTask();
        imageUploaderTask.execute(bytes);
    }

    private class ImageUploaderTask extends AsyncTask<byte[], Object, Image> {

        private JSONParser jParser = new JSONParser();
        private String imgurAPIURL = "https://api.imgur.com/3/upload.json";
        private Image image;

        @Override
        protected Image doInBackground(byte[]... params) {

            try {
                Log.d("Backg", "Enterned");
                Gson gson = new Gson();
                JSONObject json = jParser.makeHttptRequest(imgurAPIURL, IMGR_CLIENT_ID,
                        IMGR_CLIENT_SECRET, params[0]);
                Log.d("Backg", json.toString());
                image = gson.fromJson(json.toString(), Image.class);
                return image;
            } catch (Exception e) {
                Log.d("Backg", "Catch");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Image image) {

            mListener.ImgurResponse(image != null, image);
        }

        private void hl() {
        /*
                URL url = null;
        try {
            url = new URL(imgurAPIURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Client-ID " + IMGR_CLIENT_ID);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("image", params[0])
                    .appendQueryParameter("key", IMGR_CLIENT_SECRET);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                // get the returned link from the response
                Log.d("response : ", response);
                JSONObject jsonObject = new JSONObject(response);
                imagePath = jsonObject.getJSONObject("data").getString("link");
                Log.d("link : ", imagePath);
                Log.d("hopa : ", "hhhhhh");
            }
            conn.connect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;

         */
        }
    }
}
