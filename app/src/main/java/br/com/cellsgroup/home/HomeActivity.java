package br.com.cellsgroup.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.cellsgroup.Activity_splash_screen;
import br.com.cellsgroup.CompartilharActivity;
import br.com.cellsgroup.Igreja.IgrejasCriadasActivity;
import br.com.cellsgroup.comunicados.ComunicadosActivity;
import br.com.cellsgroup.Configuracao;
import br.com.cellsgroup.contato.ContatoActivity;
import br.com.cellsgroup.EnviarActivity;
import br.com.cellsgroup.Igreja.addIgrejaActivity;
import br.com.cellsgroup.intercessao.IntercessaoActivity;
import br.com.cellsgroup.R;
import br.com.cellsgroup.SobreActivity;
import br.com.cellsgroup.VisaoActivity;
import br.com.cellsgroup.agenda.AgendaActivity;
import br.com.cellsgroup.celulas.CelulasActivity;
import br.com.cellsgroup.models.igreja.Igreja;
import br.com.cellsgroup.models.igreja.Members;
import br.com.cellsgroup.models.login.LoginActivity;
import br.com.cellsgroup.relatorios.RelatorioActivityView;
import br.com.cellsgroup.usuario.AddUsuarioActivity;
import br.com.cellsgroup.usuario.UsuarioActivity;

