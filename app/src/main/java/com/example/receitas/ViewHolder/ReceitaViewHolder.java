package com.example.receitas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.R;

public class ReceitaViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener{

    public TextView txtNome, txtTempoPreparo, txtRendimento;
    public ImageView imgReceita, imgDeletar, imgCompartilhar;
    private ItemClickListener itemClickListener;

    public ReceitaViewHolder(View view) {
        super(view);

        txtNome = view.findViewById(R.id.receita_item_txtNome);
        txtTempoPreparo = view.findViewById(R.id.receita_item_txtTempoPreparo);
        txtRendimento = view.findViewById(R.id.receita_item_txtRendimento);
        imgDeletar = view.findViewById(R.id.receita_item_imgDeletar);
        imgReceita = view.findViewById(R.id.receita_item_imgReceita);
        imgCompartilhar = view.findViewById(R.id.receita_item_imgCompartilhar);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v){
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
