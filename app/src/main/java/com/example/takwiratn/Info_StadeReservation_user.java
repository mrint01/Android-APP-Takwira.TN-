package com.example.takwiratn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Info_StadeReservation_user extends AppCompatActivity {

    TextView stadename,adrstade,villestade,eclstade,vest,hours,type,prix;
    String sata="";
    ImageView imagestadeshow;
    Context context;
    String im,id_directeur;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info__stadereservation_user);

        stadename = findViewById(R.id.id_stadename);
        adrstade  = findViewById(R.id.id_addresse_stade);
        villestade = findViewById(R.id.id_villestade);
        eclstade = findViewById(R.id.id_eclairage_stade);
        vest = findViewById(R.id.id_vestiare_stade);
        hours = findViewById(R.id.id_h_oc);
        type = findViewById(R.id.id_tapis);
        prix = findViewById(R.id.id_prixstade);
        imagestadeshow = findViewById(R.id.imagestadeshow);


        Intent data = getIntent();
        im = data.getStringExtra("image_stade");
        Glide.with(imagestadeshow.getContext()).load(im).into(imagestadeshow);
        stadename.setText(data.getStringExtra("stade_name"));
        adrstade.setText(data.getStringExtra("address"));
        vest.setText(data.getStringExtra("vest_stade"));
        villestade.setText(data.getStringExtra("ville"));
        eclstade.setText(data.getStringExtra("eclairage"));
        hours.setText(data.getStringExtra("h_open") + " - " + data.getStringExtra("h_close"));
        type.setText(data.getStringExtra("type_tapis"));
        prix.setText(data.getStringExtra("prix_stade"));
        id_directeur = data.getStringExtra("id_directeur");
    }
    public void BtnOrderNow(View v){

        Intent intent = new Intent(v.getContext(), Details_orderInfo_user.class);
        intent.putExtra("image_stade",im);
        intent.putExtra("stade_name",stadename.getText().toString());
        intent.putExtra("address",adrstade.getText().toString());
        intent.putExtra("prix_stade",prix.getText().toString());
        intent.putExtra("id_directeur",id_directeur);
        v.getContext().startActivity(intent);

    }

    public void TxtGoBackTostades(View v){
        /*Intent goback = new Intent(Info_StadeReservation_user.this,List_StadiumUserActivity.class);
        startActivity(goback);*/
        finish();

    }
}