package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.apis.getUserCookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button addEmailPassword;
    Button markYourAttendance;
    Button markOtherAttendance;
    Button homeAboutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addEmailPassword = findViewById(R.id.addEmailPasswordButton);
        markYourAttendance = findViewById(R.id.markYourButton);
        markOtherAttendance = findViewById(R.id.markOtherButton);
        homeAboutButton = findViewById(R.id.homeAboutButton);

        String email =  getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        homeAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginAboutActivity.class);
                startActivity(intent);
            }
        });


        /*
            Call AddEmailPassword activity to add new user.
            Later to add delete functionality
         */
        addEmailPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEmailPassword.class);
                startActivity(intent);
            }
        });

        markYourAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("email", email);
                Log.d("password", password);
                String cookies = getUserCookies(email, password);
                if (cookies.equals("invalid")) {
                    Toast.makeText(getApplicationContext(), "Password changed or invalid password", new Integer(5));
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
                    Log.d("cookies = ", cookies);
                    intent.putExtra("cookies", cookies);
                    startActivity(intent);
                }
            }
        });

        markOtherAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelecOtherAccount.class);
                startActivity(intent);
            }
        });
    }
}