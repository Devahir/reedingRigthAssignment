package com.example.foodycookbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodycookbook.fachdataforSearchBar.searchbar;
import com.example.foodycookbook.fachdataforSearchBar.searchbyFirstChar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.foodycookbook.MESSAGE";

    TextView textView;
    SearchView mysearch;
    ArrayAdapter arrayAdapter;
    ListView listView,wishlist;
    RequestQueue mQueue;
    mealObject meaal;
    searchbar searchlist;
    List<String> set;
    ArrayList<mealObject> list;
    List<String> m,d,c,keyword;
    HashMap<String,Character> keyclass;

    com.example.foodycookbook.fachdataforSearchBar.searchbyFirstChar searchbyFirstChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.foodlist);
        wishlist=findViewById(R.id.wisslist);
        list=new ArrayList<>();
        keyclass=new HashMap<>();
        m=new ArrayList<>();
        d=new ArrayList<>();
        c=new ArrayList<>();

        searchbyFirstChar=new searchbyFirstChar();

        mQueue= Volley.newRequestQueue(this);
        ///// CALL OF THOSE FUNCTIONS TO FEATCH DATA
        fatchforsearch();
        fatchCatgories();
        fatcharea();

        mysearch=(SearchView) findViewById(R.id.search_badge);

        mysearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                 set= new ArrayList<>();
                if(newText.length()==1){
                    if(!searchbyFirstChar.containskey(newText)&&!newText.equals(" ")) fatchMealListByL(newText.toLowerCase());;
                }if(newText.length()==0) set.clear();
                if (newText.length() == 3) {
                    set.clear();
                    HashSet<String> arr=searchlist.filter(newText);
                    List<String> arrr=searchbyFirstChar.filterbyname(newText);
                    if(!arr.isEmpty()){
                        set.addAll(arr);
                    }if(!arrr.isEmpty()){
                        set.addAll(arrr);
                    }
                    if(!set.isEmpty()) setsearchAdapter(set);
                    else {
                        set.add("No such Item found");
                        setsearchAdapter(set);
                    }
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent openCookingView=new Intent(MainActivity.this,FullCookingView.class);
                openCookingView.putExtra(EXTRA_MESSAGE,c.get(position));
                startActivity(openCookingView);
            }
        });
        wishlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(keyclass.get(set.get(position))=='i'){
                    //////FATCH DATA BASED ON INGRADIENT
                    fatDATAingrad(set.get(position));
                }if(keyclass.get(set.get(position))=='c'){
                    //////FATCH DATA BASED ON CATEGORIES
                    fatDATAcata(set.get(position));
                }if(keyclass.get(set.get(position))=='a'){
                    //////FATCH DATA BASED ON AREA
                    fatDATA_area(set.get(position));
                }if(keyclass.get(set.get(position))=='n'){
                    fatchp_erticuler_meal(set.get(position));
                }
                
            }
        });
        ////// FATCH RANDOM 10 MEAL AND DISPLAY
        String[] randonArea={"American","British","Canadian","Chinese","Dutch","Egyptian","French","Greek","Indian","Malaysian","Japanese"};
        int i= (int)Math.random()*10;
        String url ="https://www.themealdb.com/api/json/v1/1/filter.php?a="+randonArea[i];
        JsonObjectRequest jsonRequesto = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            for(int i=0;i<meals.length();i++){
                                JSONObject meal=meals.getJSONObject(i);
                                mealObject temp=new mealObject(
                                        meal.getString("idMeal"),
                                        meal.getString("strMeal"),
                                        meal.getString("strMealThumb")
                                );
                                m.add(temp.strmeal);
                                c.add(temp.idmeal);
                                d.add(temp.strmealthumb);

                                setAdapter(m,c,d);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error search");
                    }
                });
        mQueue.add(jsonRequesto);

