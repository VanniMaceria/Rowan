package com.example.rowan;

import android.graphics.Color;
import android.widget.TextView;

public class Colorazione {  //classe che contiene metodi per la colorazione dei tipi che uso nelle varie activity

    public Colorazione(){

    }

    public void cambiaColoreTipo(TextView tipo) {
        //controllo per i pokemon con un solo tipo
        if (tipo.getText().toString().equals("null")) {
            tipo.setTextColor(Color.rgb(111, 129, 167));
        } else {
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
}
