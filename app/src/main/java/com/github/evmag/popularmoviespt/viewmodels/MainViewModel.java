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

    public void setDatabaseSource(String databaseName) {
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
