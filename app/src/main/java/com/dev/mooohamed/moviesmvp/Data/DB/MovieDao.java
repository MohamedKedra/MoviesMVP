package com.dev.mooohamed.moviesmvp.Data.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void addMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movies_table ORDER BY id DESC")
    LiveData<List<Movie>> getAllMovieList();

    @Query("DELETE FROM movies_table")
    void deleteAllMoviesList();

    @Query("SELECT * FROM movies_table WHERE id = :movieId")
    Movie findMovie(int movieId);
}
