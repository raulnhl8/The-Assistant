package com.example.pm.assistant.data;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao
{
    @Insert
    public void addContato(Contato contato);
    @Insert
    public void addCuidador(Cuidador cuidador);
    @Insert
    public void addUsuario (Usuario usuario);

    @Query("SELECT * from Contato")
    public List<Contato> getAllContatos();
    @Query("SELECT * from Cuidador")
    public Cuidador getCuidador();
    @Query("SELECT * from Usuario")
    public Usuario getUsuario();
    @Query("SELECT * from Contato where contato_id = :id LIMIT 1")
    public Contato getContato(int id);

    @Query("SELECT * FROM Contato WHERE contato_facetoken LIKE :faceToken")
    public Contato getContatoByFaceToken(String faceToken);

    @Delete
    public void deleteContato(Contato... contato);
    @Delete
    public void deleteCuidador(Cuidador... cuidador);
    @Delete
    public void deleteUsuario(Usuario... usuario);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateContato(Contato contato);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updadeCuidador(Cuidador cuidador);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateUsuario(Usuario usuario);
}
