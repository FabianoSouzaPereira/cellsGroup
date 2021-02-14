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
import static java.lang.System.currentTimeMillis;

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
    private FirebaseAuth mAuth;
    private static boolean validate = true;
    private Query query;
    private ValueEventListener listener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private String denominacao ="";


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
    }


    private void addIgrejaClick(MenuItem item) {
        addDataHora();
        igreja = "";
        validate=true;

        try {
            denominacao = editDenominacao.getEditText().getText().toString().trim();
            if(denominacao .equals ("")|| denominacao.length() < 4){
                validate = false;
                editDenominacao.setError("Este campo é obrigatório");
                editDenominacao.setFocusable (true);
                editDenominacao.requestFocus ();
            }
             String igrejaName = editIgreja.getEditText().getText().toString().trim();
            igreja = igrejaName;
            if(igreja .equals ("")|| igreja.length() < 4){
                validate = false;
                editIgreja.setError("Este campo é obrigatório");
                editIgreja.setFocusable (true);
                editIgreja.requestFocus ();
            }
            String endereco = editEndereco.getEditText().getText().toString().trim();
            if(endereco.equals ("")){
                validate = false;
                editEndereco.setError("Este campo é obrigatório");
                editEndereco.setFocusable (true);
                editEndereco.requestFocus ();
            }
            String bairro = editBairro.getEditText().getText().toString().trim();
            if(bairro.equals ("")){
                validate = false;
                editBairro.setError("Este campo é obrigatório");
                editBairro.setFocusable (true);
                editBairro.requestFocus ();
            }
            String cidade = editCidade.getEditText().getText().toString().trim();
            if(cidade.equals ("")){
                validate = false;
                editCidade.setError("Este campo é obrigatório");
                editCidade.setFocusable (true);
                editCidade.requestFocus ();
            }
            String estado = editEstado.getEditText().getText().toString().trim();
            if(estado.equals ("")){
                validate = false;
                editEstado.setError("Este campo é obrigatório");
                editEstado.setFocusable (true);
                editEstado.requestFocus ();
            }
            String pais_ = editPais.getEditText().getText().toString().trim();
            if(pais_.equals ("")){
                validate = false;
                editPais.setError("Este campo é obrigatório");
                editPais.setFocusable (true);
                editPais.requestFocus ();
            }
            String cep = editCep.getEditText().getText().toString().trim();
            if(cep.equals ("")){
                validate = false;
                editCep.setError("Este campo é obrigatório");
                editCep.setFocusable (true);
                editCep.requestFocus ();
            }
            String ddi = editddi.getEditText().getText().toString().trim();
            if( ddi.equals ( "" ) || ddi.length ( ) > 3 ){
                validate = false;
                editddi.setError("Este campo é obrigatório, 3 dígitos.");
                editddi.setFocusable (true);
                editddi.requestFocus ();
            }
            String phone  = editPhone.getEditText().getText().toString().trim();
            if( phone.equals ( "" ) || phone.length ( ) < 9 ){
                validate = false;
                editPhone.setError("Este campo é obrigatório, min. 9 dígitos.");
                editPhone.setFocusable (true);
                editPhone.requestFocus ();
            }
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String members = "";

            if(validate) {
                //insere igreja
                if ( !TextUtils.isEmpty ( igreja ) && Logado == true && typeUserAdmin == true ) {
                    String uid = ref.push ( ).getKey ( );
                    String igrejaID = uid;
                    Igreja ig = new Igreja ( uid , igreja , endereco , bairro , cidade , estado , pais_ , cep , ddi , phone , DataTime , userId , status , denominacao , igrejaID , members );
                    ref.child ( "churchs/" ).child ( uid + "/" + igreja ).setValue ( ig );

                    //insere no groups de igrejas
                    Map < String, Object > map1 = new HashMap <> ( );
                    map1.put ( "members/" + uid , igreja );
                    ref2.child ( "groups/" ).child ( denominacao ).updateChildren ( map1 );

                    clearEditTexts ( );
                    Toast.makeText ( this , "Criado Igreja com sucesso" , Toast.LENGTH_LONG ).show ( );
                }
                Intent home = new Intent( addIgrejaActivity.this, HomeActivity.class);
                startActivity(home);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( this, "Erro ao tentar criar a igreja!", Toast.LENGTH_LONG ).show();
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
