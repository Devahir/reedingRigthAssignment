package com.example.foodycookbook.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodycookbook.database.Meal;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //insert query
    @Insert(onConflict = REPLACE)
    void insert(Meal meal);

    @Delete
    void delete(Meal meal);

    @Delete
    void reset(List<Meal> meal);

    /// get all querry
    @Query("SELECT * FROM MEAL_TABLE")
    List<Meal> getAll();
}
