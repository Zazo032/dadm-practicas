package com.czazo.dadm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.czazo.dadm.R;
import com.czazo.dadm.models.Quotation;

import java.util.List;

public class FavouriteListAdapter extends ArrayAdapter {

    private int id;
    private List<Quotation> data;

    public FavouriteListAdapter(Context ctx, int id, List<Quotation> data) {
        super(ctx, id, data);
        this.id = id;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.quotation_list_row, null);
            ViewHolder vh = new ViewHolder(view);
            vh.text = view.findViewById(R.id.quotation_text);
            vh.author = view.findViewById(R.id.quotation_author);
            view.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) view.getTag();
        Quotation q = (Quotation) getItem(position);
        vh.text.setText(q.getQuoteText());
        if (q.getQuoteAuthor() == null || q.getQuoteAuthor().equals(""))
            vh.author.setText(getContext().getString(R.string.author_anonymous));
        else
            vh.author.setText(q.getQuoteAuthor());
        return view;
    }

    private class ViewHolder {

        TextView text, author;

        ViewHolder(final View itemView) {
            text = itemView.findViewById(R.id.quotation_text);
            author = itemView.findViewById(R.id.quotation_author);
        }
    }
}
