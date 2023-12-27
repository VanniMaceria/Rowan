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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class PokedexFragment extends Fragment {
    private EditText barraDiRicerca;
    private DatabaseHandler dbHandler;
    private ImageView pulsanteCerca;
    private ImageView pulsanteCercaFiltrato;
    private Spinner tipo1;
    private Spinner tipo2;
    private String tipo1Selezionato;
    private String tipo2Selezionato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokedex, container, false);

        dbHandler = new DatabaseHandler(getContext());
        barraDiRicerca = root.findViewById(R.id.barra_di_ricerca);
        pulsanteCerca = root.findViewById(R.id.pulsanteCerca);
        tipo1 = root.findViewById(R.id.tipo1);
        tipo2 = root.findViewById(R.id.tipo2);

        //ricerca pokèmon per nome
        pulsanteCerca.setOnClickListener(view -> {
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
        });//

        //quando clicco "enter" simulo il click del pulsante
        barraDiRicerca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    pulsanteCerca.performClick();
                }
                return false;
            }
        });
        //


        //seleziono la voce selezionata nello spinner
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //prendo l'elemento selezionato
                String selectedOption = parentView.getItemAtPosition(position).toString();

                //assegno il valore alle variabili a seconda dello spinner
                if (parentView == tipo1) {
                    tipo1Selezionato = selectedOption;
                } else if (parentView == tipo2) {
                    tipo2Selezionato = selectedOption;
                }

                Log.d("Spinner", "Spinner 1: " + tipo1Selezionato);
                Log.d("Spinner", "Spinner 2: " + tipo2Selezionato);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Azioni da eseguire quando nulla è selezionato
            }
        };

        //definisco i listener per i due spinner
        tipo1.setOnItemSelectedListener(onItemSelectedListener);
        tipo2.setOnItemSelectedListener(onItemSelectedListener);

        //definisco altri filtri...

        //cerco in base ai filtri applicati
        pulsanteCercaFiltrato.setOnClickListener(view -> {
            //esegui la query
            //lancia un'activity che mostra i risultati della select
        });


        return root;
    }
}