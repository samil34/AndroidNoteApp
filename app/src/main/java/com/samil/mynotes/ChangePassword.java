package com.samil.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    private TextView changePassword;
    private RelativeLayout saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changePassword = findViewById(R.id.changePassword);
        saveButton = findViewById(R.id.save);
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Change Your Password");
        

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = changePassword.getText().toString();

                if (password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Password is required",Toast.LENGTH_SHORT).show();
                }else if (password.length() < 7 )
                {
                    Toast.makeText(ChangePassword.this,"Password Should Greater than 7 Digits",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    user = mAuth.getInstance().getCurrentUser();
                    user.updatePassword(password)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ChangePassword.this, "Password Change Successful", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(ChangePassword.this, "Failed to Password Change", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

    }
}