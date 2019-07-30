package br.com.ieqcelulas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import static br.com.ieqcelulas.HomeActivity.tag;
public final class Activity_splash_screen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
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
