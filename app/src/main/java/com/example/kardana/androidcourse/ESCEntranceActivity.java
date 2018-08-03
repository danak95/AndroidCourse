package com.example.kardana.androidcourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.User;

public class ESCEntranceActivity extends AppCompatActivity {

    public ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esc__entrance);
        progress = this.findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onResume()
    {
        super.onResume();

        ((AutoCompleteTextView) this.findViewById(R.id.email_field_login)).setText("");
        ((EditText) this.findViewById(R.id.password_field_login)).setText("");
    }

    // This function handles login of existing user
    public void clickLogin (View view){
        String email = ((AutoCompleteTextView) this.findViewById(R.id.email_field_login)).getText().toString();
        String password = ((EditText) this.findViewById(R.id.password_field_login)).getText().toString();
        Model.getInstance().userLogin(email, password, new Model.IGetUserLoginCallback() {
            @Override
            public void onComplete(User user) {
                // Check if the user exists in the Firebase
                if (user != null)
                {
                    progress.setVisibility(View.VISIBLE);
                    Intent main_intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(main_intent);
                }
                else
                {
                    Toast.makeText(ESCEntranceActivity.this, "Invalid parameters for login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // This function opens the Add new member activity
    public void clickSignIn (View view) {
        Intent signin_intent = new Intent(this, AddNewMemberActivity.class);
        startActivity(signin_intent);
    }
}
