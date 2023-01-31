package com.example.rowan;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context context;
    private static int DATABASE_VERSION = 20;
    private static final String DATABASE_NAME = "RowanDB";
    //tabella Pokèmon
    private static final String TABELLA_POKEMON = "Pokemon";
    private static final String COLONNA_CHIAVE = "chiave";
    private static final String COLONNA_ID = "id";
    private static final String COLONNA_NOME = "nome";
    private static final String COLONNA_TIPO_1 = "tipo1";
    private static final String COLONNA_TIPO_2 = "tipo2";
    private static final String COLONNA_ALTEZZA = "altezza";
    private static final String COLONNA_PESO = "peso";
    private static final String COLONNA_ABILITA = "abilita";
    private static final String COLONNA_ABILITA_SEGRETA = "abilitaSegreta";
    private static final String COLONNA_CATEGORIA = "categoria";
    private static final String COLONNA_DESCRIZIONE = "descrizione";
    private static final String COLONNA_ARTWORK = "artwork";
    private static final String COLONNA_ARTWORK_SHINY = "artworkShiny";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePokemon = "CREATE TABLE " + TABELLA_POKEMON + "(" +
                COLONNA_CHIAVE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNA_ID + " INTEGER NOT NULL UNIQUE, " +
                COLONNA_NOME + " TEXT NOT NULL, " +
                COLONNA_TIPO_1 + " TEXT NOT NULL, " +
                COLONNA_TIPO_2 + " TEXT, " +
                COLONNA_ALTEZZA + " FLOAT NOT NULL, " +
                COLONNA_PESO + " FLOAT NOT NULL, " +
                COLONNA_ABILITA + " TEXT NOT NULL, " +
                COLONNA_ABILITA_SEGRETA + " TEXT, " +
                COLONNA_CATEGORIA + " TEXT NOT NULL, " +
                COLONNA_DESCRIZIONE + " TEXT NOT NULL, " +
                COLONNA_ARTWORK + " BLOB NOT NULL, " +
                COLONNA_ARTWORK_SHINY + " BLOB NOT NULL);";

        db.execSQL(createTablePokemon);
        Log.d("CREATE", "Tabella Pokèmon creata");
        //per cancellare il db: view -> tool windows -> device file explorer -> cartella progetto ed elimini i file in database
        //oppure se vuoi solo fare il drop table basta aggiornare la versione del db
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_POKEMON);
        //fallo anche per le altre tabelle
        onCreate(db);
    }

    public void addPokemon(Pokemon p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); //sarebbe la tupla (entry del db)

        cv.put(COLONNA_ID, p.getId());
        cv.put(COLONNA_NOME, p.getNome());
        cv.put(COLONNA_TIPO_1, p.getTipo1());
        cv.put(COLONNA_TIPO_2, p.getTipo2());
        cv.put(COLONNA_ALTEZZA, p.getAltezza());
        cv.put(COLONNA_PESO, p.getPeso());
        cv.put(COLONNA_ABILITA, p.getAbilita());
        cv.put(COLONNA_ABILITA_SEGRETA, p.getAbilitaSegreta());
        cv.put(COLONNA_CATEGORIA, p.getCategoria());
        cv.put(COLONNA_DESCRIZIONE, p.getDescrizione());
        cv.put(COLONNA_ARTWORK, p.getArtwork());
        cv.put(COLONNA_ARTWORK_SHINY, p.getArtworkShiny());

        try{
            long flag = db.insert(TABELLA_POKEMON, null, cv);   //inserisce la tabella Pokemon nel db

            if(flag == -1){
                Log.d("INSERT-TAG", "INSERT - FAILED");
            }

            else{
                Log.d("INSERT-TAG", "INSERT - OK");
            }
        }catch(SQLiteConstraintException e){
            System.out.println("Errore nell'inserimento");
        }
    }

    public void popolaDb(){
        //Kanto #[1 - 151]
        Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", "Erba", "Veleno", 0.7F, 6.9F, "Erbaiuto", "Clorofilla", "Seme", "Fin dalla nascita questo Pokémon ha sulla schiena un seme che cresce lentamente. Appena nato, trae nutrimento dalle sostanze contenute nel seme sul dorso.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._01_bulbasaur)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._01_bulbasaurshiny)));
        addPokemon(bulbasaur);
        Pokemon ivysaur = new Pokemon(2, "Ivysaur", "Erba", "Veleno", 1.0F, 13.0F, "Erbaiuto", "Clorofilla", "Seme", "Il bulbo sulla schiena è cresciuto così tanto da impedirgli di alzarsi in piedi sulle zampe posteriori. L’esposizione alla luce solare lo rafforza e fa crescere il bocciolo che ha sul dorso.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._02_ivysaur)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._02_ivysaurshiny)));
        addPokemon(ivysaur);
        Pokemon venusaur = new Pokemon(3, "Venusaur", "Erba", "Veleno", 2.0F, 100.0F, "Erbaiuto", "Clorofilla", "Seme", "Il fiore sboccia assorbendo energia solare. Si muove continuamente in cerca di luce. Il suo fiore emana una fragranza inebriante capace di placare l’animo di chi è impegnato nella lotta.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._03_venusaur)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._03_venusaurshiny)));
        addPokemon(venusaur);
        Pokemon charmander = new Pokemon(4, "Charmander", "Fuoco", null, 0.6F, 8.5F, "Aiutofuoco", "Solarpotere", "Lucertola", "Ama le cose calde. Si dice che quando piove gli esca vapore dalla punta della coda. Una fiamma gli arde sulla punta della coda fin dalla nascita. Se si spegnesse, per lui sarebbe la fine.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._04_charmander)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._04_charmandershiny)));
        addPokemon(charmander);
        Pokemon charmeleon = new Pokemon(5, "Charmeleon", "Fuoco", null, 1.1F, 19.0F, "Aiutofuoco", "Solarpotere", "Fiamma", "Ha un’indole feroce. Usa la coda fiammeggiante come una frusta e lacera l’avversario con gli artigli affilati. Se s’infervora nella lotta, sputa potenti fiamme che inceneriscono l’area circostante.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._05_charmeleon)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._05_charmeleonshiny)));
        addPokemon(charmeleon);
        Pokemon charizard = new Pokemon(6, "Charizard", "Fuoco", "Volante", 1.7F, 90.5F, "Aiutofuoco", "Solarpotere", "Fiamma", "Sputa fiamme incandescenti in grado di fondere le rocce. A volte causa incendi boschivi. Grazie alle possenti ali può volare fino a 1.400 m d’altezza. Le fiamme che sputa possono raggiungere temperature altissime.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._06_charizard)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._06_charizardshiny)));
        addPokemon(charizard);
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Acqua", null, 0.5F, 9.0F, "Acquaiuto", "Copripioggia", "Tartaghina", "Quando ritrae il lungo collo dentro la corazza sputa un vigoroso getto d’acqua. Se si sente minacciato, ritira le zampe nel guscio e inizia a spruzzare acqua dalla bocca.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._07_squirtle)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._07_squirtleshiny)));
        addPokemon(squirtle);
        Pokemon wartortle = new Pokemon(8, "Wartortle", "Acqua", null, 1.0F, 22.5F, "Acquaiuto", "Copripioggia", "Tartaruga", "È considerato un simbolo di longevità. Se c’è del muschio sul suo guscio, significa che è molto anziano. Controlla abilmente le orecchie e la coda coperte di pelo, mantenendo l’assetto mentre nuota.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._08_wartortle)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._08_wartortleshiny)));
        addPokemon(wartortle);
        Pokemon blastoise = new Pokemon(9, "Blastoise", "Acqua", null, 1.6F, 85.5F, "Acquaiuto", "Copripioggia", "Crostaceo", "Mette KO gli avversari schiacciandoli sotto il corpo possente. Se è in difficoltà, può ritrarsi nella corazza. I cannoni sul suo guscio sparano getti d’acqua capaci di bucare l’acciaio.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._09_blastoise)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._09_blastoiseshiny)));
        addPokemon(blastoise);
        Pokemon caterpie = new Pokemon(10, "Caterpie", "Coleottero", null, 0.3F, 2.9F, "Polvoscudo", "Fugafacile", "Baco", "Per proteggersi emette un puzzo terribile dalle antenne sul capo, con cui tiene lontani i nemici. Le ventose sulle sue corte zampe gli permettono di affrontare ogni salita e di scalare senza fatica qualsiasi muro.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._10_caterpie)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._10_caterpieshiny)));
        addPokemon(caterpie);
        Pokemon metapod = new Pokemon(11, "Metapod", "Coleottero", null, 0.7F, 9.9F, "Muta", null, "Bozzolo", "In attesa di evolversi, l’unica cosa che può fare è indurire la sua corazza, quindi rimane immobile per evitare di essere attaccato. La corazza esterna è robusta ma l’interno è molle. Per questo non è in grado di resistere ad attacchi particolarmente veementi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._11_metapod)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._11_metapodshiny)));
        addPokemon(metapod);
        Pokemon butterfree = new Pokemon(12, "Butterfree", "Coleottero", "Volante", 1.1F, 32.0F, "Insettocchi", "Lentifumè", "Bozzolo", "Sbatte le ali a gran velocità per liberare le sue polveri tossiche nell’aria. Raccoglie nettare tutti i giorni e lo spalma sulla peluria che ricopre le sue zampe per trasportarlo al nido.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._12_butterfree)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._12_butterfreeshiny)));
        addPokemon(butterfree);
        Pokemon weedle = new Pokemon(13, "Weedle", "Coleottero", "Veleno", 0.3F, 3.2F, "Polvoscudo", "Fugafacile", "Millepiedi", "Ha un pungiglione acuminato sul capo. Vive tra l’erba alta e nei boschi, dove si nutre di foglie.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._13_weedle)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._13_weedleshiny)));
        addPokemon(weedle);
        Pokemon kakuna = new Pokemon(14, "Kakuna", "Coleottero", "Veleno", 0.6F, 10.0F, "Muta", null, "Bozzolo", "È molto limitato nei movimenti. Se si sente minacciato, estrae il pungiglione e avvelena il nemico.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._14_kakuna)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._14_kakunashiny)));
        addPokemon(kakuna);
        Pokemon beedrill = new Pokemon(15, "Beedrill", "Coleottero", "Veleno", 1.0F, 29.5F, "Aiutinsetto", "Cecchino", "Velenape", "Possiede tre aculei velenosi, due sulle zampe anteriori e uno sull’addome, con cui punge ripetutamente i nemici.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._15_beedrill)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._15_beedrillshiny)));
        addPokemon(beedrill);
        Pokemon pidgey = new Pokemon(16, "Pidgey", "Normale", "Volante", 0.3F, 1.8F, "Sguardofermo\nIntricopiedi", "Pettinfuori", "Uccellino", "Di indole docile, preferisce gettare sabbia verso il nemico per proteggersi piuttosto che attaccarlo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._16_pidgey)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._16_pidgeyshiny)));
        addPokemon(pidgey);
        Pokemon pidgeotto = new Pokemon(17, "Pidgeotto", "Normale", "Volante", 1.1F, 30.8F, "Sguardofermo\nIntricopiedi", "Pettinfuori", "Uccello", "Un Pokémon pieno di vitalità che vola instancabile sul suo territorio in cerca di prede.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._17_pidgeotto)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._17_pidgeottoshiny)));
        addPokemon(pidgeotto);
        Pokemon pidgeot = new Pokemon(18, "Pidgeot", "Normale", "Volante", 1.5F, 39.5F, "Sguardofermo\nIntricopiedi", "Pettinfuori", "Uccello", "Può volare a una velocità di Mach 2 in cerca di prede. I suoi artigli sono armi micidiali.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._18_pidgeot)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._18_pidgeotshiny)));
        addPokemon(pidgeot);
        Pokemon rattata = new Pokemon(19, "Rattata", "Normale", null, 0.3F, 3.5F, "Fugafacile\nDentistretti", "Tuttafretta", "Topo", "Con i suoi incisivi rode qualsiasi cosa. Se se ne avvista uno, è probabile che in zona vivano almeno 40 esemplari.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._19_rattata)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._19_rattatashiny)));
        addPokemon(rattata);
        Pokemon raticate = new Pokemon(20, "Raticate", "Normale", null, 0.7F, 18.5F, "Fugafacile\nDentistretti", "Tuttafretta", "Topo", "Dispone di zampe posteriori palmate che gli permettono di nuotare nei fiumi in cerca di prede.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._20_raticate)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._20_raticateshiny)));
        addPokemon(raticate);
        Pokemon spearow = new Pokemon(21, "Spearow", "Normale", "Volante", 0.3F, 2.0F, "Sguardofermo", "Cecchino", "Uccellino", "Incapace di raggiungere alte quote, sorvola il suo territorio a gran velocità per proteggerlo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._21_spearow)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._21_spearowshiny)));
        addPokemon(spearow);
        Pokemon fearow = new Pokemon(22, "Fearow", "Normale", "Volante", 1.2F, 38.0F, "Sguardofermo", "Cecchino", "Becco", "Questo Pokémon molto antico vola in alto nel cielo al minimo accenno di pericolo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._22_fearow)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._22_fearowshiny)));
        addPokemon(fearow);
        Pokemon ekans = new Pokemon(23, "Ekans", "Veleno", null, 2.0F, 6.9F, "Prepotenza\nMuta", "Agitazione", "Serpente", "Si allunga sempre più con l’età. La notte si avvolge attorno a qualche ramo per dormire.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._23_ekans)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._23_ekansshiny)));
        addPokemon(ekans);
        Pokemon arbok = new Pokemon(24, "Arbok", "Veleno", null,  3.5F, 65.0F, "Prepotenza\nMuta", "Agitazione", "Cobra", "Gli spaventosi disegni che ha sulla pancia sono oggetto di studio. Ne sono state scoperte sei diverse variazioni.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._24_arbok)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._24_arbokshiny)));
        addPokemon(arbok);
        Pokemon pikachu = new Pokemon(25, "Pikachu", "Elettro", null, 0.4F, 6.0F, "Statico", "Parafulmine", "Topo", "Quando s’arrabbia, libera subito l’energia accumulata nelle sacche sulle guance. Quando vari Pokémon di questa specie si radunano, la loro energia si accumula e genera fulmini.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._25_pikachu)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._25_pikachushiny)));
        addPokemon(pikachu);
        Pokemon raichu = new Pokemon(26, "Raichu", "Elettro", null, 0.8F, 30.0F, "Statico", "Parafulmine", "Topo", "La sua coda scarica elettricità a terra, proteggendolo dalle scosse elettriche. Quando le sacche elettriche sulle sue guance si caricano completamente, gli si rizzano le orecchie.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._26_raichu)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._26_raichushiny)));
        addPokemon(raichu);
        Pokemon sandshrew = new Pokemon(27, "Sandshrew", "Terra", null, 0.6F, 12.0F, "Sabbiavelo", "Remasabbia", "Topo", "Ama rotolarsi nella sabbia delle zone desertiche, per rimuovere ogni traccia di sporcizia e umidità dal suo corpo. Scava nel terreno per costruire la sua tana, frantumando anche le rocce più dure con i suoi artigli affilati.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._27_sandshrew)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._27_sandshrewshiny)));
        addPokemon(sandshrew);
        Pokemon sandslash = new Pokemon(28, "Sandslash", "Terra", null, 1.0F, 29.5F, "Sabbiavelo", "Remasabbia", "Topo", "Più secco è il territorio dove Sandslash vive, più i suoi aculei dorsali diventano lisci e duri. Si arrampica sugli alberi utilizzando i suoi artigli affilati per poi condividere le bacche che trova con i Sandshrew, che aspettano sotto.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._28_sandslash)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._28_sandslashshiny)));
        addPokemon(sandslash);


    }

    public Cursor selectAll(){
        String query = "SELECT DISTINCT * FROM " + TABELLA_POKEMON + ";";
        //per le select delle regioni credo posso fare una cosa tipo: SELECT * FROM Pokemon WHERE id < min && id > max (Regione)
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor selectById(String id){
        String query = "SELECT * FROM " + TABELLA_POKEMON + " WHERE " + COLONNA_ID + " = " + id + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public byte[] convertToByteArray(Drawable d){
        Bitmap bitmap =  ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        return bitmapData;
    }

}
