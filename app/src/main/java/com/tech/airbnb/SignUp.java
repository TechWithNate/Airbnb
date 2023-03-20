package com.tech.airbnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tech.airbnb.Model.User;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mBirthDay;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mCPassword;
    private MaterialButton createBtn;
    private ProgressDialog progressDialog;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();

        createBtn.setOnClickListener(v -> {
            checkFields();
            //launchActivity();
        });

        mBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

    }

    private void initViews(){
        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mBirthDay = findViewById(R.id.birthday);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mCPassword = findViewById(R.id.cPassword);
        createBtn = findViewById(R.id.create_account);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
    }

    private void selectDate(){
        // Get current date as default date for DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog and set callback for when a date is selected
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Set the selected date to the EditText
                String dateString = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                mBirthDay.setText(dateString);
            }
        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void checkFields(){
        if (mFirstName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show();
            mFirstName.requestFocus();
        } else if (mLastName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Last Name", Toast.LENGTH_SHORT).show();
            mLastName.requestFocus();
        } else if (mBirthDay.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Birth Day", Toast.LENGTH_SHORT).show();
            mBirthDay.requestFocus();
        } else if (mEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            mEmail.requestFocus();
        } else if (mPassword.getText().toString().length() < 6) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
            mPassword.requestFocus();
        } else if (mPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            mPassword.requestFocus();
        } else if (!mCPassword.getText().toString().equals(mPassword.getText().toString())) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            mCPassword.setText(null);
            mPassword.setText(null);
            mPassword.requestFocus();
        }else {
            createAccount(mEmail.getText().toString(), mPassword.getText().toString());
        }
    }

    private void createAccount(String email, String password){
        //TODO: create user account with firebase
        progressDialog.setMessage("Creating account");
        progressDialog.setTitle("Sign Up");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    addingUserDetails();
                    FirebaseUser user = mAuth.getCurrentUser();
                    launchActivity(user);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, "Login Failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void launchActivity(FirebaseUser user){
        if (null != user){
            startActivity(new Intent(SignUp.this, Home.class));
            finish();
        }
    }

    private void addingUserDetails(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String id = null;
        if (null != firebaseUser){
            id = firebaseUser.getUid();
        }
        User user = new User(firebaseUser.getUid(), mFirstName.getText().toString(), mFirstName.getText().toString(), mBirthDay.getText().toString(), mEmail.getText().toString());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SignUp.this, ""+documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Failed to add user details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (null != user) {
            launchActivity(user);
        }
        super.onStart();

    }


}