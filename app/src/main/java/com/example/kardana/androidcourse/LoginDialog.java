package com.example.kardana.androidcourse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.User;

public class LoginDialog extends DialogFragment {
    private String email;
    private String password;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.activity_login, null);
        builder.setView(v);
        final Activity act = getActivity();
        final Context con = getContext();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        email = ((AutoCompleteTextView) v.findViewById(R.id.email_field)).getText().toString();
                        password = ((EditText) v.findViewById(R.id.password_field)).getText().toString();
                        Model.instance.userLogin(email, password, new Model.IGetUserLoginCallback() {
                                    @Override
                                    public void onComplete(User user) {
                                        Log.d("dev", "onComplete - UserLogin LoginFragment");
                                        Intent main_intent = new Intent(act, MainActivity.class);
                                        startActivity(main_intent);
                                    }

                        });
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

   /* private boolean CheckUser()
    {
        final boolean[] result = {false};
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if it's a new user
        if (currentUser == null)
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                result[0] = true;
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
        }
        return result[0];
    }*/
}
