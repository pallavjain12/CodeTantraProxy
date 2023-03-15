package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.apis.getUserCookies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        homeAboutButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginAboutActivity.class);
            startActivity(intent);
        });


        /*
            Call AddEmailPassword activity to add new user.
         */
        addEmailPassword.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddEmailPassword.class);
            startActivity(intent);
        });

        /*
            Retrieve id=1 user cookie and send it to next activity for fetching meeings details
         */
        markYourAttendance.setOnClickListener(view -> {
            String cookies = getUserCookies(email, password);
            if (cookies.equals("invalid")) {
                Toast.makeText(getApplicationContext(), "Password changed or invalid password", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
                intent.putExtra("cookies", cookies);
                startActivity(intent);
            }
        });

        /*
            Display list of all users to select from for retrieving meeting from other account.
         */
        markOtherAttendance.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SelecOtherAccount.class);
            startActivity(intent);
        });
    }
}