import static br.com.cellsgroup.models.login.LoginActivity.updateUI;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "IGREJA PADRAO -> ";
    public static FirebaseUser UI;
    public static Object groups;
    public static String group="";
    public static String igreja = "";
    public static String useremail = "";
    public  static String cellPhone = "";
    public static String uidIgreja = "";

    public static boolean typeUserAdmin = true;
    public static boolean typeUserNormal = false;

    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaref = null;
    private DatabaseReference novaref2 = null;
    private FirebaseFunctions mFunctions;

    public static boolean Logado = false;
    public static String tag = "0";
    public String DataTime;
    public String DataT;
    public String status = "1";

    private final int count = 0;
    private final int limitebusca = 500;

    //Variaveis de MENU
    int addigreja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        UI = FirebaseAuth.getInstance().getCurrentUser();
        updateUI( UI ); //verifica se usuario está logado
        setContentView( R.layout.activity_home );
        splashScreean();
        if ( !Logado ){
            Intent intent = new Intent( HomeActivity.this, LoginActivity.class );
            startActivity( intent );
            finish();
        }
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();

        Toolbar toolbar = findViewById( R.id.toolbarhome );
        setSupportActionBar( toolbar );
        inicializarFirebase();
        addDataHora();
        mAuth.getUid ();


        DrawerLayout drawer = findViewById( R.id.drawer_activityHome);
        NavigationView navigationView = findViewById( R.id.nav_view_home );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );

    }

    public void pegarPadroes() {
        String email = mAuth.getCurrentUser().getEmail();

        if ( email.isEmpty()){
            Toast.makeText( this, "Sem email cadastrado", Toast.LENGTH_LONG ).show();
            return;
        }
            novaref = databaseReference.child( "users/");
            Query query = novaref.orderByChild( "email" ).equalTo( email ).limitToFirst (1);
            query.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                @Override
                public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Object valuegroup = ds.child("group").getValue ();
                        Object valueEmail = ds.child("email").getValue ();
                        Object valueIgreja = ds.child("igrejaPadrao").getValue();
                        Object valuePhone = ds.child ("telefone").getValue ();
                        useremail = valueEmail.toString ();
                        igreja = valueIgreja.toString ();
                        group = valuegroup.toString ();
                        cellPhone = valuePhone.toString ();
                    }
                    Log.d( "email-------------> ",""+ useremail );
                    Log.d( "igreja------------> ",""+ igreja );
                    Log.d( "group------------> ",""+ group );
                    Log.d( "cellPhone------------> ",""+ cellPhone );
                  //  count = 1;
                }

                @Override
                public void onCancelled ( @NonNull DatabaseError error ) {

                }
            } );

        //carrega dados da igreja cadastrada
        final String ui = UI.getUid ();
        novaref2 = databaseReference.child ("churchs/");
        Query query3 = novaref2.orderByChild ("igrejaId").limitToFirst (1);
        query3.addValueEventListener (new ValueEventListener ( ) {
                 @Override
                public void onDataChange ( @NonNull DataSnapshot datasnapshot ) {
                     for(DataSnapshot ds : datasnapshot.getChildren ()) {
                         for ( DataSnapshot sd : ds.getChildren ( ) ) {
                                String key = sd.getKey ();
                             if(!key.equalsIgnoreCase ( "members" )
                                 && !key.equalsIgnoreCase ( "cells" )
                                 && !key.equalsIgnoreCase ( "reports" )
                                 && !key.equalsIgnoreCase ( "intercession" )
                             ) {

                                 Igreja ig = sd.getValue ( Igreja.class );

                                 String members = ig.getMembers ( );
                                 String user = ig.getUser ( );
                                 igreja = ig.getNome ( );
                                 group = ig.getGroup ( );
                                 uidIgreja = ig.getIgrejaID ( );
                             }
                         }
                     }
                    Log.d( "uidIgreja------------> ",""+ uidIgreja);
                }

                @Override
                public void onCancelled ( @NonNull DatabaseError error ) {

                }
            });



    }

    @Override
    protected void onStart() {
        super.onStart();
        UI = FirebaseAuth.getInstance().getCurrentUser();
        updateUI( UI ); //verifica se usuario está logado
        if ( !Logado ){
            Intent intent = new Intent( HomeActivity.this, LoginActivity.class );
            startActivity( intent );
        }
        pegarPadroes();
    //    initAlertDialogoIgreja();  //verifica se tem br.com.cellsgroup.models.igreja cadastrada
    //    initAlertDialogoUsuario();
    }


    @Override
    protected void onResume() {
        pegarPadroes();
        super.onResume();
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
            builder  = builder.setMessage( "Deseja encerrar o aplicativo mesmo ?" );
            builder.setTitle( "Encerrando o aplicativo..." )
                    .setCancelable( false )
                    .setNegativeButton( "cancelado", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
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

    private void splashScreean() {
        if (tag == "0") {
            Intent i = new Intent( HomeActivity.this, Activity_splash_screen.class );
            startActivity( i );
            finish();
        }
    }

    public void initAlertDialogoIgreja(){
        if(igreja.equals ("")) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder( HomeActivity.this );
            builder1 = builder1.setMessage( "Deseja criar e associar uma br.com.cellsgroup.models.igreja ?" );
            builder1.setTitle( "Não existe Igreja associada ao seu app..." ).setCancelable( false ).setNegativeButton( "cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText( getApplicationContext(), "Cancelar", Toast.LENGTH_SHORT ).show();
                }
            } ).setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getApplicationContext(), "Ok escolhido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( HomeActivity.this, addIgrejaActivity.class );
                    startActivity( intent);
                }
            } );

            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();
        }
    }

    public void initAlertDialogoUsuario(){
        if(useremail.equals ("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder( HomeActivity.this );
            builder = builder.setMessage( "É necessário a criação de um usuário.\n Podemos proceguir ?" );
            builder.setTitle( "Não existe usuario associado ao seu app..." ).setCancelable( false ).setNegativeButton( "cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText( getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT ).show();
                }
            } ).setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getApplicationContext(), "Ok escolhido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( HomeActivity.this, AddUsuarioActivity.class );
                    startActivity( intent);
                }
            } );

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(HomeActivity.this);  //inicializa  o SDK credenciais padrão do aplicativo do Google
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    /* Create the arguments to the callable function. */
    private Task<String> addMessage(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("addMessage")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }

    @SuppressLint("SimpleDateFormat")
    public void addDataHora() {

        try {
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
            DataTime = data + " "+ hora;
            DataT = data;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(){
        int addigreja = R.id.action_addIgreja;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Intent addigreja = new Intent ( HomeActivity.this , addIgrejaActivity.class );
            startActivity ( addigreja );
            return true;
        } else if ( itemId == R.id.action_readIgreja ) {
            Intent readigreja = new Intent ( HomeActivity.this , IgrejasCriadasActivity.class );
            startActivity ( readigreja );
            return true;
        }else if ( itemId == R.id.action_Usuario ) {
            Intent addusuario = new Intent ( HomeActivity.this , UsuarioActivity.class );
            startActivity ( addusuario );
            return true;
        }else if ( itemId == R.id.action_addUsuario ) {
            Intent addusuario = new Intent ( HomeActivity.this , AddUsuarioActivity.class );
            startActivity ( addusuario );
            return true;
        } else if ( itemId == R.id.action_Login ) {
            Intent login = new Intent ( HomeActivity.this , LoginActivity.class );
            startActivity ( login );
            return true;
        } else if ( itemId == R.id.action_Logout ) {
            FirebaseAuth.getInstance ( ).signOut ( );
            updateUI ( null );
            Toast.makeText ( this , getString ( R.string.Logout_sucesso ) , Toast.LENGTH_LONG ).show ( );
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

        } else if (id == R.id.nav_view) {
            Intent visao = new Intent( HomeActivity.this, VisaoActivity.class );
            startActivity( visao );

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

    public void cardcomunicacaoClick(View view) throws Exception {
        Intent comunidados = new Intent(HomeActivity.this,ComunicadosActivity.class);
        startActivity( comunidados );
    }

    public void cardintercessaoClick(View view) throws Exception {
        Intent intercessao = new Intent( HomeActivity.this,IntercessaoActivity.class );
        startActivity( intercessao );
    }

    public void cardagendaClick(View view) throws Exception {
        Intent agenda = new Intent( HomeActivity.this,AgendaActivity.class );
        startActivity( agenda );
    }

    public void cardvisaoClick(View view) throws Exception{
        Intent visao = new Intent( HomeActivity.this,VisaoActivity.class );
        startActivity( visao );
    }

    public void cardcontatoClick(View view) throws Exception {
        Intent contato = new Intent( HomeActivity.this,ContatoActivity.class );
        startActivity( contato );
    }

/*
    public void cardcadastroemailClick(View view) throws Exception {
        Intent cadastroemail = new Intent(HomeActivity.this,CadastroemailActivity.class);
        startActivity( cadastroemail );

    }

    public void cardcadastroemailClick(MenuItem item) {
        Intent cadastroemail = new Intent(HomeActivity.this,CadastroemailActivity.class);
        startActivity( cadastroemail );
    }
*/
    public void cardrelatorioClick(View view) {
        Intent relatorio = new Intent( HomeActivity.this, RelatorioActivityView.class );
        startActivity( relatorio );
    }


    /**
     * @return firebaseDatabase
     */
    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    /**
     * @return databaseReference
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

}