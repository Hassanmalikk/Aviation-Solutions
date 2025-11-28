package com.example.aviation_solutions;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<Feedback> {

    private Context context;
    private List<Feedback> feedbackList;

    public FeedbackAdapter(Context context, List<Feedback> feedbackList) {
        super(context, 0, feedbackList);
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.feedback_item, parent, false);
        }

        Feedback feedback = feedbackList.get(position);

        TextView dateTimeView = convertView.findViewById(R.id.dateTimeView);
        TextView userEmailView = convertView.findViewById(R.id.userEmailView);
        TextView feedbackMessageView = convertView.findViewById(R.id.feedbackMessageView);

        dateTimeView.setText(feedback.getDateTime());
        userEmailView.setText(feedback.getUserEmail());
        feedbackMessageView.setText(feedback.getFeedbackMessage());


        return convertView;
    }


}
