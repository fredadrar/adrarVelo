package com.velo.adrar.adrarvelo;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    public static Response sendGetOkHttpRequest(String url) throws Exception {
        Log.w("tag", "url : " + url);
        OkHttpClient client = new OkHttpClient();

        //Création de la requete
        Request request = new Request.Builder().url(url).build();

        //Execution de la requête
        Response response = client.newCall(request).execute();

        //Analyse du code retour
        if (response.code() != 200) {
            throw new Exception("Réponse du serveur incorrecte : " + response.code());
        } else {
            //Résultat de la requete.
            //ATTENTION .string() ne peut être appelée qu’une seule fois.
//            return response.body().string();
            return response;
        }
    }

    public static String sendPostOkHttpRequest(String url, String paramJson) throws Exception {
        Log.w("tag", "url : " + url);
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        //Corps de la requête
        RequestBody body = RequestBody.create(JSON, paramJson);

        //Création de la requete
        Request request = new Request.Builder().url(url).post(body).build();

        //Execution de la requête
        Response response = client.newCall(request).execute();

        //Analyse du code retour
        if (response.code() != 200) {
            throw new Exception("Réponse du serveur incorrecte : " + response.code());
        } else {
            //Résultat de la requete.
            return response.body().string();
        }
    }
}
