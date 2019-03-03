package com.czazo.dadm.tasks;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.czazo.dadm.QuotationActivity;
import com.czazo.dadm.models.Quotation;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskHttp extends AsyncTask<String, Void, Quotation> {
    private final WeakReference<QuotationActivity> activity;


    public AsyncTaskHttp(QuotationActivity activity) {
        this.activity = new WeakReference<>(activity);
    }


    @Override
    protected Quotation doInBackground(String... strings) {
        String endpoint = "";
        HttpURLConnection connection;
        Quotation quotation = new Quotation();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendPath("");
        // As being a GET request, include the parameters on the URI
        if (strings[1].equals("GET")) {
            builder.appendQueryParameter("method", "getQuote");
            builder.appendQueryParameter("format", "json");
            if (strings[0].equals("Inglés") || strings[0].equals("English")) {
                builder.appendQueryParameter("lang", "en");
            } else {
                builder.appendQueryParameter("lang", "ru");
            }
        } else {
            endpoint = "method=getQuote&format=json&lang=";
            if (strings[0].equals("Inglés") || strings[0].equals("English")) {
                endpoint += "en";
            } else {
                endpoint += "ru";
            }
        }


        try {
            if (strings[1].equals("GET")) {
                URL url = new URL(builder.build().toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
            } else {
                URL url = new URL(builder.build().toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(endpoint);
                writer.flush();
                writer.close();
            }

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Gson gson = new Gson();
                quotation = gson.fromJson(reader, Quotation.class);
                reader.close();
            }

            // Get response
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotation;
    }

    @Override
    protected void onPreExecute() {
        this.activity.get().ocultarOpciones();
    }

    @Override
    protected void onPostExecute(Quotation quotation) {
        this.activity.get().modificarEtiquetas(quotation);
    }
 }
