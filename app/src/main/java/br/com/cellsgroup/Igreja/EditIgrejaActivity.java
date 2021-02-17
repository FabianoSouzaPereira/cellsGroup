package br.com.cellsgroup.Igreja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.igreja.Igreja;
import br.com.cellsgroup.utils.MaskEditUtil;

import static br.com.cellsgroup.home.HomeActivity.igreja;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;

public class EditIgrejaActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private DatabaseReference novaRef2;

    private Query query;
    private ValueEventListener listener;
    private String uid_extra;
    private String nome_extra;
    private static boolean validate = true;

    TextInputLayout denominacao;
    TextInputLayout editIgreja;
    TextInputLayout editEndereco;
    TextInputLayout editBairro;
    TextInputLayout editCidade;
    TextInputLayout editEstado;
    TextInputLayout editPais;
    TextInputLayout editCep;
    TextInputLayout editDdi;
    TextInputLayout editPhone;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_igreja );
        Toolbar toolbar = findViewById( R.id.toolbarEdit_igreja );
        setSupportActionBar( toolbar );

        inicializaFirebase();
        inicializarComponentes();
        Intent intent = getIntent();
        uid_extra = intent.getStringExtra( "Igreja" );
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
                denominacao.setError("Este campo é obrigatório");
                denominacao.setFocusable (true);
                denominacao.requestFocus ();
            }else{
                denominacao.setError(null);
            }
            String igrejanome = Objects.requireNonNull ( editIgreja.getEditText ( ), "" ).getText ().toString().trim();
            if(igreja .equals ("")|| igreja.length() < 4){
                validate = false;
                editIgreja.setError("Este campo é obrigatório");
                editIgreja.setFocusable (true);
                editIgreja.requestFocus ();
            }else{
                editIgreja.setError(null);
            }
            String endereco = Objects.requireNonNull ( editEndereco.getEditText ( ), "" ).getText ().toString().trim();
            if(endereco.equals ("")){
                validate = false;
                editEndereco.setError("Este campo é obrigatório");
                editEndereco.setFocusable (true);
                editEndereco.requestFocus ();
            }else{
                editEndereco.setError(null);
            }
            String bairro = Objects.requireNonNull ( editBairro.getEditText ( ), "" ).getText ().toString().trim();
            if(bairro.equals ("")){
                validate = false;
                editBairro.setError("Este campo é obrigatório");
                editBairro.setFocusable (true);
                editBairro.requestFocus ();
            }else{
                editBairro.setError(null);
            }
            String cidade = Objects.requireNonNull ( editCidade.getEditText ( ), "" ).getText ().toString().trim();
            if(cidade.equals ("")){
                validate = false;
                editCidade.setError("Este campo é obrigatório");
                editCidade.setFocusable (true);
                editCidade.requestFocus ();
            }else{
                editCidade.setError(null);
            }
            String estado = Objects.requireNonNull ( editEstado.getEditText ( ), "" ).getText ().toString().trim();
            if(estado.equals ("")){
                validate = false;
                editEstado.setError("Este campo é obrigatório");
                editEstado.setFocusable (true);
                editEstado.requestFocus ();
            }else{
                editEstado.setError(null);
            }
            String pais = Objects.requireNonNull ( editPais.getEditText ( ), "" ).getText ().toString().trim();
            if(pais.equals ("")){
                validate = false;
                editPais.setError("Este campo é obrigatório");
                editPais.setFocusable (true);
                editPais.requestFocus ();
            }else{
                editPais.setError(null);
            }
            String cep = Objects.requireNonNull ( editCep.getEditText ( ), "" ).getText ().toString().trim();
            if(cep.equals ("")){
                validate = false;
                editCep.setError("Este campo é obrigatório");
                editCep.setFocusable (true);
                editCep.requestFocus ();
            }else{
                editCep.setError(null);
            }
            String ddi = Objects.requireNonNull ( editDdi.getEditText ( ), "" ).getText ().toString().trim();
            if( ddi.equals ( "" ) || ddi.length ( ) > 3 ){
                validate = false;
                editDdi.setError("Este campo é obrigatório, 3 dígitos.");
                editDdi.setFocusable (true);
                editDdi.requestFocus ();
            }else{
                editDdi.setError(null);
            }
            String fone = Objects.requireNonNull ( editPhone.getEditText ( ), "" ).getText ().toString().trim();
            if( fone.equals ( "" ) || fone.length ( ) < 9 ){
                validate = false;
                editPhone.setError("Este campo é obrigatório, min. 9 dígitos.");
                editPhone.setFocusable (true);
                editPhone.requestFocus ();
            }else{
                editPhone.setError(null);
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

                    novaRef2.updateChildren ( IgrejaUpdates );

                    Toast.makeText ( this , "Editado célula com sucesso" , Toast.LENGTH_LONG ).show ( );

                    Intent intent = new Intent ( EditIgrejaActivity.this , IgrejasCriadasActivity.class );
                    intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity ( intent );
                    finish ( );

                } else {
                    Toast.makeText ( this , "Erro ao tentar Editar célula !" , Toast.LENGTH_LONG ).show ( );
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