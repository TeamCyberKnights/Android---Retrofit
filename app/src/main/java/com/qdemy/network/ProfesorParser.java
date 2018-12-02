package com.qdemy.network;

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

    public static Profesor getProfesorJSON(String json) throws JSONException {
        if (json == null) return null;

        JSONObject object = new JSONObject(json);

        String nume = object.getString("nume");
        String utilizator = object.getString("utilizator");
        String parola = object.getString("parola");
        String mail = object.getString("mail");

        JSONArray arrayMaterii = object.getJSONArray(String.valueOf(R.string.materii));
        List<String> materii = new ArrayList<>();
        for (int i = 0; i < arrayMaterii.length(); i++) {
            String materie = arrayMaterii.getString(i);
            if (materie != null)  materii.add(materie);
        }

        List<IntrebareGrila> intrebari = getIntrebari(object.getJSONArray(String.valueOf(R.string.intrebari)));
        List<Test> teste = getTeste(object.getJSONArray(String.valueOf(R.string.teste)));


        return new Profesor(nume, utilizator, parola, mail);
    }



    private static List<IntrebareGrila> getIntrebari(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null) return null;

        List<IntrebareGrila> intrebari = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            IntrebareGrila intrebare = getIntreabreJSON(jsonArray.getJSONObject(i));
            if (intrebare != null) intrebari.add(intrebare);
        }

        return intrebari;
    }

    private static IntrebareGrila getIntreabreJSON(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;

        String nume = jsonObject.getString(String.valueOf(R.string.nume));
        String continut = jsonObject.getString(String.valueOf(R.string.continut));
        String materie = jsonObject.getString(String.valueOf(R.string.materie));

        List<String> variante = new ArrayList<>();
        JSONArray arrayVariante = jsonObject.getJSONArray(String.valueOf(R.string.variante));
        if (arrayVariante != null)
            for (int i = 0; i < arrayVariante.length(); i++) {
                String varianta = arrayVariante.getString(i);
                if (varianta != null) variante.add(varianta);
            }

        List<Boolean> raspunsuri = new ArrayList<>();
        JSONArray arrayRaspunsuri = jsonObject.getJSONArray(String.valueOf(R.string.raspunsuri));
        if (arrayRaspunsuri != null)
            for (int i = 0; i < arrayRaspunsuri.length(); i++) {
                Boolean raspuns = arrayRaspunsuri.getBoolean(i);
                raspunsuri.add(raspuns);
            }

        double punctaj = jsonObject.getDouble(String.valueOf(R.string.punctaj1));

        return new IntrebareGrila(nume, continut, materie, variante, raspunsuri, punctaj);
    }




    private static List<Test> getTeste(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null) return null;

        List<Test> teste = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Test test = getTestJSON(jsonArray.getJSONObject(i));
            if (test != null) teste.add(test);
        }

        return teste;
    }

    private static Test getTestJSON(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;

        String nume = jsonObject.getString(String.valueOf(R.string.nume));
        String descriere = jsonObject.getString(String.valueOf(R.string.descriere));
        String autor = jsonObject.getString(String.valueOf(R.string.autor));
        List<IntrebareGrila> intrebari = getIntrebari(jsonObject.getJSONArray(String.valueOf(R.string.intrebari)));
        int minute = jsonObject.getInt(String.valueOf(R.string.minute));
        Boolean estePublic = jsonObject.getBoolean(String.valueOf(R.string.estePublic));

        return new Test(nume,descriere,autor,intrebari,minute,estePublic);
    }


}
