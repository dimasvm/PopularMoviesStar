package com.example.dimas.popular_movies_star.data.network;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by       : dimas on 12/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MovieService {

    @GET("discover/movie?")
    Observable<ResponseMovie<Movie>> getDiscoverMovie(@Query("page") int page);
}
