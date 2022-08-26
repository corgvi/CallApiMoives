package com.example.callapimoives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.callapimoives.adapters.MovieRecyclerView;
import com.example.callapimoives.adapters.OnMovieListener;
import com.example.callapimoives.models.MovieModel;
import com.example.callapimoives.request.MovieService;
import com.example.callapimoives.response.MovieSearchResponse;
import com.example.callapimoives.utils.Credentials;
import com.example.callapimoives.utils.MovieApi;
import com.example.callapimoives.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    //Before we run our app, we need to add the Network Security config
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerView;
    private Button btn_search_actor, btn_search_movie_id;
    private MovieApi movieApi;
    private MovieListViewModel movieListViewModel;
    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        SetupSearchView();

        recyclerView = findViewById(R.id.rcv_movie);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        //calling the observers
        ObserveAnyChange();

        ObserverPopularMovie();

        //  RecyclerView Pagination
        //  Loading next page of api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    // here we need to display the next search results on the next page of api
                    movieListViewModel.searchNextPage();
                }
            }
        });

        //Getting popular data
        movieListViewModel.searchMoviePopularApi(1);

//        searchMovieApi("Fast", 1);

//        movieApi = MovieService.getMovieApi();
//        btn_search_actor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                GetRetrofitResponse();
//                //displaying only the results of page 1
//                searchMovieApi("fast", 1);
//            }
//        });
//        btn_search_movie_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                GetRetrofitResponeMovieId();
//                searchMovieApi();
//            }
//        });
    }

    // Get data from searchview & qury the api to get the results(Movies)
    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        //the search string getted from searchview
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPopular = false;
            }
        });
    }


    private void ObserverPopularMovie() {
        movieListViewModel.getListMoviePopular().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observer for any data change
                if(movieModels != null){
                    for(MovieModel movieModel : movieModels){
                        // Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecyclerView.setData(movieModels);
                    }
                }
            }
        });
    }
    // observing any data change
    private void ObserveAnyChange(){
        movieListViewModel.getListMovie().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observer for any data change
                if(movieModels != null){
                    for(MovieModel movieModel : movieModels){
                        // Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecyclerView.setData(movieModels);
                    }
                }
            }
        });
    }

//    //4. calling method in MainActivity
//    private void searchMovieApi(String query, int pageNumber){
//        movieListViewModel.searchMovieApi(query, pageNumber);
//    }

    //5. initializing recyclerview & adding data to it
    private void ConfigureRecyclerView(){
        movieRecyclerView = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onMovieClick(int position) {
        // we don't need position of the movie in recyclerview
        // we need the id of the movie in order to get all it's details
        Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        intent.putExtra("movie", movieRecyclerView.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    // search with name actor
//    private void GetRetrofitResponse() {
//        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
//                Credentials.API_KEY,
//                "Action",
//                1
//        );
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if(response.code() == 200){
//                    Log.v("Tag", "The response: " + response.body().getListMovies());
//
//                    List<MovieModel> listMovie = new ArrayList<>(response.body().getListMovies());
//
//                    for(MovieModel movieModel : listMovie){
//                        Log.v("Tag", "The release date: " + movieModel.getTitle());
//                    }
//                }else {
//                    try {
//                        Log.v("Tag", "Error: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    // search with id movie
//    private void GetRetrofitResponeMovieId(){
//        Call<MovieModel> responseCall = movieApi.searchIdMovie(
//                540,
//                Credentials.API_KEY
//        );
//
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if(response.code() == 200){
//                    Log.v("Tag", "Movie: " + response.body().getTitle());
//                } else {
//                    try {
//                        Log.v("Tag", "Error: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }

}