package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.fetchMeetings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class selectMeeting extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView noMeetingFoundBox;
    Button otpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meeting);

        radioGroup = findViewById(R.id.radioGroup);
        noMeetingFoundBox = findViewById(R.id.noMeetingsFoundBox);
        otpButton = findViewById(R.id.otpButton);

        String cookies = getIntent().getStringExtra("cookies");
        Map<String, String> map = fetchMeetings(cookies);

        if (map.size() != 0) {

            noMeetingFoundBox.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
            otpButton.setVisibility(View.VISIBLE);

            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
            for (Map.Entry<String, String> meeting : map.entrySet()) {
                String title = meeting.getKey();
                String id = meeting.getValue();
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(title);
                radioGroup.addView(rdbtn);
            }
        }

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = radioGroup.getCheckedRadioButtonId();
                RadioButton rbtn = (RadioButton) findViewById(selected);
                String title = rbtn.getText().toString();
                Intent intent = new Intent(getApplicationContext(), enterOTP.class);
                intent.putExtra("meetingId", map.get(title));
                startActivity(intent);
            }
        });

    }
}