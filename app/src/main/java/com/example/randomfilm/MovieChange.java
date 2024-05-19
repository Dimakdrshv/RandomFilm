package com.example.randomfilm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.se.omapi.Reader;
import android.text.StaticLayout;
import android.text.method.BaseMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MovieChange extends AppCompatActivity {
    private List<Movie> movieChangeList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private List<Movie> Save_List = new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviechange);

        SetText(); loadJson();

        FilmsDB filmsDB = new FilmsDB(this);

        movieAdapter = new MovieAdapter(movieChangeList, this);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(movieAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                movieChangeList.remove(0);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                SetText();
                if (dataObject instanceof Movie) {
                    Movie movie = new Movie(((Movie) dataObject).getTitle(), ((Movie) dataObject).getDescription(),
                            ((Movie) dataObject).getPoster(), ((Movie) dataObject).getUrl_id(),
                            ((Movie) dataObject).getEditor(), ((Movie) dataObject).getDuration(), ((Movie) dataObject).getCountry());
                    if (filmsDB.insertFilms(movie.getTitle(), movie.getPoster(), movie.getUrl_id()))
                        showCorrectToast();
                    else showBlockToast();
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) { SetText(); }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Collections.shuffle(Save_List);
                movieChangeList.addAll(Save_List);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Movie movie = (Movie) movieAdapter.getItem(itemPosition);
                TextView textView = findViewById(R.id.CV);
                textView.setMovementMethod(new ScrollingMovementMethod());
                textView.setText(movie.getDescription());
                textView.setGravity(Gravity.FILL_HORIZONTAL);
            }
        });
        bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Favour) {
                    Intent intent = new Intent(MovieChange.this, MainMenu.class);
                    intent.putExtra("Favourites", "Favourites");
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.Set) {
                    Intent intent = new Intent(MovieChange.this, MainMenu.class);
                    intent.putExtra("Settings", "Settings");
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    public void loadJson() {
        String url = "https://api.myjson.online/v1/records/be45669b-750a-432b-83a4-54f2108e4792";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("data");
                    JSONArray jsonArray = object.getJSONArray("movies");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String poster = jsonObject.getString("poster");
                        String url_id = jsonObject.getString("url");
                        String editor = jsonObject.getString("editors");
                        String duration = jsonObject.getString("duration");
                        String country = jsonObject.getString("countries");
                        movieChangeList.add(new Movie(title, description, poster, url_id, editor, duration, country));
                        Save_List.add(new Movie(title, description, poster, url_id, editor, duration, country));
                    }
                    Collections.shuffle(movieChangeList);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                movieAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    public void SetText() {
        TextView textView = findViewById(R.id.CV);
        textView.setText("Кликни на карточку, чтобы узнать описание фильма");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.moveCursorToVisibleOffset();
    }
    private void showCorrectToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, findViewById(R.id.toast));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setView(layout);
        toast.show();
    }

    private void showBlockToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.blocktoast, findViewById(R.id.blocktoast));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setView(layout);
        toast.show();
    }
}
