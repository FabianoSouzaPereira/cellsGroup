package br.com.ieqcelulas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
  //  private RecyclerView.Adapter mAdapter;
  //  private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_igrejas_criadas );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializaComponentes();
        inicializaFirebase();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration( this, layoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration( dividerItemDecoration );


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

        novaRef = databaseReference.child( "Igrejas" );
        Query query = novaRef.orderByChild("nome").limitToFirst( limitebusca );
        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ig.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                   String i = ds.getKey();
                   ig.add( i );
                }

                List<String> igrejas = ig;

                mAdapter = new AdapterListViewIgreja(igrejas, IgrejasCriadasActivity.this, IgrejasCriadasActivity.this );
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Msg","Erro readIntercessão");
            }
        } );
    }


    private void inicializaComponentes() {
        recyclerView = findViewById(R.id.recyclerViewIgreja );
    }


    private void inicializaFirebase() {
        FirebaseApp.initializeApp(IgrejasCriadasActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public void onIgrejaClick(int position) {
        Log.d( TAG, "Clicado lista: na posição -> " + position);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
