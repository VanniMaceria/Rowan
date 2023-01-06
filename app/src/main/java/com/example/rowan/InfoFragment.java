package com.example.rowan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class InfoFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        TextView tvDiritti = root.findViewById(R.id.diritti);
        tvDiritti.setOnClickListener(view -> {
            Log.d("MYTAG", "CLICK SU DIRITTI");
            Intent i = new Intent(getActivity(), Diritti.class);
            startActivity(i);
        });

        LinearLayout llGithub = root.findViewById(R.id.layoutGithub);
        llGithub.setOnClickListener(view -> {
            Log.d("MYTAG", "CLICK SU GITHUB");
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/VanniMaceria"));
            startActivity(i);
        });

        LinearLayout llInstagram = root.findViewById(R.id.instagram);
        llInstagram.setOnClickListener(view -> {
            Log.d("MYTAG", "CLICK SU INSTAGRAM");
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/andrea_bucci18/"));
            startActivity(i);
        });

        TextView tvSviluppo = root.findViewById(R.id.sviluppo);
        tvSviluppo.setOnClickListener(view -> {
            Log.d("MYTAG", "CLICK SU SVILUPPO");
            Intent i = new Intent(getActivity(), Sviluppo.class);
            startActivity(i);
        });

        //return inflater.inflate(R.layout.fragment_info, container, false);
        return root;
    }


}