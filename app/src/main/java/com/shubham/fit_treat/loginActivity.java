package com.shubham.fit_treat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    TextView noAccount;
    Button mLogIN;
    EditText mEmail,mPassword;
    ProgressBar mProgressBar3;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        noAccount=findViewById(R.id.noAccount);
        firebaseAuth=FirebaseAuth.getInstance();
        mLogIN=findViewById(R.id.logInButton);
        mEmail=findViewById(R.id.logInEmail);
        mPassword=findViewById(R.id.logInPassword);
        mProgressBar3=findViewById(R.id.progressBar3);


        mLogIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("password is required");
                    return;
                }

                if (password.length()<6){
                    mPassword.setError("password should be greater then 6 characters");
                    return;
                }

                mProgressBar3.setVisibility(View.VISIBLE);


                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(loginActivity.this, "Loging in", Toast.LENGTH_SHORT).show();
                            Intent myIntent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(myIntent);
                        }
                        else {
                            Toast.makeText(loginActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),   registerActivity.class);
                startActivity(myIntent);
            }
        });

    }

}