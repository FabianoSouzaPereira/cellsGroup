package br.com.cellsgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static br.com.cellsgroup.HomeActivity.igreja;

@SuppressWarnings("ALL")
public class DeleteCelulaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid_extra;
    private TextInputLayout celulaParaApagar;
    private TextInputLayout apagarCelula;
    private Button btnApagarCelula;
    private DatabaseReference novaRef3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_delete_celula );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        inicializarFirebase();

        Intent intent = getIntent();
        uid_extra = intent.getStringExtra( "Celula" );
        novaRef3 = databaseReference.child( "Igrejas/" + igreja + "/Celulas");
        inicializarComponentes();
        Objects.requireNonNull(celulaParaApagar.getEditText() ).setText( uid_extra );
        btnApagarCelula.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCelula();
            }
        } );


        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }


    private void inicializarComponentes() {
        celulaParaApagar = findViewById( R.id.tvCelulaParaApagar );
        apagarCelula = findViewById(R.id.tvApagarCelula);
        btnApagarCelula = findViewById(R.id.btnApagarCelula);
    }

    //ToDo Falta fazer no futuro um metodo que não apague a celula permanentemento usando status= 0 ;
    private void deleteCelula() {

        novaRef3.child( uid_extra );

        if(!TextUtils.isEmpty( uid_extra )){
            novaRef3.child( uid_extra ).removeValue();
            Toast.makeText(this,"Célula Apagada com sucesso", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Erro ao tentar Apagar a célula !", Toast.LENGTH_LONG).show();
        }

        Intent celulas = new Intent( DeleteCelulaActivity.this,CelulasActivity.class);
        startActivity( celulas );
        finish();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(DeleteCelulaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.delete_celula, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_cancel_delete) {
            Intent celulas = new Intent( DeleteCelulaActivity.this,CelulasActivity.class);
            startActivity( celulas );
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
