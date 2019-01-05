package com.intellisense.review.activities;

import android.content.Intent;
import android.content.UriMatcher;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.intellisense.review.R;
import com.intellisense.review.Utilities;
import com.intellisense.review.db_classes.Admin;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;
import com.intellisense.review.db_classes.Items_Served;
import com.intellisense.review.db_classes.Response;
import com.intellisense.review.db_classes.Review;
import com.intellisense.review.db_classes.Review_Questions;
import com.intellisense.review.db_classes.Server;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailReviewActivity extends AppCompatActivity {

    private int adminType;

    private RatingBar ratingBar;

    private TextView dateTextView;

    private LinearLayout reviewQuestionsLinearLayout;

    private TextView suggestionDetailTextView;

    private TextView suggestionServerTextView;

    private TextView waiterNameTextView;

    private LinearLayout contactLayout;

    private TextView contactTextView;

    private LinearLayout emailLayout;

    private TextView emailTextView;

    private TextView birthdayTextView;

    private TextView anniversaryTextView;

    private LinearLayout servicesOfferedLinearLayout;

    private AppDatabase mDb;

    private int review_id;

    private Review currentReview;

    private Server server;

    private List<Items_Served> items_served;

    private List<Review_Questions> reviewQuestionsList;

    private List<Response> responseList;

    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);

        actionBar = getSupportActionBar();

        ratingBar = (RatingBar)findViewById(R.id.activity_detail_review_rating_bar);
        dateTextView = (TextView)findViewById(R.id.activity_detail_review_date);
        reviewQuestionsLinearLayout = (LinearLayout)findViewById(R.id.review_questions_linear_layout);
        servicesOfferedLinearLayout = (LinearLayout)findViewById(R.id.services_offered_linear_layout);
        suggestionDetailTextView = (TextView)findViewById(R.id.activity_detail_review_suggestion);
        suggestionServerTextView = (TextView)findViewById(R.id.activity_detail_review_suggestion_server);
        waiterNameTextView = (TextView)findViewById(R.id.activity_detail_waiter_name_text_view);
        contactLayout = (LinearLayout)findViewById(R.id.activity_detail_review_contact_no_layout);
        contactTextView = (TextView)findViewById(R.id.activity_detail_review_contact_no);
        emailLayout = (LinearLayout)findViewById(R.id.activity_detail_review_email_layout);
        emailTextView = (TextView)findViewById(R.id.activity_detail_review_email);
        birthdayTextView = (TextView)findViewById(R.id.activity_detail_review_birthday);
        anniversaryTextView = (TextView)findViewById(R.id.activity_detail_review_anniversary);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent previousIntent = getIntent();

        if(previousIntent.hasExtra(Utilities.REVIEW_ID))
        {
            review_id = previousIntent.getIntExtra(Utilities.REVIEW_ID,0);
            adminType = previousIntent.getIntExtra(Utilities.ADMIN_TYPE,0);
            loadCurrentReview();
        }
    }

    public void loadCurrentReview()
    {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                currentReview = mDb.reviewDao().loadSingleReview(review_id);
                server = mDb.serverDao().loadSingleServer(currentReview.getServer_id());
                items_served = new ArrayList<>();
                String items_string = currentReview.getService_offered_ids();
                String[] ids = items_string.split(",");
                for(int i=0;i<ids.length;i++)
                {
                    int id = Integer.parseInt(ids[i]);
                    Items_Served item_served = mDb.itemsDao().loadSingleItem(id);
                    items_served.add(item_served);
                }
                reviewQuestionsList = mDb.questionsDao().loadAllQuestions();
                responseList = new ArrayList<>();

                for(int i=0;i<reviewQuestionsList.size();i++)
                {
                    Response response = mDb.responseDao().loadSingleResponse(review_id,reviewQuestionsList.get(i).getQuestion_id());
                    responseList.add(response);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateUI();
                    }
                });
            }
        });
    }

    public void populateUI()
    {

        actionBar.setTitle(currentReview.getCustomer_name());
        // Set the overall rating
        ratingBar.setRating(currentReview.getRating());

        // Set the date of review
        Date date = currentReview.getCreated_time();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTextView.setText(dateFormat.format(date));

        // Suggestion Detail
        String suggestion = currentReview.getSuggestion();
        if(suggestion!=null && suggestion.length()!=0)
        {
            suggestionDetailTextView.setText(currentReview.getSuggestion());
        }
        else
        {
            suggestionDetailTextView.setVisibility(View.GONE);
        }

        // Server Suggestion Detail
        String serverSuggestion = currentReview.getSuggestion_for_server();
        if(serverSuggestion!=null&&serverSuggestion.length()!=0)
        {
            suggestionServerTextView.setText(serverSuggestion);
        }
        else
        {
            suggestionServerTextView.setVisibility(View.GONE);
            TextView suggestionHeading = (TextView)findViewById(R.id.activity_detail_server_suggestion_heading);
            suggestionHeading.setVisibility(View.GONE);
        }


        //Populate the questions

        for(int i=0;i<reviewQuestionsList.size();i++)
        {
            Review_Questions currentQuestion = reviewQuestionsList.get(i);
            Response userResponse = responseList.get(i);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0,8,0,8);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);

            TextView questionTextView = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
            questionTextView.setLayoutParams(layoutParams);

            questionTextView.setText(currentQuestion.getQuestions());

            ImageView emojiImageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(64, 64);
            emojiImageView.setLayoutParams(layoutParamsImage);
            int imageResource;

            switch (userResponse.getResponse())
            {
                case 1:
                    imageResource = R.drawable.angry_face;
                    break;
                case 2:
                    imageResource = R.drawable.sad_face;
                    break;
                case 3:
                    imageResource = R.drawable.neutral_face;
                    break;
                case 4:
                    imageResource = R.drawable.happy_face;
                    break;
                case 5:
                    imageResource = R.drawable.heart_face;
                    break;
                default:
                    imageResource = R.drawable.angry_face;
            }
            emojiImageView.setImageResource(imageResource);


            linearLayout.addView(questionTextView,0);
            linearLayout.addView(emojiImageView,1);
            reviewQuestionsLinearLayout.addView(linearLayout);
        }

        for(int i=0;i<items_served.size();i++)
        {
            Items_Served currentItem = items_served.get(i);

            TextView itemTextView = new TextView(this);
            itemTextView.setText(currentItem.getItem_name());
            itemTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            itemTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            itemTextView.setTextColor(Color.WHITE);
            itemTextView.setPadding(24,16,24,16);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,10,0);
            itemTextView.setLayoutParams(params);

            servicesOfferedLinearLayout.addView(itemTextView);
        }

        // Server Name
        waiterNameTextView.setText(server.getServer_name());


        String contact = currentReview.getCustomer_contact_no();
        String email = currentReview.getCustomer_email();
        String birthday = currentReview.getCustomer_birthday();
        String anniversary = currentReview.getCustomer_anniversary();


        if(adminType== Admin.ADMIN_TYPE_SUPER)
        {
            if(contact!=null && contact.length()!=0)
            {
                contactTextView.setText(currentReview.getCustomer_contact_no());
            }
            else
            {
                contactTextView.setText(getString(R.string.not_available));
            }
            if(email!=null && email.length()!=0)
            {
                emailTextView.setText(currentReview.getCustomer_email());
            }
            else
            {
                emailTextView.setText(getString(R.string.not_available));
            }
        }
        else
        {
            contactLayout.setVisibility(View.GONE);
            emailLayout.setVisibility(View.GONE);
        }



        if(birthday!=null && birthday.length()!=0)
        {
            birthdayTextView.setText(currentReview.getCustomer_birthday());
        }
        else
        {
            birthdayTextView.setText(getString(R.string.not_available));
        }


        if(anniversary!=null && anniversary.length()!=0)
        {
            anniversaryTextView.setText(currentReview.getCustomer_anniversary());
        }
        else
        {
            anniversaryTextView.setText(getString(R.string.not_available));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        if(adminType==Admin.ADMIN_TYPE_NORMAL)
        {
            MenuItem callItem = menu.findItem(R.id.action_call);
            callItem.setVisible(false);
            MenuItem emailItem = menu.findItem(R.id.action_email);
            emailItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.action_call:
                callPerson();
                return true;
            case R.id.action_email:
                sendEmail();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    public void callPerson()
    {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + currentReview.getCustomer_contact_no()));
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }

    }

    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
    //    intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, currentReview.getCustomer_email());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
