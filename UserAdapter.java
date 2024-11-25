package com.example.aviation_solutions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    // Constructor for the adapter
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user1, parent, false);  // Assuming the layout file is item_user1.xml
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        Log.d("RecyclerViewBinding", "Binding User: " + user.getName() + ", Email: " + user.getEmail());

        // Bind the data to the views
        holder.userName.setText(user.getName());
        holder.userEmail.setText(user.getEmail());
        holder.userPhone.setText(user.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // ViewHolder class for each user item
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail, userPhone;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userPhone = itemView.findViewById(R.id.user_phone);
        }
    }

    // Method to update the list of users in the adapter
    public void updateUserList(List<User> updatedUserList) {
        this.userList = updatedUserList;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }
}
