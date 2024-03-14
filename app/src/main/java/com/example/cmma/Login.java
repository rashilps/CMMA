package com.example.cmma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    TextView signup,forgot,admin;
    EditText user, password;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private SharedPreferenceConfigAdmin sharedPreferenceConfigAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        forgot = findViewById(R.id.forgot);
        admin = findViewById(R.id.admin);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgot.setOnClickListener(this);
        admin.setOnClickListener(this);


        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
//        Toast.makeText(this,"" + sharedPreferenceConfig.readLoginStatus(), Toast.LENGTH_SHORT).show();

        if (sharedPreferenceConfig.readLoginStatus()) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        sharedPreferenceConfigAdmin = new SharedPreferenceConfigAdmin(getApplicationContext());
//        Toast.makeText(this,"" + sharedPreferenceConfigAdmin.readLoginStatusAdmin(), Toast.LENGTH_SHORT).show();
        if (sharedPreferenceConfigAdmin.readLoginStatusAdmin()) {
            Intent i = new Intent(this, AdminHome.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == signup) {
            Intent i = new Intent(Login.this, Signup.class);
            startActivity(i);
        }

        if (view == login) {

            String username_entered = user.getText().toString();
            String password_entered = password.getText().toString();


                if (TextUtils.isEmpty(username_entered)) {
                    user.setError("Username cannot be empty");
                    user.requestFocus();
                } else if (TextUtils.isEmpty(password_entered)) {
                    password.setError("Password cannot be empty");
                    password.requestFocus();
                } else {
                    mAuth.signInWithEmailAndPassword(username_entered, password_entered).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                sharedPreferenceConfig.writeLoginStatus(true);
                                SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                                editor.putString("name", username_entered);
                                editor.apply();
//                                Toast.makeText(getApplicationContext(),"" + sharedPreferenceConfig.readLoginStatus(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Login.this, "Login error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        }
        if (view == forgot){
            Intent i = new Intent(Login.this, PasswordReset.class);
            startActivity(i);
        }
        if (view == admin){
            Intent i = new Intent(Login.this, AdminLogin.class);
            startActivity(i);
        }
    }
}
