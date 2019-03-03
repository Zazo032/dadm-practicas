package com.czazo.dadm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.czazo.dadm.databases.AbstractRepository;
import com.czazo.dadm.databases.IDAORepository;
import com.czazo.dadm.databases.Repository;
import com.czazo.dadm.models.Quotation;

public class DashboardActivity extends AppCompatActivity {
    AbstractRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        repository = AbstractRepository.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Comprobacion creacion base de datos.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean firstRun = prefs.getBoolean("first_run", true);
        if(firstRun) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    repository.idaoRepository().listAll();
                }
            }).start();
            prefs.edit().putBoolean("first_run", false);
        }

    }

    protected void dashboardLauncherManager(View v) {
        switch (v.getId()) {
            case R.id.btn_get_quotations:
                Intent intentGet = new Intent(this, QuotationActivity.class);
                startActivity(intentGet);
                break;
            case R.id.btn_favourite_quotations:
                Intent intentFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentFavourite);
                break;
            case R.id.btn_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.btn_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
    }
}
