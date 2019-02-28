package com.czazo.dadm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.czazo.dadm.adapters.FavouriteListAdapter;
import com.czazo.dadm.databases.AbstractRepository;
import com.czazo.dadm.databases.Repository;
import com.czazo.dadm.models.Quotation;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private List<Quotation> mockList;
    private FavouriteListAdapter fla;
    private AbstractRepository abstractRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Repository repository = Repository.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mockList = repository.getAllQuotation();
        fla = new FavouriteListAdapter(this, R.id.favourite_list, mockList);
        ListView list = findViewById(R.id.favourite_list);
        list.setAdapter(fla);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String authorName = mockList.get(position).getQuoteAuthor();
                if (authorName == null || authorName.equals(""))
                    Toast.makeText(parent.getContext(), getString(R.string.author_error), Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorName));
                    startActivity(i);
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FavouriteActivity.this);
                alertDialog.setMessage(R.string.favourite_dialog_delete);
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.deleteQuotation(mockList.get(position));
                        fla.notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton(R.string.no, null);
                alertDialog.create().show();
                return true;
            }
        });
    }

    ArrayList<Quotation> getMockQuotations() {
        ArrayList<Quotation> array = new ArrayList<>();
        array.add(new Quotation("Quote Text 1", null));
        array.add(new Quotation("Quote Text 2", ""));
        array.add(new Quotation("Quote Text 3", "Author 3"));
        array.add(new Quotation("Quote Text 4", "Author 4"));
        array.add(new Quotation("Quote Text 5", "Author 5"));
        array.add(new Quotation("Quote Text 6", "Author 6"));
        array.add(new Quotation("Quote Text 7", "Author 7"));
        array.add(new Quotation("Quote Text 8", "Author 8"));
        array.add(new Quotation("Quote Text 9", "Author 9"));
        array.add(new Quotation("Quote Text 10", "Author 10"));
        return array;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_favourite, menu);
        if (mockList.size() == 0) {
            menu.getItem(0).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final Repository repositorio = Repository.getInstance(this);
        switch (item.getItemId()) {
            case R.id.delete_quotations:
                // TODO: Obtener nueva cita (Práctica 3A)
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FavouriteActivity.this);
                alertDialog.setMessage(R.string.favourite_dialog_clear_all);
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repositorio.clearAll();
                        fla.notifyDataSetChanged();
                        item.setVisible(false);
                    }
                });
                alertDialog.setNegativeButton(R.string.no, null);
                alertDialog.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
