package com.example.android.mogifortourists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();

        ImageView imageView = (ImageView) findViewById(R.id.details_image_view);
        imageView.setImageResource(extras.getInt("IMAGE_ID"));

        TextView titleTextView = (TextView) findViewById(R.id.details_title_text_view);
        titleTextView.setText(extras.getString("TITLE"));

        TextView textTextView = (TextView) findViewById(R.id.details_text_text_view);
        textTextView.setText(extras.getString("TEXT"));

        TextView whenTextView = (TextView) findViewById(R.id.details_when_text_view);
        whenTextView.setText(extras.getString("WHEN"));

        TextView whereTextView = (TextView) findViewById(R.id.details_where_text_view);
        whereTextView.setText(extras.getString("WHERE"));
    }
}
