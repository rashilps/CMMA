package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMaterialInformationUser extends AppCompatActivity {
    ArrayList<String> material;
    Spinner spinner;
    DatabaseReference dr;
    Button display,generateValues;
    ArrayAdapter<String> adapter;
    TextView materialNameTV;
    TextView materialDescriptionET,currentQuantityET,supplierNameET,supplierAddressET,supplierContactET,supplierEmailET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_material_information_user);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Material");
        spinner = findViewById(R.id.spin);
        materialNameTV = findViewById(R.id.materialname2);
        materialDescriptionET = findViewById(R.id.materialdescription2);
        currentQuantityET = findViewById(R.id.materialquantity2);
        supplierNameET = findViewById(R.id.suppliername2);
        supplierAddressET = findViewById(R.id.supplieraddress2);
        supplierContactET = findViewById(R.id.suppliercontact2);
        supplierEmailET = findViewById(R.id.supplieremail2);
        display = findViewById(R.id.displayButton);
        material = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        display.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                System.out.println(item);
                dr.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                material.clear();
//                material.add("Select item");
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            MaterialPojo mp = ds.getValue(MaterialPojo.class);
                            String name = mp.getMaterialName();
                                System.out.println(name);
//                            material.add(name);
//                            spinner.setAdapter(adapter);
                            if (name.equals(item)) {
                                String newName = mp.getMaterialName();
                                String desc = mp.getMaterialDescription();
                                String quantity = mp.getCurrentQuantity();
                                String sName = mp.getSupplierName();
                                String sAdd = mp.getSupplierAddress();
                                String sContact = mp.getSupplierContact();
                                String sEmail = mp.getSupplierEmail();
                                materialNameTV.setText(newName);
                                materialDescriptionET.setText(desc);
                                currentQuantityET.setText(quantity);
                                supplierNameET.setText(sName);
                                supplierAddressET.setText(sAdd);
                                supplierContactET.setText(sContact);
                                supplierEmailET.setText(sEmail);
                            }
//                            else {
//                                Toast.makeText(DisplayMaterialInformationUser.this, "Select item", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
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
    }
}