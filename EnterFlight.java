package com.example.aviationsolutions;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EnterFlight extends AppCompatActivity {
    EditText passengerNameEditText, usernameEditText, departureDateEditText, departureTimeEditText, originEditText, destinationEditText, flightNumberEditText;
    Button saveButton;
    FirebaseDatabase database;
    DatabaseReference reference, userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_flight);

        passengerNameEditText = findViewById(R.id.passengerNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        departureDateEditText = findViewById(R.id.departureDateEditText);
        departureTimeEditText = findViewById(R.id.departureTimeEditText);
        originEditText = findViewById(R.id.originEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        flightNumberEditText = findViewById(R.id.flightNumberEditText);
        saveButton = findViewById(R.id.saveButton);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Flights");
        userReference = database.getReference("Users");

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = passengerNameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String date = departureDateEditText.getText().toString();
                String time = departureTimeEditText.getText().toString();
                String origin = originEditText.getText().toString();
                String destination = destinationEditText.getText().toString();
                String flightNumber = flightNumberEditText.getText().toString();

                if (name.isEmpty() || username.isEmpty() || date.isEmpty() || time.isEmpty() || origin.isEmpty() || destination.isEmpty() || flightNumber.isEmpty()) {
                    Toast.makeText(EnterFlight.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the username exists in the database
                userReference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            reference.child(username).orderByChild("flightNumber").equalTo(flightNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot flightSnapshot) {
                                    if (flightSnapshot.exists()) {
                                        Toast.makeText(EnterFlight.this, "Flight number already exists.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        saveFlightDataAndScheduleReminders(name, username, date, time, origin, destination, flightNumber);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(EnterFlight.this, "Error checking flight number", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(EnterFlight.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(EnterFlight.this, "Error checking username", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void saveFlightDataAndScheduleReminders(String name, String username, String date, String time, String origin, String destination, String flightNumber) {
        HelperClassEnterFlight helperClass = new HelperClassEnterFlight(name, username, time, date, origin, destination, flightNumber);
        reference.child(username).push().setValue(helperClass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                showFlightEnteredNotification(username, flightNumber);

                try {
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm a");
                    Date departureDate = sdfDate.parse(date);
                    Date departureTime = sdfTime.parse(time);

                    Calendar departureCalendar = Calendar.getInstance();
                    departureCalendar.setTime(departureDate);
                    Calendar timeCalendar = Calendar.getInstance();
                    timeCalendar.setTime(departureTime);

                    // Setting the hour and minute in the departureCalendar
                    departureCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
                    departureCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));

                    // Reminder for 1 day before
                    Calendar oneDayBefore = (Calendar) departureCalendar.clone();
                    oneDayBefore.add(Calendar.DAY_OF_YEAR, -1);
                    setReminder(oneDayBefore.getTimeInMillis(), username, "Your flight is tomorrow!", flightNumber);

                    // Reminder for 6 hours before
                    Calendar sixHoursBefore = (Calendar) departureCalendar.clone();
                    sixHoursBefore.add(Calendar.HOUR_OF_DAY, -6);
                    setReminder(sixHoursBefore.getTimeInMillis(), username, "Your flight is in 6 hours!", flightNumber);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(EnterFlight.this, "Failed to enter flight", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFlightEnteredNotification(String username, String flightNumber) {
        // Handle push notifications via Firebase Cloud Messaging
        Intent intent = new Intent(EnterFlight.this, ReminderReceiver.class);
        intent.putExtra("username", username);
        intent.putExtra("flightNumber", flightNumber);
        intent.putExtra("message", "Your flight " + flightNumber + " has been successfully entered!");

        sendBroadcast(intent);
    }

    private void setReminder(long timeInMillis, String username, String message, String flightNumber) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("username", username);
        intent.putExtra("flightNumber", flightNumber);
        intent.putExtra("message", message + " Flight: " + flightNumber);

        int requestId = (username + flightNumber).hashCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                requestId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }
}
