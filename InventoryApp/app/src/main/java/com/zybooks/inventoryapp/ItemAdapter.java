package com.zybooks.inventoryapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.load.DataSource;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private OnItemClickListener listener;
    private DatabaseHelper dbHelper;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public ItemAdapter(Context context, List<Item> itemList, OnItemClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Bind name and quantity
        holder.nameTextView.setText(item.getName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        String imagePath = item.getImagePath();

        // Load image using Glide with listener for success and failure
        if (imagePath != null) {
            Glide.with(holder.itemView.getContext())
                    .load(imagePath)  // Load image from the URL or path
                    .listener(new RequestListener<>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false; // Returning false will allow Glide to handle the failure
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false; // Returning false will allow Glide to handle the resource
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.itemImageView); // Bind image to the itemImageView in ViewHolder
        } else {
            // Optionally handle the case where imagePath is null
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.no_image)  // Load a placeholder image if path is null
                    .into(holder.itemImageView);
        }

        // Handle item click for editing
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        // Handle delete button click
        holder.deleteButton.setOnClickListener(v -> {
            Log.d("ItemAdapter", "Delete button clicked for item: " + item.getName());

            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                Item currentItem = itemList.get(currentPosition);
                boolean isDeleted = dbHelper.deleteItem(currentItem.getId());

                if (isDeleted) {
                    Log.d("ItemAdapter", "Item successfully deleted from database.");
                    itemList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, itemList.size());
                } else {
                    Log.e("ItemAdapter", "Failed to delete item from database.");
                }
            } else {
                Log.e("ItemAdapter", "Invalid position: " + currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateData(List<Item> newItems) {
        this.itemList = newItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, quantityTextView;
        ImageButton deleteButton;
        ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            quantityTextView = itemView.findViewById(R.id.item_quantity);
            deleteButton = itemView.findViewById(R.id.delete_button);
            itemImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
