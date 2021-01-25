package br.com.cellsgroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import igreja.Igreja;
import static br.com.cellsgroup.HomeActivity.Logado;
import static br.com.cellsgroup.HomeActivity.igreja;
import static br.com.cellsgroup.HomeActivity.typeUserAdmin;

public class AddIgrejaActivity extends AppCompatActivity {
    private DatabaseReference Igrejas;
    public String DataTime;
    public String DataT;
    public String status = "1";
    private TextInputLayout editIgreja;
    private TextInputLayout editEndereco;
    private TextInputLayout editBairro;
    private TextInputLayout editCidade;
    private TextInputLayout editEstado;
    private TextInputLayout editPais;
    private TextInputLayout editCep;
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
        Igrejas = FirebaseDatabase.getInstance().getReference();
    }

    private void inicializarComponentes() {
        editIgreja = findViewById( R.id.text_input_editIgreja );
        editEndereco = findViewById( R.id.text_input_editEndereco );
        editBairro = findViewById( R.id.text_input_editBairro );
        editCidade = findViewById( R.id.text_input_editCidade );
        editEstado = findViewById( R.id.text_input_editEstado );
        editPais = findViewById( R.id.text_input_editPais_ );
        editCep = findViewById( R.id.text_input_editCep );
    }


    private void addIgrejaClick(MenuItem item) {
        addDataHora();
        try {
            igreja = editIgreja.getEditText().getText().toString().trim();
            String endereco = editEndereco.getEditText().getText().toString().trim();
            String bairro = editBairro.getEditText().getText().toString().trim();
            String cidade = editCidade.getEditText().getText().toString().trim();
            String estado = editEstado.getEditText().getText().toString().trim();
            String pais = editPais.getEditText().getText().toString().trim();
            String cep = editCep.getEditText().getText().toString().trim();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (!TextUtils.isEmpty( igreja  ) && Logado == true && typeUserAdmin == true) {
                String uid = Igrejas.push().getKey();
                Igreja ig = new Igreja( uid, igreja, endereco, bairro, cidade, estado, pais, cep, DataTime, userId, status );
                Igrejas.child( "Igrejas/" + igreja ).child( uid ).setValue( ig );
                clearEditTexts();
                Toast.makeText( this, "Criado Igreja com sucesso", Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( this, "Erro ao tentar criar célula !", Toast.LENGTH_LONG ).show();
        } finally {
            Intent home = new Intent(AddIgrejaActivity.this,HomeActivity.class);
            startActivity(home);
        }
    }

    private void updateUsuario(){
        try {
            mAuth = FirebaseAuth.getInstance();
            String userId = mAuth.getCurrentUser().getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child( "Usuarios" ).child( userId );
            Map<String, Object> usuarioUpdates = new HashMap<>();
            usuarioUpdates.put( "Usuarios" + userId + "/igrejaPadrao" , igreja);
            databaseReference.updateChildren( usuarioUpdates );
            Log.i("Updated", "Updated igreja padrão de usuário novo. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AddIgrejaActivity.this.finish();
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
        getMenuInflater().inflate( R.menu.igreja, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_Save_addigreja){
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
}
