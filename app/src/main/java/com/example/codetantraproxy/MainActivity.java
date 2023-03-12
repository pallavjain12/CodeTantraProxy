package com.example.codetantraproxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codetantraproxy.Helper.DatabaseHelper;

import java.io.File;
import java.util.List;

import com.example.codetantraproxy.Dao.UserDao;
import com.example.codetantraproxy.bean.User;

public class MainActivity extends AppCompatActivity {
    Button loginAboutButton;
    Button loginLoginButton;
    EditText loginEmailEditText;
    EditText loginPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAboutButton = findViewById(R.id.loginAboutbutton);
        loginLoginButton = findViewById(R.id.loginLoginButton);
        loginEmailEditText = findViewById(R.id.loginEmailText);
        loginPasswordEditText = findViewById(R.id.loginPaswordText);

        if (checkForLoginDetails()) {
            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(home);
        }

        loginAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getApplicationContext(), LoginAboutActivity.class);
                startActivity(about);
            }
        });

        loginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginCheck(loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString())) {
                    DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
                    User user = new User(1, loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString());
                    databaseHelper.userDao().addUser(user);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean checkForLoginDetails() {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        User user = databaseHelper.userDao().getLoginUser();
        Log.d("user null?", String.valueOf(user.getId()));
        if (user == null) {
            return false;
        }
        if (loginCheck(user.getEmailId(), user.getPassword())) {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Password changed. Please login again", new Integer(5)).show();
            return false;
        }
    }

    public boolean loginCheck(String email, String password) {
        /* checkwithapiIfsuccessfull(email, password)
           return true or false accordingly
         */
        Log.d("check", "returning true condition");
        return true;
    }
}