package com.zybooks.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameEditText, itemQuantityEditText;
    private ImageView imageView;
    private String imagePath = null; // To store the image path

    // Define the Activity Result Launcher
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    imagePath = getRealPathFromURI(imageUri); // Store image path
                    imageView.setImageURI(imageUri); // Display image
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Reference UI elements
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemQuantityEditText = findViewById(R.id.itemQuantityEditText);
        imageView = findViewById(R.id.imageView);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        Button addItemButton = findViewById(R.id.addItemButton);
        Button clearButton = findViewById(R.id.clearButton);

        // Image upload button functionality
        uploadImageButton.setOnClickListener(view -> openImageChooser());

        // Add item button functionality
        addItemButton.setOnClickListener(view -> {
            String itemName = itemNameEditText.getText().toString().trim();
            String itemQuantityStr = itemQuantityEditText.getText().toString().trim();

            if (itemName.isEmpty() || itemQuantityStr.isEmpty()) {
                Toast.makeText(AddItemActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(itemQuantityStr);

            // If no image was selected, imagePath will be null
            String imageToSave = imagePath != null ? imagePath : null;

            try (DatabaseHelper dbHelper = new DatabaseHelper(AddItemActivity.this)) {
                // Pass imageToSave instead of imagePath
                long result = dbHelper.addItem(itemName, quantity, imageToSave);

                if (result != -1) {
                    Toast.makeText(AddItemActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                    finish();  // Close the activity and return to DisplayDataActivity
                } else {
                    Toast.makeText(AddItemActivity.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(AddItemActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Clear button functionality
        clearButton.setOnClickListener(view -> {
            itemNameEditText.setText("");
            itemQuantityEditText.setText("");
            imageView.setImageResource(android.R.drawable.ic_menu_camera); // Reset image
        });
    }

    // Method to open the image chooser
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    // Convert URI to file path
    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return null;
        }
    }
}
