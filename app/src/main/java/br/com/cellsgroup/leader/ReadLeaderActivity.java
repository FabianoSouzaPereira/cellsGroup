package br.com.cellsgroup.leader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

import br.com.cellsgroup.Igreja.EditIgrejaActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.relatorios.ReadRelatorioActivity;

import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremail;

public class ReadLeaderActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_read_leader );
        Toolbar toolbar = findViewById( R.id.toolbarReadleader );
        setSupportActionBar( toolbar );

        mAuth = FirebaseAuth.getInstance();

        inicializarComponentes();

        inicializarFirebase();
        Intent intent = getIntent();
        uid = intent.getStringExtra( "uid" );
        user  = intent.getStringExtra( "user" );
        readOnlyActive();

    }

    private void inicializarComponentes() {
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
                Log.e(TAG,"Erro Database"+ databaseError.toException() );
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