package com.example.rowan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Kanto extends Activity{
    private DatabaseHandler dbHandler;

    @Override
    public void onCreate(Bundle savedIstance) {
        super.onCreate(savedIstance);
        setContentView(R.layout.kanto_layout);

        dbHandler = new DatabaseHandler(this);
        displayData();
    }

    public void displayData() {
        LinearLayout contenitore = findViewById(R.id.contenitore);  //contiene tutte le view delle righe dei pkmn
        Colorazione colorazione = new Colorazione();

        Cursor cursor = dbHandler.selectAllFromKanto();
        if (cursor.getCount() == 0) {
            Log.d("MYTAG", "NON CI SONO PKMN");
        }

        else {
            while (cursor.moveToNext()) {
                //inizializzo tutti i layout e le view che mi servono
                LinearLayout layoutRigaPkmn = new LinearLayout(getApplicationContext());    //è contenuto in contenitore
                ImageView artwork = new ImageView(this);    //è contenuto in layoutRigaPkmn
                LinearLayout layoutRigaColorata = new LinearLayout(getApplicationContext());    // è contenuto in layoutRigaPkmn
                TextView id = new TextView(this);  //è contenuto in layoutRigaColorata
                TextView nomePkmn = new TextView(getApplicationContext());  //è contenuto in layoutRigaColorata
                LinearLayout layoutTipi = new LinearLayout(getApplicationContext());
                TextView tipo1 = new TextView(getApplicationContext()); //è contenuto in layoutTipi
                TextView tipo2 = new TextView(getApplicationContext()); //è contenuto in layoutTipi

                //imposto ed annido le view
                layoutRigaPkmn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutRigaPkmn.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParamsRigaPkmn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsRigaPkmn.setMargins(0, 0, 0, 20);
                layoutRigaPkmn.setLayoutParams(layoutParamsRigaPkmn);
                layoutRigaPkmn.setClickable(true);

                int ida = cursor.getInt(1);
                //al click di una riga parte l'activity del dettaglio pokemon; Passo l'id così posso fare la select su quel Pokèmon
                layoutRigaPkmn.setOnClickListener(view -> {
                    Intent i = new Intent(getBaseContext(), DettaglioPokemon.class);
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
                byte[] img = cursor.getBlob(11);
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                artwork.setImageBitmap(bitmap);
                //fine retrieve dell'artwork

                //retrieve dell'id
                id.setText("#" + cursor.getInt(1));
                id.setTextColor(Color.WHITE);
                id.setGravity(Gravity.START);
                id.setTextSize(23);
                Typeface font = getResources().getFont(R.font.ubuntu_medium);
                id.setTypeface(font);
                id.setPadding(0, 18, 0, 0);
                id.setWidth(150);

                //retrieve del nome
                nomePkmn.setText(cursor.getString(2));
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

}   //fine classe