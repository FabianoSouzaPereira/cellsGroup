package br.com.ieqcelulas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import celulas.Celula;

import static br.com.ieqcelulas.HomeActivity.igreja;

@SuppressWarnings("ALL")
public class ReadCelulaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef0;
    public  String celula_;
    public  String celula_2;
    public  String uid;
    private TextInputLayout textInputCelula;
    private TextInputLayout textInputRede;
    private TextInputLayout textInputSupervisor;
    private TextInputLayout textInputLider;
    private TextInputLayout textInputViceLider;
    private TextInputLayout textInputAnfitriao;
    private TextInputLayout textInputSecretario;
    private TextInputLayout textInputColaborador;
    private TextInputLayout textInputDia;
    private TextInputLayout textInputHora;
    private TextInputLayout textInputDataInicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_read_celula );
        Toolbar toolbar = findViewById( R.id.toolbar);
        setSupportActionBar( toolbar );
        inicializarFirebase();
        inicializarComponentes();
        Intent intent = getIntent();
        celula_ = intent.getStringExtra( "Celula" );


        pegandoConteudoCelula();

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }


    private void inicializarComponentes() {
        textInputCelula = findViewById(R.id.txt_celula);
        textInputRede = findViewById(R.id.txt_rede);
        textInputSupervisor = findViewById(R.id.txt_supervisor);
        textInputLider = findViewById(R.id.txt_lider);
        textInputViceLider = findViewById(R.id.txt_vicelider);
        textInputAnfitriao = findViewById(R.id.txt_anfitriao);
        textInputSecretario = findViewById(R.id.txt_secretario);
        textInputColaborador = findViewById(R.id.txt_colaborador);
        textInputDia = findViewById(R.id.txt_dia);
        textInputHora = findViewById(R.id.txt_hora);
        textInputDataInicio = findViewById( R.id.txt_dataInicio );
    }

    private void pegandoConteudoCelula() {

        novaRef0 = databaseReference.child( "Igrejas/" + igreja +"/Celulas/" + this.celula_);
        novaRef0.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Celula c = ds.getValue( Celula.class );
                    String celula = Objects.requireNonNull( c ).getCelula();
                    String rede = c.getRede();
                    String supervisor = c.getSupervisor();
                    String lider = c.getLider();
                    String viceLider = c.getViceLider();
                    String anfitriao = c.getAnfitriao();
                    String secretario = c.getSecretario();
                    String colaborador = c.getColaborador();
                    String dia = c.getDia();
                    String hora = c.getHora();
                    String datainicio = c.getDatainicio();

                    Objects.requireNonNull( textInputCelula.getEditText() ).setText( celula );
                    Objects.requireNonNull( textInputRede.getEditText() ).setText(rede);
                    Objects.requireNonNull( textInputSupervisor.getEditText() ).setText(supervisor);
                    Objects.requireNonNull( textInputLider.getEditText() ).setText(lider);
                    Objects.requireNonNull( textInputViceLider.getEditText() ).setText(viceLider);
                    Objects.requireNonNull( textInputAnfitriao.getEditText() ).setText(anfitriao);
                    Objects.requireNonNull( textInputSecretario.getEditText() ).setText(secretario);
                    Objects.requireNonNull( textInputColaborador.getEditText() ).setText(colaborador);
                    Objects.requireNonNull( textInputDia.getEditText() ).setText(dia);
                    Objects.requireNonNull( textInputHora.getEditText() ).setText(hora);
                    Objects.requireNonNull( textInputDataInicio.getEditText() ).setText( datainicio );

                }

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
        getMenuInflater().inflate( R.menu.read_celula, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings_readCelula) {
            return true;
        }

        if(id == R.id.action_edit_readcelula){
            Intent intent = new Intent( ReadCelulaActivity.this,EditCelulaActivity.class );
            intent.putExtra("Celula", String.valueOf( this.celula_ ) );
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.action_delete_edit_celula){
            initAlertDlgDelete();

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( ReadCelulaActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( ReadCelulaActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( ReadCelulaActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( ReadCelulaActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( ReadCelulaActivity.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( ReadCelulaActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( ReadCelulaActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( ReadCelulaActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ReadCelulaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void initAlertDlgDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReadCelulaActivity.this);
        builder  = builder.setMessage( "Apagar a célula?" );
        builder.setTitle( "Apagando célula..." )
                .setCancelable( false )
                .setNegativeButton( "cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Cancelado Apagar", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent( ReadCelulaActivity.this,DeleteCelulaActivity.class );
                        intent.putExtra("Celula", String.valueOf( ReadCelulaActivity.this.celula_ ) );
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder . create () ;
        alertDialog.show();

    }
}
