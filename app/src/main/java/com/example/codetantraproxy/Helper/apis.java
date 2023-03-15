package com.example.codetantraproxy.Helper;

import static com.example.codetantraproxy.Helper.ApiHelper.todaysEpochTIme;
import static com.example.codetantraproxy.Helper.ApiHelper.tomorrowsEpochTime;

import android.os.StrictMode;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class apis {
        public static String getUserCookies(String email, String password) {
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
            Response response;
            try {
                response = client.newCall(request).execute();
                assert response.body() != null;
                String bodyy = response.body().string();
                JSONObject responseStatus = new JSONObject(bodyy);
                if (!responseStatus.get("result").equals("0")) {
                    return "invalid";
                }

            }
            catch(Exception e) {
                return "";
            }
            List<String> cookies = response.headers().values("Set-Cookie");
            StringBuilder returnans = new StringBuilder();
            for (String s : cookies) {
                returnans.append(s.split(";")[0]).append("; ");
            }
            return returnans.toString();
        }

        public static JSONArray getMeetings(String cookies) {
            String startDateEPOCH = todaysEpochTIme();
            String endDateEPOCH = tomorrowsEpochTime();


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
                assert response.body() != null;
                JSONObject responseObj = new JSONObject(response.body().string());
                Log.d("meetings", responseObj.toString());
                return new JSONArray(responseObj.get("ref").toString());
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

        public static String submitOtp (String cookie, String otp, String mid) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"code\":\"" + otp + "\",\"mid\":\"" + mid + "\"}");
            Request request = new Request.Builder()
                    .url("https://iiitb.codetantra.com/secure/rest/dd/muap")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Length", "62")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Host", "iiitb.codetantra.com")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"")
                    .addHeader("sec-ch-ua-mobile", "?1")
                    .addHeader("sec-ca-ua-platform", "\"Android\"")
                    .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Mobile Safari/537.36")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("Origin", "htps://iiitb.codetantra.com")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("Cookie", cookie)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                return response.body().string();
            }
            catch(Exception e) {
                return "invalid";
            }
        }
}
