package com.rahmat.codelab.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rahmat.codelab.popularmovies.R;
import com.rahmat.codelab.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahmat on 6/24/2017.
 */

public class MovieGridViewAdapter extends BaseAdapter {

    private final Context context;
    private List<Movie> urls = new ArrayList<>();

    private Cursor mCursor;

    public MovieGridViewAdapter(Context context, List<Movie> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        if (urls.size() == 0){
            return 0;
        }
        return urls.size();
    }

    @Override
    public Movie getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View gridView = view;
        if (gridView == null) {
            gridView = LayoutInflater.from(context)
                    .inflate(R.layout.item_poster, viewGroup, false);
        }
        ImageView posterImageView = (ImageView) gridView.findViewById(R.id.posterImageView);

        // Get the image URL for the current position.
        Movie movie = getItem(position);

        //needed to append the image url
        String imageBaseUrl = "http://image.tmdb.org/t/p/w185";

        Picasso.with(context) //
                .load(imageBaseUrl+movie.getPosterPath()) //
                .placeholder(R.drawable.ic_hourglass_empty_black_24dp) //
                .error(R.drawable.ic_error_black_24dp) //
                .fit() //
                .tag(context) //
                .into(posterImageView);

        Log.v("jalan ji", " "+imageBaseUrl+movie.getPosterPath());
        return gridView;
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
}
