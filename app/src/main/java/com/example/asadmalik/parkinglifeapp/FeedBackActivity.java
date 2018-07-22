package com.example.asadmalik.parkinglifeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;


public class FeedBackActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextFeedback;
    Button buttonSubmit;

    DatabaseReference databaseFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        databaseFb = FirebaseDatabase.getInstance().getReference("Fb");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextFeedback = (EditText) findViewById(R.id.editTextFeedback);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFb();
            }
        });
    }


    private void addFb(){

            String Name = editTextName.getText().toString().trim();
            String Email = editTextEmail.getText().toString().trim();
            String Feedback =editTextFeedback.getText().toString().trim();

            if(!TextUtils.isEmpty(Name)){

                String id = databaseFb.push().getKey();

                Fb fb = new Fb(id, Name, Email, Feedback);

                databaseFb.child(id).setValue(fb);

                Toast.makeText(this, "Feedback added", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "You should Enter all the infromation", Toast.LENGTH_SHORT).show();
            }

        }
}
