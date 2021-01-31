package br.com.cellsgroup.usuario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.pessoas.User;

import static br.com.cellsgroup.home.HomeActivity.group;
import static br.com.cellsgroup.home.HomeActivity.typeUserAdmin;
import static br.com.cellsgroup.home.HomeActivity.uidIgreja;
import static br.com.cellsgroup.home.HomeActivity.useremail;

public class AddUsuarioActivity extends AppCompatActivity {

    public String DataTime;
    public String DataT;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference Usuarios;
    private DatabaseReference ref;
    private final int limitebusca = 1;
    private TextInputLayout EditTextnome;
    private TextInputLayout EditTextidade;
    private TextInputLayout EditTextsexo;
    private TextInputLayout EditTextdataNascimento;
    private TextInputLayout EditTextdataBastismo;
    private TextInputLayout EditTextnomepai;
    private TextInputLayout EditTextnomemae;
    private TextInputLayout EditTextestadocivil;
    private TextInputLayout EditTexttelefone;
    private TextInputLayout EdiTextCodigoPais;
    private TextInputLayout EditTextemail;
    private TextInputLayout EditTextendereco;
    private TextInputLayout EditTextbairro;
    private TextInputLayout EditTextcidade;
    private TextInputLayout EditTextestado;
    private TextInputLayout EditTextpais;
    private TextInputLayout EditTextcep;
    private TextInputLayout EditTextcargoIgreja;
    private boolean emaildual = false;
    private String email = "";
    private String emailTest = "";
    private String key;
    private static boolean validate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_usuario );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        mAuth = FirebaseAuth.getInstance();

        inicializarComponentes();
        inicializarFirebase();


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
        EdiTextCodigoPais = findViewById (R.id.text_input_editCodigoPais );
        EditTexttelefone = findViewById( R.id.text_input_editTelefone );
        EditTextemail = findViewById( R.id.text_input_editEmail );
        EditTextendereco = findViewById( R.id.text_input_editEndereco );
        EditTextbairro = findViewById( R.id.text_input_editBairro );
        EditTextcidade = findViewById( R.id.text_input_editCidade );
        EditTextestado = findViewById ( R.id.text_input_editEstado );
        EditTextpais = findViewById( R.id.text_input_editPais );
        EditTextcep = findViewById( R.id.text_input_editCep );
        EditTextcargoIgreja = findViewById( R.id.text_input_editCargoIgreja);
    }

    private void inicializarFirebase() {
        Usuarios = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    private void addUsuarioClick(MenuItem item){
        addDataHora();
        validate=true;
        String nome =  EditTextnome.getEditText().getText().toString().trim();
        if(nome.equals ("")|| nome.length() < 4){
            validate = false;
            EditTextnome.setError("Este campo é obrigatório");
            EditTextnome.setFocusable (true);
            EditTextnome.requestFocus ();
        }
        String idade =  EditTextidade.getEditText ( ).getText().toString().trim();
        String sexo = EditTextsexo.getEditText ( ).getText().toString().trim();
        String dataNascimento = EditTextdataNascimento.getEditText().getText().toString().trim();
        if( dataNascimento .equals ( "" ) || dataNascimento .length ( ) < 8 ){
            validate = false;
            EditTextdataNascimento.setError("Este campo é obrigatório");
            EditTextdataNascimento.setFocusable (true);
            EditTextdataNascimento.requestFocus ();
        }
        String dataBastismo = EditTextdataBastismo.getEditText().getText().toString().trim();
        String nomepai = EditTextnomepai.getEditText().getText().toString().trim();
        String nomemae = EditTextnomemae.getEditText().getText().toString().trim();
        String estadocivil =  EditTextestadocivil.getEditText().getText().toString().trim();
        String codigoPais = EdiTextCodigoPais.getEditText().getText().toString().trim();
        if( codigoPais.equals ( "" ) || codigoPais.length ( ) > 2 ){
            validate = false;
            EdiTextCodigoPais.setError("Este campo é obrigatório, dois dígitos");
            EdiTextCodigoPais.setFocusable (true);
            EdiTextCodigoPais.requestFocus ();
        }
        String telefone =  EditTexttelefone.getEditText().getText().toString().trim();

        if( telefone.equals ( "" ) || telefone.length ( ) < 9 ){
            validate = false;
            EditTexttelefone.setError("Este campo é obrigatório, min. 9 dígitos.");
            EditTexttelefone.setFocusable (true);
            EditTexttelefone.requestFocus ();
        }
        useremail =  EditTextemail.getEditText().getText().toString().trim();
        String email = useremail;
        if(email .equals ("")|| email.length() < 8 || !email.contains ("@" )){
            validate = false;
            EditTextemail.setError("Campo inválido");
            EditTextemail.setFocusable (true);
            EditTextemail.requestFocus ();
        }
        String endereco =  EditTextendereco.getEditText().getText().toString().trim();
        String bairro = EditTextbairro.getEditText().getText().toString().trim();
        String cidade =  EditTextcidade.getEditText().getText().toString().trim();
        String estado =  EditTextestado.getEditText().getText().toString().trim();
        String pais =  EditTextpais.getEditText().getText().toString().trim();
        String cep = EditTextcep.getEditText().getText().toString().trim();
        String cargoIgreja = EditTextcargoIgreja.getEditText().getText().toString().trim();
        if(cargoIgreja.equals ("")|| cargoIgreja.length() < 4){
            validate = false;
            EditTextcargoIgreja.setError("Este campo é obrigatório,");
            EditTextcargoIgreja.setFocusable (true);
            EditTextcargoIgreja.requestFocus ();
        }
        String status = "1";
        final String igreja = HomeActivity.igreja;
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if( emaildual ){
            Toast.makeText( AddUsuarioActivity.this, "Já existe um cadastro com esse mesmo email " + email, Toast.LENGTH_LONG ).show();
            return ;
        }
        if( validate ){
            if(!TextUtils.isEmpty( nome ) ) {

                String uid = Usuarios.push ( ).getKey ( );
                User usuario = new User ( uid , nome , idade , sexo , dataNascimento , dataBastismo , nomepai , nomemae , estadocivil , codigoPais , telefone , email , endereco , bairro , cidade , estado, pais , cep , cargoIgreja , status , DataTime , igreja , userId , group );

                if ( uid != null ) {
                    //Cria usuario
                    Usuarios.child ( "users/" ).child ( uid ).setValue ( usuario );

                    //atualiza membro na igreja
                    Map < String, Object > map = new HashMap <> ( );
                    map.put ( "/members/" + uid , telefone );
                    ref.child ( "churchs/" + uidIgreja ).updateChildren ( map );

                    Toast.makeText ( this , "Criado usuario com sucesso" , Toast.LENGTH_LONG ).show ( );

                    Intent intent = new Intent ( AddUsuarioActivity.this , HomeActivity.class );
                    startActivity ( intent );
                }else {
                    Toast.makeText ( this , "Erro ao tentar criar usuário !" , Toast.LENGTH_LONG ).show ( );
                    if ( !typeUserAdmin ) {
                        Toast.makeText ( this , "Você não é um usuario administrador. \n Não pode criar usuario admin !" , Toast.LENGTH_LONG ).show ( );
                    }
                    Intent intent = new Intent ( AddUsuarioActivity.this , HomeActivity.class );
                    startActivity ( intent );
                }
            }
        }

    }


    private void readOnlyActive() {

        emailTest = EditTextemail.getEditText().getText().toString().trim();
        Usuarios = databaseReference.child( "users/" );
        Query query = Usuarios.orderByChild( "email" ).equalTo(email).limitToFirst(limitebusca);
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
        getMenuInflater().inflate( R.menu.menu_save_cancel , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save){
            addUsuarioClick(item);
            return true;
        }
        if(id == R.id.action_Cancel){
            AddUsuarioActivity.this.finish();
            Intent intent = new Intent( AddUsuarioActivity.this, UsuarioActivity.class );
            startActivity(intent);
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

    @Override
    protected void onResume ( ) {
        super.onResume ( );
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
    protected void onRestart ( ) {
        super.onRestart ( );
    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }

}