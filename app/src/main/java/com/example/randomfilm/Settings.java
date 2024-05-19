package com.example.randomfilm;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Settings extends Fragment {

    private Integer progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        FilmsDB filmsDB = new FilmsDB(getContext());
        SQLiteDatabase dbMain = filmsDB.getWritableDatabase();

        ProgressBar progressBar = view.findViewById(R.id.PG);
        progressBar.setVisibility(View.VISIBLE);
        progress = (int) ((double) (filmsDB.getFilms().size()) / 16.0 * 100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progress,false);

            }
        }).start();

        TextView textView = view.findViewById(R.id.TVPG);
        textView.setText(progress + "%");

        Button button = view.findViewById(R.id.DeleteButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!filmsDB.getFilms().isEmpty()) {
                    filmsDB.onUpgrade(dbMain, 1, 1);
                    progressBar.setProgress(0);
                    textView.setText(0 + "%");
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toastbase, view.findViewById(R.id.toastBase));
                    Toast toast = new Toast(view.getContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setView(layout);
                    toast.show();
                }
                else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toastbaseblock, view.findViewById(R.id.toastBaseBlock));
                    Toast toast = new Toast(view.getContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        TextView textView1 = view.findViewById(R.id.SiteTV);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kinopoisk.ru/"));
                view.getContext().startActivity(intent);
            }
        });

        Button button1 = view.findViewById(R.id.Send);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.Comment);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "dimakudrashov13@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Отзыв по приложению.");
                intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }
}