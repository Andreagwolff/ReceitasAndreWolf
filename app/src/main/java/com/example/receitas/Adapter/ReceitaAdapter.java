package com.example.receitas.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.receitas.AddEditReceitaActivity;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.DAO.ReceitaWebDAO;
import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Receita;
import com.example.receitas.R;
import com.example.receitas.ReceitasTabbedActivity;
import com.example.receitas.Util.Imagens;
import com.example.receitas.Util.MakeToast;
import com.example.receitas.Util.Textos;
import com.example.receitas.ViewHolder.ReceitaViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReceitaAdapter extends RecyclerView.Adapter {

    private List<Receita> receitas;
    private Categoria categoria;
    private transient Context context;

    public ReceitaAdapter(List<Receita> receitas, Categoria categoria, Context context) {
        this.receitas = receitas;
        this.categoria = categoria;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.receita_item, parent, false);

        ReceitaViewHolder holder = new ReceitaViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        ReceitaViewHolder holder = (ReceitaViewHolder) viewHolder;

        final Receita receita  = receitas.get(position);

        holder.txtNome.setText(receita.getNome());
        holder.txtRendimento.setText(receita.getRendimento());
        holder.txtTempoPreparo.setText(receita.getTempoPreparo());

        if(receita.getFoto() != null){
            holder.imgReceita.setImageBitmap(Imagens.converterParaBitmap(receita.getFoto()));
        }else{
            Picasso.with(context).load(R.drawable.blank).into(holder.imgReceita);
        }

        holder.imgCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dadosReceita = Textos.toStringDadosReceita(receita);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, Imagens.getUriCompartilhamento(Imagens.converterParaBitmap(receita.getFoto()),receita.getNome(), dadosReceita, context));
                context.startActivity(Intent.createChooser(i, "Compartilhe! :)"));
            }
        });

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
                                new ReceitaDAO(context).excluir(receita);
                                receitas.remove(viewHolder.getAdapterPosition());
                                notifyDataSetChanged();

                                int quantidadeReceitasWeb = new ReceitaWebDAO(context).listarQuantidadeReceitasWeb(categoria.getId());
                                int quantidadeReceitas = new ReceitaDAO(context).listarQuantidadeReceitas(categoria.getId());

                                if(quantidadeReceitas == 0 && quantidadeReceitasWeb == 0){
                                    new CategoriaDAO(context).excluir(categoria.getId());
                                    ((ReceitasTabbedActivity)context).finish();
                                }

                                MakeToast.success("Receita deletada com sucesso!", context);
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
                    Intent intent = new Intent(context,AddEditReceitaActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(Constantes.INTENT_RECEITA_MODEL, receita);
                    b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                    intent.putExtra(Constantes.INTENT_ACTIVITY, context.getClass().getSimpleName());
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }
}