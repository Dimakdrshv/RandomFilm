package com.example.randomfilm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FilmsDB extends SQLiteOpenHelper {
    public static String DBNAME = "Films.db";

    public FilmsDB(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table films(titles TEXT primary key, posters TEXT, urls TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists films");
        db.execSQL("create table films(titles TEXT primary key, posters TEXT, urls TEXT)");

    }
    public Boolean insertFilms (String title, String poster, String url) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("titles", title);
        contentValues.put("posters", poster);
        contentValues.put("urls", url);
        long result = MyDB.insert("films", null, contentValues);
        if (result == -1) return false;
        return true;
    }
    public ArrayList<Movie> getFilms() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from films", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("titles"));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow("posters"));
            String url = cursor.getString(cursor.getColumnIndexOrThrow("urls"));
            movies.add(new Movie(title, null, poster, url, null, null, null));
            cursor.moveToNext();
        }
        return movies;
    }
}
