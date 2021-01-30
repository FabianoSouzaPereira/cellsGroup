package br.com.cellsgroup.Igreja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Adapters.AdapterListViewIgreja;
import br.com.cellsgroup.R;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.celulas.EditCelulaActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.igreja.Igreja;

import static br.com.cellsgroup.home.HomeActivity.igreja;

public class EditIgrejaActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private DatabaseReference novaRef2;

    private Query query;
    private ValueEventListener listener;
    private String uid_extra;
    private String nome_extra;

    TextInputLayout denominacao;
    TextInputLayout editIgreja;
    TextInputLayout editEndereco;
    TextInputLayout editBairro;
    TextInputLayout editCidade;
    TextInputLayout editEstado;
    TextInputLayout editPais;
    TextInputLayout editCep;
    TextInputLayout editCodigoPais;
    TextInputLayout editPhone;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_igreja );
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
        editCodigoPais = findViewById (R.id.text_input_editCodigoPais);
        editPhone = findViewById (R.id.text_input_phone);
    }

    private void readIgrejaCadastrada() {
        final String ui = HomeActivity.UI.getUid ();
        novaRef = databaseReference.child( "churchs/");
        Query query = novaRef.orderByChild (uid_extra).limitToFirst (1);
        listener =  new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot sd : ds.getChildren ()) {
                        String key = sd.getKey ();
                        if(!key.equalsIgnoreCase ( "members" )
                            && !key.equalsIgnoreCase ( "cells" )
                            && !key.equalsIgnoreCase ( "reports" )
                            && !key.equalsIgnoreCase ( "intercession" )
                        ) {

                            Igreja igr = sd.getValue ( Igreja.class );
                            String group = Objects.requireNonNull ( igr ).getGroup ();
                            Objects.requireNonNull ( denominacao.getEditText ( ), "" ).setText (group);
                            Objects.requireNonNull ( editIgreja.getEditText ( ), "" ).setText (igr.getNome ());
                            Objects.requireNonNull ( editEndereco.getEditText ( ), "" ).setText (igr.getEndereco ());
                            Objects.requireNonNull ( editBairro.getEditText ( ), "" ).setText (igr.getBairro ());
                            Objects.requireNonNull ( editCidade.getEditText ( ), "" ).setText (igr.getCidade ());
                            Objects.requireNonNull ( editEstado.getEditText ( ), "" ).setText (igr.getEstado ());
                            Objects.requireNonNull ( editPais.getEditText ( ), "" ).setText (igr.getPais_ ());
                            Objects.requireNonNull ( editCep.getEditText ( ), "" ).setText (igr.getCep ());
                            Objects.requireNonNull ( editCodigoPais.getEditText ( ), "" ).setText (igr.getCodigopais ());
                            Objects.requireNonNull ( editPhone.getEditText ( ), "" ).setText (igr.getPhone ());

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Msg","Erro readIntercessão");
            }
        };
        query.addListenerForSingleValueEvent (listener);
        query.removeEventListener (listener);
    }


    private void saveIgrejaClick ( MenuItem item ) {
        String denom = Objects.requireNonNull ( denominacao.getEditText ( ), "" ).getText ().toString().trim();
        String igrejanome = Objects.requireNonNull ( editIgreja.getEditText ( ), "" ).getText ().toString().trim();
        String endereco = Objects.requireNonNull ( editEndereco.getEditText ( ), "" ).getText ().toString().trim();
        String bairro = Objects.requireNonNull ( editBairro.getEditText ( ), "" ).getText ().toString().trim();
        String cidade = Objects.requireNonNull ( editCidade.getEditText ( ), "" ).getText ().toString().trim();
        String estado = Objects.requireNonNull ( editEstado.getEditText ( ), "" ).getText ().toString().trim();
        String pais = Objects.requireNonNull ( editPais.getEditText ( ), "" ).getText ().toString().trim();
        String cep = Objects.requireNonNull ( editCep.getEditText ( ), "" ).getText ().toString().trim();
        String codigopais = Objects.requireNonNull ( editCodigoPais.getEditText ( ), "" ).getText ().toString().trim();
        String fone = Objects.requireNonNull ( editPhone.getEditText ( ), "" ).getText ().toString().trim();
      //  String hora = hh + ":" + mm;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        novaRef2 =databaseReference.child( "churchs/").child( uid_extra );

        if(!TextUtils.isEmpty(igrejanome)){
            Map <String, Object> IgrejaUpdates = new HashMap <> ();
            IgrejaUpdates.put( nome_extra  + "/group" , denom);
            IgrejaUpdates.put( nome_extra  + "/nome", igrejanome);
            IgrejaUpdates.put( nome_extra  + "/endereco", endereco );
            IgrejaUpdates.put( nome_extra  + "/bairro", bairro );
            IgrejaUpdates.put( nome_extra  + "/cidade", cidade );
            IgrejaUpdates.put( nome_extra  + "/estado", estado );
            IgrejaUpdates.put( nome_extra  + "/pais", pais );
            IgrejaUpdates.put( nome_extra  + "/cep", cep);
            IgrejaUpdates.put( nome_extra  + "/codigopais ", codigopais  );
            IgrejaUpdates.put( nome_extra  + "/phone", fone);

            novaRef2.updateChildren( IgrejaUpdates );

            Toast.makeText(this,"Editado célula com sucesso", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Erro ao tentar Editar célula !", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent( EditIgrejaActivity.this, IgrejasCriadasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity( intent );
        finish();
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

}