package com.example.randomfilm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends BaseAdapter {

    private List<Movie> movies;
    private Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_movie_selection, parent, false);
        }

        Movie movie = (Movie) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.IV);
        TextView titleTextView = convertView.findViewById(R.id.helloText);
        TextView editor = convertView.findViewById(R.id.editor);
        TextView duration = convertView.findViewById(R.id.duration);
        TextView country = convertView.findViewById(R.id.country);

        Picasso.get().load(movie.getPoster()).into(imageView);
        titleTextView.setText(movie.getTitle());
        editor.setText(movie.getEditor());
        duration.setText(movie.getDuration());
        country.setText(movie.getCountry());

        return convertView;
    }
}