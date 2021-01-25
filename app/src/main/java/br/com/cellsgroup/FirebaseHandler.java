package br.com.cellsgroup;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/* PROBLEMA
 * com.google.firebase.database.DatabaseException: Calls to setPersistenceEnabled() must be made before any other usage of FirebaseDatabase instance.
 * SOLUÇÃO
 * Create an application class that will be used across your entire application and initialize firebase Persistence in it:
 * FirebaseHandler class You can call/name the class whatever you want to name it
 */
public class FirebaseHandler extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}