package com.example.receitas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.R;

public class CategoriaViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener{

    public TextView nome, descricao;
    public ImageView imgEditar, imgDeletar;
    private ItemClickListener itemClickListener;

    public CategoriaViewHolder(View view) {
        super(view);

        nome = view.findViewById(R.id.categoria_item_txtNome);
        descricao = view.findViewById(R.id.categoria_item_txtDescricao);
        imgDeletar = view.findViewById(R.id.categoria_item_imgDeletar);
        imgEditar = view.findViewById(R.id.categoria_item_imgEditar);

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
