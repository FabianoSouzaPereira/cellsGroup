package br.com.cellsgroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.home.HomeActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;

public class EnviarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_enviar );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            }
        } );
        DrawerLayout drawer = findViewById( R.id.drawer_activity_enviar);
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_activity_enviar );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_config , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );
        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( EnviarActivity.this, CelulasActivity.class );
            startActivity( celulas );
        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( EnviarActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );
        } else if (id == R.id.nav_intersession) {
            Intent intercessao = new Intent( EnviarActivity.this, IntercessaoActivity.class );
            startActivity( intercessao );
        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( EnviarActivity.this, AgendaActivity.class );
            startActivity( agenda );
        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( EnviarActivity.this, ContatoActivity.class );
            startActivity( contato );
        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( EnviarActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );
        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( EnviarActivity.this, EnviarActivity.class );
            startActivity( Enviar );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_del_celula );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
