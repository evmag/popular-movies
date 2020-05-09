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
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String PLOT_SYNOPSIS = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";

    // Parses provided JSON string into a List<Movie>
    public static List<Movie> parseMovieDataJson(String movieDataJson) {
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject movieResultJson = new JSONObject(movieDataJson);
            JSONArray moviesJsonArray = movieResultJson.getJSONArray(RESULTS);

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieJsonObject = moviesJsonArray.getJSONObject(i);

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

                int movieId = movieJsonObject.getInt("id"); // TODO: Refactor

                Movie movie = new Movie(originalTitle, posterPath, plotSynopsis, userRating, releaseDate);
                movie.setMovieId(movieId);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return movies;
    }
}
