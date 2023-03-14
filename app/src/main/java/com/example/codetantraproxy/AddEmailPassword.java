package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.apis.checkCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codetantraproxy.Helper.DatabaseHelper;
import com.example.codetantraproxy.bean.User;

public class AddEmailPassword extends AppCompatActivity {

    Button addUserButton;
    EditText emailEditText;
    EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_email_password);

        addUserButton = (Button) findViewById(R.id.addAccount);
        emailEditText = (EditText) findViewById(R.id.emailAddressEditText);
        passwordEditText = (EditText) findViewById(R.id.paswordEditText);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // password = Helper.convertToURLSafe(password);

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
            }
        });
    }
}