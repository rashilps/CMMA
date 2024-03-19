package com.example.cmma;

import static java.lang.Short.compare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class DeleteMaterial extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> material;
    Spinner spinner;
    DatabaseReference dr;
    Button deleteMaterial;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_material);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        dr = FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Material");
        spinner = findViewById(R.id.spin);
////        spinner.setOnItemSelectedListener(this);
        deleteMaterial = findViewById(R.id.deletematerialbutton);
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
//
//
        deleteMaterial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == deleteMaterial) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            String itemSelected = spinner.getSelectedItem().toString();
//            System.out.println(dr.child("Material").child(itemSelected).getRef());
            FirebaseDatabase.getInstance("https://cmma-441c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Material").child(itemSelected).removeValue();
            spinner.setAdapter(null);
            adapter.notifyDataSetChanged();

        }
    }
}
