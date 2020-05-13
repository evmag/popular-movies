package com.github.evmag.popularmoviespt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evmag.popularmoviespt.adapters.ReviewsAdapter;
import com.github.evmag.popularmoviespt.adapters.TrailersAdapter;
import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MoviesDatabase;
import com.github.evmag.popularmoviespt.utilities.MovieDataJsonParser;
import com.github.evmag.popularmoviespt.utilities.NetworkUtils;
import com.github.evmag.popularmoviespt.viewmodels.DetailViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieDetail extends AppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "movie_id";
    public static final String EXTRA_DATABASE_SOURCE_NAME = "database_name";

    private DetailViewModel mDetailViewModel;
    private TrailersAdapter mTrailersAdapter;
    private ReviewsAdapter mReviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Get intent that started the activity and extract the provided movie position
        // Should display an error for null intent or no extra (not implemented in this project)
        Intent intent = getIntent();
        if (intent != null) {
            int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);
            String databaseName = intent.getStringExtra(EXTRA_DATABASE_SOURCE_NAME);

            if (movieId == -1 || databaseName == null) {
                // TODO: error
            } else {
                // TODO: populate view model
                mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
                mDetailViewModel.setUp(movieId, databaseName);
                setupViewModelObservers();
                setupRecyclerViewsAdapters();
            }
//            mMovie = MovieDataSource.getInstance().getMovie(posClicked);
//            populateFields();
        }
    }

    private void setupViewModelObservers() {
        mDetailViewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie != null) {
                    if (movie.getTrailerUrls() == null || movie.getReviewContents() == null) {
                        new FetchMovieTrailersAndReviews().execute(movie.getMovieId());
                    }
                    populateFields();
                }
            }
        });

        mDetailViewModel.getMovieFromFavorites().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                mDetailViewModel.setMovieFavorite(movie != null);
            }
        });
    }

    private void setupRecyclerViewsAdapters() {
        RecyclerView trailersRecyclerView = findViewById(R.id.rv_trailers);
        mTrailersAdapter = new TrailersAdapter();
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trailersRecyclerView.setAdapter(mTrailersAdapter);

        RecyclerView reviewsRecyclerView = findViewById(R.id.rv_reviews);
        mReviewsAdapter = new ReviewsAdapter();
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager((this)));
        reviewsRecyclerView.setAdapter(mReviewsAdapter);
    }

    // Populates the activity views with the selected movie values
    private void populateFields() {
        TextView originalTitleTextView = findViewById(R.id.tv_original_title);
        TextView plotSynopsisTextView =  findViewById(R.id.tv_plot_synopsis);
        TextView releaseYearTextView =  findViewById(R.id.tv_release_year);
        TextView userRatingTextView =  findViewById(R.id.tv_user_rating);
        ImageView moviePosterImageView =  findViewById(R.id.iv_movie_poster);

        Movie movie = mDetailViewModel.getMovie().getValue();

        originalTitleTextView.setText(movie.getOriginalTitle());
        plotSynopsisTextView.setText(movie.getPlotSynopsis());
        releaseYearTextView.setText(movie.getReleaseDate());
        userRatingTextView.setText(String.format("%.2f",movie.getUserRating()));


        if (movie.getPosterPath().isEmpty()) {
            Picasso.get()
                    .load(R.drawable.thumbnail_nullposter)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .error(R.drawable.thumbnail_error)
                    .into(moviePosterImageView);
        } else {
            String posterURL = NetworkUtils.buildThumbnailURL(movie.getPosterPath());

            Picasso.get()
                    .load(posterURL)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .error(R.drawable.thumbnail_error)
                    .into(moviePosterImageView);
        }

        mTrailersAdapter.setTrailerUrls(movie.getTrailerUrls());
        mReviewsAdapter.setData(movie.getReviewAuthors(), movie.getReviewContents());
    }

    class FetchMovieTrailersAndReviews extends AsyncTask<Integer, Void, String[]> {
        @Override
        protected String[] doInBackground(Integer... movieIDs) {
            int movieID = movieIDs[0];
            URL requestTrailersURL = NetworkUtils.buildTrailersURL(movieID);
            URL requestReviewsURL = NetworkUtils.buildReviewsURL(movieID);

            String[] results = new String[2];
            try {
                results[0] = NetworkUtils.getResponseFromHttpUrl(requestTrailersURL);
                results[1] = NetworkUtils.getResponseFromHttpUrl(requestReviewsURL);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return results;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            if (s == null) {
//                displayErrorNetwork();
            } else {
                List<String> trailerURLs = MovieDataJsonParser.parseMovieTrailersJson(s[0]);
                List<List<String>> reviews = MovieDataJsonParser.parseMovieReviewsJson(s[1]);

                if (trailerURLs == null && reviews == null) {
//                    displayErrorNetwork();
                } else {
                    Movie movie = mDetailViewModel.getMovie().getValue();

                    movie.setTrailerUrls(trailerURLs);
                    movie.setReviewAuthors(reviews.get(0));
                    movie.setReviewContents(reviews.get(1));

                    // TODO: move this to another thread
                    MoviesDatabase moviesDatabase =
                            MoviesDatabase.getInstance(MovieDetail.this, mDetailViewModel.getSourceDatabaseName());
                    moviesDatabase.moviesDao().updateMovie(movie);
                }
            }
        }

    }


}
