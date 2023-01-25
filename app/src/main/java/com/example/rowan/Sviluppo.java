package com.example.rowan;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class Sviluppo extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sviluppo);
        //aggiungi un pulsante trasparente che se viene cliccato tot volte ti porta alla pagina di Monferno
    }
}
