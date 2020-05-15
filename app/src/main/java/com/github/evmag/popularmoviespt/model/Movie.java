package com.github.evmag.popularmoviespt.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

// Movie entity
@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private int mMovieId;
    @ColumnInfo(name = "sort_order")
    private int mSortOrder;
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
    @ColumnInfo(name = "trailer_keys")
    private List<String> mTrailerKeys;
    @ColumnInfo(name = "trailer_names")
    private List<String> mTrailerNames;
    @ColumnInfo(name = "review_authors")
    private List<String> mReviewAuthors;
    @ColumnInfo(name = "review_contents")
    private List<String> mReviewContents;

    // == Constructors ===

    @Ignore
    public Movie(int movieId,String originalTitle, String posterPath, String plotSynopsis, double userRating, String releaseDate) {
        mMovieId = movieId;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mPlotSynopsis = plotSynopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

    public Movie(int movieId, int sortOrder, String originalTitle, String posterPath,
                 String plotSynopsis, double userRating, String releaseDate,
                 List<String> trailerKeys, List<String> trailerNames, List<String> reviewAuthors, List<String> reviewContents) {
        mMovieId = movieId;
        mSortOrder = sortOrder;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mPlotSynopsis = plotSynopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mTrailerKeys = trailerKeys;
        mTrailerNames = trailerNames;
        mReviewAuthors = reviewAuthors;
        mReviewContents = reviewContents;
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

    public int getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(int sortOrder) {
        mSortOrder = sortOrder;
    }

    public List<String> getTrailerKeys() {
        return mTrailerKeys;
    }

    public void setTrailerKeys(List<String> trailerKeys) {
        mTrailerKeys = trailerKeys;
    }

    public List<String> getReviewAuthors() {
        return mReviewAuthors;
    }

    public void setReviewAuthors(List<String> reviewAuthors) {
        mReviewAuthors = reviewAuthors;
    }

    public List<String> getReviewContents() {
        return mReviewContents;
    }

    public void setReviewContents(List<String> reviewContents) {
        mReviewContents = reviewContents;
    }

    public List<String> getTrailerNames() {
        return mTrailerNames;
    }

    public void setTrailerNames(List<String> trailerNames) {
        mTrailerNames = trailerNames;
    }
}
