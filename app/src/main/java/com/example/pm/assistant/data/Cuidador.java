package com.example.pm.assistant.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys  = @ForeignKey(entity = Contato.class,
                                        parentColumns = "cuidador_id",
                                        childColumns = "contato_id",
                                        onDelete = CASCADE))
public class Cuidador
{
    @PrimaryKey
    private int cuidador_id;
    private String cuidador_email;
    private String cuidador_senha;
    private String cuidador_celular;
    private String cuidador_endereco;

    private int contato_id;

    public Cuidador(int cuidador_id, String cuidador_email, String cuidador_senha, String cuidador_celular, String cuidador_endereco, int contato_id) {
        this.cuidador_id = cuidador_id;
        this.cuidador_email = cuidador_email;
        this.cuidador_senha = cuidador_senha;
        this.cuidador_celular = cuidador_celular;
        this.cuidador_endereco = cuidador_endereco;
        this.contato_id = contato_id;
    }

    public int getCuidador_id() {
        return cuidador_id;
    }

    public void setCuidador_id(int cuidador_id) {
        this.cuidador_id = cuidador_id;
    }

    public String getCuidador_email() {
        return cuidador_email;
    }

    public void setCuidador_email(String cuidador_email) {
        this.cuidador_email = cuidador_email;
    }

    public String getCuidador_senha() {
        return cuidador_senha;
    }

    public void setCuidador_senha(String cuidador_senha) {
        this.cuidador_senha = cuidador_senha;
    }

    public String getCuidador_celular() {
        return cuidador_celular;
    }

    public void setCuidador_celular(String cuidador_celular) {
        this.cuidador_celular = cuidador_celular;
    }

    public String getCuidador_endereco() {
        return cuidador_endereco;
    }

    public void setCuidador_endereco(String cuidador_endereco) {
        this.cuidador_endereco = cuidador_endereco;
    }

    public int getContato_id() {
        return contato_id;
    }

    public void setContato_id(int contato_id) {
        this.contato_id = contato_id;
    }
}
