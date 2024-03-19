package com.example.cmma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserHome extends AppCompatActivity {
    Button b;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.b1);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        SharedPreferences s = getSharedPreferences("name", MODE_PRIVATE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(UserHome.this, UserLogin.class);
                startActivity(i);
                finish();
                sharedPreferenceConfig.writeLoginStatus(false);
            }
        });
    }
}