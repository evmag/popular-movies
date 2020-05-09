package com.github.evmag.popularmoviespt.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MoviesDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> mMovies;
    private MainActivityState mMainActivityState;


    public MainViewModel(Application application) {
        super(application);
        mMainActivityState = MainActivityState.TOP_RATED;
    }

    public void setDatabaseSource() {
        String databaseName = "";

        switch (mMainActivityState) {
            case TOP_RATED:
                databaseName = MoviesDatabase.TOP_RATED_MOVIES_DB_NAME;
                break;
            case MOST_POPULAR:
                databaseName = MoviesDatabase.POPULAR_MOVIES_DB_NAME;
                break;
            case FAVORITES:
                databaseName = MoviesDatabase.FAVORITE_MOVIES_DB_NAME;
                break;
        }

        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(this.getApplication(), databaseName);
        mMovies = moviesDatabase.moviesDao().getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public MainActivityState getMainActivityState() {
        return mMainActivityState;
    }

    public void setMainActivityState(MainActivityState mainActivityState) {
        mMainActivityState = mainActivityState;
    }

    public enum MainActivityState {
        TOP_RATED,
        MOST_POPULAR,
        FAVORITES
    }
}
