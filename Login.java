package com.example.aviationsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText LoginUsername, LoginPassword;
    Button loginButton;
    TextView registerRedirect;
    CheckBox showPasswordCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        LoginUsername = findViewById(R.id.login_username);
        LoginPassword = findViewById(R.id.login_password);
        registerRedirect = findViewById(R.id.registerRedirect);
        loginButton = findViewById(R.id.loginButton);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    LoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                LoginPassword.setSelection(LoginPassword.getText().length());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() || !validatePassword()) {
                    return;
                } else {
                    checkUser();
                }
            }
        });

        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean validateUsername() {
        String val = LoginUsername.getText().toString();
        if (val.isEmpty()) {
            LoginUsername.setError("Username cannot be empty");
            return false;
        } else {
            LoginUsername.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = LoginPassword.getText().toString();
        if (val.isEmpty()) {
            LoginPassword.setError("Password cannot be empty");
            return false;
        } else {
            LoginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = LoginUsername.getText().toString().trim();
        String userPassword = LoginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users"); // Correct reference path
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    LoginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    // Validate password
                    if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                        Intent intent = new Intent(Login.this, UserDashboard.class);
                        intent.putExtra("username", userUsername); // Pass the username to UserDashboard
                        startActivity(intent);
                        finish(); // Optional: finish login activity
                    } else {
                        LoginPassword.setError("Invalid Credentials");
                        LoginPassword.requestFocus();
                    }
                } else {
                    LoginUsername.setError("User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
