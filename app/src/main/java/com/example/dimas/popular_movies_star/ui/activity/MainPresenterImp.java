package com.example.dimas.popular_movies_star.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dimas.popular_movies_star.data.model.ListResponse;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.network.MovieClient;
import com.example.dimas.popular_movies_star.data.network.MovieService;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;
import com.example.dimas.popular_movies_star.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MainPresenterImp
        implements BasePresenter<MainView>, MainPresenter {

    private MainView mainView;
    private MovieAdapter movieAdapter;
    private Context context;


    public MainPresenterImp(Context context, MainView mainView) {
        this.context = context;
        attachView(mainView);
    }

    @NonNull
    @Override
    public void attachView(MainView view) {
        this.mainView = view;
    }

    @NonNull
    @Override
    public void detachView(MainView view) {

    }


    @Override
    public void getMovies(int page) {
        mainView.showLoading(true);
        MovieService movieService = MovieClient.createService(MovieService.class);
        Call<ListResponse<Movie>> responseCall = movieService.getDiscoverMovie(page);

        responseCall.enqueue(new Callback<ListResponse<Movie>>() {
            @Override
            public void onResponse(Call<ListResponse<Movie>> call, Response<ListResponse<Movie>> response) {
                List<Movie> movies = response.body().getResults();
                mainView.showLoading(false);
                mainView.showMovie(movies);
            }

            @Override
            public void onFailure(Call<ListResponse<Movie>> call, Throwable t) {
                mainView.showLoading(false);
                mainView.showError(t.getMessage());
            }
        });
    }
}
