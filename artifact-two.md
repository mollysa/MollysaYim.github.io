<link rel="stylesheet" href="assets/css/style.css">

<h1 align="center">Enhancement Two: Algorithms and Data Structures</h1>
<h2 align="center">Inventory App – Algorithmic Enhancement</h2>

**Description:**  
This enhancement focused on optimizing the Inventory App by applying algorithmic principles to improve its 
efficiency and scalability.  
Originally, the app displayed data without search, sort, or batch management features. I implemented search and 
sort algorithms to dynamically filter and organize inventory items and used Java’s Set data structure for batch 
item operations.

**Enhancements Made:**  
- Added real-time search functionality to dynamically filter inventory data.  
- Implemented sorting by name and quantity using Java comparators.  
- Introduced Set and iteration logic to enable multi-select and batch delete operations.  
- Refactored loops and conditions to improve performance and maintain code readability.  
- Tested algorithmic changes to ensure accurate and efficient data handling.  

**Original Code vs. Enhanced Code**

```java
// Before Enhancement
List<Item> itemList = dbHelper.getAllItems();
adapter = new ItemAdapter(context, itemList);
recyclerView.setAdapter(adapter);

// After Enhancement
// Search and Sort
searchBar.addTextChangedListener(new TextWatcher() {
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : inventoryList) {
            if (item.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateData(filteredList);
    }
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override public void afterTextChanged(Editable s) {}
});

// Sort by Name or Quantity
if (option.equals("Name")) {
    Collections.sort(inventoryList, Comparator.comparing(Item::getName));
} else if (option.equals("Quantity")) {
    Collections.sort(inventoryList, Comparator.comparingInt(Item::getQuantity));
}
adapter.updateData(inventoryList);

// Batch delete logic
Set<Integer> selectedItems = new HashSet<>();
holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
    if (isChecked) selectedItems.add(item.getId());
    else selectedItems.remove(item.getId());
});

```

**After Enhancement:**

<div align="center">
    
<img src="assets/images/Before Multiselect.png" width="20%">
<img src="assets/images/After Multiselect.png" width="20%">

</div>

**Repository Files:**  
- [DisplayDataActivity.java](https://github.com/mollysa/MollysaYim.github.io/blob/main/InventoryApp/app/src/main/java/com/zybooks/inventoryapp/DisplayDataActivity.java) – Main logic for search, sort, and batch algorithms.  
- [ItemAdapter.java](https://github.com/mollysa/MollysaYim.github.io/blob/main/InventoryApp/app/src/main/java/com/zybooks/inventoryapp/ItemAdapter.java) – Handles batch selection and iteration logic.  
- [activity_display_data.xml](https://github.com/mollysa/MollysaYim.github.io/blob/main/InventoryApp/app/src/main/res/layout/activity_display_data.xml) – Layout used to display sorted and filtered items.  


**Skills Demonstrated:**  
Algorithmic problem solving, iteration, data structure management, refactoring for efficiency, and scalable software design.

**Narrative:**  
The artifact I selected is my Inventory App, which I originally created in CS 360 – Mobile Architecture and 
Programming. The app allows users to manage inventory items by adding, editing, and deleting records, with 
persistent storage through an SQLite database. I later enhanced the artifact by introducing multi-select batch 
operations that allows users to select multiple items using checkboxes and perform actions like delete or mark out 
of stock. These enhancements were completed during my final term as part of aligning the artifact with outcomes in 
algorithms and data structures.

I chose this artifact for my ePortfolio because it demonstrates my ability to take an existing project and improve 
it in meaningful, industry relevant ways. The original version was functional but inefficient, requiring users to 
delete or update items one at a time. By applying data structure management, such as using a set to track selected 
items, and iteration, looping through multiple items to perform batch actions, I solved a real usability problem. 
The inclusion of batch operations not only demonstrates my understanding of algorithmic logic but also highlights 
my ability to integrate backend database operations with UI state management in a scalable way.
These enhancements align directly with the outcomes I planned to address in Module One. Specifically, I met outcome 
3 by designing a solution that applied algorithmic principles, such as tracking, iteration, and set operations, to 
solve a practical inefficiency, while managing design choices between efficiency and complexity. I also met outcome 
4 by applying well-founded techniques and tools to improve the usability and value of the application in a way that 
reflects professional, industry-specific goals. 

Through the process of enhancing and modifying this artifact, I learned how much impact even small algorithmic 
improvements can have on usability and efficiency. I also gained deeper experience in balancing UI state with 
database operations, ensuring that selections in the UI matched the data changes happening in the background. One 
challenge I faced was handling RecyclerView’s recycling behavior, which caused checkboxes to appear checked or 
unchecked incorrectly until I learned to clear and reapply listeners properly. Another challenge was the user 
interface layout, particularly aligning the new buttons to the app looked clean and professional. At first, the 
buttons looked uneven, text was wrapping awkwardly, and spacing felt inconsistent. Through experimentation with 
ConstraintLayout, LinearLayout, and shared styles, I learned how to use weights, visibility controls, and 
consistent styling to create a polished, consistent look. These UI improvements, although not algorithmic, 
reinforced the importance of design considerations and how visual clarity can make technical enhancement more 
usable. These challenges pushed me to refine my problem-solving skills, experiment with different approaches, and 
think critically about design compromises.

[← Back to Home](index.md)
