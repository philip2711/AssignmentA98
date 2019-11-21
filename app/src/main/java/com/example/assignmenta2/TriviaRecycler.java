package com.example.assignmenta2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriviaRecycler extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trivia_recycler);

        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent1 = getIntent();
        int chosenCategory = intent1.getIntExtra("category", 0);

        final TriviaAdapter triviaAdapter = new TriviaAdapter();
        final RequestQueue requestQueue =  Volley.newRequestQueue(this);
        String url = "http://jservice.io/api/clues?category=" + chosenCategory;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                Gson gson = new Gson();
                Trivia[] triviaArray = gson.fromJson(response, Trivia[].class);
                List<Trivia> triviaList = Arrays.asList(triviaArray);
                triviaAdapter.setData(triviaList);
                recyclerView.setAdapter(triviaAdapter);
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
