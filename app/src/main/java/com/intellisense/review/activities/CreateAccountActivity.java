package com.intellisense.review.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    // Input fields for the admin create account form
    private TextInputLayout userNameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private Spinner adminTypeSpinner;
    private Button createAccountButton;

    List<String> adminTypes;

    // Integer to keep track of admin type
    private int adminType = Admin.ADMIN_TYPE_UNKNOWN;

    // Database Object
    private AppDatabase mDb;
    public void onBackPressed(){
        startActivity(new Intent (this, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDb = AppDatabase.getInstance(getApplicationContext());

        userNameLayout = (TextInputLayout)findViewById(R.id.create_account_activity_admin_username);
        emailLayout = (TextInputLayout)findViewById(R.id.create_account_activity_admin_email);
        passwordLayout = (TextInputLayout)findViewById(R.id.create_account_activity_admin_password);
        adminTypeSpinner = (Spinner)findViewById(R.id.create_account_activity_type_spinner);
        createAccountButton = (Button)findViewById(R.id.create_account_activity_button);

        // Get and set the previous instance state
        if(savedInstanceState!=null)
        {
            EditText userNameEditText = userNameLayout.getEditText();
            EditText emailEditText = emailLayout.getEditText();

            userNameEditText.setText(savedInstanceState.getCharSequence(Utilities.ADMIN_USERNAME).toString());
            emailEditText.setText(savedInstanceState.getCharSequence(Utilities.ADMIN_EMAIL).toString());
        }


        adminTypes = new ArrayList<String>();
        adminTypes.add(getString(R.string.type));
        adminTypes.add(getString(R.string.super_admin));
        adminTypes.add(getString(R.string.normal_admin));

        setupSpinner();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameLayout.getEditText().getText().toString();
                String email = emailLayout.getEditText().getText().toString();
                String password = passwordLayout.getEditText().getText().toString();

                performDataValidation(userName,email,password);

            }
        });
    }

    public void setupSpinner()
    {

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adminTypes) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminTypeSpinner.setAdapter(spinnerAdapter);
        adminTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==1)
        {
            adminType = Admin.ADMIN_TYPE_SUPER;
        }
        else if (position == 2)
        {
            adminType = Admin.ADMIN_TYPE_NORMAL;
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    private void performDataValidation(String userName, String email, String password)
    {
        if(TextUtils.isEmpty(userName)||userName.length()==0)
        {
            Toast.makeText(CreateAccountActivity.this,"Username cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean validEmail = isValidEmail(email);
        if(!validEmail)
        {
            Toast.makeText(CreateAccountActivity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password) || password.length()<6)
        {
            Toast.makeText(CreateAccountActivity.this,"Password must be atleast 6 characters long",Toast.LENGTH_SHORT).show();
            return;
        }

        if(adminType == Admin.ADMIN_TYPE_UNKNOWN)
        {
            Toast.makeText(CreateAccountActivity.this, "Please select the admin type", Toast.LENGTH_SHORT).show();
            Log.v("CreateAccountActivity","adminType = "+adminType);
            return;
        }

        addDataInDatabase(userName, email, password);
    }

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void addDataInDatabase(final String userName, final String email, final String password)
    {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Add data in database
                mDb.adminDao().insertAdmin(new Admin(userName,email,password,adminType));

                // Get data from database
                final Admin admin = mDb.adminDao().loadAdmin(email,password);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(admin!=null)
                        {
                            Intent reviewsIntent = new Intent(CreateAccountActivity.this,ReviewsActivity.class);
                            reviewsIntent.putExtra(Utilities.ADMIN_ID,admin.getAdmin_id());
                            reviewsIntent.putExtra(Utilities.ADMIN_TYPE,admin.getType());
                            startActivity(reviewsIntent);
                        }
                        else
                        {
                            Toast.makeText(CreateAccountActivity.this, "Email and password don't match. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String userName = userNameLayout.getEditText().getText().toString();
        String email = emailLayout.getEditText().getText().toString();

        outState.putString(Utilities.ADMIN_USERNAME,userName);
        outState.putString(Utilities.ADMIN_EMAIL,email);
    }
}
