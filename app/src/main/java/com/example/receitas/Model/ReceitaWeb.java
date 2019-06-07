package com.example.receitas.Model;

import java.io.Serializable;

public class ReceitaWeb implements Serializable {

    private int id;
    private String nome, url;
    private int categoriaId;

    public ReceitaWeb() {
    }

    public ReceitaWeb(String nome, String url, int categoriaId) {
        this.nome = nome;
        this.url = url;
        this.categoriaId = categoriaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}