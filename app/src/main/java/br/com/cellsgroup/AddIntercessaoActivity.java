package br.com.cellsgroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static br.com.cellsgroup.HomeActivity.igreja;

public class AddIntercessaoActivity extends AppCompatActivity {

    private DatabaseReference Intercessoes;
    private TextInputLayout EditTextnome;
    private TextInputLayout EditTextmotivo;
    private TextInputLayout EditTextdata;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public String DataTime;
    public String DataT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_intercessao );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        mAuth = FirebaseAuth.getInstance();
        inicializarFirebase();
        addDataHora();
        inicializarComponentes();

    }

    private void addIntercessaoClick(MenuItem item){
        try {
            String nome =  EditTextnome.getEditText().getText().toString().trim();
            String motivo =  EditTextmotivo.getEditText().getText().toString().trim();
            String data =  EditTextdata.getEditText().getText().toString().trim();

            if(!TextUtils.isEmpty( nome ) && !TextUtils.isEmpty( motivo ) && !TextUtils.isEmpty( data ) ){
                String uid = Intercessoes.push().getKey();
                Intercessao intercessao = new Intercessao( uid, nome, motivo, data);
                Intercessoes.child("Igrejas/" + igreja + "/Intercessao").child( uid ).setValue( intercessao );

                Toast.makeText(this,"Criado intercessão com sucesso", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Erro ao tentar criar intercessão !", Toast.LENGTH_LONG).show();
                // if (!typeUserAdmin){
                //     Toast.makeText(this,"Você não é um usuario administrador. \n Não pode criar celulas !", Toast.LENGTH_LONG).show();
                // }
            }
        } finally {
            Intent intent = new Intent( AddIntercessaoActivity.this, IntercessaoActivity.class );
            startActivity( intent );
            finish();
        }

    }

    private void inicializarComponentes() {
        EditTextnome = findViewById( R.id.text_input_nome);
        EditTextmotivo = findViewById( R.id.text_input_Motivo);
        EditTextdata = findViewById(R.id.text_input_Data);
        String nome = Objects.requireNonNull( mAuth.getCurrentUser().getEmail() );
        EditTextnome.getEditText().setText( nome );
        Objects.requireNonNull( EditTextdata.getEditText() ).setText( DataT );
    }

    private void inicializarFirebase() {
        Intercessoes = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        AddIntercessaoActivity.this.finish();
/*        Intent home = new Intent(AddIntercessaoActivity.this,HomeActivity.class);
        startActivity(home);*/
  /*      DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.add_intercessao, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_Save_addIntercessao){
            addIntercessaoClick( item );
            return true;
        }

        if(id == R.id.action_cancel_addIntercessao){
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
