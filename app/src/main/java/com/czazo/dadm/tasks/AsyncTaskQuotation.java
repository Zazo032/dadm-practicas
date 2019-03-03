package com.czazo.dadm.tasks;

import android.os.AsyncTask;

import com.czazo.dadm.FavouriteActivity;
import com.czazo.dadm.databases.AbstractRepository;
import com.czazo.dadm.databases.IDAORepository;
import com.czazo.dadm.models.Quotation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskQuotation extends AsyncTask<Boolean, Void, List<Quotation>> {
    private final WeakReference<FavouriteActivity> activity;
    public AbstractRepository repository;

    public AsyncTaskQuotation(FavouriteActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {
        List<Quotation> quotationList = new ArrayList<>();

        if (booleans[0].booleanValue()) {
            quotationList = repository.idaoRepository().listAll();
        }
        return  quotationList;
    }

    @Override
    protected void onPostExecute(List<Quotation> quotationList) {
        if (activity != null) {
            activity.get().editListAdapter(quotationList);
        }
    }
}
