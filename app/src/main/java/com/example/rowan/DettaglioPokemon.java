package com.example.rowan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class DettaglioPokemon extends Activity {
    int id;
    DatabaseHandler dbHandler;
    Cursor cursor;
    ImageView artwork;
    TextView idNome;
    TextView tipo1;
    TextView tipo2;
    TextView altezza;
    TextView peso;
    TextView abilita;
    TextView abilitaN;
    TextView categoria;
    TextView descrizione;
    boolean flag = false;

    /*
    * In questa classe aggiungi dei pulsanti per switchare la forma regionale, descrizione,
    * forma regionale shiny i tipi e le abilità
    * Aggiungi delle freccette che ti fanno andare avanti e dietro
    * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_pokemon_layout);

        dbHandler = new DatabaseHandler(this);
        Intent i = getIntent();
        id = 0;
        id = i.getIntExtra("id", 0);
        Log.d("MYTAG", "DettaglioPokemon dice: Ho ricevuto un id = " + id);
        String idString = String.valueOf(id);

        cursor = dbHandler.selectById(idString);

        if(cursor.getCount() == 0) {
            Log.d("MYTAG", "NON CI SONO PKMN");
            //metti che porta ad una pagina di errore tipo 404
        }

        if(cursor.moveToFirst()){
            Log.d("MYTAG", cursor.getString(1));
            Log.d("MYTAG", cursor.getString(2));
        }

        //rimpiazzo gli elementi dinamicamente con il pokèmon ottenuto
        artwork = new ImageView(this);
        artwork = findViewById(R.id.artworkBig);
        byte[] img = cursor.getBlob(11); //retrieve dell'artwork
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        artwork.setImageBitmap(bitmap); //imposto l'artwork

        idNome = new TextView(this);
        idNome = findViewById(R.id.nome);
        idNome.setText("#" + id + "    " + cursor.getString(2));

        tipo1 = new TextView(this);
        tipo1 = findViewById(R.id.dettaglioTipo1);
        tipo1.setText(cursor.getString(3));
        cambiaColoreTipo(tipo1);

        tipo2 = new TextView(this);
        tipo2 = findViewById(R.id.dettaglioTipo2);
        tipo2.setText(cursor.getString(4));
        cambiaColoreTipo(tipo2);

        if(tipo2.equals("null")){
            tipo2.setText(null);
        }

        altezza = new TextView(this);
        altezza = findViewById(R.id.dettaglioAltezza);
        altezza.setText(cursor.getString(5) + " m");

        peso = new TextView(this);
        peso = findViewById(R.id.dettaglioPeso);
        peso.setText(cursor.getString(6) + " kg");

        abilita = new TextView(this);
        abilita = findViewById(R.id.dettaglioAbilita);
        abilita.setText(cursor.getString(7));

        abilitaN = new TextView(this);
        abilitaN = findViewById(R.id.dettaglioAbilitaNascosta);
        abilitaN.setText(cursor.getString(8));

        categoria = new TextView(this);
        categoria = findViewById(R.id.dettaglioCategoria);
        categoria.setText(cursor.getString(9));

        descrizione = new TextView(this);
        descrizione = findViewById(R.id.dettaglioDescrizione);
        descrizione.setText(cursor.getString(10));

    }

    public void onShinyClick(View v){
        if(flag){
            byte[] imgShiny = cursor.getBlob(11); //retrieve dell'artwork
            Bitmap bitmapShiny = BitmapFactory.decodeByteArray(imgShiny, 0, imgShiny.length);
            artwork.setImageBitmap(bitmapShiny); //imposto l'artwork
            flag = false;
        }

        else {
            byte[] imgShiny = cursor.getBlob(12); //retrieve dell'artwork
            Bitmap bitmapShiny = BitmapFactory.decodeByteArray(imgShiny, 0, imgShiny.length);
            artwork.setImageBitmap(bitmapShiny); //imposto l'artwork shiny
            flag = true;
        }
    }

    public void OnBackClick(View v){
        Intent i = new Intent(this, DettaglioPokemon.class);
        i.putExtra("id", id--);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);   //animazione slide da sx a dx
    }

    public void OnForwardClick(View v){
        Intent i = new Intent(this, DettaglioPokemon.class);
        i.putExtra("id", id++);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);    //animazione slide da dx a sx
    }

    public void cambiaColoreTipo(TextView tipo){
        if(tipo.getText().toString().equals("null")) {
            tipo.setTextColor(Color.rgb(111, 129, 167));
        }

        switch (tipo.getText().toString()){
            case "Erba":
                tipo.setBackgroundColor(Color.rgb(50, 224, 11));
                break;
            case "Fuoco":
                tipo.setBackgroundColor(Color.rgb(240, 54, 12));
                break;
            case "Acqua":
                tipo.setBackgroundColor(Color.rgb(11, 101, 219));
                break;
            case "Elettro":
                tipo.setBackgroundColor(Color.rgb(229, 245, 7));
                break;
            case "Normale":
                tipo.setBackgroundColor(Color.rgb(150, 150, 144));
                break;
            case "Ghiaccio":
                tipo.setBackgroundColor(Color.rgb(108, 245, 229));
                break;
            case "Lotta":
                tipo.setBackgroundColor(Color.rgb(209, 83, 4));
                break;
            case "Veleno":
                tipo.setBackgroundColor(Color.rgb(92, 4, 112));
                break;
            case "Terra":
                tipo.setBackgroundColor(Color.rgb(148, 76, 16));
                break;
            case "Volante":
                tipo.setBackgroundColor(Color.rgb(149, 245, 240));
                break;
            case "Psico":
                tipo.setBackgroundColor(Color.rgb(168, 5, 227));
                break;
            case "Coleottero":
                tipo.setBackgroundColor(Color.rgb(148, 199, 8));
                break;
            case "Roccia":
                tipo.setBackgroundColor(Color.rgb(161, 154, 95));
                break;
            case "Spettro":
                tipo.setBackgroundColor(Color.rgb(62, 6, 94));
                break;
            case "Drago":
                tipo.setBackgroundColor(Color.rgb(38, 3, 99));
                break;
            case "Buio":
                tipo.setBackgroundColor(Color.rgb(37, 36, 38));
                break;
            case "Acciaio":
                tipo.setBackgroundColor(Color.rgb(143, 138, 148));
                break;
            case "Folletto":
                tipo.setBackgroundColor(Color.rgb(227, 104, 227));
                break;
        }
    }

}
