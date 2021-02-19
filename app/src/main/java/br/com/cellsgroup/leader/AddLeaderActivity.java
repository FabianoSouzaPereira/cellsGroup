package br.com.cellsgroup.leader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import br.com.cellsgroup.models.pessoas.User;
import br.com.cellsgroup.utils.MaskEditUtil;
import br.com.cellsgroup.utils.ResolveDate;

import static br.com.cellsgroup.home.HomeActivity.UI;
import static br.com.cellsgroup.home.HomeActivity.group;
import static br.com.cellsgroup.home.HomeActivity.typeUserAdmin;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremail;

public class AddLeaderActivity extends AppCompatActivity {

    public String DataTime;
    public String DataT;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference Leaders;
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
    private TextInputLayout EditTextcidade;
    private TextInputLayout EditTextestado;
    private TextInputLayout EditTextpais;
    private TextInputLayout EditTextcep;
    private TextInputLayout EditTextcargoIgreja;
    private boolean emaildual = false;
    private String email = "";
    private String emailTest = "";
    private String key;
    private static boolean validate = true;
    private static Boolean res = false;
    private final ArrayList<String> cels = new ArrayList<String>();

    private ArrayAdapter<String> arrayAdapterCelula;
    Query query;
    Query queryCelula;
    ValueEventListener queryListener;
    ValueEventListener listenerCelula;
    private String celula = "";
    private String uidCelula = "";
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_leader );
        Toolbar toolbar = findViewById( R.id.toolbarAddleader );
        setSupportActionBar( toolbar );

        mAuth = FirebaseAuth.getInstance();
        inicializarFirebase();
        inicializarComponentes();

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
        mensagem1 =  getResources ().getString (R.string.erroCampoObrigatorio);
        mensagem2 =  getResources ().getString (R.string.criadoleader);
        mensagem3 =  getResources ().getString (R.string.erroCriarleader);
        mensagem4 =  getResources ().getString (R.string.erroPreencherTudo);
        mensagem5 =  getResources ().getString (R.string.erroNaoAdmin);
        mensagem6 = getResources ().getString (R.string.erroCampo3digitos);
        mensagem7 = getResources ().getString (R.string.erroCampo11digitos);
        mensagem8 = getResources ().getString (R.string.erroEmailexiste);
        mensagem9 = getResources ().getString (R.string.escolhaCelula);
        mensagem10 = getResources ().getString (R.string.erroCampoInvalido);
        mensagem11 = getResources ().getString (R.string.erroCampo10digitos);
        loadSpinner();
    }
    public void loadSpinner(){
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
                                    String uid = c.getUid ();
                                    cels.add( celula );
                                }
                            }
                        }
                    }
                }

                ArrayAdapter <String> adapter = new ArrayAdapter<String>( AddLeaderActivity.this, R.layout.spinner_dropdown_item, cels);
                spCelula = findViewById( R.id.spinnercelula );
                spCelula.setAdapter( adapter );
                spCelula.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(spCelula.getSelectedItem().equals (0) ){ return; }
                        celula = (String)spCelula.getSelectedItem();
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
            }
        } ;

        query.addValueEventListener (queryListener );

    }

    private void inicializarFirebase() {
        Leaders = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();
        FirebaseApp.initializeApp(AddLeaderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    private void addLeaderClick(MenuItem item){
        addDataHora();
        validate = true;
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
        if(cargoIgreja.equals ("")|| cargoIgreja.length() < 4){
            validate = false;
            EditTextcargoIgreja.setError(mensagem1);
            EditTextcargoIgreja.setFocusable (true);
            EditTextcargoIgreja.requestFocus ();
        }else{
            EditTextcargoIgreja.setError(null);
        }
        String status = "1";
        final String igreja = HomeActivity.igreja;
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if( emaildual ){
            Toast.makeText( AddLeaderActivity.this, mensagem8 + " " + email, Toast.LENGTH_LONG ).show();
            return ;
        }
        if( validate ){
            if(!TextUtils.isEmpty( nome ) ) {

                String uid = Leaders.push ( ).getKey ( );
                User Leader = new User ( uid , nome , idade , sexo , dataNascimento , dataBastismo , nomepai , nomemae , estadocivil , ddi , telefone , email , endereco , bairro , cidade , estado, pais , cep , cargoIgreja , status , DataTime , igreja , userId , group,celula );

                if ( uid != null ) {
                    //Cria leader
                    Leaders.child ("churchs/" + uidIgreja + "/leaders/" ).child ( uid ).setValue ( Leader );

                    //atualiza membro na igreja
                    Map < String, Object > map = new HashMap <> ( );
                    map.put ( "/members/" + uid , telefone );

                    ref.child ( "churchs/" + uidIgreja ).updateChildren ( map );

                    if ( celula != mensagem9 ) {

                        pegandoConteudoCelula(celula);
                        //atualiza lider na célula
                        Map < String, Object > map1 = new HashMap <> ( );
                        map1.put ( celula + "/" + uidCelula + "/lider" , nome );

                        ref.child ( "churchs/" + uidIgreja + "/cells/").updateChildren ( map1 );
                    }

                    Toast.makeText ( this , mensagem2, Toast.LENGTH_SHORT ).show ( );

                    Intent intent = new Intent ( AddLeaderActivity.this , HomeActivity.class );
                    startActivity ( intent );

                }else {
                    Toast.makeText ( this , mensagem3 , Toast.LENGTH_LONG ).show ( );
                    if ( !typeUserAdmin ) {
                        Toast.makeText ( this , mensagem5 , Toast.LENGTH_LONG ).show ( );
                    }
                    Intent intent = new Intent ( AddLeaderActivity.this , HomeActivity.class );
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

        emailTest = EditTextemail.getEditText().getText().toString().trim();
        Leaders = databaseReference.child( "Leaders/" );
        Query query = Leaders.orderByChild( "email" ).equalTo(email).limitToFirst(limitebusca);
        query.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot dados : ds.getChildren()) {
                            Leader u = dados.getValue( Leader.class );
                            email = u.getEmail();
                        }
                    }
                    if (email == emailTest) {
                        emaildual = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        } );
    }

    @Override
    public void onBackPressed() {
        AddLeaderActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save_cancel , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save){
            addLeaderClick(item);
            return true;
        }
        if(id == R.id.action_Cancel){
            AddLeaderActivity.this.finish();
            Intent intent = new Intent( AddLeaderActivity.this, LeaderActivity.class );
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        DataTime = data + " "+ hora;
        DataT = data;
    }

    @Override
    protected void onPause ( ) {
        query.removeEventListener(queryListener );
        queryCelula.removeEventListener (listenerCelula);
        super.onPause ( );
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