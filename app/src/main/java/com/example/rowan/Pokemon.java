package com.example.rowan;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Pokemon {
    private int id; //chiave primaria
    private String nome;
    private String tipo1;
    private String tipo2;
    private float altezza;
    private float peso;
    private String abilita;
    private String abilitaSegreta;
    private String categoria;
    private String descrizione;
    private byte[] artwork;
    private byte[] artworkShiny;

    public Pokemon(){
        //costruttore vuoto
    }

    public Pokemon(int id, String nome, String tipo1, String tipo2, float altezza, float peso, String abilita, String abilitaSegreta, String categoria, String descrizione, byte[] artwork, byte[] artworkShiny) {
        this.id = id;
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.altezza = altezza;
        this.peso = peso;
        this.abilita = abilita;
        this.abilitaSegreta = abilitaSegreta;
        this.categoria = categoria;
        this.descrizione = descrizione;
        this.artwork = artwork;
        this.artworkShiny = artworkShiny;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public float getAltezza() {
        return altezza;
    }

    public void setAltezza(float altezza) {
        this.altezza = altezza;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getAbilita() {
        return abilita;
    }

    public void setAbilita(String abilita) {
        this.abilita = abilita;
    }

    public String getAbilitaSegreta() {
        return abilitaSegreta;
    }

    public void setAbilitaSegreta(String abilitaNascosta) {
        this.abilitaSegreta = abilitaNascosta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public byte[] getArtwork() {
        //DA DRAWABLE A BYTE[]
        /*Drawable drawable = this.artwork;
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        Log.d("MYTAG", "" + bitMapData);
        return bitMapData;*/
        return this.artwork;
    }

    public void setArtwork(byte[] artwork) {
        this.artwork = artwork;
    }

    public byte[] getArtworkShiny() {
        //DA DRAWABLE A BYTE[]
        /*Drawable drawable = this.artwork;
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        Log.d("MYTAG", "" + bitMapData);
        return bitMapData;*/
        return this.artworkShiny;
    }

    public void setArtworkShiny(byte[] artworkShiny) {
        this.artworkShiny = artworkShiny;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo1='" + tipo1 + '\'' +
                ", tipo2='" + tipo2 + '\'' +
                ", altezza=" + altezza +
                ", peso=" + peso +
                ", abilita='" + abilita + '\'' +
                ", abilitaNascosta='" + abilitaSegreta + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", artwork=" + artwork +
                ", artworkShiny=" + artworkShiny +
                '}';
    }
}
