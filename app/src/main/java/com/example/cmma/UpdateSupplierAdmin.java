package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateSupplierAdmin extends AppCompatActivity {
//    ArrayList<String> material;
//    Spinner spinnerMaterial;
//    ArrayAdapter<String> adapter;

    ArrayList<String> supplierList;
    Spinner spinnerSupplier;
    ArrayAdapter<String> adapterSupplier;
    DatabaseReference dr;
    DatabaseReference drSupplier;
    Button updateSupplier,generateValuesSupplier;

    TextView materialNameTV,supplierNameTV;
    EditText supplierAddressET,supplierContactET,supplierEmailET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier_admin);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Material");
        drSupplier = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Supplier");
        supplierNameTV = findViewById(R.id.suppliername);
        supplierAddressET = findViewById(R.id.supplieraddress);
        supplierContactET = findViewById(R.id.suppliercontact);
        supplierEmailET = findViewById(R.id.supplieremail);
        materialNameTV = findViewById(R.id.materialname);
        updateSupplier = findViewById(R.id.updateButton);
//        spinnerMaterial = findViewById(R.id.spin);
        spinnerSupplier = findViewById(R.id.spinSupplier);

//        material = new ArrayList<String>();
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
//        adapter.setDropDownViewResource(R.layout.spinner_item);

        supplierList = new ArrayList<String>();
        adapterSupplier = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, supplierList);
        adapterSupplier.setDropDownViewResource(R.layout.spinner_item);
        generateValuesSupplier = findViewById(R.id.generateValuesSupplier);

//        spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                materialNameTV.setText(spinnerMaterial.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        generateValuesSupplier.setOnClickListener(new View.OnClickListener() {
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
                                materialNameTV.setText(materialName);
                                supplierNameTV.setText(sName);
                                supplierAddressET.setText(sAdd);
                                supplierContactET.setText(sContact);
                                supplierEmailET.setText(sEmail);
                            } else {
                                Toast.makeText(UpdateSupplierAdmin.this, "Select item", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

//        dr.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                material.clear();
//                material.add("Select item");
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    MaterialPojo mp = ds.getValue(MaterialPojo.class);
//                    String name = mp.getMaterialName();
//                    material.add(name);
//                    spinnerMaterial.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
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

        updateSupplier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Toast.makeText(UpdateMaterialAdmin.this, "Updated", Toast.LENGTH_SHORT).show();
                String materialName = materialNameTV.getText().toString();
                String supplierName = supplierNameTV.getText().toString();
                String supplierAddress = supplierAddressET.getText().toString();
                String supplierContact = supplierContactET.getText().toString();
                String supplierEmail = supplierEmailET.getText().toString();
                SupplierPojo mat = new SupplierPojo(materialName,supplierName,supplierAddress,supplierContact,supplierEmail);
                if (!supplierName.isEmpty() && !supplierAddress.isEmpty() && !supplierContact.isEmpty() && !supplierEmail.isEmpty()){
                    drSupplier.child(supplierName).setValue(mat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                        finish();
                            materialNameTV.setText("");
                            supplierNameTV.setText("");
                            supplierAddressET.setText("");
                            supplierContactET.setText("");
                            supplierEmailET.setText("");

                        }
                    });
                }
                else {
                    Toast.makeText(UpdateSupplierAdmin.this, "Please select an item from the dropdown", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}