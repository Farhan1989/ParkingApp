package com.example.asadmalik.parkinglifeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAccountActivity extends AppCompatActivity {

    public static final String  USER_NAME = "username";
    public static final String  USER_ID = "userid";

    EditText editTextName;
    Button buttonAddUser;
    Spinner spinnerAreas;
    DatabaseReference databaseUsers;
    ListView listViewUsers;

    List<User> users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);
        spinnerAreas = (Spinner) findViewById(R.id.spinnerArea);

        users = new ArrayList<>();
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });


        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                User user = users.get(i);

                Intent intent = new Intent(getApplicationContext(), NewBookingActivity.class);
                startActivity(intent);

                intent.putExtra(USER_ID, user.getUserId());
                intent.putExtra(USER_NAME, user.getUserName());

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);

                    users.add(user);
                }
                UserList adapter = new UserList(MyAccountActivity.this, users);
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addUser(){
        String name = editTextName.getText().toString().trim();
        String areas = spinnerAreas.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){

            String id = databaseUsers.push().getKey();

            User user = new User(id, name, areas);
            databaseUsers.child(id).setValue(user);

            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();



        }else{
            Toast.makeText(this, "You should Enter a name", Toast.LENGTH_SHORT).show();
        }
    }
}
