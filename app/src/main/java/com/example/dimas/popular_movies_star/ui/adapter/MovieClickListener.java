package com.example.dimas.popular_movies_star.ui.adapter;

import android.widget.ImageView;

import com.example.dimas.popular_movies_star.data.model.Movie;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MovieClickListener {

    void onMovieClicked(Movie movie, ImageView sharedImage);
}
