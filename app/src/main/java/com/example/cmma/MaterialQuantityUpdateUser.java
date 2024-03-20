package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;

public class MaterialQuantityUpdateUser extends AppCompatActivity {
    ArrayList<String> material;
    Spinner spinner;
    DatabaseReference dr;
    Button updateMaterialQuantity,generateValues;
    ArrayAdapter<String> adapter;
    TextView materialNameTV,currentQuantityTV;
    EditText newQuantityET;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_quantity_update_user);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Material");
        spinner = findViewById(R.id.spin);
        materialNameTV = findViewById(R.id.materialname);
        radioGroup=findViewById(R.id.radioGroup);
        newQuantityET = findViewById(R.id.updateQuantityAmount);
        radioGroup.clearCheck();
        currentQuantityTV = findViewById(R.id.currentquantity);
        material = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        generateValues = findViewById(R.id.generateValues);
        updateMaterialQuantity = findViewById(R.id.updateButton);
        dr.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                material.clear();
                material.add("Select item");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    MaterialPojo mp = ds.getValue(MaterialPojo.class);
//                    System.out.println(mp);
                    String name = mp.getMaterialName();
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
        generateValues.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                material.clear();
                String item = spinner.getSelectedItem().toString();
                System.out.println(item);

                dr.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            MaterialPojo mp = ds.getValue(MaterialPojo.class);
                            String name = mp.getMaterialName();
                            System.out.println(name.equals(item));
                            if (name.equals(item)) {
//                                System.out.println(item);
                                String newName = mp.getMaterialName();
                                String quantity = mp.getCurrentQuantity();
                                materialNameTV.setText(newName);
                                currentQuantityTV.setText(quantity);
                            } else {
//                                Toast.makeText(MaterialQuantityUpdateUser.this, "Select item", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });
        updateMaterialQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuantityString = newQuantityET.getText().toString();

                int newQuantity = 0;
                int currentQuantity = 0;
                String materialName = materialNameTV.getText().toString();
                String currentQuantityString = currentQuantityTV.getText().toString();
                int id = radioGroup.getCheckedRadioButtonId();
                if (!currentQuantityString.isEmpty()) {
                    currentQuantity = Integer.parseInt(currentQuantityTV.getText().toString());
                }
                else {
                    Toast.makeText(MaterialQuantityUpdateUser.this, "Select item and Generate values", Toast.LENGTH_SHORT).show();

                }
                if (!newQuantityString.isEmpty()) {
                    newQuantity = Integer.parseInt(newQuantityET.getText().toString());
                }
                else {
                   Toast.makeText(MaterialQuantityUpdateUser.this, "Enter value for new quantity", Toast.LENGTH_SHORT).show();

                }
                if (id == 1) {
                    newQuantity += currentQuantity;
                    String newQuantitySendDB = String.valueOf(newQuantity);
                    if (!newQuantityString.isEmpty()) {

                        dr.child(materialName).child("currentQuantity").setValue(newQuantitySendDB).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MaterialQuantityUpdateUser.this, "Increased", Toast.LENGTH_SHORT).show();
                                materialNameTV.setText("");
                                currentQuantityTV.setText("");
                                radioGroup.clearCheck();
                                newQuantityET.setText("");
                            }
                        });

                    }
//                    else {
//                        Toast.makeText(MaterialQuantityUpdateUser.this, "Enter quantity", Toast.LENGTH_SHORT).show();
//
//                    }
                }
                else if (id==2) {
                    newQuantity = currentQuantity - newQuantity;
                    String newQuantitySendDB = String.valueOf(newQuantity);

                    if (!newQuantityString.isEmpty()) {
                        dr.child(materialName).child("currentQuantity").setValue(newQuantitySendDB).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MaterialQuantityUpdateUser.this, "Decreased", Toast.LENGTH_SHORT).show();
                                materialNameTV.setText("");
                                currentQuantityTV.setText("");
                                radioGroup.clearCheck();
                                newQuantityET.setText("");
                            }
                        });
                    }
//                    else {
//                        Toast.makeText(MaterialQuantityUpdateUser.this, "Enter quantity", Toast.LENGTH_SHORT).show();
//
//                    }
                }
                else {
                    Toast.makeText(MaterialQuantityUpdateUser.this, "Select increase or decrease quantity", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}