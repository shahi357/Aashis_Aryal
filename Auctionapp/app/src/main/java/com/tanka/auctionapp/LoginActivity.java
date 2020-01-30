package com.tanka.auctionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tanka.auctionapp.Activities.RegisterActivity;
import com.tanka.auctionapp.Activities.UserDashboardActivity;
import com.tanka.auctionapp.Bll.LoginBLL;
import com.tanka.auctionapp.StrictMode.StrictModeClass;

public class LoginActivity extends AppCompatActivity {
   private TextView Signup;

   private TextInputEditText etemail, etpassword;
   private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Form");
        Signup = findViewById(R.id.textSignUp);
        etemail = findViewById(R.id.email);
        etpassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogIn);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              login();
            }
        });
    }

    private void login() {
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();

        LoginBLL loginBLL = new LoginBLL();

        StrictModeClass.StrictMode();
        if (loginBLL.checkUser(email, password)) {
            Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            etemail.requestFocus();
        }

    }
}
