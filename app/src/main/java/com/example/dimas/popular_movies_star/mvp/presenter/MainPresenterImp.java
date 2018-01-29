package com.example.dimas.popular_movies_star.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dimas.popular_movies_star.OptionMovie;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;
import com.example.dimas.popular_movies_star.data.network.MovieClient;
import com.example.dimas.popular_movies_star.data.network.MovieService;
import com.example.dimas.popular_movies_star.mvp.model.MainModelImp;
import com.example.dimas.popular_movies_star.mvp.ResultListener;
import com.example.dimas.popular_movies_star.mvp.view.MainView;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;
import com.example.dimas.popular_movies_star.ui.base.BasePresenter;
import com.example.dimas.popular_movies_star.utils.Constants;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MainPresenterImp
        implements BasePresenter<MainView>, MainPresenter, ResultListener {

    private MainView mainView;
    private MainModelImp mainModel;
    private MovieAdapter movieAdapter;
    private Context context;


    public MainPresenterImp(Context context, MainView mainView) {
        this.context = context;
        attachView(mainView);
        mainModel = new MainModelImp();
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
    public void getMovies(int page, OptionMovie optionMovie) {
        mainView.showLoading(true);
        MovieService movieService = MovieClient.createService(MovieService.class);

        String sortBy;
        switch (optionMovie){
            case POPULAR_MOVIE:
                sortBy = Constants.APIConstants.SORT_POPULARITY;
                break;
            case UPCOMING_MOVIE:
                sortBy = Constants.APIConstants.SORT_RELEASE_DATE;
                break;
            default:
                return;
        }

        Observable<ResponseMovie<Movie>> movieObservable = movieService.getDiscoverMovie(page, sortBy);

        mainModel.callMatchMovies(movieObservable, this);

    }

    @Override
    public void getNowPlaying(int currentPage) {
        mainView.showLoading(true);
        MovieService movieService = MovieClient.createService(MovieService.class);
        Observable<ResponseMovie<Movie>> movieObservable = movieService.getNowPlaying(currentPage);

        mainModel.callMatchMovies(movieObservable, this);
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        mainView.showLoading(false);
        mainView.showMovie(movies);
    }

    @Override
    public void onFailure(String errorMessage) {
        mainView.showLoading(false);
        mainView.showError(errorMessage);
    }
}
