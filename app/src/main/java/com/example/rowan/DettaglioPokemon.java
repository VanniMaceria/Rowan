package com.example.rowan;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class DettaglioPokemon extends AppCompatActivity {
    private int id;
    private DatabaseHandler dbHandler;
    private Cursor cursor;
    private ImageView artwork;
    private TextView idTv;
    private TextView nome;
    private TextView tipo1;
    private TextView tipo2;
    private TextView altezza;
    private TextView peso;
    private TextView abilita;
    private TextView abilitaN;
    private TextView categoria;
    private TextView descrizione;
    private boolean flag = false;
    private Colorazione colorazione;
    private ArrayList<Integer> leggendari;

    /*
     * In questa classe aggiungi dei pulsanti per switchare la forma regionale, descrizione,
     * forma regionale shiny i tipi e le abilità
     * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recupero l'id passatomi dall'activity di una regione o dalla barra di ricerca per nome
        Intent i = getIntent();
        id = 0;
        id = i.getIntExtra("id", 0);
        Log.d("MYTAG", "DettaglioPokemon dice: Ho ricevuto un id = " + id);
        String idString = String.valueOf(id);

        /*lista contenente gli id di tutti i leggendari l'alternativa sarebbe stata mettere un campo booleano isLeggendario come
         attributo della tabella Pokèmon ma preferisco fare così*/
        leggendari = new ArrayList<Integer>(Arrays.asList(144, 145, 146, 150, 151));

        boolean isLeggendario = isLeggendario();
        Log.d("MYTAG", "FLAG -> " + isLeggendario);

        if(isLeggendario){
            setContentView(R.layout.dettaglio_leggendario_layout);
        }

        else{
            setContentView(R.layout.dettaglio_pokemon_layout);
        }


        dbHandler = new DatabaseHandler(this);
        cursor = dbHandler.selectById(idString);

        if (cursor.getCount() == 0) {
            Log.d("MYTAG", "NON CI SONO PKMN");
            //metti che porta ad una pagina di errore tipo 404 Not Found
        }

        if (cursor.moveToFirst()) {
            Log.d("MYTAG", cursor.getString(1));
            Log.d("MYTAG", cursor.getString(2));
        }

        //rimpiazzo gli elementi dinamicamente con il pokèmon ottenuto
        artwork = new ImageView(this);
        artwork = findViewById(R.id.artworkBig);
        byte[] img = cursor.getBlob(11); //retrieve dell'artwork
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        artwork.setImageBitmap(bitmap); //imposto l'artwork

        idTv = new TextView(this);
        idTv = findViewById(R.id.idText);
        idTv.setText("#" + id);

        nome = new TextView(this);
        nome = findViewById(R.id.nome);
        nome.setText(cursor.getString(2));

        colorazione = new Colorazione();

        tipo1 = new TextView(this);
        tipo1 = findViewById(R.id.dettaglioTipo1);
        tipo1.setText(cursor.getString(3));
        colorazione.cambiaColoreTipo(tipo1);

        tipo2 = new TextView(this);
        tipo2 = findViewById(R.id.dettaglioTipo2);
        tipo2.setText(cursor.getString(4));
        colorazione.cambiaColoreTipo(tipo2);

        if (tipo2.equals("null")) {
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

    public void onShinyClick(View v) {
        if (flag) {
            byte[] imgShiny = cursor.getBlob(11); //retrieve dell'artwork
            Bitmap bitmapShiny = BitmapFactory.decodeByteArray(imgShiny, 0, imgShiny.length);
            artwork.setImageBitmap(bitmapShiny); //imposto l'artwork
            flag = false;
        } else {
            byte[] imgShiny = cursor.getBlob(12); //retrieve dell'artwork
            Bitmap bitmapShiny = BitmapFactory.decodeByteArray(imgShiny, 0, imgShiny.length);
            artwork.setImageBitmap(bitmapShiny); //imposto l'artwork shiny
            flag = true;
        }
    }

    public void onBackClick(View v) {
        Intent i = new Intent(this, this.getClass());
        id = id - 1;

        if (id < 1) {
            Toast.makeText(this, "Non puoi andare oltre", Toast.LENGTH_SHORT).show();
            id = id + 1;
            i.putExtra("id", id);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        } else {
            i.putExtra("id", id);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);   //animazione slide da sx a dx
        }
    }

    public void onForwardClick(View v) {
        Intent i = new Intent(this, this.getClass());
        id = id + 1;

        if (id > 905) {   //questo lo devo aggiornare man mano che escono nuovi pokèmon
            Toast.makeText(this, "Non puoi andare oltre", Toast.LENGTH_SHORT).show();
            id = id - 1;
            i.putExtra("id", id);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else {
            i.putExtra("id", id);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);    //animazione slide da dx a sx
        }
    }

    public void goBack(View v) {
        onBackPressed();
    }

    public boolean isLeggendario() {
        if(leggendari.contains(id)){
            return true;
        }
        return false;
    }

}
