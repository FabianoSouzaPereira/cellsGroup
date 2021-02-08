package br.com.cellsgroup.leader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.Igreja.EditIgrejaActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.relatorios.ReadRelatorioActivity;
import br.com.cellsgroup.relatorios.RelatorioActivityView;

import static br.com.cellsgroup.home.HomeActivity.group;
import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremail;
import static br.com.cellsgroup.home.HomeActivity.useremailAuth;

public class ReadLeaderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "TAG";
    public String DataTime;
    public String DataT;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference leaders;
    private DatabaseReference ref;
    private DatabaseReference novaRef;

    private final int limitebusca = 1;
    private TextInputLayout EditTextCelula;
    private TextInputLayout EditTextnome;
    private TextInputLayout EditTextidade;
    private TextInputLayout EditTextsexo;
    private TextInputLayout EditTextdataNascimento;
    private TextInputLayout EditTextdataBastismo;
    private TextInputLayout EditTextnomepai;
    private TextInputLayout EditTextnomemae;
    private TextInputLayout EditTextestadocivil;
    private TextInputLayout EditTexttelefone;
    private TextInputLayout EdiTextddi;
    private TextInputLayout EditTextemail;
    private TextInputLayout EditTextendereco;
    private TextInputLayout EditTextbairro;
    private TextInputLayout EditTextestado;
    private TextInputLayout EditTextcidade;
    private TextInputLayout EditTextpais;
    private TextInputLayout EditTextcep;
    private TextInputLayout EditTextcargoIgreja;
    private final boolean emaildual = false;
    private final String email = "";
    private final String emailTest = "";
    private String key;
    private static final boolean validate = true;
    private String uid;
    private String user;
    private Query query;
    private ValueEventListener queryListener;
    private static final boolean DeletePermission = false;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_read_leader );
        Toolbar toolbar = findViewById( R.id.toolbar_read_leader );
        setSupportActionBar( toolbar );

        mAuth = FirebaseAuth.getInstance();
        useremailAuth = mAuth.getCurrentUser ().getEmail ();

        inicializarComponentes();

        inicializarFirebase();
        Intent intent = getIntent();
        uid = intent.getStringExtra( "uid" );
        user  = intent.getStringExtra( "user" );
        readOnlyActive();

        DrawerLayout drawer = findViewById( R.id.drawer_activity_read_leader);
        NavigationView navigationView = findViewById( R.id.nav_view_read_leader);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );

        View headerView = navigationView.getHeaderView(0);
        nhTitle = headerView.findViewById (R.id.nhTitle_read_leader);
        nhName = headerView.findViewById (R.id.nhName_read_leader);
        nhEmail = headerView.findViewById (R.id.nhEmail_read_leader);
        nhEmail.setText (useremailAuth);
        nhTitle.setText (group);
        nhName.setText(igreja);

    }

    private void inicializarComponentes() {
        EditTextCelula = findViewById (R.id.text_input_readCelula );
        EditTextnome = findViewById( R.id.text_input_readNome);
        EditTextidade = findViewById( R.id.text_input_readIdade);
        EditTextsexo = findViewById( R.id.text_input_readSexo );
        EditTextdataNascimento = findViewById( R.id.text_input_readDataNascimento);
        EditTextdataBastismo = findViewById( R.id.text_input_readDataBatismo );
        EditTextnomepai = findViewById( R.id.text_input_readNomePai );
        EditTextnomemae = findViewById( R.id.text_input_readNomeMae );
        EditTextestadocivil = findViewById( R.id.text_input_readEstadoCivil );
        EdiTextddi = findViewById (R.id.text_input_readddi );
        EditTexttelefone = findViewById( R.id.text_input_readTelefone );
        EditTextemail = findViewById( R.id.text_input_readEmail );
        EditTextendereco = findViewById( R.id.text_input_readEndereco );
        EditTextbairro = findViewById( R.id.text_input_readBairro );
        EditTextcidade = findViewById( R.id.text_input_readCidade );
        EditTextestado = findViewById ( R.id.text_input_readEstado );
        EditTextpais = findViewById( R.id.text_input_readPais );
        EditTextcep = findViewById( R.id.text_input_readCep );
        EditTextcargoIgreja = findViewById( R.id.text_input_readCargoIgreja);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp( ReadLeaderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    private void readOnlyActive() {
        leaders = databaseReference.child( "leaders/");
        query = leaders
            .orderByChild( "uid" ).equalTo (uid).limitToFirst(1);
        queryListener =  new ValueEventListener () {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dados : dataSnapshot.getChildren()) {
                    try {
                        //  User u = dados.getValue( User.class );
                        Object ui =  dados.child ("uid").getValue ();
                        String id = Objects.requireNonNull ( ui,"" ).toString ();
                        if(id.equalsIgnoreCase (uid) ) {
                            Object celulaOb = dados.child ( "celula" ).getValue ( );
                            Object userOb = dados.child ( "nome" ).getValue ( );
                            Object idadeOb = dados.child ( "idade" ).getValue ( );
                            Object sexoOb = dados.child ( "sexo" ).getValue ( );
                            Object dataNascimentoOb = dados.child ( "dataNascimento" ).getValue ( );
                            Object dataBastismoOb = dados.child ( "dataBastismo" ).getValue ( );
                            Object nomepaiOb = dados.child ( "nomepai" ).getValue ( );
                            Object nomemaeOb = dados.child ( "nomemae" ).getValue ( );
                            Object estadocivilOb = dados.child ( "estadocivil" ).getValue ( );
                            Object ddiOb = dados.child ( "ddi" ).getValue ( );
                            Object telefoneOb = dados.child ( "telefone" ).getValue ( );
                            Object emailOb = dados.child ( "email" ).getValue ( );
                            Object enderecoOb = dados.child ( "endereco" ).getValue ( );
                            Object bairroOb = dados.child ( "bairro" ).getValue ( );
                            Object cidadeOb = dados.child ( "cidade" ).getValue ( );
                            Object estadoOb = dados.child ( "estado" ).getValue ( );
                            Object paisOb = dados.child ( "pais" ).getValue ( );
                            Object cepOb = dados.child ( "cep" ).getValue ( );
                            Object cargoIgrejaOb = dados.child ( "cargoIgreja" ).getValue ( );

                            String celula = Objects.requireNonNull ( celulaOb , "" ).toString ( );
                            String nome = Objects.requireNonNull ( userOb , "" ).toString ( );
                            String idade = Objects.requireNonNull ( idadeOb , "" ).toString ( );
                            String sexo = Objects.requireNonNull ( sexoOb , "" ).toString ( );
                            String dataNascimento = Objects.requireNonNull ( dataNascimentoOb , "" ).toString ( );
                            String dataBastismo = Objects.requireNonNull ( dataBastismoOb , "" ).toString ( );
                            String nomepai = Objects.requireNonNull ( nomepaiOb , "" ).toString ( );
                            String nomemae = Objects.requireNonNull ( nomemaeOb , "" ).toString ( );
                            String estadocivil = Objects.requireNonNull ( estadocivilOb , "" ).toString ( );
                            String ddi = Objects.requireNonNull ( ddiOb , "" ).toString ( );
                            String telefone = Objects.requireNonNull ( telefoneOb , "" ).toString ( );
                            useremail = Objects.requireNonNull ( emailOb , "" ).toString ( );
                            String email = useremail;
                            String endereco = Objects.requireNonNull ( enderecoOb , "" ).toString ( );
                            String bairro = Objects.requireNonNull ( bairroOb , "" ).toString ( );
                            String cidade = Objects.requireNonNull ( cidadeOb , "" ).toString ( );
                            String estado = Objects.requireNonNull ( estadoOb , "" ).toString ( );
                            String pais = Objects.requireNonNull ( paisOb , "" ).toString ( );
                            String cep = Objects.requireNonNull ( cepOb , "" ).toString ( );
                            String cargoIgreja = Objects.requireNonNull ( cargoIgrejaOb , "" ).toString ( );

                            Objects.requireNonNull ( EditTextCelula.getEditText ( ) , "" ).setText ( celula );
                            Objects.requireNonNull ( EditTextnome.getEditText ( ) , "" ).setText ( nome );
                            Objects.requireNonNull ( EditTextidade.getEditText ( ) , "" ).setText ( idade );
                            Objects.requireNonNull ( EditTextsexo.getEditText ( ) , "" ).setText ( sexo );
                            Objects.requireNonNull ( EditTextdataNascimento.getEditText ( ) , "" ).setText ( dataNascimento );
                            Objects.requireNonNull ( EditTextdataBastismo.getEditText ( ) , "" ).setText ( dataBastismo );
                            Objects.requireNonNull ( EditTextnomepai.getEditText ( ) , "" ).setText ( nomepai );
                            Objects.requireNonNull ( EditTextnomemae.getEditText ( ) , "" ).setText ( nomemae );
                            Objects.requireNonNull ( EditTextestadocivil.getEditText ( ) , "" ).setText ( estadocivil );
                            Objects.requireNonNull ( EdiTextddi.getEditText ( ) , "" ).setText ( ddi );
                            Objects.requireNonNull ( EditTexttelefone.getEditText ( ) , "" ).setText ( telefone );
                            Objects.requireNonNull ( EditTextemail.getEditText ( ) , "" ).setText ( email );
                            Objects.requireNonNull ( EditTextendereco.getEditText ( ) , "" ).setText ( endereco );
                            Objects.requireNonNull ( EditTextbairro.getEditText ( ) , "" ).setText ( bairro );
                            Objects.requireNonNull ( EditTextcidade.getEditText ( ) , "" ).setText ( cidade );
                            Objects.requireNonNull ( EditTextestado.getEditText ( ) , "" ).setText ( estado );
                            Objects.requireNonNull ( EditTextpais.getEditText ( ) , "" ).setText ( pais );
                            Objects.requireNonNull ( EditTextcep.getEditText ( ) , "" ).setText ( cep );
                            Objects.requireNonNull ( EditTextcargoIgreja.getEditText ( ) , "" ).setText ( cargoIgreja );
                        }
                    } catch ( Exception e ) {
                        e.printStackTrace ( );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } ;

        query.addValueEventListener (queryListener );

    }

    private void deleteUsuario(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder( ReadLeaderActivity.this );
        builder1 = builder1.setMessage( "Lider "+ useremail);
        builder1.setTitle( "Apagar Lider ?" ).setCancelable( false ).setNegativeButton( "cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( getApplicationContext(), "Cancelar", Toast.LENGTH_SHORT ).show();
            }
        } ).setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ref = databaseReference;

                //apaga leader de membros
                ref.child ( "churchs/" + uidIgreja ).child ("/members/").child(uid).removeValue ();

                //Apaga leader em users/
                leaders.child ( uid ).removeValue ( );

                Toast.makeText ( ReadLeaderActivity.this , "Lider apagado com sucesso" , Toast.LENGTH_LONG ).show ( );

                ReadLeaderActivity.this.finish();
                Intent intent = new Intent( ReadLeaderActivity.this, LeaderActivity.class );
                startActivity(intent);
            }
        } );

        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

    }

    @Override
    public void onBackPressed() {
        ReadLeaderActivity.this.finish();
        Intent intent = new Intent( ReadLeaderActivity.this, LeaderActivity.class );
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_edit_delete, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_Edit){
            ReadLeaderActivity.this.finish();
            Intent intent = new Intent(  ReadLeaderActivity.this, EditLeaderActivity.class );
            intent.putExtra("uid", String.valueOf( uid) );
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_delete){
            deleteUsuario ();
            return true;
        }

        if(id == R.id.action_Cancel){
            ReadLeaderActivity.this.finish();
            Intent intent = new Intent(  ReadLeaderActivity.this, LeaderActivity.class );
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, ReadLeaderActivity.class );
            startActivity( home );

        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( ReadLeaderActivity.this, CelulasActivity.class );
            celulas.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity( celulas );

        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( ReadLeaderActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );

        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( ReadLeaderActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );

        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( ReadLeaderActivity.this, AgendaActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_realatorio) {
            Intent relatorio = new Intent( ReadLeaderActivity.this, RelatorioActivityView.class );
            startActivity( relatorio );

        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( ReadLeaderActivity.this, ContatoActivity.class );
            startActivity( contato );

        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( ReadLeaderActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );

        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( ReadLeaderActivity.this, EnviarActivity.class );
            startActivity( Enviar );

        }

        DrawerLayout drawer = findViewById( R.id.drawer_activity_read_leader );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }


    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat ("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        DataTime = data + " "+ hora;
        DataT = data;
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