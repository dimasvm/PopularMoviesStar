package com.example.dimas.popular_movies_star.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.ui.activity.DetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by       : dimas on 12/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MovieAdapter
        extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMoviesList;
    private Context mContext;
    private MovieClickListener movieClickListener;


    public MovieAdapter(Context context, List<Movie> movies) {
        mMoviesList = new ArrayList<>();
        addAll(movies);
        mContext = context;
    }

    public void setMovieClickListener(MovieClickListener listener) {
        this.movieClickListener = listener;
    }

    public void addAll(List<Movie> movies) {
        mMoviesList.clear();
        mMoviesList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_grid_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        holder.bindData(movie);
        setMovieClickListener(new MovieClickListener() {
            @Override
            public void onMovieClicked(Movie movie) {
                Intent intent = new Intent(mContext, DetailMovieActivity.class);
                intent.putExtra("movie", movie);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageMovie;
        public TextView titleMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);

            imageMovie = itemView.findViewById(R.id.imageMovie);
            titleMovie = itemView.findViewById(R.id.titleMovie);

            itemView.setOnClickListener(this);

        }

        public void bindData(Movie movie) {
            Picasso.with(mContext)
                    .load(movie.getPoster_path())
                    .placeholder(android.R.drawable.stat_sys_warning)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageMovie);
            titleMovie.setText(movie.getTitle());
        }

        @Override
        public void onClick(View view) {
            // TODO: 29/01/18 Go To Detail Activity
            movieClickListener.onMovieClicked(mMoviesList.get(getAdapterPosition()));
        }
    }

}
