package com.czazo.dadm.databases;

import com.czazo.dadm.models.Quotation;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface IDAORepository {

    @Delete
    public void deleteQuotation(Quotation quotation);

    @Insert
    public void addQuote(Quotation quotation);

    @Query("SELECT * FROM quotation_table")
    public List<Quotation> listAll();

    @Query("SELECT * FROM quotation_table WHERE quote =:quote")
    public Quotation findOneByQuote(String quote);

    @Query("DELETE FROM quotation_table")
    public void clearAll();
}
