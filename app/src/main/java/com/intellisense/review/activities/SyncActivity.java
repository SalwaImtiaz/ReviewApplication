package com.intellisense.review.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.intellisense.review.R;
import com.intellisense.review.adapters.JsonPlaceHolderApi;
import com.intellisense.review.adapters.Post;
import com.intellisense.review.adapters.Comment;
import com.intellisense.review.adapters.SyncDown;
import com.intellisense.review.adapters.SyncUp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SyncActivity extends AppCompatActivity {
    private TextView textviewresult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_sync );

        textviewresult = findViewById ( R.id.text );
        retrofit = new Retrofit.Builder ()
                .baseUrl ( "http://www.icoderslab.com/Api/ReviewApp/" )
//                .baseUrl ( "https://jsonplaceholder.typicode.com/" )
                .addConverterFactory ( GsonConverterFactory.create () )
                .build ();

        jsonPlaceHolderApi = retrofit.create ( JsonPlaceHolderApi.class );

//        SyncingDown();                      //SyncDown

        SyncingUp();
    }


//    private void GetReviews() {
//     //   Call<List<SyncDown>> call = jsonPlaceHolderApi.getReviews ();
//        call.enqueue ( new Callback<List<SyncDown>> () {
//            @Override
//            public void onResponse(Call<List<SyncDown>> call, Response<List<SyncDown>> response) {
//                if (!response.isSuccessful ()) {
//                    textviewresult.setText ( "Code: " + response.code () );
//                    return;
//                }
//                List<SyncDown> syncDowns = response.body ();
//
//                for (SyncDown syncDown : syncDowns) {
//                    String content = "";
//                    content += "Admin ID: " + syncDown.getAdmin_id () + "\n";
//                    content += "Review ID: " + syncDown.getUser_name () + "\n";
//                    content += "Response: " + syncDown.getEmail () + "\n";
//                    content += "QuestionID: " + syncDown.getPassword () + "\n";
//                    content += "CompanyID: " + syncDown.getType () + "\n";
//                    content += "EntryTime: " + syncDown.getCompany_id () + "\n";
//                    content += "EntryTime: " + syncDown.getActive () + "\n";
//                    content += "EntryTime: " + syncDown.getLast_sync_time () + "\n\n";
//
//                    textviewresult.append ( content );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<SyncDown>> call, Throwable t) {
//                textviewresult.setText ( t.getMessage () );
//            }
//        } );
//    }


    private void SyncingDown(){
//        String date = new Date().toString ();
        String date = "2019-03-11 13:05:52";
//        SyncDown syncDown = new SyncDown (5,"test","test@mail.com","123456",  1,1,1,date );
        SyncDown syncDown = new SyncDown (date);
        Call<SyncDown> call = jsonPlaceHolderApi.SyncDown_Call ( syncDown );

        call.enqueue ( new Callback<SyncDown> () {
            @Override
            public void onResponse(Call<SyncDown> call, Response<SyncDown> response) {

                if(!response.isSuccessful ()){
                    textviewresult.setText ( "Code "+response.code () );
                    return;
                }

                SyncDown syncResponse = response.body ();

                String content = "";
                  content += "ID: " + response.code () + "\n";
                content += "Post ID: " + syncResponse.getAdmin_id () + "\n";
                content += "Name: " + syncResponse.getUser_name () + "\n";
                content += "Email: " + syncResponse.getEmail () + "\n";
                content += "Password: " + syncResponse.getPassword () + "\n";
                content += "Type: " + syncResponse.getType () + "\n";
                content += "LastSyncTime: " + syncResponse.getLast_sync_time () +"\n\n";

                textviewresult.setText (content );
            }

            @Override
            public void onFailure(Call<SyncDown> call, Throwable t) {
                textviewresult.setText ( t.getMessage () );
            }
        } );
    }




    private void SyncingUp(){
        String date = new Date().toString ();
//        String date = "2019-03-11 13:05:52";
//        SyncDown syncDown = new SyncDown (5,"test","test@mail.com","123456",  1,1,1,date );
//        SyncUp syncUp = new SyncUp (date);

        SyncUp syncUp = new SyncUp ( "Normal Admin","normaladmin@gmail.com","normaladmin",1,1,1  );
        Call<SyncUp> call = jsonPlaceHolderApi.SYNC_UP_CALL (syncUp);

        call.enqueue ( new Callback<SyncUp> () {
            @Override
            public void onResponse(Call<SyncUp> call, Response<SyncUp> response) {

                if(!response.isSuccessful ()){
                    textviewresult.setText ( "Code "+response.code () );
                    return;
                }

                SyncUp syncResponse = response.body ();

                String content = "";
                content += "Code: " + response.code () + "\n";
                content += "Active: " + syncResponse.getActive () + "\n";
                content += "Name: " + syncResponse.getUser_name () + "\n";
                content += "Email: " + syncResponse.getEmail () + "\n";
                content += "Password: " + syncResponse.getPassword () + "\n";
                content += "status: " + syncResponse.getActive () + "\n";
                content += "message: " + syncResponse.getMessage () + "\n";
                content += "Type: " + syncResponse.getType () + "\n\n";
//                content += "LastSyncTime: " + syncResponse.get.getLast_sync_time () +"\n\n";

                textviewresult.setText (content );
            }

            @Override
            public void onFailure(Call<SyncUp> call, Throwable t) {
                textviewresult.setText ( t.getMessage () );
            }
        } );
    }
}