package com.example.rowan;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
        pulsanteCercaFiltrato = root.findViewById(R.id.pulsante_cerca_filtrato);
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

        pulsanteCercaFiltrato.setOnClickListener(view -> {
            displayData(root);
        });


        return root;
    }

    public void displayData(View root) {
        LinearLayout contenitore = root.findViewById(R.id.contenitore_righe_pkmn);  //contiene tutte le view delle righe dei pkmn
        contenitore.removeAllViews();   //è necessario rimuovere tutte le view per poter aggiornare il container per una nuova interrogazione
        Colorazione colorazione = new Colorazione();

        Cursor cursor = dbHandler.selectByFilter(tipo1Selezionato, tipo2Selezionato);
        if (cursor.getCount() == 0) {
            Log.d("MYTAG", "NON CI SONO PKMN");
            Toast.makeText(getContext(), "Non ci sono pokèmon con i requisiti richiesti", Toast.LENGTH_LONG).show();
        }

        else {
            while (cursor.moveToNext()) {
                //inizializzo tutti i layout e le view che mi servono
                LinearLayout layoutRigaPkmn = new LinearLayout(getContext());    //è contenuto in contenitore
                ImageView artwork = new ImageView(getContext());    //è contenuto in layoutRigaPkmn
                LinearLayout layoutRigaColorata = new LinearLayout(getContext());    // è contenuto in layoutRigaPkmn
                TextView id = new TextView(getContext());  //è contenuto in layoutRigaColorata
                TextView nomePkmn = new TextView(getContext());  //è contenuto in layoutRigaColorata
                LinearLayout layoutTipi = new LinearLayout(getContext());
                TextView tipo1 = new TextView(getContext()); //è contenuto in layoutTipi
                TextView tipo2 = new TextView(getContext()); //è contenuto in layoutTipi

                //imposto ed annido le view
                layoutRigaPkmn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutRigaPkmn.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParamsRigaPkmn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsRigaPkmn.setMargins(0, 0, 0, 20);
                layoutRigaPkmn.setLayoutParams(layoutParamsRigaPkmn);
                layoutRigaPkmn.setClickable(true);

                int ida = cursor.getInt(0);
                //al click di una riga parte l'activity del dettaglio pokemon; Passo l'id così posso fare la select su quel Pokèmon
                layoutRigaPkmn.setOnClickListener(view -> {
                    Intent i = new Intent(getContext(), DettaglioPokemon.class);
                    i.putExtra("id", ida);
                    Log.d("MYTAG", "Click su #" + ida);
                    startActivity(i);
                });


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
                layoutParams.gravity = Gravity.CENTER;
                artwork.setLayoutParams(layoutParams);

                layoutRigaColorata.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
                layoutRigaColorata.setOrientation(LinearLayout.HORIZONTAL);
                layoutRigaColorata.setPadding(7, 10, 0, 0);
                layoutRigaColorata.setBackgroundColor(Color.rgb(111, 129, 167));

                layoutTipi.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutTipi.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParamsTipi = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsTipi.setMargins(80, 5, 0, 0);
                layoutTipi.setLayoutParams(layoutParamsTipi);

                contenitore.addView(layoutRigaPkmn);
                layoutRigaPkmn.addView(artwork);
                layoutRigaPkmn.addView(layoutRigaColorata);
                layoutRigaColorata.addView(id);
                layoutRigaColorata.addView(nomePkmn);
                layoutRigaColorata.addView(layoutTipi);
                layoutTipi.addView(tipo1);
                layoutTipi.addView(tipo2);

                //retrieve dell'artwork
                byte[] img = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                artwork.setImageBitmap(bitmap);
                //fine retrieve dell'artwork

                //retrieve dell'id
                id.setText("#" + cursor.getInt(0));
                id.setTextColor(Color.WHITE);
                id.setGravity(Gravity.START);
                id.setTextSize(23);
                Typeface font = getResources().getFont(R.font.ubuntu_medium);
                id.setTypeface(font);
                id.setPadding(0, 18, 0, 0);
                id.setWidth(150);

                //retrieve del nome
                nomePkmn.setText(cursor.getString(1));
                nomePkmn.setTextColor(Color.WHITE);
                nomePkmn.setGravity(Gravity.START);
                nomePkmn.setTextSize(23);
                nomePkmn.setPadding(40, 18, 0, 0);
                nomePkmn.setWidth(450);
                nomePkmn.setTypeface(font);

                tipo1.setText(cursor.getString(3));
                tipo1.setTextColor(Color.WHITE);
                tipo1.setTextSize(15);
                tipo1.setTypeface(font);
                colorazione.cambiaColoreTipo(tipo1);
                tipo1.setPadding(10, 5, 10, 5);
                tipo1.setGravity(Gravity.START);
                tipo1.setWidth(350);

                tipo2.setText(cursor.getString(4));
                tipo2.setTextColor(Color.WHITE);
                tipo2.setTextSize(15);
                tipo2.setTypeface(font);
                colorazione.cambiaColoreTipo(tipo2);
                tipo2.setPadding(10, 5, 10, 5);
                tipo2.setGravity(Gravity.START);
                tipo2.setWidth(350);
            }   //fine while
        }
    }
}