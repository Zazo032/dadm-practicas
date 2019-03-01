package com.czazo.dadm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.czazo.dadm.databases.AbstractRepository;
import com.czazo.dadm.databases.Repository;

public class QuotationActivity extends AppCompatActivity {

    private int contadorCitasRecibidas = 0;
    private AbstractRepository abstractRepository;
    private Menu menu;
    private boolean isAddVisible = false;
    final String KEY_QUOTE = "QUOTE", KEY_AUTHOR = "AUTHOR", KEY_COUNT = "COUNT", KEY_ADD = "ADD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        if (savedInstanceState == null) {
            final TextView hint = findViewById(R.id.quotation_hint);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            String name = pref.getString("username", "Nameless One");
            hint.setText(String.format(getString(R.string.quotation_hint), name));
        } else {
            ((TextView) findViewById(R.id.quotation_hint)).setText(savedInstanceState.getString(KEY_QUOTE));
            ((TextView) findViewById(R.id.sample_author)).setText(savedInstanceState.getString(KEY_AUTHOR));
            contadorCitasRecibidas = savedInstanceState.getInt(KEY_COUNT);
            isAddVisible = savedInstanceState.getBoolean(KEY_ADD);
        }
        abstractRepository = AbstractRepository.getInstance(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUOTE, ((TextView) findViewById(R.id.quotation_hint)).getText().toString());
        outState.putString(KEY_AUTHOR, ((TextView) findViewById(R.id.sample_author)).getText().toString());
        outState.putInt(KEY_COUNT, contadorCitasRecibidas);
        outState.putBoolean(KEY_ADD, isAddVisible);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quotation, menu);
        this.menu = menu;
        menu.findItem(R.id.add_quotation).setVisible(isAddVisible);
        return super.onCreateOptionsMenu(menu);
    }

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

                item.setVisible(false);
                isAddVisible = false;
                return true;
            case R.id.refresh_quotation:
                // TODO: Obtener nueva cita (Práctica 3A)
                TextView hint = findViewById(R.id.quotation_hint);
                TextView author = findViewById(R.id.sample_author);
                menu.findItem(R.id.add_quotation).setVisible(true);
                isAddVisible = true;
                hint.setText(String.format(getString(R.string.quotation_sample_text), contadorCitasRecibidas));
                author.setText(String.format(getString(R.string.quotation_sample_author), contadorCitasRecibidas));
                contadorCitasRecibidas++;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
