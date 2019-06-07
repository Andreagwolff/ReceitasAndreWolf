package com.example.receitas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.receitas.Helper.DBHelper;
import com.example.receitas.Model.Ingrediente;
import com.example.receitas.Model.Receita;
import com.example.receitas.Model.ReceitaWeb;

import java.util.ArrayList;
import java.util.List;

public class ReceitaWebDAO {
    private com.example.receitas.Helper.DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable  ;
    private Context context;

    public ReceitaWebDAO(Context context){
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
        this.context = context;
    }

    public long inserir (ReceitaWeb receitaWeb){
        ContentValues values = new ContentValues();
        values.put("nome", receitaWeb.getNome());
        values.put("url", receitaWeb.getUrl());
        values.put("categoriaId", receitaWeb.getCategoriaId());

        long idReceita = databaseWritable.insert("receitasWeb", null, values);

        return idReceita;
    }

    public List<ReceitaWeb> listarPorCategoria(int categoriaId){
        List<ReceitaWeb> receitasWeb = new ArrayList<>();
        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "receitasWeb.id, " +
                        "receitasWeb.nome, " +
                        "receitasWeb.url, " +
                        "receitasWeb.categoriaId " +
                        "FROM " + "receitasWeb " +
                        "INNER JOIN categorias " +
                        "ON categorias.id = receitasWeb.categoriaId " +
                        "WHERE receitasWeb.categoriaId = " + String.valueOf(categoriaId) + " " +
                        "ORDER BY receitasWeb.nome"
                ,null,null);

        while (cursor.moveToNext()){
            ReceitaWeb receitaWeb = new ReceitaWeb();
            receitaWeb.setId(cursor.getInt(0));
            receitaWeb.setNome(cursor.getString(1));
            receitaWeb.setUrl(cursor.getString(2));
            receitaWeb.setCategoriaId(cursor.getInt(3));

            receitasWeb.add(receitaWeb);
        }

        return receitasWeb;
    }

    public int listarQuantidadeReceitasWeb(int categoriaId){

        int qtdReceitas = 0;

        Cursor cursor = databaseReadable.rawQuery(
                "SELECT count(" + "receitasWeb.id) as qtdReceitas " +
                        "FROM " + "receitasWeb " +
                        "INNER JOIN categorias " +
                        "ON categorias.id = receitasWeb.categoriaId " +
                        "WHERE receitasWeb.categoriaId = " + categoriaId
                ,null,null);

        while (cursor.moveToNext()){
            qtdReceitas = cursor.getInt(0);
        }

        return qtdReceitas;
    }

    public void excluir(ReceitaWeb receitaWeb){

        databaseWritable.delete(
                "receitasWeb",
                "id = ?",
                new String[]{String.valueOf(receitaWeb.getId())});
    }
}

