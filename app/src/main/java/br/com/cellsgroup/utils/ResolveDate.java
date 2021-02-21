package br.com.cellsgroup.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class ResolveDate {
    static Boolean hasError = false;

    public static Boolean getDateRes(String dates){
        Boolean result;
    
        //  Date dataHoraAtual = new Date();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat( pattern , Locale.getDefault( ) );
    
        sdf.setLenient( false );
    
        try{
        
            Date date = sdf.parse( dates );
            result = hasError = false;
        
        }catch( ParseException e ){
            e.printStackTrace( );
            result = hasError = true;
        }
        return result;
    }
}
