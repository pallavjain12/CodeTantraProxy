package com.example.codetantraproxy.Helper;

import static com.example.codetantraproxy.Helper.apis.checkCredentials;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.codetantraproxy.bean.User;

public class Methods {
    public static boolean loginCheck(String email, String password) {
        if (checkCredentials(email, password)) {
            return true;
        }
        else {
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
        Log.d("user null?", String.valueOf(user.getId()));
        if (user == null) {
            return user;
        }
        if (checkCredentials(user.getEmailId(), user.getEmailId())) {
            return user;
        }
        else {
            Toast.makeText(context, "Password changed. Please login again", new Integer(5)).show();
            return null;
        }
    }
}
