package com.example.cmma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    Button login;
    EditText user, password;
    TextView userLogin;
    private SharedPreferenceConfigAdmin sharedPreferenceConfigAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        login = findViewById(R.id.login);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        userLogin = findViewById(R.id.userLogin);
        sharedPreferenceConfigAdmin = new SharedPreferenceConfigAdmin(getApplicationContext());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_entered = user.getText().toString();
                String password_entered = password.getText().toString();
                if (username_entered.equals("admin") && password_entered.equals("123456")) {
//                    Toast.makeText(AdminLogin.this, username_entered + " " + password_entered, Toast.LENGTH_SHORT).show();
                    Toast.makeText(AdminLogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminLogin.this, AdminHome.class));
                    sharedPreferenceConfigAdmin.writeLoginStatusAdmin(true);
                    SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                    editor.putString("admin", username_entered);
                    editor.apply();
                }
                else {
//                    Toast.makeText(AdminLogin.this, username_entered + " " + password_entered, Toast.LENGTH_SHORT).show();

                    Toast.makeText(AdminLogin.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }

        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLogin.this, UserLogin.class);
                startActivity(i);
                finish();
            }
        });
    }
}