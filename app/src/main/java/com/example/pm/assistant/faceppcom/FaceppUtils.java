package com.example.pm.assistant.faceppcom;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class FaceppUtils {
    private static String APIKEY = "citvp57CbL2qzjjG8YZKOjtFL7qxBgI-";
    private static String APIKEYSECRET = "7wfQQFz73KCUFUqFzLLhsAkX9beDq4-S";

    public interface FaceTokensResultCallback {
        void onFaceTokensCallback(List<String> tokens);
    }

    public static class FetchFaceTokensTask extends AsyncTask<String, Void, List<String>> {
        private FaceTokensResultCallback cb;

        public FetchFaceTokensTask(FaceTokensResultCallback cb) {
            this.cb = cb;
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            final String requestUrl = "https://api-us.faceplusplus.com/facepp/v3/detect";
            List<String> result = new ArrayList<>();
            try {
                final URL url = new URL(requestUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setConnectTimeout(1 * 60 * 1000);

                DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
                outStream.writeBytes(
                        "api_key=" + APIKEY + "&" +
                                "api_secret=" + APIKEYSECRET + "&" +
                                "image_base64=" + strings[0]
                );
                outStream.flush();
                outStream.close();

                conn.connect();


                if(conn.getResponseCode() == 200) {
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String responseString = "";
                    String line;
                    while((line = inputReader.readLine()) != null)
                        responseString += line;

                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray facesArray = jsonObject.getJSONArray("faces");

                    for(int i = 0, len = facesArray.length(); i < len; i++) {
                        result.add(facesArray.getJSONObject(i).getString("face_token"));
                    }
                }

                BufferedReader inputReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseString = "";
                String line;
                while((line = inputReader.readLine()) != null)
                    responseString += line;

                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray facesArray = jsonObject.getJSONArray("faces");

                for(int i = 0, len = facesArray.length(); i < len; i++) {
                    result.add(facesArray.getJSONObject(i).getString("face_token"));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            cb.onFaceTokensCallback(strings);
        }
    }

}
