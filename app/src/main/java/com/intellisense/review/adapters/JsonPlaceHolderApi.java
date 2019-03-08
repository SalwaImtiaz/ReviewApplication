package com.intellisense.review.adapters;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 3/30/2019.
 */

public interface JsonPlaceHolderApi {

//    @GET("posts")
//    Call<List<Post>> getPosts(@Query("userID")int userid);

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);


    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("public/syncdown/1")
    Call<SyncDown> SyncDown_Call(@Body SyncDown syncDown);

    @POST("public/syncup/1")
    Call<SyncUp> SYNC_UP_CALL(@Body SyncUp syncUp);

    @POST("posts")
    Call<Post> createPost(@Body Post post);


//
//    @FormUrlEncoded
//    @POST("posts")
//    Call<Post> createPost(
//            @Field("userId") int userId,
//            @Field("title") String title,
//            @Field("body") String text
//    );
//
//    @FormUrlEncoded
//    @POST("posts")
//    Call<Post> createPost(@FieldMap Map<String, String> fields);
}
