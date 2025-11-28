package com.example.aviationsolutions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FlightAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FlightRetrieval> flights;

    public FlightAdapter(Context context, ArrayList<FlightRetrieval> flights) {
        this.context = context;
        this.flights = flights;
    }

    @Override
    public int getCount() {
        return flights.size();
    }

    @Override
    public Object getItem(int position) {
        return flights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.flight_list_item, parent, false);
        }

        FlightRetrieval flight = flights.get(position);

        TextView flightNumberTextView = convertView.findViewById(R.id.flightNumberTextView);
        TextView destinationTextView = convertView.findViewById(R.id.destinationTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);
        Button shareButton = convertView.findViewById(R.id.ShareButton);

        flightNumberTextView.setText(flight.getFlightNumber());
        destinationTextView.setText(flight.getDestination());
        statusTextView.setText(flight.getStatus());

        // Show the share button only if the flight is scheduled
        if (flight.getStatus() != null && flight.getStatus().startsWith("Scheduled")) {
            shareButton.setVisibility(View.VISIBLE);
            shareButton.setOnClickListener(v -> shareFlightDetails(flight));
        } else {
            shareButton.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void shareFlightDetails(FlightRetrieval flight) {
        String shareText = "Flight Number: " + flight.getFlightNumber() +
                "\nDestination: " + flight.getDestination() +
                "\nDeparture Date: " + flight.getDept_date() +
                "\nDeparture Time: " + flight.getDept_time() +
                "\nOrigin: " + flight.getOrigin() +
                "\nName: " + flight.getName();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, "Share Flight Details"));
    }
}
