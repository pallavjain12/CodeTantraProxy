package com.example.codetantraproxy.Helper;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class apis {
        public static String getUserCookies(String email, String password) throws IOException, JSONException {
            OkHttpClient client = new OkHttpClient();
            String mediaTypeString = "{\"loginId\":\""+ email + "\",\"password\":\""+ password +"\",\"_ct_blip\":\"\"}";
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, mediaTypeString);
            Request request = new Request.Builder()
                    .url("https://iiitb.codetantra.com/rest/login")
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"")
                    .addHeader("sec-ch-ua-platform", "\"Linux\"")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                    .addHeader("Host", "iiitb.codetantra.com")
                    .build();
            Response response = client.newCall(request).execute();
            String bodyy = response.body().string();
            JSONObject responseStatus = new JSONObject(bodyy);
            if (!responseStatus.get("result").equals("0")) {
                return "invalid";
            }
            List<String> cookies = response.headers().values("Set-Cookie");
            String returnans = "";
            for (String s : cookies) {
                returnans += (s.split(";")[0]  + "; ");
            }
            return returnans;
        }

        public static JSONArray getMeetings(String cookies) {
            String startDateEPOCH = "";
            String endDateEPOCH = "";


            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"minDate\": "+startDateEPOCH+",\n    \"maxDate\": " + endDateEPOCH + ",\n    \"filters\": {\n        \"showSelf\": true,\n        \"status\": \"started,ended,scheduled\"\n    }\n}");
            Request request = new Request.Builder()
                    .url("https://iiitb.codetantra.com/secure/rest/dd/mf")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Host", "iiitb.codetantra.com")
                    .addHeader("Origin", "https://iiitb.codetantra.com")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"")
                    .addHeader("sec-ch-ua-platform", "\"Linux\"")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                    .addHeader("Content-Length", "112")
                    .addHeader("Referer", "https://iiitb.codetantra.com/secure/tla/m.jsp")
                    .addHeader("Cookie", cookies)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject responseObj = new JSONObject(response.body().string());
                JSONArray meetingsArray = new JSONArray(responseObj.get("ref").toString());
                return meetingsArray;
            }
            catch (Exception e) {
                return null;
            }
        }
        public static boolean checkCredentials(String email, String password) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            try {
                if (getUserCookies(email, password).equals("invalid")) {
                    Log.d("checkCredentials", "false");
                    return false;
                }
                Log.d("checkCredentials", "true");
                return true;
            }
            catch (Exception e) {
                Log.d("cheeckCredential", "code pahata" + e);
                return false;
            }
        }
}
