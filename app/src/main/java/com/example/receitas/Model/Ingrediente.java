package com.example.receitas.Model;

import java.io.Serializable;

public class Ingrediente implements Serializable {

    private int id;
    private String nome, unidade, quantidade;

    public Ingrediente() {}

    public Ingrediente(int id, String nome, String unidade, String quantidade) {
        this.id = id;
        this.nome = nome;
        this.unidade = unidade;
        this.quantidade = quantidade;
    }

    public Ingrediente(String nome, String unidade, String quantidade) {
        this.nome = nome;
        this.unidade = unidade;
        this.quantidade = quantidade;
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
