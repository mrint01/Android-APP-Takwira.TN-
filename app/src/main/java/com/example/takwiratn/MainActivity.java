package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.takwiratn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private ProgressBar progresbar;
    private Button btn_log;
    private FirebaseUser user;
    private DatabaseReference reff;
    private String UserId;
    public String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.email =  (EditText) findViewById(R.id.id_user);
        this.password = (EditText) findViewById(R.id.id_pass);
        this.progresbar = (ProgressBar) findViewById(R.id.progressBar);
        this.btn_log = (Button) findViewById(R.id.id_btn_log);



    }

    public void BtnLoginAction(View v){
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        // Validation of form
        if(em.matches("")){
            email.setError("Vous n'avez pas entré votre email");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            email.setError("Vous n'avez pas entré valid email");
            email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Vous n'avez pas entré de Mot de passe");
            password.requestFocus();
            return;
        }

        // Conx to DataBase & Authentification
        mAuth = FirebaseAuth.getInstance();
        progresbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reff = FirebaseDatabase.getInstance().getReference("Users");
                    UserId = user.getUid();
                    reff.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userprofil = snapshot.getValue(User.class);
                            if (userprofil != null) {
                                type = userprofil.getType();
                                if(type.equalsIgnoreCase("Compte Personnel")){
                                    Intent in = new Intent(MainActivity.this, Dashboard_userActivity.class);
                                    startActivity(in);
                                    //finish();
                                    progresbar.setVisibility(View.GONE);
                                }else if(type.equalsIgnoreCase("Compte Professionnel")){
                                    Toast.makeText(MainActivity.this,"Directeur Account" , Toast.LENGTH_LONG).show();
                                    progresbar.setVisibility(View.GONE);

                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this, "Identifiant invalide", Toast.LENGTH_SHORT).show();
                    email.setError("Revérifier");
                    password.setError("Revérifier");
                    email.requestFocus();
                    progresbar.setVisibility(View.GONE);
                }
            }


        });




    }

    public void TxtGoToInscriAction(View v){

        // to go to next activity
        Intent intent = new Intent(this, InscriActivity.class);
        startActivity(intent);
        //finish();
    }

    public void TxtGoToForgetPassword(View v){
        Intent forget = new Intent(MainActivity.this, ForgetPassword_user.class);
        startActivity(forget);
        //finish();

    }


}
