package com.example.callapimoives.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.callapimoives.models.MovieModel;
import com.example.callapimoives.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public MovieApiClient getMovieApiClient() {
        return movieApiClient;
    }

    public LiveData<List<MovieModel>> getListMovie() {
        return MovieApiClient.getListMovie();
    }

    public LiveData<List<MovieModel>> getListMoviePopular() {
        return MovieApiClient.getListMoviePopular();
    }

    //Calling the method in repository
    public void searchMovieApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePopularApi(int pageNumber) {
        mPageNumber = pageNumber;
        movieApiClient.searchMoviePopularApi(pageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
        searchMoviePopularApi(mPageNumber+1);
    }
}
