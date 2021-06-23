package com.samil.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText loginMail, loginPassword;
    private RelativeLayout login, goToSignup;
    private TextView goToForgotPassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }

        loginMail = findViewById(R.id.loginemail);
        loginPassword = findViewById(R.id.loginpassword);
        login = findViewById(R.id.login);
        goToForgotPassword = findViewById(R.id.goToForgotPassword);
        goToSignup = findViewById(R.id.gotosignup);

        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,signup.class));
            }
        });

        goToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,forgotPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = loginMail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if(mail.isEmpty() ||password.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"All Fields are Required",Toast.LENGTH_SHORT).show();

                }else
                {
                    //Login the user
                    mAuth.signInWithEmailAndPassword(mail,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                checkMailVerification();
                            }else
                            {
                                Toast.makeText(MainActivity.this,"Account Doesn't Exist ",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



                }
            }
        });
    }

    private void checkMailVerification(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user.isEmailVerified()==true)
        {
            Toast.makeText(MainActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }else {
            Toast.makeText(getApplicationContext(),"Verify your mail first",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }

    }
}