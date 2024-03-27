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

public class SupplierContactInformationUser extends AppCompatActivity {
    ArrayList<String> supplierList;
    Spinner spinnerSupplier;
    ArrayAdapter<String> adapterSupplier;
    DatabaseReference dr;
    DatabaseReference drSupplier;
    Button updateSupplier;

    TextView supplierNameTV;
    TextView supplierAddressET,supplierContactET,supplierEmailET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_contact_information_user);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Material");
        drSupplier = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Supplier");
        supplierNameTV = findViewById(R.id.suppliername2);
        supplierAddressET = findViewById(R.id.supplieraddress2);
        supplierContactET = findViewById(R.id.suppliercontact2);
        supplierEmailET = findViewById(R.id.supplieremail2);
        updateSupplier = findViewById(R.id.generateButton);
        spinnerSupplier = findViewById(R.id.spinSupplier);

        supplierList = new ArrayList<String>();
        adapterSupplier = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, supplierList);
        adapterSupplier.setDropDownViewResource(R.layout.spinner_item);
        updateSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinnerSupplier.getSelectedItem().toString();

                drSupplier.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                material.clear();
//                material.add("Select item");
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            SupplierPojo mp = ds.getValue(SupplierPojo.class);
                            String name = mp.getSupplierName();
////                    System.out.println(name);
//                            material.add(name);
//                            spinner.setAdapter(adapter);


                            if (name.equals(item)) {
                                String sName = mp.getSupplierName();
                                String sAdd = mp.getSupplierAddress();
                                String sContact = mp.getSupplierContact();
                                String sEmail = mp.getSupplierEmail();
                                String materialName = mp.getMaterialName();
                                supplierNameTV.setText(sName);
                                supplierAddressET.setText(sAdd);
                                supplierContactET.setText(sContact);
                                supplierEmailET.setText(sEmail);
                            } else {
                                Toast.makeText(SupplierContactInformationUser.this, "Select item", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        drSupplier.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                supplierList.clear();
                supplierList.add("Select item");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SupplierPojo mp = ds.getValue(SupplierPojo.class);
                    String name = mp.getSupplierName();
                    supplierList.add(name);
                    spinnerSupplier.setAdapter(adapterSupplier);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}