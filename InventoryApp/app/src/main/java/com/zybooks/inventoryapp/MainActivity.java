package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Reference UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        // Login button functionality
        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else if (databaseHelper.checkUserCredentials(username, password)) {  // Check credentials in the database
                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                // Explicit intent to open DisplayDataActivity
                Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
                startActivity(intent);

                // Finish MainActivity so user can't go back to login screen using the back button
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Create Account button functionality
        createAccountButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Check if username already exists
                if (databaseHelper.isUsernameTaken(username)) {
                    Toast.makeText(MainActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    long result = databaseHelper.addUser(username, password);  // Store the result as long
                    if (result != -1) {  // Check if the user was added successfully
                        Toast.makeText(MainActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
