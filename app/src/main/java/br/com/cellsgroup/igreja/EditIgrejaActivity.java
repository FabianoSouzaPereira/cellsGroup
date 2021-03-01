package br.com.cellsgroup.igreja;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.igreja.Igreja;
import br.com.cellsgroup.utils.MaskEditUtil;

import static br.com.cellsgroup.home.HomeActivity.igreja;

public class EditIgrejaActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private DatabaseReference novaRef2;

    private ValueEventListener listener;
    private String uid_extra;
    private String nome_extra;
    private static boolean validate = true;

    private TextInputLayout denominacao;
    private TextInputLayout editIgreja;
    private TextInputLayout editEndereco;
    private TextInputLayout editBairro;
    private TextInputLayout editCidade;
    private TextInputLayout editEstado;
    private TextInputLayout editPais;
    private TextInputLayout editCep;
    private TextInputLayout editDdi;
    private TextInputLayout editPhone;
    private TextInputLayout editPhoneFixo;
    String mensagem1 = "";
    String mensagem2 = "";
    String mensagem3 = "";
    String mensagem6 = "";
    String mensagem7 = "";
    String mensagem11 = "";

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_igreja );
        Toolbar toolbar = findViewById( R.id.toolbarEdit_igreja );
        setSupportActionBar( toolbar );

        inicializaFirebase();
        inicializarComponentes();
        Intent intent = getIntent();
        uid_extra = intent.getStringExtra( "igreja" );
        nome_extra = intent.getStringExtra( "Nome" );
        readIgrejaCadastrada();

    }

    private void inicializarComponentes ( ) {
        denominacao = findViewById (R.id.text_input_denominacao);
        editIgreja = findViewById (R.id.text_input_editIgreja);
        editEndereco = findViewById (R.id.text_input_editEndereco);
        editBairro = findViewById (R.id.text_input_editBairro);
        editCidade = findViewById (R.id.text_input_editCidade);
        editEstado = findViewById (R.id.text_input_editEstado );
        editPais = findViewById (R.id.text_input_editPais_);
        editCep = findViewById (R.id.text_input_editCep);
        editCep.getEditText ().addTextChangedListener ( MaskEditUtil.mask(editCep, MaskEditUtil.FORMAT_CEP));
        editDdi = findViewById (R.id.text_input_editddi);
        editPhone = findViewById (R.id.text_input_phone);
        editPhone.getEditText ().addTextChangedListener ( MaskEditUtil.mask(editPhone, MaskEditUtil.FORMAT_FONE));
        editPhoneFixo = findViewById (R.id.text_input_Fixo);
        editPhoneFixo.getEditText ().addTextChangedListener ( MaskEditUtil.mask(editPhoneFixo, MaskEditUtil.FORMAT_FONE_FIXO));
        mensagem1 =  getResources ().getString (R.string.erroCampoObrigatorio);
        mensagem2 =  getResources ().getString (R.string.editadoIgrejasuccess);
        mensagem3 =  getResources ().getString (R.string.erroEditarIgreja);
        mensagem6 = getResources ().getString (R.string.erroCampo3digitos);
        mensagem7 = getResources ().getString (R.string.erroCampo11digitos);
        mensagem11 = getResources ().getString (R.string.erroCampo10digitos);

    }

    private void readIgrejaCadastrada() {
        final String ui = HomeActivity.UI.getUid ();
        novaRef = databaseReference.child( "churchs/" + uid_extra);
        Query query = novaRef.orderByChild ("user").equalTo (ui);
        listener =  new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot sd : dataSnapshot.getChildren()) {
                        String key = sd.getKey ();
                        if(!key.equalsIgnoreCase ( "members" )
                            && !key.equalsIgnoreCase ( "leaders" )
                            && !key.equalsIgnoreCase ( "cells" )
                            && !key.equalsIgnoreCase ( "reports" )
                            && !key.equalsIgnoreCase ( "intercession" )
                        ) {

                            Igreja igr = sd.getValue ( Igreja.class );
                            if(igr.getUser ().equals (ui)) {
                                String group = Objects.requireNonNull ( igr ).getGroup ( );
                                Objects.requireNonNull ( denominacao.getEditText ( ) , "" ).setText ( group );
                                Objects.requireNonNull ( editIgreja.getEditText ( ) , "" ).setText ( igr.getNome ( ) );
                                Objects.requireNonNull ( editEndereco.getEditText ( ) , "" ).setText ( igr.getEndereco ( ) );
                                Objects.requireNonNull ( editBairro.getEditText ( ) , "" ).setText ( igr.getBairro ( ) );
                                Objects.requireNonNull ( editCidade.getEditText ( ) , "" ).setText ( igr.getCidade ( ) );
                                Objects.requireNonNull ( editEstado.getEditText ( ) , "" ).setText ( igr.getEstado ( ) );
                                Objects.requireNonNull ( editPais.getEditText ( ) , "" ).setText ( igr.getPais_ ( ) );
                                Objects.requireNonNull ( editCep.getEditText ( ) , "" ).setText ( igr.getCep ( ) );
                                Objects.requireNonNull ( editDdi.getEditText ( ) , "" ).setText ( igr.getDdi ( ) );
                                Objects.requireNonNull ( editPhone.getEditText ( ) , "" ).setText ( igr.getPhone ( ) );
                                Objects.requireNonNull ( editPhoneFixo.getEditText ( ) , "" ).setText ( igr.getPhone_fixo () );
                            }
                        }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent (listener);
        query.removeEventListener (listener);
    }


    private void saveIgrejaClick ( MenuItem item ) {
        igreja = "";
        validate=true;

        try {
            String denom = Objects.requireNonNull ( denominacao.getEditText ( ), "" ).getText ().toString().trim();
            if(denom .equals ("")|| denom.length() < 4){
                validate = false;
                denominacao.setError(mensagem1);
                denominacao.setFocusable (true);
                denominacao.requestFocus ();
            }else{
                denominacao.setError(null);
            }
            String igrejanome = Objects.requireNonNull ( editIgreja.getEditText ( ), "" ).getText ().toString().trim();
            if(igrejanome .equals ("")|| igrejanome.length() < 4){
                validate = false;
                editIgreja.setError(mensagem1);
                editIgreja.setFocusable (true);
                editIgreja.requestFocus ();
            }else{
                editIgreja.setError(null);
            }
            String endereco = Objects.requireNonNull ( editEndereco.getEditText ( ), "" ).getText ().toString().trim();
            if(endereco.equals ("")){
                validate = false;
                editEndereco.setError(mensagem1);
                editEndereco.setFocusable (true);
                editEndereco.requestFocus ();
            }else{
                editEndereco.setError(null);
            }
            String bairro = Objects.requireNonNull ( editBairro.getEditText ( ), "" ).getText ().toString().trim();
            if(bairro.equals ("")){
                validate = false;
                editBairro.setError(mensagem1);
                editBairro.setFocusable (true);
                editBairro.requestFocus ();
            }else{
                editBairro.setError(null);
            }
            String cidade = Objects.requireNonNull ( editCidade.getEditText ( ), "" ).getText ().toString().trim();
            if(cidade.equals ("")){
                validate = false;
                editCidade.setError(mensagem1);
                editCidade.setFocusable (true);
                editCidade.requestFocus ();
            }else{
                editCidade.setError(null);
            }
            String estado = Objects.requireNonNull ( editEstado.getEditText ( ), "" ).getText ().toString().trim();
            if(estado.equals ("")){
                validate = false;
                editEstado.setError(mensagem1);
                editEstado.setFocusable (true);
                editEstado.requestFocus ();
            }else{
                editEstado.setError(null);
            }
            String pais = Objects.requireNonNull ( editPais.getEditText ( ), "" ).getText ().toString().trim();
            if(pais.equals ("")){
                validate = false;
                editPais.setError(mensagem1);
                editPais.setFocusable (true);
                editPais.requestFocus ();
            }else{
                editPais.setError(null);
            }
            String cep = Objects.requireNonNull ( editCep.getEditText ( ), "" ).getText ().toString().trim();
            if(cep.equals ("")){
                validate = false;
                editCep.setError(mensagem1);
                editCep.setFocusable (true);
                editCep.requestFocus ();
            }else{
                editCep.setError(null);
            }
            String ddi = Objects.requireNonNull ( editDdi.getEditText ( ), "" ).getText ().toString().trim();
            if( ddi.equals ( "" ) || ddi.length ( ) > 3 ){
                validate = false;
                editDdi.setError(mensagem6);
                editDdi.setFocusable (true);
                editDdi.requestFocus ();
            }else{
                editDdi.setError(null);
            }
            String fone = Objects.requireNonNull ( editPhone.getEditText ( ), "" ).getText ().toString().trim();
            if( fone.equals ( "" ) || fone.length ( ) < 14 ){
                validate = false;
                editPhone.setError(mensagem7);
                editPhone.setFocusable (true);
                editPhone.requestFocus ();
            }else{
                editPhone.setError(null);
            }
            String phone_fixo  = editPhoneFixo.getEditText().getText().toString().trim();
            // pode ser vazio, mas se tiver valor, tem que ser maior que 10
            if( phone_fixo.length ( ) > 0 && phone_fixo.length ( ) < 13 ){
                validate = false;
                editPhoneFixo.setError(mensagem11);
                editPhoneFixo.setFocusable (true);
                editPhoneFixo.requestFocus ();
            }else{
                editPhoneFixo.setError(null);
            }
            //  String hora = hh + ":" + mm;
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            novaRef2 =databaseReference.child( "churchs/").child( uid_extra );
            if(validate) {
                if ( !TextUtils.isEmpty ( igrejanome ) ) {
                    Map < String, Object > IgrejaUpdates = new HashMap <> ( );
                    IgrejaUpdates.put ( nome_extra + "/group" , denom );
                    IgrejaUpdates.put ( nome_extra + "/nome" , igrejanome );
                    IgrejaUpdates.put ( nome_extra + "/endereco" , endereco );
                    IgrejaUpdates.put ( nome_extra + "/bairro" , bairro );
                    IgrejaUpdates.put ( nome_extra + "/cidade" , cidade );
                    IgrejaUpdates.put ( nome_extra + "/estado" , estado );
                    IgrejaUpdates.put ( nome_extra + "/pais_" , pais );
                    IgrejaUpdates.put ( nome_extra + "/cep" , cep );
                    IgrejaUpdates.put ( nome_extra + "/ddi" , ddi );
                    IgrejaUpdates.put ( nome_extra + "/phone" , fone );
                    IgrejaUpdates.put ( nome_extra + "/phone_fixo" , phone_fixo );

                    novaRef2.updateChildren ( IgrejaUpdates );

                    Toast.makeText ( this , mensagem2, Toast.LENGTH_LONG ).show ( );

                    Intent intent = new Intent ( EditIgrejaActivity.this , IgrejasCriadasActivity.class );
                    intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity ( intent );
                    finish ( );

                } else {
                    Toast.makeText ( this , mensagem3, Toast.LENGTH_LONG ).show ( );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }

    }

    @Override
    public void onBackPressed() {
        EditIgrejaActivity.this.finish ();
       super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save_cancel , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save){
            saveIgrejaClick(item);
            return true;
        }
        if(id == R.id.action_Cancel){
            EditIgrejaActivity.this.finish ();
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    private void inicializaFirebase() {
        FirebaseApp.initializeApp(EditIgrejaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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