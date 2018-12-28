package com.dev.mooohamed.moviesmvp.UI.Details;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.dev.mooohamed.moviesmvp.Data.DB.MovieDao;
import com.dev.mooohamed.moviesmvp.Data.DB.MovieDatabase;
import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

import java.util.List;

public class DetailsData  implements DetailsContract.FavoriteData

{

    MovieDatabase movieDatabase;
    MovieDao movieDao;
    LiveData<List<Movie>> movies;
    DetailsContract.FavoritePresenter presenter;

    public DetailsData(Context context,DetailsContract.FavoriteView favoriteView){

        movieDatabase = MovieDatabase.getInstance(context);
        movieDao = movieDatabase.movieDao();
        movies = movieDao.getAllMovieList();
        presenter = new DetailsPresenter(context,favoriteView);
    }

    public void addNewMovie(Movie movie){
        new InsertTask(movieDao).execute(movie);
    }

    public void deleteMovie(Movie movie){
        new DeleteTask(movieDao).execute(movie);
    }

    public LiveData<List<Movie>> getAllMovies() {

        return movies;
    }

    @Override
    public void findMovie(int movieId) {
        new FindTask(movieDao).execute(movieId);
    }

    public class InsertTask extends AsyncTask<Movie,Void,Void>{

        private MovieDao movieDao;

        public InsertTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.addMovie(movies[0]);
            return null;
        }
    }

    public class DeleteTask extends AsyncTask<Movie,Void,Void>{

        private MovieDao movieDao;

        public DeleteTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteMovie(movies[0]);
            return null;
        }
    }

    public class FindTask extends AsyncTask<Integer,Void,Boolean>{

        private MovieDao movieDao;

        public FindTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            if (movieDao.findMovie(integers[0]) == null){
                return false;
            }else {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            presenter.isFavorite(aBoolean);
        }
    }
}