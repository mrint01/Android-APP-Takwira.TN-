package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.takwiratn.models.Booking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Card_Info_user extends AppCompatActivity {

    CardForm cardForm;
    Button btnBuy;
    AlertDialog.Builder alertBuilder;
    FirebaseAuth fauth;
    String stade_name,prix_total_order,nbr_total_personne,date_order,id_directeur,id_user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__info_user);
        cardForm = (CardForm) findViewById(R.id.card_form);
        btnBuy = findViewById(R.id.btnBuy);
        // get data from last activity
        Intent data = getIntent();
        stade_name = data.getStringExtra("stade_name");
        prix_total_order = data.getStringExtra("prix_total_order");
        nbr_total_personne = data.getStringExtra("nbr_total_personne");
        date_order = data.getStringExtra("date_order");
        id_directeur = data.getStringExtra("id_directeur");
        id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();


        btnBuy.setText("Payez " +data.getStringExtra("prix_total_order"));
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .actionLabel("Purchase")
                .setup(Card_Info_user.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()) {
                    // Dilaog MSG
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Card_Info_user.this);
                    alertBuilder.setTitle("Confirmer avant de réserver");
                    alertBuilder.setMessage("êtes-vous sûr ?");
                    alertBuilder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            // Send info to collection firebase reservation
                            String id_book = FirebaseDatabase.getInstance().getReference().child("Booking").push().getKey();
                            Booking booknow = new Booking(stade_name,prix_total_order,nbr_total_personne,date_order,id_directeur,id_user,id_book);
                            FirebaseDatabase.getInstance().getReference("Booking")
                                    .child(id_book)
                                    .setValue(booknow).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Card_Info_user.this, "Réservation complète", Toast.LENGTH_SHORT).show();
                                        // go to the last activity finish
                                        Intent gotofinish = new Intent(Card_Info_user.this,Finish_OrderUser.class);
                                        startActivity(gotofinish);
                                        finish();
                                    }else{
                                        Toast.makeText(Card_Info_user.this, "n'a pas réussi à s'réserver", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    alertBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                }else {
                    Toast.makeText(Card_Info_user.this, "Veuillez remplir le formulaire", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void BtnAnnuler(View v){
        Intent goback = new Intent(Card_Info_user.this,Dashboard_userActivity.class);
        startActivity(goback);
        //finish();
    }


}