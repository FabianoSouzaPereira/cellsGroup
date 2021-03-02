package br.com.cellsgroup;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.*;
import android.os.Bundle;
import android.text.*;
import android.util.*;
import android.widget.*;
import com.google.android.material.textfield.*;
import java.io.*;

public class ManualActivity extends AppCompatActivity{
   private EditText editTextManual;
   @Override
   protected void onCreate( Bundle savedInstanceState ){
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_manual );
      
     editTextManual = findViewById(R.id.editTextManual );
      try{
         Resources res =  getResources();
         InputStream in = res.openRawResource( R.raw.manual );
      
         byte[] b = new byte[in.available()];
         in.read(b);
         String text = new String(b);
         editTextManual.setText( Html.fromHtml(text) );
      
      }catch (Exception e){
         Log.d("log ", String.valueOf( e ) );
      }
   }
}