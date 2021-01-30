package br.com.cellsgroup.Igreja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.cellsgroup.R;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.home.HomeActivity;

import static br.com.cellsgroup.home.HomeActivity.igreja;

public class DeleteIgrejaActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef4;
    private String uid_extra;
    private String nome_extra;
    private TextInputLayout igrejaParaApagar;
    private TextInputLayout apagarIgreja;
    private Button btnApagarIgreja;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_delete_igreja );
        inicializarFirebase();

        Intent intent = getIntent();
        uid_extra = intent.getStringExtra( "Igreja" );
        nome_extra = intent.getStringExtra( "Nome" );
        final String ui = HomeActivity.UI.getUid ();
        novaRef4 = databaseReference.child( "churchs/").child(ui + "/" + igreja);
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

        novaRef4.child( uid_extra );

        if(!TextUtils.isEmpty( uid_extra )){
            novaRef4.child( uid_extra ).removeValue();

            Toast.makeText(this,"Igreja Apagada com sucesso", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Erro ao tentar Apagar a Igreja !", Toast.LENGTH_LONG).show();
        }

        Intent igreja  = new Intent( DeleteIgrejaActivity.this, IgrejasCriadasActivity.class);
        startActivity( igreja);
        finish();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(DeleteIgrejaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializarComponentes ( ) {
        igrejaParaApagar = findViewById( R.id.tvIgrejaParaApagar );
        apagarIgreja = findViewById(R.id.tvApagarIgreja );
        btnApagarIgreja = findViewById(R.id.btnApagarIgreja);
    }
}