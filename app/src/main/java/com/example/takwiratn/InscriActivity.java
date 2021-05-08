package com.example.takwiratn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takwiratn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class InscriActivity   extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fname;
    private EditText email;
    private EditText phone;
    private EditText user;
    private EditText pass;
    private RadioButton R1;
    private RadioButton R2;
    private ProgressBar progresbar;
    private Button btninscri;
    public String username,password,fullname,emaill,phonee,TypeAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscri);

        this.fname = (EditText) findViewById(R.id.id_fullname);
        this.email = (EditText) findViewById(R.id.id_emailinscri);
        this.phone = (EditText) findViewById(R.id.id_phoneinscri);
        this.user = (EditText) findViewById(R.id.id_userinscri);
        this.pass = (EditText) findViewById(R.id.id_passinscri);
        this.R1 = (RadioButton) findViewById(R.id.id_radioPersonnel);
        this.R2 = (RadioButton) findViewById(R.id.id_radioProfessionnel);
        this.progresbar = (ProgressBar) findViewById(R.id.progressBar);
        this.btninscri = (Button) findViewById(R.id.id_btninscri);

        mAuth = FirebaseAuth.getInstance();

    }

    public void BtnInscriAction(View v){


        username = user.getText().toString().trim();
        password = pass.getText().toString().trim();
        emaill = email.getText().toString().trim();
        phonee = phone.getText().toString().trim();
        fullname = fname.getText().toString().trim();

        // Validation of form
        if(fullname.isEmpty()){
            fname.setError("Enter votre nom et prénom");
            fname.requestFocus();
            return;
        }
        if(emaill.isEmpty()){
            email.setError("Enter votre email");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emaill).matches() ){
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }
        if(phonee.isEmpty()){
            phone.setError("Enter votre numéro de tééphone");
            phone.requestFocus();
            return;
        }
        if(username.isEmpty()){
            user.setError("Enter votre nom d`utilisateur");
            user.requestFocus();
            return;

        }
        if(password.isEmpty()){
            pass.setError("Enter votre mot de passe");
            pass.requestFocus();
            return;
        }
        if(R1.isChecked()){
            TypeAccount = R1.getText().toString();
        }else if(R2.isChecked()){
            TypeAccount = R2.getText().toString();
        }
        if(R1.isChecked() != true && R2.isChecked() != true){
            R1.setError("Choisir le type de compte");
            R2.setError("Choisir le type de compte");
            return;
        }

        // conx to DataBase & insert Data
        progresbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emaill , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User us = new User(fullname,emaill,username,password,phonee,TypeAccount);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(us).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(InscriActivity.this, "Inscription complète", Toast.LENGTH_SHORT).show();
                                        progresbar.setVisibility(View.GONE);
                                        Intent i = new Intent(InscriActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(InscriActivity.this, "n'a pas réussi à s'inscrire", Toast.LENGTH_SHORT).show();
                                        progresbar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(InscriActivity.this, "Compte existe déjà", Toast.LENGTH_SHORT).show();
                            progresbar.setVisibility(View.GONE);
                        }
                    }
                });

    }
    public void TxtReturnToLoginAction(View v){

        // to go to next activity
        finish();
        Intent i = new Intent(InscriActivity.this, MainActivity.class);
        startActivity(i);



    }
}
