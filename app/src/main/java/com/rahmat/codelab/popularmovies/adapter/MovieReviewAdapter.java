package com.rahmat.codelab.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahmat.codelab.popularmovies.R;
import com.rahmat.codelab.popularmovies.model.ReviewList;

import java.util.List;

/**
 * Created by rahmat on 8/1/2017.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewAdapter> {

    private final List<ReviewList> reviewLists;

    public class ReviewAdapter extends RecyclerView.ViewHolder {
        public final TextView author;
        public final TextView content;
        public ReviewAdapter(View view){
            super(view);
            author = (TextView) view.findViewById(R.id.author_review);
            content = (TextView) view.findViewById(R.id.content_review);
        }
    }

    public MovieReviewAdapter(List<ReviewList> reviewLists){
        this.reviewLists = reviewLists;
    }

    @Override
    public ReviewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter(viewItem);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter holder, int position) {
        ReviewList reviewList = reviewLists.get(position);
        holder.author.setText(reviewList.getAuthor());
        holder.content.setText(reviewList.getContent());
    }

    @Override
    public int getItemCount() {
        if(reviewLists == null){
            return 0;
        }
        return reviewLists.size();
    }
}
