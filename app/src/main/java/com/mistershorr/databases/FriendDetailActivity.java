package com.mistershorr.databases;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FriendDetailActivity extends AppCompatActivity {

    private EditText editTextFriendName;
    private Switch switchAwesomeness;
    private SeekBar seekBarClumsiness;
    private RatingBar ratingBarTrustworthiness; //star scale
    private SeekBar seekBarGymFrequency;
    private EditText editTextMoneyOwed;

    private TextView textViewFriendName;
    private TextView textViewAwesome;
    private TextView textViewClumsiness;
    private TextView textViewTrustworthiness;
    private TextView textViewGymFrequency;
    private TextView textViewMoneyOwed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
