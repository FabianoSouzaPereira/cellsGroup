package br.com.cellsgroup.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ActivityNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewUsuario;
import br.com.cellsgroup.Igreja.addIgrejaActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.celulas.EditCelulaActivity;
import br.com.cellsgroup.celulas.ReadCelulaActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.models.pessoas.User;


public class UsuarioActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "TAG";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;

    private RecyclerView recyclerView;
    private final ArrayList < User > arrayUser = new ArrayList < User > ();
    private AdapterListViewUsuario mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private final boolean mItemPressed = false;
    private final boolean itemReturned = false;

    private final int limitebusca = 200;
    private Query query;
    private ValueEventListener queryListener;
    private String uid;
    private String nome;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_usuario );

        iniciaComponentes();
        inicializarFirebase();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);

        readOnlyActive();

    }

    private void iniciaComponentes ( ) {
        recyclerView = findViewById( R.id.listviewUser );
        recyclerView.setLongClickable(true);
    }

    private void readOnlyActive() {
        novaRef = databaseReference.child( "users/");
        query = novaRef.orderByChild( "uid/" ).limitToLast(limitebusca);
        queryListener =  new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid = "";
                arrayUser.clear();
                  for(DataSnapshot dados : dataSnapshot.getChildren()) {
                      try {
                          User u = dados.getValue (User.class);
                          arrayUser.add(u);
                      } catch ( Exception e ) {
                          e.printStackTrace ( );
                      }
                  }
                List < User > usuarios = arrayUser;

                mAdapter = new AdapterListViewUsuario ( arrayUser );
                mAdapter.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick ( View v ) {
                        uid = arrayUser.get(recyclerView.getChildAdapterPosition (v)).getUid ();
                        nome = arrayUser.get(recyclerView.getChildAdapterPosition (v)).getNome ();
                        if (mItemPressed) {
                            // Multi-item swipes not handled
                            return;
                        }
                        Intent intent = new Intent( UsuarioActivity.this, EditUsuarioActivity.class );
                        intent.putExtra("uid", String.valueOf( uid) );
                        intent.putExtra("nome", String.valueOf( nome ) );
                        startActivity(intent);
                        finish();
                    }
                } );

                mAdapter.setOnLongClickListener ( new View.OnLongClickListener ( ) {
                    @Override
                    public boolean onLongClick ( View v ) {
                        // Implementar se houver necesidade
                        return false;
                    }
                } );

                recyclerView.setAdapter( mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,"Erro Database"+ databaseError.toException() );
            }
        } ;

        query.addValueEventListener (queryListener );

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(UsuarioActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @Override
    public void onBackPressed() {
        UsuarioActivity.this.finish();
    }

    @Override
    protected void onResume ( ) {
        super.onResume ( );
    }

    @Override
    protected void onStart ( ) {
        super.onStart ( );
    }

    @Override
    protected void onStop ( ) {
        query.removeEventListener (queryListener);
        super.onStop ( );
    }

    @Override
    protected void onRestart ( ) {
        super.onRestart ( );
    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );
    }
}