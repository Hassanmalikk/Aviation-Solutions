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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private String selectedUserID;

    private EditText messageEditText; // Input field for agent's reply
    private Button sendMessageButton; // Button for sending agent's reply

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get the selected user ID from the intent
        selectedUserID = getIntent().getStringExtra("USER_ID");

        // Initialize UI components
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messageList);
        messageRecyclerView.setAdapter(messageAdapter);

        messageEditText = findViewById(R.id.messageEditText);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        // Set up the Send button to handle agent replies
        sendMessageButton.setOnClickListener(v -> sendAgentReply());

        // Load messages for the selected user
        loadMessagesForSelectedUser();
    }

    private void loadMessagesForSelectedUser() {
        if (selectedUserID == null) {
            Toast.makeText(this, "No user selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("chats").child(selectedUserID);

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
                Log.d("Firebase", "Messages for user " + selectedUserID + ": " + messageList);
                messageAdapter.updateMessageList(messageList);
            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Failed to load messages: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendAgentReply() {
        String replyText = messageEditText.getText().toString().trim();

        // Validate the input
        if (replyText.isEmpty()) {
            Toast.makeText(this, "Please type a message to send.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedUserID == null || selectedUserID.isEmpty()) {
            Toast.makeText(this, "No user selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear the input field
        messageEditText.setText("");

        // Reference to the user's chat node in Firebase
        DatabaseReference chatRef = FirebaseDatabase.getInstance()
                .getReference("chats")
                .child(selectedUserID);

        // Create the message data
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("text", replyText);
        messageData.put("sender", "Agent");
        messageData.put("type", "agent");

        // Push the message to Firebase
        chatRef.push().setValue(messageData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to send message.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
