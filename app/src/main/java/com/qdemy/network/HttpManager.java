package com.qdemy.network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager extends AsyncTask<String, Void, String> {

    private URL urlJSON;
    private HttpURLConnection conexiune;
    private BufferedReader bufferedReader;
    private StringBuilder rezultat = new StringBuilder();


    @Override
    protected String doInBackground(String... strings) {

        try {
            String rand;
            urlJSON = new URL(strings[0]);
            conexiune = (HttpURLConnection) urlJSON.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(conexiune.getInputStream()));


            while((rand = bufferedReader.readLine())!=null)
                rezultat.append(rand);
        }

        catch (Exception e) {
            e.printStackTrace();
        }


        if(bufferedReader!=null)
                try { bufferedReader.close();}
                catch (Exception e) { e.printStackTrace();}
        if(conexiune!=null)  conexiune.disconnect();


        return rezultat.toString();
    }
}
