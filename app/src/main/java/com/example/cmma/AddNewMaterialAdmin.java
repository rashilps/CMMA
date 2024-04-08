package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setLogo(R.drawable.cmma);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.cmma));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.custom_image_icon,null);
//        actionBar.setCustomView(view);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Material");
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


//                Toast.makeText(AddNewMaterialAdmin.this, "Clicked", Toast.LENGTH_SHORT).show();
                String materialName = materialNameET.getText().toString();
                String materialDescription = materialDescriptionET.getText().toString();
                String currentQuantity = currentQuantityET.getText().toString();
                String supplierName = supplierNameET.getText().toString();
                String supplierAddress = supplierAddressET.getText().toString();
                String supplierContact = supplierContactET.getText().toString();
                String supplierEmail = supplierEmailET.getText().toString();
                MaterialPojo mat = new MaterialPojo(materialName, materialDescription, currentQuantity, supplierName, supplierAddress, supplierContact, supplierEmail);
                if (!materialDescription.isEmpty() && !currentQuantity.isEmpty() && !supplierName.isEmpty()
                        && !supplierAddress.isEmpty() && !supplierContact.isEmpty() && !supplierEmail.isEmpty()) {
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
                        }
                    });
                }
                else {
                    Toast.makeText(AddNewMaterialAdmin.this, "Please select an item from the dropdown", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}