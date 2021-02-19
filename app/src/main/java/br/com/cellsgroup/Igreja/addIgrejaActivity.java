package br.com.cellsgroup.Igreja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.models.igreja.Igreja;
import br.com.cellsgroup.utils.MaskEditUtil;

import static br.com.cellsgroup.home.HomeActivity.Logado;
import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.typeUserAdmin;

public class addIgrejaActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private DatabaseReference ref2;
    public String DataTime;
    public String DataT;
    public String status = "1";
    private TextInputLayout editDenominacao;
    private TextInputLayout editIgreja;
    private TextInputLayout editEndereco;
    private TextInputLayout editBairro;
    private TextInputLayout editCidade;
    private TextInputLayout editEstado;
    private TextInputLayout editPais;
    private TextInputLayout editCep;
    private TextInputLayout editddi;
    private TextInputLayout editPhone;
    private TextInputLayout editPhoneFixo;
    private FirebaseAuth mAuth;
    private static boolean validate = true;
    private Query query;
    private ValueEventListener listener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private String denominacao ="";
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_igreja );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        addDataHora();
        inicializarFirebase();
        inicializarComponentes();

        mAuth = FirebaseAuth.getInstance();

    }

    private void inicializarFirebase() {
        ref = FirebaseDatabase.getInstance().getReference();
        ref2 = FirebaseDatabase.getInstance().getReference();
        FirebaseApp.initializeApp(addIgrejaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializarComponentes() {
        editDenominacao = findViewById( R.id.text_input_denominacao );
        editIgreja = findViewById( R.id.text_input_editIgreja );
        editEndereco = findViewById( R.id.text_input_editEndereco );
        editBairro = findViewById( R.id.text_input_editBairro );
        editCidade = findViewById( R.id.text_input_editCidade );
        editEstado = findViewById( R.id.text_input_editEstado );
        editPais = findViewById( R.id.text_input_editPais_ );
        editCep = findViewById( R.id.text_input_editCep );
        editCep.getEditText ().addTextChangedListener ( MaskEditUtil.mask(editCep, MaskEditUtil.FORMAT_CEP));
        editddi = findViewById( R.id.text_input_editddi);
        editPhone= findViewById (R.id.text_input_phone );
        editPhone.getEditText ().addTextChangedListener ( MaskEditUtil.mask(editPhone, MaskEditUtil.FORMAT_FONE));
        editPhoneFixo = findViewById (R.id.text_input_Fixo);
        editPhoneFixo.getEditText ().addTextChangedListener ( MaskEditUtil.mask(editPhoneFixo, MaskEditUtil.FORMAT_FONE_FIXO));
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
        mensagem12 = getResources ().getString (R.string.criadoIgrejasuccess);
        mensagem13 = getResources ().getString (R.string.erroCrairIgreja);
    }


    private void addIgrejaClick(MenuItem item) {
        addDataHora();
        igreja = "";
        validate=true;

        try {
            denominacao = editDenominacao.getEditText().getText().toString().trim();
            if(denominacao .equals ("")|| denominacao.length() < 4){
                validate = false;
                editDenominacao.setError(mensagem1);
                editDenominacao.setFocusable (true);
                editDenominacao.requestFocus ();
            }else{
                editDenominacao.setError(null);
            }
             String igrejaName = editIgreja.getEditText().getText().toString().trim();
            if(igrejaName.equals ("")|| igrejaName.length() < 4){
                validate = false;
                editIgreja.setError(mensagem1);
                editIgreja.setFocusable (true);
                editIgreja.requestFocus ();
            }else{
                editIgreja.setError(null);
            }
            String endereco = editEndereco.getEditText().getText().toString().trim();
            if(endereco.equals ("")){
                validate = false;
                editEndereco.setError(mensagem1);
                editEndereco.setFocusable (true);
                editEndereco.requestFocus ();
            }else{
                editEndereco.setError(null);
            }
            String bairro = editBairro.getEditText().getText().toString().trim();
            if(bairro.equals ("")){
                validate = false;
                editBairro.setError(mensagem1);
                editBairro.setFocusable (true);
                editBairro.requestFocus ();
            }else{
                editBairro.setError(null);
            }
            String cidade = editCidade.getEditText().getText().toString().trim();
            if(cidade.equals ("")){
                validate = false;
                editCidade.setError(mensagem1);
                editCidade.setFocusable (true);
                editCidade.requestFocus ();
            }else{
                editCidade.setError(null);
            }
            String estado = editEstado.getEditText().getText().toString().trim();
            if(estado.equals ("")){
                validate = false;
                editEstado.setError(mensagem1);
                editEstado.setFocusable (true);
                editEstado.requestFocus ();
            }else{
                editEstado.setError(null);
            }
            String pais_ = editPais.getEditText().getText().toString().trim();
            if(pais_.equals ("")){
                validate = false;
                editPais.setError(mensagem1);
                editPais.setFocusable (true);
                editPais.requestFocus ();
            }else{
                editPais.setError(null);
            }
            String cep = editCep.getEditText().getText().toString().trim();
            if(cep.equals ("")){
                validate = false;
                editCep.setError(mensagem1);
                editCep.setFocusable (true);
                editCep.requestFocus ();
            }else{
                editCep.setError(null);
            }
            String ddi = editddi.getEditText().getText().toString().trim();
            if( ddi.equals ( "" ) || ddi.length ( ) > 3 ){
                validate = false;
                editddi.setError(mensagem6);
                editddi.setFocusable (true);
                editddi.requestFocus ();
            }else{
                editddi.setError(null);
            }
            String phone  = editPhone.getEditText().getText().toString().trim();
            if( phone.equals ( "" ) || phone.length ( ) < 14 ){
                validate = false;
                editPhone.setError(mensagem7);
                editPhone.setFocusable (true);
                editPhone.requestFocus ();
            }else{
                editPhone.setError(null);
            }
            String phone_fixo  = editPhoneFixo.getEditText().getText().toString().trim();
            // pode ser vazio, mas se tiver valor ter'que ser maior que oito
            if( phone_fixo.length ( ) > 0 && phone_fixo.length ( ) < 13 ){
                validate = false;
                editPhoneFixo.setError(mensagem11);
                editPhoneFixo.setFocusable (true);
                editPhoneFixo.requestFocus ();
            }else{
                editPhoneFixo.setError(null);
            }
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String members = "";

            if(validate) {
                //insere igreja
                if ( !TextUtils.isEmpty ( igrejaName ) && Logado == true && typeUserAdmin == true ) {
                    String uid = ref.push ( ).getKey ( );
                    String igrejaID = uid;
                    Igreja ig = new Igreja ( uid , igrejaName , endereco , bairro , cidade , estado , pais_ , cep , ddi , phone , phone_fixo, DataTime , userId , status , denominacao , igrejaID , members );
                    ref.child ( "churchs/" ).child ( uid + "/" + igrejaName ).setValue ( ig );

                    //insere no groups de igrejas
                    Map < String, Object > map1 = new HashMap <> ( );
                    map1.put ( "members/" + uid , igrejaName );
                    ref2.child ( "groups/" ).child ( denominacao ).updateChildren ( map1 );

                    clearEditTexts ( );
                    Toast.makeText ( this ,mensagem12 , Toast.LENGTH_LONG ).show ( );
                }
                Intent home = new Intent( addIgrejaActivity.this, HomeActivity.class);
                startActivity(home);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( this, mensagem13, Toast.LENGTH_LONG ).show();
        }
    }

    private void updateUsuario(){
        try {
            mAuth = FirebaseAuth.getInstance();
            String userId = mAuth.getCurrentUser().getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child( "users" ).child( userId );

            Map<String, Object> usuarioUpdates = new HashMap<>();
            usuarioUpdates.put( "users" + userId + "/igrejaPadrao" , igreja);

            databaseReference.updateChildren( usuarioUpdates );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        addIgrejaActivity.this.finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save_edit_delete , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_save){
            addIgrejaClick(item);
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    private void clearEditTexts(){
            editIgreja.getEditText().getText().toString().trim();
            editEndereco.getEditText().setText( "" );
            editBairro.getEditText().setText( "" );
            editCidade.getEditText().setText( "" );
            editEstado.getEditText().setText( "" );
            editPais.getEditText().setText( "" );
            editCep.getEditText().setText( "" );
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
