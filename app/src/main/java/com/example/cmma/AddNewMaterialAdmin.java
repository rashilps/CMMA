package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewMaterialAdmin extends AppCompatActivity {
    EditText materialNameET,materialDescriptionET,currentQuantityET,supplierNameET,supplierAddressET,supplierContactET,supplierEmailET;
    Button add;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_material_admin);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("addNewMaterial");
        materialNameET = findViewById(R.id.materialname);
        materialDescriptionET = findViewById(R.id.materialdescription);
        currentQuantityET = findViewById(R.id.currentquantity);
        supplierNameET = findViewById(R.id.suppliername);
        supplierAddressET = findViewById(R.id.supplieraddress);
        supplierContactET = findViewById(R.id.suppliercontact);
        supplierEmailET = findViewById(R.id.supplieremail);
        add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(AddNewMaterialAdmin.this, "Clicked", Toast.LENGTH_SHORT).show();
                String materialName = materialNameET.getText().toString();
                String materialDescription = materialDescriptionET.getText().toString();
                String currentQuantity = currentQuantityET.getText().toString();
                String supplierName = supplierNameET.getText().toString();
                String supplierAddress = supplierAddressET.getText().toString();
                String supplierContact = supplierContactET.getText().toString();
                String supplierEmail = supplierEmailET.getText().toString();
                AddNewMaterialPojo mat = new AddNewMaterialPojo(materialName,materialDescription,currentQuantity,supplierName,supplierAddress,supplierContact,supplierEmail);
                dr.child(materialName).setValue(mat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        materialNameET.setText("");
                        materialDescriptionET.setText("");
                        currentQuantityET.setText("");
                        supplierNameET.setText("");
                        supplierAddressET.setText("");
                        supplierContactET.setText("");
                        supplierEmailET.setText("");
//                        Toast.makeText(AddNewMaterialAdmin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}