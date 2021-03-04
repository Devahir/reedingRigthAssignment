package com.example.foodycookbook.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.foodycookbook.R;

import java.util.ArrayList;
import java.util.List;

public class LocalStoredMeal extends AppCompatActivity {

    List<Meal> mealList=new ArrayList<Meal>();

    LinearLayoutManager linearLayoutManager;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_stored_meal);
    }
}