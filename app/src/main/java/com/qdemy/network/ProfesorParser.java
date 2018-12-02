package com.qdemy.network;

import android.content.Context;

import com.qdemy.R;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfesorParser {

    public static Profesor getProfesorJSON(Context context, String json) throws JSONException {
        if (json == null) return null;

        JSONObject object = new JSONObject(json);

        String nume = object.getString(context.getString(R.string.nume1));
        String utilizator = object.getString(context.getString(R.string.utilizator1));
        String parola = object.getString(context.getString(R.string.parola1));
        String mail = object.getString(context.getString(R.string.mail));

        JSONArray arrayMaterii = object.getJSONArray(context.getString(R.string.materii));
        List<String> materii = new ArrayList<>();
        for (int i = 0; i < arrayMaterii.length(); i++) {
            String materie = arrayMaterii.getString(i);
            if (materie != null)  materii.add(materie);
        }

        List<IntrebareGrila> intrebari = getIntrebari(object.getJSONArray(context.getString(R.string.intrebari)), context);
        List<Test> teste = getTeste(object.getJSONArray(context.getString(R.string.teste)), context);


        return new Profesor(nume, utilizator, parola, mail, materii, intrebari, teste);
    }



    private static List<IntrebareGrila> getIntrebari(JSONArray jsonArray, Context context) throws JSONException {
        if (jsonArray == null) return null;

        List<IntrebareGrila> intrebari = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            IntrebareGrila intrebare = getIntreabreJSON(jsonArray.getJSONObject(i), context);
            if (intrebare != null) intrebari.add(intrebare);
        }

        return intrebari;
    }

    private static IntrebareGrila getIntreabreJSON(JSONObject jsonObject, Context context) throws JSONException {
        if (jsonObject == null) return null;

        String nume = jsonObject.getString(context.getString(R.string.nume1));
        String continut = jsonObject.getString(context.getString(R.string.continut));
        String materie = jsonObject.getString(context.getString(R.string.materie));

        List<String> variante = new ArrayList<>();
        JSONArray arrayVariante = jsonObject.getJSONArray(context.getString(R.string.variante));
        if (arrayVariante != null)
            for (int i = 0; i < arrayVariante.length(); i++) {
                String varianta = arrayVariante.getString(i);
                if (varianta != null) variante.add(varianta);
            }

        List<Boolean> raspunsuri = new ArrayList<>();
        JSONArray arrayRaspunsuri = jsonObject.getJSONArray(context.getString(R.string.raspunsuri));
        if (arrayRaspunsuri != null)
            for (int i = 0; i < arrayRaspunsuri.length(); i++) {
                Boolean raspuns = arrayRaspunsuri.getBoolean(i);
                raspunsuri.add(raspuns);
            }

        double punctaj = jsonObject.getDouble(context.getString(R.string.punctaj1));

        return new IntrebareGrila(nume, continut, materie, variante, raspunsuri, punctaj);
    }




    private static List<Test> getTeste(JSONArray jsonArray, Context context) throws JSONException {
        if (jsonArray == null) return null;

        List<Test> teste = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Test test = getTestJSON(jsonArray.getJSONObject(i), context);
            if (test != null) teste.add(test);
        }

        return teste;
    }

    private static Test getTestJSON(JSONObject jsonObject, Context context) throws JSONException {
        if (jsonObject == null) return null;

        String nume = jsonObject.getString(context.getString(R.string.nume1));
        String descriere = jsonObject.getString(context.getString(R.string.descriere));
        String autor = jsonObject.getString(context.getString(R.string.autor));
        List<IntrebareGrila> intrebari = getIntrebari(jsonObject.getJSONArray(context.getString(R.string.intrebari)), context);
        int minute = jsonObject.getInt(context.getString(R.string.minute));
        Boolean estePublic = jsonObject.getBoolean(context.getString(R.string.estePublic));

        return new Test(nume,descriere,autor,intrebari,minute,estePublic);
    }


}
