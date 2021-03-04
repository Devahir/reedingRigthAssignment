package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FullCookingView extends AppCompatActivity {

    RequestQueue mQueue;
    ImageView imageView;
    TextView Area,Cata,Instuc;
    ListView ingeradient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_cooking_view);
        ingeradient=findViewById(R.id.ingradient);

        mQueue= Volley.newRequestQueue(this);

        imageView=findViewById(R.id.finalmeal);
        Area=findViewById(R.id.area);
        Cata=findViewById(R.id.catagories);
        Instuc=findViewById(R.id.Instructions);

        Intent GETintent = getIntent();
        String message = GETintent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=".concat(message);
        JsonObjectRequest jsonRequesto = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals = response.getJSONArray("meals");
                            JSONObject ob=meals.getJSONObject(0);

                            ///////SET IMAGE OF SELECTED MEAL
                            String url=ob.getString("strMealThumb");
                            Picasso.with(FullCookingView.this).load(url).into(imageView);

                            /////SET INFORMATION ABOUT THAT MEAL

                            String areaS="This is a "+ob.getString("strArea")+" "+ob.getString("strMeal");
                            String CatagoS="  "+ob.getString("strCategory");
                            String Instruc=ob.getString("strInstructions");
                            Area.setText(areaS);
                            Cata.setText(CatagoS);
//
                            Instuc.setText(Instruc);
                            List<String> list=new ArrayList<>();

                            for(int i=1;i<21;i++){
                                String qn=ob.getString("strIngredient"+String.valueOf(i));
                                String qu=ob.getString("strMeasure"+String.valueOf(i));
                                qn=qn+": "+qu;
                                list.add(qn);
                            }

                //            list.add("kjcaiab");

                            setAdapter(list);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        mQueue.add(jsonRequesto);

    }
    private void setAdapter(List<String> l) {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l);
        ingeradient.setAdapter(arrayAdapter);
    }
}