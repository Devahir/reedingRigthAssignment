package com.example.foodycookbook.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.security.PrivateKey;

////Define table name
@Entity(tableName = "meal_table")
public class Meal implements Serializable {
    //create id coloumn
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "strMeal")
    private String strMeal;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    @ColumnInfo(name = "strCategory")
    private String strCategory;

    @ColumnInfo(name = "strInstructions")
    private String strInstructions;

    @ColumnInfo(name = "strIngredient")
    private String strIngredient;

}
