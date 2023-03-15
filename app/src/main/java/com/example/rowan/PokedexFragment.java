package com.example.rowan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class PokedexFragment extends Fragment {
    EditText barraDiRicerca;
    DatabaseHandler dbHandler;
    ImageView button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokedex, container, false);

        dbHandler = new DatabaseHandler(getContext());
        barraDiRicerca = root.findViewById(R.id.barra_di_ricerca);
        button = root.findViewById(R.id.pulsanteCerca);

        button.setOnClickListener(view -> {
            String nome = barraDiRicerca.getText().toString();
            Cursor cursor = dbHandler.selectByName(nome);

            if (cursor.moveToFirst()){
                int id = cursor.getInt(1);
                Log.d("MYTAG", "ID TROVATO: " + id);
                Intent i = new Intent(getContext(), DettaglioPokemon.class);
                i.putExtra("id", id);
                startActivity(i);
            }

            else{
                //Pagina not found
                Intent i = new Intent(getContext(), NotFound.class);
                startActivity(i);
            }
        });

        //quando clicco "enter" simulo il click del pulsante
        barraDiRicerca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    button.performClick();
                }
                return false;
            }
        });

        return root;
    }
}