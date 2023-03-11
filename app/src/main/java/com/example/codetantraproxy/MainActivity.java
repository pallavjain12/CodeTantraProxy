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
        if (checkForLoginDetails("", "")) {
            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
        }
        loginAboutButton = (Button)findViewById(R.id.loginAboutbutton);
        loginLoginButton = (Button)findViewById(R.id.loginLoginButton);

        loginEmailEditText = (EditText)findViewById(R.id.loginEmailText);
        loginPasswordEditText = (EditText)findViewById(R.id.loginPaswordText);
        Toast.makeText(getApplicationContext(), "this is a toazxcst", new Integer(10)).show();

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
                loginCheck(loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString());
            }
        });
    }

    public boolean checkForLoginDetails(String email, String password) {
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        User userList = databaseHelper.userDao().getLoginUser();
        if (userList == null) {
            Log.d("msg", "did not find any user");
        }

        User user = new User(email, password);
        databaseHelper.userDao().addUser(user);
        Log.d("msg", "inserted data");
        return true;
    }

    public boolean loginCheck(String email, String password) {
        Log.d("email", email);
        Log.d("password", password);
        checkForLoginDetails(email, password);
        return false;
    }
}