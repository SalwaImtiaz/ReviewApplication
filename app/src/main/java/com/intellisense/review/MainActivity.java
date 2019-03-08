package com.intellisense.review;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.intellisense.review.activities.LoginActivity;
import com.intellisense.review.activities.More_Info_Activity;
import com.intellisense.review.activities.Personal_Info_activity;
import com.intellisense.review.activities.Review_one;
import com.intellisense.review.db_classes.AppDatabase;


public class MainActivity extends AppCompatActivity {
    private AppDatabase mDb;

    String baseUrl = "https://api.github.com/users/SalwaImtiaz/repos";

    boolean doubleBackToExitPressedOnce = false;
    private String url;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed ();
            finishAffinity ();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText ( this, "Please click BACK again to exit", Toast.LENGTH_SHORT ).show ();

        new Handler ().postDelayed ( new Runnable () {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        //Response =(TextView) findViewById ( R.id.Response );
        mDb = AppDatabase.getInstance ( getApplicationContext () );
        Button button = (Button) findViewById ( R.id.login_main_activity_button );
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, LoginActivity.class );
                startActivity ( intent );
            }
        } );

        Button button2 = (Button) findViewById ( R.id.CustomerBtn );
        button2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( MainActivity.this, Review_one.class );
                startActivity ( intent );
            }
        } );
    }

}
