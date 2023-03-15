package com.example.codetantraproxy;

import static com.example.codetantraproxy.Helper.Methods.checkForLoginDetails;
import static com.example.codetantraproxy.Helper.Methods.loginCheck;
import static com.example.codetantraproxy.Helper.Methods.updateFirstUser;
import static com.example.codetantraproxy.Helper.StringHelper.convertToURLSafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codetantraproxy.bean.User;
public class MainActivity extends AppCompatActivity {
    Button loginAboutButton;
    Button loginLoginButton;
    EditText loginEmailEditText;
    EditText loginPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAboutButton = findViewById(R.id.loginAboutbutton);
        loginLoginButton = findViewById(R.id.loginLoginButton);
        loginEmailEditText = findViewById(R.id.loginEmailText);
        loginPasswordEditText = findViewById(R.id.loginPaswordText);


        /*
            Check in database if there is a user with id 1
            If found, check if login credentials are still correct.
                If credentials are found correct, user will be redirected to next home page.
                If credentials are found incorrect, a toast will appear saying login password may have changed. Please try again later.
            If not found, take user input.
         */
        User user = checkForLoginDetails(getApplicationContext());
        if (user != null) {
            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
            home.putExtra("email", user.getEmailId());
            home.putExtra("password", user.getPassword());
            startActivity(home);
        }


        /*
            About Button on Login page.
            It will redirect user to About activity.
         */
        loginAboutButton.setOnClickListener(view -> {
            Intent about = new Intent(getApplicationContext(), LoginAboutActivity.class);
            startActivity(about);
        });


        /*
            Login Button on login page
            Check if credentials are valid.
            If found valid store user in db with id 1 and continue to home activity.
            If found invalid message will appear that password incorrect.
         */
        loginLoginButton.setOnClickListener(view -> {
            String  email = loginEmailEditText.getText().toString();
            String password = loginPasswordEditText.getText().toString();
            password = convertToURLSafe(password);
            if (loginCheck(email, password)) {
                updateFirstUser(getApplicationContext(), email, password);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Incorrect userid or password", Toast.LENGTH_LONG).show();
                loginPasswordEditText.setText("");
            }
        });
    }
}