package com.example.receitas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.receitas.Helper.DBHelper;
import com.example.receitas.Model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable  ;

    public CategoriaDAO(Context context){
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
    }

    public long inserir (Categoria categoria){
        ContentValues values = new ContentValues();
        values.put("nome", categoria.getNome());
        values.put("descricao", categoria.getDescricao());
        return databaseWritable.insert("categorias", null, values);
    }

    public List<Categoria> listarTudo(){
        List<Categoria> categorias = new ArrayList<>();
        Cursor cursor = databaseReadable.query("categorias",
                new String[]{
                        "id",
                        "nome",
                        "descricao"
                },
                null, null, null, null, "nome", null);

        while (cursor.moveToNext()){
            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNome(cursor.getString(1));
            categoria.setDescricao(cursor.getString(2));
            categorias.add(categoria);
        }

        return categorias;
    }

    public Categoria listarPorId(int id){
        Categoria categoria = new Categoria();
        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "id, " +
                        "nome, " +
                        "descricao " +
                        "FROM " + "categorias " +
                        "WHERE id = " + String.valueOf(id)
                ,null,null);

        while (cursor.moveToNext()){
            categoria.setId(cursor.getInt(0));
            categoria.setNome(cursor.getString(1));
            categoria.setDescricao(cursor.getString(2));
        }

        return categoria;
    }

    public void excluir(int id){
        databaseWritable.delete(
                "categorias",
                "id = ?",
                new String[]{String.valueOf(id)});
    }

    public void atualizar(Categoria categoria){
        ContentValues values = new ContentValues();
        values.put("nome", categoria.getNome());
        values.put("descricao", categoria.getDescricao());
        databaseWritable.update(
                "categorias",
                values,
                "id = ?",
                new String[]{String.valueOf(categoria.getId())});
    }
}
