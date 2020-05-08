package com.github.evmag.popularmoviespt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MovieDataSource;
import com.github.evmag.popularmoviespt.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Get intent that started the activity and extract the provided movie position
        // Should display an error for null intent or no extra (not implemented in this project)
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                int posClicked = intent.getIntExtra(Intent.EXTRA_TEXT, -1);
                mMovie = MovieDataSource.getInstance().getMovie(posClicked);
                populateFields();
            }
        }
    }

    // Populates the activity views with the selected movie values
    private void populateFields() {
        TextView originalTitleTextView = (TextView) findViewById(R.id.tv_original_title);
        TextView plotSynopsisTextView = (TextView) findViewById(R.id.tv_plot_synopsis);
        TextView releaseYearTextView = (TextView) findViewById(R.id.tv_release_year);
        TextView userRatingTextView = (TextView) findViewById(R.id.tv_user_rating);
        ImageView moviePosterImageView = (ImageView) findViewById(R.id.iv_movie_poster);

        originalTitleTextView.setText(mMovie.getOriginalTitle());
        plotSynopsisTextView.setText(mMovie.getPlotSynopsis());
        releaseYearTextView.setText(mMovie.getReleaseDate());
        userRatingTextView.setText(String.format("%.2f",mMovie.getUserRating()));


        if (mMovie.getPosterPath().isEmpty()) {
            Picasso.get()
                    .load(R.drawable.thumbnail_nullposter)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .error(R.drawable.thumbnail_error)
                    .into(moviePosterImageView);
        } else {
            String posterURL = NetworkUtils.buildThumbnailURL(mMovie.getPosterPath());

            Picasso.get()
                    .load(posterURL)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .error(R.drawable.thumbnail_error)
                    .into(moviePosterImageView);
        }
    }
}
