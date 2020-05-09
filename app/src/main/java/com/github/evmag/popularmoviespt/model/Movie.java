package com.github.evmag.popularmoviespt.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    private int mMovieId;
    @ColumnInfo(name = "original_title")
    private String mOriginalTitle;
    @ColumnInfo(name = "poster_path")
    private String mPosterPath;
    @ColumnInfo(name = "plot_synopsis")
    private String mPlotSynopsis;
    @ColumnInfo(name = "user_rating")
    private double mUserRating;
    @ColumnInfo(name = "release_date")
    private String mReleaseDate;
    @ColumnInfo(name = "trailers")
    private List<Trailer> mTrailers;
    @ColumnInfo(name = "reviews")
    private List<Review> mReviews;

    // == Constructors ===

    @Ignore
    public Movie(String originalTitle, String posterPath, String plotSynopsis, double userRating, String releaseDate) {
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mPlotSynopsis = plotSynopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

    public Movie(int movieId, String originalTitle, String posterPath, String plotSynopsis, double userRating, String releaseDate, List<Trailer> trailers, List<Review> reviews) {
        mMovieId = movieId;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mPlotSynopsis = plotSynopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mTrailers = trailers;
        mReviews = reviews;
    }

    // === Setters/Getters ==

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        mPlotSynopsis = plotSynopsis;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public void setUserRating(double userRating) {
        mUserRating = userRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public List<Trailer> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers = trailers;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }
}
