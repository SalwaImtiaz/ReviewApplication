package com.intellisense.review.db_classes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.intellisense.review.daos.AdminDao;
import com.intellisense.review.daos.ItemsDao;
import com.intellisense.review.daos.QuestionsDao;
import com.intellisense.review.daos.ResponseDao;
import com.intellisense.review.daos.ReviewDao;
import com.intellisense.review.daos.ServerDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 12/1/2018.
 */

@Database(entities = {Admin.class,Review.class,Server.class,Review_Questions.class,Response.class,Items_Served.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase{

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "review_db";
   // private final static String DATABASE_PATH = "/data/data/com.intellisense.review/databases/";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context)
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                copyAttachedDatabase(context.getApplicationContext(), DATABASE_NAME);
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return sInstance;
    }

    private static void copyAttachedDatabase(Context context, String databaseName) {
        final File dbPath = context.getDatabasePath(databaseName);

        // If the database already exists, return
        if (dbPath.exists()) {
            return;
        }

        // Make sure we have a path to the file
        dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = context.getAssets().open("databases/" +databaseName+".db");
            final OutputStream output = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            Log.d(LOG_TAG, "Data written successfully");

            output.flush();
            output.close();
            inputStream.close();
        }
        catch (IOException e) {
            Log.d(LOG_TAG, "Failed to open file", e);
            e.printStackTrace();
        }
    }

    public abstract AdminDao adminDao();

    public abstract ItemsDao itemsDao();

    public abstract QuestionsDao questionsDao();

    public abstract ResponseDao responseDao();

    public abstract ReviewDao reviewDao();

    public abstract ServerDao serverDao();
}
