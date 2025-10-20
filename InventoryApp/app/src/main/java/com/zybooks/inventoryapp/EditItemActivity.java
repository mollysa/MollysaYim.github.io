package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText editName, editQuantity;
    private Button updateButton;
    private DatabaseHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editName = findViewById(R.id.editItemName);
        editQuantity = findViewById(R.id.editItemQuantity);
        updateButton = findViewById(R.id.buttonUpdateItem);
        dbHelper = new DatabaseHelper(this);

        // Retrieve item details from intent
        Intent intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", -1);
        String itemName = intent.getStringExtra("ITEM_NAME");
        int itemQuantity = intent.getIntExtra("ITEM_QUANTITY", 0);

        if (itemId == -1) {
            Toast.makeText(this, "Error loading item.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Populate the fields
        editName.setText(itemName);
        editQuantity.setText(String.valueOf(itemQuantity));

        // Handle update button click
        updateButton.setOnClickListener(v -> {
            String newName = editName.getText().toString().trim();
            int newQuantity = Integer.parseInt(editQuantity.getText().toString().trim());

            // Update the database
            int rowsAffected = dbHelper.updateItem(itemId, newName, newQuantity);
            if (rowsAffected > 0) {
                Toast.makeText(EditItemActivity.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close activity
            } else {
                Toast.makeText(EditItemActivity.this, "Update failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
