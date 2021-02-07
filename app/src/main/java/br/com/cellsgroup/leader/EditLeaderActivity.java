package br.com.cellsgroup.leader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.celulas.Celula;

import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.typeUserAdmin;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremail;

public class EditLeaderActivity extends AppCompatActivity {
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
    private Spinner spCelula;
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
    private static boolean validate = true;
    private String uid;
    private String user;

    private Query query;
    private ValueEventListener queryListener;
    private static final boolean DeletePermission = false;
    private String cel;
    private String celula;
    private final ArrayList<String> cels = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterCelula;
    private String celulaName;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_leader );
        Toolbar toolbar = findViewById( R.id.toolbarEditleader );
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
        EditTextnome = findViewById( R.id.text_input_editNome);
        EditTextidade = findViewById( R.id.text_input_editIdade);
        EditTextsexo = findViewById( R.id.text_input_editSexo );
        EditTextdataNascimento = findViewById( R.id.text_input_editDataNascimento);
        EditTextdataBastismo = findViewById( R.id.text_input_editDataBatismo );
        EditTextnomepai = findViewById( R.id.text_input_editNomePai );
        EditTextnomemae = findViewById( R.id.text_input_editNomeMae );
        EditTextestadocivil = findViewById( R.id.text_input_editEstadoCivil );
        EdiTextddi = findViewById (R.id.text_input_editddi );
        EditTexttelefone = findViewById( R.id.text_input_editTelefone );
        EditTextemail = findViewById( R.id.text_input_editEmail );
        EditTextendereco = findViewById( R.id.text_input_editEndereco );
        EditTextbairro = findViewById( R.id.text_input_editBairro );
        EditTextcidade = findViewById( R.id.text_input_editCidade );
        EditTextestado = findViewById ( R.id.text_input_editEstado );
        EditTextpais = findViewById( R.id.text_input_editPais );
        EditTextcep = findViewById( R.id.text_input_editCep );
        EditTextcargoIgreja = findViewById( R.id.text_input_editCargoIgreja);

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp( EditLeaderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void EditUsuarioClick( MenuItem item){
        addDataHora();
        validate=true;
        String nome =  EditTextnome.getEditText().getText().toString().trim();
        if(nome.equals ("")|| nome.length() < 4){
            validate = false;
            EditTextnome.setError("Este campo é obrigatório");
            EditTextnome.setFocusable (true);
            EditTextnome.requestFocus ();
        }
        String idade =  EditTextidade.getEditText ( ).getText().toString().trim();
        String sexo = EditTextsexo.getEditText ( ).getText().toString().trim();
        String dataNascimento = EditTextdataNascimento.getEditText().getText().toString().trim();
        if( dataNascimento .equals ( "" ) || dataNascimento .length ( ) < 8 ){
            validate = false;
            EditTextdataNascimento.setError("Este campo é obrigatório");
            EditTextdataNascimento.setFocusable (true);
            EditTextdataNascimento.requestFocus ();
        }
        String dataBastismo = EditTextdataBastismo.getEditText().getText().toString().trim();
        String nomepai = EditTextnomepai.getEditText().getText().toString().trim();
        String nomemae = EditTextnomemae.getEditText().getText().toString().trim();
        String estadocivil =  EditTextestadocivil.getEditText().getText().toString().trim();
        String ddi = EdiTextddi.getEditText().getText().toString().trim();
        if( ddi.equals ( "" ) || ddi.length ( ) > 2 ){
            validate = false;
            EdiTextddi.setError("Este campo é obrigatório, dois dígitos");
            EdiTextddi.setFocusable (true);
            EdiTextddi.requestFocus ();
        }
        String telefone =  EditTexttelefone.getEditText().getText().toString().trim();
        if( telefone.equals ( "" ) || telefone.length ( ) < 9 ){
            validate = false;
            EditTexttelefone.setError("Este campo é obrigatório, min. 9 dígitos.");
            EditTexttelefone.setFocusable (true);
            EditTexttelefone.requestFocus ();
        }
        useremail =  EditTextemail.getEditText().getText().toString().trim();
        String email = useremail;
        if(email .equals ("")|| email.length() < 8 || !email.contains ("@" )){
            validate = false;
            EditTextemail.setError("Campo inválido");
            EditTextemail.setFocusable (true);
            EditTextemail.requestFocus ();
        }
        String endereco =  EditTextendereco.getEditText().getText().toString().trim();
        String bairro = EditTextbairro.getEditText().getText().toString().trim();
        String cidade =  EditTextcidade.getEditText().getText().toString().trim();
        String estado =  EditTextestado.getEditText().getText().toString().trim();
        String pais =  EditTextpais.getEditText().getText().toString().trim();
        String cep = EditTextcep.getEditText().getText().toString().trim();
        String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
        if(cargoIgreja.equals ("")|| cargoIgreja.length() < 4){
            validate = false;
            EditTextcargoIgreja.setError("Este campo é obrigatório,");
            EditTextcargoIgreja.setFocusable (true);
            EditTextcargoIgreja.requestFocus ();
        }
        String status = "1";
        final String igreja = HomeActivity.igreja;
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if( emaildual ){
            Toast.makeText( EditLeaderActivity.this, "Já existe um cadastro com esse mesmo email " + email, Toast.LENGTH_LONG ).show();
            return ;
        }
        if( validate ){
            if(!TextUtils.isEmpty( nome ) ) {

                if ( uid != null ) {

                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put( "/celula" , cel);
                    userUpdates.put( "/nome" , nome);
                    userUpdates.put( "/idade", idade );
                    userUpdates.put( "/sexo", sexo );
                    userUpdates.put( "/dataNascimento", dataNascimento );
                    userUpdates.put( "/dataBastismo", dataBastismo );
                    userUpdates.put( "/nomepai", nomepai );
                    userUpdates.put( "/nomemae", nomemae );
                    userUpdates.put( "/estadocivil", estadocivil );
                    userUpdates.put( "/ddi", ddi );
                    userUpdates.put( "/telefone", telefone );
                    userUpdates.put( "/endereco", endereco );
                    userUpdates.put( "/bairro", bairro );
                    userUpdates.put( "/cidade", cidade );
                    userUpdates.put( "/estado", estado );
                    userUpdates.put( "/pais", pais );
                    userUpdates.put( "/cep", cep );
                    userUpdates.put( "/email", email );

                    leaders.child(uid).updateChildren( userUpdates);

                    Toast.makeText(this,"Editado célula com sucesso", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent ( EditLeaderActivity.this , LeaderActivity.class );
                    startActivity ( intent );

                }else {
                    Toast.makeText ( this , "Erro ao tentar criar usuário !" , Toast.LENGTH_LONG ).show ( );
                    if ( !typeUserAdmin ) {
                        Toast.makeText ( this , "Você não é um leader administrador. \n Não pode criar leader admin !" , Toast.LENGTH_LONG ).show ( );
                    }
                    Intent intent = new Intent ( EditLeaderActivity.this , HomeActivity.class );
                    startActivity ( intent );
                }
            }
        }

    }

    private void readOnlyActive() {
        leaders = databaseReference.child( "leaders/");
        query = leaders.orderByChild( "uid" ).equalTo (uid).limitToFirst(1);
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

                           celulaName = Objects.requireNonNull ( celulaOb , "" ).toString ( );
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
                           loadSpinner(celulaName);

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

    public void loadSpinner( final String celulaName){
        final String ui = UI.getUid ();
        novaRef = databaseReference.child( "churchs/" + uidIgreja + "/cells/");
        query = novaRef.orderByChild ("celula").limitToLast (200);
        queryListener =  new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cels.clear();
                cels.add("Escolha uma célula");
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    for(DataSnapshot dados : ds.getChildren()) {
                        if ( dados.hasChild ( "celula" ) ) {
                            Celula c = dados.getValue ( Celula.class );
                            if ( !c.getCelula ( ).equals ( "" ) ) {
                                if ( c.getUserId ( ).equals ( ui ) ) {
                                    String celula = c.getCelula ( );
                                    cels.add ( celula );
                                }
                            }
                        }
                    }
                }
                ArrayAdapter <String> adapter = new ArrayAdapter<String>( EditLeaderActivity.this, android.R.layout.simple_spinner_dropdown_item, cels);
                spCelula = findViewById( R.id.spinnerEditcelula );
                int pos = spinnerPosition (cels,celulaName);
                spCelula.setSelection(pos);
                spCelula.setAdapter( adapter );
                spCelula.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(spCelula.getSelectedItem().equals (0) ){ return; }
                        cel= (String)spCelula.getSelectedItem();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        cel = "";
                    }
                } );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG","Erro Database"+ databaseError.toException() );
            }
        } ;

        query.addValueEventListener (queryListener );

    }

    private int spinnerPosition(ArrayList<String> array, String position){
        int posicaoArray = 0;

        for(int i=0; (i <= array.size()-1) ; i++){
            if(array.get(i).equals(position)){

             posicaoArray = i;
            }else{
             posicaoArray = 0;
            }
        }
        return posicaoArray;
    }

    private void deleteUsuario(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder( EditLeaderActivity.this );
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

                Toast.makeText ( EditLeaderActivity.this , "Lider apagado com sucesso" , Toast.LENGTH_LONG ).show ( );

                EditLeaderActivity.this.finish();
                Intent intent = new Intent( EditLeaderActivity.this, LeaderActivity.class );
                startActivity(intent);
            }
        } );

        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

    }

    public void initAlertDialogo(){

    }

    @Override
    public void onBackPressed() {
        EditLeaderActivity.this.finish();
        Intent intent = new Intent( EditLeaderActivity.this, LeaderActivity.class );
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save_cancel, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save){
            EditUsuarioClick(item);
            return true;
        }
        if(id == R.id.action_Edit){
            return true;
        }
        if(id == R.id.action_delete){
            deleteUsuario ();
            return true;
        }

        if(id == R.id.action_Cancel){
            EditLeaderActivity.this.finish();
            Intent intent = new Intent( EditLeaderActivity.this, LeaderActivity.class );
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