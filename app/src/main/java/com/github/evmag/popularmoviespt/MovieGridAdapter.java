package com.github.evmag.popularmoviespt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.model.MovieDataSource;
import com.github.evmag.popularmoviespt.model.MoviesDao;
import com.github.evmag.popularmoviespt.model.MoviesDatabase;
import com.github.evmag.popularmoviespt.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

// RecyclerView adapter for providing movie_grid_item objects to the RecyclerView
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MyViewHolder> {

    private final MovieGridAdapterOnClickHandler mOnClickHandler;
    private final MoviesDao mMoviesDao;

    public MovieGridAdapter(MovieGridAdapterOnClickHandler onClickHandler, MoviesDao moviesDao) {
        mOnClickHandler = onClickHandler;
        mMoviesDao = moviesDao;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_grid_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Movie movie = MovieDataSource.getInstance().getMovie(position);
        Movie movie = mMoviesDao.getMovies().get(position); // TODO: Refactor

        holder.mTextView.setText(movie.getOriginalTitle());

        if (movie.getPosterPath().isEmpty()) {
            Picasso.get()
                    .load(R.drawable.thumbnail_nullposter)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .error(R.drawable.thumbnail_error)
                    .into(holder.mThumbnail);
        } else {
            String posterURL = NetworkUtils.buildThumbnailURL(movie.getPosterPath());

            Picasso.get()
                    .load(posterURL)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .error(R.drawable.thumbnail_error)
                    .into(holder.mThumbnail);
        }

        if (movie.getPosterPath().isEmpty()) {
            holder.mTextView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return mMoviesDao.getMovieCount();
//        return MovieDataSource.getInstance().getMovieCount();
    }

    public interface MovieGridAdapterOnClickHandler {
        void onClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mThumbnail;
        TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.iv_movie_thumbnail);
            mTextView = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickHandler.onClick(getAdapterPosition());
        }
    }
}
