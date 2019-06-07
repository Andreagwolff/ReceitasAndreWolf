package com.example.receitas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.receitas.Helper.DBHelper;
import com.example.receitas.Model.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class IngredienteDAO {

    private com.example.receitas.Helper.DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable  ;
    private Context context;

    public IngredienteDAO(Context context){
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
        this.context = context;
    }

    public long inserir (Ingrediente ingrediente){
        ContentValues values = new ContentValues();
        values.put("nome", ingrediente.getNome());
        values.put("unidade", ingrediente.getUnidade());
        values.put("quantidade", ingrediente.getQuantidade());
        return databaseWritable.insert("ingredientes", null, values);
    }

    public List<Ingrediente> listarPorReceita(int idReceita){

        List<Ingrediente> ingredientes = new ArrayList<>();

        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "ingredientes.id, " +
                        "ingredientes.nome, " +
                        "ingredientes.unidade, " +
                        "ingredientes.quantidade " +
                        "FROM ingredientes " +
                        "INNER JOIN receitasIngredientes " +
                        "ON ingredientes.id = receitasIngredientes.idIngrediente " +
                        "WHERE receitasIngredientes.idReceita = " + String.valueOf(idReceita) + " " +
                        "ORDER BY ingredientes.nome"
                ,null,null);

        while (cursor.moveToNext()){
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setId(cursor.getInt(0));
            ingrediente.setNome(cursor.getString(1));
            ingrediente.setUnidade(cursor.getString(2));
            ingrediente.setQuantidade(cursor.getString(3));

            ingredientes.add(ingrediente);
        }

        return  ingredientes;
    }

    public void excluir(Ingrediente ingrediente){
        databaseWritable.delete(
                "ingredientes",
                "id = ?",
                new String[]{String.valueOf(ingrediente.getId())});
    }
}
