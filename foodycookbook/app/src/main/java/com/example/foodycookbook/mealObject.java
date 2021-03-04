package com.example.foodycookbook;
import java.util.ArrayList;


public class mealObject {
    String idmeal;
    String strmeal;
    String strDrinkalternate;
    String catagories;
    String area;
    String strInstruction;
    String strmealthumb;
    String tags;
    String youtubLink;
    ArrayList<String> Ingradient;
    ArrayList<String> amount;
    String strsorce;
    String dateofmodify;

    public mealObject(String idmeal, String strmeal, String strDrinkalternate, String catagories, String area, String strInstruction, String strmealthumb, String tags, String youtubLink, ArrayList<String> ingradient,ArrayList<String> amount, String strsorce, String dateofmodify) {
        this.idmeal = idmeal;
        this.strmeal = strmeal;
        this.strDrinkalternate = strDrinkalternate;
        this.catagories = catagories;
        this.area = area;
        this.strInstruction = strInstruction;
        this.strmealthumb = strmealthumb;
        this.tags = tags;
        this.youtubLink = youtubLink;
        Ingradient = ingradient;
        this.amount=amount;
        this.strsorce = strsorce;
        this.dateofmodify = dateofmodify;
    }

    public mealObject(String idmeal, String strmeal, String area, String catagories, String strmealthumb) {
        this.idmeal = idmeal;
        this.strmeal = strmeal;
        this.area= area;
        this.catagories = catagories;
        this.strmealthumb = strmealthumb;
    }

    public mealObject(String idmeal, String strmeal, String strmealthumb) {
        this.idmeal = idmeal;
        this.strmeal = strmeal;
        this.strmealthumb = strmealthumb;
    }
}
