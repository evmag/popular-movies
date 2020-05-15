package com.github.evmag.popularmoviespt.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.HashMap;
import java.util.Map;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(ListStringConverter.class)
public abstract class MoviesDatabase extends RoomDatabase {
    public static final String POPULAR_MOVIES_DB_NAME = "popular_movies";
    public static final String TOP_RATED_MOVIES_DB_NAME = "top_rated_movies";
    public static final String FAVORITE_MOVIES_DB_NAME = "favorite_movies";

    private static final Object LOCK = new Object();

    private static Map<String, MoviesDatabase> sMoviesDBs;

    public static MoviesDatabase getInstance(Context context, String dbName) {
        if (sMoviesDBs == null) {
            sMoviesDBs = new HashMap<>();
        }

        MoviesDatabase moviesDatabase= sMoviesDBs.get(dbName);
        if (moviesDatabase == null) {
            synchronized (LOCK) {
                moviesDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class,
                        dbName)
                        .build();
                sMoviesDBs.put(dbName, moviesDatabase);
            }
        }

        return moviesDatabase;
    }

    public abstract MoviesDao moviesDao();
}
