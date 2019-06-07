package com.example.receitas.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.receitas.AddEditReceitaActivity;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Receita;
import com.example.receitas.R;
import com.example.receitas.ReceitasTabbedActivity;
import com.example.receitas.SearchBarActivity;
import com.example.receitas.Util.Imagens;
import com.example.receitas.ViewHolder.ReceitaViewHolder;
import com.example.receitas.ViewHolder.SearchViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter {

    private List<Receita> receitas;
    private transient Context context;

    public SearchAdapter(List<Receita> receitas, Context context) {
        this.receitas = receitas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_item, parent, false);

        SearchViewHolder holder = new SearchViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        SearchViewHolder holder = (SearchViewHolder) viewHolder;

        final Receita receita  = receitas.get(position);

        holder.txtNome.setText(receita.getNome());
        holder.txtModoPreparo.setText(receita.getModoPreparo());

        if(receita.getFoto() != null){
            holder.imgReceita.setImageBitmap(Imagens.converterParaBitmap(receita.getFoto()));
        }else{
            Picasso.with(context).load(R.drawable.blank).into(holder.imgReceita);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    Categoria categoria = new CategoriaDAO(context).listarPorId(receita.getIdCategoria());
                    Intent intent = new Intent(context,AddEditReceitaActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(Constantes.INTENT_RECEITA_MODEL, receita);
                    b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                    intent.putExtra(Constantes.INTENT_ACTIVITY, context.getClass().getSimpleName());
                    intent.putExtras(b);
                    context.startActivity(intent);
                    ((SearchBarActivity)context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }
}