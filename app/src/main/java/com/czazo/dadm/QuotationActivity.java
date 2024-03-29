package com.czazo.dadm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.czazo.dadm.databases.AbstractRepository;
import com.czazo.dadm.models.Quotation;
import com.czazo.dadm.tasks.AsyncTaskHttp;

public class QuotationActivity extends AppCompatActivity {

    private Menu menu;
    private AbstractRepository abstractRepository;
    private Handler handler;
    private AsyncTaskHttp asyncTaskHttp;
    private boolean isAddVisible = false;
    private String KEY_AUTHOR = "AUTOR", KEY_QUOTE = "CITA", KEY_ADD = "ADDVISIBLE";

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
            isAddVisible = savedInstanceState.getBoolean(KEY_ADD);
        }
        abstractRepository = AbstractRepository.getInstance(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUOTE, ((TextView) findViewById(R.id.quotation_hint)).getText().toString());
        outState.putString(KEY_AUTHOR, ((TextView) findViewById(R.id.sample_author)).getText().toString());
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
                item.setVisible(false);
                isAddVisible = false;
                new Thread(new Runnable() {
                    TextView anotherHint = findViewById(R.id.quotation_hint);
                    TextView anotherAuthor = findViewById(R.id.sample_author);

                    String quoteText = anotherHint.getText().toString();
                    String quoteAuthor = anotherAuthor.getText().toString();

                    @Override
                    public void run() {
                        abstractRepository.idaoRepository().addQuote(new Quotation(quoteText, quoteAuthor));
                    }
                }).start();
                return true;
            case R.id.refresh_quotation:
                if (comprobarEstadoRed()) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    String metodo = prefs.getString("http", getString(R.string.settings_method_get));
                    String idioma = prefs.getString("idioma", getString(R.string.language_english));
                    asyncTaskHttp = new AsyncTaskHttp(this);
                    asyncTaskHttp.execute(idioma, metodo);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ocultarOpciones() {
        menu.findItem(R.id.refresh_quotation).setVisible(false);
        menu.findItem(R.id.add_quotation).setVisible(false);

    }

    public void modificarEtiquetas(Quotation quotation) {
        TextView hint = findViewById(R.id.quotation_hint);
        TextView author = findViewById(R.id.sample_author);

        hint.setText(quotation.getQuoteText());
        author.setText(quotation.getQuoteAuthor());

        menu.findItem(R.id.refresh_quotation).setVisible(true);
        handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                TextView hint = findViewById(R.id.quotation_hint);
                Quotation quote = abstractRepository.idaoRepository().findOneByQuote(hint.getText().toString());

                if (quote != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            menu.findItem(R.id.add_quotation).setVisible(false);
                            isAddVisible = false;
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            menu.findItem(R.id.add_quotation).setVisible(true);
                            isAddVisible = true;
                        }
                    });
                }
            }
        }).start();
    }

    public boolean comprobarEstadoRed() {
        // Get a reference to the ConnectivityManager
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // Get information about the default active data network
        NetworkInfo info = manager.getActiveNetworkInfo();
        // There will be connectivity when there is a default connected network
        return ((info != null) && (info.isConnected()));
    }

    public void onDestroy() {
        if (this.asyncTaskHttp != null){
            if (this.asyncTaskHttp.getStatus() == AsyncTaskHttp.Status.RUNNING) {
                this.asyncTaskHttp.cancel(true);
            }
        }
        super.onDestroy();
    }
}
