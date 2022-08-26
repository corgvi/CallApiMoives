package com.example.callapimoives;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.callapimoives.adapters.MovieViewHolder;
import com.example.callapimoives.models.MovieModel;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvDesc;
    private ImageView imageView;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_desc);
        imageView = findViewById(R.id.imageView_details);
        ratingBar = findViewById(R.id.rating_bar);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            tvDesc.setText(movieModel.getOverview());
            tvTitle.setText(movieModel.getTitle());
            ratingBar.setRating(movieModel.getVote_average()/2);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path()).into(imageView);

        }
    }
}