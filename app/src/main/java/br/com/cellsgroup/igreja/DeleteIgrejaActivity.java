package br.com.cellsgroup.igreja;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;

import static br.com.cellsgroup.home.HomeActivity.igreja;

public class DeleteIgrejaActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef4;
    private DatabaseReference ref;
    private String uid_extra;
    private String nome_extra;
    private TextInputLayout igrejaParaApagar;
    private Button btnApagarIgreja;
    private FirebaseAuth mAuth;
    
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_delete_igreja );
        inicializarFirebase();

        mAuth = FirebaseAuth.getInstance();
        mAuth.getUid ();

        Intent intent = getIntent();
        uid_extra = intent.getStringExtra( "igreja" );
        nome_extra = intent.getStringExtra( "Nome" );
        final String ui = HomeActivity.UI.getUid ();
        novaRef4 = databaseReference.child( "churchs/");

        inicializarComponentes();
        igrejaParaApagar.getEditText().setText( nome_extra );
        btnApagarIgreja.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIgreja();
            }
        } );
    }

    //ToDo Falta fazer no futuro um metodo que não apague a igreja permanentemento usando status= 0 ;
    private void deleteIgreja() {
        try {

            if(!TextUtils.isEmpty( uid_extra )){

                //apaga igreja de groups
                ref = databaseReference;
                ref.child("groups/").child(igreja).child ("member/");
                ref.child(uid_extra).removeValue ();

                novaRef4.child( uid_extra );
                novaRef4.child( uid_extra ).removeValue();

                Toast.makeText(this,"igreja Apagada com sucesso", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Erro ao tentar Apagar a igreja !", Toast.LENGTH_LONG).show();
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        } finally {
            Intent igreja  = new Intent( DeleteIgrejaActivity.this, IgrejasCriadasActivity.class);
            startActivity( igreja);
            finish();
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(DeleteIgrejaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializarComponentes ( ) {
        igrejaParaApagar = findViewById( R.id.tvIgrejaParaApagar );
        btnApagarIgreja = findViewById(R.id.btnApagarIgreja);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent readigreja = new Intent ( DeleteIgrejaActivity.this , HomeActivity.class );
        startActivity ( readigreja );
        super.onBackPressed();
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
    protected void onResume ( ) {
        super.onResume ( );
    }

    @Override
    protected void onPause ( ) {
        super.onPause ( );
    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }
}