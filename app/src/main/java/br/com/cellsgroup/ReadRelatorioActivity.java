package br.com.cellsgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import login.LoginActivity;
import relatorios.Relatorio;

import static br.com.cellsgroup.HomeActivity.igreja;
import static login.LoginActivity.updateUI;

@SuppressWarnings("ALL")
public class ReadRelatorioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String cel_1;
    private DatabaseReference novaRef6;
    private ArrayList<String> rel = new ArrayList<String>();
    private ArrayAdapter<String> ArrayAdapterRelatorio;
    private ListView relatorio;
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
    private TextInputLayout textInputbaseCelula;
    private TextInputLayout textInputmembrosIEQ;
    private TextInputLayout textInputconvidados;
    private TextInputLayout textInputcriancas;
    private TextInputLayout textInputtotal;
    private TextInputLayout textInputestudos;
    private TextInputLayout textInputquebragelo;
    private TextInputLayout textInputlanche;
    private TextInputLayout textInputaceitacao;
    private TextInputLayout textInputreconciliacao;
    private TextInputLayout textInputtestemunho;
    private TextInputLayout textInputdataHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_read_relatorio );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializarFirebase();
        inicializarComponentes();
        Intent intent = getIntent();
        String cel_ = intent.getStringExtra( "Relatorio" );
        int pos = cel_.indexOf(":");
        cel_1 = cel_.substring( 0, pos );
        mostraRelatorio();

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }

    private void inicializarComponentes() {
        textInputCelula = findViewById(R.id.text_input_celula);
        textInputRede = findViewById(R.id.text_input_rede);
        textInputSupervisor = findViewById(R.id.text_input_supervisor);
        textInputLider = findViewById(R.id.text_input_lider);
        textInputViceLider = findViewById(R.id.text_input_vice_lider);
        textInputAnfitriao = findViewById(R.id.text_input_anfitriao);
        textInputSecretario = findViewById(R.id.text_input_secretario);
        textInputColaborador = findViewById(R.id.text_input_colaborador);
        textInputDia = findViewById(R.id.text_input_dia);
        textInputHora = findViewById(R.id.text_input_hora);
        textInputbaseCelula = findViewById(R.id.text_input_basecelula  );
        textInputmembrosIEQ = findViewById(R.id.text_input_membrosieq  );
        textInputconvidados = findViewById(R.id.text_input_convidados  );
        textInputcriancas = findViewById( R.id.text_input_criancas );
        textInputtotal  = findViewById(R.id.text_input_total  );
        textInputestudos = findViewById( R.id.text_input_estudo );
        textInputquebragelo = findViewById( R.id.text_input_quebragelo );
        textInputlanche = findViewById( R.id.text_input_lanche );
        textInputaceitacao = findViewById( R.id.text_input_aceitacao );
        textInputreconciliacao = findViewById( R.id.text_input_reconciliacao );
        textInputtestemunho = findViewById( R.id.text_input_testemunho  );
        textInputdataHora = findViewById( R.id.text_input_datahora );

    }

    private void mostraRelatorio() {
        novaRef6 = databaseReference.child( "Igrejas/" + igreja + "/Relatorios/" + cel_1 );
        novaRef6.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Relatorio r = ds.getValue(Relatorio.class);
                    String celula = Objects.requireNonNull( r ).getCelula();
                    String rede = r.getRede();
                    String supervisor = r.getSupervisor();
                    String lider = r.getLider();
                    String viceLider = r.getViceLider();
                    String anfitriao = r.getAnfitriao();
                    String secretario = r.getSecretario();
                    String colaborador = r.getColaborador();
                    String dia = r.getDia();
                    String hora = r.getHora();
                    String baseCelula = r.getBaseCelula();
                    String membrosIEQ = r.getMembrosIEQ();
                    String convidados = r.getConvidados();
                    String criancas = r.getCriancas();
                    String total =  r.getTotal();
                    String estudo = r.getEstudo();
                    String quebragelo = r.getQuebragelo();
                    String lanche = r.getLanche();
                    String aceitacao = r.getAceitacao();
                    String reconciliacao = r.getReconciliacao();
                    String testemunho = r.getTestemunho();
                    String dataHora = r.getDatahora();


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

                    Objects.requireNonNull(textInputbaseCelula.getEditText()).setText(baseCelula);
                    Objects.requireNonNull(textInputmembrosIEQ.getEditText()).setText(membrosIEQ);
                    Objects.requireNonNull(textInputconvidados.getEditText()).setText(convidados);
                    Objects.requireNonNull(textInputcriancas.getEditText()).setText(criancas);
                    Objects.requireNonNull(textInputtotal.getEditText()).setText(total);

                    Objects.requireNonNull(textInputestudos.getEditText()).setText(estudo);
                    Objects.requireNonNull(textInputquebragelo.getEditText()).setText(quebragelo);
                    Objects.requireNonNull(textInputlanche.getEditText()).setText(lanche);
                    Objects.requireNonNull(textInputaceitacao.getEditText()).setText(aceitacao);
                    Objects.requireNonNull(textInputreconciliacao.getEditText()).setText(reconciliacao);
                    Objects.requireNonNull(textInputtestemunho.getEditText()).setText(testemunho);
                    Objects.requireNonNull(textInputdataHora.getEditText()).setText(dataHora);

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
        getMenuInflater().inflate( R.menu.read_relatorio, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:
                Intent config = new Intent( ReadRelatorioActivity.this,Configuracao.class );
                startActivity( config );
                return true;
            case R.id.action_addIgreja:
                Intent addigreja = new Intent( ReadRelatorioActivity.this,AddIgrejaActivity.class );
                addigreja.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                addigreja.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity( addigreja );
                return true;
            case R.id.action_addUsuario:
                Intent addusuario = new Intent( ReadRelatorioActivity.this,AddUsuarioActivity.class );
                addusuario.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                addusuario.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity( addusuario );
                return true;
            case R.id.action_Login:
                Intent login = new Intent( ReadRelatorioActivity.this, LoginActivity.class);
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
            Intent celulas = new Intent( ReadRelatorioActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( ReadRelatorioActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( ReadRelatorioActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( ReadRelatorioActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( ReadRelatorioActivity.this, VisaoActivity.class );
            startActivity( visao );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( ReadRelatorioActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( ReadRelatorioActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( ReadRelatorioActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ReadRelatorioActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
