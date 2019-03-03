package com.czazo.dadm.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.czazo.dadm.models.Quotation;

import java.util.ArrayList;
import java.util.List;

public class Repository extends SQLiteOpenHelper {

    private static Repository ourInstance;

    private Repository(Context context) {
        super(context, "quotation_database", null, 1);
    }

    private Repository(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public synchronized static Repository getInstance(Context context) {

        if (ourInstance == null) {
            ourInstance = new Repository(context, "contacts_database", null, 1);
        }
        return ourInstance;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE quotation_table (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quote TEXT NOT NULL, author TEXT, UNIQUE(quote));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Quotation> getAllQuotation() {
        List<Quotation> result = new ArrayList<>();
        Quotation quote;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("quotation_table", new String[]{"quote", "author"},
                null, null, null, null, "quote", null);
        while (cursor.moveToNext()) {
            // Create a HashMap<String,String> object for the given entry in the database
            quote = new Quotation(
                    cursor.getString(0),
                    cursor.getString(1));
            // Add the object to the result list
            result.add(quote);
        }
        // Close the cursor and database
        cursor.close();
        database.close();
        return result;
    }

    public Boolean checkQuote(Quotation quotationToCheck) {
        Quotation quote;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("quotation_table", null,
                "quote=?", new String[]{quotationToCheck.getQuoteText()}, null,
                null, null, null);
        cursor.close();
        database.close();
        return cursor.getCount() > 0;
    }

    public void addQuote(String quoteText, String quoteAuthor) {
        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        // Insert the new contact into the table (autoincremental id)
        ContentValues values = new ContentValues();
        values.put("quote", quoteText);
        values.put("author", quoteAuthor);
        database.insert("quotation_table", null, values);
        // Close the database
        database.close();
    }

    public void clearAll() {
        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        database.delete("quotation_table", null, null);
        // Close the database
        database.close();
    }

    public void deleteQuotation(Quotation quotation) {
        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        database.delete("quotation_table", "quote=?", new
                String[]{quotation.getQuoteText()});
        database.close();
    }
}
