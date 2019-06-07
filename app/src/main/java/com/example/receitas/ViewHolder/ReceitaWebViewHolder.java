package com.example.receitas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.receitas.Interface.ItemClickListener;
import com.example.receitas.R;

public class ReceitaWebViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener{

    public TextView txtNome, txtUrl;
    public ImageView imgDeletar;
    private ItemClickListener itemClickListener;

    public ReceitaWebViewHolder(View view) {
        super(view);

        txtNome = view.findViewById(R.id.receita_web_item_txtNome);
        txtUrl = view.findViewById(R.id.receita_web_item_txtUrl);
        imgDeletar = view.findViewById(R.id.receita_web_item_imgDeletar);

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
