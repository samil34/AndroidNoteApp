package com.samil.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNotes extends AppCompatActivity {

    EditText title, description;
    Button updateNotes;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        updateNotes = findViewById(R.id.updateNote);

        getSupportActionBar().setTitle("Update Note");

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        description.setText(i.getStringExtra("description"));
        id = i.getStringExtra("id");

        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString()))
                {
                    DatabaseClass db = new DatabaseClass(UpdateNotes.this);
                    db.updateNotes(title.getText().toString(),description.getText().toString(),id);

                    Intent i = new Intent(UpdateNotes.this,HomePage.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Both Fields Required",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}