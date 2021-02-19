package br.com.cellsgroup.models.filesmanipulation;


import android.content.res.Resources;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings( "ResultOfMethodCallIgnored" )
public class LoadFiles {

    //a handle to the application's resources
    private Resources resources;
    //a string to output the contents of the files to LogCat
    private String output;

    private void loadFile(){
        //get the application's resources
        resources = Resources.getSystem();

        try
        {
            //Load the file from the raw folder - don't forget to OMIT the extension
            output = LoadFile("from_raw_folder", true);
            //output to LogCat
            Log.i("test", output);
        }
        catch (IOException e)
        {
            //display an error toast message
         ///   Toast toast = Toast.makeText( this, "Cancelar", Toast.LENGTH_SHORT ).show();
         //   toast.show();
        }

        try
        {
            //Load the file from assets folder - don't forget to INCLUDE the extension
            output = LoadFile("from_assets_folder.txt", false);
            //output to LogCat
            Log.i("test", output);
        }
        catch (IOException e)
        {
            //display an error toast message
          //  Toast toast = Toast.makeText(this, "File: not found!", Toast.LENGTH_LONG);
          //  toast.show();
        }
    }

    //load file from apps res/raw folder or Assets folder
    public String LoadFile(String fileName, boolean loadFromRawFolder) throws IOException
    {
        //Create a InputStream to read the file into
        InputStream iS;

        if (loadFromRawFolder)
        {
            //get the resource id from the file name
            int rID = resources.getIdentifier("fortyonepost.com.lfas:raw/"+fileName, null, null);
            //get the file as a stream
            iS = resources.openRawResource(rID);
        }
        else
        {
            //get the file as a stream
            iS = resources.getAssets().open(fileName);
        }

        //create a buffer that has the same size as the InputStream
        byte[] buffer = new byte[iS.available()];
        //read the text file as a stream, into the buffer
        iS.read(buffer);
        //create a output stream to write the buffer into
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        //write this buffer to the output stream
        oS.write(buffer);
        //Close the Input and Output streams
        oS.close();
        iS.close();

        //return the output stream as a String
        return oS.toString();
    }


}
