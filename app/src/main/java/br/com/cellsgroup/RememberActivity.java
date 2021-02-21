package br.com.cellsgroup;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RememberActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout editEmail;
    private Button btnRegistrarRemember;
    private Button btnCancelarRemember;
    private ProgressDialog progressDialog4;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_remember );

        mAuth = FirebaseAuth.getInstance();
        progressDialog4 = new ProgressDialog (this );
        btnRegistrarRemember = findViewById( R.id.btnEnviarEmailRemember  );
        btnRegistrarRemember.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                if (mAuth != null) {
                    resetPassword();
                }
                progressDialog4.dismiss ();
                finish ();
            }
        });

        btnCancelarRemember = findViewById ( R.id.btnCancelarLoginRemember );
        btnCancelarRemember.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                progressDialog4.dismiss ();
                finish();
            }
        } );
    }

    private void resetPassword(){
        editEmail = findViewById (R.id.emailRemember );
        String emailAddress = Objects.requireNonNull ( editEmail.getEditText ( ),"" ).getText ().toString ().trim ();
        if (!emailAddress.equals("")) {
            mAuth.setLanguageCode("pt-br");
            mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( RememberActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}
