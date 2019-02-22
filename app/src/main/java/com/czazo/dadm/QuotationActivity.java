package com.czazo.dadm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        final TextView hint = findViewById(R.id.quotation_hint);
        String name = "Nameless One";
        hint.setText(String.format(getString(R.string.quotation_hint), name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quotation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_quotation:
                // TODO: Añadir cita a favoritos (Práctica 4)
                return true;
            case R.id.refresh_quotation:
                // TODO: Obtener nueva cita (Práctica 3A)
                final TextView hint = findViewById(R.id.quotation_hint);
                hint.setText(getString(R.string.quotation_sample_text));
                TextView author = findViewById(R.id.sample_author);
                author.setText(getString(R.string.quotation_sample_author));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
