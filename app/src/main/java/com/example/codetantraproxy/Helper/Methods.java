package com.example.codetantraproxy.Helper;

import static com.example.codetantraproxy.Helper.apis.checkCredentials;
import static com.example.codetantraproxy.Helper.apis.getMeetings;
import static com.example.codetantraproxy.Helper.apis.getUserCookies;
import static com.example.codetantraproxy.Helper.apis.submitOtp;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.codetantraproxy.bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

public class Methods {
    public static boolean loginCheck(String email, String password) {
        return checkCredentials(email, password);
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
            return null;
        }
        Log.d("user null?", String.valueOf(user.getId()));
        if (checkCredentials(user.getEmailId(), user.getPassword())) {
            return user;
        }
        else {
            Toast.makeText(context, "Password changed. Please login again", Toast.LENGTH_LONG).show();
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
                return new HashMap<>();
            }
        }
        return ans;
    }

    public static HashMap<String, String> fetchUsersCookies(Context context) {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
        List<User> list = databaseHelper.userDao().getAllUsers();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            User temp = list.get(i);
            map.put(temp.getEmailId(), getUserCookies(temp.getEmailId(), temp.getPassword()));
        }
        return map;
    }

    public static boolean markAttendence(String cookie, String otp, String mid) {
        String response = submitOtp(cookie, otp, mid);
        if (response.equals("invalid")) return false;

        try {
            JSONObject responseObj = new JSONObject(response);
            if (responseObj.get("msg") != null && responseObj.get("msg").equals("Invalid OTP")) {
                return false;
            }
            else return responseObj.get("msg") != null && responseObj.get("msg").equals("Successfull");
        }
        catch (Exception e) {
            return  false;
        }
    }

    public static HashMap<String, String> fetchAllUsers(Context context) {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
        List<User> list = databaseHelper.userDao().getAllUsers();
        HashMap<String, String> map = new HashMap<>();
        for (User u : list) {
            map.put(u.getEmailId(), u.getPassword());
        }
        return map;
    }

    public static HashMap<Integer, String> fetchAllUserWithId(Context context) {
        HashMap<Integer, String> map = new HashMap<>();
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);

        List<User> list = databaseHelper.userDao().getAllUsers();
        for (User u : list) {
            map.put(u.getId(), u.getEmailId());
        }
        return map;
    }

    public static boolean deleteFromDB(Context context, int id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
        try {
            databaseHelper.userDao().deleteUser(new User(id, "",""));
            return  true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static String makeURLSafe(String str) {
        try {
            String temp = URLEncoder.encode(str, "utf-8");
            Log.d("encoded = ", temp);
            return temp;
        }
        catch(Exception e) {
            return  "";
        }
    }
}
