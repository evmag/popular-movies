package com.github.evmag.popularmoviespt.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MoviesDatabase;

public class DetailViewModel extends AndroidViewModel {
    private LiveData<Movie> mMovie;
    private LiveData<Movie> mMovieFromFavorites;
    private boolean mIsMovieFavorite;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUp(int movieId, String sourceDatabaseName) {
        if (sourceDatabaseName.equals(MoviesDatabase.FAVORITE_MOVIES_DB_NAME)) {
            // TODO: Handle this case
        } else {
            MoviesDatabase moviesSourceDatabase = MoviesDatabase.getInstance(this.getApplication(), sourceDatabaseName);
            mMovie = moviesSourceDatabase.moviesDao().getMovieById(movieId);
        }

        MoviesDatabase moviesFavoriteDatabase = MoviesDatabase.getInstance(this.getApplication(), MoviesDatabase.FAVORITE_MOVIES_DB_NAME);
        mMovieFromFavorites = moviesFavoriteDatabase.moviesDao().getMovieById(movieId);
    }

    public LiveData<Movie> getMovie() {
        return mMovie;
    }

    public LiveData<Movie> getMovieFromFavorites() {
        return mMovieFromFavorites;
    }

    public boolean isMovieFavorite() {
        return mIsMovieFavorite;
    }

    public void setMovieFavorite(boolean movieFavorite) {
        mIsMovieFavorite = movieFavorite;
    }
}
