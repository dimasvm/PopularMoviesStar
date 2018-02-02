package com.example.dimas.popular_movies_star.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import com.example.dimas.popular_movies_star.data.model.ResponseTrailer;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.data.network.MovieClient;
import com.example.dimas.popular_movies_star.data.network.MovieService;
import com.example.dimas.popular_movies_star.mvp.ResultListener;
import com.example.dimas.popular_movies_star.mvp.model.DetailModel;
import com.example.dimas.popular_movies_star.mvp.model.DetailModelImp;
import com.example.dimas.popular_movies_star.mvp.view.DetailMovieView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailPresenterImp implements DetailPresenter {

    private Context mContext;
    private DetailMovieView detailMovieView;
    private DetailModel detailModel;

    public DetailPresenterImp(Context context, DetailMovieView detailMovieView) {
        this.mContext = context;
        this.detailMovieView = detailMovieView;
        detailModel = new DetailModelImp();
    }

    @NonNull
    @Override
    public void attachView(DetailMovieView view) {
        this.detailMovieView = view;
    }

    @NonNull
    @Override
    public void detachView(DetailMovieView view) {

    }

    @Override
    public void setupTrailerVideo(int idMovie) {
        MovieService movieService = MovieClient.createService(MovieService.class);
        // TODO: 02/02/18 get trailer observable
        Observable<ResponseTrailer> trailerObservable = movieService.getTrailer(idMovie);
        detailModel.callTrailerVideo(trailerObservable, new ResultListener<Trailer>() {
            @Override
            public void onSuccess(List<Trailer> listT) {
                detailMovieView.showTrailer(listT);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
}
