package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilUserActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reff;
    private String UserId;
    TextView username,fullname,email,phone;
    CircleImageView profilimage;
    StorageReference storagereff;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        username = (TextView) findViewById(R.id.profilusernameuser);
        fullname = (TextView) findViewById(R.id.id_usernameprofil);
        email = (TextView) findViewById(R.id.id_emailprofil);
        phone = (TextView) findViewById(R.id.id_phoneprofil);
        profilimage = (CircleImageView) findViewById(R.id.profilimguser);

        // to get the current user data
        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();
        reff.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofil = snapshot.getValue(User.class);
                if(userprofil != null){
                    username.setText(userprofil.getUsername());
                    fullname.setText(userprofil.getFullname());
                    email.setText(userprofil.getEmail());
                    phone.setText(userprofil.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilUserActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
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

    public void Returntodashboard(View v){
        Intent goback = new Intent(ProfilUserActivity.this, Dashboard_userActivity.class);
        startActivity(goback);
        //finish();
    }

    public void TxtGoToUpdateProfil(View v){
        Intent update = new Intent(v.getContext(), EditProfil_user.class);
        update.putExtra("username", username.getText().toString());
        update.putExtra("fullname", fullname.getText().toString());
        update.putExtra("email", email.getText().toString());
        update.putExtra("phone", phone.getText().toString());
        startActivity(update);

    }
}