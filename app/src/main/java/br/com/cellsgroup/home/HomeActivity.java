package br.com.cellsgroup.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.functions.FirebaseFunctions;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.cellsgroup.R;
import br.com.cellsgroup.*;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.igreja.IgrejasCriadasActivity;
import br.com.cellsgroup.igreja.addIgrejaActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.leader.LeaderActivity;
import br.com.cellsgroup.models.igreja.Igreja;
import br.com.cellsgroup.models.login.LoginActivity;
import br.com.cellsgroup.relatorios.RelatorioActivityView;

import static br.com.cellsgroup.models.login.LoginActivity.updateUI;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static FirebaseUser UI;
    public static String group="";
    public static String igreja = "";
    public static String useremail = "";
    public static String uidIgreja = "";
    public static String useremailAuth = "";

    public static boolean typeUserAdmin = true;

    public FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaref2 = null;
    private FirebaseFunctions mFunctions;
    public static final int  Permission_All = 1;
    public static final int PERMISSION_CODE = 3;
    public static final String[] Permissions = new String[]{
        // Any permision is necessary
    };
    public static boolean Logado = false;
    public static String tag = "0";

    TextView nhTitle;
    TextView nhEmail;
    TextView nhName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        UI = FirebaseAuth.getInstance().getCurrentUser();
        if(UI != null) {
            updateUI ( UI ); //verifica se leader está logado
        }
        setContentView( R.layout.activity_home );
        if ( !Logado ){
            Intent intent = new Intent( HomeActivity.this, LoginActivity.class );
            startActivity( intent );
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();

        Toolbar toolbar = findViewById( R.id.toolbarhome );
        setSupportActionBar( toolbar );
        inicializarFirebase();
        init();
        addDataHora();
        mAuth.getUid ();
        try {
            if ( mAuth != null ) {
                useremailAuth = mAuth.getCurrentUser ().getEmail ();
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }

        DrawerLayout drawer = findViewById( R.id.drawer_activityHome);
        NavigationView navigationView = findViewById( R.id.nav_view_home );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );

        View headerView = navigationView.getHeaderView(0);
        nhTitle = (TextView) headerView.findViewById (R.id.nhTitle);
        nhName = (TextView) headerView.findViewById (R.id.nhName);
        nhEmail = (TextView) headerView.findViewById (R.id.nhEmail);
        nhEmail.setText (useremailAuth);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(UI != null) {
            updateUI ( UI ); //verifica se leader está logado
        }
        if ( !Logado ){
            Intent intent = new Intent( HomeActivity.this, LoginActivity.class );
            startActivity( intent );
        }
        pegarPadroes();
    }

    public void pegarPadroes() {

        final String ui;
        if (  UI == null ) {
           return;
        }
        ui = UI.getUid ();
        if ( ui.isEmpty()){
            Toast.makeText( this, "Sem leader cadastrado", Toast.LENGTH_LONG ).show();
            return;
        }

        //carrega dados da igreja cadastrada
        novaref2 = databaseReference.child ("churchs/");
        Query query3 = novaref2.orderByChild ("igrejaID");
        query3.addValueEventListener (new ValueEventListener () {
                 @Override
                public void onDataChange ( @NonNull DataSnapshot datasnapshot ) {
                     for(DataSnapshot ds : datasnapshot.getChildren ()) {
                         for ( DataSnapshot sd : ds.getChildren ( ) ) {
                                String key = sd.getKey ();
                             if(
                                !key.equalsIgnoreCase ( "members" )
                                 && !key.equalsIgnoreCase ( "leaders" )
                                 && !key.equalsIgnoreCase ( "cells" )
                                 && !key.equalsIgnoreCase ( "reports" )
                                 && !key.equalsIgnoreCase ( "intercession" )
                                 && !key.equalsIgnoreCase ( "Skedule" )
                             ) {

                                 Igreja ig = sd.getValue ( Igreja.class );
                                 if (ig.getUser() != null) {
                                     if(ig.getUser ().equals (ui)) {
                                         String members = ig.getMembers ( );
                                         String user = ig.getUser ( );
                                         igreja = ig.getNome ( );
                                         group = ig.getGroup ( );
                                         uidIgreja = ig.getIgrejaID ( );
                                     }
                                 }
                             }
                         }
                     }
                    nhTitle.setText (group);
                    nhName.setText(igreja);
                }

                @Override
                public void onCancelled ( @NonNull DatabaseError error ) {

                }
            });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasPhonePermissions( this, Permissions )) {
            ActivityCompat.requestPermissions( this,Permissions,Permission_All );
        }
    }

    private static boolean hasPhonePermissions( Context context, String... permissions) {
        if(context != null && permissions != null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission( context, permission ) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText( HomeActivity.this, "Permission allowed" , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText( HomeActivity.this, "Permission denied" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
     AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder  = builder.setMessage( "Deseja encerrar o aplicativo ?" );
            builder.setTitle( "Encerrando o aplicativo..." )
                    .setCancelable( false )
                    .setNegativeButton( "cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          //  Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                              //  Finish this activity as well as all activities immediately below it in the current task that have the same affinity.
                                finishAffinity();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    });

            AlertDialog alertDialog = builder . create () ;
            alertDialog.show();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(HomeActivity.this);  //inicializa  o SDK credenciais padrão do aplicativo do Google
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {

        try {
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(){
        int addigreja = R.id.action_addIgreja;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.home, menu );
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu ( Menu menu ) {
        MenuItem addIgreja = menu.findItem(R.id.action_addIgreja);
        MenuItem igreja = menu.findItem(R.id.action_readIgreja);
        if( uidIgreja != null && !uidIgreja.equals ( "" ) ) {
            addIgreja.setVisible ( false );
            igreja.setVisible (true );
         }else{
            addIgreja.setVisible ( true );
            igreja.setVisible (false);
        }
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId ( );
        if ( itemId == R.id.action_settings ) {
            Intent config = new Intent ( HomeActivity.this , Configuracao.class );
            startActivity ( config );
            return true;
        } else if ( itemId == R.id.action_addIgreja ) {
            Intent addigreja = new Intent ( HomeActivity.this  , addIgrejaActivity.class );
            startActivity ( addigreja );
            return true;
        } else if ( itemId == R.id.action_readIgreja ) {
            Intent readigreja = new Intent ( HomeActivity.this , IgrejasCriadasActivity.class );
            startActivity ( readigreja );
            return true;
        }else if ( itemId == R.id.action_lideres) {
            Intent addlideres = new Intent ( HomeActivity.this , LeaderActivity.class );
            startActivity ( addlideres);
            return true;
        }else if ( itemId == R.id.action_manual) {
            Intent manual= new Intent ( HomeActivity.this , ManualActivity.class );
            startActivity ( manual);
            return true;
        }else if ( itemId == R.id.action_Sobre) {
            Intent sobre= new Intent ( HomeActivity.this , SobreActivity.class );
            startActivity ( sobre);
            return true;
        }else if ( itemId == R.id.action_Sair ) {
            finishAffinity ();
            return true;
        } else if ( itemId == R.id.action_Logout ) {
            FirebaseAuth.getInstance ( ).signOut ( );
            updateUI ( null );
            Toast.makeText ( this , getString ( R.string.Logout_sucesso ) , Toast.LENGTH_SHORT ).show ( );
            finishAffinity ();
            return true;
        }
        return super.onOptionsItemSelected ( item );

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent( this, HomeActivity.class );
            startActivity( home );

        } else if (id == R.id.nav_cells) {
            Intent celulas = new Intent( HomeActivity.this, CelulasActivity.class );
            celulas.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity( celulas );

        } else if (id == R.id.nav_communication) {
            Intent comunidados = new Intent( HomeActivity.this, ComunicadosActivity.class );
            startActivity( comunidados );

        } else if (id == R.id.nav_intersession) {
                Intent intercessao = new Intent( HomeActivity.this, IntercessaoActivity.class );
                startActivity( intercessao );

        } else if (id == R.id.nav_schedule) {
            Intent agenda = new Intent( HomeActivity.this, AgendaActivity.class );
            startActivity( agenda );

        } else if (id == R.id.nav_leader) {
            Intent agenda = new Intent( HomeActivity.this, LeaderActivity.class );
            startActivity( agenda );

        }else if (id == R.id.nav_realatorio) {
            Intent relatorio = new Intent( HomeActivity.this, RelatorioActivityView.class );
            startActivity( relatorio );

        } else if (id == R.id.nav_contact) {
            Intent contato = new Intent( HomeActivity.this, ContatoActivity.class );
            startActivity( contato );

        } else if (id == R.id.nav_share) {
            Intent compartilhar = new Intent( HomeActivity.this, CompartilharActivity.class );
            startActivity( compartilhar );

        } else if (id == R.id.nav_send) {
            Intent Enviar = new Intent( HomeActivity.this, EnviarActivity.class );
            startActivity( Enviar );

        }

        DrawerLayout drawer = findViewById( R.id.drawer_activityHome );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    /**  Click do card celula
     *
     */
    public void cardcellClick(View view) throws Exception{
        Intent celulas = new Intent(HomeActivity.this,CelulasActivity.class);
        startActivity( celulas );
    }

    public void cardigrejaClick(View view) throws Exception {
        Intent igrejas = new Intent(HomeActivity.this,IgrejasCriadasActivity.class);
        startActivity( igrejas );
    }

    public void cardagendaClick(View view) throws Exception {
        Intent agenda = new Intent( HomeActivity.this,AgendaActivity.class );
        startActivity( agenda );
    }

    public void cardleaderClick(View view) throws Exception{
        Intent leader = new Intent( HomeActivity.this,LeaderActivity.class );
        startActivity( leader );
    }

    public void cardcontatoClick(View view) throws Exception {
        Intent contato = new Intent( HomeActivity.this,ContatoActivity.class );
        startActivity( contato );
    }

    public void cardrelatorioClick(View view) {
        Intent relatorio = new Intent( HomeActivity.this, RelatorioActivityView.class );
        startActivity( relatorio );
    }
    
    public void cardintercessaoClick(View view) {
        Snackbar.make(view, getString ( R.string.implementacao_futura ), Snackbar.LENGTH_LONG)
           .setAction("Action", null)
           .setTextColor(getColor(R.color.colorWhite))
           .show();
    }
}
