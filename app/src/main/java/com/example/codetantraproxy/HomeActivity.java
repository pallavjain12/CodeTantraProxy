package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.getUserCookie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button addEmailPassword;
    Button markYourAttendance;
    Button markOtherAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addEmailPassword = findViewById(R.id.addEmailPasswordButton);
        markYourAttendance = findViewById(R.id.markYourButton);
        markOtherAttendance = findViewById(R.id.markOtherButton);
        String email =  getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

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
                String cookies = getUserCookie(email, password);
                if (cookies.equals("invalid")) {
                    Toast.makeText(getApplicationContext(), "Password changed or invalid password", new Integer(5));
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
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