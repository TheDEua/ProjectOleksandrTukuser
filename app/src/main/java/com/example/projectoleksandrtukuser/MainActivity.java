package com.example.projectoleksandrtukuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectoleksandrtukuser.adapter.Adapter;
import com.example.projectoleksandrtukuser.models.Models;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Adapter adapter;
    private DatabaseReference reference;
    EditText number;
    private RecyclerView numbersList;
    private ArrayList<Models> listModels = new ArrayList<>();

    Button btn, btnRnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numbersList = (RecyclerView) findViewById(R.id.recyclerList);
        btn = (Button) findViewById(R.id.button);
        btnRnd = (Button) findViewById(R.id.buttonRnd);
        number = (EditText) findViewById(R.id.editTextNumber);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        numbersList.setLayoutManager(new LinearLayoutManager(this));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberTxt = number.getText().toString();
                if (numberTxt.isEmpty()){
                   Toast.makeText(getApplicationContext(), R.string.spaces, Toast.LENGTH_LONG).show();
                }
                else {
                    String chose = "You chose " + numberTxt;
                    reference.child("numbers").push().child("number").setValue(chose);
                    Toast.makeText(getApplicationContext(), "You pressed " + numberTxt, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Facts.class);
                    i.putExtra("cat", numberTxt);
                    startActivity(i);
                }

            }
        });
        btnRnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String random = "random/math";
                String chose = "You chose " + random;
                reference.child("numbers").push().child("number").setValue(chose);
                Intent i = new Intent(getApplicationContext(), Facts.class);
                i.putExtra("cat", random);
                Toast.makeText(getApplicationContext(), R.string.pressed_random, Toast.LENGTH_LONG).show();
                startActivity(i);


            }
        });
        getNumbersFromFirebase();

    }
    private void getNumbersFromFirebase(){
        reference.child("numbers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds:
                            snapshot.getChildren()) {
                        String number = ds.child("number").getValue().toString();
                        listModels.add(new Models(number));
                    }
                    adapter = new Adapter(listModels, R.layout.constructor_number);
                    numbersList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}