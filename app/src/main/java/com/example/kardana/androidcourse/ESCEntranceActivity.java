package com.example.kardana.androidcourse;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.RoomsViewModel;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.Model.UserViewModel;

import java.util.List;

public class ESCEntranceActivity extends AppCompatActivity {

    private ProgressBar progress;
    private boolean isViewLoaded = false;
    private User currUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserViewModel dataModel = ViewModelProviders.of(this).get(UserViewModel.class);
        dataModel.getData().observe(this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(@Nullable List<User> users) {
                        String userid = Model.getInstance().getCurrentUserId();

                        for (User user :  users)
                        {
                            if (user.getUserid().equals(userid))
                            {
                                currUser = user;
                                break;
                            }
                        }

                        if (currUser != null)
                        {
                            callMainActivity();
                        }
                        else
                        {
                            loadview();
                        }
                    }
                });
//        Model.getInstance().getCurrentUser(new Model.IGetCurrentUserCallback() {
//            @Override
//            public void onComplete(User user)
//            {
//                if (user != null)
//                {
//                    callMainActivity();
//                }
//                else
//                {
//                    loadview();
//                }
//            }
//        });
    }

    private void loadview(){
        setContentView(R.layout.activity_esc__entrance);
        isViewLoaded = true;
        progress = this.findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (isViewLoaded) {
            ((AutoCompleteTextView) this.findViewById(R.id.email_field_login)).setText("");
            ((EditText) this.findViewById(R.id.password_field_login)).setText("");
        }
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
                    callMainActivity();
                }
                else
                {
                    Toast.makeText(ESCEntranceActivity.this, "Invalid parameters for login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callMainActivity()
    {
        Intent main_intent = new Intent(getBaseContext(), MainActivity.class);
        main_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main_intent);
        overridePendingTransition(0, 0);
        finish();
    }

    // This function opens the Add new member activity
    public void clickSignIn (View view) {
        Intent signin_intent = new Intent(this, AddNewMemberActivity.class);
        startActivity(signin_intent);
    }
}
