package com.example.cmma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserHomeScreen extends AppCompatActivity {
    Button logout, materialQuantityUpdate,materialInformation,notification,autoEmail,supplierContact;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        logout = findViewById(R.id.b1);
        materialQuantityUpdate = findViewById(R.id.materialQuantityUpdate);
        materialInformation = findViewById(R.id.materialInformationButton);
        notification = findViewById(R.id.notificationButton);
        autoEmail = findViewById(R.id.emailButton);
        supplierContact = findViewById(R.id.suppliercontactinfoButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(UserHomeScreen.this, UserLogin.class);
                startActivity(i);
                finish();
                sharedPreferenceConfig.writeLoginStatus(false);
            }
        });
        materialQuantityUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, MaterialQuantityUpdateUser.class);
                startActivity(i);
            }
        });
        materialInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, DisplayMaterialInformationUser.class);
                startActivity(i);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, NotificationAlertUser.class);
                startActivity(i);
            }
        });
    autoEmail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(UserHomeScreen.this, AutomaticEmailGeneration.class);
            startActivity(i);
        }
    });
    supplierContact.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(UserHomeScreen.this, SupplierContactInformationUser.class);
            startActivity(i);
        }
    });
    }
}