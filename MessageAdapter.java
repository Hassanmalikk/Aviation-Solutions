package com.example.myapplication4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<Message> messageList;

    // Constructor
    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList != null ? messageList : new ArrayList<>();
    }

    // Method to update the message list
    public void updateMessageList(List<Message> newMessageList) {
        this.messageList = newMessageList != null ? newMessageList : new ArrayList<>();
        notifyDataSetChanged(); // Notify RecyclerView of data changes
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        Log.d("MessageAdapter", "Binding message: " + message.toString());

        // Bind message data with null checks
        holder.senderTextView.setText(message.getSender() != null ? message.getSender() : "Unknown");
        holder.messageTextView.setText(message.getText() != null ? message.getText() : "");
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // ViewHolder class for messages
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView, messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
