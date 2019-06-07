package com.example.receitas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.R;

public class SearchViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener{

    public TextView txtNome, txtModoPreparo;
    public ImageView imgReceita;
    private ItemClickListener itemClickListener;

    public SearchViewHolder(View view) {
        super(view);

        txtNome = view.findViewById(R.id.search_item_txtNome);
        txtModoPreparo = view.findViewById(R.id.search_item_txtModoPreparo);
        imgReceita = view.findViewById(R.id.search_item_imgReceita);

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
