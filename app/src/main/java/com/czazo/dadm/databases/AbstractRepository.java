package com.czazo.dadm.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Repository.class}, version = 1)
public abstract class AbstractRepository extends RoomDatabase {

    private static AbstractRepository abstractRepository;

    public synchronized static AbstractRepository getInstance(Context context) {
        if (abstractRepository == null) {
            abstractRepository = Room
                    .databaseBuilder(context, AbstractRepository.class, "quotation_table")
                    .allowMainThreadQueries()
                    .build();
        }
        return abstractRepository;
    }
    public abstract IDAORepository idaoRepository();
}