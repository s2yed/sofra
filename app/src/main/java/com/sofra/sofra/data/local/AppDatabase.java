package com.sofra.sofra.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sofra.sofra.data.model.Item;

@Database(entities = Item.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static synchronized AppDatabase getAppDatabase(Context context){
        if (appDatabase==null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "dataSofra3")
                    .allowMainThreadQueries()
                    .build();
        }
     return appDatabase;
    }


    public abstract ItemDAO getItemDAO();

}
