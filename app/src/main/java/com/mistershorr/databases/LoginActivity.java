package com.mistershorr.databases;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String EXTRA_USERNAME = "login username";
    public static final int REQUEST_CREATE_ACCOUNT = 1;

    private TextView textViewCreateAccount;
    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize backendless
        Backendless.initApp(this, Credentials.APP_ID, Credentials.API_KEY);

        wireWidgets();
        setListeners();

        // preload the username
        String username = getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME);
        editTextUsername.setText(username);
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToBackendless();
            }
        });

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO replace create account startActivity with startActivityForResult
                Intent createAccountIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                createAccountIntent.putExtra(EXTRA_USERNAME, editTextUsername.getText().toString());
                //startActivity(createAccountIntent);
                startActivityForResult(createAccountIntent, REQUEST_CREATE_ACCOUNT);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == REQUEST_CREATE_ACCOUNT)
        {
            if(resultCode == RESULT_OK)
            {
                // use the intent from the parameter to extract the username and password
                // and prefill them into the editText fields here
                editTextUsername.setText(getIntent().getStringExtra(RegistrationActivity.EXTRA_USERNAME));
                editTextPassword.setText(getIntent().getStringExtra(RegistrationActivity.EXTRA_PASSWORD));
            }
        }
    }

    private void loginToBackendless() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if(!username.isEmpty() && !password.isEmpty())
        {
            Backendless.UserService.login( username, password, new AsyncCallback<BackendlessUser>()
            {
                public void handleResponse( BackendlessUser user )
                {
                    // user has been logged in
                    Toast.makeText(LoginActivity.this, "Welcome "+ user.getProperty("username") + "!", Toast.LENGTH_SHORT).show();
                }
                public void handleFault( BackendlessFault fault )
                {
                    // login failed, to get the error code call fault.getCode()
                    Toast.makeText(LoginActivity.this, fault.getDetail(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Enter username and password", Toast.LENGTH_SHORT).show();
        }

        // do not forget to call Backendless.initApp in the app initialization code


    }

    private void wireWidgets() {
        textViewCreateAccount = findViewById(R.id.textview_login_create_account);
        buttonLogin = findViewById(R.id.button_login_login);
        editTextUsername = findViewById(R.id.edit_text_login_username);
        editTextPassword = findViewById(R.id.edit_text_login_password);
    }
}
