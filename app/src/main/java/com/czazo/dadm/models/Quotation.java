package com.czazo.dadm.models;

public class Quotation {
    private String quoteText, quoteAuthor;

    public Quotation(String text, String author) {
        quoteText = text;
        quoteAuthor = author;
    }

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
}
