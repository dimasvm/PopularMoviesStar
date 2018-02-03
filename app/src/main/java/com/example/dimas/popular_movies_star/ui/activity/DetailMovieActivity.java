package com.example.dimas.popular_movies_star.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.mvp.presenter.DetailPresenter;
import com.example.dimas.popular_movies_star.mvp.presenter.DetailPresenterImp;
import com.example.dimas.popular_movies_star.mvp.view.DetailMovieView;
import com.example.dimas.popular_movies_star.ui.adapter.TrailerAdapter;
import com.example.dimas.popular_movies_star.ui.fragment.DetailMovieFragment;
import com.example.dimas.popular_movies_star.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_IMAGE_MOVIE_TRANSITION_NAME;
import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_MOVIE;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailMovieActivity
        extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        supportPostponeEnterTransition();

        bundle = getIntent().getExtras();



        if (savedInstanceState == null){

            DetailMovieFragment detailMovieFragment = new DetailMovieFragment();
            detailMovieFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail_movie_fragment, detailMovieFragment)
                    .commit();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
