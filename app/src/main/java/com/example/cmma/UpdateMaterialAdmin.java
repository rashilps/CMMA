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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateMaterialAdmin extends AppCompatActivity {
    ArrayList<String> material;
    Spinner spinner;
    DatabaseReference dr;
    Button updateMaterial,generateValues;
    ArrayAdapter<String> adapter;
    TextView materialNameTV;
    EditText materialDescriptionET,currentQuantityET,supplierNameET,supplierAddressET,supplierContactET,supplierEmailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_material_admin);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Material");
        spinner = findViewById(R.id.spin);
        materialNameTV = findViewById(R.id.materialname);
        materialDescriptionET = findViewById(R.id.materialdescription);
        currentQuantityET = findViewById(R.id.currentquantity);
        supplierNameET = findViewById(R.id.suppliername);
        supplierAddressET = findViewById(R.id.supplieraddress);
        supplierContactET = findViewById(R.id.suppliercontact);
        supplierEmailET = findViewById(R.id.supplieremail);
        updateMaterial = findViewById(R.id.updateButton);
        material = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        generateValues = findViewById(R.id.generateValues);


        generateValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();

                dr.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                material.clear();
//                material.add("Select item");
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            MaterialPojo mp = ds.getValue(MaterialPojo.class);
                            String name = mp.getMaterialName();
////                    System.out.println(name);
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
                            } else {
                                Toast.makeText(UpdateMaterialAdmin.this, "Select item", Toast.LENGTH_SHORT).show();
                            }
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
                System.out.println(material.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        updateMaterial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Toast.makeText(UpdateMaterialAdmin.this, "Updated", Toast.LENGTH_SHORT).show();
                String materialName = materialNameTV.getText().toString();
                String materialDescription = materialDescriptionET.getText().toString();
                String currentQuantity = currentQuantityET.getText().toString();
                String supplierName = supplierNameET.getText().toString();
                String supplierAddress = supplierAddressET.getText().toString();
                String supplierContact = supplierContactET.getText().toString();
                String supplierEmail = supplierEmailET.getText().toString();
                MaterialPojo mat = new MaterialPojo(materialName,materialDescription,currentQuantity,supplierName,supplierAddress,supplierContact,supplierEmail);
                if (!materialDescription.isEmpty() && !currentQuantity.isEmpty() && !supplierName.isEmpty()
                && !supplierAddress.isEmpty() && !supplierContact.isEmpty() && !supplierEmail.isEmpty()){
                    dr.child(materialName).setValue(mat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                        finish();
                            materialNameTV.setText("");
                            materialDescriptionET.setText("");
                            currentQuantityET.setText("");
                            supplierNameET.setText("");
                            supplierAddressET.setText("");
                            supplierContactET.setText("");
                            supplierEmailET.setText("");

                        }
                    });
                }
                else {
                    Toast.makeText(UpdateMaterialAdmin.this, "Please select an item from the dropdown", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}