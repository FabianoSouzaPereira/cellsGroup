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
    private final ArrayList<String> cels = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterCelula;
    Query query;
    ValueEventListener queryListener;
    private String celula;


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
                ArrayAdapter <String> adapter = new ArrayAdapter<String>( AddLeaderActivity.this, R.layout.spinner_dropdown_item, cels);
                spCelula = findViewById( R.id.spinnercelula );
                spCelula.setAdapter( adapter );
                spCelula.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(spCelula.getSelectedItem().equals (0) ){ return; }
                        celula = (String)spCelula.getSelectedItem();

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
        validate=true;
        String nome =  EditTextnome.getEditText().getText().toString().trim();
        if(nome.equals ("")|| nome.length() < 4){
            validate = false;
            EditTextnome.setError("Este campo é obrigatório");
            EditTextnome.setFocusable (true);
            EditTextnome.requestFocus ();
        }
        String idade =  EditTextidade.getEditText ( ).getText().toString().trim();
        if( idade.equals ( "" )){
            validate = false;
            EditTextidade.setError("Este campo é obrigatório");
            EditTextidade.setFocusable (true);
            EditTextidade.requestFocus ();
        }
        String sexo = EditTextsexo.getEditText ( ).getText().toString().trim();
        if( sexo.equals ( "" )){
            validate = false;
            EditTextsexo.setError("Este campo é obrigatório");
            EditTextsexo.setFocusable (true);
            EditTextsexo.requestFocus ();
        }
        String dataNascimento = EditTextdataNascimento.getEditText().getText().toString().trim();
        if( dataNascimento .equals ( "" ) || dataNascimento .length ( ) < 8 ){
            validate = false;
            EditTextdataNascimento.setError("Este campo é obrigatório");
            EditTextdataNascimento.setFocusable (true);
            EditTextdataNascimento.requestFocus ();
        }
        String dataBastismo = EditTextdataBastismo.getEditText().getText().toString().trim();
        if( dataBastismo.equals ( "" ) || dataBastismo.length ( ) < 8 ){
            validate = false;
            EditTextdataBastismo.setError("Este campo é obrigatório");
            EditTextdataBastismo.setFocusable (true);
            EditTextdataBastismo.requestFocus ();
        }
        String nomepai = EditTextnomepai.getEditText().getText().toString().trim();
        if(nomepai .equals ("")){
            validate = false;
            EditTextnomepai.setError("Este campo é obrigatório");
            EditTextnomepai.setFocusable (true);
            EditTextnomepai.requestFocus ();
        }
        String nomemae = EditTextnomemae.getEditText().getText().toString().trim();
        if(nomemae.equals ("")){
            validate = false;
            EditTextnomemae.setError("Este campo é obrigatório");
            EditTextnomemae.setFocusable (true);
            EditTextnomemae.requestFocus ();
        }
        String estadocivil =  EditTextestadocivil.getEditText().getText().toString().trim();
        if( estadocivil.equals ( "" )){
            validate = false;
            EditTextestadocivil.setError("Este campo é obrigatório, dois dígitos");
            EditTextestadocivil.setFocusable (true);
            EditTextestadocivil.requestFocus ();
        }
        String ddi = EdiTextddi.getEditText().getText().toString().trim();
        if( ddi.equals ( "" ) || ddi.length ( ) > 3 ){
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
        if(endereco.equals ("")){
            validate = false;
            EditTextendereco.setError("Este campo é obrigatório");
            EditTextendereco.setFocusable (true);
            EditTextendereco.requestFocus ();
        }
        String bairro = EditTextbairro.getEditText().getText().toString().trim();
        if(bairro.equals ("")){
            validate = false;
            EditTextbairro.setError("Este campo é obrigatório");
            EditTextbairro.setFocusable (true);
            EditTextbairro.requestFocus ();
        }
        String cidade =  EditTextcidade.getEditText().getText().toString().trim();
        if(cidade.equals ("")){
            validate = false;
            EditTextcidade.setError("Este campo é obrigatório");
            EditTextcidade.setFocusable (true);
            EditTextcidade.requestFocus ();
        }
        String estado =  EditTextestado.getEditText().getText().toString().trim();
        if(estado.equals ("")){
            validate = false;
            EditTextestado.setError("Este campo é obrigatório");
            EditTextestado.setFocusable (true);
            EditTextestado.requestFocus ();
        }
        String pais =  EditTextpais.getEditText().getText().toString().trim();
        if(pais.equals ("")){
            validate = false;
            EditTextpais.setError("Este campo é obrigatório");
            EditTextpais.setFocusable (true);
            EditTextpais.requestFocus ();
        }
        String cep = EditTextcep.getEditText().getText().toString().trim();
        if(cep.equals ("")){
            validate = false;
            EditTextcep.setError("Este campo é obrigatório");
            EditTextcep.setFocusable (true);
            EditTextcep.requestFocus ();
        }
        String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
        if(cargoIgreja.equals ("")|| cargoIgreja.length() < 4){
            validate = false;
            EditTextcargoIgreja.setError("Obrigatório +4 digitos.");
            EditTextcargoIgreja.setFocusable (true);
            EditTextcargoIgreja.requestFocus ();
        }
        String status = "1";
        final String igreja = HomeActivity.igreja;
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if( emaildual ){
            Toast.makeText( AddLeaderActivity.this, "Já existe um cadastro com esse mesmo email " + email, Toast.LENGTH_LONG ).show();
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

                    Toast.makeText ( this , "Criado leader com sucesso" , Toast.LENGTH_LONG ).show ( );

                    Intent intent = new Intent ( AddLeaderActivity.this , HomeActivity.class );
                    startActivity ( intent );
                }else {
                    Toast.makeText ( this , "Erro ao tentar criar leader !" , Toast.LENGTH_LONG ).show ( );
                    if ( !typeUserAdmin ) {
                        Toast.makeText ( this , "Você não é um leader administrador. \n Não pode criar leader admin !" , Toast.LENGTH_LONG ).show ( );
                    }
                    Intent intent = new Intent ( AddLeaderActivity.this , HomeActivity.class );
                    startActivity ( intent );
                }
            }
        }

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