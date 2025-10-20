<h1 align="center">Enhancement One: Software Design and Engineering</h1>
<h2 align="center">Inventory App</h2>

**Description:**  
This Android app allows users to manage an inventory of items by adding, editing, 
and deleting records. The original version focused on functionality, but I enhanced 
it to improve the layout, usability, and overall design.

**Enhancements Made:**  
- Added search and sort functionality to improve navigation and data visibility 
within the inventory.  
- Implemented a delete confirmation dialog to prevent accidental data loss and 
improve user safety.  
- Fixed the image upload feature by saving image links instead of file paths, 
ensuring consistency across Android versions.  
- Enhanced the UI layout using RecyclerView and custom adapters for a cleaner, more 
user-friendly interface.  
- Refactored code to follow clean architecture principles and improve readability 
and maintainability.   

**Original Code vs. Enhanced Code**
```java
// Delete button - Old Version
holder.deleteButton.setOnClickListener(v -> {
    dbHelper.deleteItem(item.getId());
    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
    refreshList();
});

// Image handling - Old Version
String imagePath = data.getData().getPath(); 
item.setImagePath(imagePath);



// Delete button - Enhanced Version
holder.deleteButton.setOnClickListener(v -> {
    new AlertDialog.Builder(context)
        .setTitle("Delete Item")
        .setMessage("Are you sure you want to delete " + item.getName() + "?")
        .setPositiveButton("Yes", (dialog, which) -> {
            dbHelper.deleteItem(item.getId());
            itemList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();
        })
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .show();
});

// Image handling - Enhanced Version
Uri imageUri = data.getData();
item.setImagePath(imageUri.toString()); // Save URI instead of file path
imageView.setImageURI(imageUri);

```

**Skills Demonstrated:**  
Software design, Java development, UI/UX improvements, and object-oriented 
programming.

**Narrative:**  
The artifact I selected to represent enhancements and outcomes within the Software Engineering and Design category is my Inventory App,  originally created during my coursework in CS-360, Mobile Architecture and Programming. The project was developed in Android Studio using Java and SQLite. This week, I enhanced the application by adding search and sort functionality for inventory items, a confirmation dialog before deletion, and a fix for the image upload feature, which previously saved incorrect file paths. I updated the app to store image links instead, ensuring consistency across Android versions.

I chose to include this artifact because it demonstrates my ability to design, 
debug, and enhance a mobile application to meet real-world needs. It showcases 
skills such as creating interactive and user-friendly interfaces with RecyclerView 
and custom adapters. I was able to improve the user experience with search, sort, 
confirmation dialogs, and image uploaders. The confirmation dialog highlights my 
focus on user-centered design and safety, while the enhanced image uploader shows 
my persistence in problem-solving and debugging technical issues.

With these enhancements, I feel confident that I met course outcomes 3 and 4.
Adding filtering, sorting, and confirmation dialogs allowed me to address real 
usability problems while balancing efficiency and user safety. Filtering and 
sorting required logic to loop through and reorder data, while the delete 
confirmation added an extra step but prevented users from losing items by accident. 
I also worked with industry-standard Android tools like RecyclerView, search bars, 
and dialogs to refine workflows and improve the overall user experience. These 
updates show my ability to design and improve solutions that are both practical for 
users and aligned with professional development practices.

Enhancing this artifact taught me how important small but thoughtful features are 
for usability. Implementing search and sort helped me practice algorithmic thinking 
by designing logic to filter and reorder lists dynamically while keeping the 
RecyclerView synchronized with the database. Adding a confirmation dialog before 
deleting showed me the value of user-centered design, since it provided a safeguard 
against accidental data loss. The biggest challenge was integrating these features 
smoothly into the existing app without breaking other functionality, but solving 
that pushed me to refactor my code and think carefully about balancing efficiency, 
simplicity, and data safety.

[← Back to Home](index.md)
