package com.example.cmma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserHomeStart extends AppCompatActivity {
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        b = findViewById(R.id.b1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeStart.this, UserHomeScreen.class);
                startActivity(i);
            }
        });
    }
}



//
//b.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        mAuth.signOut();
//        Intent i = new Intent(UserHomeStart.this, UserLogin.class);
//        startActivity(i);
//        finish();
//        sharedPreferenceConfig.writeLoginStatus(false);
//        }
//        });