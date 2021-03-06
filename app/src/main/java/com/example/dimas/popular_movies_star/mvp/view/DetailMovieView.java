package com.example.dimas.popular_movies_star.mvp.view;

import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.mvp.BaseView;

import java.util.List;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface DetailMovieView extends BaseView {

    void showDetailMovie();

    void showTrailer(List<Trailer> listT);
}
