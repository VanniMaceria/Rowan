package com.example.rowan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import com.example.rowan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DatabaseHandler(this);
        dbHandler.popolaDb();   //questo mi serve per quando aggiungo i pokemon, poi alla fine lo toglierò

        /*
          Con SharedPreferences posso specificare alcune informazioni, in questo caso decido di usare il metodo
          popolaDb() solo durante il primo avvio dell'applicazione, cioè post installazione
        */
        SharedPreferences ratePrefs = getSharedPreferences("First Update", 0);
        if (!ratePrefs.getBoolean("FrstTime", false)) {
            // Do update you want here
            dbHandler.popolaDb();   //popolo il db

            SharedPreferences.Editor edit = ratePrefs.edit();
            edit.putBoolean("FrstTime", true);
            edit.commit();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());    //all'avvio dell'applicazione ci troviamo in HomeFragment

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.pokedex:
                    replaceFragment(new PokedexFragment());
                    break;
                case R.id.info:
                    replaceFragment(new InfoFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy(){
        dbHandler.close();
        super.onDestroy();
    }
}