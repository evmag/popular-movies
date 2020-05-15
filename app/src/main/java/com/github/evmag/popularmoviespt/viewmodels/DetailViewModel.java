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

    private Movie mBackupFavoriteMovie;

    private String mSourceDatabaseName;
    private boolean mIsMovieFavorite;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUp(int movieId, String sourceDatabaseName) {
        MoviesDatabase moviesFavoriteDatabase = MoviesDatabase.getInstance(this.getApplication(), MoviesDatabase.FAVORITE_MOVIES_DB_NAME);
        mMovieFromFavorites = moviesFavoriteDatabase.moviesDao().getMovieById(movieId);

        mSourceDatabaseName = sourceDatabaseName;

        MoviesDatabase moviesSourceDatabase = MoviesDatabase.getInstance(this.getApplication(), sourceDatabaseName);
        mMovie = moviesSourceDatabase.moviesDao().getMovieById(movieId);
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

    public String getSourceDatabaseName() {
        return mSourceDatabaseName;
    }

    public void setBackupFavoriteMovie(Movie movie) {
        mBackupFavoriteMovie = movie;
    }

    public Movie getBackupFavoriteMovie() {
        return mBackupFavoriteMovie;
    }
}