//        for(mealObject meal:list){
//            m.add(meal.strmeal);
//            c.add(meal.catagories);
//            d.add(meal.area);
//        }


    }

    private void fatchp_erticuler_meal(String s) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=".concat(s);
        JsonObjectRequest jsonRequestoe = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            JSONObject meal=meals.getJSONObject(0);

                            Intent openCookingView=new Intent(MainActivity.this,FullCookingView.class);
                            openCookingView.putExtra(EXTRA_MESSAGE,meal.getString("idMeal"));
                            startActivity(openCookingView);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error search");
                    }
                });
        mQueue.add(jsonRequestoe);
    }

    private void fatDATA_area(String s) {
        m.clear();
        c.clear();
        d.clear();
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?a=".concat(s);
        JsonObjectRequest jsonRequestoe = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            for(int i=0;i<meals.length();i++){
                                JSONObject meal=meals.getJSONObject(i);
                                mealObject temp=new mealObject(
                                        meal.getString("idMeal"),
                                        meal.getString("strMeal"),
                                        meal.getString("strMealThumb")
                                );
                                m.add(temp.strmeal);
                                c.add(temp.idmeal);
                                d.add(temp.strmealthumb);

                                setAdapter(m,c,d);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error search");
                    }
                });
        mQueue.add(jsonRequestoe);
    }

    private void fatDATAcata(String s) {
        m.clear();
        c.clear();
        d.clear();
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=".concat(s);
        JsonObjectRequest jsonRequestoe = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            for(int i=0;i<meals.length();i++){
                                JSONObject meal=meals.getJSONObject(i);
                                mealObject temp=new mealObject(
                                        meal.getString("idMeal"),
                                        meal.getString("strMeal"),
                                        meal.getString("strMealThumb")
                                );
                                m.add(temp.strmeal);
                                c.add(temp.idmeal);
                                d.add(temp.strmealthumb);

                                setAdapter(m,c,d);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error search");
                    }
                });
        mQueue.add(jsonRequestoe);
    }

    private void fatDATAingrad(String cat) {
        m.clear();
        c.clear();
        d.clear();
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=".concat(cat);
        JsonObjectRequest jsonRequestoe = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            for(int i=0;i<meals.length();i++){
                                JSONObject meal=meals.getJSONObject(i);
                                mealObject temp=new mealObject(
                                        meal.getString("idMeal"),
                                        meal.getString("strMeal"),
                                        meal.getString("strMealThumb")
                                );
                                m.add(temp.strmeal);
                                c.add(temp.idmeal);
                                d.add(temp.strmealthumb);

                                setAdapter(m,c,d);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error search");
                    }
                });
        mQueue.add(jsonRequestoe);
    }

    ///////ALL FUNCTIONS AND CLASS OF THE APP
    public void fatchMealListByL(String ch){
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?f="+ch;
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<String> ll=new ArrayList<>();
                            JSONArray meals = response.getJSONArray("meals");
                            for(int i=0;i<meals.length();i++){
                                JSONObject meal=meals.getJSONObject(i);
                                ll.add(meal.getString("strMeal").toLowerCase());
                                keyclass.put(meal.getString("strMeal").toLowerCase(),'n');
                            }
                            searchbyFirstChar.addList(ll,ch);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error");

                    }
                });
        mQueue.add(jsonObjectReq);
    }
    public void fatchforsearch(){
        keyword=new ArrayList<>();
        String url = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ingradientlist = response.getJSONArray("meals");
                            for(int i=0;i<ingradientlist.length();i++){
                                JSONObject l=ingradientlist.getJSONObject(i);
                                keyword.add(l.getString("strIngredient").toLowerCase());
                                keyclass.put(l.getString("strIngredient").toLowerCase(),'i');
                            }
//                            for (String s: list1) ss=ss.concat(s)+" ||";
//
//                            textView.setText(ss);
                            searchlist=new searchbar(keyword);
                            List<String> list1=new ArrayList<>();
                            list1.add("Type Area,Catagory or main ingradient");
                            setsearchAdapter(list1);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error search");
                    }
                });
        mQueue.add(jsonRequest);
    }
    private void setAdapter(List<String> m,List<String> d,List<String> c) {
        myadapeter adapterr=new myadapeter(this,m,d,c);
        listView.setAdapter(adapterr);
    }
    public void setsearchAdapter(List<String> li){
        if(!li.isEmpty()) {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, li);
            wishlist.setAdapter(arrayAdapter);
        }
    }public void fatcharea(){
        keyword=new ArrayList<>();
        String url = "https://www.themealdb.com/api/json/v1/1/list.php?a=list";
        JsonObjectRequest jsonRequesta = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray area = response.getJSONArray("meals");
                            for(int i=0;i<area.length();i++){
                                JSONObject l=area.getJSONObject(i);
                                keyword.add(l.getString("strArea").toLowerCase());
                                keyclass.put(l.getString("strArea").toLowerCase(),'a');
                            }

//                            for (String s: list1) ss=ss.concat(s)+" ||";
//
//                            textView.setText(ss);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error area");
                    }
                });
        mQueue.add(jsonRequesta);
    }public void fatchCatgories(){
        String url = "https://www.themealdb.com/api/json/v1/1/list.php?c=list";
        JsonObjectRequest jsonRequestc = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray catalist = response.getJSONArray("meals");
                            for(int i=0;i<catalist.length();i++){
                                JSONObject l=catalist.getJSONObject(i);
                                keyword.add(l.getString("strCategory").toLowerCase());
                                keyclass.put(l.getString("strCategory").toLowerCase(),'c');
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error cat");
                    }
                });
        mQueue.add(jsonRequestc);
    }

    class myadapeter extends ArrayAdapter<String>{
        Context context;
        List<String> listmeal;
        List<String> categories;
        List<String> ID;
        List<String> lisnklist;

        myadapeter (Context c,List<String> listmeal,List<String> ID,List<String> imgLink){
            super(c,R.layout.tiles, listmeal);
            this.context=c;
            this.listmeal=listmeal;
            this.lisnklist=imgLink;
            this.ID=ID;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.tiles,parent,false);

            TextView strmeal=row.findViewById(R.id.strmeal);
            TextView iD=row.findViewById(R.id.ID);
           // TextView drinkalternative=row.findViewById(R.id.drinkalternative);

            ImageView meal=row.findViewById(R.id.meal);

            strmeal.setText(listmeal.get(position));
            iD.setText(ID.get(position));
            Picasso.with(MainActivity.this).load(lisnklist.get(position)).into(meal);


            return row;
        }
    }
}