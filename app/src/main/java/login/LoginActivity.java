package login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import br.com.ieqcelulas.HomeActivity;
import br.com.ieqcelulas.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseAuth.AuthStateListener mAuthListener;
    private View login;
    private EditText editNome = null;
    private EditText editEmail = null;
    private EditText editSenha = null;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private static final String TAG = "CustomAuthActivity";
    private String mCustomToken;
    private TokenBroadcastReceiver mTokenReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
       // Toolbar toolbar = findViewById( R.id.toolbar );
      //  setSupportActionBar( toolbar );

        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById( R.id.email );
        editSenha = findViewById( R.id.password );
        btnRegistrar = findViewById( R.id.btnEnviarRegistro );
        btnRegistrar.setOnClickListener(this);
        progressDialog = new ProgressDialog( this );


        // Create token receiver (for demo purposes only)
        mTokenReceiver = new TokenBroadcastReceiver() {
            @Override
            public void onNewToken(String token) {
                Log.d( TAG, "onNewToken:" + token );
                setCustomToken( token );
            }
        };

    }

    private void logarUsuario(){
        final String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (!validateForm()) {
            return;
        }

        progressDialog.setMessage( getString( R.string.iniciando_login) );
        progressDialog.show();

        //Consultar se usuario existe
        mAuth.signInWithEmailAndPassword( email, senha )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checando sucesso
                        if(task.isSuccessful()){
                            int pos = email.indexOf("@");
                            String user = email.substring(0,pos);
                            Toast.makeText( LoginActivity.this, getString( R.string.logado_sucesso),Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                            Intent home = new Intent( LoginActivity.this, HomeActivity.class );
                            startActivity( home );
                          //  startSignIn();
                        }else{  //se houver colisão de mesmo usuário
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText( LoginActivity.this, getString( R.string.ususario_existe),Toast.LENGTH_SHORT).show();
                                HomeActivity.Logado = false;
                            }else{
                                Toast.makeText( LoginActivity.this,getString( R.string.falha_login), Toast.LENGTH_LONG ).show();
                                HomeActivity.Logado = false;
                            }

                        }
                        editEmail.setText( "" );
                        editSenha.setText( "" );
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

            case R.id.btnEnviarLogin:
                logarUsuario();
                break;
        }


    }

    private void registrarUsuario(){
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (!validateForm()) {
            return;
        }

        progressDialog.setMessage( getString( R.string.registrando) );
        progressDialog.show();

        //criando novo usuario
        mAuth.createUserWithEmailAndPassword( email, senha )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checando sucesso
                        if(task.isSuccessful()){
                            Toast.makeText( LoginActivity.this,getString( R.string.Registro_sucesso), Toast.LENGTH_LONG ).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI( currentUser );
                            Intent home = new Intent( LoginActivity.this, HomeActivity.class );
                            startActivity( home );
                          //  createToken();
                        }else{  //se houver colisão de mesmo usuário
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText( LoginActivity.this,getString( R.string.ususario_existe), Toast.LENGTH_LONG ).show();
                               updateUI( null );
                            }else{
                                Toast.makeText( LoginActivity.this,getString( R.string.Falha_registro), Toast.LENGTH_LONG ).show();
                                updateUI( null );
                            }

                        }
                        editEmail.setText( "" );
                        editSenha.setText( "" );
                        progressDialog.dismiss();
                    }
                } );


    }

    private void createToken() {
        // Initiate sign in with custom token
        // [START sign_in_custom]
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_custom]
    }

    private void setCustomToken(String token) {
        mCustomToken = token;

        String status;
        if (mCustomToken != null) {
            status = "Token:" + mCustomToken;
        } else {
            status = "Token: null";
        }
    }

    private void startSignIn() {
        // Initiate sign in with custom token
        // [START sign_in_custom]
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_custom]
    }

    private String getString(String string) {
        return string;
    }

    private CharSequence getString(CharSequence charSequence) {
        return charSequence;
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = editEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email) || validateEmailFormat(email) == false) {
            Toast.makeText(this,getString( R.string.Email_erro), Toast.LENGTH_LONG).show();
            editEmail.setError(getString( R.string.obrigatorio));
            valid = false;
        }else{
            editEmail.setError(null);
        }

        String senha = editSenha.getText().toString().trim();
        if (TextUtils.isEmpty(senha)) {
            Toast.makeText( this,getString( R.string.Senha_vazia),Toast.LENGTH_LONG ).show();
            editSenha.setError(getString( R.string.obrigatorio));
            valid = false;
        }else{
            editSenha.setError(null);
        }

        return valid;
    }

    public static void updateUI(FirebaseUser user){
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
     protected void onResume() {
        super.onResume();
         registerReceiver(mTokenReceiver, TokenBroadcastReceiver.getFilter());
      }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mTokenReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void nav_header(){

    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateEmailFormat(final String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher( email ).matches();
      //  Toast.makeText( LoginActivity.this,"Email inválido", Toast.LENGTH_LONG ).show();
    }

}
