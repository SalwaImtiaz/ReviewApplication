package com.intellisense.review.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intellisense.review.MainActivity;
import com.intellisense.review.R;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;
import com.intellisense.review.db_classes.Items_Served;
import com.intellisense.review.db_classes.Response;
import com.intellisense.review.db_classes.Review_Questions;
import com.intellisense.review.db_classes.Server;
import com.intellisense.review.db_classes.SessionManager;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Review_one extends AppCompatActivity {
    SessionManager session;
EditText questionText,questionType;
TextView question,servers,items,quesNo;
Button btn_save,btnMore,btnLess;

    ImageView one,two, three,four,five;
    final int ratings [] = new int[10];
    private AppDatabase mDb;
    int quizCounter = 1 ,   selection =0;
    Animation animation ;
    int ratingCounter = 0;
    LinearLayout linearlayoutID;
    private List<Review_Questions> ques;
    private String quizes;
    private int TotalQuiz;

    public void onBackPressed(){startActivity(new Intent (this, MainActivity.class));}

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
                selection = 5;
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.bounce );
                five.startAnimation ( animation );
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                if(quizCounter != (TotalQuiz+1)) {
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.righttoleft );
                    linearlayoutID.startAnimation ( animation );}

                fetchData ();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 4;
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.bounce );
                four.startAnimation ( animation );
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                if(quizCounter != (TotalQuiz+1)){
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.righttoleft );
                linearlayoutID.startAnimation ( animation );}
                fetchData ();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 3;
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.bounce );
                three.startAnimation ( animation );
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                if(quizCounter != (TotalQuiz+1)){
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.righttoleft );
                linearlayoutID.startAnimation ( animation );}
                fetchData ();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 2;
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.bounce );
                two.startAnimation ( animation );
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                if(quizCounter != (TotalQuiz+1)){
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.righttoleft );
                linearlayoutID.startAnimation ( animation );}
                fetchData ();
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 1;
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.bounce );
                one.startAnimation ( animation );
                ratings[ratingCounter] = selection;
                ratingCounter++;
                quizCounter++;
                if(quizCounter != (TotalQuiz+1)){
                animation = AnimationUtils.loadAnimation ( Review_one.this,R.anim.righttoleft );
                linearlayoutID.startAnimation ( animation );}
                fetchData ();
            }
        });
        fetchData();
//        fetchFoodData();
//        fetchServerData();
//Log.i("Quizz counter",  quizCounter ) );
//

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

    private void fetchData() {
            AppExecutors.getInstance().diskIO().execute( new Runnable() {
                @Override
                public void run() {

                //    ques = new ArrayList<Review_Questions> ();
                  //  Review_Questions[] array = ques.toArray(new Review_Questions[ques.size()]);
                    ques = mDb.questionsDao ().loadAllQuestions ();
                    TotalQuiz = mDb.questionsDao ().TotalQuestion ();
                    if(quizCounter <= TotalQuiz)
                    quizes = mDb.questionsDao ().findQuestion ( quizCounter );

//
                    if(quizCounter == (TotalQuiz+1))
                    {
                        Response response = null;
                        int review_id = mDb.reviewDao ().getID ();
                        int questionID = 1,company_id_fk = 1;
                        final java.util.Date date = new java.util.Date ();
                        for(int counter = 0 ; counter <TotalQuiz ;counter++){
                        response = new Response ( review_id,questionID,ratings[counter],company_id_fk,date );
                            mDb.responseDao ().insertResponse ( response );
                        questionID++;
                        }


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
                            if(quizCounter > 1)
                                btnLess.setVisibility ( View.VISIBLE );
                            if(quizCounter <= 1)
                                btnLess.setVisibility ( View.INVISIBLE );


                            StringBuilder sb = new StringBuilder();
                            for (Review_Questions questn : ques) {
                                sb.append(String.format( Locale.US,
                                        "%s\n", questn.getQuestions ()));
                            }

                            Log.e ( "Total Quizes", "value" +TotalQuiz  );
                            Log.e ( "quizCounter","value=" + quizCounter );
                            Log.e ( "rateCounter","rate=" + ratingCounter );
                            Log.e ( "array" , "myarray"+ratings[0]+","+ratings[1]+","+ratings[2]+","+ratings[3]);

                            question.setText ( quizes );        //set sb to show all quezes
                                    //setting the quesNo at top
                            if(quizCounter <= TotalQuiz){
                            String quesNos = quizCounter + " out of " + TotalQuiz;
                            Log.e ( "quesNos",quesNos );
                            quesNo.setText(quesNos);}

                        }
                    } );

                }
            });

    }

    private void initViews(){
        questionText = (EditText) findViewById ( R.id.questionText );
        questionType = (EditText) findViewById ( R.id.questionType );

        question = (TextView) findViewById ( R.id.question );
        one = (ImageView) findViewById ( R.id.one );
        two = (ImageView) findViewById ( R.id.two);
        three = (ImageView) findViewById ( R.id.three);
       four = (ImageView) findViewById ( R.id.four);
        five = (ImageView) findViewById ( R.id.five );
        linearlayoutID = (LinearLayout) findViewById ( R.id.linearlayoutID );
        btnLess = (Button) findViewById ( R.id.btnLess);
        btnMore = (Button) findViewById ( R.id.btnMove);
        quesNo = (TextView) findViewById ( R.id.quesNo);
    }
    public void Less(View v){
        if(quizCounter <= 1)
            btnLess.setVisibility ( View.INVISIBLE );
        if(!(ratingCounter <=0))
        {quizCounter-- ;
        ratingCounter--;}
        fetchData ();
    }
    public void More(View v){
        quizCounter++ ;
        fetchData ();

    }



}
