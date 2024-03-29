package com.intellisense.review.db_classes;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by user on 12/1/2018.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp)
    {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date)
    {
        return date == null ? null : date.getTime();
    }
}
