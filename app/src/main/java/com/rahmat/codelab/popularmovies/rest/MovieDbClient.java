package com.rahmat.codelab.popularmovies.rest;

import com.rahmat.codelab.popularmovies.model.MovieResult;
import com.rahmat.codelab.popularmovies.model.MovieSingle;
import com.rahmat.codelab.popularmovies.model.MovieTrailer;
import com.rahmat.codelab.popularmovies.model.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rahmat on 6/25/2017.
 */

public interface MovieDbClient {
    @GET("movie/{sort}")
    Call<MovieResult> getMovies(@Path ("sort") String order, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieSingle> getMovie(@Path ("id") String order, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<MovieTrailer> getMovieTrailer(@Path ("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<Review> getMovieReview(@Path ("id") String id, @Query("api_key") String apiKey);
}
