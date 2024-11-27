package com.example.myapplication4;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserChatActivity extends AppCompatActivity {

    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private String userID;

    private EditText messageEditText;
    private Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get the logged-in user's ID
        userID = getIntent().getStringExtra("USER_ID");

        if (userID == null || userID.isEmpty()) {
            Toast.makeText(this, "Invalid user. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize UI components
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messageList);
        messageRecyclerView.setAdapter(messageAdapter);

        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        // Set up the Send button
        sendMessageButton.setOnClickListener(v -> sendUserMessage());

        // Load existing messages for this user
        loadMessages();
    }

    private void loadMessages() {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance()
                .getReference("chats")
                .child(userID);

        messagesRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                messageList.clear();
                for (com.google.firebase.database.DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                Log.d("Firebase", "Messages for user " + userID + ": " + messageList);
                messageAdapter.updateMessageList(messageList);
            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                Toast.makeText(UserChatActivity.this, "Failed to load messages: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendUserMessage() {
        String messageText = messageEditText.getText().toString().trim();

        if (messageText.isEmpty()) {
            Toast.makeText(this, "Please type a message.", Toast.LENGTH_SHORT).show();
            return;
        }

        messageEditText.setText("");

        DatabaseReference chatRef = FirebaseDatabase.getInstance()
                .getReference("chats")
                .child(userID);

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("text", messageText);
        messageData.put("sender", userID);
        messageData.put("type", "user");

        chatRef.push().setValue(messageData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to send message.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
