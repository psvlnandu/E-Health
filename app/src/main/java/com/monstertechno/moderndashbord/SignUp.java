package com.monstertechno.moderndashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class SignUp<gotoLoginPage> extends AppCompatActivity {
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;


    DatabaseReference reference;
    //Authentication
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.User_Name);
        regEmail = findViewById(R.id.Email);
        regPhoneNo = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.SignUptoLogin);

        //authentication
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");


        //Save data to fiebase
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get alll data

                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                PerfomAuth(email, password);
            }
        });
    }

    private void PerfomAuth(String email, String password) {
        progressDialog.setMessage("Please wait while Registration");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    String name = regName.getEditText().getText().toString();
                    String username = regUsername.getEditText().getText().toString();
                    String email = regEmail.getEditText().getText().toString();
                    String phoneNo = regPhoneNo.getEditText().getText().toString();
                    String password = regPassword.getEditText().getText().toString();


                    UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("user")
                            .setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(SignUp.this, "Failed registration:" + task.getException(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
//                else {
//                    progressDialog.dismiss();
//                    Toast.makeText(SignUp.this, "Failed registration:" + task.getException(), Toast.LENGTH_SHORT).show();
//                }
            }
        });


    }

    private Boolean validateName() {

        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {

            regName.setError("Field cannot be empty");

            return false;

        } else {

            regName.setError(null);

            regName.setErrorEnabled(false);

            return true;

        }

    }

    private Boolean validateUsername() {

        String val = regUsername.getEditText().getText().toString();

        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {

            regUsername.setError("Field cannot be empty");

            return false;

        } else if (val.length() >= 15) {

            regUsername.setError("Username too long");

            return false;

        } else if (!val.matches(noWhiteSpace)) {

            regUsername.setError("White Spaces are not allowed");

            return false;

        } else {

            regUsername.setError(null);

            regUsername.setErrorEnabled(false);

            return true;

        }

    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    //This function will execute when user click on Register Button
    public void registerUser(View view) {
        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()) {
            return;
        }

    }

    public void gotoLoginPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }


}