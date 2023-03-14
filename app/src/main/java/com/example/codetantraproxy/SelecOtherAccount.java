package com.example.codetantraproxy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SelecOtherAccount extends AppCompatActivity {

    Button  continueToSelectMeeting;
    RadioGroup showAccountsRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_other_account);

        continueToSelectMeeting = findViewById(R.id.continueToSelectMeeting);
        showAccountsRadioGroup = findViewById(R.id.showAccountsRadioGroup);

        continueToSelectMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
                startActivity(intent);
            }
        });
    }
}