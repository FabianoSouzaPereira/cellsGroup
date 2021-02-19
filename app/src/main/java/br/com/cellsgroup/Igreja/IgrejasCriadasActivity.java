package br.com.cellsgroup.Igreja;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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

import static br.com.cellsgroup.home.HomeActivity.uidIgreja;


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
    private Button btnIgreja;
    String mensagem1 = "";
    String mensagem2 = "";
    String mensagem3 = "";
    String mensagem4 = "";
    String mensagem5 = "";
    String mensagem6 = "";
    String mensagem7 = "";
    String mensagem8 = "";
    String mensagem9 = "";
    String mensagem10 = "";
    String mensagem11 = "";

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
        btnIgreja = findViewById (R.id.btnIgreja );
        btnIgreja.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent addigreja = new Intent ( IgrejasCriadasActivity.this  , addIgrejaActivity.class );
                startActivity ( addigreja );
            }
        } );

        readIgrejaCadastrada();

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_SHORT ).setAction( "Action", null ).show();
            }
        } );

    }

    private void readIgrejaCadastrada() {
        final String ui = HomeActivity.UI.getUid ().toString ();
        novaRef = databaseReference.child( "churchs/"  + uidIgreja);
        Query query = novaRef.orderByChild ("user").equalTo (ui);
        listener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ig.clear();
                    for(DataSnapshot sd : dataSnapshot.getChildren ()) {
                        String key = sd.getKey();
                        if(!key.equalsIgnoreCase ( "members" )
                            && !key.equalsIgnoreCase ( "leaders" )
                            && !key.equalsIgnoreCase ( "cells" )
                            && !key.equalsIgnoreCase ( "reports" )
                            && !key.equalsIgnoreCase ( "intercession" )
                            && !key.equalsIgnoreCase ( "Skedule" )
                        ) {

                            Igreja igr = sd.getValue ( Igreja.class );
                            if (igr.getUser() != null) {
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
                                    String telefone_fixo = igr.getPhone_fixo ();
                                    String igrejaID = igr.getIgrejaID ();
                                    String members = igr.getMembers ();

                                    ig.add ( mensagem1 + ": " + group );
                                    ig.add ( mensagem2 + ": " + nome );
                                    ig.add ( mensagem3 + ": " + endereco );
                                    ig.add ( mensagem4 + ": " + bairro );
                                    ig.add ( mensagem5 + ": " + cidade );
                                    ig.add ( mensagem6 + ": " + estado );
                                    ig.add ( mensagem7 + ": " + pais );
                                    ig.add ( mensagem8 + ": " + cep );
                                    ig.add ( mensagem9 + ": " + ddi );
                                    ig.add ( mensagem10 + ": " + telefone );
                                    ig.add ( mensagem11 + ": " + telefone_fixo);
                                }
                            }
                        }
                    }

                List<String> igrejas = ig;

                mAdapter = new AdapterListViewIgreja(igrejas, IgrejasCriadasActivity.this, IgrejasCriadasActivity.this );
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                hiddShowMessage();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                hiddShowMessage();
                String errorPermission = "";
                Snackbar snackbar = Snackbar.make(findViewById (R.id.constrantLayoutIgrejas), R.string.Permission_denied, Snackbar.LENGTH_INDEFINITE);
                snackbar.setTextColor (getColor (R.color.colorWhite ) );
                snackbar.show();
            }
        };
        query.addListenerForSingleValueEvent (listener);
        query.removeEventListener (listener);
    }

    // Mostra memsagem se lista vir vazia
    private void hiddShowMessage(){

        CardView cardView = findViewById (R.id.cardViewIgrejas);
        if( ig.size() == 0){
            recyclerView.setVisibility (View.GONE);
            cardView.setVisibility (View.VISIBLE);
        }else{
            cardView.setVisibility (View.GONE);
            recyclerView.setVisibility (View.VISIBLE);
        }
    }

    private void inicializaComponentes() {
        recyclerView = findViewById(R.id.recyclerViewIgreja );
        mensagem1 = getResources ().getString (R.string.denominacao);
        mensagem2 = getResources ().getString (R.string.nome);
        mensagem3 = getResources ().getString (R.string.endere_o);
        mensagem4 = getResources ().getString (R.string.bairro);
        mensagem5 = getResources ().getString (R.string.cidade);
        mensagem6 = getResources ().getString (R.string.estado);
        mensagem7 = getResources ().getString (R.string.pa_s);
        mensagem8 = getResources ().getString (R.string.cep);
        mensagem9 = getResources ().getString (R.string.DDI);
        mensagem10 = getResources ().getString (R.string.telefone);
        mensagem11 = getResources ().getString (R.string.telefone_fixo);
    }

    @Override
    protected void onStart ( ) {
        super.onStart ( );
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
            if( ig.size() != 0) {
                Intent intent = new Intent ( IgrejasCriadasActivity.this , EditIgrejaActivity.class );
                intent.putExtra ( "Igreja" , "" + uid );
                intent.putExtra ( "Nome" , "" + nome );
                startActivity ( intent );
            }
            return true;
        }
        if(id == R.id.action_delete){
            if( ig.size() != 0) {
                Intent intent = new Intent ( IgrejasCriadasActivity.this , DeleteIgrejaActivity.class );
                intent.putExtra ( "Igreja" , "" + uid );
                intent.putExtra ( "Nome" , "" + nome );
                startActivity ( intent );
            }
            return true;
        }

        return super.onOptionsItemSelected( item );
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
