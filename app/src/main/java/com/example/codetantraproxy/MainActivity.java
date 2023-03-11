package com.example.codetantraproxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button loginAboutButton;
    Button loginLoginButton;
    EditText loginEmailEditText;
    EditText loginPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAboutButton = (Button)findViewById(R.id.loginAboutbutton);
        loginLoginButton = (Button)findViewById(R.id.loginLoginButton);

        loginEmailEditText = (EditText)findViewById(R.id.loginEmailText);
        loginPasswordEditText = (EditText)findViewById(R.id.loginPaswordText);

        loginAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getApplicationContext(), LoginAboutActivity.class);
                startActivity(about);
            }
        });
    }
}