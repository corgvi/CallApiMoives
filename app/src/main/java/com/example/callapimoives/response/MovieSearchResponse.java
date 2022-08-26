package com.example.callapimoives.response;

import com.example.callapimoives.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//This class is for getting multiple movies ( movies list) - popular movies
public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> listMovies;

    public int getTotal_count(){
        return total_count;
    }

    public List<MovieModel> getListMovies(){
        return listMovies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", listMovies=" + listMovies +
                '}';
    }
}
