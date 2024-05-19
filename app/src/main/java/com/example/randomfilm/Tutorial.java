package com.example.randomfilm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Tutorial extends AppCompatActivity {
    List<TutorialObject> tutorialObjects = new ArrayList<>();

    TutorialAdapter tutorialAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        String[] text = new String[]{"Для перемещения по обучению сдвигайте карточки в левую или в правую сторону!", "Для навигации по приложению используйте панель, которая расположена снизу!",
        "Во вкладке \"Выбор фильма\" Вы можете выбрать фильмы. Сдвинув карточку влево, фильм сохранится в избранное. Кликнув на карточку с фильмом, на верхнем экране высветится его описание!",
        "Во вкладке \"Избранное\" Вы сможете найти фильмы из базы данных, которую создали. Нажав на нужный фильм, вы перейдете на страницу в интернете данного фильма. Если зажмете кнопку, то фильм удалится из избранного!",
        "Во вкладке \"Настройки\" Вы сможете полностью очистить избранное, а также написать отзыв и связаться с разработчиком напрямую!"};
        Drawable[] drawables = new Drawable[]{getResources().getDrawable(R.drawable.tutorial1), getResources().getDrawable(R.drawable.tutorial2),
        getResources().getDrawable(R.drawable.tutorial3), getResources().getDrawable(R.drawable.tutorial4), getResources().getDrawable(R.drawable.tutorial5)};

        SwipeFlingAdapterView flingAdapterView = (SwipeFlingAdapterView) findViewById(R.id.cardframe);

        for (int i = 0; i < text.length; i++) {
            tutorialObjects.add(new TutorialObject(text[i], drawables[i]));
        }
        tutorialAdapter = new TutorialAdapter(getApplicationContext(), tutorialObjects);

        flingAdapterView.setAdapter(tutorialAdapter);
        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                tutorialObjects.remove(0);
                tutorialAdapter.notifyDataSetChanged();
                if (tutorialObjects.isEmpty()) {
                    startActivity(new Intent(Tutorial.this, MainMenu.class));
                    showCurrentToast();
                }
            }

            @Override
            public void onLeftCardExit(Object o) {

            }

            @Override
            public void onRightCardExit(Object o) {

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
            }

            @Override
            public void onScroll(float v) {

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Favour) {
                    Intent intent = new Intent(Tutorial.this, MainMenu.class);
                    intent.putExtra("Favourites", "Favourites");
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.Set) {
                    Intent intent = new Intent(Tutorial.this, MainMenu.class);
                    intent.putExtra("Settings", "Settings");
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.Movie) {
                    startActivity(new Intent(Tutorial.this, MovieChange.class));
                }
                return false;
            }
        });
    }

    private void showCurrentToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.tutorialtoast, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setView(layout);
        toast.show();
    }
}
