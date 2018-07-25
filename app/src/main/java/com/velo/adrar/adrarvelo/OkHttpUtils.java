package com.velo.adrar.adrarvelo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    static Response sendGetOkHttpRequest(String url) throws Exception {

        if (!isInternetConnexion(VeloApplication.getVeloApplication())) {
            throw new Exception("Vous n'êtes pas connecté à internet");
        }

        Log.w("tag", "url : " + url);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        //Création de la requete
        Request request = new Request.Builder().url(url).build();

        try {
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
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des données, bande passante insuffisante", e);
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

    // Méthode de vérification de la connexion à internet.
    public static boolean isInternetConnexion(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
