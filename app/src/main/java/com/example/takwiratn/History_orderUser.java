package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.takwiratn.models.Booking;
import com.example.takwiratn.models.Stade;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History_orderUser extends AppCompatActivity {

    private TextView title;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter_HistoryUser myAdapterUser;
    ArrayList<Booking> list2;
    String type;
    ImageView deleteview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_user);

        recyclerView = findViewById(R.id.userList);
        deleteview = findViewById(R.id.deleteview);

        database = FirebaseDatabase.getInstance().getReference("Booking");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        list2 = new ArrayList<>();
        myAdapterUser = new MyAdapter_HistoryUser(this,list2);
        recyclerView.setAdapter(myAdapterUser);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Booking book = dataSnapshot.getValue(Booking.class);
                    if(book.getId_user().equalsIgnoreCase(userid)){
                        list2.add(book);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void BtnGoBackToDash(View v){
        Intent gobacktodash = new Intent(History_orderUser.this,Dashboard_userActivity.class);
        startActivity(gobacktodash);
        //finish();
    }
}