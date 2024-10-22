package br.com.cellsgroup.intercessao;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

import adapters.AdapterListViewIntercessao;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.intercessao.Intercessao;

import static br.com.cellsgroup.home.HomeActivity.uidIgreja;

@SuppressWarnings( "UnnecessaryLocalVariable" )
public class IntercessaoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DatabaseReference Intercessao;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private FirebaseDatabase firebaseDatabase;
    private final int limitebusca = 500;
    private final ArrayList< Intercessao > inter = new ArrayList <>( );
    private String mUid;
    private RecyclerView recyclerView;
    private AdapterListViewIntercessao mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_intercessao );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        iniciaComponentes();
        inicializarFirebase();

      //  recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        layoutManager.getWidth ();
        layoutManager.getHeight ();
        recyclerView.setLayoutManager(layoutManager);

        readIntercessao();
        clickListaIntercessao();


        FloatingActionButton fabIntercessao = findViewById( R.id.fabIntercessao);
        fabIntercessao.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntercessaoActivity.this, AddIntercessaoActivity.class);
                startActivity(intent);
                finish();
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_activityIntercessao );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
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
        recyclerView = findViewById(R.id.recycleViewIntercessao );
        recyclerView.setLongClickable( true );
    }

    private void clickListaIntercessao(){

    }

    private void readIntercessao() {
        novaRef = Intercessao.child( "churchs/" + uidIgreja + "/Intercession/");
        Query query = novaRef.orderByChild( "data" ).limitToLast( limitebusca );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inter.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Intercessao intercessao = ds.getValue( Intercessao.class );
                        inter.add ( intercessao );
                        //todo Implemetar userId para usar no filtro
                }
                List<Intercessao> intercessoes = inter;

                mAdapter = new AdapterListViewIntercessao(intercessoes );

                // Não implementado no viewholder, por julgar desnecessário
                mAdapter.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick ( View v ) {
                        Toast.makeText(IntercessaoActivity.this,"Clicado", Toast.LENGTH_LONG).show();
                    }
                } );
                // Implementado no viewholder
                mAdapter.setOnLongClickListener ( new View.OnLongClickListener ( ) {
                    @Override
                    public boolean onLongClick ( View v ) {
                        Toast.makeText(IntercessaoActivity.this,"Clicado", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } );
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
    }

    @Override
    public void onBackPressed() {
        finish();
         DrawerLayout drawer = findViewById( R.id.drawer_activityIntercessao);
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save_edit_delete , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_delete){
            deleteViewselected(mUid);
        }

        return super.onOptionsItemSelected( item );
    }
    
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
            finish();
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( IntercessaoActivity.this, CelulasActivity.class );
            startActivity( celulas );
            finish();
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( IntercessaoActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
            finish();
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( IntercessaoActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
            finish();
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( IntercessaoActivity.this, AgendaActivity.class );
            startActivity( agenda );
            finish();
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( IntercessaoActivity.this, ContatoActivity.class );
            startActivity( contato );
            finish();
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( IntercessaoActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
            finish();
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( IntercessaoActivity.this, EnviarActivity.class );
            startActivity( Enviar );
            finish();
        }

        DrawerLayout drawer = findViewById( R.id.drawer_activityIntercessao);
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void deleteViewselected(String uid) {
        novaRef = Intercessao.child( "Igrejas/" + uidIgreja + "/Intercessao" );
        novaRef.child( uid ).removeValue();
        Toast.makeText(this,"Apagada com sucesso", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
