package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.takwiratn.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard_userActivity extends AppCompatActivity {


    private FirebaseUser user;
    private DatabaseReference reff;
    private String UserId;
    private CircleImageView profilimage;
    private  TextView  username;

    ImageSlider mainslider;
    FirebaseAuth fauth;
    StorageReference storagereff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        username = (TextView) findViewById(R.id.txt_username);
        profilimage = findViewById(R.id.profilimagedashboad);

        // to get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();



        // for image slider
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainslider=(ImageSlider)findViewById(R.id.image_slider);
        final List<SlideModel> remoteimages=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                            remoteimages.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(), ScaleTypes.FIT));

                        mainslider.setImageList(remoteimages,ScaleTypes.FIT);
                        mainslider.startSliding(3000);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Dashboard_userActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                    }
                });


        reff.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofil = snapshot.getValue(User.class);
                if(userprofil != null){
                    String name = userprofil.fullname;
                    username.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Dashboard_userActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        });

        // To get image profile from firebase
        fauth = FirebaseAuth.getInstance();
        storagereff = FirebaseStorage.getInstance().getReference();
        StorageReference profilreff = storagereff.child("Users/"+fauth.getCurrentUser().getUid()+"/profile.jpg");
        profilreff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilimage);
            }
        });

    }

    public void Logout(View v){
        FirebaseAuth.getInstance().signOut();
        //finish();
        Intent logoutt = new Intent(Dashboard_userActivity.this,MainActivity.class);
        startActivity(logoutt);

    }

    public void BtnGoToProfil(View v){
        Intent gotoprofil = new Intent(Dashboard_userActivity.this,ProfilUserActivity.class);
        startActivity(gotoprofil);
    }

    public void BtnGoToStadium(View v){
        //finish();
        Intent gotostade = new Intent(Dashboard_userActivity.this,Stadium_UserActivity.class);
        startActivity(gotostade);

    }

    public void BtnGoToHistory(View v){
        //finish();
        Intent history = new Intent(Dashboard_userActivity.this,History_orderUser.class);
        startActivity(history);

    }
}