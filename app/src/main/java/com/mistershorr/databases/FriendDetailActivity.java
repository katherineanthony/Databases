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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class FriendDetailActivity extends AppCompatActivity {

    private Switch switchAwesomeness;
    private SeekBar seekBarClumsiness;
    private RatingBar ratingBarTrustworthiness; //star scale
    private SeekBar seekBarGymFrequency;
    private EditText editTextMoneyOwed;
    private Friend friend;
    private Button buttonSave;

    private EditText editTextFriendName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        wireWidgets();
        setListeners();
        Intent addFriend = getIntent();
        friend = addFriend.getParcelableExtra(FriendListActivity.EXTRA_FRIEND);

        if(friend != null)
        {

            seekBarGymFrequency.setProgress((int) friend.getGymFrequency());
            seekBarClumsiness.setProgress(friend.getClumsiness());
            editTextMoneyOwed.setText(String.valueOf(Double.valueOf(friend.getMoneyOwed())));

            // continue to set values to that of friend
        }
        else {
            friend = new Friend();
            seekBarGymFrequency.setProgress(0);
            seekBarClumsiness.setProgress(0);
            switchAwesomeness.setChecked(false);
            ratingBarTrustworthiness.setNumStars(0);
            editTextFriendName.setText("Friend");
            editTextMoneyOwed.setText("0.00");
            // then do an if else and find if is open friend
            // if it is, then we will get their objectId and pull their info
            // también, si queremos, necesitamos obtener el ownerId

        }

    }

    private void saveFriendToBackendless(){
        friend.setOwnerId(Backendless.UserService.CurrentUser().getUserId());
        friend.setName(editTextFriendName.getText().toString());
        friend.setAwesome(switchAwesomeness.isChecked());
        friend.setClumsiness(seekBarClumsiness.getProgress() + 1);
        friend.setGymFrequency(seekBarGymFrequency.getProgress() + 1);
        friend.setTrustworthiness(ratingBarTrustworthiness.getNumStars());
        friend.setMoneyOwed(Double.parseDouble(editTextMoneyOwed.getText().toString()));


        Backendless.Persistence.of(Friend.class).save(friend, new AsyncCallback<Friend>() {
            @Override
            public void handleResponse(Friend response) {
                // make toast that friend is saved
                // send person back to the listActivity
                Toast.makeText(FriendDetailActivity.this, "You have saved your friend", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(FriendDetailActivity.this, "You have not saved your friend. Take the L", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setListeners() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFriendToBackendless();
            }
        });
        switchAwesomeness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchAwesomeness.isChecked())
                {
                    switchAwesomeness.setText("Awesome");
                }
                else{
                    switchAwesomeness.setText("Not Awesome");
                }
            }
        });
    }

    private void wireWidgets() {
        switchAwesomeness=findViewById(R.id.switch_detail_awesome);
        seekBarClumsiness=findViewById(R.id.seekBar_detail_clumsiness);
        seekBarGymFrequency=findViewById(R.id.seekBar_detail_gymFrequency);
        editTextMoneyOwed=findViewById(R.id.editText_detail_moneyOwed);
        editTextFriendName=findViewById(R.id.editText_detail_friendName);
        ratingBarTrustworthiness=findViewById(R.id.ratingBar_detail_trustworthiness);
        buttonSave=findViewById(R.id.button_detail_update);

    }
}
