package com.example.pm.assistant.data;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {Contato.class, Cuidador.class, Usuario.class}, version=1)
public abstract class myDatabase extends RoomDatabase
{
    public abstract Dao dao();
}
