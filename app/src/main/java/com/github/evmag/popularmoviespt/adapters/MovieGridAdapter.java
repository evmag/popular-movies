package com.github.evmag.popularmoviespt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.evmag.popularmoviespt.R;
import com.github.evmag.popularmoviespt.model.Movie;
import com.github.evmag.popularmoviespt.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

// RecyclerView adapter for providing movie_grid_item objects to the RecyclerView
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MyViewHolder> {

    private final MovieGridAdapterOnClickHandler mOnClickHandler;
    private List<Movie> mMovies;

    public MovieGridAdapter(MovieGridAdapterOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
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
        Movie movie = mMovies.get(position);

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
        return (mMovies == null) ? 0 : mMovies.size();
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public interface MovieGridAdapterOnClickHandler {
        void onClick(int movieId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mThumbnail;
        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.iv_movie_thumbnail);
            mTextView = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = mMovies.get(getAdapterPosition());
            mOnClickHandler.onClick(movie.getMovieId());
        }
    }
}
