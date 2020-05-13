package com.github.evmag.popularmoviespt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.evmag.popularmoviespt.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private List<String> mAuthors;
    private List<String> mContents;

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        holder.mAuthor.setText(mAuthors.get(position));
        holder.mContent.setText(mContents.get(position));
    }

    @Override
    public int getItemCount() {
        return (mAuthors == null) ? 0 : mAuthors.size();
    }

    public void setData(List<String> authors, List<String> contents) {
        mAuthors = authors;
        mContents = contents;
        notifyDataSetChanged();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthor;
        TextView mContent;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.tv_author);
            mContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
