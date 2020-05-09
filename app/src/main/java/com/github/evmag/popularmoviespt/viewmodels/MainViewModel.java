package com.github.evmag.popularmoviespt.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MoviesDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> mMovies;


    public MainViewModel(Application application) {
        super(application);

        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(this.getApplication(), MoviesDatabase.TOP_RATED_MOVIES_DB_NAME);
        mMovies = moviesDatabase.moviesDao().getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies; }
}
