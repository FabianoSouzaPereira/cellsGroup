package br.com.cellsgroup.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class ResolveDate {

    static Boolean hasError = false;
    private static String DataTime;
    private static String DataT;

    public static Boolean getDateRes(String dates){

      //  Date dataHoraAtual = new Date();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault ());

        sdf.setLenient(false);

        try {

            Date date = sdf.parse(dates);
          return  hasError = false;

        } catch ( ParseException e) {
            e.printStackTrace();
            return hasError = true;
        }
    }
}
