package com.example.myapplication4;

import android.os.Bundle;
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

public class UserActivity extends AppCompatActivity {

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

        // Get the user ID passed from LoginActivity
        userID = getIntent().getStringExtra("userID");

        if (userID == null) {
            Toast.makeText(this, "Error: Missing user ID.", Toast.LENGTH_SHORT).show();
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

        sendMessageButton.setOnClickListener(v -> sendMessage());

        // Load chat history
        loadChatHistory();
    }

    private void loadChatHistory() {
        DatabaseReference chatRef = FirebaseDatabase.getInstance()
                .getReference("chats")
                .child(userID);

        chatRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                messageAdapter.updateMessageList(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Message cannot be empty.", Toast.LENGTH_SHORT).show();
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
