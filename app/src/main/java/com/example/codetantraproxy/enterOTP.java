package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.markAttendence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class enterOTP extends AppCompatActivity {

    Button submitOtp;
    EditText otpField;
    HashMap<String, String> selectedStudents;
    ArrayList<String> list;
    TextView remainingStudents;
    TextView markedStudents;
    LinearLayout remainingStudentsLayout;
    LinearLayout markedStudentsLayout;
    ScrollView remainingStudentsScrollView;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        submitOtp = findViewById(R.id.submitOTP);
        otpField = findViewById(R.id.enterOTPField);
        remainingStudents = findViewById(R.id.remainingCountTextView);
        markedStudents = findViewById(R.id.markedSuccessfullyTextView);
        remainingStudentsLayout = findViewById(R.id.remainingStudentsLayout);
        markedStudentsLayout = findViewById(R.id.markedStudentsLinearLayout);
        remainingStudentsScrollView = findViewById(R.id.remainingStudentsScrollView);

        selectedStudents = (HashMap<String, String>) getIntent().getSerializableExtra("selectedstudentsMap");
        String mid = getIntent().getStringExtra("mid");
        total = selectedStudents.size();

        remainingStudents.setText("Count : " + selectedStudents.size());
        markedStudents.setText("Count : " + (total - selectedStudents.size()));

        for (Map.Entry<String, String> em : selectedStudents.entrySet()) {
            TextView temp = new TextView(getApplicationContext());
            temp.setText(em.getKey());
            remainingStudentsLayout.addView(temp);
        }

        submitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otpField.getText().toString();
                ArrayList<String> tempList = new ArrayList<>();
                HashMap<String, String> tempHash = new HashMap<>();
                tempHash.putAll(selectedStudents);
                for (Map.Entry<String, String> em : tempHash.entrySet()) {
                    String cookie = em.getValue();
                    if (markAttendence(cookie, otp, mid)) {
                        tempList.add(em.getKey());
                        selectedStudents.remove(em.getKey());
                    }
                    else {
                        break;
                    }
                }
                for (String s : tempList) {
                    TextView temp = new TextView(getApplicationContext());
                    temp.setText(s);
                    markedStudentsLayout.addView(temp);
                }
                remainingStudentsScrollView.removeAllViews();
                remainingStudentsLayout = new LinearLayout(getApplicationContext());
                remainingStudentsScrollView.addView(remainingStudentsLayout);
                Log.d(" after sending otp size of selected studnets", "" + selectedStudents.size());
                for (Map.Entry<String, String> em : selectedStudents.entrySet()) {
                    TextView temp = new TextView(getApplicationContext());
                    temp.setText(em.getKey());
                    remainingStudentsLayout.addView(temp);
                }
                remainingStudents.setText("Count : " + selectedStudents.size());
                markedStudents.setText("Count : " + (total - selectedStudents.size()));
                Toast.makeText(getApplicationContext(), "Operation Completed", new Integer(5));
            }
        });
    }
}