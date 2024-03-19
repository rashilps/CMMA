package com.example.cmma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteSupplier extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> supplierList;
    Spinner spinner;
    DatabaseReference dr;
    Button deleteSupplier;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_supplier);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Supplier");
        spinner = findViewById(R.id.spin);
////        spinner.setOnItemSelectedListener(this);
        deleteSupplier = findViewById(R.id.deletesupplierbutton);
        supplierList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, supplierList);
        adapter.setDropDownViewResource(R.layout.spinner_item);


        dr.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                supplierList.clear();
                supplierList.add("Select item");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SupplierPojo mp = ds.getValue(SupplierPojo.class);
//                    System.out.println(mp);
                    String name = mp.getSupplierName();
//                    System.out.println(name);
                    supplierList.add(name);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//
//
        deleteSupplier.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == deleteSupplier) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            String itemSelected = spinner.getSelectedItem().toString();
            FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Supplier").child(itemSelected).removeValue();
            spinner.setAdapter(null);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}