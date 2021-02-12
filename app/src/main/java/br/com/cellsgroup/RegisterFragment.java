package br.com.cellsgroup;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

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
  //  private final EditText editNome = null;
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
              //  registrarUsuario( );
                (( LoginActivity )getActivity ()).onBackPressed();
            }
        });

        btnCancelarLogin = view.findViewById ( R.id.btnCancelarLoginReg );
        btnCancelarLogin.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                (( LoginActivity)getActivity ()).onBackPressed();
            }
        } );
        // Inflate the layout for this fragment
        return view;
    }
}