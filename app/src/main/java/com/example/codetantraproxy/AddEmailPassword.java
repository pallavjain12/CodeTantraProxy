package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.deleteFromDB;
import static com.example.codetantraproxy.Helper.Methods.fetchAllUserWithId;
import static com.example.codetantraproxy.Helper.StringHelper.convertToURLSafe;
import static com.example.codetantraproxy.Helper.apis.checkCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.codetantraproxy.Helper.DatabaseHelper;
import com.example.codetantraproxy.bean.User;

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

        HashMap<Integer, String> map;
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
            chbx.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    idsToRemove.add(em.getKey());
                }
                else {
                    idsToRemove.remove(em.getKey());
                }
            });
        }

        btnDeleteAccounts.setOnClickListener(view -> {
            for (int i : idsToRemove) {
                if (i == 1) continue;
                if (deleteFromDB(getApplicationContext(), i)) {
                    CheckBox chbx = new CheckBox(getApplicationContext());
                    chbx.setId(i);
                    linearLayoutStudents.removeView(chbx);
                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error deleting", Toast.LENGTH_LONG).show();
                }
            }
            finish();
            startActivity(getIntent());
        });

        addUserButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            password = convertToURLSafe(password);
            if (checkCredentials(email, password)) {
                Toast.makeText(getApplicationContext(), "Credentials verified successfully", Toast.LENGTH_LONG).show();
                try {
                    DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
                    databaseHelper.userDao().addUser(new User(email, password));
                    Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error occured while adding user. Please try again later", Toast.LENGTH_LONG).show();
                }
                emailEditText.setText("");
                passwordEditText.setText("");
            }
            else {
                Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_LONG).show();
            }
            finish();
            startActivity(getIntent());
        });
    }
}