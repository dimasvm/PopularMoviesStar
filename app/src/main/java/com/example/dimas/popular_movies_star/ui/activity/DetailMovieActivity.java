package com.example.dimas.popular_movies_star.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.mvp.view.DetailMovieView;
import com.squareup.picasso.Picasso;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailMovieActivity
        extends AppCompatActivity implements DetailMovieView {

    private ImageView imageBackdrop;
    private ImageView imagePoster;
    private TextView titleMovie;
    private TextView ratingMovie;
    private TextView releaseDate;
    private TextView overviewMovie;

    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        Intent intent = getIntent();
        if (intent != null) {
            movie = intent.getParcelableExtra("movie");
        }

        initViews();

        showDetailMovie();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));

        imageBackdrop = findViewById(R.id.backdrop_image);
        imagePoster = findViewById(R.id.poster_image);

        titleMovie = findViewById(R.id.title_movie);
        ratingMovie = findViewById(R.id.rating_movie);
        releaseDate = findViewById(R.id.release_date);
        overviewMovie = findViewById(R.id.overview_text);

    }

    @Override
    public void showDetailMovie() {
        if (movie != null) {
            showLoading(false);

            Picasso.with(this).load(movie.getBackdrop_path()).into(imageBackdrop);
            Picasso.with(this).load(movie.getPoster_path()).into(imagePoster);
            titleMovie.setText(movie.getTitle());
            String release = getResources().getString(R.string.release_date) + movie.getRelease_date();
            ratingMovie.setText(String.valueOf(movie.getVote_average()));
            releaseDate.setText(release);
            overviewMovie.setText(movie.getOverview());

        } else {
            showError("Movie gagal di load");
        }
    }

    @Override
    public void showLoading(boolean b) {

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
