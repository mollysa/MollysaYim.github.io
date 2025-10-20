<h1 align="center">Enhancement Three: Databases</h1>
<h2 align="center">Inventory App</h2>

**Description:**  
This enhancement expanded the Inventory App’s functionality by improving database interaction and integrating an SMS notification system.  
I added logic to automatically check inventory levels and send low-stock alerts using Android’s `SmsManager`.  
This demonstrated both backend database design and real-world communication features.

**Enhancements Made:**  
- Added a method to query the database for items below a user-defined quantity threshold.  
- Implemented an SMS notification feature for low-stock alerts using Android’s `SmsManager`.  
- Created a settings screen that allows users to toggle SMS alerts and customize stock thresholds.  
- Used `SharedPreferences` to store user preferences securely.  
- Improved the app’s database schema for better performance and data accuracy.  

**Original Code vs. Enhanced Code**

```java
// Before Enhancement
public long addItem(String name, int quantity) {
    ContentValues values = new ContentValues();
    values.put("name", name);
    values.put("quantity", quantity);
    return db.insert("items", null, values);
}

// After Enhancement
private void checkInventoryLevels() {
    SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    int threshold = prefs.getInt("sms_threshold", 5);
    boolean smsEnabled = prefs.getBoolean("sms_enabled", true);

    if (!smsEnabled) return;

    List<Item> lowStockItems = dbHelper.getItemsBelowThreshold(threshold);
    if (!lowStockItems.isEmpty()) {
        StringBuilder message = new StringBuilder("Low stock alert: ");
        for (Item item : lowStockItems) {
            message.append(item.getName()).append(", ");
        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("1234567890", null, message.toString(), null, null);
        Toast.makeText(this, "Low stock SMS alert sent!", Toast.LENGTH_SHORT).show();
    }
}

```

**Skills Demonstrated:**  
Database design, SQL querying, Android permissions and security, data persistence, and real-world API integration.

**Narrative:**  
The artifact I chose is my Inventory App, which I first built during my Computer Science coursework in CS 360, 
Mobile Architecture and Programming. It started out as a straightforward project in Android Studio using Java and 
SQLite, where users could add, edit, and delete inventory items. As I got further into my program, especially in my 
final term, I went back and improved it by adding features like multi-select batch actions, a setting screen, and 
now, SMS alerts for low stock notifications. This feature allowed users to set a threshold for when inventory is 
running low and receive a text message listing the affected items.

I included this app in my ePortfolio because the SMS alerts show how I can take a simple project and make it more 
useful and ready for the real world. Adding this feature wasn’t just about sending a text, it meant building a 
settings screen where users could turn notifications on or off and set their own stock threshold. On the backend, I 
had to write the logic to scan the database, figure out which items were under the threshold, and build a clean, 
readable message for the user. This ties together a lot of skills such as working with databases, iterating through 
data, handling Android permissions, and designing features with the user in mind.

I feel like I met the outcomes I set out to achieve. The algorithm for checking items against the threshold gave me 
the chance to practice applying data structures and iteration in a meaningful way. Adding the preferences, 
permissions, and SMS integration showed that I could also use tools and practices that line up with industry 
standards. Because this enhancement covered the goals I had in mind, I don’t think I need to make any changes to my 
original outcome plan.

Working on the SMS alerts taught me a lot about thinking past just making something work and focusing on how it 
feels for the user. I had to figure out how Android’s permission system worked, how to save and load user 
preferences, and how to generate messages that were actually helpful instead of generic. Some of the harder parts 
were getting the preferences and SMS permissions to play nicely together, and making sure the app didn’t notify the 
user too often. It was also a challenge to get the alerts to list out the actual item names so the text felt 
specific and useful. In the end, these challenges pushed me to think about usability just as much as functionality, 
which I think is an important step forward in my development skills.

[← Back to Home](index.md)
