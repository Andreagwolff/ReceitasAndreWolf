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

import com.example.receitas.AddEditCategoriaActivity;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.HomeActivity;
import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.Model.Categoria;
import com.example.receitas.R;
import com.example.receitas.ReceitasTabbedActivity;
import com.example.receitas.Util.MakeToast;
import com.example.receitas.ViewHolder.CategoriaViewHolder;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter {

    private List<Categoria> categorias;
    private Context context;

    public CategoriaAdapter(List<Categoria> categorias, Context context) {
        this.categorias = categorias;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.categoria_item, parent, false);

        CategoriaViewHolder holder = new CategoriaViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        CategoriaViewHolder holder = (CategoriaViewHolder) viewHolder;

        final Categoria categoria  = categorias.get(position) ;

        holder.nome.setText(categoria.getNome());
        holder.descricao.setText(categoria.getDescricao());

        holder.imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditCategoriaActivity.class);
                Bundle b = new Bundle();
                b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

        holder.imgDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder((HomeActivity)(context));

                alertDialog.setTitle("Deletar esta categoria??");
                alertDialog.setMessage("Você tem certeza que gostaria de deletar esta categoria?");
                alertDialog.setIcon(R.drawable.ic_delete_black_24dp);

                alertDialog.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CategoriaDAO categoriaDAO = new CategoriaDAO(context);
                                categoriaDAO.excluir(categoria.getId());
                                categorias.remove(viewHolder.getAdapterPosition());
                                notifyDataSetChanged();
                                MakeToast.success("Categoria deletada com sucesso!", context);
                            }
                        }
                );

                alertDialog.show();
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context,ReceitasTabbedActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }
}