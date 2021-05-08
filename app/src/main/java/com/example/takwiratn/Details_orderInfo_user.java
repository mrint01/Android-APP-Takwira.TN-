package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.example.takwiratn.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class Details_orderInfo_user extends AppCompatActivity {

    private ImageView imagestadeshow,imgclicked;
    private TextView id_phonedirecteur,id_directeurname,id_prixstade,id_pricetotal;
    private EditText id_nbrperson,date_time_input;
    private FirebaseUser user;
    private DatabaseReference reff;
    String id_directeur,im,t,id_stadename;
    int total,nbr,priceone;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order_info_user);

        imagestadeshow = findViewById(R.id.imagestadeshow);
        id_directeurname =findViewById(R.id.id_stadename);
        id_phonedirecteur = findViewById(R.id.id_addresse_stade);
        id_prixstade = findViewById(R.id.id_prixstade);
        id_pricetotal = findViewById(R.id.id_pricetotal);
        id_nbrperson = findViewById(R.id.id_nbrperson);
        imgclicked = findViewById(R.id.imgclicked);
        date_time_input = findViewById(R.id.date_time_input);
        date_time_input.setInputType(InputType.TYPE_NULL);

        Intent data = getIntent();
        im = data.getStringExtra("image_stade");
        Glide.with(imagestadeshow.getContext()).load(im).into(imagestadeshow);

        id_stadename = data.getStringExtra("stade_name");
        id_prixstade.setText(data.getStringExtra("prix_stade"));
        id_directeur = data.getStringExtra("id_directeur");
        id_nbrperson.setText("1");
        id_pricetotal.setText(id_prixstade.getText());
        date_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date_time_input);
            }
        });

        // to get the Directeur Data
        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        reff.child(id_directeur).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofil = snapshot.getValue(User.class);
                if(userprofil != null){

                    id_directeurname.setText(userprofil.getFullname());
                    id_phonedirecteur.setText(userprofil.getPhone());
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yy-MM-dd HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(Details_orderInfo_user.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(Details_orderInfo_user.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    public void BtnConfirmOrder(View v){
        BtnVerifprice(null);
        Intent intent = new Intent(v.getContext(), Card_Info_user.class);
        intent.putExtra("stade_name",id_stadename);
        intent.putExtra("prix_total_order",id_pricetotal.getText().toString());
        intent.putExtra("nbr_total_personne",id_nbrperson.getText().toString());
        intent.putExtra("date_order",date_time_input.getText().toString());
        intent.putExtra("id_directeur",id_directeur);
        v.getContext().startActivity(intent);
    }

    public void TxtGoBackTostadeinfo(View v){
        /*Intent goback = new Intent(Details_orderInfo_user.this, Info_StadeReservation_user.class);
        startActivity(goback);*/
        finish();
    }
    @SuppressLint("SetTextI18n")
    public void BtnVerifprice(View v){
        if(id_nbrperson.getText().toString().isEmpty()){
            id_nbrperson.setError("entre votre nombre de joueurs");
            id_nbrperson.requestFocus();
            return;
        }
        nbr = Integer.parseInt(id_nbrperson.getText().toString());
        if(nbr == 0){
            id_nbrperson.setError("Le nombre doit etre <> 0");
            id_nbrperson.requestFocus();
            return;
        }
        priceone = Integer.parseInt(id_prixstade.getText().toString());
        total = nbr * priceone;
        t = String.valueOf(total);
        id_pricetotal.setText(t+ "DT");
    }
}