package com.example.kardana.androidcourse;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ESC_Entrance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esc__entrance);
    }

    // This function opens Dialog window of Login activity
    public void clickLogin (View view){
        DialogFragment newFragment = new LoginDialog();
        newFragment.show(getSupportFragmentManager(),"TAG");
    }

    // This function opens the Add new member activity
    public void clickSignIn (View view) {
        Intent signin_intent = new Intent(this, AddNewMember.class);
        startActivity(signin_intent);
    }
}
