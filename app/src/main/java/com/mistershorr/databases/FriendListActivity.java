package com.mistershorr.databases;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    private ListView listViewFriend;
    private List<Friend> friendList;

    private TextView textViewOverallLikability;
    private TextView textViewName;
    private TextView textViewMoneyOwed;
    private FloatingActionButton addFriendFloatingActionButton;

    private FriendAdapter friendAdapter;

    public static final String EXTRA_FRIEND = "the friend";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        wireWidgets();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDataFromBackendless();
    }

    private void loadDataFromBackendless(){

        Backendless.initApp(this, Credentials.APP_ID, Credentials.API_KEY);

        String userId = Backendless.UserService.CurrentUser().getObjectId();

        // ownerId = 9B113F7E-2040-C216-FF78-718758640D00

        String whereClause = "ownerId = '" + userId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);


        Backendless.Data.of(Friend.class).find(queryBuilder, new AsyncCallback<List<Friend>>(){
            @Override
            public void handleResponse(final List<Friend> foundFriends)
            {
                // all Contact instances have been found
                friendList = foundFriends;
                friendAdapter = new FriendAdapter(friendList);
                listViewFriend.setAdapter(friendAdapter);
                listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent openFriend = new Intent
                                (FriendListActivity.this, FriendDetailActivity.class);
                        openFriend.putExtra(EXTRA_FRIEND, foundFriends.get(i));
                        startActivity(openFriend);
                        //add an option for delete?
                    }
                });
                Log.d("Loaded friends", "handleResponse: " + foundFriends.toString());

            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }


    private void setListeners() {
        addFriendFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addANewFriend = new Intent
                        (FriendListActivity.this, FriendDetailActivity.class);
                startActivity(addANewFriend);
            }
        });
        // the onClickListener for the listView is in the backendless data call

    }

    // these two methods are for the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friendlist_sorting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_friendlist_sortByName:
                sortByName();
                friendAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_friendlist_sortByMoney:
                sortByMoney();
                friendAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByName(){
        Collections.sort(friendList, new Comparator<Friend>() {
            @Override
            public int compare(Friend friend, Friend t1) {
                return friend.getName().compareTo(t1.getName());
            }
        });
    }
    private void sortByMoney(){
        Collections.sort(friendList);
    }

    private void wireWidgets() {
        listViewFriend=findViewById(R.id.listView_list_friendList);
        addFriendFloatingActionButton=findViewById(R.id.floatingActionButton_list_addFriend);
    }

    private class FriendAdapter extends ArrayAdapter<Friend> {
        // make an instance variable to keep track of the hero list
        private List<Friend> friendList;

        public FriendAdapter(List<Friend> friendList) {
            // since we're in the HeroListActivity class, we already have the context
            // we're hardcoding in a particular layout, so we don't need to put it in
            // the constructor either
            super(FriendListActivity.this, -1, friendList);
            this.friendList=friendList;
        } //im worried that hes going to tell his friends that im a bad kisser




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1. inflate a layout
            LayoutInflater inflater = getLayoutInflater();

            // check if convertView is null, if so, replace it
            if(convertView == null)
            {
                // R.layout.item_hero is a custom layout we can make that represents
                // what a single item would look like in our listview
                convertView = inflater.inflate(R.layout.item_friend, parent, false);
            }

            // 2. wire widgets and link the hero to those widgets
            // instead of calling findViewById at the activity class leve,
            // we're calling it from the inflated layout to find THOSE widgets
            textViewMoneyOwed = convertView.findViewById(R.id.textView_item_moneyOwed);
            textViewName = convertView.findViewById(R.id.textView_item_name);



            // do this for as many widgets as you need

            // set the value for each widget. use the position parameter variable
            // to get the hero that you need out of the list
            // and set the values for widgets.

            textViewName.setText(String.valueOf(friendList.get(position).getName()));
            textViewMoneyOwed.setText(String.valueOf(friendList.get(position).getMoneyOwed()));



            // 3. return the inflated view
            return convertView;

        }
    }
}
