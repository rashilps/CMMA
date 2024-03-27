package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AutomaticEmailGeneration extends AppCompatActivity {
    ArrayList<String> material;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    DatabaseReference dr;
    Button generateAutoEmail, generateValues;
    TextView currentQuantityET,supplierNameET,supplierAddressET,supplierContactET,supplierEmailET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_email_generation);
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Material");
        currentQuantityET = findViewById(R.id.materialquantity2);
        supplierEmailET = findViewById(R.id.supplieremail2);
        supplierNameET = findViewById(R.id.suppliername2);
        supplierAddressET = findViewById(R.id.supplieraddress2);
        supplierContactET = findViewById(R.id.suppliercontact2);
        spinner = findViewById(R.id.spin);
        generateAutoEmail = findViewById(R.id.generateAutoEmailButton);
        generateValues = findViewById(R.id.generateValues);
        material = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, material);
        adapter.setDropDownViewResource(R.layout.spinner_item);
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
                                currentQuantityET.setText(quantity);
                                supplierNameET.setText(sName);
                                supplierAddressET.setText(sAdd);
                                supplierContactET.setText(sContact);
                                supplierEmailET.setText(sEmail);
                            }
//                            else {
//                                Toast.makeText(AutomaticEmailGeneration.this, "Select item", Toast.LENGTH_SHORT).show();
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
                System.out.println(material.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        generateAutoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailsend = supplierEmailET.getText().toString();
//                System.out.println(emailsend);
                StringBuilder stringBuilder = new StringBuilder();
                String quantity = currentQuantityET.getText().toString();
                String sName = supplierNameET.getText().toString();
                String sAdd = supplierAddressET.getText().toString();
                String sContact = supplierContactET.getText().toString();
                stringBuilder.append("Current Material quantity: ").append(quantity).append("\n");
                stringBuilder.append("Supplier name: ").append(sName).append("\n");
                stringBuilder.append("Supplier address: ").append(sAdd).append("\n");
                stringBuilder.append("Supplier contact: ").append(sContact);

                String emailsubject = "Sending email to supplier" + sName;
                String emailbody = stringBuilder.toString();

                // define Intent object with action attribute as ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);

                // add three fields to intent using putExtra function
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailbody);

                // set type of intent
                intent.setType("message/rfc822");

                // startActivity with intent with chooser as Email client using createChooser function
//                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                startActivity(intent);
            }
        });
    }
}