package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword_user extends AppCompatActivity {

    private EditText resetemail;
    private ProgressBar progresbar;
    FirebaseAuth nauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        this.resetemail = (EditText) findViewById(R.id.txt_email_forgetpass);
        this.progresbar = (ProgressBar) findViewById(R.id.progressBar22);
        nauth = FirebaseAuth.getInstance();
    }

    public void resetpassword(View v){

        String ema = resetemail.getText().toString().trim();

        if(ema.isEmpty()){
            resetemail.setError("Entre votre Email");
            resetemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(ema).matches()){
            resetemail.setError("Entre votre valid Email");
            resetemail.requestFocus();
            return;
        }


        progresbar.setVisibility(View.VISIBLE);
        nauth.sendPasswordResetEmail(ema).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword_user.this, "vérifiez votre boite mail pour réinitialiser votre mot de passe", Toast.LENGTH_SHORT).show();
                    progresbar.setVisibility(View.GONE);
                    finish();
                }else{
                    Toast.makeText(ForgetPassword_user.this, "erreur d'envoi d'e-mail", Toast.LENGTH_SHORT).show();
                    progresbar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void BtnGoBack(View v){
        Intent goback = new Intent(ForgetPassword_user.this, MainActivity.class);
        startActivity(goback);
    }
}