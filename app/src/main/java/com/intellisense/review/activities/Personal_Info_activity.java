package com.intellisense.review.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intellisense.review.MainActivity;
import com.intellisense.review.R;
import com.intellisense.review.db_classes.SessionManager;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Personal_Info_activity extends AppCompatActivity {
EditText name,email,contact;
TextView birth,anniversary;
TextView error,nameerror;
Button move;
ImageView btnbirth,btnanniversary;
    SessionManager session;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private DatePickerDialog.OnDateSetListener mDateSetListner1;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onBackPressed() {
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);}
        startActivity(new Intent (Personal_Info_activity.this, Review_one.class));}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_personal__info_activity );
        session = new SessionManager (getApplicationContext ());
        initViews();

        btnbirth.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
              DatePickerDialog dialog = new DatePickerDialog (
                      Personal_Info_activity.this,
                      android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                      mDateSetListner,
                      year,month,day);
              dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT));;
              dialog.show ();
                          }
        });
    mDateSetListner = new DatePickerDialog.OnDateSetListener () {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month +1;
            String date = month +"/" + dayOfMonth + "/" + year;
            birth.setText(date);
        }
    };

        btnanniversary.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog (
                        Personal_Info_activity.this,
                        android.R.style.Theme_Holo_Light,
                        mDateSetListner1,
                        year,month,day);
                dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT));
                dialog.show ();
            }
        });
        mDateSetListner1 = new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = month +"/" + dayOfMonth + "/" + year;
                anniversary.setText(date);
            }
        };
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName =  name.getText ().toString () ;
                String customerEmail = email.getText ().toString () ;
                String customerContact = contact.getText ().toString () ;
                String customerBirth = birth.getText ().toString () ;
                String customerAnniversary = anniversary.getText ().toString () ;
                if(customerName.length () <= 2 )
                {
                    error.setVisibility ( View.VISIBLE );
                    nameerror.setVisibility ( View.VISIBLE );
                    error.setText("Please fill in your name correctly");
                    new Handler ().postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            error.setVisibility ( View.INVISIBLE );
                                }
                    }, 4000);
                }

                else if  (customerContact.length () != 0)
               // { if(!isValidMobile ( customerContact))
                if(customerContact.length() < 6 || customerContact.length() > 13) {
                    Toast.makeText(Personal_Info_activity.this,"Recheck your number",Toast.LENGTH_SHORT).show();
                }

                else if (customerEmail.length () != 0) {
                    if (!isValidEmail(customerEmail))
                    Toast.makeText(Personal_Info_activity.this,"Invalid Email",Toast.LENGTH_SHORT).show();
                }
                else
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
            }

        });


    }
    private boolean isValidMobile(String phone) {
        boolean check= false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 6 || phone.length() > 13) {
                check = false;
//                Toast.makeText(Personal_Info_activity.this,"Invalid number",Toast.LENGTH_SHORT).show();
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void initViews(){
        name = (EditText) findViewById ( R.id.name );

        email = (EditText) findViewById ( R.id.email);

        contact = (EditText) findViewById ( R.id.contact);

        birth = (TextView) findViewById ( R.id.DOB);

        anniversary = (TextView) findViewById ( R.id.anniversary);

        move = (Button) findViewById ( R.id.next );

        error = (TextView) findViewById ( R.id.error );

        nameerror = (TextView) findViewById ( R.id. nameerror);

        btnbirth =(ImageView)findViewById ( R.id.btnbirth );

        btnanniversary = (ImageView) findViewById ( R.id.btnanniversary );
    }

  //  public void move_activity(View v){
//getting values

  }



