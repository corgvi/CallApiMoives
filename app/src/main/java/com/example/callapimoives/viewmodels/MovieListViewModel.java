package com.example.callapimoives.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.callapimoives.models.MovieModel;
import com.example.callapimoives.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getListMovie(){
        return movieRepository.getListMovie();
    }

    public LiveData<List<MovieModel>> getListMoviePopular(){
        return movieRepository.getListMoviePopular();
    }

    // Calling method in viewModel
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePopularApi(int pageNumber){
        movieRepository.searchMoviePopularApi(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}
