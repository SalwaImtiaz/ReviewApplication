package com.intellisense.review.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.intellisense.review.MainActivity;
import com.intellisense.review.R;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;
import com.intellisense.review.db_classes.Review;
import com.intellisense.review.db_classes.Server;
import com.intellisense.review.db_classes.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class More_Info_Activity extends AppCompatActivity {
EditText tableNo = null,suggestions = null;
Button btn_done,btnMove;
Spinner spinnerItems,spinnerServer;
TextView thanks,txtclose;
String id_server,id_food ;
    SessionManager session;
    private AppDatabase mDb;
    private List<String> servers ;
    private List<String> Items ;
    public  Dialog mydialog;
//    @Override
//    public void onBackPressed(){
//        finishAffinity();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_more__info_ );
        mydialog = new Dialog ( this );
        mDb = AppDatabase.getInstance ( getApplicationContext () );
        session = new SessionManager (getApplicationContext ());
        initViews();
        addSpinnerItems();
        addSpinnerServers();

        //save btn
        btn_done.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    AppExecutors.getInstance ().diskIO ().execute ( new Runnable () {
                        @Override
                        public void run() {
                            String tableno = tableNo.getText ().toString ();
                            String suggest = suggestions.getText ().toString ();

                            Date date = new Date();
                            HashMap<String, String> user = session.getUserDetails ();
                            final String rate = user.get ( SessionManager.ratings );
                            final Float floatRate = Float.parseFloat ( rate );
                            final int server2 = Integer.parseInt ( id_server );

                            final String customerName = user.get ( SessionManager.customer_name );
                            final String customerContact = user.get ( SessionManager.customer_contact_no );
                            final String customerEmail = user.get ( SessionManager.customer_email );
                            final String customerBirth = user.get ( SessionManager.customer_birthday );
                            final String customerAnniversary = user.get ( SessionManager.customer_anniversary );
//                            final Date date = new Date ();
                            // float rates = mDb.responseDao ().getID ();

                            Review review = new Review (  customerName, customerEmail, customerContact, customerBirth, customerAnniversary, server2, id_food, floatRate, suggest, tableno, date ,1,date);
                            mDb.reviewDao ().insertReview ( review );
//
        StringBuilder sb5 = new StringBuilder();
        List<Review> ques = mDb.reviewDao ().loadAllReviews ();
//        String quizes = mDb.serverDao ().getServerName ();
//
//
///////////////////////////////////////////////////////////////////////////////////////////delete
//       // mDb.reviewQuestionsDao ().deleteAll ();
        for (Review quest : ques) {
            sb5.append(String.format( Locale.US,
                    "%s - %s - %s,\n",quest.getEntry_time (), quest.getCompany_id (),quest.getService_offered_ids ()));
        }
                            Log.e("formattedDate:" ," "+sb5);
//        servers.setText(sb5);
//


                            String review_test = String.valueOf ( mDb.reviewDao ().loadAllReviews () );
                            Log.e("All reviews",review_test);
                            Log.e ( "values from session", customerName + " " + customerContact + " " + customerEmail + " " + floatRate + " " + server2 + " " + id_food + " " + tableno + " " + suggest + " " + date + " " + customerAnniversary );


                            runOnUiThread ( new Runnable () {
                                @Override
                                public void run() {
                                    //thanks.setVisibility ( View.VISIBLE );
                                                mydialog.setContentView ( R.layout.custompop );
                                                mydialog.show ();
                                    new Handler ().postDelayed( new Runnable() {
                                        @Override
                                        public void run() {
                                            mydialog.dismiss ();
                                            startActivity(new Intent (More_Info_Activity.this, MainActivity.class));
                                        }
                                    }, 4000);


                                            }

                            } );


                        }
                    } );

                }
            }
        });
    }

    public void addSpinnerServers() {
        final StringBuilder sb4 = new StringBuilder ();
        AppExecutors.getInstance ().diskIO ().execute ( new Runnable () {
            @Override
            public void run() {
                servers = mDb.serverDao ().findServernames ();
                //ques = mDb.serverDao ().findServer ();
                runOnUiThread ( new Runnable () {
                    @Override
                    public void run() {

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> ( More_Info_Activity.this, android.R.layout.simple_spinner_item, servers );
                        dataAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
                        spinnerServer.setAdapter ( dataAdapter );
                        spinnerServer.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int pos, long id) {
                                Object item = parent.getItemAtPosition ( pos );
                                Log.i ( "Id", item.toString () + " " + id + 1 );
                                id_server = String.valueOf ( id+1 );


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                Log.i ( "Message", "Nothing is selected" );

                            }


                        } );
                    }
                } );

            }
        } );
    }

    public void addSpinnerItems(){
        final StringBuilder sb5 = new StringBuilder();
        AppExecutors.getInstance().diskIO().execute( new Runnable() {
            @Override
            public void run() {
                Items = mDb.itemsDao ().findItemnames();

                runOnUiThread ( new Runnable () {
                    @Override
                    public void run() {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(More_Info_Activity.this,android.R.layout.simple_spinner_item, Items);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerItems.setAdapter(dataAdapter);
                        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int pos, long id) {


                                Object item = parent.getItemAtPosition ( pos );
                                Log.i ( "Id", item.toString () + " " + id+1 );
                                id_food = String.valueOf ( id+1 );
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                Log.i ( "Message", "Nothing is selected" );

                            }
                        });
                    }
                } );
            }
        });


        }
    private void initViews(){
        tableNo = (EditText) findViewById ( R.id.tableNo );
        suggestions = (EditText) findViewById ( R.id.suggestions );
        btn_done = (Button)findViewById ( R.id.btndone );
        btnMove = (Button) findViewById ( R.id.btnMove );
        spinnerItems = (Spinner) findViewById ( R.id.spinnerItems ) ;
        spinnerServer = (Spinner) findViewById ( R.id.spinnerServer );
        thanks = (TextView) findViewById ( R.id.thanks );
        txtclose =(TextView) findViewById ( R.id.txtclose );
    }

//    public void btn_done (View view){
//
//    }
}
