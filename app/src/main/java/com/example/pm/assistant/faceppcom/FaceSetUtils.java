package com.example.pm.assistant.faceppcom;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FaceSetUtils {
    public static String create(@Nullable String faceSetName) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("api_key", FacePP.APIKEY);
        bodyBuilder.addFormDataPart("api_secret", FacePP.APIKEYSECRET);
        if(faceSetName != null)
            bodyBuilder.addFormDataPart("display_name", faceSetName);
        requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(FacePP.APIURL + "/faceset/create").post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject jsonObject = new JSONObject(response.body().string());
            Thread.sleep(1000);
            return jsonObject.getString("faceset_token");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addFace(String fsToken, String faceToken) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("api_key", FacePP.APIKEY);
        bodyBuilder.addFormDataPart("api_secret", FacePP.APIKEYSECRET);
        bodyBuilder.addFormDataPart("faceset_token", fsToken);
        bodyBuilder.addFormDataPart("face_tokens", faceToken);
        requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(FacePP.APIURL + "/faceset/addface").post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject jsonObject = new JSONObject(response.body().string());
            Thread.sleep(1000);

            if(jsonObject.getInt("face_added") == 1)
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeFace(String fsToken, String faceToken) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("api_key", FacePP.APIKEY);
        bodyBuilder.addFormDataPart("api_secret", FacePP.APIKEYSECRET);
        bodyBuilder.addFormDataPart("faceset_token", fsToken);
        bodyBuilder.addFormDataPart("face_tokens", faceToken);
        requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(FacePP.APIURL + "/faceset/removeface").post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject jsonObject = new JSONObject(response.body().string());
            Thread.sleep(1000);
            if(jsonObject.getInt("face_removed") == 1)
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String fsToken) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("api_key", FacePP.APIKEY);
        bodyBuilder.addFormDataPart("api_secret", FacePP.APIKEYSECRET);
        bodyBuilder.addFormDataPart("faceset_token", fsToken);
        requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(FacePP.APIURL + "/faceset/delete").post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject jsonObject = new JSONObject(response.body().string());
            Thread.sleep(1000);
            if(jsonObject.getString("faceset_token").equalsIgnoreCase(fsToken))
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> detectFaces(byte[] imgBytes) {
        List<String> result = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("api_key", FacePP.APIKEY);
        bodyBuilder.addFormDataPart("api_secret", FacePP.APIKEYSECRET);
        bodyBuilder.addFormDataPart("image_base64", Base64.encodeToString(imgBytes, Base64.NO_WRAP));

        requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(FacePP.APIURL + "/detect").post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();

            String responseStr = response.body().string();
            Log.e("DEBUGRESPONSE", responseStr);

            JSONObject jsonObject = new JSONObject(responseStr);
            Thread.sleep(1000);

            if(jsonObject.has("error_message")) {
                Log.e("FacePPERROR", jsonObject.getString("error_message"));
                return result;
            }
            else {
                JSONArray resultsArray = jsonObject.getJSONArray("faces");

                for(int i = 0, len = resultsArray.length(); i < len; i++) {
                    result.add(resultsArray.getJSONObject(i).getString("face_token"));
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> reconFaces(String fsToken, byte[] imgBytes) {
        List<String> result = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody;

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("api_key", FacePP.APIKEY);
        bodyBuilder.addFormDataPart("api_secret", FacePP.APIKEYSECRET);
        bodyBuilder.addFormDataPart("image_base64", Base64.encodeToString(imgBytes, Base64.NO_WRAP));
        bodyBuilder.addFormDataPart("faceset_token", fsToken);
        bodyBuilder.addFormDataPart("return_result_count", "2");

        requestBody = bodyBuilder.build();

        Request request = new Request.Builder().url(FacePP.APIURL + "/search").post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();

            String responseStr = response.body().string();
            Log.e("FacePPERROR", responseStr);

            JSONObject jsonObject = new JSONObject(responseStr);
            Thread.sleep(1000);

            if(jsonObject.has("error_message")) {
                Log.e("FacePPERROR", jsonObject.getString("error_message"));
                return result;
            }
            else {
                JSONArray resultsArray = jsonObject.getJSONArray("results");

                for(int i = 0, len = resultsArray.length(); i < len; i++) {
                    Log.e("CONFIDENCE", Double.toString(resultsArray.getJSONObject(i).getDouble("confidence")) + "  " + resultsArray.getJSONObject(i).getString("face_token"));
                    if(resultsArray.getJSONObject(i).getDouble("confidence") > 80.0)
                        result.add(resultsArray.getJSONObject(i).getString("face_token"));
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
