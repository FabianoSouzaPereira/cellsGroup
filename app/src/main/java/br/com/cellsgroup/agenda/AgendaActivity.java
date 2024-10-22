package br.com.cellsgroup.agenda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.AdapterListViewAgenda;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.models.agendas.Agenda;

import static br.com.cellsgroup.home.HomeActivity.uidIgreja;

@SuppressWarnings( { "UnnecessaryLocalVariable" , "UnusedAssignment" } )
public class AgendaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference novaRef;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final int limitebusca = 60;
    private final ArrayList<Agenda> ag = new ArrayList <>( );
    private List<Agenda> agendas;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private MaterialCalendarView calendarView;
    private String widgetdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_agenda );
        Toolbar toolbar = findViewById( R.id.toolbarAgenda );
        setSupportActionBar( toolbar );

        inicializarFirebase();
        addDataHora();
        iniciaComponentes();


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration ( this, LinearLayoutManager.VERTICAL );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration( dividerItemDecoration );

        readAgenda();

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( AgendaActivity.this, AddAgendaActivity.class );
                startActivity(intent);
                finish();
            }
        } );
        DrawerLayout drawer = findViewById( R.id.drawer_activityAgenda );
        NavigationView navigationView = findViewById( R.id.nav_viewAgenda );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(AgendaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void iniciaComponentes() {
        recyclerView = findViewById(R.id.recycleViewAgenda );
        calendarView = findViewById( R.id.calendarViewAg );
        calendarView.setCurrentDate( new Date() );
        calendarView.setDateSelected( new Date(),true );
        //SPINNERS
//        ArrayAdapter <String> adapterhora = new ArrayAdapter<>( AgendaActivity.this, R.layout.spinner_layout,hora );
//        hr = findViewById( R.id.spinnerhoraAg );
//        adapterhora.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        hr.setAdapter( adapterhora );
//        hr.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                hh = (String)hr.getSelectedItem();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                hh = "";
//            }
//        } );
//
//        ArrayAdapter<String> adaptermin = new ArrayAdapter<>( AgendaActivity.this, R.layout.spinner_layout,minuto );
//        min = findViewById( R.id.spinnerminAg );
//        adapterhora.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        min.setAdapter( adaptermin );
//        min.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mm = (String)min.getSelectedItem();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                mm = "";
//            }
//        } );
        //FIM SPINNERS
        calendarView.setOnDateChangedListener( new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                widgetdate =formatCalendaryData(date);
                int position = findAgArray( agendas, widgetdate );
                recyclerView.scrollToPosition( position );
            }
        } );

    }

    public String formatCalendaryData(CalendarDay date){
        String dia = "";
        String mes = "";
        String ano = "";
        dia = String.valueOf( date.getDay() );
            int v = date.getMonth();
                v += 1;
            if(v < 10){ String m = String.valueOf( v );  mes = "0"+m; }
        ano = String.valueOf( date.getYear() );
        String data = dia + "/" + mes + "/" + ano;
        return data;
}

    public int findAgArray(List<Agenda> agendas,String data ){
        for(int i = 0 ;i < agendas.size(); i++){
           String a = agendas.get( i ).getData();
            if (a.equals( data )) { return i;}
        }
        return 0;
    }

    private void readAgenda(){
        novaRef = databaseReference.child( "churchs/" + uidIgreja + "/Skedule/" );
        Query query = novaRef.orderByChild( "data" ).limitToFirst( limitebusca );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ag.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Agenda a = ds.getValue(Agenda.class);
                    ag.add(a);
                }
                agendas = ag;
                mAdapter = new AdapterListViewAgenda( agendas, AgendaActivity.this );
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
             }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_activityAgenda);
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
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
    protected void onPause ( ) {
        super.onPause ( );
    }

    @Override
    protected void onResume ( ) {
        super.onResume ( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_config, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( AgendaActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( AgendaActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( AgendaActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( AgendaActivity.this, AgendaActivity.class );
            startActivity( agenda );
        }else if (id == R.id.nav_contact) {
            Intent contato = new Intent( AgendaActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( AgendaActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( AgendaActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_del_celula );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat ("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
    }
    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }
}
