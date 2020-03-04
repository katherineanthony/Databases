package com.mistershorr.databases;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;

public class FriendDetailActivity extends AppCompatActivity {

    private Switch switchAwesomeness;
    private SeekBar seekBarClumsiness;
    private RatingBar ratingBarTrustworthiness; //star scale
    private SeekBar seekBarGymFrequency;
    private SeekBar overallLikability;
    private EditText editTextMoneyOwed;
    private Friend friend;
    private Button buttonSave;

    private TextView textViewFriendName;

    private boolean isAwesome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        wireWidgets();

        setListeners();
        Intent addFriend = getIntent().getParcelableExtra(FriendListActivity.EXTRA_FRIEND);
        if(addFriend != null)
        {
            if(addFriend.equals("addANewFriend"))
            {
                friend = new Friend();
                seekBarGymFrequency.setProgress(0);
                seekBarClumsiness.setProgress(0);
                switchAwesomeness.setChecked(false);
                ratingBarTrustworthiness.setNumStars(0);
                textViewFriendName.setText("Friend");
                editTextMoneyOwed.setText("0.00");
                overallLikability.setProgress(0);

                // set everything to zero/default values
            }
            else {

            }
            // then do an if else and find if is open friend
            // if it is, then we will get their objectId and pull their info
            // también, si queremos, necesitamos obtener el ownerId

        }

    }

    private void saveFriendToBackendless(){
        friend.setName(textViewFriendName.getText().toString());
        friend.setAwesome(switchAwesomeness.isChecked());
        friend.setClumsiness(seekBarClumsiness.getProgress() + 1);
        friend.setGymFrequency(seekBarGymFrequency.getProgress() + 1);
        friend.setTrustworthiness(ratingBarTrustworthiness.getNumStars());
        friend.setOverallLikability(overallLikability.getProgress());
        friend.setMoneyOwed(Double.parseDouble(editTextMoneyOwed.getText().toString()));


        Backendless.Persistence.of(Friend.class);
    }

    private void setListeners() {

        switchAwesomeness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(isAwesome)
                {
                    isAwesome=false;
                    friend.setAwesome(isAwesome);
                }
                else
                {
                    isAwesome=true;
                    friend.setAwesome(isAwesome);
                }
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFriendToBackendless();
            }
        });
        seekBarClumsiness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                friend.setClumsiness(seekBarClumsiness.getProgress() + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarGymFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                friend.setGymFrequency(seekBarGymFrequency.getProgress() + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ratingBarTrustworthiness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                friend.setTrustworthiness(ratingBarTrustworthiness.getNumStars());
            }
        });


    }

    private void wireWidgets() {
        switchAwesomeness=findViewById(R.id.switch_detail_awesome);
        seekBarClumsiness=findViewById(R.id.seekBar_detail_clumsiness);
        seekBarGymFrequency=findViewById(R.id.seekBar_detail_gymFrequency);
        editTextMoneyOwed=findViewById(R.id.editText_detail_moneyOwed);
        textViewFriendName=findViewById(R.id.textView_detail_personName);
        ratingBarTrustworthiness=findViewById(R.id.ratingBar_detail_trustworthiness);
        overallLikability=findViewById(R.id.seekBar_detail_likability);
        buttonSave=findViewById(R.id.button_detail_update);

    }
}
