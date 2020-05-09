package com.github.evmag.popularmoviespt.model;

import java.util.List;

public class Movie {
    private int mMovieId;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mPlotSynopsis;
    private double mUserRating;
    private String mReleaseDate;
    private List<Trailer> mTrailers;
    private List<Review> mReviews;

    // == Constructors ===

    public Movie() {
    }

    public Movie(String originalTitle, String posterPath, String plotSynopsis, double userRating, String releaseDate) {
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mPlotSynopsis = plotSynopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
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
}
