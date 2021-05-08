package com.example.takwiratn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Finish_OrderUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish__order_user);
    }

    public void BtnGoToHistoryVerif(View v){
        Intent gotohistory = new Intent(Finish_OrderUser.this,History_orderUser.class);
        startActivity(gotohistory);
        //finish();
    }
}