package com.example.randomfilm;

import static java.util.Collections.min;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Favourites extends Fragment {
    RecyclerView recyclerView;
    public List<Movie> movies = new ArrayList<>();
    public List<Movie> movies_null = new ArrayList<>();
    public RecycleViewAdapter recycleViewAdapter;
    public int size = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        FilmsDB filmsDB = new FilmsDB(getContext());

        recyclerView = view.findViewById(R.id.RV);

        if (filmsDB.getFilms().isEmpty()) {
            recycleViewAdapter = new RecycleViewAdapter(getContext(), movies_null);
            recyclerView.setAdapter(recycleViewAdapter);
            TextView textView = view.findViewById(R.id.EmptyTV);
            textView.setText("В избранном пока нет фильмов");
        } else {
            TextView textView = view.findViewById(R.id.EmptyTV);
            textView.setText("Избранное");
            if (size < filmsDB.getFilms().size() || size == 0) {
                for (int i = 0; i < filmsDB.getFilms().size(); i++) {
                    movies.add(filmsDB.getFilms().get(i));
                }
                size = filmsDB.getFilms().size();
            }
            recycleViewAdapter = new RecycleViewAdapter(getContext(), movies);
            recyclerView.setAdapter(recycleViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext());
        recyclerView.addItemDecoration(dividerItemDecoration);

        SearchView searchView = view.findViewById(R.id.SV);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        return view;
    }

    private void filterList(String newText) {
        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(movie);
            }
        }
        recycleViewAdapter.setFilteredList(filteredList);
    }
}