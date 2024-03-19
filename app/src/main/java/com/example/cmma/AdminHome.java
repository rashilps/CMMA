package com.example.cmma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {
    Button signOut,addNewMaterial,deleteMaterial;
    FirebaseAuth mAuth;
    private SharedPreferenceConfigAdmin sharedPreferenceConfigAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        signOut = findViewById(R.id.b1);
        addNewMaterial = findViewById(R.id.addnewmmaterial);
        deleteMaterial = findViewById(R.id.deletematerial);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfigAdmin = new SharedPreferenceConfigAdmin(getApplicationContext());
        SharedPreferences s = getSharedPreferences("name", MODE_PRIVATE);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(AdminHome.this, Login.class);
                startActivity(i);
                finish();
                sharedPreferenceConfigAdmin.writeLoginStatusAdmin(false);
            }
        });
        addNewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHome.this, AddNewMaterialAdmin.class);
                startActivity(i);
            }
        });
        deleteMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHome.this, DeleteMaterial.class);
                startActivity(i);
            }
        });
    }
}