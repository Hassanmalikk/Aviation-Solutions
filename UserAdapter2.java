package com.example.aviation_solutions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter2 extends RecyclerView.Adapter<UserAdapter2.UserViewHolder> {

    private List<User> userList;
    private UserSelectionListener listener;

    public interface UserSelectionListener {
        void onUserSelected(String email);
        void onUserDeselected(String email);
    }

    public UserAdapter2(List<User> userList, UserSelectionListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailCheckBox.setText(user.getEmail());
        holder.emailCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listener.onUserSelected(user.getEmail());
            } else {
                listener.onUserDeselected(user.getEmail());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public CheckBox emailCheckBox;

        public UserViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            emailCheckBox = view.findViewById(R.id.emailCheckBox);
        }
    }
}
