package com.example.codetantraproxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelecOtherAccount extends AppCompatActivity {

    Button  continueToSelectMeeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_other_account);

        continueToSelectMeeting = findViewById(R.id.continueToSelectMeeting);

        continueToSelectMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
                startActivity(intent);
            }
        });
    }
}