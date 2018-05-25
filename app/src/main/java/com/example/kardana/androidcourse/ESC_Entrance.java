package com.example.kardana.androidcourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

public class ESC_Entrance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esc__entrance);
    }

    // This function opens PopUp window of Login activity
    public void clickLogin (View view){
        Intent login_intent = new Intent(this, LoginActivity.class);
        PopupWindow window = new PopupWindow(view, 400, 400, true);
    }

    public void clickSignIn (View view) {
        Intent signin_intent = new Intent(this, AddNewMember.class);
        startActivity(signin_intent);
    }
}
