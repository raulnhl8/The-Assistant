package com.example.pm.assistant.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int usuario_id;
    private String usuario_nome;
    private boolean usuario_sexo;
    private String usuario_dataDeNascimento;
    private boolean dicaAtiv;
    private String usuario_endereco;
    private String faceSetToken;

    public Usuario(String usuario_nome, boolean usuario_sexo, String usuario_dataDeNascimento, boolean dicaAtiv, String usuario_endereco, String faceSetToken) {
        this.usuario_nome = usuario_nome;
        this.usuario_sexo = usuario_sexo;
        this.usuario_dataDeNascimento = usuario_dataDeNascimento;
        this.dicaAtiv = dicaAtiv;
        this.usuario_endereco = usuario_endereco;
        this.faceSetToken = faceSetToken;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public boolean isUsuario_sexo() {
        return usuario_sexo;
    }

    public void setUsuario_sexo(boolean usuario_sexo) {
        this.usuario_sexo = usuario_sexo;
    }

    public String getUsuario_dataDeNascimento() {
        return usuario_dataDeNascimento;
    }

    public void setUsuario_dataDeNascimento(String usuario_dataDeNascimento) {
        this.usuario_dataDeNascimento = usuario_dataDeNascimento;
    }

    public boolean isDicaAtiv() {
        return dicaAtiv;
    }

    public void setDicaAtiv(boolean dicaAtiv) {
        this.dicaAtiv = dicaAtiv;
    }

    public String getUsuario_endereco() {
        return usuario_endereco;
    }

    public void setUsuario_endereco(String usuario_endereco) {
        this.usuario_endereco = usuario_endereco;
    }

    public String getFaceSetToken() {
        return faceSetToken;
    }

    public void setFaceSetToken(String faceSetToken) {
        this.faceSetToken = faceSetToken;
    }
}
