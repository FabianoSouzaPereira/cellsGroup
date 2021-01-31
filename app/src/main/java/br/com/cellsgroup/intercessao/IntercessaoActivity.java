package br.com.cellsgroup.intercessao;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.MenuItem;

import android.view.Menu;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewIntercessao;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.VisaoActivity;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.Intercessao;

import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;

public class IntercessaoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterListViewIntercessao.OnIntercessaoListener {
    private static final String TAG = "ClickLista";
    private DatabaseReference Intercessao;
    private DatabaseReference novaRef;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final int limitebusca = 500;
    private final ArrayList< Intercessao > inter = new ArrayList<Intercessao>( );
    private String mUid;
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

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
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
        recyclerView = findViewById(R.id.recycleViewAgenda );
        recyclerView.setLongClickable( true );
    }

    private void clickListaIntercessao(){

    }

    private void readIntercessao() {
        novaRef = Intercessao.child( "churchs/" + uidIgreja);
        Query query = novaRef.child("/Intercession").orderByChild( "data" ).limitToFirst( limitebusca );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inter.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Intercessao intercessao = ds.getValue( Intercessao.class );
                    inter.add( intercessao);
                }
                List<Intercessao> intercessoes = inter;

                mAdapter = new AdapterListViewIntercessao(intercessoes,IntercessaoActivity.this,IntercessaoActivity.this );
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Msg","Erro readIntercessão");
            }
        } );
    }

    @Override
    public void onBackPressed() {
        IntercessaoActivity.this.finish();
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
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( IntercessaoActivity.this, VisaoActivity.class );
            startActivity( visao );
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

   public void onIntercessaoClick(int position, String uid){
       Log.d( TAG, "" + position);
       mUid = uid;

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
