package com.czazo.dadm.databases;

import com.czazo.dadm.models.Quotation;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface IDAORepository {

    @Delete
    void deleteQuotation(Quotation quotation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuote(Quotation quotation);

    @Query("SELECT * FROM quotation_table")
    List<Quotation> listAll();

    @Query("SELECT * FROM quotation_table WHERE quote =:quote")
    Quotation findOneByQuote(String quote);

    @Query("DELETE FROM quotation_table")
    void clearAll();
}
