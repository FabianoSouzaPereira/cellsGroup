package br.com.cellsgroup.relatorios;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

import br.com.cellsgroup.R;
import br.com.cellsgroup.*;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.igreja.IgrejasCriadasActivity;
import br.com.cellsgroup.igreja.addIgrejaActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.leader.LeaderActivity;
import br.com.cellsgroup.models.relatorios.Relatorio;

import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.group;
import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremailAuth;
import static br.com.cellsgroup.models.login.LoginActivity.updateUI;


public class RelatorioActivityView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef4;
    private DatabaseReference novarefPesq;
    private final ArrayList<String> rel = new ArrayList <>( );
    private ArrayAdapter<String> ArrayAdapterRelatorio;
    private ListView relatorio;
    SearchView searchView;
    private final int limitebusca = 500;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_relatorio_view );
        Toolbar toolbar = findViewById( R.id.toolbar_relatorio );
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
                if(ArrayAdapterRelatorio!=null) {
                    ArrayAdapterRelatorio.getFilter ( ).filter ( newText );
                }
                return false;
            }
        } );

        DrawerLayout drawer = findViewById( R.id.drawer_read_relatorioView);
        NavigationView navigationView = findViewById( R.id.nav_view_relatorio_activity_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );

        View headerView = navigationView.getHeaderView(0);
        nhTitle = headerView.findViewById (R.id.nhTitle_relatorio_view);
        nhName = headerView.findViewById (R.id.nhName_realtorio_view);
        nhEmail = headerView.findViewById (R.id.nhEmail_relatorio_view);
        nhEmail.setText (useremailAuth);
        nhTitle.setText (group);
        nhName.setText(igreja);
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
                startActivity(intent);
            }
        } );
    }

    private void readRelOnlyActive() {
        final String ui = UI.getUid ();
        novaRef4 = databaseReference.child("churchs/" + uidIgreja + "/Reports/" );
        Query query = novaRef4.orderByChild( "celula" ).limitToLast(limitebusca );
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

                ArrayAdapterRelatorio = new ArrayAdapter <>( RelatorioActivityView.this , android.R.layout.simple_selectable_list_item , rel );
                relatorio.setAdapter( ArrayAdapterRelatorio );
                ArrayAdapterRelatorio.notifyDataSetChanged();
                hiddShowMessage();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        } );

    }

    // Mostra memsagem se lista vir vazia
    private void hiddShowMessage() {
        // Mostra a mensagem em caso de lista fazia
        CardView cardView = findViewById (R.id.cardViewRelatorio );
        if(rel.size() == 0){
            relatorio.setVisibility (View.GONE);
            cardView.setVisibility (View.VISIBLE);

        }else{
            cardView.setVisibility (View.GONE);
            relatorio.setVisibility (View.VISIBLE);
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(RelatorioActivityView.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_read_relatorioView);
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.home, menu );
        return  true;
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
            Intent config = new Intent ( RelatorioActivityView.this , Configuracao.class );
            startActivity ( config );
            return true;
        } else if ( itemId == R.id.action_addIgreja ) {
            Intent addigreja = new Intent ( RelatorioActivityView.this  , addIgrejaActivity.class );
            startActivity ( addigreja );
            return true;
        } else if ( itemId == R.id.action_readIgreja ) {
            Intent readigreja = new Intent ( RelatorioActivityView.this , IgrejasCriadasActivity.class );
            startActivity ( readigreja );
            return true;
        }else if ( itemId == R.id.action_lideres) {
            Intent addlideres = new Intent ( RelatorioActivityView.this , LeaderActivity.class );
            startActivity ( addlideres);
            return true;
        }else if ( itemId == R.id.action_Sobre) {
            Intent sobre= new Intent ( RelatorioActivityView.this , SobreActivity.class );
            startActivity ( sobre);
            return true;
        }else if ( itemId == R.id.action_Sair ) {
            finishAffinity ();
            return true;
        }  else if ( itemId == R.id.action_Logout ) {
            FirebaseAuth.getInstance ( ).signOut ( );
            updateUI ( null );
            Toast.makeText ( this , getString ( R.string.Logout_sucesso ) , Toast.LENGTH_SHORT ).show ( );
            finishAffinity ();
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

        } else if (id == R.id.nav_leader) {
            Intent agenda = new Intent( RelatorioActivityView.this, LeaderActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_realatorio) {
            Intent relatorio = new Intent( RelatorioActivityView.this, RelatorioActivityView.class );
            startActivity( relatorio );

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

        DrawerLayout drawer = findViewById( R.id.drawer_read_relatorioView );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }


    @Override
    protected void onStart ( ) {
        super.onStart ( );
    }

    @Override
    protected void onStop ( ) {
        super.onStop ( );
    }

    @Override
    protected void onResume ( ) {
        super.onResume ( );
    }

    @Override
    protected void onPause ( ) {
        super.onPause ( );
    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }
}
