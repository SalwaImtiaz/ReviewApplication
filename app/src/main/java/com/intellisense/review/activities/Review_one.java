package com.intellisense.review.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.intellisense.review.R;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;
import com.intellisense.review.db_classes.Items_Served;
import com.intellisense.review.db_classes.Response;
import com.intellisense.review.db_classes.Review_Questions;
import com.intellisense.review.db_classes.Server;
import com.intellisense.review.db_classes.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Review_one extends AppCompatActivity {
    SessionManager session;
EditText questionText,questionType;
TextView question,servers,items;
Button btn_save;
int questCount=0;
    ImageView one,two, three,four,five;
    final int ratings [] = new int[10];
    private AppDatabase mDb;
    int quizCounter = 1 ,   selection =0;

    int ratingCounter = 0;

    private List<Review_Questions> ques;
    private String quizes;
    private int TotalQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_review_one );
        initViews();
        mDb = AppDatabase.getInstance ( getApplicationContext () );
        session = new SessionManager (getApplicationContext ());
//Emojis
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 1;
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                fetchData ();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 2;
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                fetchData ();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 3;
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                fetchData ();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 4;
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                fetchData ();
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 5;
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                fetchData ();
            }
        });
        fetchData();
//        fetchFoodData();
//        fetchServerData();

    }
//    private void fetchServerData() {
//
//        StringBuilder sb5 = new StringBuilder();
//        List<Server> ques = mDb.serverDao ().findServer ();
//        String quizes = mDb.serverDao ().getServerName ();
//
//
///////////////////////////////////////////////////////////////////////////////////////////delete
//       // mDb.reviewQuestionsDao ().deleteAll ();
//        for (Server quest : ques) {
//            sb5.append(String.format( Locale.US,
//                    "%d %s,\n",quest.getServer_id(), quest.getServer_name ()));
//        }
//        servers.setText(sb5);
//
//
//    }

//    private void fetchFoodData() {
//        StringBuilder sb4 = new StringBuilder();
//        List<Items_Served> ques = mDb.itemServedDao ().findItems ();
//        String quizes = mDb.itemServedDao ().getItems ();
//
//
//
//        // RoomDB.reviewQuestionsDao ().deleteAll ();
//        for (Items_Served quest : ques) {
//            sb4.append(String.format( Locale.US,
//                    "%d %s\n",quest.getItem_id(), quest.getItem_name ()));
//        }
//
//        items.setText(sb4);
//        //2nd
//    }

    private void fetchData() {      //  List<Review_Questions> ques = mDb.questionsDao ().loadAllQuestions ();
//       questCount  = mDb.reviewQuestionsDao ().countQues();
      // RoomDB.reviewQuestionsDao ().deleteAll ();
//            quizCounter++;
            //quizes = mDb.questionsDao ().findQuestion ( quizCounter );
            AppExecutors.getInstance().diskIO().execute( new Runnable() {
                @Override
                public void run() {

                //    ques = new ArrayList<Review_Questions> ();
                  //  Review_Questions[] array = ques.toArray(new Review_Questions[ques.size()]);
                    ques = mDb.questionsDao ().loadAllQuestions ();
                    TotalQuiz = mDb.questionsDao ().TotalQuestion ();
                    quizes = mDb.questionsDao ().findQuestion ( quizCounter );

//                    String[] ArrayQues = new String[ques.size()];
//                    ArrayQues = ques.toArray(ArrayQues);
//
//                    for(int i=0;i<= 3;i++) {
//                        Log.e ( "Questions", String.valueOf ( array[i] ) );
//                    }
//                    int review_id = mDb.reviewDao ().getID ();
//                    int questionID = 0;
//                    Response response = new Response ( review_id,questionID,ratings[questionID] );
//                    questionID++;

//                    mDb.responseDao ().insertResponse ( response );
//                    Response response2 = new Response ( review_id,questionID,ratings[questionID] );
//                    mDb.responseDao ().insertResponse ( response2 );

                    if(quizCounter == (TotalQuiz+1))
                    {
                        Response response = null;
                        int review_id = mDb.reviewDao ().getID ();
                        int questionID = 1;
                        for(int counter = 0 ; counter <TotalQuiz ;counter++){
                        response = new Response ( review_id,questionID,ratings[counter] );
                            mDb.responseDao ().insertResponse ( response );
                        questionID++;
                        }
//

//                        Response response2 = new Response ( review_id,questionID,ratings[questionID] );
//                        mDb.responseDao ().insertResponse ( response2 );

                        float average_rates =0;
                        for(int a = 0; a < TotalQuiz; a++ ) {
                            average_rates = average_rates + ratings[a];
                        }
                        average_rates = average_rates/TotalQuiz;
                        String str = String.valueOf ( average_rates );

                        session.createLoginSession (str,null,null,null,null,null,null,null,null,null);
                        HashMap<String, String> user = session.getUserDetails ();
                        final String rate = user.get (SessionManager.ratings);
                        Log.e ( "Rate",rate  );
                        startActivity(new Intent (Review_one.this, Personal_Info_activity.class));
                    }
                    runOnUiThread ( new Runnable () {
                        @Override
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            for (Review_Questions questn : ques) {
                                sb.append(String.format( Locale.US,
                                        "%s\n", questn.getQuestions ()));
                            }

                            Log.e ( "quizCounter","value=" + quizCounter );
                            Log.e ( "rateCounter","rate=" + ratingCounter );
                            Log.e ( "array" , "myarray"+ratings[0]+","+ratings[1]+","+ratings[2]+","+ratings[3]);
                            question.setText ( quizes );        //set sb to show all quezes

                        }
                    } );

                }
            });

    }

    private void initViews(){
        questionText = (EditText) findViewById ( R.id.questionText );
        questionType = (EditText) findViewById ( R.id.questionType );
        items = (TextView) findViewById ( R.id.Items );
        servers = (TextView) findViewById ( R.id.Servers );
        btn_save = (Button) findViewById ( R.id.btnsave );
        question = (TextView) findViewById ( R.id.question );
        one = (ImageView) findViewById ( R.id.one );
        two = (ImageView) findViewById ( R.id.two);
        three = (ImageView) findViewById ( R.id.three);
       four = (ImageView) findViewById ( R.id.four);
        five = (ImageView) findViewById ( R.id.five );
    }

    public void saving(View v){
        String type = questionType.getText ().toString ();

//        if(type == "1") {
//            String question = questionText.getText ().toString ();
////
//            Review_Questions review_questions = new Review_Questions ( question );
//            mDb.reviewQuestionsDao ().insert ( review_questions );
//            finish ();
//            Log.e ( "Saved", "Saved question" );
//     //   mDb.serverDao ().deleteAllServers ();
//            startActivity ( new Intent ( this, MainActivity.class ) );

//        }
//        else if( type =="2")
//        {
           // RoomDB.itemServedDao ().deleteAll ();
//
//            String question = questionText.getText ().toString ();
//
//            Items_Served items_served = new Items_Served ( question );
//            RoomDB.itemServedDao ().insert ( items_served );
//            finish ();
//
//
//            startActivity ( new Intent ( this, MainActivity.class ) );
//            Log.e ( "Saved", "Saved item" );
//        }
//        else if(type == "3")
//        {
//            String question = questionText.getText ().toString ();
//
//            Server server = new Server ( question );
//        mDb.serverDao ().insert ( server );
//            finish ();
//
//            Log.e ( "Saved", "Saved server" );
//
//            startActivity ( new Intent ( this, MainActivity.class ) );
////        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
