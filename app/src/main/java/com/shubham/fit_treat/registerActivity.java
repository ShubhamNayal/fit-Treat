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

public class registerActivity extends AppCompatActivity {
    TextView mhaveAccount;
    EditText mfullName,memail,mpassword,mphone;
    Button mregister;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mhaveAccount=findViewById(R.id.textHaveAccount);
        mfullName=findViewById(R.id.FullName);
        memail=findViewById(R.id.Email);
        mpassword=findViewById(R.id.Password);
        mphone=findViewById(R.id.Phone);
        mregister=findViewById(R.id.button);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);


        if(firebaseAuth.getCurrentUser()!=null){
            Intent myIntent=new Intent(getApplicationContext(),MainActivity.class);
            finish();
        }


        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();



                if (TextUtils.isEmpty(email))
                {
                    memail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mpassword.setError("password is required");
                    return;
                }

                if (password.length()<6)
                {
                    mpassword.setError("password should be greater then 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                //Registering user


                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(registerActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            Intent myIntent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(myIntent);
                        }
                        else {
                            Toast.makeText(registerActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mhaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),   loginActivity.class);
                startActivity(myIntent);
            }
        });


    }


}