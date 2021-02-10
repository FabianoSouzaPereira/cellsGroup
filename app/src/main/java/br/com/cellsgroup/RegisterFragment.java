package br.com.cellsgroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.dynamicfeatures.fragment.ui.InstallViewModel;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseAuth.AuthStateListener mAuthListener;
    private View login;
    private final EditText editNome = null;
    private TextInputLayout editEmail = null;
    private TextInputLayout editSenha = null;
    private Button btnRegistrar;
    private Button btnCancelarLogin;
    private ProgressDialog progressDialog;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View view;

    public RegisterFragment ( ) {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance ( String param1 , String param2 ) {
        RegisterFragment fragment = new RegisterFragment ( );
        Bundle args = new Bundle ( );
        args.putString ( ARG_PARAM1 , param1 );
        args.putString ( ARG_PARAM2 , param2 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        if ( getArguments ( ) != null ) {
            mParam1 = getArguments ( ).getString ( ARG_PARAM1 );
            mParam2 = getArguments ( ).getString ( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        view =  inflater.inflate ( R.layout.fragment_register , container , false );
        mAuth = FirebaseAuth.getInstance();
        editEmail = view.findViewById (R.id.emailReg );
        editSenha = view.findViewById( R.id.passwordReg );
        btnRegistrar = view.findViewById( R.id.btnEnviarRegistroReg );
        btnRegistrar.setOnClickListener ( new View.OnClickListener ( ) {
              @Override
              public void onClick ( View v ) {
                  registrarUsuario( );
              }
          });

        btnCancelarLogin = view.findViewById ( R.id.btnCancelarLoginReg );
        btnCancelarLogin.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                (( LoginActivity)getActivity ()).onBackPressed();
            }
        } );
        progressDialog = new ProgressDialog( getActivity () );
        // Inflate the layout for this fragment
        return view;
    }

    public void registrarUsuario(){
        String email = editEmail.getEditText().getText().toString().trim();
        String senha = editSenha.getEditText().getText().toString().trim();

        if (!validateForm()) {
            return;
        }
        progressDialog.setMessage( getString( R.string.registrando) );
        progressDialog.show();

        //criando novo leader
        mAuth.createUserWithEmailAndPassword( email, senha )
            .addOnCompleteListener(getActivity (), new OnCompleteListener < AuthResult > () {
                @Override
                public void onComplete(@NonNull Task <AuthResult> task) {
                    //checando sucesso
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity (),getString( R.string.Registro_sucesso), Toast.LENGTH_LONG ).show();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI( currentUser );
                        (( LoginActivity)getActivity ()).onBackPressed();
                        //  createToken();
                    }else{  //se houver colisão de mesmo usuário
                        if (task.getException() instanceof FirebaseAuthUserCollisionException ){
                            Toast.makeText( getActivity (),getString( R.string.registro_existe), Toast.LENGTH_LONG ).show();
                            updateUI( null );
                            editEmail.setError ("Esse registro já existe!");
                            editEmail.setFocusable (true);
                            editEmail.requestFocus ();
                        }else{
                            Toast.makeText( getActivity (),getString( R.string.Falha_registro), Toast.LENGTH_LONG ).show();
                            updateUI( null );
                            editEmail.setError ("Esse registro já existe!");
                            editEmail.setFocusable (true);
                            editEmail.requestFocus ();
                        }
                    }
                    editEmail.getEditText().setText("");
                    editSenha.getEditText().setText("");
                    progressDialog.dismiss();
                }
            } );
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = editEmail.getEditText().getText().toString().trim();
        if ( TextUtils.isEmpty(email) || validateEmailFormat(email) == false) {
            Toast.makeText(getActivity ().getApplicationContext(),getString( R.string.Email_erro), Toast.LENGTH_LONG).show();
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

    private boolean validateEmailFormat(final String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher( email ).matches();
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