package com.rahmat.codelab.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rahmat.codelab.popularmovies.adapter.MovieGridViewAdapter;
import com.rahmat.codelab.popularmovies.data.MovieContract;
import com.rahmat.codelab.popularmovies.model.Favorite;
import com.rahmat.codelab.popularmovies.model.Movie;
import com.rahmat.codelab.popularmovies.model.MovieResult;
import com.rahmat.codelab.popularmovies.model.MovieSingle;
import com.rahmat.codelab.popularmovies.rest.MovieDbClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private GridView gridView;
    private List<Movie> movie;
    private ProgressBar pbar;
    private TextView tvErrorMessage;
    private MovieDbClient movieDbClient;
    private String apiKey;
    private String appTitle;
    private String sortType;
    private MovieGridViewAdapter mGridAdapter;
    private static final int TASK_LOADER_ID = 44;
    List<Favorite> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String baseUrl = "http://api.themoviedb.org/3/";
        apiKey = "PASTE YOUR OWN APIKEY HERE :)";
        gridView = (GridView) findViewById(R.id.movieitem_grid);

        pbar = (ProgressBar) findViewById(R.id.progressbar);
        tvErrorMessage = (TextView) findViewById(R.id.tv_error);
        mGridAdapter = new MovieGridViewAdapter(this, movie);
        sortType="";

        pbar.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        movieDbClient = retrofit.create(MovieDbClient.class);

        if(savedInstanceState != null){
            movie = savedInstanceState.getParcelableArrayList("movie_list");
            //Log.v("isinya", movie.get(1).getTitle());
            mGridAdapter = new MovieGridViewAdapter(this, movie);
            appTitle = savedInstanceState.getString("title");
            setTitle(appTitle);
            gridView.setAdapter(mGridAdapter);
            mGridAdapter.notifyDataSetChanged();
            pbar.setVisibility(View.INVISIBLE);
        }else {
            appTitle = "Popular Movies";
            if (getSupportActionBar() != null) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(appTitle);
            }
            Call<MovieResult> call = movieDbClient.getMovies("popular", apiKey);

            call.enqueue(new Callback<MovieResult>() {
                @Override
                public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                    movie = response.body().getResults();
                    Log.v("Test", movie.toString());
                    mGridAdapter = new MovieGridViewAdapter(getApplicationContext(), movie);
                    gridView.setAdapter(mGridAdapter);
                    showGridView();
                    pbar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    pbar.setVisibility(View.INVISIBLE);
                    showErrorMessage();
                }
            });
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String imageBaseUrl = "http://image.tmdb.org/t/p/w185";
                final String backdropBaseUrl = "http://image.tmdb.org/t/p/w780";
                Intent intent = new Intent(MainActivity.this, MovieDetail.class);
                intent.putExtra("poster_path", imageBaseUrl + movie.get(position).getPosterPath());
                intent.putExtra("vote_average", String.valueOf(movie.get(position).getVoteAverage()));
                intent.putExtra("movie_id", String.valueOf(movie.get(position).getId()));

                intent.putExtra("release", movie.get(position).getReleaseDate());
                intent.putExtra("overview", movie.get(position).getOverview());
                intent.putExtra("title", movie.get(position).getOriginalTitle());
                intent.putExtra("background_path", backdropBaseUrl + movie.get(position).getBackdropPath());
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    }
    private void showErrorMessage(){
        tvErrorMessage.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.INVISIBLE);
    }
    private void showGridView(){
        tvErrorMessage.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_popular){
            loadPopularMovie();
        }else if(id == R.id.action_highest_rate){
            loadHighestRatedMovie();
        }else if(id == R.id.action_favorite){
            loadFavoriteMovie();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadPopularMovie(){
        Call<MovieResult> call = movieDbClient.getMovies("popular", apiKey);
        appTitle ="Popular Movies";
        setTitle(appTitle);

        movie.clear();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                movie = response.body().getResults();
                mGridAdapter = new MovieGridViewAdapter(getApplicationContext(), movie);
                gridView.setAdapter(mGridAdapter);
                showGridView();
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                pbar.setVisibility(View.INVISIBLE);
                showErrorMessage();
            }
        });
    }
    private void loadHighestRatedMovie(){
        Call<MovieResult> call = movieDbClient.getMovies("top_rated", apiKey);
        appTitle = "Highest Rate Movie";
        setTitle(appTitle);
        movie.clear();
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                movie = response.body().getResults();
                mGridAdapter = new MovieGridViewAdapter(getApplicationContext(), movie);
                gridView.setAdapter(mGridAdapter);
                showGridView();
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                pbar.setVisibility(View.INVISIBLE);
                showErrorMessage();
            }
        });

    }

    private void loadFavoriteMovie(){
        appTitle = "Favorite Movie";
        sortType = "favorite";
        setTitle(appTitle);
        /*Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieContract.MovieEntry.COLUMN_TITLE);

        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));

            try{
                Favorite fav = new Favorite();
                fav.setId(id);
                fav.setTitle(title);
                favorites.add(fav);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        cursor.close();*/
        movie.clear();

        for(Favorite favorite : favorites){
            Call<MovieSingle> call = movieDbClient.getMovie(favorite.getId(), apiKey);

            call.enqueue(new Callback<MovieSingle>() {
                @Override
                public void onResponse(@NonNull Call<MovieSingle> call, @NonNull Response<MovieSingle> response) {
                    Movie mov = new Movie();
                    mov.setBackdropPath(response.body().getBackdrop_path());
                    mov.setOverview(response.body().getOverview());
                    mov.setReleaseDate(response.body().getRelease_date());
                    mov.setTitle(response.body().getTitle());
                    mov.setOriginalTitle(response.body().getOriginal_title());
                    mov.setVoteAverage(response.body().getVote_average());
                    mov.setPosterPath(response.body().getPoster_path());
                    mov.setId(response.body().getId());
                    movie.add(mov);
                    showGridView();
                    pbar.setVisibility(View.INVISIBLE);
                    mGridAdapter = new MovieGridViewAdapter(getApplicationContext(), movie);
                    gridView.setAdapter(mGridAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<MovieSingle> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    pbar.setVisibility(View.INVISIBLE);
                    Log.v("gagal", "berhasil");
                    showErrorMessage();
                }
            });
        }

        mGridAdapter.notifyDataSetChanged();
        //Log.v("berhasil", " "+movie.get(0).getTitle());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Movie> movies = (ArrayList<Movie>) movie;
        outState.putParcelableArrayList("movie_list", movies);
        outState.putString("title", appTitle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    private void setTitle(String title){
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if(mMovieData != null){
                    deliverResult(mMovieData);
                }else{
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry.COLUMN_TITLE);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while(data.moveToNext()){
            String id = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
            String title = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));

            try{
                Favorite fav = new Favorite();
                fav.setId(id);
                fav.setTitle(title);
                favorites.add(fav);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mGridAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mGridAdapter.swapCursor(null);
    }
}
