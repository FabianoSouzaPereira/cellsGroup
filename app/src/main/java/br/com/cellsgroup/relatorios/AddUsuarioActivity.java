package br.com.cellsgroup.relatorios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
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

import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.models.pessoas.User;

import static br.com.cellsgroup.home.HomeActivity.useremail;
import static br.com.cellsgroup.home.HomeActivity.typeUserAdmin;

public class AddUsuarioActivity extends AppCompatActivity {

    public String DataTime;
    public String DataT;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference users;
    private final int limitebusca = 1;
    private TextInputLayout EditTextnome;
    private TextInputLayout EditTextidade;
    private TextInputLayout EditTextsexo;
    private TextInputLayout EditTextdataNascimento;
    private TextInputLayout EditTextdataBastismo;
    private TextInputLayout EditTextnomepai;
    private TextInputLayout EditTextnomemae;
    private TextInputLayout EditTextestadocivil;
    private TextInputLayout EditTextCodigoPais;
    private TextInputLayout EditTexttelefone;
    private TextInputLayout EditTextemail;
    private TextInputLayout EditTextendereco;
    private TextInputLayout EditTextbairro;
    private TextInputLayout EditTextcidade;
    private TextInputLayout EditTextpais;
    private TextInputLayout EditTextcep;
    private TextInputLayout EditTextcargoIgreja;
    private TextInputLayout EditTextGroup;
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
        EditTextCodigoPais = findViewById ( R.id.text_input_editCodigoPais );
        EditTexttelefone = findViewById( R.id.text_input_editTelefone );
        EditTextemail = findViewById( R.id.text_input_editEmail );
        EditTextendereco = findViewById( R.id.text_input_editEndereco );
        EditTextbairro = findViewById( R.id.text_input_editBairro );
        EditTextcidade = findViewById( R.id.text_input_editCidade );
        EditTextpais = findViewById( R.id.text_input_editPais );
        EditTextcep = findViewById( R.id.text_input_editCep );
        EditTextcargoIgreja = findViewById( R.id.text_input_editCargoIgreja);
        EditTextGroup = findViewById (R.id.text_input_editGroup );
    }

    private void inicializarFirebase() {
        users = FirebaseDatabase.getInstance().getReference();
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
           String codigopais = EditTextCodigoPais.getEditText ().getText ().toString ().trim ();
           String telefone =  EditTexttelefone.getEditText().getText().toString().trim();
           useremail =  EditTextemail.getEditText().getText().toString().trim();
           String email = useremail;
           String endereco =  EditTextendereco.getEditText().getText().toString().trim();
           String bairro = EditTextbairro.getEditText().getText().toString().trim();
           String cidade =  EditTextcidade.getEditText().getText().toString().trim();
           String pais =  EditTextpais.getEditText().getText().toString().trim();
           String cep = EditTextcep.getEditText().getText().toString().trim();
           String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
           String status = "1";
           String igreja = HomeActivity.igreja;
           String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
           ArrayList<String> group = null;

        if(emaildual){
                Toast.makeText( AddUsuarioActivity.this, "Já existe um cadastro com esse mesmo email " + email, Toast.LENGTH_LONG ).show();
                return ;
        }
            if(!TextUtils.isEmpty( nome )){
                String uid = users.push().getKey();
                User user = new User (uid, nome, idade, sexo, dataNascimento, dataBastismo, nomepai, nomemae, estadocivil, codigopais,telefone, email , endereco, bairro, cidade, pais, cep, cargoIgreja, status, DataTime, igreja , userId, group);
                if (uid != null) {
                    try {
                        users.child( "/users/" ).child( uid ).setValue( user );
                    } catch ( Exception e ) {
                        e.printStackTrace ( );
                    }
                }
                Toast.makeText( this, "Criado user com sucesso", Toast.LENGTH_LONG ).show();

            }else{
                Toast.makeText(this,"Erro ao tentar criar usuário !", Toast.LENGTH_LONG).show();
            if (!typeUserAdmin){
                Toast.makeText(this,"Você não é um usuario administrador. \n Não pode criar usuario admin !", Toast.LENGTH_LONG).show();
            }
        }
            Intent intent= new Intent(AddUsuarioActivity.this,HomeActivity.class);
            startActivity(intent);
    }


    private void editUsuarioClick(MenuItem item){
        addDataHora();

        String nome =  EditTextnome.getEditText().getText().toString().trim();
        String idade =  EditTextidade.getEditText().getText().toString().trim();
        String sexo = EditTextsexo.getEditText().getText().toString().trim();
        String dataNascimento = EditTextdataNascimento.getEditText().getText().toString().trim();
        String dataBastismo = EditTextdataBastismo.getEditText().getText().toString().trim();
        String nomepai = EditTextnomepai.getEditText().getText().toString().trim();
        String nomemae = EditTextnomemae.getEditText().getText().toString().trim();
        String estadocivil =  EditTextestadocivil.getEditText().getText().toString().trim();
        String codigopais = EditTextCodigoPais.getEditText ().getText ().toString ().trim ();
        String telefone =  EditTexttelefone.getEditText().getText().toString().trim();
        useremail =  EditTextemail.getEditText().getText().toString().trim();
        String email = useremail;
        String endereco =  EditTextendereco.getEditText().getText().toString().trim();
        String bairro = EditTextbairro.getEditText().getText().toString().trim();
        String cidade =  EditTextcidade.getEditText().getText().toString().trim();
        String pais =  EditTextpais.getEditText().getText().toString().trim();
        String cep = EditTextcep.getEditText().getText().toString().trim();
        String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
        String status = "1";
        String igreja = HomeActivity.igreja;
        String group = "Padrão";
        users = databaseReference.child( "users/" ).child ( email );
        if(emaildual){
            Toast.makeText( AddUsuarioActivity.this, "Já existe um cadastro com esse mesmo email " + email, Toast.LENGTH_LONG ).show();
            return ;
        }

        users.child (useremail);

        if(!TextUtils.isEmpty( nome )){
            Map <String, Object> usersUpdates = new HashMap <> ();
            usersUpdates.put (users + "/name/",nome );
            usersUpdates.put (users + "/idade/",idade );
            usersUpdates.put (users + "/sexo/",sexo );
            usersUpdates.put (users + "/dataNascimento /",dataNascimento );
            usersUpdates.put (users + "/dataBastismo/",dataBastismo );
            usersUpdates.put (users + "/nomepai/",nomepai );
            usersUpdates.put (users + "/nomemae /",nomemae );
            usersUpdates.put (users + "/estadocivil/",estadocivil );
            usersUpdates.put (users + "/codigopais/",codigopais );
            usersUpdates.put (users + "/email/",email );
            usersUpdates.put (users + "/endereco/",endereco );
            usersUpdates.put (users + "/bairro",bairro );
            usersUpdates.put (users + "/cidade/",cidade );
            usersUpdates.put (users + "/pais/",pais );
            usersUpdates.put (users + "/cep/",cep );
            usersUpdates.put (users + "/cargoIgreja/",cargoIgreja );
            usersUpdates.put (users + "/status/",status );
            usersUpdates.put (users + "/igreja/",igreja);
            usersUpdates.put (users + "/group/",group );
            String uid = users.push().getKey();

             if (uid != null) {
                try {
                    users.updateChildren (usersUpdates );
                } catch ( Exception e ) {
                    e.printStackTrace ( );
                }
            }
            Toast.makeText( this, "Criado user com sucesso", Toast.LENGTH_LONG ).show();

        }else{
            Toast.makeText(this,"Erro ao tentar criar usuário !", Toast.LENGTH_LONG).show();
            if (!typeUserAdmin){
                Toast.makeText(this,"Você não é um usuario administrador. \n Não pode criar usuario admin !", Toast.LENGTH_LONG).show();
            }
        }
        Intent intent= new Intent(AddUsuarioActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void deleteUsuario(){
        useremail =  EditTextemail.getEditText().getText().toString().trim();
        if(!useremail.isEmpty()) {
            users.child ( "users/" ).child ( useremail ).removeValue ( );
            Toast.makeText ( this , "Usuário Apagada com sucesso" , Toast.LENGTH_LONG ).show ( );
        }else{
            Toast.makeText(this,"Erro ao tentar Apagar a usuário!", Toast.LENGTH_LONG).show();
        }
        Intent intent= new Intent(AddUsuarioActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void readOnlyActive() {

        emailTest = EditTextemail.getEditText().getText().toString().trim();
        users = databaseReference.child( "users/" );
        Query query = users.orderByChild( "email" ).equalTo(email).limitToFirst(limitebusca);
        query.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot dados : ds.getChildren()) {
                            User u = dados.getValue( User.class );
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
                Log.i("Erro", "Erro consulta readOnlyActive(). Tipo de erro :"+  databaseError.getCode());
            }

        } );
    }

    @Override
    public void onBackPressed() {
        AddUsuarioActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_save, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_save){
            addUsuarioClick(item);
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

}
