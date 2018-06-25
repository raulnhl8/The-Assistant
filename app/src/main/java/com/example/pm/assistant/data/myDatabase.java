package com.example.pm.assistant.data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@android.arch.persistence.room.Database(entities = {Contato.class, Cuidador.class, Usuario.class}, version=1, exportSchema = false)
public abstract class myDatabase extends RoomDatabase
{
    private static myDatabase sInstance;
    private static final String LOG_TAG = myDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "myDatabase";

    public static myDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "criando nova instancia de DB");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        myDatabase.class, myDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract Dao dao();
}
