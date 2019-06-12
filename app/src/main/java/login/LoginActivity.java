package login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import br.com.ieqcelulas.HomeActivity;
import br.com.ieqcelulas.R;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private View login;
    private EditText editNome = null;
    private EditText editEmail = null;
    private EditText editSenha = null;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        mAuth = FirebaseAuth.getInstance();
            editEmail = findViewById( R.id.email );
            editSenha = findViewById( R.id.password );
        btnRegistrar = (Button)findViewById( R.id.btnEnviarRegistro );
        btnRegistrar.setOnClickListener(this);
        progressDialog = new ProgressDialog( this );

    }

    private void logarUsuario(){
        final String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email não pode estar vazio ", Toast.LENGTH_LONG).show();

        }

        if (TextUtils.isEmpty( senha )){
            Toast.makeText( this,"Falta colocar a senha",Toast.LENGTH_LONG ).show();
            return;
        }

        progressDialog.setMessage( "Iniciando Login..." );
        progressDialog.show();

        //Consultar se usuario existe
        mAuth.signInWithEmailAndPassword( email, senha )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checando sucesso
                        if(task.isSuccessful()){
                            HomeActivity.Logado = true;
                            int pos = email.indexOf("@");
                            String user = email.substring(0,pos);
                            Toast.makeText( LoginActivity.this,"Benvindo", Toast.LENGTH_LONG ).show();
                            Intent logar = new Intent(getApplication(),login.WellcomeActivity.class);
                            logar.putExtra(WellcomeActivity.user, user);
                            startActivity(logar);


                        }else{  //se houver colisão de mesmo usuário
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText( LoginActivity.this, "Esse usuário já existe. ",Toast.LENGTH_SHORT).show();
                                HomeActivity.Logado = false;
                            }else{
                                Toast.makeText( LoginActivity.this,"Falha ao registrar usuário", Toast.LENGTH_LONG ).show();
                                HomeActivity.Logado = false;
                            }


                        }
                        progressDialog.dismiss();
                    }
                } );

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnEnviarRegistro:
                registrarUsuario();
                break;

            case R.id.btnEnviarLogin: logarUsuario();
                break;
        }


    }

    private void registrarUsuario(){
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email não pode estar vazio ", Toast.LENGTH_LONG).show();

        }

        if (TextUtils.isEmpty( senha )){
            Toast.makeText( this,"Falta colocar a senha",Toast.LENGTH_LONG ).show();
            return;
        }

        progressDialog.setMessage( "Realizando o registro ..." );
        progressDialog.show();

        //criando novo usuario
        mAuth.createUserWithEmailAndPassword( email, senha )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checando sucesso
                        if(task.isSuccessful()){
                            Toast.makeText( LoginActivity.this,"Usuário registrado com sucesso.", Toast.LENGTH_LONG ).show();

                        }else{  //se houver colisão de mesmo usuário
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                               Toast.makeText( LoginActivity.this, "Esse usuário já existe. ",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText( LoginActivity.this,"Falha ao registrar usuário", Toast.LENGTH_LONG ).show();
                            }


                        }
                        progressDialog.dismiss();
                    }
                } );


    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void nav_header(){

    }
}
