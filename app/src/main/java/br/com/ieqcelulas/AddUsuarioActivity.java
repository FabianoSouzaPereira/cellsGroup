package br.com.ieqcelulas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

import celulas.Celula;
import pessoas.Usuario;

import static br.com.ieqcelulas.HomeActivity.igreja;
import static br.com.ieqcelulas.HomeActivity.typeUserAdmin;

public class AddUsuarioActivity extends AppCompatActivity {

    public String DataTime;
    public String DataT;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference Usuarios;
    private int limitebusca = 1;


    private TextInputLayout EditTextnome;
    private TextInputLayout EditTextidade;
    private TextInputLayout EditTextsexo;
    private TextInputLayout EditTextdataNascimento;
    private TextInputLayout EditTextdataBastismo;
    private TextInputLayout EditTextnomepai;
    private TextInputLayout EditTextnomemae;
    private TextInputLayout EditTextestadocivil;
    private TextInputLayout EditTexttelefone;
    private TextInputLayout EditTextemail;
    private TextInputLayout EditTextendereco;
    private TextInputLayout EditTextbairro;
    private TextInputLayout EditTextcidade;
    private TextInputLayout EditTextpais;
    private TextInputLayout EditTextcep;
    private TextInputLayout EditTextcargoIgreja;
    private boolean emaildual = false;
    private String email = "";
    private String emailTest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_usuario );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        mAuth = FirebaseAuth.getInstance();

        inicializarComponentes();
        inicializarFirebase();


        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            }
        } );
    }

    private void inicializarComponentes() {
        EditTextnome = findViewById( R.id.text_input_editNome);
        EditTextidade = findViewById( R.id.text_input_editIdade);
        EditTextsexo = findViewById( R.id.text_input_editSexo );
        EditTextdataNascimento = findViewById( R.id.text_input_editDataNascimento);
        EditTextdataBastismo = findViewById( R.id.text_input_editDataBatismo );
        EditTextnomepai = findViewById( R.id.text_input_editNomePai );
        EditTextnomemae = findViewById( R.id.text_input_editNomeMae );
        EditTextestadocivil = findViewById( R.id.text_input_editEstadoCivil );
        EditTexttelefone = findViewById( R.id.text_input_editTelefone );
        EditTextemail = findViewById( R.id.text_input_editEmail );
        EditTextendereco = findViewById( R.id.text_input_editEndereco );
        EditTextbairro = findViewById( R.id.text_input_editBairro );
        EditTextcidade = findViewById( R.id.text_input_editCidade );
        EditTextpais = findViewById( R.id.text_input_editPais );
        EditTextcep = findViewById( R.id.text_input_editCep );
        EditTextcargoIgreja = findViewById( R.id.text_input_editCargoIgreja);
    }

    private void inicializarFirebase() {
        Usuarios = FirebaseDatabase.getInstance().getReference();
    }

    private void addUsuarioClick(MenuItem item){
        addDataHora();

           String nome =  EditTextnome.getEditText().getText().toString().trim();
           String idade =  EditTextidade.getEditText().getText().toString().trim();
           String sexo = EditTextsexo.getEditText().getText().toString().trim();
           String dataNascimento = EditTextdataNascimento.getEditText().getText().toString().trim();
           String dataBastismo = EditTextdataBastismo.getEditText().getText().toString().trim();
           String nomepai = EditTextnomepai.getEditText().getText().toString().trim();
           String nomemae = EditTextnomemae.getEditText().getText().toString().trim();
           String estadocivil =  EditTextestadocivil.getEditText().getText().toString().trim();
           String telefone =  EditTexttelefone.getEditText().getText().toString().trim();
           String email =  EditTextemail.getEditText().getText().toString().trim();
           String endereco =  EditTextendereco.getEditText().getText().toString().trim();
           String bairro = EditTextbairro.getEditText().getText().toString().trim();
           String cidade =  EditTextcidade.getEditText().getText().toString().trim();
           String pais =  EditTextpais.getEditText().getText().toString().trim();
           String cep = EditTextcep.getEditText().getText().toString().trim();
           String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
           String status = "1";
           String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

           readOnlyActive();

        if(emaildual == true ){
                Toast.makeText( AddUsuarioActivity.this, "Já existe um cadastro com esse mesmo email " + email, Toast.LENGTH_LONG ).show();
                return ;
        }
            if(!TextUtils.isEmpty( nome )){
                String uid = Usuarios.push().getKey();
                Usuario usuario = new Usuario(uid, nome, idade, sexo, dataNascimento, dataBastismo, nomepai, nomemae, estadocivil, telefone, email, endereco, bairro, cidade, pais, cep, cargoIgreja, status, DataTime, igreja , userId);
                if (uid != null) {
                    Usuarios.child( "/Usuarios/" ).child( uid ).setValue( usuario );
                }
                Toast.makeText( this, "Criado usuario com sucesso", Toast.LENGTH_LONG ).show();

            }else{
                Toast.makeText(this,"Erro ao tentar criar usuário !", Toast.LENGTH_LONG).show();
            if (!typeUserAdmin){
                Toast.makeText(this,"Você não é um usuario administrador. \n Não pode criar usuario admin !", Toast.LENGTH_LONG).show();
            }
        }
            Intent intent= new Intent(AddUsuarioActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
    }


    private void readOnlyActive() {

        emailTest = EditTextemail.getEditText().getText().toString().trim();
        Usuarios = databaseReference.child( "Usuarios/" );
        Query query = Usuarios.orderByChild( "email" ).equalTo(email).limitToFirst(limitebusca);
        query.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dados : ds.getChildren()) {
                        Usuario u = dados.getValue( Usuario.class );
                        email = u.getEmail();
                    }
                }
                if (email == emailTest) {
                   emaildual = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        } );
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.usuario, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_Save_addUsuario){
            addUsuarioClick(item);
            return true;
        }
        if(id == R.id.action_cancel_addUsuario){
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



/*
    private void createToken(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("Admin", true);
        FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
    }


    private void verifyToken() {
        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken( idToken );
        if (Boolean.TRUE.equals( decoded.getClaims().get( "admin" ) )) {
            // Allow access to requested admin resource.
        }
    }

    private void verifyAcessLevel(){
        // Verify the ID token first.
        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);
        if (Boolean.TRUE.equals(decoded.getClaims().get("admin"))) {
            // Allow access to requested admin resource.
        }
    }*/
}
