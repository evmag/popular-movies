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
    private List<String> mTrailerUrls;

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new TrailersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        int trailerNumber = position + 1;
        holder.mTrailerName.setText("Trailer " + trailerNumber);
    }

    @Override
    public int getItemCount() {
        return (mTrailerUrls == null) ? 0 : mTrailerUrls.size();
    }

    public void setTrailerUrls(List<String> trailerUrls) {
        mTrailerUrls = trailerUrls;
        notifyDataSetChanged();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {
        TextView mTrailerName;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerName = itemView.findViewById(R.id.tv_trailer);
        }
    }
}
