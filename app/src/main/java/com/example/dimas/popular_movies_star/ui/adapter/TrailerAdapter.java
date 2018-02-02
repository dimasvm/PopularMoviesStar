package com.example.dimas.popular_movies_star.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by       : dimas on 02/02/18.
 * Email            : araymaulana66@gmail.com
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    private List<Trailer> trailerList;

    private OnTrailerClickListener onTrailerClickListener;

    public TrailerAdapter(Context context, List<Trailer> trailers){
        this.mContext = context;
        this.trailerList = trailers;
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_trailer_horizontal, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer trailerData = trailerList.get(position);

        holder.bindData(trailerData);
    }

    @Override
    public int getItemCount() {
        return trailerList != null ? trailerList.size() : 0;
    }

    public Trailer getItem(int position){
        if (trailerList == null || position < 0 || position > trailerList.size()){
            return null;
        }
        return trailerList.get(position);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageTrailer;
        private TextView titleTrailer;

        TrailerViewHolder(View itemView) {
            super(itemView);

            imageTrailer = itemView.findViewById(R.id.imageTrailer);
            titleTrailer = itemView.findViewById(R.id.titleTrailer);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("PrivateResource")
        void bindData(Trailer trailerData) {
            Picasso.with(mContext)
                    .load(trailerData.getYoutubeThumbnail(trailerData.getKey()))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(R.color.accent_material_light)))
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageTrailer);
            titleTrailer.setText(trailerData.getName());

        }


        @Override
        public void onClick(View view) {
            onTrailerClickListener.onTrailerClicked(getAdapterPosition());
        }
    }

    public interface OnTrailerClickListener {
        void onTrailerClicked(int position);
    }
}
