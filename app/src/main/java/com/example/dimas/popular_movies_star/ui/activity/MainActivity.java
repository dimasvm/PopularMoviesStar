package com.example.dimas.popular_movies_star.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";
    int currentPage = 1;
    // Presenter
    MainPresenter mainPresenter;
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        mainPresenter = new MainPresenterImp(this, this);
        mainPresenter.getMovies(currentPage);
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }


    @Override
    public void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, "Error :\n " + errorMessage, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showMovie(List<Movie> movies) {
        movieAdapter = new MovieAdapter(this, movies);
        mRecyclerView.setAdapter(movieAdapter);
    }
}