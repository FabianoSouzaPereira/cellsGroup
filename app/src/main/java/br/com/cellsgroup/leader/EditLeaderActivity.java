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
import java.util.Map;
import java.util.Objects;

import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.celulas.Celula;
import br.com.cellsgroup.models.pessoas.Leader;
import br.com.cellsgroup.utils.MaskEditUtil;
import br.com.cellsgroup.utils.ResolveDate;

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
    private DatabaseReference novaRef6;
    private DatabaseReference novaRef7;

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
    private static Boolean res = false;
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
    private String uidCelula = "";
    Query queryCelula;
    ValueEventListener listenerCelula;
    String mensagem1 = "";
    String mensagem2 = "";
    String mensagem3 = "";
    String mensagem4 = "";
    String mensagem5 = "";
    String mensagem6 = "";
    String mensagem7 = "";
    String mensagem8 = "";
    String mensagem9 = "";
    String mensagem10 = "";
    String mensagem11 = "";
    String mensagem12 = "";
    String mensagem13 = "";
    String mensagem14 = "";
    String mensagem15 = "";

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
        Objects.requireNonNull ( EditTextdataNascimento.getEditText ( ), "//" ).addTextChangedListener ( MaskEditUtil.mask ( EditTextdataNascimento, MaskEditUtil.FORMAT_DATE));
        EditTextdataBastismo = findViewById( R.id.text_input_editDataBatismo );
        Objects.requireNonNull ( EditTextdataBastismo.getEditText ( ),"//" ).addTextChangedListener ( MaskEditUtil.mask ( EditTextdataBastismo, MaskEditUtil.FORMAT_DATE));
        EditTextnomepai = findViewById( R.id.text_input_editNomePai );
        EditTextnomemae = findViewById( R.id.text_input_editNomeMae );
        EditTextestadocivil = findViewById( R.id.text_input_editEstadoCivil );
        EdiTextddi = findViewById (R.id.text_input_editddi );
        EditTexttelefone = findViewById( R.id.text_input_editTelefone );
        Objects.requireNonNull ( EditTexttelefone.getEditText ( ),"00000-0000" ).addTextChangedListener ( MaskEditUtil.mask (EditTexttelefone, MaskEditUtil.FORMAT_FONE ) );
        EditTextemail = findViewById( R.id.text_input_editEmail );
        EditTextendereco = findViewById( R.id.text_input_editEndereco );
        EditTextbairro = findViewById( R.id.text_input_editBairro );
        EditTextcidade = findViewById( R.id.text_input_editCidade );
        EditTextestado = findViewById ( R.id.text_input_editEstado );
        EditTextpais = findViewById( R.id.text_input_editPais );
        EditTextcep = findViewById( R.id.text_input_editCep );
        Objects.requireNonNull ( EditTextcep.getEditText ( ), "00000-000" ).addTextChangedListener ( MaskEditUtil.mask (EditTextcep, MaskEditUtil.FORMAT_CEP));
        EditTextcargoIgreja = findViewById( R.id.text_input_editCargoIgreja);
        mensagem1 = getResources ().getString (R.string.erroCampoObrigatorio);
        mensagem2 = getResources ().getString (R.string.editadoleader);
        mensagem3 = getResources ().getString (R.string.erroEditarleader);
        mensagem4 = getResources ().getString (R.string.erroPreencherTudo);
        mensagem5 = getResources ().getString (R.string.erroNaoAdmin);
        mensagem6 = getResources ().getString (R.string.erroCampo3digitos);
        mensagem7 = getResources ().getString (R.string.erroCampo11digitos);
        mensagem8 = getResources ().getString (R.string.erroEmailexiste);
        mensagem9 = getResources ().getString (R.string.escolhaCelula);
        mensagem10 = getResources ().getString (R.string.erroCampoInvalido);
        mensagem11 = getResources ().getString (R.string.apagadoLiderSuccess);
        mensagem12 = getResources ().getString (R.string.erroApagarLider);
        mensagem13 = getResources ().getString (R.string.lider);
        mensagem14 = getResources ().getString (R.string.questionApagarlider);
        mensagem15 = getResources ().getString (R.string.cancelar);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp( EditLeaderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    private void EditUsuarioClick( MenuItem item){
        addDataHora();
        validate=true;
        res = false;

        String nome =  EditTextnome.getEditText().getText().toString().trim();
        if(nome.equals ("")|| nome.length() < 4){
            validate = false;
            EditTextnome.setError(mensagem1);
            EditTextnome.setFocusable (true);
            EditTextnome.requestFocus ();
        }else{
            EditTextnome.setError(null);
        }
        String idade =  EditTextidade.getEditText ( ).getText().toString().trim();
        if( idade.equals ( "" ) || Integer.parseInt (idade) > 122){
            validate = false;
            EditTextidade.setError(mensagem1);
            EditTextidade.setFocusable (true);
            EditTextidade.requestFocus ();
        }else{
            EditTextidade.setError(null);
        }
        String sexo = EditTextsexo.getEditText ( ).getText().toString().trim();
        if( sexo.equals ( "" )){
            validate = false;
            EditTextsexo.setError(mensagem1);
            EditTextsexo.setFocusable (true);
            EditTextsexo.requestFocus ();
        }else{
            EditTextsexo.setError(null);
        }
        String dataNascimento = EditTextdataNascimento.getEditText().getText().toString().trim();
        res = ResolveDate.getDateRes(dataNascimento);
        if( dataNascimento .equals ( "" ) || dataNascimento .length ( ) < 8 || res == true){
            validate = false;
            EditTextdataNascimento.setError(mensagem1);
            EditTextdataNascimento.setFocusable (true);
            EditTextdataNascimento.requestFocus ();
        }else{
            EditTextdataNascimento.setError(null);
        }
        String dataBastismo = EditTextdataBastismo.getEditText().getText().toString().trim();
        res = ResolveDate.getDateRes(dataBastismo);
        if( dataBastismo.equals ( "" ) || dataBastismo.length ( ) < 8 || res == true){
            validate = false;
            EditTextdataBastismo.setError(mensagem1);
            EditTextdataBastismo.setFocusable (true);
            EditTextdataBastismo.requestFocus ();
        }else{
            EditTextdataBastismo.setError(null);
        }
        String nomepai = EditTextnomepai.getEditText().getText().toString().trim();
        if(nomepai .equals ("")){
            validate = false;
            EditTextnomepai.setError(mensagem1);
            EditTextnomepai.setFocusable (true);
            EditTextnomepai.requestFocus ();
        }else{
            EditTextnomepai.setError(null);
        }
        String nomemae = EditTextnomemae.getEditText().getText().toString().trim();
        if(nomemae.equals ("")){
            validate = false;
            EditTextnomemae.setError(mensagem1);
            EditTextnomemae.setFocusable (true);
            EditTextnomemae.requestFocus ();
        }else{
            EditTextnomemae.setError(null);
        }
        String estadocivil =  EditTextestadocivil.getEditText().getText().toString().trim();
        if( estadocivil.equals ( "" )){
            validate = false;
            EditTextestadocivil.setError(mensagem1);
            EditTextestadocivil.setFocusable (true);
            EditTextestadocivil.requestFocus ();
        }else{
            EditTextestadocivil.setError(null);
        }
        String ddi = EdiTextddi.getEditText().getText().toString().trim();
        if( ddi.equals ( "" ) || ddi.length ( ) > 3 ){
            validate = false;
            EdiTextddi.setError(mensagem6);
            EdiTextddi.setFocusable (true);
            EdiTextddi.requestFocus ();
        }else{
            EdiTextddi.setError(null);
        }
        String telefone =  EditTexttelefone.getEditText().getText().toString().trim();
        if( telefone.equals ( "" ) || telefone.length ( ) < 14 ){
            validate = false;
            EditTexttelefone.setError(mensagem7);
            EditTexttelefone.setFocusable (true);
            EditTexttelefone.requestFocus ();
        }else{
            EditTexttelefone.setError(null);
        }
        useremail =  EditTextemail.getEditText().getText().toString().trim();
        String email = useremail;
        if(email .equals ("")|| email.length() < 8 || !email.contains ("@" )){
            validate = false;
            EditTextemail.setError(mensagem10);
            EditTextemail.setFocusable (true);
            EditTextemail.requestFocus ();
        }else{
            EditTextemail.setError(null);
        }
        String endereco =  EditTextendereco.getEditText().getText().toString().trim();
        if(endereco.equals ("")){
            validate = false;
            EditTextendereco.setError(mensagem1);
            EditTextendereco.setFocusable (true);
            EditTextendereco.requestFocus ();
        }else{
            EditTextendereco.setError(null);
        }
        String bairro = EditTextbairro.getEditText().getText().toString().trim();
        if(bairro.equals ("")){
            validate = false;
            EditTextbairro.setError(mensagem1);
            EditTextbairro.setFocusable (true);
            EditTextbairro.requestFocus ();
        }else{
            EditTextbairro.setError(null);
        }
        String cidade =  EditTextcidade.getEditText().getText().toString().trim();
        if(cidade.equals ("")){
            validate = false;
            EditTextcidade.setError(mensagem1);
            EditTextcidade.setFocusable (true);
            EditTextcidade.requestFocus ();
        }else{
            EditTextcidade.setError(null);
        }
        String estado =  EditTextestado.getEditText().getText().toString().trim();
        if(estado.equals ("")){
            validate = false;
            EditTextestado.setError(mensagem1);
            EditTextestado.setFocusable (true);
            EditTextestado.requestFocus ();
        }else{
            EditTextestado.setError(null);
        }
        String pais =  EditTextpais.getEditText().getText().toString().trim();
        if(pais.equals ("")){
            validate = false;
            EditTextpais.setError(mensagem1);
            EditTextpais.setFocusable (true);
            EditTextpais.requestFocus ();
        }else{
            EditTextpais.setError(null);
        }
        String cep = EditTextcep.getEditText().getText().toString().trim();
        if(cep.equals ("")){
            validate = false;
            EditTextcep.setError(mensagem1);
            EditTextcep.setFocusable (true);
            EditTextcep.requestFocus ();
        }else{
            EditTextcep.setError(null);
        }
        String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
        if(cargoIgreja.equals ("")|| cargoIgreja.length() < 3){
            validate = false;
            EditTextcargoIgreja.setError(mensagem6);
            EditTextcargoIgreja.setFocusable (true);
            EditTextcargoIgreja.requestFocus ();
        }else{
            EditTextcargoIgreja.setError(null);
        }
        String status = "1";
        final String igreja = HomeActivity.igreja;
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if( emaildual ){
            Toast.makeText( EditLeaderActivity.this, mensagem8 + " " + email, Toast.LENGTH_LONG ).show();
            return ;
        }
        if( validate ){
            if(!TextUtils.isEmpty( nome ) ) {
                leaders = databaseReference.child( "churchs/" + uidIgreja + "/leaders/");
                if ( uid != null ) {

                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put( "/celula" , celula);
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

                    if ( celula != mensagem9 ) {

                        pegandoConteudoCelula(celula);

                        //atualiza lider na célula
                        Map < String, Object > map1 = new HashMap <> ( );
                        map1.put ( celula + "/" + uidCelula + "/lider" , nome );

                       ref.child( "churchs/" + uidIgreja + "/cells/").updateChildren ( map1 );
                    }

                    Toast.makeText(this,mensagem2, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent ( EditLeaderActivity.this , LeaderActivity.class );
                    startActivity ( intent );

                }else {
                    Toast.makeText ( this , mensagem3 , Toast.LENGTH_LONG ).show ( );
                    if ( !typeUserAdmin ) {
                        Toast.makeText ( this , mensagem5 , Toast.LENGTH_LONG ).show ( );
                    }
                    Intent intent = new Intent ( EditLeaderActivity.this , HomeActivity.class );
                    startActivity ( intent );
                }
            }
        }

    }

    private void pegandoConteudoCelula(String name) {

        novaRef7 = databaseReference.child( "churchs/" + uidIgreja +"/cells/" + name);
        queryCelula = novaRef7;
        listenerCelula =  new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Celula c = ds.getValue( Celula.class );
                    uidCelula = Objects.requireNonNull( c ,"").getUid();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } ;
        queryCelula.addValueEventListener (listenerCelula);

    }

    private void readOnlyActive() {
        leaders = databaseReference.child( "churchs/" + uidIgreja + "/leaders/");
        query = leaders.orderByChild( "uid" ).equalTo (uid).limitToFirst(1);
        queryListener =  new ValueEventListener () {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for(DataSnapshot dados : dataSnapshot.getChildren()) {
                    try {
                       Leader l = dados.getValue (Leader.class);
                        if (l.getUid() != null && l.getUid() != null) {
                            if(l.getUid().equalsIgnoreCase (l.getUid()) ) {
                                String celula = l.getCelula().trim();
                                String nome = l.getNome().trim();
                                String idade = l.getIdade().trim ();
                                String sexo = l.getSexo ().trim ();
                                String dataNascimento = l.getDataNascimento ().trim ();
                                String dataBastismo = l.getDataBastismo ().trim ();
                                String nomepai = l.getNomepai ().trim ();
                                String nomemae = l.getNomemae ().trim ();
                                String estadocivil = l.getEstadocivil ().trim ();
                                String ddi = l.getDdi ().trim ();
                                String telefone = l.getTelefone ().trim ();
                                String email = l.getEmail ().trim ();
                                String endereco = l.getEndereco ().trim ();
                                String bairro = l.getBairro ().trim ();
                                String cidade = l.getCidade ().trim ();
                                String estado = l.getEstado ().trim ();
                                String pais = l.getPais ().trim ();
                                String cep = l.getCep ().trim ();
                                String cargoIgreja = l.getCargoIgreja ().trim ();

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
                                loadSpinner(celula);

                            }
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
                cels.add(mensagem9);
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
                ArrayAdapter <String> adapter = new ArrayAdapter<String>( EditLeaderActivity.this, R.layout.spinner_dropdown_item, cels);
                spCelula = findViewById( R.id.spinnerEditcelula );
                spCelula.setAdapter( adapter );

                spCelula = findViewById( R.id.spinnerEditcelula );
                int poAd = adapter.getPosition(celulaName);
                spCelula.setSelection (poAd);

                spCelula.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(spCelula.getSelectedItem().equals (0) ){ return; }
                        celula= (String)spCelula.getSelectedItem();
                        pegandoConteudoCelula(celula);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        celula = "";
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

    //private method of your class
    private int getIndex(Spinner sp, String pos){
        for (int i=0;i<sp.getCount();i++){
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(pos)){
                return i;
            }
        }

        return 0;
    }

    private void deleteUsuario(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder( EditLeaderActivity.this );
        builder1 = builder1.setMessage( mensagem13 + " " + useremail);
        builder1.setTitle( mensagem14 ).setCancelable( false ).setNegativeButton( mensagem15, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( getApplicationContext(), mensagem15, Toast.LENGTH_SHORT ).show();
            }
        } ).setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ref = databaseReference;
                    leaders = databaseReference.child( "churchs/" + uidIgreja + "/leaders/");
                    //apaga leader de membros
                    ref.child ( "churchs/" + uidIgreja ).child ("/members/").child(uid).removeValue ();

                    //Apaga leader em users/
                    leaders.child ( uid ).removeValue ( );

                    Toast.makeText ( EditLeaderActivity.this , mensagem11, Toast.LENGTH_SHORT ).show ( );

                    EditLeaderActivity.this.finish();
                    Intent intent = new Intent( EditLeaderActivity.this, LeaderActivity.class );
                    startActivity(intent);
                } catch ( Exception e ) {
                    Toast.makeText ( EditLeaderActivity.this , mensagem12, Toast.LENGTH_SHORT ).show ( );
                    e.printStackTrace ( );
                }
            }
        } );

        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

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
        queryCelula.removeEventListener (listenerCelula);
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