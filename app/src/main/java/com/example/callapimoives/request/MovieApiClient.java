package com.example.callapimoives.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.callapimoives.AppExecutors;
import com.example.callapimoives.models.MovieModel;
import com.example.callapimoives.response.MovieSearchResponse;
import com.example.callapimoives.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    //Live data for search
    private static MutableLiveData<List<MovieModel>> listMovie = new MutableLiveData<>();

    private static MovieApiClient instance;

    //making global runnable
    private RetrieveMovieRunnable retrieveMovieRunnable;

    //Live data for popular
    private static MutableLiveData<List<MovieModel>> listMoviePopular = new MutableLiveData<>();

    //making popular runnable
    private RetrieveMoviePopularRunnable retrieveMoviePopularRunnable;

    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        listMovie = new MutableLiveData<>();
        listMoviePopular = new MutableLiveData<>();
    }

    public static LiveData<List<MovieModel>> getListMovie(){
        return listMovie;
    }

    public static LiveData<List<MovieModel>> getListMoviePopular(){
        return listMoviePopular;
    }

    public void searchMovieApi(String query, int pageNumber){
        if(retrieveMovieRunnable != null){
            retrieveMovieRunnable = null;
        }

        retrieveMovieRunnable = new RetrieveMovieRunnable(query, pageNumber);

        final Future myHandle = AppExecutors.getInstance().networkIO().submit(retrieveMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandle.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void searchMoviePopularApi(int pageNumber){
        if(retrieveMoviePopularRunnable != null){
            retrieveMoviePopularRunnable = null;
        }

        retrieveMoviePopularRunnable = new RetrieveMoviePopularRunnable(pageNumber);

        final Future myHandle = AppExecutors.getInstance().networkIO().submit(retrieveMoviePopularRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandle.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    //Retrieve data from restapi by runnable class
    private class RetrieveMovieRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public  RetrieveMovieRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getListMovies());
                    if(pageNumber == 1){
                        // sending data to live data
                        // postvalue: used for background thread
                        // setvalue: not for background thread
                        listMovie.postValue(list);
                    } else {
                        List<MovieModel> currentMovie = listMovie.getValue();
                        currentMovie.addAll(list);
                        listMovie.postValue(currentMovie);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("tag", "error: " + error);
                    listMovie.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listMovie.postValue(null);
            }


            if (cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){

            return MovieService.getMovieApi().searchMovie(
                    Credentials.API_KEY
                    ,query
                    ,pageNumber);
        }

        private void cancelRequest(){
            Log.v("tag", "Cancelling search request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviePopularRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public  RetrieveMoviePopularRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMoviesPopular(pageNumber).execute();
                if(cancelRequest){
                    return;
                }

                if (response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getListMovies());
                    if(pageNumber == 1){
                        // sending data to live data
                        // postvalue: used for background thread
                        // setvalue: not for background thread
                        listMoviePopular.postValue(list);
                    } else {
                        List<MovieModel> currentMovie = listMoviePopular.getValue();
                        currentMovie.addAll(list);
                        listMoviePopular.postValue(currentMovie);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("tag", "error: " + error);
                    listMoviePopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listMoviePopular.postValue(null);
            }


            if (cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getMoviesPopular(int pageNumber){

            return MovieService.getMovieApi().getPopularMovie(
                    Credentials.API_KEY
                    ,pageNumber);
        }

        private void cancelRequest(){
            Log.v("tag", "Cancelling search request");
            cancelRequest = true;
        }
    }
}
