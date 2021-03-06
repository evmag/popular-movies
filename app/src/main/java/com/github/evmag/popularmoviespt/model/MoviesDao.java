package com.github.evmag.popularmoviespt.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY sort_order ASC")
    LiveData<List<Movie>> getMovies();

    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    LiveData<Movie> getMovieById(int movieId);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertMovie(Movie movie);

    @Insert
    void insertAllMovies(List<Movie> movies);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

}
