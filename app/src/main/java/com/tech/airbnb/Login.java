package com.tech.airbnb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
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

public class Login extends AppCompatActivity {

    private static final int REQ_CODE = 1;

    private EditText mEmail;
    private EditText mPassword;
    private MaterialButton loginBtn;
    private ImageView mGoogleBtn;
    private TextView mSignupBtn;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                        .build();

        gsc = GoogleSignIn.getClient(this, gso);






        loginBtn.setOnClickListener(v -> {
            //launchActivity();
            checkFields();
        });

        mGoogleBtn.setOnClickListener(v -> {
            loginWithGoogle();
        });

        mSignupBtn.setOnClickListener(v -> {
            openSignUp();
        });
    }


    private void initViews(){
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        mGoogleBtn = findViewById(R.id.google_login);
        mSignupBtn = findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void checkFields(){
        if (mEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            mEmail.requestFocus();
        } else if (mPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            mPassword.requestFocus();
        }else {
            loginUser(mEmail.getText().toString(), mPassword.getText().toString());
        }
    }

    private void loginUser(String email, String password){
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Checking account");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    saveDetails();
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    launchActivity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginWithGoogle(){
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, REQ_CODE);
    }

    private void saveDetails(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String firstname = null;
        String lastname = null;
        String email = null;
        String imgUrl = null;
        String userID = null;


        if (account != null) {
            Toast.makeText(this, "Account not null", Toast.LENGTH_SHORT).show();
            firstname = account.getDisplayName();
            lastname = account.getFamilyName();
            email = account.getEmail();
            userID = account.getId();


            if (account.getPhotoUrl() != null) {
                imgUrl = account.getPhotoUrl().getLastPathSegment();
            }
        }else{
            Toast.makeText(this, "Account isn null", Toast.LENGTH_SHORT).show();
        }

        User user = new User(userID, firstname, lastname, null, email, imgUrl);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("Google Account:: ", documentReference.getId());
                        Toast.makeText(Login.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openSignUp(){
        startActivity(new Intent(Login.this, SignUp.class));
        finish();
    }

    private void launchActivity(){
        startActivity(new Intent(Login.this, Home.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE && resultCode == RESULT_OK){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()){
                try {
                    task.getResult(ApiException.class);
                    Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show();
                    launchActivity();
                    finish();
                }catch (ApiException e){
                    Toast.makeText(this, "Api Error", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Login Failed Try again", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (null != user) {
            launchActivity();
            finish();
        }

    }
}