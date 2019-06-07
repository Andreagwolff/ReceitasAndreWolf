package com.example.receitas.Util;

import com.example.receitas.Model.Ingrediente;
import com.example.receitas.Model.Receita;

public class Textos {
    public static String toStringDadosReceita(Receita receita){

        String ingredientes = "";

        for (Ingrediente ingrediente : receita.getIngredientes()){
            ingredientes = ingredientes.concat(ingrediente.getQuantidade() + " (" + ingrediente.getUnidade() + " ) de " + ingrediente.getNome() + ", ");
        }

        String texto = "Modo de Preparo: " + receita.getModoPreparo() + "." +
                "\nIngredientes: " + ingredientes +
                "\nTempo de Preparo: " + receita.getTempoPreparo() +
                ". Rendimento: " + receita.getRendimento() + ".";

        return texto;
    }
}
