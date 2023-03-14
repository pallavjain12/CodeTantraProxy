package com.example.codetantraproxy.Helper;

import static com.example.codetantraproxy.Helper.apis.checkCredentials;
import static com.example.codetantraproxy.Helper.apis.getMeetings;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.codetantraproxy.bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Methods {
    public static boolean loginCheck(String email, String password) {
        Log.d("logincheck", email + " " + password);
        if (checkCredentials(email, password)) {
            Log.d("loginChecl", "ture");
            return true;
        }
        else {
            Log.d("logincheck", "false");
            return false;
        }
    }

    public static void updateFirstUser(Context context, String email, String password) {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
        User user = new User(1, email, password);
        databaseHelper.userDao().addUser(user);
    }

    public static User checkForLoginDetails(Context context) {

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
        User user = databaseHelper.userDao().getLoginUser();
        if (user == null) {
            Log.d("msg","user empty in checkforLogindetails");
            return user;
        }
        Log.d("user null?", String.valueOf(user.getId()));
        if (checkCredentials(user.getEmailId(), user.getPassword())) {
            return user;
        }
        else {
            Toast.makeText(context, "Password changed. Please login again", new Integer(5)).show();
            return null;
        }
    }

    public static HashMap<String, String> fetchMeetings(String cookies) {
        JSONArray meetingsArray = getMeetings(cookies);
        if (meetingsArray == null)    return  new HashMap<>();
        HashMap<String, String> ans = new HashMap<>();
        for (int i = 0; i < meetingsArray.length(); i++) {
            try {
                JSONObject meetingObj = (JSONObject) meetingsArray.get(i);
                ans.put(meetingObj.get("title").toString(), meetingObj.get("_id").toString());
            }
            catch (Exception e) {
            }
        }
        return ans;
    }

    public static String getUserCookie(String email, String password) {

        return "";
    }
}
