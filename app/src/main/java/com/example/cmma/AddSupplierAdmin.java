package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddSupplierAdmin extends AppCompatActivity {
    EditText supplierNameET,supplierAddressET,supplierContactET,supplierEmailET;
    Button add;
    DatabaseReference dr;
    DatabaseReference drSupplier;
    ArrayList<String> material;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier_admin);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Material");
        drSupplier = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Supplier");

        supplierNameET = findViewById(R.id.suppliername);
        supplierAddressET = findViewById(R.id.supplieraddress);
        supplierContactET = findViewById(R.id.suppliercontact);
        supplierEmailET = findViewById(R.id.supplieremail);
        add = findViewById(R.id.addButton);
        spinner = findViewById(R.id.spin);
        material = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        adapter.setDropDownViewResource(R.layout.spinner_item);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                Toast.makeText(AddNewMaterialAdmin.this, "Clicked", Toast.LENGTH_SHORT).show();
                String supplierName = supplierNameET.getText().toString();
                String supplierAddress = supplierAddressET.getText().toString();
                String supplierContact = supplierContactET.getText().toString();
                String supplierEmail = supplierEmailET.getText().toString();
                String materialName = spinner.getSelectedItem().toString();
                SupplierPojo mat = new SupplierPojo(materialName, supplierName, supplierAddress, supplierContact, supplierEmail);

                if (!supplierName.isEmpty() && !supplierAddress.isEmpty() && !supplierContact.isEmpty()
                        && !supplierEmail.isEmpty()) {
                    drSupplier.child(supplierName).setValue(mat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            supplierNameET.setText("");
                            supplierAddressET.setText("");
                            supplierContactET.setText("");
                            supplierEmailET.setText("");
                        }
                    });
                }
                else {
                    Toast.makeText(AddSupplierAdmin.this, "Please enter all the information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}