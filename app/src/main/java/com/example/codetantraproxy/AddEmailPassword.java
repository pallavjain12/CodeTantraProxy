package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.deleteFromDB;
import static com.example.codetantraproxy.Helper.Methods.fetchAllUserWithId;
import static com.example.codetantraproxy.Helper.Methods.makeURLSafe;
import static com.example.codetantraproxy.Helper.apis.checkCredentials;
import static com.example.codetantraproxy.Helper.apis.getUserCookies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.codetantraproxy.Helper.DatabaseHelper;
import com.example.codetantraproxy.bean.User;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEmailPassword extends AppCompatActivity {

    Button addUserButton;
    EditText emailEditText;
    EditText passwordEditText;

    Button btnDeleteAccounts;
    LinearLayout linearLayoutStudents;
    List<Integer> idsToRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_email_password);

        addUserButton = (Button) findViewById(R.id.addAccount);
        emailEditText = (EditText) findViewById(R.id.emailAddressEditText);
        passwordEditText = (EditText) findViewById(R.id.paswordEditText);
        btnDeleteAccounts = findViewById(R.id.btnDeleteAccounts);
        linearLayoutStudents = findViewById(R.id.studentsList);

        HashMap<Integer, String> map = new HashMap<>();
        map = fetchAllUserWithId(getApplicationContext());
        if(map.size() == 1) {
            btnDeleteAccounts.setVisibility(View.GONE);
        }

        idsToRemove = new ArrayList<>();

        for(Map.Entry<Integer, String> em : map.entrySet()) {
            if (em.getKey() == 1)   continue;
            CheckBox chbx = new CheckBox(getApplicationContext());
            chbx.setText(em.getValue());
            chbx.setId(em.getKey());
            linearLayoutStudents.addView(chbx);
            chbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        idsToRemove.add(em.getKey());
                    }
                    else {
                        idsToRemove.remove(Integer.valueOf(em.getKey()));
                    }
                }
            });
        }

        btnDeleteAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i : idsToRemove) {
                    if (i == 1) continue;
                    if (deleteFromDB(getApplicationContext(), i)) {
                        CheckBox chbx = new CheckBox(getApplicationContext());
                        chbx.setId(i);
                        linearLayoutStudents.removeView(chbx);
                    }
                    else {
                        Log.d("error deleteing", "cannot dle");
                    }
                }
                finish();
                startActivity(getIntent());
            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                password = makeURLSafe(password);
                if (checkCredentials(email, password)) {
                    Toast.makeText(getApplicationContext(), "Credentials verified successfully", new Integer(5)).show();
                    try {
                        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
                        databaseHelper.userDao().addUser(new User(email, password));
                        Toast.makeText(getApplicationContext(), "User added successfully", new Integer(5));
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error occured while adding user. Please try again later", new Integer(5));
                    }
                    emailEditText.setText("");
                    passwordEditText.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect email or password", new Integer(5)).show();
                }
                finish();
                startActivity(getIntent());
            }
        });
    }
}