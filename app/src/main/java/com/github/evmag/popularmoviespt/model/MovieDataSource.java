package com.github.evmag.popularmoviespt.model;

import java.util.List;

// Wrapper class for a list of movies
// Implements singleton pattern to allow data access to both activities
public class MovieDataSource {
    private static MovieDataSource mInstance;

    private List<Movie> mMovies;

    // Make default constructor private
    private MovieDataSource() {}

    // Singleton instance
    public static MovieDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new MovieDataSource();
        }

        return mInstance;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public Movie getMovie(int position) {
        return mMovies.get(position);
    }

    public int getMovieCount() {
        return mMovies.size();
    }
}
