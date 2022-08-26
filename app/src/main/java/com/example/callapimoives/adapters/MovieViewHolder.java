package com.example.callapimoives.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapimoives.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv_title_movie, tv_release_date_movie, tv_duration_movie;
    ImageView img_movie;
    RatingBar rating_bar;
    OnMovieListener onMovieListener;
    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        tv_title_movie = itemView.findViewById(R.id.tv_title_movie);
        tv_release_date_movie = itemView.findViewById(R.id.tv_release_date_movie);
        tv_duration_movie = itemView.findViewById(R.id.tv_duration_movie);
        rating_bar = itemView.findViewById(R.id.rating_bar);
        img_movie = itemView.findViewById(R.id.img_movie);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
