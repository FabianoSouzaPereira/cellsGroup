package br.com.cellsgroup;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.*;
import android.os.Bundle;
import android.text.*;
import android.util.*;
import android.widget.*;
import com.google.android.material.textfield.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ManualActivity extends AppCompatActivity{
   private EditText editTextManual;
   private static int MANUAL;
   @Override
   protected void onCreate( Bundle savedInstanceState ){
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_manual );
     String language = LanguageHelper.getLocale();
   
      try{
         if(language.contains("es_")){
            MANUAL = R.raw.manual_es;
         }else if(language.contains("pt_")){
            MANUAL = R.raw.manual;
         }else{
            MANUAL = R.raw.manual_en;
         }
      }catch( Exception e ){
         e.printStackTrace( );
      }
      
      editTextManual = findViewById(R.id.editTextManual );
      try{
         Resources res =  getResources();
         InputStream in = res.openRawResource( MANUAL);
      
         byte[] b = new byte[in.available()];
         in.read(b);
         String text = new String(b);
         editTextManual.setText( Html.fromHtml(text) );
      
      }catch (Exception e){
         Log.d("log ", String.valueOf( e ) );
      }
   }
}