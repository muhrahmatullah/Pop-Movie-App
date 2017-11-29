package com.rahmat.codelab.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahmat.codelab.popularmovies.adapter.MovieReviewAdapter;
import com.rahmat.codelab.popularmovies.adapter.MovieTrailerAdapter;
import com.rahmat.codelab.popularmovies.data.MovieContract;
import com.rahmat.codelab.popularmovies.model.MovieTrailer;
import com.rahmat.codelab.popularmovies.model.Review;
import com.rahmat.codelab.popularmovies.model.ReviewList;
import com.rahmat.codelab.popularmovies.model.TrailerList;
import com.rahmat.codelab.popularmovies.rest.MovieDbClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rahmat.codelab.popularmovies.R.id.fab;

public class MovieDetail extends AppCompatActivity implements MovieTrailerAdapter.ListItemClickListener{

    private String apiKey;
    private List<TrailerList> trailerLists = new ArrayList<>();
    private List<ReviewList> reviewLists = new ArrayList<>();
    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;
    private MovieTrailerAdapter movieTrailerAdapter;
    private MovieReviewAdapter movieReviewAdapter;
    private FloatingActionButton fabLove;

    private String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent receivedIntent = getIntent();
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle(receivedIntent.getStringExtra("title"));
        }

        TextView tvTitle = (TextView) findViewById(R.id.txt_title);
        TextView tvRelease = (TextView) findViewById(R.id.txt_release);
        TextView tvOverview = (TextView) findViewById(R.id.txt_overview);
        TextView tvRate = (TextView) findViewById(R.id.txt_rate);
        ImageView backdrop = (ImageView) findViewById(R.id.img_background);

        ImageView detailImage = (ImageView) findViewById(R.id.detail_image);

        fabLove = (FloatingActionButton) findViewById(fab);

        movieTitle = receivedIntent.getStringExtra("title");

        //String release_date = receivedIntent.getStringExtra("release");
        tvTitle.setText(movieTitle);
        tvRelease.setText(receivedIntent.getStringExtra("release"));
        tvOverview.setText(receivedIntent.getStringExtra("overview"));
        tvRate.setText(receivedIntent.getStringExtra("vote_average"));

        final String id = receivedIntent.getStringExtra("movie_id");
        Log.v("trailer", id);


        Picasso.with(getApplicationContext()) //
                .load(receivedIntent.getStringExtra("poster_path")) //
                .fit() //
                .into(detailImage);
        Picasso.with(getApplicationContext()) //
                .load(receivedIntent.getStringExtra("background_path")) //
                .fit() //
                .into(backdrop);

        String baseUrl = "http://api.themoviedb.org/3/";
        apiKey = BuildConfig.THE_MOVIE_DB_API_KEY;

        trailerRecyclerView = (RecyclerView) findViewById(R.id.trailer_recylerview);
        movieTrailerAdapter = new MovieTrailerAdapter(trailerLists, this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(trailerRecyclerView.getContext(),
                layoutManager.getOrientation());
        trailerRecyclerView.addItemDecoration(mDividerItemDecoration);
        trailerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_recylerview);
        movieReviewAdapter = new MovieReviewAdapter(reviewLists);
        reviewRecyclerView.setLayoutManager(mLayoutManager);
        reviewRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(reviewRecyclerView.getContext(),
                layoutManager.getOrientation());
        reviewRecyclerView.addItemDecoration(dividerItemDecoration);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        MovieDbClient movieDbClient = retrofit.create(MovieDbClient.class);
        Call<MovieTrailer> call = movieDbClient.getMovieTrailer(id, apiKey);

        call.enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                trailerLists = response.body().getResults();
                trailerRecyclerView.setAdapter(new MovieTrailerAdapter(trailerLists, MovieDetail.this));
            }

            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {
                Log.v("Trailer jie", "Gagal bos");
            }
        });

        Call<Review> callReview = movieDbClient.getMovieReview(id, apiKey);

        callReview.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                reviewLists = response.body().getResults();
                reviewRecyclerView.setAdapter(new MovieReviewAdapter(reviewLists));
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.v("Review jie", "Gagal bos");
            }
        });


        if (isRecordExists(id)) {
            if (fabLove != null) {
                fabLove.setImageResource(R.drawable.ic_favorite_white_24dp);
            }

        }

        if (fabLove != null) {
            fabLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isRecordExists(id)) {
                        fabLove.setImageResource(R.drawable.ic_favorite_white_24dp);

                        // Insert new task data via a ContentResolver
                        // Create new empty ContentValues object
                        ContentValues contentValues = new ContentValues();
                        // Put the task description and selected mPriority into the ContentValues
                        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
                        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieTitle);
                        // Insert the content values via a ContentResolver
                        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                        Snackbar.make(view, "This movie has been add to your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(id).build();

                        getContentResolver().delete(uri, null, null);
                        fabLove.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        Snackbar.make(view, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

    }

    @Override
    public void onListItemClick(String key) {
        String baseUrl = "https://www.youtube.com/watch?v=";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl + key));
        startActivity(intent);
    }
    private boolean isRecordExists(String id) {
        String selection = " movie_id=?";
        String[] selectionArgs = { id };
        String[] projection = {MovieContract.MovieEntry.COLUMN_MOVIE_ID};

        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, projection , selection, selectionArgs, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        Log.v("isi", Boolean.toString(exists));
        return exists;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MovieDetail.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
