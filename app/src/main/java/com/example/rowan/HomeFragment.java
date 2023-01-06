package com.example.rowan;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //implemento i vari listener per rendere cliccabili le immagini
        ImageView kanto = root.findViewById(R.id.mappaKanto);
        kanto.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Kanto.class);
            startActivity(i);
        });

        ImageView johto = root.findViewById(R.id.mappaJohto);
        johto.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Johto.class);
            startActivity(i);
        });

        ImageView hoenn = root.findViewById(R.id.mappaHoenn);
        hoenn.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Hoenn.class);
            startActivity(i);
        });

        ImageView sinnoh = root.findViewById(R.id.mappaSinnoh);
        sinnoh.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Sinnoh.class);
            startActivity(i);
        });

        ImageView unima = root.findViewById(R.id.mappaUnima);
        unima.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Unima.class);
            startActivity(i);
        });

        ImageView kalos = root.findViewById(R.id.mappaKalos);
        kalos.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Kalos.class);
            startActivity(i);
        });

        ImageView alola = root.findViewById(R.id.mappaAlola);
        alola.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Alola.class);
            startActivity(i);
        });

        ImageView galar = root.findViewById(R.id.mappaGalar);
        galar.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Galar.class);
            startActivity(i);
        });

        return root;
    }

}