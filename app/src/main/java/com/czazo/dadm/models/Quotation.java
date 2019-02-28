package com.czazo.dadm.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity( tableName = "quotation_table", indices = {@Index(value = {"quote"}, unique = true)})
public class Quotation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "quote")
    @NonNull
    private String quoteText;

    @ColumnInfo(name = "author")
    private String quoteAuthor;


    public Quotation(String text, String author) {
        quoteText = text;
        quoteAuthor = author;
    }

    public Quotation() {}

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
