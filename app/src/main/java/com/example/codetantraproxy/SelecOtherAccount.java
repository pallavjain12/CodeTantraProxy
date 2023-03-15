package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.fetchAllUsers;
import static com.example.codetantraproxy.Helper.apis.getUserCookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SelecOtherAccount extends AppCompatActivity {

    Button  continueToSelectMeeting;
    RadioGroup showAccountsRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_other_account);

        continueToSelectMeeting = findViewById(R.id.continueToSelectMeeting);
        showAccountsRadioGroup = findViewById(R.id.showAccountsRadioGroup);
        HashMap<String, String> map = fetchAllUsers(getApplicationContext());

        for (Map.Entry<String, String> meeting : map.entrySet()) {
            String title = meeting.getKey();
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(title);
            showAccountsRadioGroup.addView(rdbtn);
            showAccountsRadioGroup.check(rdbtn.getId());
        }

        continueToSelectMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), selectMeeting.class);
            int selected = showAccountsRadioGroup.getCheckedRadioButtonId();
            RadioButton rbtn = (RadioButton) findViewById(selected);
            String email = rbtn.getText().toString();
            String password = map.get(email);
            String cookie = getUserCookies(email, password);
            if (cookie.length() < 10)   {
                Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_LONG).show();
            }
            else {
                intent.putExtra("cookies", cookie);
                startActivity(intent);
            }
        });
    }
}