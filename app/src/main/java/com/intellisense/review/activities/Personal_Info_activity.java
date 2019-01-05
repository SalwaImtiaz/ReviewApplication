package com.intellisense.review.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.intellisense.review.R;
import com.intellisense.review.db_classes.SessionManager;

import java.util.HashMap;

public class Personal_Info_activity extends AppCompatActivity {
EditText name,email,contact,birth,anniversary;
TextView error;
Button move;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_personal__info_activity );
        session = new SessionManager (getApplicationContext ());
        initViews();

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName =  name.getText ().toString () ;
                String customerEmail = email.getText ().toString () ;
                String customerContact = contact.getText ().toString () ;
                String customerBirth = birth.getText ().toString () ;
                String customerAnniversary = anniversary.getText ().toString () ;

                if(customerName != "" && customerEmail != "" && customerContact != "" && customerBirth != "" && customerAnniversary != "")
                {
                    Log.e("values from user",customerAnniversary+" "+customerBirth+" "+customerContact+" "+customerEmail+" "+customerName);

                    //storing to session
                    HashMap<String, String> user = session.getUserDetails ();
                    final String rate = user.get (SessionManager.ratings);
                    session.createLoginSession (rate,customerName,customerContact,customerEmail,customerBirth,customerAnniversary,null,null,null,null);

                    final String rate1 = user.get (SessionManager.ratings);
                    final String name = user.get (SessionManager.customer_name);
                    final String contact = user.get (SessionManager.customer_contact_no);
                    final String email = user.get (SessionManager.customer_email);
                    final String birth = user.get (SessionManager.customer_birthday);
                    final String anniversary = user.get (SessionManager.customer_anniversary);

                    Log.e("values from session",rate1+" "+name+" "+contact+" "+email+" "+birth+" "+anniversary);
                    startActivity(new Intent (Personal_Info_activity.this, More_Info_Activity.class));
                }
                else
                {
                    error.setVisibility ( View.VISIBLE );
                    error.setText("Please fill the form comlete");
                }


            }
        });

    }

    private void initViews(){
        name = (EditText) findViewById ( R.id.name );

        email = (EditText) findViewById ( R.id.email);

        contact = (EditText) findViewById ( R.id.contact);

        birth = (EditText) findViewById ( R.id.DOB);

        anniversary = (EditText) findViewById ( R.id.anniversary);

        move = (Button) findViewById ( R.id.next );

        error = (TextView) findViewById ( R.id.error );
    }

  //  public void move_activity(View v){
//getting values

}
