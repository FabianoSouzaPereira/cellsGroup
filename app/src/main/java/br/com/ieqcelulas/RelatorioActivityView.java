package br.com.ieqcelulas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import login.LoginActivity;
import relatorios.Relatorio;

import static br.com.ieqcelulas.HomeActivity.igreja;
import static login.LoginActivity.updateUI;


public class RelatorioActivityView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef4;
    private DatabaseReference novarefPesq;
    private ArrayList<String> rel = new ArrayList<String>();
    private ArrayAdapter<String> ArrayAdapterRelatorio;
    private ListView relatorio;
    SearchView searchView;
    public String DataTime;
    public String DataT;
    private int limitebusca = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_relatorio_view );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializarFirebase();
        inicializarComponents();

        readRelOnlyActive();
        clickListaRelatorio();
        searchView = findViewById( R.id.searchViews );

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayAdapterRelatorio.getFilter().filter(newText);
                return false;
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }


    private void inicializarComponents() {
        relatorio = findViewById( R.id.listRelatorio );

    }

    private void clickListaRelatorio() {
        relatorio.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Id = (int)parent.getAdapter().getItemId(position);
                String relatorio = ArrayAdapterRelatorio.getItem( Id );
                Intent intent = new Intent(RelatorioActivityView.this, ReadRelatorioActivity.class);
                intent.putExtra("Relatorio", String.valueOf( relatorio) );
                //  intent.putExtra("uid", uid);
                startActivity(intent);
            }
        } );
    }


    private void readRelOnlyActive() {
        novaRef4 = databaseReference.child("Igrejas/" + igreja + "/Relatorios" );
        Query query = novaRef4.orderByChild( "datahora" ).limitToFirst(limitebusca);
        query.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rel.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    for(DataSnapshot dados : ds.getChildren()) {
                        Relatorio r = dados.getValue( Relatorio.class );
                        String relatorio = r.getCelula();
                        String datahora = r.getDatahora();
                        rel.add( relatorio +": "+ datahora);
                    }
                }
                ArrayAdapterRelatorio = new ArrayAdapter<String>(RelatorioActivityView.this,android.R.layout.simple_selectable_list_item, rel );
                relatorio.setAdapter( ArrayAdapterRelatorio );
                ArrayAdapterRelatorio.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        } );
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(RelatorioActivityView.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void pesquisarRelatorios(){
        String datainicial = "";
        String datafinal = "";

        novarefPesq = databaseReference.child("Igrejas/" + igreja + "/Relatorios" );
        Query querypesq = novarefPesq.orderByChild( "datahora" ).startAt( datainicial ).endAt( datafinal );
        querypesq.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rel.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    for(DataSnapshot dados : ds.getChildren()) {
                        Relatorio r = dados.getValue( Relatorio.class );
                        String relatorio = r.getCelula();
                        String datahora = r.getDatahora();
                        rel.add( relatorio + ": " + datahora );
                    }
                }

                 ArrayAdapterRelatorio = new ArrayAdapter<String>(RelatorioActivityView.this,android.R.layout.simple_selectable_list_item, rel );
                 relatorio.setAdapter( ArrayAdapterRelatorio );
                 ArrayAdapterRelatorio.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.relatorio_activity_view, menu );
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:
                Intent config = new Intent( RelatorioActivityView.this,Configuracao.class );
                startActivity( config );
                return true;
            case R.id.action_addIgreja:
                Intent addigreja = new Intent( RelatorioActivityView.this,AddIgrejaActivity.class );
                addigreja.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                addigreja.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity( addigreja );
                return true;
            case R.id.action_addUsuario:
                Intent addusuario = new Intent( RelatorioActivityView.this,AddUsuarioActivity.class );
                addusuario.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                addusuario.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity( addusuario );
                return true;
            case R.id.action_Login:
                Intent login = new Intent( RelatorioActivityView.this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity( login );
                return true;
            case R.id.action_Logout:
                FirebaseAuth.getInstance().signOut();
                updateUI(null);
                Toast.makeText(this,getString( R.string.Logout_sucesso), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( RelatorioActivityView.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( RelatorioActivityView.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( RelatorioActivityView.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( RelatorioActivityView.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( RelatorioActivityView.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( RelatorioActivityView.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( RelatorioActivityView.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( RelatorioActivityView.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {

        try {
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
            DataTime = data + " "+ hora;
            DataT = data;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
