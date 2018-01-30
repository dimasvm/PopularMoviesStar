package com.example.dimas.popular_movies_star.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.mvp.presenter.MainPresenter;
import com.example.dimas.popular_movies_star.mvp.presenter.MainPresenterImp;
import com.example.dimas.popular_movies_star.mvp.view.MainView;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;

import java.util.List;

import static com.example.dimas.popular_movies_star.OptionMovie.POPULAR_MOVIE;
import static com.example.dimas.popular_movies_star.OptionMovie.UPCOMING_MOVIE;

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
        mainPresenter.getMovies(currentPage, POPULAR_MOVIE);

    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        int gridColumn = getResources().getInteger(R.integer.grid_column_count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumn));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenu = item.getItemId();

        switch (selectedMenu) {
            case R.id.popular:
                mainPresenter.getMovies(currentPage, POPULAR_MOVIE);
                item.setChecked(true);
                break;
            case R.id.upcoming:
                mainPresenter.getMovies(currentPage, UPCOMING_MOVIE);
                item.setChecked(true);
                break;
            case R.id.now_playing:
                mainPresenter.getNowPlaying(currentPage);
                item.setChecked(true);
        }
        return super.onOptionsItemSelected(item);
    }

}
