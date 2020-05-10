package com.github.evmag.popularmoviespt.utilities;

import android.net.Uri;

import com.github.evmag.popularmoviespt.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// Utilities for communication with the TMDb API
public class NetworkUtils {
    // Base URLs
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String SORT_BY_RATING_URL = "top_rated";
    private static final String SORT_BY_POPULARITY_URL = "popular";
    private static final String VIDEOS_URL = "/videos";
    private static final String REVIEWS_URL = "/reviews";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    // TMDB API key, defined in local.properties file with key: "tmdb.api.key.value"
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY_VALUE = BuildConfig.TMDB_API_KEY;


    // URL builder for the movies list API path
    // If sortByPopularity is true, the list is sorted by popularity, otherwise by user rating
    public static URL buildURL(boolean sortByPopularity) {
        String baseUrl = BASE_URL +(sortByPopularity ? SORT_BY_POPULARITY_URL : SORT_BY_RATING_URL);

        return appendAPIKey(baseUrl);
    }

    // URL builder for the poster images
    public static String buildThumbnailURL(String posterPath) {
        return BASE_IMAGE_URL + posterPath;
    }

    public static URL buildTrailersURL(int movieID) {
        String baseUrl = BASE_URL + movieID + VIDEOS_URL;

        return appendAPIKey(baseUrl);
    }

    public static URL buildReviewsURL(int movieID) {
        String baseUrl = BASE_URL + movieID + REVIEWS_URL;

        return appendAPIKey(baseUrl);
    }

    public static URL buildVideoURL(String site, String key) {
        URL url = null;

        try {
            switch (site.toLowerCase()) {
                case "youtube":
                    url = new URL("https://www.youtube.com/watch?v=" + key);
                    break;
                case "vimeo":
                    url = new URL("https://www.vimeo.com/" + key);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static URL appendAPIKey(String baseUrl) {
        Uri buildUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // Returns the entire result from the HTTP response of the provided URL
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
