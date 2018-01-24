package com.example.dimas.popular_movies_star.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by       : dimas on 12/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMoviesList;
    private Context mContext;


    public MovieAdapter(Context context, List<Movie> movies) {
        mMoviesList = new ArrayList<>();
        mMoviesList = movies;
        mContext = context;
    }


    public void addAll(List<Movie> movies) {
        mMoviesList.addAll(movies);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_grid_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.bindData(mMoviesList.get(position));
    }


    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageMovie;
        public TextView titleMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);

            imageMovie = (ImageView) itemView.findViewById(R.id.imageMovie);
            titleMovie = (TextView) itemView.findViewById(R.id.titleMovie);
        }

        public void bindData(Movie movie) {
            Picasso.with(mContext)
                    .load(movie.getPoster_path())
                    .placeholder(android.R.drawable.stat_sys_warning)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageMovie);
            titleMovie.setText(movie.getTitle());
        }
    }

}
