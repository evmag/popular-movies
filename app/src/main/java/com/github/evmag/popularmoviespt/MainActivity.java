package com.github.evmag.popularmoviespt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.evmag.popularmoviespt.adapters.MovieGridAdapter;
import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MoviesDatabase;
import com.github.evmag.popularmoviespt.utilities.DiskIOExecutor;
import com.github.evmag.popularmoviespt.utilities.MovieDataJsonParser;
import com.github.evmag.popularmoviespt.utilities.NetworkUtils;
import com.github.evmag.popularmoviespt.viewmodels.MainViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.MovieGridAdapterOnClickHandler {

    public static final int THUMBNAIL_WIDTH = 185;
    private RecyclerView mMovieGrid;
    private MovieGridAdapter mMovieGridAdapter;

    private boolean mSortByPopularity;
    private ProgressBar mLoadingProgressBar;
    private TextView mErrorDisplayTextView;
    private MainViewModel mMainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGrid = findViewById(R.id.rv_movie_grid);
        mLoadingProgressBar = findViewById(R.id.pb_loading);
        mErrorDisplayTextView = findViewById(R.id.tv_error_display);

        mSortByPopularity = false;

        // Setup the RecyclerView
        mMovieGridAdapter = new MovieGridAdapter(this);

        int screenWidthDp = getResources().getConfiguration().screenWidthDp;
        int maxGridColumns = screenWidthDp / THUMBNAIL_WIDTH;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, maxGridColumns);
        mMovieGrid.setLayoutManager(layoutManager);
        mMovieGrid.setAdapter(mMovieGridAdapter);

        setupViewModel();
        loadOnlineData();
    }

    // Handle RecyclerView clicks, starts a DetailActivity providing the clicked position
    @Override
    public void onClick(int movieId) {
        Intent intentToMovieDetailActivity = new Intent(this, MovieDetail.class);
        intentToMovieDetailActivity.putExtra(MovieDetail.EXTRA_MOVIE_ID, movieId);
        intentToMovieDetailActivity.putExtra(MovieDetail.EXTRA_DATABASE_SOURCE_NAME, getDatabaseName());
        startActivity(intentToMovieDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mMainViewModel.getMainActivityState()) {
            case TOP_RATED:
                menu.findItem(R.id.action_sort_by_rating).setChecked(true);
                break;
            case MOST_POPULAR:
                menu.findItem(R.id.action_sort_by_popularity).setChecked(true);
                break;
            case FAVORITES:
                menu.findItem(R.id.action_favorite_movies).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_rating:
                mMainViewModel.setMainActivityState(MainViewModel.MainActivityState.TOP_RATED);
                mSortByPopularity = false;
                setupViewModel();
                loadOnlineData();
                return true;
            case R.id.action_sort_by_popularity:
                mMainViewModel.setMainActivityState(MainViewModel.MainActivityState.MOST_POPULAR);
                mSortByPopularity = true;
                setupViewModel();
                loadOnlineData();
                return true;
            case R.id.action_favorite_movies:
                mMainViewModel.setMainActivityState(MainViewModel.MainActivityState.FAVORITES);
                setupViewModel();
                return true;
            case R.id.action_refresh:
                loadOnlineData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupViewModel() {
        // Initialize view model
        if (mMainViewModel == null) {
            mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        }

        // Remove observers from current movies list if there are any
        if (mMainViewModel.getMovies() != null && mMainViewModel.getMovies().hasObservers()) {
            mMainViewModel.getMovies().removeObservers(this);
        }

        // Set the database source
        mMainViewModel.setDatabaseSource();

        // Add observer to update the recyclerview adapter
        mMainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mMovieGrid.setVisibility(View.VISIBLE);
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mErrorDisplayTextView.setVisibility(View.INVISIBLE);
                mMovieGridAdapter.setMovies(movies);
            }
        });
    }

    // Initiated the loading of data
    private void loadOnlineData() {
        mMovieGrid.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mErrorDisplayTextView.setVisibility(View.INVISIBLE);

        new FetchMovieData().execute();
    }


    // Display error message
    private void displayErrorNetwork() {
        mMovieGrid.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mErrorDisplayTextView.setVisibility(View.VISIBLE);
    }

    private String getDatabaseName() {
        switch (mMainViewModel.getMainActivityState()) {
            case TOP_RATED:
                return MoviesDatabase.TOP_RATED_MOVIES_DB_NAME;
            case MOST_POPULAR:
                return MoviesDatabase.POPULAR_MOVIES_DB_NAME;
            case FAVORITES:
                return MoviesDatabase.FAVORITE_MOVIES_DB_NAME;
            default:
                return "";
        }
    }

    // Async task for downloading the movie data from the API end point
    class FetchMovieData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            URL requestURL = NetworkUtils.buildURL(mSortByPopularity);

            try {
                return NetworkUtils.getResponseFromHttpUrl(requestURL);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                displayErrorNetwork();
            } else {
                final List<Movie> movies = MovieDataJsonParser.parseMovieDataJson(s);
                if (movies == null) {
                    displayErrorNetwork();
                } else {

                    final MoviesDatabase moviesDatabase =
                            MoviesDatabase.getInstance(MainActivity.this, getDatabaseName());
                    DiskIOExecutor.getInstance().getDiskIOExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            moviesDatabase.moviesDao().deleteAllMovies();
                            moviesDatabase.moviesDao().insertAllMovies(movies);
                        }
                    });

                }
            }
        }
    }
}
