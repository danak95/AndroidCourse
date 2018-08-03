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

import java.util.ArrayList;
import java.util.List;

public class ModelFirebaseUser {
    private static final String USERS_KEY = "Users";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;
    private DatabaseReference usersReference;
    ValueEventListener eventListener;

    private User currentUser;

    public ModelFirebaseUser() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference(USERS_KEY);
    }


    // Login of user
    interface IGetUserLoginCallback {
        void onComplete(User user);
    }
    public void userLogin(String email, String password, final IGetUserLoginCallback callback) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                getCurrentUser(new IGetCurrentUserCallback() {
                                    @Override
                                    public void onComplete(User user) {
                                        Log.d("TAG", "signInWithEmail:success");
                                        callback.onComplete(currentUser);
                                    }
                                });
                            } else {
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                callback.onComplete(currentUser);
                            }
                        }
                    });
        }
        else
        {
            callback.onComplete(currentUser);
        }
    }

    // Add new user
    interface IAddNewUser {
        void onComplete(User user);
    }
    public void AddNewMember(final User newUser, final IAddNewUser callback) {
        mAuth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            newUser.setUserid(firebaseUser.getUid());
                            currentUser = newUser;
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmailAndPassword:success");
                            usersReference.child(newUser.getUserid()).setValue(newUser);
                            callback.onComplete(currentUser);
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            callback.onComplete(currentUser);
                        }
                    }
                });
    }

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
                        currentUser.setUserid(firebaseUser.getUid());
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    // Update user details
    interface IUpdateUserCallback {
        void onComplete(boolean success);
    }
    public void updateUser(User user,final IUpdateUserCallback callback){
        usersReference.child(user.getUserid()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }
    public interface IGetAllUsers
    {
        void onSuccess(List<User> users);
    }

    public void getAllUsers(final ModelFirebaseUser.IGetAllUsers callback) {
        eventListener = usersReference.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<User>();

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    userList.add(userSnapshot.getValue(User.class));
                }

                callback.onSuccess(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void signOut()
    {
        mAuth.signOut();
        currentUser = null;
    }
}