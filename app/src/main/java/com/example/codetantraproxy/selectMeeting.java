package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.fetchMeetings;
import static com.example.codetantraproxy.Helper.Methods.fetchUsersCookies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class selectMeeting extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView noMeetingFoundBox;
    Button otpButton;

    ScrollView scrollView;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meeting);

        radioGroup = findViewById(R.id.radioGroup);
        noMeetingFoundBox = findViewById(R.id.noMeetingsFoundBox);
        otpButton = findViewById(R.id.otpButton);
        scrollView = findViewById(R.id.checkBoxContainer);
        container = findViewById(R.id.linearLayoutInsideCheckBoxContainer);

        String meetingsFetchCookies = getIntent().getStringExtra("cookies");
        Map<String, String> mapMeetings = fetchMeetings(meetingsFetchCookies);
        HashMap<String, String> selectedStudents = new HashMap<>();

        if (mapMeetings.size() != 0) {

            noMeetingFoundBox.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
            otpButton.setVisibility(View.VISIBLE);

            radioGroup.setOrientation(LinearLayout.VERTICAL);
            for (Map.Entry<String, String> meeting : mapMeetings.entrySet()) {
                String title = meeting.getKey();
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(title);
                radioGroup.addView(rdbtn);
                radioGroup.check(rdbtn.getId());
            }

            HashMap<String, String> students = fetchUsersCookies(getApplicationContext());
            for(Map.Entry<String, String> em : students.entrySet()) {
                String email = em.getKey();
                String cookie = em.getValue();
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setText(email);
                container.addView(cb);
                if (cookie.length() < 10) {
                    cb.setChecked(false);
                    cb.setEnabled(false);
                }
                else {
                    selectedStudents.put(email, cookie);
                    cb.setChecked(true);
                }
                cb.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (isChecked) {
                        selectedStudents.put(email, cookie);
                    }
                    else {
                        selectedStudents.remove(email);
                    }
                });
            }
        }

        otpButton.setOnClickListener(view -> {
            int selected = radioGroup.getCheckedRadioButtonId();
            RadioButton rbtn = (RadioButton) findViewById(selected);
            Intent intent = new Intent(getApplicationContext(), enterOTP.class);
            intent.putExtra("mid", mapMeetings.get(rbtn.getText().toString()));
            intent.putExtra("selectedstudentsMap", selectedStudents);
            startActivity(intent);
        });

    }
}