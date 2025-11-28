package com.example.aviationsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    EditText registerName, registerEmail, registerUsername, registerPassword, registerCpassword;
    TextView loginRedirect;
    Button registerButton;
    CheckBox showPasswordCheckbox, showCPasswordCheckbox;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.name);
        registerEmail = findViewById(R.id.email);
        registerUsername = findViewById(R.id.username);
        registerPassword = findViewById(R.id.password);
        registerCpassword = findViewById(R.id.cpassword);
        registerButton = findViewById(R.id.registerButton);
        loginRedirect = findViewById(R.id.loginRedirect);
        showPasswordCheckbox = findViewById(R.id.showPasswordCheckbox);
        showCPasswordCheckbox = findViewById(R.id.showCPasswordCheckbox);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                String cpassword = registerCpassword.getText().toString();

                if (!password.equals(cpassword)) {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                HelperClass helperClass = new HelperClass(name, email, username, password, cpassword);
                reference.child(username).setValue(helperClass);
                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


        showPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                registerPassword.setInputType(1);
                registerCpassword.setInputType(1);
            } else {
                registerPassword.setInputType(129);
                registerCpassword.setInputType(129);
            }
            registerPassword.setSelection(registerPassword.length());
            registerCpassword.setSelection(registerCpassword.length());
        });

        showCPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                registerCpassword.setInputType(1);
            } else {
                registerCpassword.setInputType(129);
            }
            registerCpassword.setSelection(registerCpassword.length());
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
