package com.dev.mooohamed.moviesmvp.Services;

import com.dev.mooohamed.moviesmvp.Data.Models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String key);

    @GET("top_rated")
    Call<MovieResponse> getRatedMovies(@Query("api_key") String key);

}
