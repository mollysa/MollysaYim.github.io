package com.zybooks.inventoryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DisplayDataActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private DatabaseHelper dbHelper;
    private static final int SMS_PERMISSION_REQUEST_CODE = 1;
    private static final int IMAGE_PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addItemButton = findViewById(R.id.buttonAddData);
        Button settingsButton = findViewById(R.id.btnOpenSettings);
        dbHelper = new DatabaseHelper(this);

        // Fetch all items from the database
        List<Item> itemList = dbHelper.getAllItems();

        // Set the adapter with the fetched item list and set click listener
        itemAdapter = new ItemAdapter(this, itemList, this);
        recyclerView.setAdapter(itemAdapter);

        // Navigate to AddItemActivity when the "Add Item" button is clicked
        addItemButton.setOnClickListener(view -> {
            // Check for permission to read media (images)
            checkMediaPermission();
            Intent intent = new Intent(DisplayDataActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        // Open settings activity on button click
        settingsButton.setOnClickListener(view -> {
            checkSMSPermission();
        });
    }

    // Handle item click to open EditItemActivity
    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(DisplayDataActivity.this, EditItemActivity.class);
        intent.putExtra("ITEM_ID", item.getId());
        intent.putExtra("ITEM_NAME", item.getName());
        intent.putExtra("ITEM_QUANTITY", item.getQuantity());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning from EditItemActivity
        List<Item> updatedItemList = dbHelper.getAllItems();
        itemAdapter.updateData(updatedItemList);
    }

    // Check if the app has SMS permissions
    private void checkSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with SMS functionality
            sendSMSNotification("Inventory Alert", "You have a low inventory level! Login to review your items.");
        }
    }

    // Handle the result of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with SMS functionality
                sendSMSNotification("Inventory Alert", "You have a low inventory level!");
            } else {
                // Permission denied, show a message but don't block app functionality
                Toast.makeText(this, "Permission denied, SMS notifications will not be sent.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == IMAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with reading images
                Toast.makeText(this, "Permission granted to access images.", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission denied to access images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Send SMS notification
    private void sendSMSNotification(String messageTitle, String messageBody) {
        SmsManager smsManager = SmsManager.getDefault();
        String phoneNumber = "1234567890";  // Test phone number
        smsManager.sendTextMessage(phoneNumber, null, messageTitle + "\n" + messageBody, null, null);
        Toast.makeText(this, "SMS notification sent!", Toast.LENGTH_SHORT).show();
    }

    // Check if the app has media permission (for images) on Android 13 and above
    private void checkMediaPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            // Request permission for reading media (images)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, IMAGE_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with accessing images
            Toast.makeText(this, "Permission granted to access images.", Toast.LENGTH_SHORT).show();
        }
    }
}
