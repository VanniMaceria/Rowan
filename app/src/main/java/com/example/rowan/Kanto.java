package com.example.rowan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Kanto extends Activity {
    DatabaseHandler dbHandler;

    @Override
    public void onCreate(Bundle savedIstance) {
        super.onCreate(savedIstance);
        setContentView(R.layout.kanto_layout);

        dbHandler = new DatabaseHandler(this);
        displayData();
    }

    public void displayData() {
        LinearLayout contenitore = findViewById(R.id.contenitore);  //contiene tutte le view delle righe dei pkmn

         Cursor cursor = dbHandler.selectAll();
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
                id.setTextSize(25);
                Typeface font = getResources().getFont(R.font.ubuntu_medium);
                id.setTypeface(font);
                id.setPadding(0, 15, 0, 0);
                id.setWidth(130);

                //retrieve del nome
                nomePkmn.setText(cursor.getString(2));
                nomePkmn.setTextColor(Color.WHITE);
                nomePkmn.setGravity(Gravity.START);
                nomePkmn.setTextSize(25);
                nomePkmn.setPadding(40, 0, 0, 0);
                nomePkmn.setWidth(450);
                nomePkmn.setTypeface(font);

                tipo1.setText(cursor.getString(3));
                tipo1.setTextColor(Color.WHITE);
                tipo1.setTextSize(15);
                tipo1.setTypeface(font);
                cambiaColoreTipo(tipo1);
                tipo1.setPadding(10, 5, 10, 5);
                tipo1.setGravity(Gravity.START);
                tipo1.setWidth(350);

                tipo2.setText(cursor.getString(4));
                tipo2.setTextColor(Color.WHITE);
                tipo2.setTextSize(15);
                tipo2.setTypeface(font);
                cambiaColoreTipo(tipo2);
                tipo2.setPadding(10, 5, 10, 5);
                tipo2.setGravity(Gravity.START);
                tipo2.setWidth(350);
            }   //fine while
        }
    }

    public void cambiaColoreTipo(TextView tipo){
        //controllo per i pokemon con un solo tipo
        if(tipo.getText().toString().equals("null")) {
            tipo.setTextColor(Color.rgb(111, 129, 167));
        }

        else {
            switch (tipo.getText().toString()) {
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
                    tipo.setBackgroundColor(Color.rgb(253, 218, 13));
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
                    tipo.setBackgroundColor(Color.rgb(100, 149, 237));
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

}   //fine classe
