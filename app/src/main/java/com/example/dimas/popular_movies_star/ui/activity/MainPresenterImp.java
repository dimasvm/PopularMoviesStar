package com.example.dimas.popular_movies_star.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;
import com.example.dimas.popular_movies_star.data.network.MovieClient;
import com.example.dimas.popular_movies_star.data.network.MovieService;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;
import com.example.dimas.popular_movies_star.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
        final Observable<ResponseMovie<Movie>> movieObservable = movieService.getDiscoverMovie(page);

        movieObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMovie<Movie>>() {
                               @Override
                               public void accept(ResponseMovie<Movie> movieResponseMovie) throws Exception {
                                   List<Movie> movies = movieResponseMovie.getResults();
                                   mainView.showLoading(false);
                                   mainView.showMovie(movies);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mainView.showLoading(false);
                                mainView.showError(throwable.getMessage());
                            }
                        });
    }
}
