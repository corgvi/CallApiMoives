package com.example.callapimoives.utils;

import com.example.callapimoives.models.MovieModel;
import com.example.callapimoives.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // search with name
    //https://api.themoviedb.org/3/search/movie?api_key=9c80c58d60a64ebd960ccc7761b03455&query=Jack+Reacher&page=1

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    // search with id film
    //https://api.themoviedb.org/3/movie/540?api_key=9c80c58d60a64ebd960ccc7761b03455
    //movie_id = 550 is for Jack Reacher actor
    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> searchIdMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    // get popular movie
    // https://api.themoviedb.org/3/movie/popular?api_key=9c80c58d60a64ebd960ccc7761b03455&page=1

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovie(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

}
