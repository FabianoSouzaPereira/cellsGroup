package br.com.cellsgroup.contato;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import android.view.Menu;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewContato;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.Configuracao;
import br.com.cellsgroup.Igreja.IgrejasCriadasActivity;
import br.com.cellsgroup.Igreja.addIgrejaActivity;
import br.com.cellsgroup.SobreActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;

import br.com.cellsgroup.leader.LeaderActivity;
import br.com.cellsgroup.models.pessoas.Leader;
import br.com.cellsgroup.relatorios.RelatorioActivityView;

import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremailAuth;
import static br.com.cellsgroup.home.HomeActivity.group;
import static br.com.cellsgroup.models.login.LoginActivity.updateUI;


public class ContatoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , AdapterListViewContato.OnContatoListener{
    private static final String TAG = "TAG";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private final int limitebusca = 200;

    private RecyclerView recyclerView;
    private final ArrayList < Leader > arrayLeader = new ArrayList < Leader > ();
    private AdapterListViewContato mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Query querycontato;
    private ValueEventListener queryContatoListener;
    private String uid;
    private String nome;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_contato );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        initcomponents();
        inicializarFirebase();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);
        final String ui = UI.getUid ();

        novaRef = databaseReference.child( "leaders/");
        querycontato = novaRef.orderByChild( "userId").startAt(ui).limitToLast(limitebusca);
        queryContatoListener =  new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid = "";
                int count=0;
                arrayLeader.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()) {
                    try {
                        Leader u = dados.getValue (Leader.class);
                        if(u.getUserId ().equals (ui)) {
                            count=1;
                            arrayLeader.add ( u );
                        }else{
                            if(count < 1) {
                                count=2;
                                Leader a = new Leader ( );
                                a.setNome ( "Lista de Lideres está vazia" );
                                arrayLeader.add ( a );
                            }
                        }
                    } catch ( Exception e ) {
                        e.printStackTrace ( );
                    }
                }
                List < Leader > leaders = arrayLeader;

                mAdapter = new AdapterListViewContato(leaders, ContatoActivity.this, ContatoActivity.this );
                recyclerView.setAdapter( mAdapter);
                mAdapter.notifyDataSetChanged();
                hiddShowMessage();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } ;

        querycontato.addValueEventListener (queryContatoListener );

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            }
        } );
        DrawerLayout drawer = findViewById( R.id.drawer_activity_contato);
        NavigationView navigationView = findViewById( R.id.nav_view_contato );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );

        View headerView = navigationView.getHeaderView(0);
        nhTitle = ( TextView ) headerView.findViewById (R.id.nhTitleContato);
        nhName = (TextView) headerView.findViewById (R.id.nhNameContato);
        nhEmail = (TextView) headerView.findViewById (R.id.nhEmailContato);
        nhEmail.setText (useremailAuth);
        nhTitle.setText (group);
        nhName.setText(igreja);
    }

    // Mostra memsagem se lista vir vazia
    private void hiddShowMessage() {
        ImageView image = findViewById (R.id.imageViewContato);
        CardView carviewContato = findViewById (R.id.carviewContato );
        if(arrayLeader.size() == 0){
            recyclerView.setVisibility (View.GONE);
            carviewContato.setVisibility (View.VISIBLE);
        }else{
            carviewContato.setVisibility (View.GONE);
            recyclerView.setVisibility (View.VISIBLE);
        }

    }
        private void initcomponents ( ) {
        recyclerView = findViewById( R.id.recyclerview_contato );
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp( ContatoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }


    @Override
    public void onBackPressed() {
        ContatoActivity.this.finish();
        Intent home = new Intent(ContatoActivity.this, HomeActivity.class);
        startActivity(home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            Intent config = new Intent ( ContatoActivity.this , Configuracao.class );
            startActivity ( config );
            return true;
        } else if ( itemId == R.id.action_addIgreja ) {
            Intent addigreja = new Intent ( ContatoActivity.this  , addIgrejaActivity.class );
            startActivity ( addigreja );
            return true;
        } else if ( itemId == R.id.action_readIgreja ) {
            Intent readigreja = new Intent ( ContatoActivity.this  , IgrejasCriadasActivity.class );
            startActivity ( readigreja );
            return true;
        }else if ( itemId == R.id.action_lideres) {
            Intent addlideres = new Intent ( ContatoActivity.this , LeaderActivity.class );
            startActivity ( addlideres);
            return true;
        }else if ( itemId == R.id.action_Sobre) {
            Intent sobre= new Intent ( ContatoActivity.this  , SobreActivity.class );
            startActivity ( sobre);
            return true;
        }else if ( itemId == R.id.action_Sair ) {
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( ContatoActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( ContatoActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( ContatoActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( ContatoActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view_leader) {
            Intent agenda = new Intent( ContatoActivity.this, LeaderActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( ContatoActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_realatorio) {
            Intent relatorio = new Intent( ContatoActivity.this, RelatorioActivityView.class );
            startActivity( relatorio );

        }  else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( ContatoActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_activity_contato);
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    protected void onStart ( ) {
        super.onStart ( );
    }

    @Override
    protected void onStop ( ) {
        querycontato.removeEventListener (queryContatoListener );
        super.onStop ( );
    }

    @Override
    protected void onResume ( ) {
        super.onResume ( );
    }

    @Override
    protected void onRestart ( ) {
        super.onRestart ( );
    }

    @Override
    protected void onPause ( ) {
        super.onPause ( );
    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }

    @Override
    public void onContatoClick ( int position , String key ) {

    }
}
