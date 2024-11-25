package com.example.aviation_solutions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NumberOfUsersActivity extends AppCompatActivity {

    private TextView tvNumberOfUsers;
    private Spinner spinnerSort;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> usersList;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_users);

        tvNumberOfUsers = findViewById(R.id.tvNumberOfUsers);
        spinnerSort = findViewById(R.id.spinnerSort);
        recyclerView = findViewById(R.id.recyclerView);

        // Set up RecyclerView with UserAdapter
        usersList = new ArrayList<>();
        userAdapter = new UserAdapter(usersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);

        // Initialize the spinner for sorting options
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Sort by Name", "Sort by Admin", "Sort by Email"});
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);


        // Firebase reference
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Query Firebase to get all users
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                int totalUsers = 0;
                int adminUsers = 0;
                int normalUsers = 0;
                StringBuilder userDetails = new StringBuilder();

                // Loop through the users and populate the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("FirebaseData", "Raw Data: " + snapshot.getValue());
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        totalUsers++;
                        if ("admin".equals(user.getAdmin())) {
                            adminUsers++;
                        } else {
                            normalUsers++;
                        }

                        // Add user to the list
                        usersList.add(user);
                    }

                }
                Log.d("FirebaseData", "Total users: " + totalUsers);
                // Display total users, admin users, normal users
                String displayText = "Total Users: " + totalUsers + "\n" +
                        "Admins: " + adminUsers + "\n" +
                        "Normal Users: " + normalUsers;
                tvNumberOfUsers.setText(displayText);
                Log.d("AdapterCheck", "Item count in adapter: " + usersList.size());


                // Update the RecyclerView with the users
                userAdapter.updateUserList(usersList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(NumberOfUsersActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Spinner item selection listener
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Based on the selection, sort users
                switch (position) {
                    case 0: // Sort by Name
                        sortUsersByName();
                        break;
                    case 1: // Sort by Admin status
                        sortUsersByAdmin();
                        break;
                    case 2: // Sort by Email
                        sortUsersByEmail();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    // Method to sort users by name
    private void sortUsersByName() {
        usersList.sort((u1, u2) ->
                (u1.getName() != null ? u1.getName() : "").compareToIgnoreCase(
                        u2.getName() != null ? u2.getName() : ""));
        userAdapter.updateUserList(usersList);
    }

    private void sortUsersByAdmin() {
        usersList.sort((u1, u2) ->
                (u1.getAdmin() != null ? u1.getAdmin() : "").compareToIgnoreCase(
                        u2.getAdmin() != null ? u2.getAdmin() : ""));
        userAdapter.updateUserList(usersList);
    }

    private void sortUsersByEmail() {
        usersList.sort((u1, u2) ->
                (u1.getEmail() != null ? u1.getEmail() : "").compareToIgnoreCase(
                        u2.getEmail() != null ? u2.getEmail() : ""));
        userAdapter.updateUserList(usersList);
    }


}
