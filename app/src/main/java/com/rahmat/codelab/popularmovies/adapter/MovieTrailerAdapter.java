package com.rahmat.codelab.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahmat.codelab.popularmovies.R;
import com.rahmat.codelab.popularmovies.model.TrailerList;

import java.util.List;

/**
 * Created by rahmat on 7/18/2017.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerAdapter> {

    private final List<TrailerList> trailerLists;

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String key);
    }


    public class TrailerAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView trailerTitle;

        public TrailerAdapter(View view){
            super(view);
            trailerTitle = (TextView) view.findViewById(R.id.trailer_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String trailerKey = trailerLists.get(adapterPosition).getKey();
            mOnClickListener.onListItemClick(trailerKey);
        }
    }

    public MovieTrailerAdapter(List<TrailerList> trailerLists, ListItemClickListener mOnClickListener) {
        this.trailerLists = trailerLists;
        this.mOnClickListener = mOnClickListener;

    }

    @Override
    public TrailerAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter holder, int position) {
        TrailerList trailer = trailerLists.get(position);
        holder.trailerTitle.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        if (null == trailerLists) return 0;
        return trailerLists.size();
    }
}
