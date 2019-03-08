package com.intellisense.review.activities;

import android.content.Intent;
import android.content.UriMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.intellisense.review.MainActivity;
import com.intellisense.review.R;
import com.intellisense.review.Utilities;
import com.intellisense.review.adapters.ReviewAdapter;
import com.intellisense.review.db_classes.AppDatabase;
import com.intellisense.review.db_classes.AppExecutors;
import com.intellisense.review.db_classes.Company;
import com.intellisense.review.db_classes.Items_Served;
import com.intellisense.review.db_classes.Response;
import com.intellisense.review.db_classes.Review;
import com.intellisense.review.db_classes.Review_Questions;
import com.intellisense.review.db_classes.Server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity implements ReviewAdapter.ListItemClickListener {

    private List<Review> reviews;
    private ReviewAdapter reviewAdapter;

    private RecyclerView reviewsRecyclerView;

    private AppDatabase mDb;

    private int adminId;
    private int adminType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        mDb = AppDatabase.getInstance(getApplicationContext());

        //insert data()
//        insertDummyData();
        reviewsRecyclerView = (RecyclerView)findViewById(R.id.reviews_recycler_view);
        reviewsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),layoutManager.getOrientation());
        reviewsRecyclerView.addItemDecoration(decoration);

        Intent getPreviousIntent = getIntent();
        if(getPreviousIntent.hasExtra(Utilities.ADMIN_ID)&&getPreviousIntent.hasExtra(Utilities.ADMIN_TYPE))
        {
            adminId = getPreviousIntent.getIntExtra(Utilities.ADMIN_ID,0);
            adminType = getPreviousIntent.getIntExtra(Utilities.ADMIN_TYPE,0);
        }

        reviews = new ArrayList<Review>();
        loadReviewsFromDatabase();

        reviewAdapter = new ReviewAdapter(reviews,this,this);
        reviewsRecyclerView.setAdapter(reviewAdapter);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(ReviewsActivity.this,DetailReviewActivity.class);
        Review currentReview = reviews.get(clickedItemIndex);
        intent.putExtra(Utilities.REVIEW_ID,currentReview.getReview_id());
        intent.putExtra(Utilities.ADMIN_TYPE,adminType);
        startActivity(intent);

    }

    public void loadReviewsFromDatabase()
    {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                reviews = mDb.reviewDao().loadAllReviews();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reviewAdapter.setReviewsList(reviews);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reviews,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId)
        {
            case R.id.action_sync:
                Toast.makeText(ReviewsActivity.this,"Sync",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewsActivity.this,SyncActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                intent = new Intent(ReviewsActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
//            case R.id.action_insert_dummy_data:
//                insertDummyData();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void insertDummyData()
    {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
//                Server server1 = new Server("Anna Colleman",1);
//                Server server2 = new Server("Henry",1);
//                mDb.serverDao().insertServer(server1);
//                mDb.serverDao().insertServer(server2);
//
//                Company company1 = new Company ("KFC");
//                Company company2 = new Company ("California Pizza");
//                Company company3 = new Company ("Delizia");
//                mDb.companyDao ().insertCompany (company1);
//                mDb.companyDao ().insertCompany (company2);
//                mDb.companyDao ().insertCompany (company3);
//
//                Items_Served items_served1 = new Items_Served("Fries",1);
//                Items_Served items_served2 = new Items_Served("Burger",1);
//                Items_Served items_served3 = new Items_Served("Pizza",1);
//                mDb.itemsDao().insertItem(items_served1);
//                mDb.itemsDao().insertItem(items_served2);
//                mDb.itemsDao().insertItem(items_served3);

//                Review_Questions question1 = new Review_Questions("How was the quality of food?",1);
//                Review_Questions question2 = new Review_Questions("How was the taste of food?",1);
//                Review_Questions question3 = new Review_Questions("How was the service?",1);
//                mDb.questionsDao().insertQuestion(question1);
//                mDb.questionsDao().insertQuestion(question2);
//                mDb.questionsDao().insertQuestion(question3);

//                mDb.reviewDao().insertReview(new Review("James Richardson","james@gmail.com","1234567890","17/5/1995","29/12/2017",1,"1,3",4.7f,"The food quality was very much good. Service was really awesome. Will surely try again.",null, new Date(),1));
//                mDb.reviewDao().insertReview(new Review("Thomas","thomas@yahoo.com","9876543210","21/1/1998",null,2,"1,2",2.1f,"The food quality was very much good. Service was really awesome. Will surely try again.",null, new Date(),1));
//                mDb.reviewDao().insertReview(new Review("Jennifer","jennifer@yahoo.com","567890","24/7/1995","13/03/2016",2,"1,2,3",3.5f,"The food quality was very much good. Service was really awesome. Will surely try again.",null, new Date(),1));
//                mDb.reviewDao().insertReview(new Review("Carl","carl@gmail.com","543210","03/08/1996",null,1,"2,3",4.1f,"The food quality was very much good. Service was really awesome. Will surely try again.",null, new Date(),1));
//                mDb.reviewDao().insertReview(new Review("Sarah","sarah@gmail.com",null,null,null,2,"3,2,1",4.1f,"The food quality was very much good. Service was really awesome. Will surely try again.","Server was good.", new Date(),1));
//
//                mDb.responseDao().insertResponse(new Response(1,1,5,1));
//                mDb.responseDao().insertResponse(new Response(1,2,4,1));
//                mDb.responseDao().insertResponse(new Response(1,3,5,1));
//
//                mDb.responseDao().insertResponse(new Response(2,1,4,1));
//                mDb.responseDao().insertResponse(new Response(2,2,5,1));
//                mDb.responseDao().insertResponse(new Response(2,3,4,1));
//
//                mDb.responseDao().insertResponse(new Response(3,1,2,1));
//                mDb.responseDao().insertResponse(new Response(3,2,4,1));
//                mDb.responseDao().insertResponse(new Response(3,3,3,1));
//
//                mDb.responseDao().insertResponse(new Response(4,1,1,1));
//                mDb.responseDao().insertResponse(new Response(4,2,5,1));
//                mDb.responseDao().insertResponse(new Response(4,3,2,1));
//
//                mDb.responseDao().insertResponse(new Response(5,1,3,1));
//                mDb.responseDao().insertResponse(new Response(5,2,5,1));
//                mDb.responseDao().insertResponse(new Response(5,3,2,1));

            }
        });

        loadReviewsFromDatabase();
    }
}
