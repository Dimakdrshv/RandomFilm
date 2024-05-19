package com.example.randomfilm;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    Context context;
    List<Movie> movieList;


    public RecycleViewAdapter(Context context, List<Movie> moviesList) {
        this.context = context;
        this.movieList = moviesList;
    }

    public void setFilteredList(List<Movie> filteredList) {
        this.movieList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {

        holder.row_name.setText(movieList.get(position).getTitle());
        Picasso.get().load(movieList.get(position).getPoster()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = movieList.get(holder.getAdapterPosition()).getUrl_id();
                openWebPage(holder.itemView.getContext(), url);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                FilmsDB filmsDB = new FilmsDB(context);
                SQLiteDatabase dbMain = filmsDB.getWritableDatabase();

                String[] Title = new String[]{movieList.get(holder.getAdapterPosition()).getTitle()};

                movieList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();

                dbMain.delete("films", "titles=?", Title);

                showCurrentToast();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView row_name;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_name = itemView.findViewById(R.id.TVRV);
            imageView = itemView.findViewById(R.id.IVRV);
        }
    }
    private void openWebPage(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        context.startActivity(intent);
    }
    private void showCurrentToast() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toastfilms, null);
        TextView textView = layout.findViewById(R.id.FilmTV);
        textView.setText("Фильм был успешно удален" );
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setView(layout);
        toast.show();
    }
}
