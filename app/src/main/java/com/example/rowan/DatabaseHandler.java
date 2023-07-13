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
    private static int DATABASE_VERSION = 23;
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
    //tabella Regioni
    private static final String TABELLA_REGIONI = "Regioni";
    private static final String COLONNA_NOME_REGIONE = "nomeRegione";
    //join table Appartenenza (M:N)
    private static final String TABELLA_APPARTENENZA= "Appartenenza";
    private static final String COLONNA_ID_REPLICA = "id";
    private static final String COLONNA_NOME_REGIONE_REPLICA = "nomeRegione";

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
        Pokemon nidoranF = new Pokemon(29, "Nidoran♀", "Veleno", null, 0.4F, 7.0F, "Velenopunte\nAntagonismo", "Tuttafretta", "Velenago", "È più sensibile agli odori rispetto ai maschi. Capta le correnti d’aria con le vibrisse e si posiziona sottovento per cercare le prede. Mangia le bacche frantumandole con i robusti incisivi. La punta del corno è leggermente più arrotondata rispetto a quella del maschio.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._29_nidoran)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._29_nidoranshiny)));
        addPokemon(nidoranF);
        Pokemon nidorina = new Pokemon(30, "Nidorina", "Veleno", null, 0.8F, 20.0F, "Velenopunte\nAntagonismo", "Tuttafretta", "Velenago", "Si pensa che il corno sulla fronte si sia atrofizzato per evitare che Nidorina punga i suoi cuccioli quando li nutre. Quando il branco è minacciato da un pericolo, i suoi membri uniscono le forze e dispiegano un coro di ultrasuoni.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._30_nidorina)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._30_nidorinashiny)));
        addPokemon(nidorina);
        Pokemon nidoqueen = new Pokemon(31, "Nidoqueen", "Veleno", "Terra", 1.3F, 60.0F, "Velenopunte\nAntagonismo", "Forzabruta", "Trapano", "Più abile nella difesa che nell’attacco, protegge i cuccioli da qualunque attacco grazie alla corazza di squame. Coccola i cuccioli mettendoseli sulla schiena negli spazi fra gli aculei, che in quei momenti smettono di secernere veleno.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._31_nidoqueen)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._31_nidoqueenshiny)));
        addPokemon(nidoqueen);
        Pokemon nidoranM = new Pokemon(32, "Nidoran♂", "Veleno", null, 0.5F, 9.0F, "Velenopunte\nAntagonismo", "Tuttafretta", "Velenago", "Il corno sulla fronte è estremamente velenoso. Di indole circospetta, rizza costantemente le sue grandi orecchie. Impavido nonostante la corporatura minuta, lotta con coraggio per proteggere la femmina a cui è affezionato, anche a costo della vita.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._32_nidoran)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._32_nidoranshiny)));
        addPokemon(nidoranM);
        Pokemon nidorino = new Pokemon(33, "Nidorino", "Veleno", null, 0.9F, 19.5F, "Velenopunte\nAntagonismo", "Tuttafretta", "Velenago", "Vaga alla ricerca di una Pietralunare frantumando rocce con il suo corno più duro del diamante. È irritabile e sempre pronto a battersi. Quando il livello di adrenalina nel suo corpo si alza, aumenta anche la concentrazione di tossine.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._33_nidorino)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._33_nidorinoshiny)));
        addPokemon(nidorino);
        Pokemon nidoking = new Pokemon(34, "Nidoking", "Veleno", "Terra", 1.4F, 62.0F, "Velenopunte\nAntagonismo", "Forzabruta", "Trapano", "Quando si scatena non c’è modo di fermarlo, ma davanti a Nidoqueen, la sua compagna di lunga data, ritrova la calma. Si dà vanto della propria forza e lotta con vigore sfruttando la coda robusta e il corno in grado di frantumare il diamante.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._34_nidoking)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._34_nidokingshiny)));
        addPokemon(nidoking);
        Pokemon clefairy = new Pokemon(35, "Clefairy", "Folletto", null, 0.6F, 7.5F, "Incantevole\nMagicscudo", "Amicoscudo", "Fata", "Si dice che vedere un gruppo di Clefairy ballare con la luna piena sia di ottimo auspicio. Il suo verso e le sue movenze graziose rendono questo adorabile Pokémon molto popolare. Sfortunatamente, però, è molto raro.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._35_clefairy)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._35_clefairyshiny)));
        addPokemon(clefairy);
        Pokemon clefable = new Pokemon(36, "Clefable", "Folletto", null, 1.3F, 40.0F, "Incantevole\nMagicscudo", "Imprudenza", "Fata", "Timido Pokémon Fata, molto raro a vedersi. Scappa e si nasconde non appena avverte la presenza delle persone. Il loro udito è così acuto che sentono un ago cadere a 1 km di distanza. Per questo di solito vivono in luoghi molto silenziosi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._36_clefable)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._36_clefableshiny)));
        addPokemon(clefable);
        Pokemon vulpix = new Pokemon(37, "Vulpix", "Fuoco", null, 0.6F, 9.9F, "Fuocardore", "Siccità", "Volpe", "Quando è giovane ha sei meravigliose code, che si moltiplicano durante la sua crescita. La pelliccia di cui sono ricoperte è calda al tatto.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._37_vulpix)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._37_vulpixshiny)));
        addPokemon(vulpix);
        Pokemon ninetales = new Pokemon(38, "Ninetales", "Fuoco", null, 1.1F, 19.9F, "Fuocardore", "Siccità", "Volpe", "Dicono che viva un millennio. Ognuna delle sue code è dotata di un potere magico. Molto intelligente e vendicativo. Chi gli afferra una coda rischia una maledizione millenaria.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._38_ninetales)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._38_ninetalesshiny)));
        addPokemon(ninetales);
        Pokemon jigglypuff = new Pokemon(39, "Jigglypuff", "Normale", "Folletto", 0.5F, 5.5F, "Incantevole\nTenacia", "Amicoscudo", "Pallone", "Quando i suoi occhioni rotondi tremolano, canta una misteriosa melodia che fa addormentare. Quando si gonfia prima di cantare, la sua ninnananna dura più a lungo e fa addormentare immancabilmente chi la ascolta.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._39_jigglypuff)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._39_jigglypuffshiny)));
        addPokemon(jigglypuff);
        Pokemon wiggltuff = new Pokemon(40, "Wigglytuff", "Normale", "Folletto", 1.0F, 12.0F, "Incantevole\nTenacia", "Indagine", "Pallone", "Ha un pelo molto fino. Attenzione a non farlo adirare, perché può gonfiarsi e caricare con tutto il suo peso. Il suo folto pelo è così morbido che chiunque lo tocchi non può fare a meno di continuare ad accarezzarlo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._40_wigglytuff)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._40_wigglytuffshiny)));
        addPokemon(wiggltuff);
        Pokemon zubat = new Pokemon(41, "Zubat", "Veleno", "Volante", 0.8F, 7.5F, "Forza interiore", "Intrapasso", "Pipistrello", "Sonda l’ambiente circostante emettendo ultrasuoni dalla bocca. In questo modo riesce a volteggiare agilmente anche in caverne strette. Vive in grotte mai rischiarate dalla luce del sole. Quando si fa giorno, si raggruppa con i suoi simili per scaldarsi mentre dorme.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._41_zubat)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._41_zubatshiny)));
        addPokemon(zubat);
        Pokemon golbat = new Pokemon(42, "Golbat", "Veleno", "Volante", 1.6F, 55.0F, "Forza interiore", "Intrapasso", "Pipistrello","Va matto per il sangue di altre creature. Si dice che a volte lo condivida con i compagni affamati. Cammina agilmente con i piccoli piedi. Si avvicina di soppiatto alle prede addormentate per azzannarle e berne il sangue.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._42_golbat)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._42_golbatshiny)));
        addPokemon(golbat);
        Pokemon oddish = new Pokemon(43, "Oddish", "Erba", "Veleno", 0.5F, 5.4F, "Clorofilla", "Fugafacile", "Malerba", "Inizia a muoversi quando è illuminato dalla luce lunare. Di notte se ne va in giro a spargere semi. Di giorno vive nel freddo sottosuolo, per evitare la luce del sole. Cresce assorbendo la luce lunare.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._43_oddish)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._43_oddishshiny)));
        addPokemon(oddish);
        Pokemon gloom = new Pokemon(44, "Gloom", "Erba", "Veleno", 0.8F, 8.6F, "Clorofilla", "Tanfo", "Malerba", "I pistilli emanano un puzzo orribile, capace di far svenire chiunque nel raggio di 2 km. Ciò che sembra bava è in realtà dolce nettare. È molto viscoso: si appiccica appena lo si sfiora.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._44_gloom)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._44_gloomshiny)));
        addPokemon(gloom);
        Pokemon vileplume = new Pokemon(45, "Vileplume", "Erba", "Veleno", 1.2F, 18.6F, "Clorofilla", "Spargispora", "Fiore", "Ha i petali più grandi del mondo. Ad ogni suo passo, i petali liberano fitte nubi di polline tossico. Più grandi sono i petali, maggiore è la quantità di polline tossico che contengono. La testa, però, diventa molto pesante e fa fatica a sostenerla.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._45_vileplume)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._45_vileplumeshiny)));
        addPokemon(vileplume);
        Pokemon paras = new Pokemon(46, "Paras", "Coleottero", "Veleno", 0.3F, 5.4F, "Spargispora\nPellearsa", "Umidità", "Fungo", "Scava nella terra per cibarsi delle radici degli alberi, ma i funghi che ha sul dorso assorbono quasi tutte le sostanze nutritive.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._46_paras)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._46_parasshiny)));
        addPokemon(paras);
        Pokemon parasect = new Pokemon(47, "Parasect", "Coleottero", "Veleno", 1.0F, 29.5F, "Spargispora\nPellearsa", "Umidità", "Fungo", "Il fungo sulla schiena assorbe l’energia dell’organismo ospite, su cui ha anche pieno controllo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._47_parasect)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._47_parasectshiny)));
        addPokemon(parasect);
        Pokemon venonat = new Pokemon(48, "Venonat", "Coleottero", "Veleno", 1.0F, 30.0F, "Insettocchi\nLentifumè", "Fugafacile", "Insetto", "Da tutto il suo corpo trasuda veleno. Di notte cattura piccoli Pokémon Coleottero attratti dalla luce. Usa gli occhi come radar per spostarsi nel buio, oltre a servirsene per lanciare potenti raggi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._48_venonat)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._48_venonatshiny)));
        addPokemon(venonat);
        Pokemon venomoth = new Pokemon(49, "Venomoth", "Coleottero", "Veleno", 1.5F, 12.5F, "Polvoscudo\nLentifumè", "Splendicute", "Velentarma", "Le ali sono ricoperte di scaglie. Ogni volta che le batte, viene liberata nell’aria una polvere velenosa.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._49_venomoth)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._49_venomothshiny)));
        addPokemon(venomoth);
        Pokemon diglett = new Pokemon(50, "Diglett", "Terra", null, 0.2F, 0.8F, "Sabbiavelo\nTrappoarena", "Silicoforza", "Talpa", "Vive un metro sottoterra, dove si nutre di radici. Talvolta compare in superficie. La sua pelle è sottilissima. Se si espone alla luce, si indebolisce poiché il suo sangue si riscalda.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._50_diglett)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._50_diglettshiny)));
        addPokemon(diglett);
        Pokemon dugtrio = new Pokemon(51, "Dugtrio", "Terra", null, 0.7F, 33.3F, "Sabbiavelo\nTrappoarena", "Silicoforza", "Talpa", "Le tre teste si muovono alternativamente per ammorbidire il terreno e renderlo così più facile da scavare. Durante la lotta, scava tunnel nel terreno per sorprendere il nemico attaccandolo da una direzione imprevedibile.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._51_dugtrio)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._51_dugtrioshiny)));
        addPokemon(dugtrio);
        Pokemon meowth = new Pokemon(52, "Meowth", "Normale", null, 0.4F, 4.2F, "Raccolta\nTecnico", "Agitazione", "Graffimiao", "Di giorno non fa che dormire, mentre la notte perlustra il suo territorio con gli occhi luccicanti. Ama gli oggetti scintillanti. Se nota qualcosa che brilla, anche la moneta sulla sua fronte risplende.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._52_meowth)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._52_meowthshiny)));
        addPokemon(meowth);
        Pokemon persian = new Pokemon(53, "Persian", "Normale", null, 1.0F, 32.0F, "Tecnico\nScioltezza", "Agitazione", "Nobilgatto", "Molti lo vogliono come Pokémon da compagnia per via dello splendido pelo, ma non va preso sotto gamba perché graffia facilmente. Pokémon dal temperamento impetuoso. Quando rizza la coda, meglio stargli alla larga: è segno che sta per scattare e mordere.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._53_persian)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._53_persianshiny)));
        addPokemon(persian);
        Pokemon psyduck = new Pokemon(54, "Psyduck", "Acqua", null, 0.8F, 19.6F, "Umidità\nAntimeteo", "Nuotovelox", "Papero", "È costantemente tormentato dal mal di testa. Se questo si acuisce, può manifestare strani poteri di cui in seguito non ha memoria.",  convertToByteArray(ContextCompat.getDrawable(context, R.drawable._54_psyduck)),  convertToByteArray(ContextCompat.getDrawable(context, R.drawable._54_psyduckshiny)));
        addPokemon(psyduck);
        Pokemon golduck = new Pokemon(55, "Golduck", "Acqua", null, 1.7F, 76.0F, "Umidità\nAntimeteo", "Nuotovelox", "Papero", "Quando nuota a tutta velocità con i suoi lunghi arti palmati, la fronte gli si illumina. Predilige i laghi e i corsi d’acqua dalla corrente placida. Quando nuota è molto aggraziato.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._55_golduck)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._55_golduckshiny)));   //con Antimeteo, il terrore di Archeo-Groudon
        addPokemon(golduck);
        Pokemon mankey = new Pokemon(56, "Mankey", "Lotta", null, 0.5F, 28.0F, "Spiritovivo\nGrancollera", "Agonismo", "Suinpanzè", "Vivono in branchi in cima agli alberi. Se perdono il contatto con il gruppo s’infuriano per la solitudine. Anche quando è di buon umore, solitamente può abbandonarsi all’ira da un momento all’altro per un nonnulla.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._56_mankey)),convertToByteArray(ContextCompat.getDrawable(context, R.drawable._56_mankeyshiny)));
        addPokemon(mankey);
        Pokemon primape = new Pokemon(57, "Primape", "Lotta", null, 1.0F, 32.0F, "Spiritovivo\nGrancollera", "Agonismo", "Suinpanzé", "Diventa furioso se si sente osservato. Insegue chiunque incontri il suo sguardo. Secondo le teorie di certi studiosi, i Primeape sono arrabbiati anche quando si trovano dentro la Poké Ball.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._57_primeape)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._57_primeapeshiny)));
        addPokemon(primape);
        Pokemon growlithe = new Pokemon(58, "Growlithe", "Fuoco", null, 0.7F, 19.0F, "Prepotenza\nFuocardore", "Giustizia", "Cagnolino", "Coraggioso e affidabile, si oppone senza paura anche a nemici più grandi e forti di lui. Coraggioso e affidabile, si oppone senza paura anche a nemici più grandi e forti di lui.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._58_growlithe)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._58_growlitheshiny)));
        addPokemon(growlithe);
        Pokemon arcanine  = new Pokemon(59, "Arcanine", "Fuoco", null, 1.9F, 155.0F, "Prepotenza\nFuocardore", "Giustizia", "Leggenda", "Dal disegno su un’antica pergamena si vede come le sue corse sui prati incantassero le persone. Il suo maestoso ruggito ispira un senso di ammirazione. Chiunque lo senta non può far a meno di prostrarsi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._59_arcanine)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._59_arcanineshiny)));
        addPokemon(arcanine);
        Pokemon poliwag = new Pokemon(60, "Poliwag", "Acqua", null, 0.6F, 12.4F, "Umidità\nAssorbacqua", "Nuotovelox", "Girino", "È più portato per il nuoto che per la corsa. Il motivo spiraliforme sul ventre è parte dei suoi organi interni visibili in trasparenza. Nei fiumi dalla corrente impetuosa, si attacca alle rocce utilizzando le spesse labbra come una ventosa.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._60_poliwag)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._60_poliwagshiny)));
        addPokemon(poliwag);
        Pokemon poliwhirl = new Pokemon(61, "Poliwhirl", "Acqua", null, 1.0F, 20.0F, "Umidità\nAssorbacqua", "Nuotovelox", "Girino", "Chi fissa a lungo il disegno spiraliforme sul ventre si assopisce, tant’è che Poliwhirl lo utilizza al posto della ninnananna per far dormire i piccoli. Secerne un sudore viscido simile a muco, grazie al quale riesce a fuggire sgusciando via dalla presa dei nemici.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._61_poliwhirl)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._61_poliwhirlshiny)));
        addPokemon(poliwhirl);
        Pokemon poliwrath = new Pokemon(62, "Poliwrath", "Acqua", "Lotta", 1.3F, 54.0F, "Umidità\nAssorbacqua", "Nuotovelox", "Girino", "Il suo corpo è un ammasso di muscoli. Nuota anche nei mari più freddi frantumando il ghiaccio fluttuante con le braccia vigorose per farsi strada. È abile sia nel nuoto che nella lotta corpo a corpo. Sferra pugni vigorosi grazie alle sue braccia allenate.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._62_poliwrath)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._62_poliwrathshiny)));
        addPokemon(poliwrath);
        Pokemon abra = new Pokemon(63, "Abra", "Psico", null, 0.9F, 19.5F, "Forza interiore\nSincronismo", "Magicscudo", "Psico", "Il contenuto dei suoi sogni influisce sui suoi poteri psichici, che può utilizzare anche mentre dorme. È in grado di teletrasportarsi mentre dorme. Si dice che più profondo sia il suo sonno, più possa spostarsi lontano.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._63_abra)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._63_abrashiny)));
        addPokemon(abra);
        Pokemon kadabra = new Pokemon(64, "Kadabra", "Psico", null, 1.3F, 56.5F, "Forza interiore\nSincronismo", "Magicscudo", "Psico", "I suoi poteri psichici gli permettono di levitare mentre dorme. Come cuscino usa la sua coda straordinariamente elastica. La potenza della sua psicocinesi è immensa. Immagazzina i suoi poteri psichici nella stella sulla fronte, preparandosi all’evoluzione.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._64_kadabra)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._64_kadabrashiny)));
        addPokemon(kadabra);
        Pokemon alakazam = new Pokemon(65, "Alakazam", "Psico", null, 1.5F, 48.0F, "Forza interiore\nSincronismo", "Magicscudo", "Psico", "Possiede un intelletto estremamente elevato e si dice sia in grado di ricordare ogni evento della sua vita, dalla nascita alla morte. Padroneggia potenti poteri psichici, con i quali si dice abbia creato i cucchiai che tiene in mano.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._65_alakazam)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._65_alakazamshiny)));
        addPokemon(alakazam);
        Pokemon machop = new Pokemon(66, "Machop", "Lotta", null, 0.8F, 19.5F, "Dentistretti\nNullodifesa", "Cuordeciso", "Megaforza", "Il suo corpo è formato interamente da muscoli. Non è più alto di un bambino, ma può sollevare e lanciare 100 adulti. Sempre traboccante di energia, passa il tempo a sollevare massi. Così diventa ancora più forte.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._66_machop)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._66_machopshiny)));
        addPokemon(machop);
        Pokemon machoke = new Pokemon(67, "Machoke", "Lotta", null, 1.5F, 70.5F, "Dentistretti\nNullodifesa", "Cuordeciso", "Megaforza", "Il suo corpo muscoloso è così forte che usa una cintura per contenere la sua potenza. Aiuta le persone a trasportare carichi e a svolgere altri lavori pesanti.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._67_machamp)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._67_machokeshiny)));
        addPokemon(machoke);
        Pokemon machamp = new Pokemon(68, "Machamp", "Lotta", null, 1.6F, 130.0F, "Dentistretti\nNullodifesa", "Cuordeciso", "Megaforza", "Agita velocemente le quattro braccia tempestando gli avversari di pugni e colpi da ogni direzione.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._68_machamp)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._68_machampshiny)));
        addPokemon(machamp);
        Pokemon bellsprout = new Pokemon(69, "Bellsprout", "Erba", "Veleno", 0.7F, 4.0F, "Clorofilla", "Voracità", "Fiore", "Ama i luoghi caldi e umidi. Cattura minuscoli Pokémon di tipo Coleottero con i suoi viticci e poi li divora.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._69_bellsprout)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._69_bellsproutshiny)));
        addPokemon(bellsprout);
        Pokemon weepinbell = new Pokemon(70, "Weepinbell", "Erba", "Veleno", 1.0F, 6.4F, "Clorofilla", "Voracità", "Moschivoro", "Quando è affamato divora qualsiasi cosa gli capiti a tiro e la scioglie con i suoi potenti acidi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._70_weepinbell)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._70_weepinbellshiny)));
        addPokemon(weepinbell);
        Pokemon victreebel = new Pokemon(71, "Victreebell", "Erba", "Veleno", 1.7F, 15.5F, "Clorofilla", "Voracità", "Moschivoro", "Attira le prede con il dolce aroma del miele, le inghiotte e nel giro di un giorno le scioglie completamente, ossa incluse.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._71_victreebel)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._71_victreebelshiny)));
        addPokemon(victreebel);
        Pokemon tentacool = new Pokemon(72, "Tentacool", "Acqua", "Veleno", 0.9F, 45.0F, "Corpochiaro\nMelma", "Copripioggia", "Medusa", "Non essendo molto abile a nuotare, va in cerca di prede fluttuando in acque poco profonde. Il suo corpo composto quasi interamente d’acqua rende molto difficile individuarlo quando è in mare.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._72_tentacool)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._72_tentacoolshiny)));
        addPokemon(tentacool);
        Pokemon tentacruel = new Pokemon(73, "Tentacruel", "Acqua", "Veleno", 1.6F, 55.0F, "Corpochiaro\nMelma", "Copripioggia", "Medusa", "Meglio fare attenzione quando le sfere rosse che ha sulla testa brillano intensamente, perché vuol dire che sta per emettere ultrasuoni. Può allungare e accorciare a piacere i suoi 80 tentacoli. Li estende a mo’ di rete per catturare le prede, che trafigge con aculei velenosi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._73_tentacruel)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._73_tentacruelshiny)));
        addPokemon(tentacruel);
        Pokemon geodude = new Pokemon(74, "Geodude", "Roccia", "Terra", 0.4F, 20.0F, "Testadura\nVigore", "Sabbiavelo", "Roccia", "Lo si può trovare vicino ai sentieri di montagna. Bisogna fare attenzione a non calpestarlo per sbaglio, altrimenti diventa furioso.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._74_geodude)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._74_geodudeshiny)));
        addPokemon(geodude);
        Pokemon graveler = new Pokemon(75, "Graveler", "Roccia", "Terra", 1.0F, 105.0F, "Testadura\nVigore", "Sabbiavelo", "Roccia", "Spesso viene visto rotolare giù per i sentieri di montagna, travolgendo tutto ciò che incontra.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._75_graveler)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._75_gravelershiny)));
        addPokemon(graveler);
        Pokemon golem = new Pokemon(76, "Golem", "Roccia", "Terra", 1.4F, 300.0F, "Testadura\nVigore", "Sabbiavelo", "Megatone", "Subito dopo la muta il suo corpo è bianco e morbido, ma al contatto con l’aria si trasforma immediatamente in una dura corazza.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._76_golem)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._76_golemshiny)));
        addPokemon(golem);
        Pokemon ponyta = new Pokemon(77, "Ponyta", "Fuoco", null, 1.0F, 30.0F, "Fugafacile\nFuocardore", "Corpodifuoco", "Cavalfuoco", "Appena nato non è un buon corridore, ma col tempo irrobustisce le sue zampe rincorrendo i suoi simili. Una volta conquistata la fiducia di Ponyta, è possibile toccare la sua criniera infuocata senza scottarsi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._77_ponyta)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._77_ponytashiny)));
        addPokemon(ponyta);
        Pokemon rapidash = new Pokemon(78, "Rapidash", "Fuoco", null, 1.7F, 95.0F, "Fugafacile\nFuocardore", "Corpodifuoco", "Cavalfuoco", "Sfreccia nelle praterie a una velocità di 240 km/h, facendo sventolare la sua criniera ardente. L’esemplare più veloce diventa il capo del gruppo e ne decide velocità e destinazione.",  convertToByteArray(ContextCompat.getDrawable(context, R.drawable._78_rapidash)),  convertToByteArray(ContextCompat.getDrawable(context, R.drawable._78_rapidashshiny)));
        addPokemon(rapidash);
        Pokemon slowpoke = new Pokemon(79, "Slowpoke", "Acqua", "Psico", 1.2F, 36.0F, "Indifferenza\nMente locale", "Rigenergia", "Ronfone",  "Incredibilmente lento e tonto, ci mette cinque secondi a percepire il dolore dopo essere stato colpito. È sempre assorto, ma nessuno sa a cosa stia pensando. Si serve della coda per pescare.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._79_slowpoke)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._79_slowpokeshiny)));
        addPokemon(slowpoke);
        Pokemon slowbro = new Pokemon(80, "Slowbro", "Acqua", "Psico", 1.6F, 78.5F, "Indifferenza\nMente locale", "Rigenergia", "Paguro", "Uno Slowpoke che si trovava in mare in cerca di cibo fu morso da uno Shellder, evolvendosi in Slowbro. Se durante l’impeto di una lotta perde lo Shellder che gli morde la coda, torna a essere uno Slowpoke.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._80_slowbro)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._80_slowbroshiny)));
        addPokemon(slowbro);
        Pokemon magnemite = new Pokemon(81, "Magnemite", "Elettro", "Acciaio", 0.3F, 6.0F, "Vigore\nMagnetismo", "Ponderazione", "Calamita", "Le onde elettromagnetiche generate dagli elementi laterali neutralizzano la gravità permettendogli di levitare a mezz’aria.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._81_magnemite)),  convertToByteArray(ContextCompat.getDrawable(context, R.drawable._81_magnemiteshiny)));
        addPokemon(magnemite);
        Pokemon magneton = new Pokemon(82, "Magneton", "Elettro", "Acciaio", 1.0F, 60.0F, "Vigore\nMagnetismo", "Ponderazione", "Calamita", "Tre Magnemite sono uniti da una potente forza magnetica. Se ci si avvicina troppo, le orecchie fischiano. Si dice che i Magneton appaiano in gran numero quando ci sono molte macchie solari.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._82_magnetone)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._82_magnetoneshiny)));
        addPokemon(magneton);
        Pokemon farfetchd = new Pokemon(83, "Farfetch'd", "Normale", "Volante", 0.8F, 15.0F, "Sgaurdofermo\nForza interiore", "Agonismo", "Selvanatra", "Colpisce gli avversari con un gambo, che brandisce con l’ala come se fosse una spada. In caso di necessità, può anche mangiarselo. Affronta i nemici usando il gambo di una pianta, brandendolo secondo stili diversi in base alla scuola di lotta che segue.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._83_farfetchd)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._83_farfetchdshiny)));
        addPokemon(farfetchd);
        Pokemon doduo = new Pokemon(84, "Doduo", "Normale", "Volante", 1.4F, 39.2F, "Fugafacile\nSveglialampo", "Intricopiedi", "Biuccello", "Le cortissime ali non gli permettono di volare. Può tuttavia correre velocissimo grazie alle zampe ben sviluppate.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._84_doduo)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._84_doduoshiny)));
        addPokemon(doduo);
        Pokemon dodrio = new Pokemon(85, "Dodrio", "Normale", "Volante", 1.8F, 85.2F, "Fugafacile\nSveglialampo", "Intricopiedi", "Triuccello", "Un Doduo si evolve in Dodrio quando una delle due teste si divide. Lo si vede sfrecciare per le praterie a 60 km/h.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._85_dodrio)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._85_dodrioshiny)));
        addPokemon(dodrio);
        Pokemon seel = new Pokemon(86, "Seel", "Acqua", null, 1.1F, 90.0F, "Grassospesso\nIdratazione", "Corpogelo", "Otaria", "Adora gli ambienti gelidi. Nuota con piacere nell’acqua a temperature di circa -10 ºC.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._86_seel)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._86_seelshiny)));
        addPokemon(seel);
        Pokemon dewgong = new Pokemon(87, "Dewgong", "Acqua", "Ghiaccio", 1.7F, 120.0F, "Grassospesso\nIdratazione", "Corpogelo", "Otaria", "Il suo corpo è bianco come la neve. Non soffre il freddo e nuota senza problemi anche in acque gelide.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._87_dewgong)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._87_dewgongshiny)));
        addPokemon(dewgong);
        Pokemon grimer = new Pokemon(88, "Grimer", "Veleno", null, 0.9F, 30.0F, "Tanfo\nAntifurto", "Velentocco", "Melma", "Un Pokémon nato dalla melma. Predilige i posti sporchi perché gli permettono di aumentare la sua carica batterica. Quando due esemplari combinano i propri corpi, creano nuovi tipi di veleno.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._88_grimer)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._88_grimershiny)));
        addPokemon(grimer);
        Pokemon muk = new Pokemon(89, "Muk", "Veleno", null, 1.2F, 30.0F,  "Tanfo\nAntifurto", "Velentocco", "Melma", "Coperto di melma sudicia, è così tossico che persino le sue impronte sono velenose. Ha un odore estremamente sgradevole e il suo corpo è un concentrato di veleni. Non c’è pianta che rimanga in vita dopo il suo passaggio.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._89_muk)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._89_mukshiny)));
        addPokemon(muk);
        Pokemon shellder = new Pokemon(90, "Shellder", "Acqua", null, 0.3F, 4.0F, "Guscioscudo\nAbillegame", "Copricapo", "Bivalve", "La conchiglia esterna è più dura del diamante. L’interno, invece, è sorprendentemente morbido. Quando attanaglia il nemico con le due valve il molle interno rimane esposto, perciò usa questa tecnica solo come ultima risorsa.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._90_shellder)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._90_shelldershiny)));
        addPokemon(shellder);
        Pokemon cloyster = new Pokemon(91, "Cloyster", "Acqua", "Ghiaccio", 1.5F, 132.5F, "Guscioscudo\nAbillegame", "Copricapo", "Bivalve", "Ai Cloyster che vivono in mari con forti correnti crescono grandi aculei appuntiti sul guscio. Se attaccato, scaglia gli aculei di cui è dotato in rapida successione. Nessuno sa cosa si celi al suo interno.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._91_cloyster)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._91_cloystershiny)));
        addPokemon(cloyster);
        Pokemon gastly = new Pokemon(92, "Gastly", "Spettro", "Veleno", 1.3F, 0.1F, "Levitazione", null, "Gas", "Avvolge le prede nel corpo gassoso e le indebolisce lentamente facendo penetrare il veleno nella loro pelle.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._92_gastly)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._92_gastlyshiny)));  //Pierre Gastly
        addPokemon(gastly);
        Pokemon haunter = new Pokemon(93, "Haunter", "Spettro", "Veleno", 1.6F, 0.1F, "Levitazione", null, "Gas", "Adora stare in agguato nei luoghi bui e toccare le spalle delle vittime con le sue mani gassose. Il suo tocco causa brividi incontenibili.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._93_haunter)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._93_hauntershiny)));
        addPokemon(haunter);
        Pokemon gengar = new Pokemon(94, "Gengar", "Spettro", "Veleno", 1.5F, 40.5F, "Corpofunesto", null, "Ombra", "Per sottrarre energia alla vittima si nasconde nella sua ombra e attende in silenzio il momento opportuno. Di notte si cela nell’ombra delle persone per assorbirne il calore poco a poco, provocando loro tremiti irrefrenabili.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._94_gengar)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._94_gengarshiny)));
        addPokemon(gengar);
        Pokemon onix = new Pokemon(95, "Onix", "Roccia", "Terra", 8.8F, 210.0F, "Testadura\nVigore", "Sottilguscio", "Serpesasso", "Scava nel terreno a una velocità di 80 km/h assorbendo gli oggetti più duri per irrobustire il suo corpo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._95_onix)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._95_onixshiny)));
        addPokemon(onix);
        Pokemon drowzee = new Pokemon(96, "Drowzee", "Psico", null, 1.0F, 23.0F, "Insonnia\nPremonizione", "Forza interiore", "Ipnosi", "Quando fiuta con il suo grande naso, riesce a localizzare chi dorme e a capire che sogni sta facendo. Ricorda ogni singolo sogno che mangia. Mangia raramente i sogni degli adulti, perché quelli dei bambini sono più buoni.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._96_drowzee)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._96_drowzeeshiny)));
        addPokemon(drowzee);
        Pokemon hypno = new Pokemon(97, "Hypno", "Psico", null, 1.6F, 75.6F, "Insonnia\nPremonizione", "Forza interiore", "Ipnosi", "Quando cattura lo sguardo del nemico, usa una varietà di poteri psichici come, ad esempio, l’ipnosi. Ha sempre con sé un pendolo che agita a ritmo regolare. Fa assopire chiunque gli si avvicini.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._97_hypno)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._97_hypnoshiny)));
        addPokemon(hypno);
        Pokemon krabby = new Pokemon(98, "Krabby", "Acqua", null, 0.4F, 6.5F, "Guscioscudo\nIpertaglio", "Forzabruta", "Granchio", "Lo si può trovare vicino al mare. Le grandi chele, se si staccano, ricrescono. Se si sente in pericolo, emette bolle dalla bocca e se ne circonda per sembrare più grande.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._98_krabby)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._98_krabbyshiny)));
        addPokemon(krabby);
        Pokemon kingler = new Pokemon(99, "Kingler", "Acqua", null, 1.3F, 60.0F, "Guscioscudo\nIpertaglio", "Forzabruta", "Chela", "La chela più grande sprigiona una potenza di 10.000 CV. Le dimensioni gli rendono difficili gli spostamenti.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._99_kingler)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._99_kinglershiny)));
        addPokemon(kingler);
        Pokemon voltorb = new Pokemon(100, "Voltorb", "Elettro", null, 0.5F, 10.4F, "Statico\nAntisuono", "Scoppio", "Ball", "Rotola per spostarsi. Se il terreno è irregolare, può esplodere all’improvviso a causa dell’urto contro un dosso. Solitamente si aggira nelle centrali elettriche e luoghi simili. Molti lo confondono con una Poké Ball, finendo folgorati.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._00_voltorb)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._01_voltorbshiny)));
        addPokemon(voltorb);
        Pokemon electrode = new Pokemon(101, "Electrode", "Elettro", null, 1.2F, 66.6F, "Statico\nAntisuono", "Scoppio", "Ball", "L’energia Elettro che immagazzina lo fa andare sempre più veloce, ma lo fa anche esplodere più facilmente. Temuto da tutti, è conosciuto con il nomignolo di “Bomba Ball”.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._01_electrode)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._01_electrodeshiny)));
        addPokemon(electrode);
        Pokemon exeggcute = new Pokemon(102, "Exeggcute", "Erba", "Psico", 0.4F, 2.5F, "Clorofilla", "Coglibacche", "Uovo", "Somiglia a un mucchio di uova, ma è un Pokémon a tutti gli effetti. Pare che comunichi con i suoi simili telepaticamente. Se non sono presenti tutti e sei gli individui che lo compongono, non è tranquillo. Appena ne sparisce uno si prepara alla fuga.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._02_exeggcute)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._02_exeggcuteshiny)));
        addPokemon(exeggcute);
        Pokemon exeggutor = new Pokemon(103, "Exeggutor", "Erba", "Psico", 2.0F, 120.F, "Clorofilla", "Coglibacche", "Nocecocco", "Le sue tre teste ragionano in modo indipendente. Sembra che ciascuna pensi solo a sé. I poteri psichici che le sue tre teste scatenano all’unisono sono travolgenti. Quando il cielo si rannuvola, i suoi movimenti si fanno lenti.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._03_exeggutor)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._03_exeggutorshiny)));
        addPokemon(exeggutor);
        Pokemon cubone = new Pokemon(104, "Cubone", "Terra", null, 0.4F, 6.5F, "Testadura\nParafulmine", "Lottascudo", "Solitario", "Quando piange pensando alla madre defunta, i suoi lamenti riecheggiano tristemente all’interno del teschio che ha in testa. Indossa il teschio della madre defunta. A volte capita che pianga vedendola in sogno, ma a ogni lacrima versata diventa più forte.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._04_cubone)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._04_cuboneshiny)));
        addPokemon(cubone);
        Pokemon marowak = new Pokemon(105, "Marowak", "Terra", null, 1.0F, 45.0F, "Testadura\nParafulmine", "Lottascudo", "Guardaossi", "Si è evoluto dopo essersi temprato e aver superato il lutto. Usa l’osso come un’arma e lotta valorosamente contro i nemici. Evolvendosi è diventato tutt’uno con il teschio della madre che prima indossava e ha acquisito un temperamento violento.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._05_marowak)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._05_marowakshiny)));
        addPokemon(marowak);
        Pokemon hitmonlee = new Pokemon(106, "Hitmonlee", "Lotta", null, 1.5F, 49.8F, "Scioltezza\nTemerarietà", "Agiltecnica", "Tiracalci", "Questo straordinario Pokémon ha un grande equilibrio. Può tirare raffiche di calci da qualsiasi posizione. Può accorciare o allungare le gambe a piacere. In questo modo riesce a colpire con i calci anche gli avversari distanti.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._06_hitmonlee)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._06_hitmonleeshiny)));
        addPokemon(hitmonlee);
        Pokemon hitmonchan = new Pokemon(107, "Hitmonchan", "Lotta", null, 1.4F, 50.2F, "Sguardofermo\nFerropugno", "Forza interiore", "Tirapugni", "I suoi pugni fendono l’aria a una tale velocità che basta venire sfiorati per riportare una scottatura, tuttavia, dopo tre minuti di lotta deve riprendere fiato.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._07_hitmonchan)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._07_hitmonchanshiny)));
        addPokemon(hitmonchan);
        Pokemon lickitung = new Pokemon(108, "Lickitung", "Normale", null, 1.2F, 65.5F, "Indifferenza\nMente locale", "Antimeteo", "Linguaccia", "La sua saliva appiccicosa provoca un prurito terribile che non dà tregua se le parti leccate non vengono ripulite. Si nutre principalmente di Pokémon Coleottero. Paralizza le prede leccandole con la lunga lingua e le inghiotte in un boccone.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._08_lickitung)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._08_lickitungshiny)));
        addPokemon(lickitung);
        Pokemon koffing = new Pokemon(109, "Koffing", "Veleno", null, 0.6F, 1.0F, "Levitazione\nGas reagente", "Tanfo", "Velenuvola", "Il suo corpo è gonfio di gas velenosi. Va nelle discariche attratto dall’odore di rifiuti putrescenti. È ghiotto di aria inquinata. Si dice che in passato a Galar ci fossero molti più Koffing rispetto a ora.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._09_koffing)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._09_koffingshiny)));
        addPokemon(koffing);
        Pokemon weezing = new Pokemon(110, "Weezing", "Veleno", null, 1.2F, 9.5F, "Levitazione\nGas reagente", "Tanfo", "Velenuvola", "Si serve del suo duplice corpo per miscelare i gas. Sembra che in passato lo si potesse avvistare un po’ ovunque nella regione di Galar.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._10_weezing)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._10_weezingshiny)));
        addPokemon(weezing);
        Pokemon rhyhorn = new Pokemon(111, "Rhyhorn", "Terra", "Roccia", 1.0F, 115.0F, "Testadura\nParafulmine", "Temerarietà", "Punzoni", "È molto forte, benché non particolarmente brillante. Se si scaglia contro un grattacielo può raderlo al suolo. Non è in grado di ricordare più di una cosa alla volta. Quando parte alla carica ne dimentica subito il motivo.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._11_rhyhorn)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._11_rhyhornshiny)));
        addPokemon(rhyhorn);
        Pokemon rhydon = new Pokemon(112, "Rhydon", "Terra", "Roccia", 1.9F, 120.0F, "Testadura\nParafulmine", "Temerarietà", "Trapano", "Dopo l’evoluzione inizia a muoversi sulle zampe posteriori. Con il corno può perforare la roccia. La sua pelle, simile a un’armatura, lo protegge consentendogli di vivere nel magma a 2.000 ºC.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._12_rhydon)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._12_rhydonshiny)));
        addPokemon(rhydon);
        Pokemon chansey = new Pokemon(113, "Chansey", "Normale", null, 1.1F, 34.6F, "Alternacura\nLeggiadro", "Curacuore", "Uovo", "Un Pokémon altruista che depone uova molto nutrienti e le condivide con persone o Pokémon feriti. Cammina con prudenza per non rompere il suo uovo. Tuttavia, può fuggire con estrema rapidità.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._13_chansey)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._13_chanseyshiny)));
        addPokemon(chansey);
        Pokemon tangela = new Pokemon(114, "Tangela", "Erba", null, 1.0F, 35.0F, "Clorofilla\nFogliamanto", "Rigenergia", "Liana", "Non è ancora chiaro quale sia il vero aspetto celato dietro alle sue liane, che crescono all’infinito anche dopo essere state recise. Le sue liane emanano un odore peculiare. In alcune zone di Galar vengono usate come erbe aromatiche.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._14_tangela)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._14_tangelashiny)));
        addPokemon(tangela);
        Pokemon kangaskhan = new Pokemon(115, "Kangaskhan", "Normale", null, 2.2F, 80.0F, "Sveglialampo\nNervisaldi", "Forza interiore", "Genitore", "Ha un agile gioco di gambe, nonostante si porti appresso il cucciolo nel marsupio ventrale. Intimidisce gli avversari con jab velocissimi. Alcuni documenti parlano di Kangaskhan senza cuccioli che hanno allevato bambini umani dispersi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._15_kangaskhan)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._15_kangaskhanshiny)));
        addPokemon(kangaskhan);
        Pokemon horsea = new Pokemon(116, "Horsea", "Acqua", null, 0.4F, 8.0F, "Nuotovelox\nCecchino", "Umidità", "Drago", "Vive in mari dalle correnti tranquille. Quando viene attaccato spruzza inchiostro nero e ne approfitta per fuggire. Nuota come se danzasse nell’acqua creando mulinelli. Gioca con i suoi simili facendo a gara a chi ne crea di più grandi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._16_horsea)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._16_horseashiny)));
        addPokemon(horsea);
        Pokemon seadra = new Pokemon(117, "Seadra", "Acqua", null, 1.2F, 25.0F, "Velenopunte\nCecchino", "Umidità", "Drago", "Il maschio si occupa dei piccoli. Quando li alleva, il veleno degli aculei dorsali diventa denso e potente. Nonostante la misura ridotta, la sua bocca ha un potere aspirante tale da permettergli di risucchiare e inghiottire grosse prede all’istante.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._17_seadra)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._17_seadrashiny)));
        addPokemon(seadra);
        Pokemon goldeen = new Pokemon(118, "Goldeen", "Acqua", null, 0.6F, 15.0F, "Nuotovelox\nIdrovelo", "Parafulmine", "Pescerosso", "Le pinne su dorso, petto e coda ondeggiano con grazia. Perciò viene chiamato 'danzatore d’acqua'. Le pinne dorsali e pettorali sono sviluppate come potenti muscoli. Può raggiungere la velocità di 5 nodi.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._18_goldeen)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._18_goldeenshiny)));
        addPokemon(goldeen);
        Pokemon seaking = new Pokemon(119, "Seaking", "Acqua", null, 1.3F, 39.0F, "Nuotovelox\nIdrovelo", "Parafulmine", "Pescerosso", "In autunno ingrassa in previsione della stagione degli amori e assume colori sgargianti. Perfora con il suo corno le rocce sul letto dei fiumi, costruendo tane che proteggono le Uova dalla corrente.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._19_seaking)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._19_seakingshiny)));
        addPokemon(seaking);
        Pokemon staryu = new Pokemon(120, "Staryu", "Acqua", null, 0.8F, 34.5F, "Alternacura\nRisplendi", "Ponderazione", "Stella", "Alla fine dell’estate, sui litorali è possibile osservare gruppi di Staryu che emettono luce a un ritmo regolare. Viene mangiucchiato dai Pokémon acquatici, ma non ci dà peso, perché le parti danneggiate del suo corpo si rigenerano subito.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._20_staryu)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._20_staryushiny)));
        addPokemon(staryu);
        Pokemon starmie = new Pokemon(121, "Starmie", "Acqua", "Psico", 1.1F, 80.0F, "Alternacura\nRisplendi", "Ponderazione", "Misterioso", "Quando scatena i suoi poteri psichici, l’organo centrale, detto nucleo, brilla di sette colori diversi. Risucchia il plancton mentre nuota nel mare facendo ruotare il suo corpo ad alta velocità.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._21_starmie)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._21_starmieshiny)));
        addPokemon(starmie);
        Pokemon mrmime = new Pokemon(122, "Mr. Mime", "Psico", "Folletto", 1.3F, 54.0F, "Antisuono\nFiltro", "Tecnico", "Barriera", "È un esperto di mimica che, con i gesti, riesce a innalzare pareti che diventano reali. Con le onde che emette dalla punta delle dita solidifica l’aria e crea pareti invisibili che respingono gli attacchi più violenti.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._22_mrmime)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._22_mrmimeshiny)));
        addPokemon(mrmime);
        Pokemon scyther = new Pokemon(123, "Scyther", "Coleottero", "Volante", 1.5F, 56.0F, "Aiutinsetto\nTecnico", "Cuordeciso", "Mantide", "Si fa strada tra l’erba grazie alle lame affilate, muovendosi troppo velocemente per l’occhio umano. Le falci sulle sue zampe anteriori diventano sempre più affilate man mano che tranciano oggetti duri.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._23_scyther)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._23_scythershiny)));
        addPokemon(scyther);
        Pokemon jynx = new Pokemon(124, "Jynx", "Ghiaccio", "Psico", 1.4F, 40.6F, "Indifferenza\nPremonizione", "Pellearsa", "Umanoide", "In una certa zona di Galar, Jynx era temuto e venerato dagli abitanti, che lo chiamavano 'Regina dei ghiacci'. La maggior parte dei Jynx di Galar hanno una voce talmente incantevole e delicata da avere addirittura dei fan.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._24_jynx)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._24_jynxshiny)));
        addPokemon(jynx);
        Pokemon electabuzz = new Pokemon(125, "Electabuzz", "Elettro", null, 1.1F, 30.0F, "Statico", "Spiritovivo", "Elettrico", "Molte centrali elettriche utilizzano Pokémon di tipo Terra per difendersi dagli Electabuzz a caccia di elettricità. All’arrivo di un temporale questi Pokémon si raggruppano intorno agli alberi alti, aspettando immobili che cada un fulmine.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._25_electabuzz)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._25_electabuzzshiny)));
        addPokemon(electabuzz);
        Pokemon magmar = new Pokemon(126, "Magmar", "Fuoco", null, 1.3F, 44.5F, "Corpodifuoco", "Spiritovivo", "Sputafuoco", "Abbatte le sue prede con le fiamme, ma finisce per carbonizzarle accidentalmente, con suo grande rammarico. Il suo corpo è perennemente in fiamme. Viene considerato una delle cause degli incendi, e per questo è temuto.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._26_magmar)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._26_magmarshiny)));
        addPokemon(magmar);
        Pokemon pinsir = new Pokemon(127, "Pinsir", "Coleottero", null, 1.5F, 55.0F, "Ipertaglio\nRompiforma", "Arroganza", "Cervolante", "I Pinsir si giudicano a vicenda in base alle corna. Quelli con corna grosse e imponenti hanno più successo con gli esemplari dell’altro sesso. Afferra le prede con le corna e le trancia in due, oppure le lancia via con violenza.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._27_pinsir)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._27_pinsirshiny)));
        addPokemon(pinsir);
        Pokemon tauros = new Pokemon(128, "Tauros", "Normale", null, 1.4F, 88.4F, "Prepotenza\nGrancollera", "Forzabruta", "Torobrado", "Dopo aver puntato la preda, la carica a testa bassa. È noto per la sua indole violenta.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._28_tauros)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._28_taurosshiny)));
        addPokemon(tauros);
        Pokemon magikarp = new Pokemon(129, "Magikarp", "Acqua", null, 0.9F, 10.0F, "Nuotovelox", "Paura", "Pesce", "Un Pokémon debole e patetico. Nelle rare occasioni in cui spicca alti salti, non riesce comunque a raggiungere i due metri. Pare che anticamente fosse un tantino più forte. Oggi è così debole da fare quasi pena.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._29_magikarp)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._29_magikarpshiny)));
        addPokemon(magikarp);
        Pokemon gyarados = new Pokemon(130, "Gyarados", "Acqua", "Volante", 6.5F, 235.0F, "Prepotenza", "Arroganza", "Atroce", "Quando esce allo scoperto in preda alla furia, non si placa finché non ha raso al suolo ogni cosa. Quando nel mondo regnano i conflitti, appare dal nulla e mette a ferro e fuoco tutto ciò che incontra sul proprio cammino.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._30_gyarados)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._30_gyaradosshiny)));
        addPokemon(gyarados);
        Pokemon lapras = new Pokemon(131, "Lapras", "Acqua", "Ghiaccio", 2.5F, 220.0F, "Assorbacqua\nGuscioscudo", "Idratazione", "Trasporto", "Ama attraversare il mare con persone e Pokémon sul suo dorso. Capisce il linguaggio umano.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._31_lapras)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._31_laprasshiny)));
        addPokemon(lapras);
        Pokemon ditto = new Pokemon(132, "Ditto", "Normale", null, 0.3F, 4.0F, "Scioltezza", "Sosia", "Mutante", "Sa trasformarsi in maniera perfetta, ma se lo si fa ridere perde subito il travestimento. Può ricombinare a piacere la propria struttura cellulare per trasformarsi in altre forme di vita.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._32_ditto)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._32_dittoshiny)));
        addPokemon(ditto);
        Pokemon eevee = new Pokemon(133, "Eevee", "Normale", null, 0.3F, 6.5F, "Fugafacile\nAdattabilità", "Presagio", "Evoluzione", "La capacità di evolversi in diverse specie gli permette di adattarsi perfettamente a qualsiasi tipo di ambiente. A causa del suo codice genetico anomalo, può trasformarsi improvvisamente se esposto alle radiazioni emesse dalle pietre.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._33_eevee)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._33_eeveeshiny)));
        addPokemon(eevee);
        Pokemon vaporeon = new Pokemon(134, "Vaporeon", "Acqua", null, 1.0F, 29.0F, "Assorbacqua", "Idratazione", "Bollajet", "Vive vicino all’acqua. Sulla punta della coda ha una pinna, per questo alcuni lo scambiano per una sirena. La sua composizione cellulare è simile a quella delle molecole d’acqua, in cui si può dissolvere diventando invisibile.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._34_vaporeon)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._34_vaporeonshiny)));
        addPokemon(vaporeon);
        Pokemon jolteon = new Pokemon(135, "Jolteon", "Elettro", null, 0.8F, 24.5F, "Assorbivolt", "Piedisvelti", "Luminoso", "Concentra la debole carica elettrica emessa dalle sue cellule per lanciare potenti scariche. Quando si agita, usa l’elettricità per rizzare i peli e scagliarli in piccoli nugoli.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._35_jolteon)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._35_jolteonshiny)));
        addPokemon(jolteon);
        Pokemon flareon = new Pokemon(136, "Flareon", "Fuoco", null, 0.9F, 25.0F, "Fuocardore", "Dentistretti", "Fiamma", "L’aria che inspira si infiamma passando per la sacca ardente che ha nel corpo, per poi essere espirata alla temperatura di 1.700 ºC. Arruffa il folto pelo intorno al collo per raffreddare il corpo, che può raggiungere i 900 ºC.", convertToByteArray(ContextCompat.getDrawable(context, R.drawable._36_flareon)), convertToByteArray(ContextCompat.getDrawable(context, R.drawable._36_flareonshiny)));
        addPokemon(flareon);
        



    }


    public Cursor selectAll(){
        String query = "SELECT DISTINCT * FROM " + TABELLA_POKEMON + ";";
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

    public Cursor selectByName(String nome){
        String query = "SELECT * FROM " + TABELLA_POKEMON + " WHERE " + COLONNA_NOME + " = " + "'" + nome + "'" + " COLLATE NOCASE" + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor selectAllFromKanto(){
        String query = "SELECT * FROM " + TABELLA_POKEMON + " JOIN " + TABELLA_APPARTENENZA + " ON " + COLONNA_ID + " = " + COLONNA_ID_REPLICA + " WHERE " + COLONNA_NOME_REGIONE + " = Kanto;";
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
