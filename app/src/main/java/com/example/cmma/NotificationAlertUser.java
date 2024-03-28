package com.example.cmma;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class NotificationAlertUser extends AppCompatActivity {
    ArrayList<String> material;
    Spinner spinner;
    DatabaseReference dr,drNoti;
    Button setAlert;
    ArrayAdapter<String> adapter;
    EditText newQuantityET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_alert_user);
        newQuantityET = findViewById(R.id.newQuantity);
        spinner = findViewById(R.id.spin);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Material");
        drNoti = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Notification");

        material = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        setAlert = findViewById(R.id.setAlertButton);
        dr.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                material.clear();
                material.add("Select item");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    MaterialPojo mp = ds.getValue(MaterialPojo.class);
//                    System.out.println(mp);
                    String name = mp.getMaterialName();
//                    String quantity = mp.getCurrentQuantity();
//                    System.out.println(name);
                    material.add(name);
                    spinner.setAdapter(adapter);

                }
//                System.out.println(material.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        setAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = newQuantityET.getText().toString();
                String materialName = spinner.getSelectedItem().toString();
                NotificationPojo nat = new NotificationPojo(materialName,quantity);
                if (!materialName.equals("Select item") && !quantity.isEmpty() ){
                    drNoti.child(materialName).setValue(nat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            newQuantityET.setText("");
//                            currentQuantityET.setText("");
                        }
                    });
                }
                else {
                    Toast.makeText(NotificationAlertUser.this, "Please select an item from the dropdown", Toast.LENGTH_SHORT).show();
                }
            }
        });





//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//                        System.out.println(token);
//                        // Log and toast
////                        String msg = getString(R.string.app_name, token);
////                        Log.d(TAG, msg);
////                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}