package br.com.cellsgroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.login.LoginActivity;


public class RegisterActivity extends AppCompatActivity {
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseAuth.AuthStateListener mAuthListener;
    private View login;
    //  private final EditText editNome = null;
    private TextInputLayout editEmail = null;
    private TextInputLayout editSenha = null;
    private Button btnRegistrar;
    private Button btnCancelarLogin;
    private ProgressDialog progressDialog3;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        mAuth = FirebaseAuth.getInstance();
        progressDialog3 = new ProgressDialog (this );
        editEmail = findViewById (R.id.emailRegister );
        editSenha = findViewById( R.id.passwordRegister );
        btnRegistrar = findViewById( R.id.btnEnviarRegistroRegister );
        btnRegistrar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                registrarUsuario();
            }
        });

        btnCancelarLogin = findViewById ( R.id.btnCancelarLoginRegister );
        btnCancelarLogin.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                finish();
            }
        } );
    }

    public void registrarUsuario(){
        String email = editEmail.getEditText().getText().toString().trim();
        String senha = editSenha.getEditText().getText().toString().trim();

        if (!validateForm()) {
            return;
        }
        progressDialog3.setMessage( getString( R.string.registrando) );
        progressDialog3.show();

        //criando novo leader
        mAuth.createUserWithEmailAndPassword( email, senha )
            .addOnCompleteListener( RegisterActivity.this , new OnCompleteListener < AuthResult > () {
                @Override
                public void onComplete(@NonNull Task < AuthResult > task) {
                    //checando sucesso
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,getString( R.string.Registro_sucesso), Toast.LENGTH_SHORT ).show();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI( currentUser );
                        sendEmailVerification();
                        RegisterActivity.this.onBackPressed();
                        //  createToken();
                    }else{  //se houver colisão de mesmo usuário
                        if (task.getException() instanceof FirebaseAuthUserCollisionException ){
                            Toast.makeText(RegisterActivity.this,getString( R.string.registro_existe), Toast.LENGTH_LONG ).show();
                            updateUI( null );
                            editEmail.setError ("Esse registro já existe!");
                            editEmail.setFocusable (true);
                            editEmail.requestFocus ();
                        }else{
                            Toast.makeText( RegisterActivity.this,getString( R.string.Falha_registro), Toast.LENGTH_LONG ).show();
                            updateUI( null );
                            editEmail.setError ("Esse registro já existe!");
                            editEmail.setFocusable (true);
                            editEmail.requestFocus ();
                        }
                    }
                    editEmail.getEditText().setText("");
                    editSenha.getEditText().setText("");
                    progressDialog3.dismiss();
                }
            } );
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = editEmail.getEditText().getText().toString().trim();
        if ( TextUtils.isEmpty(email) || !validateEmailFormat ( email ) ) {
            Toast.makeText(RegisterActivity.this.getApplicationContext(),getString( R.string.Email_erro), Toast.LENGTH_LONG).show();
            editEmail.setError(getString( R.string.obrigatorio_email_valid));
            editEmail.setFocusable ( true );
            editEmail.requestFocus ( );
            valid = false;
        }else{
            editEmail.setError(null);
        }

        String senha = editSenha.getEditText().getText().toString().trim();
        if(senha .equals ("")|| senha .length() < 6 ) {
            valid = false;
            editSenha.setError (getString( R.string.obrigatorio_maior_5));
            editSenha.setFocusable ( true );
            editSenha.requestFocus ( );
        }else{
            editSenha.setError(null);
        }

        return valid;
    }

    public static void updateUI( FirebaseUser user){
        if (user != null) {
            HomeActivity.Logado = true;
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

        } else {
            HomeActivity.Logado = false;
        }
    }

    private boolean validateEmailFormat(final String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher( email ).matches();
    }

    private void sendEmailVerification(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        auth.setLanguageCode("pt-br");
        user.sendEmailVerification()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("Email sent ", "Email sent.");
                    }
                }
            });
    }

    private void updateEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail("user@example.com")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("Email Update ", "User email address updated.");
                    }
                }
            });
    }



    @Override
    public void onPause ( ) {
        super.onPause ( );
    }

    @Override
    public void onResume ( ) {
        super.onResume ( );
    }

    @Override
    public void onStart ( ) {
        super.onStart ( );
    }

    @Override
    public void onStop ( ) {
        super.onStop ( );
    }

    @Override
    public void onDestroy ( ) {
        super.onDestroy ( );
    }

}