package br.com.cellsgroup;

import android.content.res.*;

import java.util.*;

public class LanguageHelper{
   public static Configuration configuration;
   static String locale;
   
   public static String getLocale(){
      configuration =  new Configuration(Resources.getSystem().getConfiguration());
      locale= configuration.locale.toString();
      
      if( "es_ES".equals( locale ) ){
        return "es_ES";
      }else if( "en_EN".equals( locale ) ){
         return "en_EN";
      }else{
         return "pt_BR";
      }

   }
   
   public static void changeLocale( Resources resources ,  String locale){

      Configuration config;
      config = new Configuration(resources.getConfiguration());

      switch(locale){
         case "es":
            config.locale = new Locale("es" );
            break;
         case "en":
            config.locale = Locale.ENGLISH;
            break;
         default:
            config.locale = new Locale("pt-BR");
      }
      resources.updateConfiguration(config, resources.getDisplayMetrics( ));
   }
   
   @Override
   public boolean equals(Object o) {

      if (this == o)
         return true;
      if (o == null)
         return false;
      if (getClass() != o.getClass())
         return false;

      // field comparison
      return Objects.equals("es_ES", locale)
         && Objects.equals("en_EN", locale);
   }
   
}
