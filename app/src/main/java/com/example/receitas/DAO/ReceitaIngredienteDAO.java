package com.example.receitas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.receitas.Helper.DBHelper;

public class ReceitaIngredienteDAO {
    private com.example.receitas.Helper.DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable;
    private Context context;

    public ReceitaIngredienteDAO(Context context) {
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
        this.context = context;
    }

    public long inserirIngredienteReceita (long idReceita, long idIngrediente){
        ContentValues values = new ContentValues();
        values.put("idReceita", idReceita);
        values.put("idIngrediente", idIngrediente);
        return databaseWritable.insert("receitasIngredientes", null, values);
    }

    public void excluirReceitaIngredientes(int idReceita){
        databaseWritable.delete(
                "receitasIngredientes",
                "idReceita = ?",
                new String[]{String.valueOf(idReceita)});
    }
}
