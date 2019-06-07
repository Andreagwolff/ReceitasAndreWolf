package com.example.receitas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.receitas.Helper.DBHelper;
import com.example.receitas.Model.Ingrediente;
import com.example.receitas.Model.Receita;

import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO {

    private DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable  ;
    private Context context;

    public ReceitaDAO(Context context){
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
        this.context = context;
    }

    public long inserir (Receita receita){
        ContentValues values = new ContentValues();
        values.put("nome", receita.getNome());
        values.put("tempoPreparo", receita.getTempoPreparo());
        values.put("rendimento", receita.getRendimento());
        values.put("modoPreparo", receita.getModoPreparo());
        values.put("foto", receita.getFoto());
        values.put("categoriaId", receita.getIdCategoria());
        values.put("favorito", receita.getFavorito());

        long idReceita = databaseWritable.insert("receitas", null, values);

        for(int i=0; i< receita.getIngredientes().size(); i++){
            long idIngrediente =  new IngredienteDAO(context).inserir(receita.getIngredientes().get(i));
            new ReceitaIngredienteDAO(context).inserirIngredienteReceita(idReceita,idIngrediente);
        }

        return idReceita;
    }

    public List<Receita> listarPorCategoria(int categoriaId){
        List<Receita> receitas = new ArrayList<>();
        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "receitas.id, " +
                        "receitas.nome, " +
                        "receitas.tempoPreparo, " +
                        "receitas.rendimento, " +
                        "receitas.modoPreparo, " +
                        "receitas.foto, " +
                        "receitas.categoriaId, " +
                        "receitas.favorito " +

                        "FROM " + "receitas " +
                        "INNER JOIN categorias " +
                        "ON categorias.id = receitas.categoriaId " +
                        "WHERE receitas.categoriaId = " + String.valueOf(categoriaId) + " " +
                        "ORDER BY receitas.nome"
                ,null,null);

        IngredienteDAO ingredienteDAO = new IngredienteDAO(context);

        while (cursor.moveToNext()){
            Receita receita = new Receita();
            List<Ingrediente> ingredientes = new ArrayList<>();
            receita.setId(cursor.getInt(0));
            receita.setNome(cursor.getString(1));
            receita.setTempoPreparo(cursor.getString(2));
            receita.setRendimento(cursor.getString(3));
            receita.setModoPreparo(cursor.getString(4));
            receita.setFoto(cursor.getBlob(5));
            receita.setIdCategoria(cursor.getInt(6));
            receita.setFavorito(cursor.getInt(7));

            ingredientes = ingredienteDAO.listarPorReceita(receita.getId());
            receita.setIngredientes(ingredientes);

            receitas.add(receita);
        }

        return receitas;
    }

    public List<Receita> listarPorIngrediente(int categoriaId, String ingrediente){
        List<Receita> receitas = new ArrayList<>();
        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "receitas.id, " +
                        "receitas.nome, " +
                        "receitas.tempoPreparo, " +
                        "receitas.rendimento, " +
                        "receitas.modoPreparo, " +
                        "receitas.foto, " +
                        "receitas.categoriaId, " +
                        "receitas.favorito " +
                        "FROM receitas " +
                        "INNER JOIN categorias " +
                        "ON receitas.categoriaId = categorias.id " +
                        "INNER JOIN receitasIngredientes " +
                        "ON receitas.id = receitasIngredientes.idReceita " +
                        "INNER JOIN ingredientes " +
                        "ON receitasIngredientes.idIngrediente = ingredientes.id " +
                        "WHERE ingredientes.nome LIKE '%"+ingrediente+"%' AND receitas.categoriaId = "+ String.valueOf(categoriaId) + " " +
                        "GROUP BY receitas.nome " +
                        "ORDER BY receitas.nome"
                ,null,null);

        IngredienteDAO ingredienteDAO = new IngredienteDAO(context);

        while (cursor.moveToNext()){
            Receita receita = new Receita();
            List<Ingrediente> ingredientes = new ArrayList<>();
            receita.setId(cursor.getInt(0));
            receita.setNome(cursor.getString(1));
            receita.setTempoPreparo(cursor.getString(2));
            receita.setRendimento(cursor.getString(3));
            receita.setModoPreparo(cursor.getString(4));
            receita.setFoto(cursor.getBlob(5));
            receita.setIdCategoria(cursor.getInt(6));
            receita.setFavorito(cursor.getInt(7));

            ingredientes = ingredienteDAO.listarPorReceita(receita.getId());
            receita.setIngredientes(ingredientes);

            receitas.add(receita);
        }

        return receitas;
    }

    public List<Receita> listarPorNome(String nome){
        List<Receita> receitas = new ArrayList<>();
        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "receitas.id, " +
                        "receitas.nome, " +
                        "receitas.tempoPreparo, " +
                        "receitas.rendimento, " +
                        "receitas.modoPreparo, " +
                        "receitas.foto, " +
                        "receitas.categoriaId, " +
                        "receitas.favorito " +
                        "FROM " + "receitas " +
                        "INNER JOIN categorias " +
                        "ON categorias.id = receitas.categoriaId " +
                        "WHERE receitas.nome LIKE '%" + nome + "%' " +
                        "ORDER BY receitas.nome"
                ,null,null);

        IngredienteDAO ingredienteDAO = new IngredienteDAO(context);

        while (cursor.moveToNext()){
            Receita receita = new Receita();
            List<Ingrediente> ingredientes = new ArrayList<>();
            receita.setId(cursor.getInt(0));
            receita.setNome(cursor.getString(1));
            receita.setTempoPreparo(cursor.getString(2));
            receita.setRendimento(cursor.getString(3));
            receita.setModoPreparo(cursor.getString(4));
            receita.setFoto(cursor.getBlob(5));
            receita.setIdCategoria(cursor.getInt(6));
            receita.setFavorito(cursor.getInt(7));

            ingredientes = ingredienteDAO.listarPorReceita(receita.getId());
            receita.setIngredientes(ingredientes);

            receitas.add(receita);
        }

        return receitas;
    }

    public List<Receita> listarFavoritos(){
        List<Receita> receitas = new ArrayList<>();
        Cursor cursor = databaseReadable.rawQuery(
                "SELECT " +
                        "receitas.id, " +
                        "receitas.nome, " +
                        "receitas.tempoPreparo, " +
                        "receitas.rendimento, " +
                        "receitas.modoPreparo, " +
                        "receitas.foto, " +
                        "receitas.categoriaId, " +
                        "receitas.favorito " +

                        "FROM " + "receitas " +
                        "INNER JOIN categorias " +
                        "ON categorias.id = receitas.categoriaId " +
                        "WHERE receitas.favorito = 1 " +
                        "ORDER BY receitas.nome"
                ,null,null);

        IngredienteDAO ingredienteDAO = new IngredienteDAO(context);

        while (cursor.moveToNext()){
            Receita receita = new Receita();
            List<Ingrediente> ingredientes = new ArrayList<>();
            receita.setId(cursor.getInt(0));
            receita.setNome(cursor.getString(1));
            receita.setTempoPreparo(cursor.getString(2));
            receita.setRendimento(cursor.getString(3));
            receita.setModoPreparo(cursor.getString(4));
            receita.setFoto(cursor.getBlob(5));
            receita.setIdCategoria(cursor.getInt(6));
            receita.setFavorito(cursor.getInt(7));

            ingredientes = ingredienteDAO.listarPorReceita(receita.getId());
            receita.setIngredientes(ingredientes);

            receitas.add(receita);
        }

        return receitas;
    }

    public int listarQuantidadeReceitas(int categoriaId){

        int qtdReceitas = 0;

        Cursor cursor = databaseReadable.rawQuery(
                "SELECT count(" + "receitas.id) as qtdReceitas " +
                        "FROM " + "receitas " +
                        "INNER JOIN categorias " +
                        "ON categorias.id = receitas.categoriaId " +
                        "WHERE receitas.categoriaId = " + categoriaId
                ,null,null);

        while (cursor.moveToNext()){
            qtdReceitas = cursor.getInt(0);
        }

        return qtdReceitas;
    }

    public void excluir(Receita receita){

        new ReceitaIngredienteDAO(context).excluirReceitaIngredientes(receita.getId());

        for(int i=0; i< receita.getIngredientes().size(); i++){
            new IngredienteDAO(context).excluir(receita.getIngredientes().get(i));
        }
        
        databaseWritable.delete(
                "receitas",
                "id = ?",
                new String[]{String.valueOf(receita.getId())});
    }

    public void atualizar(Receita receita){
        ContentValues values = new ContentValues();
        values.put("nome", receita.getNome());
        values.put("tempoPreparo", receita.getTempoPreparo());
        values.put("rendimento", receita.getRendimento());
        values.put("modoPreparo", receita.getModoPreparo());
        values.put("foto", receita.getFoto());
        values.put("categoriaId", receita.getIdCategoria());
        values.put("favorito", receita.getFavorito());
        databaseWritable.update("receitas",
                values,
                "id = ?",
                new String[]{String.valueOf(receita.getId())});

        IngredienteDAO ingredienteDAO = new IngredienteDAO(context);
        List<Ingrediente> listaIngredientesAntiga = ingredienteDAO.listarPorReceita(receita.getId());

        new ReceitaIngredienteDAO(context).excluirReceitaIngredientes(receita.getId());

        for(int i=0; i< listaIngredientesAntiga.size(); i++){
            ingredienteDAO.excluir(listaIngredientesAntiga.get(i));
        }

        for(int i=0; i< receita.getIngredientes().size(); i++){
            long idIngrediente = ingredienteDAO.inserir(receita.getIngredientes().get(i));
            new ReceitaIngredienteDAO(context).inserirIngredienteReceita(receita.getId(),idIngrediente);
        }
    }
}

