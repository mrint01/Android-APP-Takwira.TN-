package com.example.takwiratn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Stadium_UserActivity extends AppCompatActivity {

    private TextView title1,title2,title3,title4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium__user);
        title1 = findViewById(R.id.txtstadetunis);
        title2 = findViewById(R.id.txtstadeariana);
        title3 = findViewById(R.id.txtstadebenarous);
        title4 = findViewById(R.id.txtstademanouba);
    }

    public void BtnGoToStadeTunis(View v){
        Intent tunis = new Intent(Stadium_UserActivity.this,List_StadiumUserActivity.class);
        tunis.putExtra("title",title1.getText().toString().trim());
        startActivity(tunis);
        //finish();

    }

    public void BtnGoToStadeAriana(View v){

        Intent ariana = new Intent(Stadium_UserActivity.this,List_StadiumUserActivity.class);
        ariana.putExtra("title",title2.getText().toString().trim());
        startActivity(ariana);
        //finish();
    }

    public void BtnGoToStadeBenArouss(View v){

        Intent arous = new Intent(Stadium_UserActivity.this,List_StadiumUserActivity.class);
        arous.putExtra("title",title3.getText().toString().trim());
        startActivity(arous);
        //finish();
    }

    public void BtnGoToStadeManouba(View v){
        Intent manouba = new Intent(Stadium_UserActivity.this,List_StadiumUserActivity.class);
        manouba.putExtra("title",title4.getText().toString().trim());
        startActivity(manouba);
        //finish();
    }

    public void BtnGoBackToDashboad(View v){
        Intent goback = new Intent(Stadium_UserActivity.this, Dashboard_userActivity.class);
        startActivity(goback);
        //finish();
    }

}