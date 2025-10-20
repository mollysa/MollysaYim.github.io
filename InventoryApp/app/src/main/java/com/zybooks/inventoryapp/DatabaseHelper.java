package com.zybooks.inventoryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 2;

    // Other constants (items table, etc.)
    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_IMAGE = "image";

    // User table and columns for credentials (assuming you have a User table)
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create items table
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_QUANTITY + " INTEGER, "
                + COLUMN_IMAGE + " TEXT)";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to check user credentials
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                new String[]{username, password});

        boolean isValidUser = cursor.moveToFirst(); // Check if user exists
        cursor.close();
        db.close();

        return isValidUser;
    }



    // Method to add an item
    public long addItem(String item_name, int quantity, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item_name);
        values.put(COLUMN_QUANTITY, quantity);
        if (imagePath != null) {
            values.put(COLUMN_IMAGE, imagePath);
        } else {
            values.putNull(COLUMN_IMAGE);  // Store null for image if not provided
        }
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Method to update an existing inventory item
    public int updateItem(int id, String item_name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item_name);
        values.put(COLUMN_QUANTITY, quantity);

        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }


    // Method to delete an item
    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("items", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }


    // Method to fetch all items from the database
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                    String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));

                    // Add to the item list
                    Item item = new Item(id, name, quantity, imagePath);
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return itemList;
    }

    // Method to check if the username is already taken
    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean isTaken = cursor != null && cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isTaken;  // Returns true if username exists, false otherwise
    }

    // Method to add a user
    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);  // Store password

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;  // Return the row ID if successful, or -1 if failed
    }

}
