package com.example.takwiratn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takwiratn.models.Stade;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class List_StadiumUserActivity extends AppCompatActivity {

    private TextView title;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter_user myAdapterUser;
    ArrayList<Stade> list;
    String type,ratingstar;
    RatingBar rbStars;
    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__stadium_user);

        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Stade");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent dataa = getIntent();
        type = dataa.getStringExtra("title");
        list = new ArrayList<>();
        myAdapterUser = new MyAdapter_user(this,list);
        recyclerView.setAdapter(myAdapterUser);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Stade stade = dataSnapshot.getValue(Stade.class);

                    if (type.trim().equalsIgnoreCase("Les Stades de Tunis") && stade.getVille().trim().equalsIgnoreCase("tunis")) {
                        list.add(stade);
                    }
                    if (type.trim().equalsIgnoreCase("Les Stades de Ariana") && stade.getVille().trim().equalsIgnoreCase("Ariana")) {
                        list.add(stade);
                    }
                    if (type.trim().equalsIgnoreCase("Les Stades de Ben Arous") && stade.getVille().trim().equalsIgnoreCase("Ben Arous")) {
                        list.add(stade);
                    }
                    if (type.trim().equalsIgnoreCase("Les Stade de Mannouba") && stade.getVille().trim().equalsIgnoreCase("Mannouba")) {
                        list.add(stade);
                    }
                    myAdapterUser.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void BtnGoBackToListtype(View v){
        Intent goback = new Intent(List_StadiumUserActivity.this, Stadium_UserActivity.class);
        startActivity(goback);
        //finish();
    }


}
