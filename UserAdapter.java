package com.example.myapplication4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<String> userList; // List of user IDs

    public UserAdapter(Context context, List<String> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_with_button, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String userId = userList.get(position); // Get the user ID for this position

        // Set the button text to the user ID
        holder.userButton.setText("Chat with " + userId);

        // Set a click listener for the button
        holder.userButton.setOnClickListener(v -> {
            // Navigate to ChatActivity and pass the user ID
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("USER_ID", userId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        Button userButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userButton = itemView.findViewById(R.id.userButton); // Match the ID in item_user_with_button.xml
        }
    }
}
