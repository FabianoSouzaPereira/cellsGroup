package br.com.cellsgroup.leader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewLeader;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.Configuracao;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.Igreja.IgrejasCriadasActivity;
import br.com.cellsgroup.Igreja.addIgrejaActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.models.login.LoginActivity;
import br.com.cellsgroup.models.pessoas.Leader;
import br.com.cellsgroup.relatorios.RelatorioActivityView;

import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.models.login.LoginActivity.updateUI;


public class LeaderActivity extends AppCompatActivity implements Serializable ,NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "TAG";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private final Boolean listaLider = true;

    private RecyclerView recyclerView;
    private final ArrayList < Leader > arrayLeader = new ArrayList < Leader > ();
    private AdapterListViewLeader mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private final boolean mItemPressed = false;
    private final boolean itemReturned = false;

    private final int limitebusca = 200;
    private Query query;
    private ValueEventListener queryListener;
    private String uid;
    private String nome;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_leader );
        Toolbar toolbar = findViewById( R.id.toolbarleader );
        setSupportActionBar( toolbar );

        iniciaComponentes();
        inicializarFirebase();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);

        readOnlyActive();

        FloatingActionButton fab = findViewById( R.id.fabLeader );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCelula = new Intent(LeaderActivity.this, AddLeaderActivity.class);
                startActivity( addCelula );
                finish();
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_activity_leader);
        NavigationView navigationView = findViewById( R.id.nav_view_leader);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );

    }

    private void iniciaComponentes ( ) {
        recyclerView = findViewById( R.id.listviewLeader );
        recyclerView.setLongClickable(true);
    }

    private void readOnlyActive() {
        final String ui = UI.getUid ();
        novaRef = databaseReference.child( "leaders/");
        query = novaRef.orderByChild( "userId").startAt(ui).endAt(ui).limitToLast(limitebusca);
        queryListener =  new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid = "";
                arrayLeader.clear();
                  for(DataSnapshot dados : dataSnapshot.getChildren()) {
                      try {
                          Leader u = dados.getValue (Leader.class);
                          arrayLeader.add(u);
                      } catch ( Exception e ) {
                          e.printStackTrace ( );
                      }
                  }

                List < Leader > leaders = arrayLeader;

                mAdapter = new AdapterListViewLeader ( leaders );
                mAdapter.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick ( View v ) {
                        uid = arrayLeader.get(recyclerView.getChildAdapterPosition (v)).getUid ();
                        nome = arrayLeader.get(recyclerView.getChildAdapterPosition (v)).getNome ();
                        if (mItemPressed) {
                            // Multi-item swipes not handled
                            return;
                        }
                        Intent intent = new Intent( LeaderActivity.this, ReadLeaderActivity.class );
                        intent.putExtra("uid", String.valueOf( uid) );
                        intent.putExtra("nome", String.valueOf( nome ) );
                        startActivity(intent);
                        finish();
                    }
                } );

                mAdapter.setOnLongClickListener ( new View.OnLongClickListener ( ) {
                    @Override
                    public boolean onLongClick ( View v ) {
                        // Implementar se houver necesidade
                        return false;
                    }
                } );

                recyclerView.setAdapter( mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,"Erro Database"+ databaseError.toException() );
            }
        } ;

        query.addValueEventListener (queryListener );


    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home, menu );
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu ( Menu menu ) {
        MenuItem addIgreja = menu.findItem(R.id.action_addIgreja);
        MenuItem igreja = menu.findItem(R.id.action_readIgreja);
        if( uidIgreja != null && !uidIgreja.equals ( "" ) ) {
            addIgreja.setVisible ( false );
            igreja.setVisible (true );
        }else{
            addIgreja.setVisible ( true );
            igreja.setVisible (false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId ( );
        if ( itemId == R.id.action_settings ) {
            Intent config = new Intent ( LeaderActivity.this , Configuracao.class );
            startActivity ( config );
            return true;
        } else if ( itemId == R.id.action_addIgreja ) {
            Intent addigreja = new Intent ( LeaderActivity.this  , addIgrejaActivity.class );
            startActivity ( addigreja );
            return true;
        } else if ( itemId == R.id.action_readIgreja ) {
            Intent readigreja = new Intent ( LeaderActivity.this , IgrejasCriadasActivity.class );
            startActivity ( readigreja );
            return true;
        }else if ( itemId == R.id.action_Usuario ) {
            Intent addusuario = new Intent ( LeaderActivity.this , LeaderActivity.class );
            startActivity ( addusuario );
            return true;
        }else if ( itemId == R.id.action_addUsuario ) {
            Intent addusuario = new Intent ( LeaderActivity.this , AddLeaderActivity.class );
            startActivity ( addusuario );
            return true;
        } else if ( itemId == R.id.action_Login ) {
            Intent login = new Intent ( LeaderActivity.this , LoginActivity.class );
            startActivity ( login );
            return true;
        } else if ( itemId == R.id.action_Sair ) {
            finishAffinity ();
            return true;
        } else if ( itemId == R.id.action_Logout ) {
            FirebaseAuth.getInstance ( ).signOut ( );
            updateUI ( null );
            Toast.makeText ( this , getString ( R.string.Logout_sucesso ) , Toast.LENGTH_LONG ).show ( );
            finish();
            return true;
        }
        return super.onOptionsItemSelected ( item );

    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp( LeaderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, ReadLeaderActivity.class );
            startActivity( home );

        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( LeaderActivity.this, CelulasActivity.class );
            celulas.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity( celulas );

        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( LeaderActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );

        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( LeaderActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );

        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( LeaderActivity.this, AgendaActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_realatorio) {
            Intent relatorio = new Intent( LeaderActivity.this, RelatorioActivityView.class );
            startActivity( relatorio );

        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( LeaderActivity.this, ContatoActivity.class );
            startActivity( contato );

        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( LeaderActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );

        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( LeaderActivity.this, EnviarActivity.class );
            startActivity( Enviar );

        }

        DrawerLayout drawer = findViewById( R.id.drawer_activity_leader);
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }


    @Override
    public void onBackPressed() {
        LeaderActivity.this.finish();
    }

    @Override
    protected void onResume ( ) {
        super.onResume ( );
    }

    @Override
    protected void onStart ( ) {
        super.onStart ( );
    }

    @Override
    protected void onStop ( ) {
        query.removeEventListener (queryListener);
        super.onStop ( );
    }

    @Override
    protected void onRestart ( ) {
        super.onRestart ( );
    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }
}