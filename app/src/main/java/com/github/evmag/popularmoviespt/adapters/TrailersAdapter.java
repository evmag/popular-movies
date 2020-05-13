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
    private List<String> mTrailers;

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new TrailersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        holder.mTrailerName.setText("Trailer " + position);

    }

    @Override
    public int getItemCount() {
        return (mTrailers == null) ? 0 : mTrailers.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {
        TextView mTrailerName;

        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerName = itemView.findViewById(R.id.tv_trailer);
        }
    }
}
