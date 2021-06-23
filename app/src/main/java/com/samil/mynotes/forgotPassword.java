package com.samil.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    private EditText forgotPassword;
    private RelativeLayout passwordRecoverButton;
    private TextView goBackToLogin;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgotPassword);
        passwordRecoverButton  = findViewById(R.id.forgotPasswordButton);
        goBackToLogin = findViewById(R.id.goToBackLogin);

        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });

        passwordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = forgotPassword.getText().toString().trim();
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter your first",Toast.LENGTH_SHORT).show();
                }
                else{
                    //we have to send password recover email
                    mAuth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(forgotPassword.this,"Mail send, You can recover your password using mail.",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotPassword.this,MainActivity.class));
                            }else
                            {
                                Toast.makeText(forgotPassword.this,"Email is wrong or account not exist",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });



    }


}