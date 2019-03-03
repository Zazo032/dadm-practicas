package com.czazo.dadm.databases;

import android.content.Context;

import com.czazo.dadm.models.Quotation;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Quotation.class}, version = 1, exportSchema = false)
public abstract class AbstractRepository extends RoomDatabase {

    private static AbstractRepository abstractRepository;

    public synchronized static AbstractRepository getInstance(Context context) {
        if (abstractRepository == null) {
            abstractRepository = Room
                    .databaseBuilder(context, AbstractRepository.class, "quotation_table")
                    .build();
        }
        return abstractRepository;
    }
    public abstract IDAORepository idaoRepository();
}
