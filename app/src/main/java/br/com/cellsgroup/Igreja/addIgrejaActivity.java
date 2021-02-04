package br.com.cellsgroup.Igreja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.models.igreja.Igreja;

import static br.com.cellsgroup.home.HomeActivity.Logado;
import static br.com.cellsgroup.home.HomeActivity.cellPhone;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_igreja );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        addDataHora();
        inicializarComponentes();
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();

    }

    private void inicializarFirebase() {
        ref = FirebaseDatabase.getInstance().getReference();
        ref2 = FirebaseDatabase.getInstance().getReference();

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
        editddi = findViewById( R.id.text_input_editddi);
        editPhone= findViewById (R.id.text_input_phone );
    }


    private void addIgrejaClick(MenuItem item) {
        addDataHora();
        try {
            String denominacao = editDenominacao.getEditText().getText().toString().trim();
            igreja = editIgreja.getEditText().getText().toString().trim();
            String endereco = editEndereco.getEditText().getText().toString().trim();
            String bairro = editBairro.getEditText().getText().toString().trim();
            String cidade = editCidade.getEditText().getText().toString().trim();
            String estado = editEstado.getEditText().getText().toString().trim();
            String pais_ = editPais.getEditText().getText().toString().trim();
            String cep = editCep.getEditText().getText().toString().trim();
            String ddi = editddi.getEditText().getText().toString().trim();
            String phone  = editPhone.getEditText().getText().toString().trim();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String members = "";

            //insere igreja
            if (!TextUtils.isEmpty( igreja  ) && Logado == true && typeUserAdmin == true) {
                String uid = ref.push().getKey();
                String igrejaID = uid;
                Igreja ig = new Igreja( uid, igreja, endereco, bairro, cidade, estado, pais_, cep, ddi, phone,DataTime, userId,status,denominacao,igrejaID, members);
                ref.child( "churchs/").child(uid + "/" + igreja).setValue( ig );

                //insere no groups de igrejas
                Map<String, Object> map1 = new HashMap<>();
                map1.put("members/"+ currentTimeMillis(), igreja);
                ref2.child("groups/").child(denominacao).updateChildren (map1);

                clearEditTexts();
                Toast.makeText( this, "Criado Igreja com sucesso", Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( this, "Erro ao tentar criar a igreja!", Toast.LENGTH_LONG ).show();
        } finally {
            Intent home = new Intent( addIgrejaActivity.this, HomeActivity.class);
            startActivity(home);
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

            Log.i("Updated", "Updated br.com.cellsgroup.models.igreja padrão de usuário novo. ");
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
