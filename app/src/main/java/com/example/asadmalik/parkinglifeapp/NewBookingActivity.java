package com.example.asadmalik.parkinglifeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewBookingActivity extends AppCompatActivity {

//    TextView textViewUserName;
//    TextView textViewDate;
//    TextView textViewTime;
//    TextView textViewDuration;
//
//    EditText editTextDate;
//    EditText editTextTime;
//    EditText editTextDuration;
//    Button buttonAddBooking;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);

//        textViewUserName = (TextView) findViewById(R.id.textViewName);
//        textViewDate = (TextView) findViewById(R.id.textViewDate);
//        textViewTime = (TextView) findViewById(R.id.textViewTime);
//        textViewDuration = (TextView) findViewById(R.id.textViewDuration);
//        editTextDate = (EditText) findViewById(R.id.editTextDate);
//        editTextTime = (EditText) findViewById(R.id.editTextTime);
//        editTextDuration = (EditText) findViewById(R.id.editTextDuration);
//
//        buttonAddBooking = (Button) findViewById(R.id.buttonAddBooking);
//
//        Intent intent = getIntent();
//
//        String id = intent.getStringExtra(MyAccountActivity.USER_ID);
//        String name = intent.getStringExtra(MyAccountActivity.USER_NAME);
//
//   textViewUserName.setText(name);
//        databaseReference = FirebaseDatabase.getInstance().getReference("booking").child(id);
//
//        buttonAddBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveBooking();
//            }
//        });
//    }
//
//        private void saveBooking(){
//            String date = editTextDate.getText().toString().trim();
//            String time = textViewTime.getText().toString().trim();
//            String duration = textViewDuration.getText().toString().trim();
//
//            if(!TextUtils.isEmpty(date)){
//                String id = databaseReference.push().getKey();
//                Booking booking = new Booking(id, date, time, duration);
//
//                databaseReference.child(id).setValue(booking);
//                Toast.makeText(this,"Booking Save Successfully", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(this, "date should not be empty", Toast.LENGTH_LONG).show();
//            }
        }
}
