package com.example.foodycookbook.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Meal.class}, version=1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //create database

    private static RoomDB database;

    //define database name
    private static String DATABASE_NAME="database";

    public synchronized static RoomDB getInstance(Context context){
        //cheak condition
        if(database==null){
            //whane database is null intialise database
            database= Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //return database
        return database;

    }

    //creatin dao
    public abstract MainDao mainDao();
}
