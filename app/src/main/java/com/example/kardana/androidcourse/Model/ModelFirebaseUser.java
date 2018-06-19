package com.example.kardana.androidcourse.Model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

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

public class ModelFirebaseUser {
    private static final String USERS_KEY = "Users";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;
    private DatabaseReference usersReference;

    private User currentUser;

    public ModelFirebaseUser() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference(USERS_KEY);
       // mAuth = FirebaseAuth.getInstance();
        //mAuth.addAuthStateListener(mAuthStateListener);
    }


    // Login of user
    interface IGetUserLoginCallback {
        void onComplete(User user);
    }
    public void userLogin(String email, String password, final IGetUserLoginCallback callback) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            currentUser = new User();
                            currentUser.setUserid(1);
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                        }
                        callback.onComplete(currentUser);
                    }
                });
    }


/*
    public void userLogin(String email, String password, final IGetUserLoginCallback callback) {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null)
                {
                    Log.d("TAG", "fail");
                }
            }
        };
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getCurrentUser(new IGetCurrentUserCallback() {
                                // Sign in success, update UI with the signed-in user's information
                                @Override
                                public void onComplete(User user) {
                                    Log.d("TAG", "createUserWithEmail:success");
                                    callback.onComplete(user);
                                }
                            });
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.d("dev","onComplete userLogin ModelUserFirebase with null "+task.getException());
                            callback.onComplete(null);
                        }
                    }
                });
    }
*/

    // Get current user
    interface IGetCurrentUserCallback {
        void onComplete(User user);
    }

    public void getCurrentUser(final IGetCurrentUserCallback callback) {
        // singleton
        if (currentUser != null) {
            callback.onComplete(currentUser);
        }
        else {
            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
            Log.d("dev","getCurrentUser ModelUserFirebase ");

            if (firebaseUser == null) {
                callback.onComplete(null);
            }
            else {
                Log.d("dev","getCurrentUser ModelUserFirebase "+ firebaseUser.getUid());
                getUserById(firebaseUser.getUid(), new IGetUserByIdCallback() {
                    @Override
                    public void onComplete(User user) {
                        currentUser = new User(user);
                        currentUser.setUserid(Integer.parseInt(firebaseUser.getUid()));
                        callback.onComplete(currentUser);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        }
    }


    // Get user by ID
    interface IGetUserByIdCallback {
        void onComplete(User user);
        void onCancel();
    }
    public void getUserById(String id, final IGetUserByIdCallback callback) {
        usersReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }
}