package com.intellisense.review;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.intellisense.review.activities.LoginActivity;
import com.intellisense.review.activities.Personal_Info_activity;
import com.intellisense.review.activities.Review_one;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;
import com.intellisense.review.db_classes.Response;
import com.intellisense.review.db_classes.Review_Questions;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AppDatabase mDb;
TextView Response ;
    StringBuilder sb = new StringBuilder ();
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
//        AppExecutors.getInstance ().diskIO ().execute ( new Runnable () {
//            @Override
//            public void run() {
//                List<Response> respon = mDb.responseDao ().getResponse ();
//
//                for (Response resp : respon) {
//                    sb.append ( String.format ( Locale.US,
//                            "%s - %s - %s - %s \n", resp.getResponse_id (),resp.getReview_id (),resp.getQuestion_id (),resp.getResponse () ) );
//                }
//
//                runOnUiThread ( new Runnable () {
//                    @Override
//                    public void run() {
//                    Response.setText ( sb );
//                    }
//                } );
//            }
//        } );
    }
}