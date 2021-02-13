package br.com.cellsgroup.Igreja;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewIgreja;
import br.com.cellsgroup.R;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.igreja.Igreja;


@SuppressWarnings( "ALL" )
public class IgrejasCriadasActivity<onIgrejaListener> extends AppCompatActivity implements AdapterListViewIgreja.OnIgrejaListener {
    private static final String TAG = "ClickLista";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private int limitebusca = 500;
    private ArrayList<String> ig = new ArrayList<String>( );
    private AdapterListViewIgreja mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private String uid;
    private String nome;
    private Query query;
    private ValueEventListener listener;
    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_igrejas_criadas );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializaComponentes();
        inicializaFirebase();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);

        readIgrejaCadastrada();

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            }
        } );
    }

    private void readIgrejaCadastrada() {
        final String ui = HomeActivity.UI.getUid ().toString ();
        novaRef = databaseReference.child( "churchs/");
        Query query = novaRef.orderByChild ("igrejaID");
        listener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ig.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot sd : ds.getChildren ()) {
                        String key = sd.getKey();
                        if(!key.equalsIgnoreCase ( "members" )
                            && !key.equalsIgnoreCase ( "cells" )
                            && !key.equalsIgnoreCase ( "reports" )
                            && !key.equalsIgnoreCase ( "intercession" )
                            && !key.equalsIgnoreCase ( "Skedule" )
                        ) {

                            Igreja igr = sd.getValue ( Igreja.class );
                            if(igr.getUser ().equals (ui)) {
                                uid = igr.getUid ( );
                                String user = igr.getUser ( );
                                String group = igr.getGroup ( );
                                nome = igr.getNome ( );
                                String endereco = igr.getEndereco ( );
                                String bairro = igr.getBairro ( );
                                String cidade = igr.getCidade ( );
                                String estado = igr.getEstado ( );
                                String pais = igr.getPais_ ( );
                                String cep = igr.getCep ( );
                                String ddi = igr.getDdi ( );
                                String telefone = igr.getPhone ( );
                                String igrejaID = igr.getIgrejaID ();
                                String members = igr.getMembers ();

                                ig.add ( "Denominação:  " + group );
                                ig.add ( "Nome:  " + nome );
                                ig.add ( "Endereço:  " + endereco );
                                ig.add ( "Bairro:  " + bairro );
                                ig.add ( "Cidade:  " + cidade );
                                ig.add ( "Estado:  " + estado );
                                ig.add ( "País:  " + pais );
                                ig.add ( "Cep:  " + cep );
                                ig.add ( "DDI:  " + ddi );
                                ig.add ( "Fone:  " + telefone );
                            }
                        }
                    }
                }

                List<String> igrejas = ig;

                mAdapter = new AdapterListViewIgreja(igrejas, IgrejasCriadasActivity.this, IgrejasCriadasActivity.this );
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent (listener);
        query.removeEventListener (listener);
    }


    private void inicializaComponentes() {
        recyclerView = findViewById(R.id.recyclerViewIgreja );
    }

    @Override
    public void onBackPressed() {
        IgrejasCriadasActivity.this.finish();
        Intent home = new Intent(IgrejasCriadasActivity.this, HomeActivity.class);
        startActivity(home);
    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(IgrejasCriadasActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public void onIgrejaClick(int position) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_edit_delete , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_Edit){
            Intent intent = new Intent ( IgrejasCriadasActivity.this , EditIgrejaActivity.class );
            intent.putExtra("Igreja", "" + uid );
            intent.putExtra("Nome", "" + nome );
            startActivity ( intent );
            return true;
        }
        if(id == R.id.action_delete){
            Intent intent = new Intent ( IgrejasCriadasActivity.this , DeleteIgrejaActivity.class );
            intent.putExtra("Igreja", "" + uid );
            intent.putExtra("Nome", "" + nome );
            startActivity ( intent);
            return true;
        }

        return super.onOptionsItemSelected( item );
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
    protected void onRestart ( ) {
        super.onRestart ( );
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
