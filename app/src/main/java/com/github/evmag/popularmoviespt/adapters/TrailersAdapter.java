package com.github.evmag.popularmoviespt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.evmag.popularmoviespt.R;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {
    private List<String> mTrailerKeys;
    private List<String> mTrailerNames;
    private TrailersAdapterOnClickHandler mOnClickHandler;

    public TrailersAdapter(TrailersAdapterOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new TrailersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        holder.mTrailerName.setText(mTrailerNames.get(position));
        if (position == 0) {
            holder.mDivider.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (mTrailerKeys == null) ? 0 : mTrailerKeys.size();
    }

    public void setData(List<String> trailerKeys, List<String> trailerNames) {
        mTrailerKeys = trailerKeys;
        mTrailerNames = trailerNames;
        notifyDataSetChanged();
    }

    public interface TrailersAdapterOnClickHandler {
        void onClickTrailer(String videoURL);
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTrailerName;
        View mDivider;

        TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerName = itemView.findViewById(R.id.tv_trailer);
            mDivider = itemView.findViewById(R.id.divider_trailer_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickHandler.onClickTrailer(mTrailerKeys.get(getAdapterPosition()));
        }
    }
}
