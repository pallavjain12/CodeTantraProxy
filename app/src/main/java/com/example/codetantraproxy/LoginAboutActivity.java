package com.example.codetantraproxy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginAboutActivity extends AppCompatActivity {

    static final String LINKEDIN = "https://www.linkedin.com/in/pallavjain12/";
    static final String GITHUB_ISSUE = "https://github.com/pallavjain12/CodeTantraProxy/issues/new";
    static final String EMAIL = "mailto:\"pallavjain12@gmail.com\"";
    Button btnReportGithub;
    Button btnLinkedIn;
    Button btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_about);


        btnEmail = findViewById(R.id.btnEmail);
        btnLinkedIn = findViewById(R.id.btnLinkedIn);
        btnReportGithub = findViewById(R.id.btnReportGithub);

        btnEmail.setOnClickListener(view -> {
            Intent httpIntent = new Intent(Intent.ACTION_VIEW);
            httpIntent.setData(Uri.parse(EMAIL));
            startActivity(httpIntent);
        });

        btnLinkedIn.setOnClickListener(view -> {
            Intent httpIntent = new Intent(Intent.ACTION_VIEW);
            httpIntent.setData(Uri.parse(LINKEDIN));
            startActivity(httpIntent);
        });

        btnReportGithub.setOnClickListener(view -> {
            Intent httpIntent = new Intent(Intent.ACTION_VIEW);
            httpIntent.setData(Uri.parse(GITHUB_ISSUE));
            startActivity(httpIntent);
        });
    }
}