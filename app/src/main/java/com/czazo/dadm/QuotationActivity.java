package com.czazo.dadm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.czazo.dadm.databases.AbstractRepository;
import com.czazo.dadm.databases.Repository;

public class QuotationActivity extends AppCompatActivity {

    private int contadorCitasRecibidas = 0;
    private AbstractRepository abstractRepository;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        final TextView hint = findViewById(R.id.quotation_hint);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = pref.getString("username", "Nameless One");
        hint.setText(String.format(getString(R.string.quotation_hint), name));
        abstractRepository = AbstractRepository.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quotation, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_quotation:
                // TODO: Añadir cita a favoritos (Práctica 4)
                TextView anotherHint = findViewById(R.id.quotation_hint);
                TextView anotherAuthor = findViewById(R.id.sample_author);

                String quoteText = anotherHint.getText().toString();
                String quoteAuthor = anotherAuthor.getText().toString();

                Repository.getInstance(this).addQuote(quoteText, quoteAuthor);
                return true;
            case R.id.refresh_quotation:
                // TODO: Obtener nueva cita (Práctica 3A)
                final TextView hint = findViewById(R.id.quotation_hint);
                contadorCitasRecibidas++;
                menu.findItem(R.id.add_quotation).setVisible(true);
                hint.setText(String.format(getString(R.string.quotation_sample_text), contadorCitasRecibidas));
                TextView author = findViewById(R.id.sample_author);
                author.setText(String.format(getString(R.string.quotation_sample_author), contadorCitasRecibidas));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
