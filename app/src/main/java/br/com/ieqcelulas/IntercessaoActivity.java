package br.com.ieqcelulas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewIntercessao;

import static br.com.ieqcelulas.HomeActivity.igreja;

public class IntercessaoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference Intercessao;
    private DatabaseReference novaRef;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //private ListView  listaIntercessao;
    private int limitebusca = 500;
    private ArrayList<Intercessao> inter = new ArrayList<Intercessao>( );
  //  private ArrayAdapter<String> arrayAdapterIntercessao;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_intercessao );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        iniciaComponentes();
        inicializarFirebase();

        
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        readIntercessao();



        FloatingActionButton fabIntercessao = findViewById( R.id.fabIntercessao);
        fabIntercessao.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntercessaoActivity.this, AddIntercessaoActivity.class);
                startActivity(intent);
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(IntercessaoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Intercessao = FirebaseDatabase.getInstance().getReference();
    }

    private void iniciaComponentes() {
        recyclerView = findViewById(R.id.recycleView);

    }

    private void clickListaIntercessao(){

    }

    private void readIntercessao() {
        novaRef = Intercessao.child( "Igrejas/" + igreja );
        Query query = novaRef.child("Intercessao").orderByChild( "data" ).limitToFirst( limitebusca );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inter.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Intercessao intercessao = ds.getValue( Intercessao.class );
                    inter.add( intercessao);
                }
                List<Intercessao> intercessoes = inter;

                // specify an adapter (see also next example)
                mAdapter = new AdapterListViewIntercessao(intercessoes,IntercessaoActivity.this );
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
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
        getMenuInflater().inflate( R.menu.intercessao, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_delete_intercessao){

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
            Intent celulas = new Intent( IntercessaoActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( IntercessaoActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( IntercessaoActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( IntercessaoActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( IntercessaoActivity.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( IntercessaoActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( IntercessaoActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( IntercessaoActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
