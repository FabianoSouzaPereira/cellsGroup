package br.com.ieqcelulas;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import java.util.Date;
import login.LoginActivity;
import pessoas.Usuario;

@SuppressWarnings("ALL")
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static FirebaseUser UI;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static boolean Logado = false;
    public static String tag = "0";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String DataTime;
    public String DataT;
    public String status = "1";
    public static String igreja = "IEQSacodosLimões";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        splashScreean();

        Toolbar toolbar = findViewById( R.id.toolbar );
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar( toolbar );
        inicializarFirebase();
        addDataHora();



        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        LoginActivity.updateUI(currentUser);
    }

    @Override
    protected void onResume() {
         super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    public void onBackPressed() {
     AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder  = builder.setMessage( "Deseja encerrar o aplicativo mesmo ?" );
            builder.setTitle( "Encerrando o aplicativo..." )
                    .setCancelable( false )
                    .setNegativeButton( "cancelado", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                System.exit(0);
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    });

            AlertDialog alertDialog = builder . create () ;
            alertDialog.show();

    }

    private void splashScreean() {
        if (tag == "0") {
            Intent i = new Intent( HomeActivity.this, Activity_splash_screen.class );
            startActivity( i );
            finish();
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(HomeActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        DataTime = data + " "+ hora;
        DataT = data;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
/*            Intent intent = new Intent( HomeActivity.this,Configuracao.class );
            startActivity( intent );*/
        }
        if (id == R.id.action_Login) {
            Intent login = new Intent( HomeActivity.this, LoginActivity.class);
            startActivity( login );
            finish();
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( HomeActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( HomeActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( HomeActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( HomeActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( HomeActivity.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( HomeActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( HomeActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( HomeActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    /**  Click do card celula
     *
     */
    public void cardcellClick(View view) throws Exception{
        Intent celulas = new Intent(HomeActivity.this,CelulasActivity.class);
        startActivity( celulas );
    }


    public void cardcomunicacaoClick(View view) throws Exception {
        Intent comunidados = new Intent(HomeActivity.this,ComunicadosActivity.class);
        startActivity( comunidados );
    }


    public void cardintercessaoClick(View view) throws Exception {
        Intent intercessao = new Intent( HomeActivity.this,IntercessaoActivity.class );
        startActivity( intercessao );
    }


    public void cardagendaClick(View view) throws Exception {
        Intent agenda = new Intent( HomeActivity.this,AgendaActivity.class );
        startActivity( agenda );
    }


    public void cardvisaoClick(View view) throws Exception{
        Intent visao = new Intent( HomeActivity.this,VisaoActivity.class );
        startActivity( visao );
    }


    public void cardcontatoClick(View view) throws Exception {
        Intent contato = new Intent( HomeActivity.this,ContatoActivity.class );
        startActivity( contato );
    }

/*
    public void cardcadastroemailClick(View view) throws Exception {
        Intent cadastroemail = new Intent(HomeActivity.this,CadastroemailActivity.class);
        startActivity( cadastroemail );
    }

    public void cardcadastroemailClick(MenuItem item) {
        Intent cadastroemail = new Intent(HomeActivity.this,CadastroemailActivity.class);
        startActivity( cadastroemail );
    }
*/
    public void cardrelatorioClick(View view) {
        Intent relatorio = new Intent( HomeActivity.this, RelatorioActivityView.class );
        startActivity( relatorio );
    }



    /**
     * @return showaviso
     */
/*    public boolean isShowaviso() {
        return showaviso;
    }*/



    /**
     * @return firebaseDatabase
     */
    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    /**
     * @return databaseReference
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

}
