package com.example.codetantraproxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
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