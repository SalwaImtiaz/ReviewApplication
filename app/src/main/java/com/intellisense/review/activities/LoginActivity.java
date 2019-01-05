package com.intellisense.review.activities;

import android.content.Intent;
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

import com.intellisense.review.R;
import com.intellisense.review.Utilities;
import com.intellisense.review.db_classes.Admin;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;

import java.io.UTFDataFormatException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    // Input fields for the admin login form
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private Button loginButton;

    // Database Object
    private AppDatabase mDb;

    private TextView createAccountLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDb = AppDatabase.getInstance(getApplicationContext());

        emailLayout = (TextInputLayout)findViewById(R.id.activity_login_admin_email);
        passwordLayout = (TextInputLayout)findViewById(R.id.activity_login_admin_password);
        loginButton = (Button)findViewById(R.id.activity_login_button);

        // Get and set the previous instance state
        if(savedInstanceState!=null)
        {
            EditText emailEditText = emailLayout.getEditText();
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
                String email = emailLayout.getEditText().getText().toString();
                String password = passwordLayout.getEditText().getText().toString();
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

        String email = emailLayout.getEditText().getText().toString();
        outState.putString(Utilities.ADMIN_EMAIL,email);
    }
}
