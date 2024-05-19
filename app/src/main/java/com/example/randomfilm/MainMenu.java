package com.example.randomfilm;

import static androidx.databinding.DataBindingUtil.findBinding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.randomfilm.databinding.MainmenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;


public class MainMenu extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Favourites favourites = new Favourites();
    Settings settings = new Settings();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Button button = findViewById(R.id.buttonTutorial);

        Bundle bundle = getIntent().getExtras();

        String intent_name_favourites; String intent_name_favourites1;

        if (bundle != null) {
            intent_name_favourites = (String) bundle.get("Favourites");
            intent_name_favourites1 = (String) bundle.get("Settings");
        }
        else {
            intent_name_favourites = null;
            intent_name_favourites1 = null;
        }
        if (intent_name_favourites != null && intent_name_favourites.equals("Favourites")) {
            button.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, favourites).commit();
        }
        else if (intent_name_favourites1 != null && intent_name_favourites1.equals("Settings")) {
            button.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, settings).commit();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Tutorial.class));
            }
        });

        bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Movie) {
                    button.setVisibility(View.GONE);
                    Intent intent = new Intent(MainMenu.this, MovieChange.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.Favour) {
                    button.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, favourites).commit();
                    return true;
                }
                else if (item.getItemId() == R.id.Set) {
                    button.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settings).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
