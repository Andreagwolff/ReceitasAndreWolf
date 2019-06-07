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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.receitas.AddEditReceitaActivity;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.DAO.ReceitaWebDAO;
import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Receita;
import com.example.receitas.Model.ReceitaWeb;
import com.example.receitas.ReceitasTabbedActivity;
import com.example.receitas.Util.Imagens;
import com.example.receitas.Util.MakeToast;
import com.example.receitas.ViewHolder.ReceitaViewHolder;
import com.example.receitas.ViewHolder.ReceitaWebViewHolder;
import com.example.receitas.R;
import com.example.receitas.WebActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReceitaWebAdapter extends RecyclerView.Adapter {

    private List<ReceitaWeb> receitasWeb;
    private Categoria categoria;
    private transient Context context;

    public ReceitaWebAdapter(List<ReceitaWeb> receitasWeb, Categoria categoria, Context context) {
        this.receitasWeb = receitasWeb;
        this.categoria = categoria;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.receita_web_item, parent, false);

        ReceitaWebViewHolder holder = new ReceitaWebViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        ReceitaWebViewHolder holder = (ReceitaWebViewHolder) viewHolder;

        final ReceitaWeb receitaWeb  = receitasWeb.get(position);

        holder.txtNome.setText(receitaWeb.getNome());
        holder.txtUrl.setText(receitaWeb.getUrl());

        holder.imgDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder((ReceitasTabbedActivity)(context));

                alertDialog.setTitle("Deletar esta receita??");
                alertDialog.setMessage("Você tem certeza que gostaria de deletar esta receita?");
                alertDialog.setIcon(R.drawable.ic_delete_black_24dp);

                alertDialog.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new ReceitaWebDAO(context).excluir(receitaWeb);
                                receitasWeb.remove(viewHolder.getAdapterPosition());
                                notifyDataSetChanged();

                                int quantidadeReceitasWeb = new ReceitaWebDAO(context).listarQuantidadeReceitasWeb(categoria.getId());
                                int quantidadeReceitas = new ReceitaDAO(context).listarQuantidadeReceitas(categoria.getId());

                                if(quantidadeReceitas == 0 && quantidadeReceitasWeb == 0){
                                    new CategoriaDAO(context).excluir(categoria.getId());
                                    ((ReceitasTabbedActivity)context).finish();
                                }
                            }
                        }
                );

                alertDialog.show();
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, WebActivity.class);

                Bundle b = new Bundle();
                b.putSerializable(Constantes.INTENT_RECEITA_WEB_MODEL, receitaWeb);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receitasWeb.size();
    }
}