package br.com.cellsgroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RememberActivity extends AppCompatActivity {
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextInputLayout editEmail = null;
    private final TextInputLayout editSenha = null;
    private Button btnRegistrarRemember;
    private Button btnCancelarRemember;
    private ProgressDialog progressDialog4;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_remember );

        mAuth = FirebaseAuth.getInstance();
        progressDialog4 = new ProgressDialog (this );
        editEmail = findViewById (R.id.emailRemember );
        btnRegistrarRemember = findViewById( R.id.btnEnviarEmailRemember  );
        btnRegistrarRemember.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                resetPassword();
                finish ();
            }
        });

        btnCancelarRemember = findViewById ( R.id.btnCancelarLoginRemember );
        btnCancelarRemember.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                finish();
            }
        } );
    }

    private void updatePassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = editSenha.getEditText ().getText ().toString ().trim ();
        user.updatePassword(newPassword)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("password ", "User password updated.");
                        Toast.makeText( RememberActivity.this, "Atualizado Senha",
                            Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void resetPassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = editEmail.getEditText ().getText ().toString ().trim ();
        auth.setLanguageCode("pt-br");
        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("reset", "Email sent.");
                        Toast.makeText( RememberActivity.this, "Email enviado",
                            Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
