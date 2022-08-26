package com.example.callapimoives.adapters;


import android.content.Context;
import android.graphics.Movie;
import android.media.TimedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.callapimoives.R;
import com.example.callapimoives.models.MovieModel;
import com.example.callapimoives.utils.Credentials;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MovieModel> listMovie = new ArrayList<>();
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;

    public void setData(List<MovieModel> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
            return new MovieViewHolder(view, onMovieListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_popular_item,parent,false);
            return new MoviePopularViewHolder(view, onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == DISPLAY_SEARCH){
            MovieModel movieModel = listMovie.get(position);
            ((MovieViewHolder)holder).tv_title_movie.setText(movieModel.getTitle());
            ((MovieViewHolder)holder).tv_release_date_movie.setText(movieModel.getRelease_date());
            ((MovieViewHolder)holder).tv_duration_movie.setText(movieModel.getOriginal_language());
            ((MovieViewHolder)holder).rating_bar.setRating(movieModel.getVote_average()/2);
            Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path()).into(((MovieViewHolder)holder).img_movie);
        }else {
            MovieModel movieModel = listMovie.get(position);
            ((MoviePopularViewHolder)holder).tv_title_movie.setText(movieModel.getTitle());
            ((MoviePopularViewHolder)holder).tv_release_date_movie.setText(movieModel.getRelease_date());
            ((MoviePopularViewHolder)holder).tv_duration_movie.setText(movieModel.getOriginal_language());
            ((MoviePopularViewHolder)holder).rating_bar.setRating(movieModel.getVote_average()/2);
            Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path()).into(((MovieViewHolder)holder).img_movie);

        }
    }

    @Override
    public int getItemCount() {
        if (listMovie != null){
            return listMovie.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (Credentials.POPULAR){
            return DISPLAY_POP;
        } else {
            return DISPLAY_SEARCH;
        }
    }

    //Getting the movie id when clicked
    public MovieModel getSelectedMovie(int position){
        if(listMovie != null){
            if(listMovie.size() > 0){
                return listMovie.get(position);
            }
        }
        return null;
    }

}
