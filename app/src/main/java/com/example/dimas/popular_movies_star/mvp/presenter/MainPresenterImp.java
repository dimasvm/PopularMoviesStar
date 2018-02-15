package com.example.dimas.popular_movies_star.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.dimas.popular_movies_star.OptionMovie;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;
import com.example.dimas.popular_movies_star.data.network.MovieClient;
import com.example.dimas.popular_movies_star.data.network.MovieService;
import com.example.dimas.popular_movies_star.mvp.ResultListener;
import com.example.dimas.popular_movies_star.mvp.model.MainModelImp;
import com.example.dimas.popular_movies_star.mvp.view.MainView;
import com.example.dimas.popular_movies_star.ui.activity.DetailMovieActivity;
import com.example.dimas.popular_movies_star.utils.Constants;

import java.util.List;

import io.reactivex.Observable;

import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_IMAGE_MOVIE_TRANSITION_NAME;
import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_MOVIE;


/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MainPresenterImp
        implements MainPresenter, ResultListener<Movie> {

    private static final String TAG = MainPresenterImp.class.getSimpleName();
    private MainView mainView;
    private MainModelImp mainModel;
    private Context context;
    private RecyclerView recyclerView;


    public MainPresenterImp(Context context, MainView mainView, RecyclerView recyclerView) {
        this.context = context;
        attachView(mainView);
        mainModel = new MainModelImp();
        this.recyclerView = recyclerView;
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
    public void getPopularMovies(int page) {
        Log.d(TAG, "getPopularMovies: page " + page);

        mainView.showLoading(true);
        MovieService movieService = MovieClient.createService(MovieService.class);

        Observable<ResponseMovie<Movie>> movieObservable = movieService.getPopularMovie(page);

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
    public void getDetailPage(Movie movie, ImageView sharedImage) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        intent.putExtra(EXTRA_IMAGE_MOVIE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImage));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                ((AppCompatActivity) context),
                sharedImage,
                ViewCompat.getTransitionName(sharedImage));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options.toBundle());
        } else context.startActivity(intent);
    }


    @Override
    public void onSuccess(List<Movie> movies) {
        Log.d(TAG, "onSuccess: Movie sukses didapat dari pemanggilan API");
        mainView.showLoading(false);
        mainView.showMovie(movies);
    }


    @Override
    public void onFailure(String errorMessage) {
        mainView.showLoading(false);
        mainView.showError(errorMessage);
    }
}
