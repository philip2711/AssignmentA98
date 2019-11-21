package com.example.assignmenta2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignmenta2.database.AppDatabase;
import com.example.assignmenta2.database.AsyncTaskDelegate;
import com.example.assignmenta2.database.InsertCategoriesAsyncTask;
import com.example.assignmenta2.database.RetrieveCategoriesAsyncTask;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityCategory extends AppCompatActivity{

    private RecyclerView recyclerView;

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_categories_recycler);

        recyclerView = findViewById(R.id.rv2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final CategoriesAdapter categoriesAdapter = new CategoriesAdapter();
        final RequestQueue requestQueue =  Volley.newRequestQueue(this);
        String url = "http://jservice.io/api/categories?count=100";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                Gson gson = new Gson();
                Category[] triviaArray = gson.fromJson(response, Category[].class);
                List<Category> triviaList = Arrays.asList(triviaArray);
                categoriesAdapter.setData(triviaList);
                recyclerView.setAdapter(categoriesAdapter);
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);

        requestQueue.add(stringRequest);
    }
}