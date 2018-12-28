package com.dev.mooohamed.moviesmvp.Data.DB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

@Database(entities = {Movie.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;
    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context,MovieDatabase.class,"movie_database")
                    .addCallback(callback).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new OpenDBTask(instance).execute();
        }
    };

    private static class OpenDBTask extends AsyncTask<Void,Void,Void>{

        private MovieDao movieDao;

        public OpenDBTask(MovieDatabase movieDatabase){
            this.movieDao = movieDatabase.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
