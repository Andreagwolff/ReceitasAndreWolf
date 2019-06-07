package com.example.receitas.Model;

import java.io.Serializable;
import java.util.List;

public class Receita implements Serializable {

    private int id;
    private int idCategoria;
    private String nome, tempoPreparo, rendimento, modoPreparo;
    private byte[] foto;
    private List<Ingrediente> ingredientes;
    private int favorito;

    public Receita(){}

    public Receita(int idCategoria, String nome, String tempoPreparo, String rendimento, String modoPreparo, byte[] foto, List<Ingrediente> ingredientes, int favorito) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.tempoPreparo = tempoPreparo;
        this.rendimento = rendimento;
        this.modoPreparo = modoPreparo;
        this.foto = foto;
        this.ingredientes = ingredientes;
        this.favorito = favorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getRendimento() {
        return rendimento;
    }

    public void setRendimento(String rendimento) {
        this.rendimento = rendimento;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }
}
