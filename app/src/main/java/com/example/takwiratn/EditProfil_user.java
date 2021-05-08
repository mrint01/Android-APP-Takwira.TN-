package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfil_user extends AppCompatActivity {

    EditText usernameedit, fullnameedit, emailedit, phoneedit, passedit;
    FirebaseAuth fauth;
    FirebaseUser user;
    DatabaseReference reff;
    CircleImageView profilimage;
    StorageReference storagereff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_user);
        Intent data = getIntent();
        String username = data.getStringExtra("username");
        String fullname = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        usernameedit = findViewById(R.id.profilusernameedituser);
        fullnameedit = findViewById(R.id.id_usernameeditprofil);
        emailedit = findViewById(R.id.id_emaileditprofil);
        phoneedit = findViewById(R.id.id_phoneeditprofil);
        passedit = findViewById(R.id.id_passeditprofil);
        profilimage = findViewById(R.id.profileditimguser);
        // set info to form
        usernameedit.setText(username);
        fullnameedit.setText(fullname);
        emailedit.setText(email);
        phoneedit.setText(phone);

        // connect to DataBase to get data
        fauth = FirebaseAuth.getInstance();
        user = fauth.getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");

        // To get image from firebase
        storagereff = FirebaseStorage.getInstance().getReference();
        StorageReference profilreff = storagereff.child("Users/"+fauth.getCurrentUser().getUid()+"/profile.jpg");
        profilreff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilimage);
            }
        });
    }

    public void ConfirmEditProfil(View v) {
        if (usernameedit.getText().toString().isEmpty() || fullnameedit.getText().toString().isEmpty() || emailedit.getText().toString().isEmpty() || phoneedit.getText().toString().isEmpty()) {
            Toast.makeText(EditProfil_user.this, "Erreur", Toast.LENGTH_LONG).show();
            return;
        }

        user.updateEmail(emailedit.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reff.child(user.getUid()).child("username").setValue(usernameedit.getText().toString());
                reff.child(user.getUid()).child("fullname").setValue(fullnameedit.getText().toString());
                reff.child(user.getUid()).child("email").setValue(emailedit.getText().toString());
                reff.child(user.getUid()).child("phone").setValue(phoneedit.getText().toString());
                if (!passedit.getText().toString().equals("")) {
                    reff.child(user.getUid()).child("password").setValue(passedit.getText().toString());
                }

                Toast.makeText(EditProfil_user.this, "Mise a jour terminer", Toast.LENGTH_LONG).show();
                if (!passedit.getText().toString().equals("")) {
                    Toast.makeText(EditProfil_user.this, "Mot de passe changé , Déconnection...", Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent logouttt = new Intent(EditProfil_user.this, MainActivity.class);
                    startActivity(logouttt);
                } else {
                    Intent okok = new Intent(EditProfil_user.this, ProfilUserActivity.class);
                    startActivity(okok);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfil_user.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void TxtGoBackToProfil(View v) {
        Intent goback = new Intent(EditProfil_user.this, ProfilUserActivity.class);
        startActivity(goback);
        //finish();
    }


    public void ChangePhotoProfil(View v) {
        // to load image from gallory
        Intent opengalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(opengalery, 1000);
    }

    // To check if image are selected from gallory
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageuri = data.getData();
                UploadimgtoFirebase(imageuri);
            }
        }
    }

    // upload image to firebase
    private void UploadimgtoFirebase(Uri imguri) {
        final StorageReference filereff = storagereff.child("Users/"+fauth.getCurrentUser().getUid()+"/profile.jpg");
        filereff.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filereff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilimage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfil_user.this,"Image Non téléchargée ",Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(EditProfil_user.this,"Image téléchargée ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(EditProfil_user.this,"Errer de téléchargée d'image",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
