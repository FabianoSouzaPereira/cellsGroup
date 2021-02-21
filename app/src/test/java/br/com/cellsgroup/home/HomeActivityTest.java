package br.com.cellsgroup.home;

import android.content.Context;

import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {
    Context appContext = getInstrumentation ().getContext ();
    public static FirebaseUser UI;
 
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
 
 @Test
    public void onCreate ( ) {
    HomeActivityTest.UI = FirebaseAuth.getInstance().getCurrentUser();
        Assert.assertNotNull ( HomeActivityTest.UI );
        inicializarFirebase();
    }

    @Test
    public void inicializarFirebase ( ) {
        FirebaseApp.initializeApp(appContext);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @Test
    public void onStart ( ) {
    }

    @Test
    public void pegarPadroes ( ) {
    }

    @Test
    public void onResume ( ) {
    }

    @Test
    public void onRequestPermissionsResult ( ) {
    }

    @Test
    public void onPause ( ) {
    }

    @Test
    public void onStop ( ) {
    }

    @Test
    public void onDestroy ( ) {
    }

    @Test
    public void onRestart ( ) {
    }

    @Test
    public void onBackPressed ( ) {
    }

    @Test
    public void addDataHora ( ) {
    }

    @Test
    public void init ( ) {
    }

    @Test
    public void onCreateOptionsMenu ( ) {
    }

    @Test
    public void onPrepareOptionsMenu ( ) {
    }

    @Test
    public void onOptionsItemSelected ( ) {
    }

    @Test
    public void onNavigationItemSelected ( ) {
    }

    @Test
    public void cardcellClick ( ) {
    }

    @Test
    public void cardcomunicacaoClick ( ) {
    }

    @Test
    public void cardintercessaoClick ( ) {
    }

    @Test
    public void cardagendaClick ( ) {
    }

    @Test
    public void cardleaderClick ( ) {
    }

    @Test
    public void cardcontatoClick ( ) {
    }

    @Test
    public void cardrelatorioClick ( ) {
    }

    @Test
    public void getFirebaseDatabase ( ) {
    }

    @Test
    public void getDatabaseReference ( ) {
    }
}