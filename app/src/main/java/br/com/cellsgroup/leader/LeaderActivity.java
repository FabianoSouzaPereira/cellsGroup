package br.com.cellsgroup.leader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterListViewLeader;
import br.com.cellsgroup.R;
import br.com.cellsgroup.models.pessoas.Leader;

import static br.com.cellsgroup.home.HomeActivity.UI;


public class LeaderActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "TAG";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaRef;
    private final Boolean listaLider = true;

    private RecyclerView recyclerView;
    private final ArrayList < Leader > arrayLeader = new ArrayList < Leader > ();
    private AdapterListViewLeader mAdapter;
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
        setContentView ( R.layout.activity_leader );
        Toolbar toolbar = findViewById( R.id.toolbarleader );
        setSupportActionBar( toolbar );

        iniciaComponentes();
        inicializarFirebase();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);

        readOnlyActive();

        FloatingActionButton fab = findViewById( R.id.fabLeader );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCelula = new Intent(LeaderActivity.this, AddLeaderActivity.class);
                startActivity( addCelula );
                finish();
            }
        } );

    }

    private void iniciaComponentes ( ) {
        recyclerView = findViewById( R.id.listviewLeader );
        recyclerView.setLongClickable(true);
    }

    private void readOnlyActive() {
        final String ui = UI.getUid ();
        novaRef = databaseReference.child( "leaders/");
        query = novaRef.orderByChild( "userId").startAt(ui).endAt(ui).limitToLast(limitebusca);
        queryListener =  new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid = "";
                arrayLeader.clear();
                  for(DataSnapshot dados : dataSnapshot.getChildren()) {
                      try {
                          Leader u = dados.getValue (Leader.class);
                          arrayLeader.add(u);
                      } catch ( Exception e ) {
                          e.printStackTrace ( );
                      }
                  }
                List < Leader > leaders = arrayLeader;

                mAdapter = new AdapterListViewLeader ( leaders );
                mAdapter.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick ( View v ) {
                        uid = arrayLeader.get(recyclerView.getChildAdapterPosition (v)).getUid ();
                        nome = arrayLeader.get(recyclerView.getChildAdapterPosition (v)).getNome ();
                        if (mItemPressed) {
                            // Multi-item swipes not handled
                            return;
                        }
                        Intent intent = new Intent( LeaderActivity.this, ReadLeaderActivity.class );
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
                hiddShowMessage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,"Erro Database"+ databaseError.toException() );
            }
        } ;

        query.addValueEventListener (queryListener );


    }

    // Mostra memsagem se lista vir vazia
    private void hiddShowMessage() {
        // Mostra a mensagem em caso de lista fazia
        CardView cardView = findViewById (R.id.cardViewLeaders);
        ImageView image = findViewById (R.id.imageViewLeader);
        TextView text = findViewById (R.id.textMensagemLeader);
        if(arrayLeader.size() == 0){
            recyclerView.setVisibility (View.GONE);
            cardView.setVisibility (View.VISIBLE);
        }else{
            cardView.setVisibility (View.GONE);
            recyclerView.setVisibility (View.VISIBLE);
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp( LeaderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @Override
    public void onBackPressed() {
        LeaderActivity.this.finish();
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