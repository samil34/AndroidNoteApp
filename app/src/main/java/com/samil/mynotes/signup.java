package com.samil.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    private EditText signupEmail;
    private EditText signupPassword;
    private RelativeLayout signup;
    private TextView goToLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signup = findViewById(R.id.signup);
        goToLogin = findViewById(R.id.gotoLogin);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();

                if (mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(signup.this,"All Fields are Required",Toast.LENGTH_SHORT).show();
                }
                else if (password.length() < 7)
                {
                    Toast.makeText(signup.this,"Password Should Greater than 7 Digits",Toast.LENGTH_SHORT).show();
                }
                else{
                    //registered the user to firebase
                    mAuth.createUserWithEmailAndPassword(mail,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(signup.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }else
                            {
                                Toast.makeText(signup.this,"Failed To Register",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: " + e);
                        }
                    });
                }


            }
        });
    }

    //send email verification
    private void sendEmailVerification(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(signup.this,"Verification Email is Send, Verify and Login Again",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(signup.this,MainActivity.class));
                    }
                }
            });
        }
        else
            {
            Toast.makeText(signup.this,"Failed To Send Verification Email",Toast.LENGTH_SHORT).show();

        }
    }
}