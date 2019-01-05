package com.intellisense.review.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.intellisense.review.db_classes.Items_Served;
import com.intellisense.review.db_classes.Response;
import com.intellisense.review.db_classes.Review;
import com.intellisense.review.db_classes.Review_Questions;
import com.intellisense.review.db_classes.Server;
import com.intellisense.review.db_classes.SessionManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class More_Info_Activity extends AppCompatActivity {
EditText tableNo = null,suggestions = null;
Button btnSave;
Spinner spinnerItems,spinnerServer;
TextView thanks;
String id_server = "Not selected",id_food = "Not specified";
    SessionManager session;
    private AppDatabase mDb;
    private List<String> servers ;
    private List<String> Items ;
    private List<String> Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_more__info_ );
        mDb = AppDatabase.getInstance ( getApplicationContext () );
        session = new SessionManager (getApplicationContext ());
        initViews();
        addSpinnerItems();
        addSpinnerServers();

        //save btn
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    AppExecutors.getInstance().diskIO().execute( new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         String tableno = tableNo.getText ().toString ();
                                                                         String suggest = suggestions.getText ().toString ();

                                                                         HashMap<String, String> user = session.getUserDetails ();
                                                                         final String rate = user.get ( SessionManager.ratings ); final Float floatRate= Float.parseFloat(rate);
                                                                         final String suggest2 = user.get ( SessionManager.table_no );
                                                                         final String sugges1 = user.get ( SessionManager.suggestions );
                                                                         final String item1 = user.get ( SessionManager.item_served );// final int item3 = Integer.valueOf(item1);
                                                                         final String server1 = user.get ( SessionManager.server_id );

                                                                         final int server2 = Integer.parseInt ( id_server );

                                                                         final String customerName = user.get ( SessionManager.customer_name );
                                                                         final String customerContact = user.get ( SessionManager.customer_contact_no );
                                                                         final String customerEmail = user.get ( SessionManager.customer_email );
                                                                         final String customerBirth = user.get ( SessionManager.customer_birthday );
                                                                         final String customerAnniversary = user.get ( SessionManager.customer_anniversary );
                                                                         final Date date = new Date ();
                                                                        // float rates = mDb.responseDao ().getID ();

                                                                         Review review = new Review ( customerName, customerEmail, customerContact, customerBirth, customerAnniversary, server2, id_food, floatRate, suggest, tableno, date );
                                                                         mDb.reviewDao ().insertReview ( review );

                                                                         //    session.createLoginSession ( rate, customerName, customerContact, customerEmail, customerBirth, customerAnniversary, id_food, id_server, tableno, suggest );

                                                                         Log.e ( "values from session", customerName + " " + customerContact + " " + customerEmail + " " + floatRate + " " + server2 + " " + id_food + " " + tableno + " " + suggest + " " + date + " " + customerAnniversary );


                                                                         //Viewing saved session results
//                                                                         final String rate1 = user.get ( SessionManager.ratings );
//                                                                         final String name = user.get ( SessionManager.customer_name );
//                                                                         final String contact = user.get ( SessionManager.customer_contact_no );
//                                                                         final String email = user.get ( SessionManager.customer_email );
//                                                                         final String birth = user.get ( SessionManager.customer_birthday );
//
//                                                                         final String anniversary = user.get ( SessionManager.customer_anniversary );
//                                                                         final String table = user.get ( SessionManager.table_no );
//                                                                         final String sugges = user.get ( SessionManager.suggestions );
//                                                                         final String item = user.get ( SessionManager.item_served );
//                                                                         final String server = user.get ( SessionManager.server_id );


                                                                         runOnUiThread ( new Runnable () {
                                                                             @Override
                                                                             public void run() {
                                                                                 thanks.setVisibility ( View.VISIBLE );

                                                                                 startActivity(new Intent (More_Info_Activity.this, MainActivity.class));
                                                                             }
                                                                         });
                                                                     }
                                                                 });


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
        btnSave = (Button)findViewById ( R.id.btndone );
        spinnerItems = (Spinner) findViewById ( R.id.spinnerItems ) ;
        spinnerServer = (Spinner) findViewById ( R.id.spinnerServer );
        thanks = (TextView) findViewById ( R.id.thanks );
    }

//    public void btndone (View view){
//
//    }
}
