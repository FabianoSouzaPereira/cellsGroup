package br.com.cellsgroup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import static br.com.cellsgroup.HomeActivity.tag;
public final class Activity_splash_screen extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );
        Handler handle = new Handler();
        handle.postDelayed( new Runnable() {
            @Override
            public void run() {
                tag = "1";
                Intent intent = new Intent( Activity_splash_screen.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }

}
