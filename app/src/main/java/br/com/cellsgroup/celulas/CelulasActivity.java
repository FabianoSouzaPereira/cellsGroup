package br.com.cellsgroup.celulas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import br.com.cellsgroup.Igreja.IgrejasCriadasActivity;
import br.com.cellsgroup.relatorios.AddRelatorioActivity;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.Configuracao;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.Igreja.addIgrejaActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.VisaoActivity;
import br.com.cellsgroup.models.celulas.Celula;
import br.com.cellsgroup.models.login.LoginActivity;
import br.com.cellsgroup.leader.AddLeaderActivity;
import br.com.cellsgroup.leader.LeaderActivity;

import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.group;
import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremailAuth;
import static br.com.cellsgroup.models.login.LoginActivity.updateUI;


public final class CelulasActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private static final String TAG = "ERRO ! ";
    public  String uid;
    public String rede;
    private ListView celulaList;
    private final ArrayList<String> cels = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterCelula;
    private final int limitebusca = 500;
    Query query;
    ValueEventListener queryListener;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_celulas);
        Toolbar toolbar = findViewById( R.id.toolbarCelulas );
        setSupportActionBar( toolbar );
        iniciaComponentes();
        inicializarFirebase();

        readOnlyActive();
        clickLista();
        
        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCelula = new Intent(CelulasActivity.this, AddCelulaActivity.class);
                startActivity( addCelula );
                finish();
            }
        } );
        DrawerLayout drawer = findViewById ( R.id.drawer_activityCelulas );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        NavigationView navigationView = findViewById( R.id.nav_view_celula );
        navigationView.setNavigationItemSelectedListener( this );

        View headerView = navigationView.getHeaderView(0);
        nhTitle = headerView.findViewById (R.id.nhTitle_celula);
        nhName = headerView.findViewById (R.id.nhName_celula);
        nhEmail = headerView.findViewById (R.id.nhEmail_celula);
        nhEmail.setText (useremailAuth);
        nhTitle.setText (group);
        nhName.setText(igreja);
    }

    private void readOnlyActive() {
        final String ui = UI.getUid ();
        novaRef = databaseReference.child( "churchs/" + uidIgreja + "/cells/");
        query = novaRef.orderByChild ("celula").limitToLast (limitebusca);
         queryListener =  new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cels.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    for(DataSnapshot dados : ds.getChildren()) {
                        Celula c = dados.getValue( Celula.class );
                        if ( c != null ) {
                            if( c.getUserId ( ).equals ( ui ) ) {
                                String celula = c.getCelula ( );
                                cels.add ( celula );
                            }
                        }
                    }
                }
                arrayAdapterCelula = new ArrayAdapter<String>(CelulasActivity.this,android.R.layout.simple_selectable_list_item, cels );
                celulaList.setAdapter( arrayAdapterCelula );
                arrayAdapterCelula.notifyDataSetChanged();
                hiddShowMessage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,"Erro Database"+ databaseError.toException() );
            }
        } ;

        query.addValueEventListener (queryListener );

    }


    // Mostra memsagem se lista vir vazia
    private void hiddShowMessage(){

        CardView cardView = findViewById (R.id.cardviewCells);
        if(cels.size() == 0){
            celulaList.setVisibility (View.GONE);
            cardView.setVisibility (View.VISIBLE);
        }else{
            cardView.setVisibility (View.GONE);
            celulaList.setVisibility (View.VISIBLE);
        }
    }

    private void clickLista(){
        celulaList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Id = (int)parent.getAdapter().getItemId(position);
                String celula = arrayAdapterCelula.getItem( Id );
                Intent intent = new Intent(CelulasActivity.this, ReadCelulaActivity.class);
                intent.putExtra("Celula", String.valueOf( celula ) );
                startActivity(intent);
            }
        } );

        celulaList.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(CelulasActivity.this, "long clicked pos: " + position, Toast.LENGTH_LONG).show();
                int Id = (int)parent.getAdapter().getItemId(position);
                String celula = arrayAdapterCelula.getItem( Id );
                Intent intent = new Intent(CelulasActivity.this, AddRelatorioActivity.class);
                intent.putExtra("Celula", String.valueOf( celula ) );

                startActivity(intent);
                return true;
            }
        } );
    }

    private void iniciaComponentes() {
        celulaList = findViewById( R.id.listViewCelula );
        celulaList.setLongClickable(true);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CelulasActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @Override
    public void onBackPressed() {
        CelulasActivity.this.finish();
        Intent home = new Intent(CelulasActivity.this, HomeActivity.class);
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
            Intent config = new Intent ( CelulasActivity.this , Configuracao.class );
            startActivity ( config );
            return true;
        } else if ( itemId == R.id.action_addIgreja ) {
            Intent addigreja = new Intent ( CelulasActivity.this , addIgrejaActivity.class );
            startActivity ( addigreja );
            return true;
        } else if ( itemId == R.id.action_readIgreja ) {
            Intent readigreja = new Intent ( CelulasActivity.this , IgrejasCriadasActivity.class );
            startActivity ( readigreja );
            return true;
        }else if ( itemId == R.id.action_lideres) {
            Intent addusuario = new Intent ( CelulasActivity.this , LeaderActivity.class );
            startActivity ( addusuario );
            return true;
        } else if ( itemId == R.id.action_addLider) {
            Intent addusuario = new Intent ( CelulasActivity.this , AddLeaderActivity.class );
            startActivity ( addusuario );
            return true;
        } else if ( itemId == R.id.action_Logout ) {
            FirebaseAuth.getInstance ( ).signOut ( );
            updateUI ( null );
            Toast.makeText ( this , getString ( R.string.Logout_sucesso ) , Toast.LENGTH_LONG ).show ( );
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @Override
   public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(this,HomeActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent(CelulasActivity.this,CelulasActivity.class);
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent(CelulasActivity.this, ComunicadosActivity.class);
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( CelulasActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( CelulasActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( CelulasActivity.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( CelulasActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( CelulasActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( CelulasActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_activityCelulas );
        drawer.closeDrawer( GravityCompat.START );
        return true;
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