package com.example.pm.assistant.data;

import android.arch.persistence.room.Insert;

@android.arch.persistence.room.Dao
public interface Dao
{

    @Insert
    public void addContato(Contato contato);
    @Insert
    public void addCuidador(Cuidador cuidador);
    @Insert
    public void addUsuario (Usuario usuario);
}
