package com.intellisense.review.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.intellisense.review.MainActivity;
import com.intellisense.review.R;
import com.intellisense.review.Utilities;
import com.intellisense.review.db_classes.Admin;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;

import org.w3c.dom.Text;

import java.io.UTFDataFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
//shared preferences

private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private final String DefaultUnameValue = "";
    private String UnameValue;

    // Input fields for the admin login form
    private EditText emailLayout;
    private EditText passwordLayout;
    private Button loginButton;
    private TextView switch_user;


    // Database Object
    private AppDatabase mDb;

    private TextView createAccountLink;

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = emailLayout.getText ().toString ();
        System.out.println("onPause save name: " + UnameValue);
        editor.putString(PREF_UNAME, UnameValue);
        editor.commit();

    }
public void setSwitch_user(View v){
    emailLayout.setText ( "" );
}
    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        if(UnameValue.length () >0 )
        { switch_user.setVisibility ( View.VISIBLE );

        }
        emailLayout.setText ( UnameValue );
        System.out.println("onResume load name: " + UnameValue);
    }
    public void onBackPressed(){
        startActivity(new Intent (this, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDb = AppDatabase.getInstance(getApplicationContext());

        switch_user = (TextView) findViewById ( R.id.switch_user );
        emailLayout = (EditText)findViewById(R.id.activity_login_admin_email);
        passwordLayout = (EditText)findViewById(R.id.activity_login_admin_password);
        loginButton = (Button)findViewById(R.id.activity_login_button);


        // Get and set the previous instance state
        if(savedInstanceState!=null)
        {
            EditText emailEditText = emailLayout;
            emailEditText.setText(savedInstanceState.getCharSequence(Utilities.ADMIN_EMAIL).toString());
        }

        createAccountLink = (TextView)findViewById(R.id.activity_login_create_account_textview);
        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createAccountIntent = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(createAccountIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailLayout.getText().toString();
                String password = passwordLayout.getText().toString();
                performDataValidation(email,password);
            }
        });


    }

    private void performDataValidation(String email, String password)
    {

        boolean validEmail = isValidEmail(email);
        if(!validEmail)
        {
            Toast.makeText(LoginActivity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password == null || TextUtils.isEmpty(password))
        {
            Toast.makeText(LoginActivity.this,"Please fill the password field",Toast.LENGTH_SHORT).show();
            return;
        }

        login(email, password);
    }

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void login(final String email, final String password)
    {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Admin loginAdmin = mDb.adminDao().loadAdmin(email,password);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(loginAdmin!=null)
                        {
                            Intent intent = new Intent(LoginActivity.this,ReviewsActivity.class);
                            intent.putExtra(Utilities.ADMIN_ID,loginAdmin.getAdmin_id());
                            intent.putExtra(Utilities.ADMIN_TYPE,loginAdmin.getType());
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Email and password don't match. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String email = emailLayout.getText().toString();
        outState.putString(Utilities.ADMIN_EMAIL,email);
    }
}
