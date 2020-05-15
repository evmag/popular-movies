package com.github.evmag.popularmoviespt.utilities;

import com.github.evmag.popularmoviespt.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Utility functions for parsing JSON data from TMDb
public class MovieDataJsonParser {

    // Key names
    private static final String RESULTS = "results";
    private static final String MOVIE_ID = "id";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String PLOT_SYNOPSIS = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";

    private static final String VIDEO_TYPE = "type";

    private static final String VIDEO_SITE = "site";
    private static final String VIDEO_KEY = "key";
    private static final String VIDEO_NAME = "name";

    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";

    private static final String SITE_YOUTUBE = "youtube";
    private static final String TYPE_TRAILER = "trailer";

    // Parses provided JSON string into a List<Movie>
    public static List<Movie> parseMovieDataJson(String movieDataJson) {
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject movieResultJson = new JSONObject(movieDataJson);
            JSONArray moviesJsonArray = movieResultJson.getJSONArray(RESULTS);

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieJsonObject = moviesJsonArray.getJSONObject(i);

                int movieId = movieJsonObject.getInt(MOVIE_ID);

                String originalTitle = movieJsonObject.getString(ORIGINAL_TITLE);

                // Poster path can contain "null" value, set it to empty string in that case
                String posterPath = movieJsonObject.optString(POSTER_PATH);
                if (posterPath.equals("null")) {
                    posterPath = "";
                }

                String plotSynopsis = movieJsonObject.getString(PLOT_SYNOPSIS);
                double userRating = movieJsonObject.getDouble(USER_RATING);

                // Extract only the year from date or set it to "-" if date is null
                String releaseDate = movieJsonObject.getString(RELEASE_DATE);
                if (releaseDate.equals("null") || releaseDate.length() < 4) {
                    releaseDate = "-";
                } else {
                    releaseDate = releaseDate.substring(0, 4);
                }

                Movie movie = new Movie(movieId, originalTitle, posterPath, plotSynopsis, userRating, releaseDate);
                movie.setSortOrder(i); // Used for maintaining the original sort order after inserting movies to the database
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return movies;
    }

    /*
    * Parses provided Videos JSON String to a List<List<String>>
    * First element contains a list with the extracted trailer keys
    * Second element contains a list with the extracted trailer names
    *
     */
    public static List<List<String>> parseMovieTrailersJson(String movieTrailersJson) {
        List<String> trailerKeys = new ArrayList<>();
        List<String> trailerNames = new ArrayList<>();

        try {
            JSONObject trailersResults = new JSONObject(movieTrailersJson);
            JSONArray videos = trailersResults.getJSONArray(RESULTS);

            for (int i = 0; i < videos.length(); i++) {
                JSONObject video = videos.getJSONObject(i);

                String videoType = video.getString(VIDEO_TYPE);

                if (videoType != null && videoType.toLowerCase().equals(TYPE_TRAILER)) {
                    String site = video.getString(VIDEO_SITE);
                    if (site.toLowerCase().equals(SITE_YOUTUBE)) {
                        String key = video.getString(VIDEO_KEY);
                        String name = video.getString(VIDEO_NAME);

                        trailerKeys.add(key);
                        trailerNames.add(name);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        List<List<String>> results = new ArrayList<>();
        results.add(trailerKeys);
        results.add(trailerNames);

        return results;
    }

    /*
     * Parses provided Reviews JSON String to a List<List<String>>
     * First element contains a list with the extracted review author names
     * Second element contains a list with the extracted review contents
     *
     */
    public static List<List<String>> parseMovieReviewsJson(String movieReviewsJson) {
        List<String> authors = new ArrayList<>();
        List<String> contents = new ArrayList<>();

        try {
            JSONObject reviewsResults = new JSONObject(movieReviewsJson);
            JSONArray reviews = reviewsResults.getJSONArray(RESULTS);

            for (int i = 0; i < reviews.length(); i++) {
                JSONObject review = reviews.getJSONObject(i);

                String author = review.getString(REVIEW_AUTHOR);
                String content = review.getString(REVIEW_CONTENT);

                authors.add(author);
                contents.add(content);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        List<List<String>> results = new ArrayList<>();
        results.add(authors);
        results.add(contents);

        return results;
    }
}
