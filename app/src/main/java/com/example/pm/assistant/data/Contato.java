package com.example.pm.assistant.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Contato
{
    @PrimaryKey(autoGenerate = true)
    private int contato_id;
    private String contato_nome;
    private String contato_relacionamento;
    private String contato_foto;
    private String contato_facetoken;

    public Contato(String contato_nome, String contato_relacionamento, String contato_foto, String contato_facetoken) {
        this.contato_nome = contato_nome;
        this.contato_relacionamento = contato_relacionamento;
        this.contato_foto = contato_foto;
        this.contato_facetoken = contato_facetoken;
    }

    public int getContato_id() {
        return contato_id;
    }

    public void setContato_id(int contato_id) {
        this.contato_id = contato_id;
    }

    public String getContato_nome() {
        return contato_nome;
    }

    public void setContato_nome(String contato_nome) {
        this.contato_nome = contato_nome;
    }

    public String getContato_relacionamento() {
        return contato_relacionamento;
    }

    public void setContato_relacionamento(String contato_relacionamento) {
        this.contato_relacionamento = contato_relacionamento;
    }

    public String getContato_foto() {
        return contato_foto;
    }

    public void setContato_foto(String contato_foto) {
        this.contato_foto = contato_foto;
    }

    public String getContato_facetoken() {
        return contato_facetoken;
    }

    public void setContato_facetoken(String contato_facetoken) {
        this.contato_facetoken = contato_facetoken;
    }
}
