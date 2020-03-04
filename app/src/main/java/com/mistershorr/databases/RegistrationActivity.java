package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegistrationActivity extends AppCompatActivity {

    public static final String TAG = RegistrationActivity.class.getSimpleName();

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonCreateAccount;
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show the back button

        wireWidgets();
        setListeners();
    }

    // there is so much toxic male energy in this class

    private void setListeners() {
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBackendlessAccount();
            }
        });
    }// and like he was like "Open your legs." like dead serious and super scary and i was like no. and then he tried to like open my legs and kept saying that

    private void createBackendlessAccount() {
        // TODO update to make this work with startActivityForResult

        // create account on backendless

        BackendlessUser user = new BackendlessUser();
        user.setProperty( "email", editTextEmail.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
        //user.setEmail(editTextEmail.getText().toString());
        user.setProperty("name", editTextName.getText().toString());
        user.setProperty("username", editTextUsername.getText().toString());

        if(editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()))
        {
            Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
            {
                public void handleResponse( BackendlessUser registeredUser )
                {
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();
                    Intent registrationCompleteIntent = new Intent();
                    registrationCompleteIntent.putExtra(EXTRA_USERNAME, username);
                    registrationCompleteIntent.putExtra(EXTRA_PASSWORD, password);
                    setResult(RESULT_OK, registrationCompleteIntent);
                    finish();
                }

                public void handleFault( BackendlessFault fault )
                {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    Toast.makeText(RegistrationActivity.this, fault.getDetail(), Toast.LENGTH_SHORT).show();
                }
            } );
        }
        else {
            Toast.makeText(RegistrationActivity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
        }



        // finish the activity

        // also going to send back the username and password

    }

    private void wireWidgets() {
        editTextUsername = findViewById(R.id.edit_text_create_account_username);
        editTextPassword = findViewById(R.id.edit_text_create_account_password);
        editTextName = findViewById(R.id.edit_text_create_account_name);
        editTextEmail = findViewById(R.id.edit_text_create_account_email);
        editTextConfirmPassword = findViewById(R.id.edit_text_create_account_confirm_password);
        buttonCreateAccount = findViewById(R.id.button_create_account);
    }
}
