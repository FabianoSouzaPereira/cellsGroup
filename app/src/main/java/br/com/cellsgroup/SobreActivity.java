package br.com.cellsgroup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sobre );
    }

    @Override
    public void onBackPressed() {
      super.onBackPressed();
     }

}
