package br.com.cellsgroup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import br.com.cellsgroup.home.HomeActivity;

import static br.com.cellsgroup.home.HomeActivity.Logado;
import static br.com.cellsgroup.home.HomeActivity.tag;
public final class Activity_splash_screen extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        if( Logado ){
            tag = "1";
            Intent intent = new Intent( Activity_splash_screen.this , HomeActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );
            finish( );
        }else{
            setContentView( R.layout.activity_splash_screen );
            Handler handle = new Handler( );
            handle.postDelayed( new Runnable( ){
                @Override
                public void run( ){
                    tag = "1";
                    Intent intent = new Intent( Activity_splash_screen.this , HomeActivity.class );
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( intent );
                    finish( );
                }
            } , Activity_splash_screen.SPLASH_TIME_OUT );
        }
    
    }

}
