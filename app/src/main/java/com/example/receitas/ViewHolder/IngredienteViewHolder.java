package com.example.receitas.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.receitas.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class IngredienteViewHolder extends RecyclerView.ViewHolder{

    public MaterialEditText editNome, editUnidade, editQuantidade;

    public ImageButton btnRemover;

    public IngredienteViewHolder(View itemView) {
        super(itemView);

        editNome = (MaterialEditText) itemView.findViewById(R.id.ingrediente_item_editNome);
        editUnidade = (MaterialEditText) itemView.findViewById(R.id.ingrediente_item_editUnidade);
        editQuantidade = (MaterialEditText) itemView.findViewById(R.id.ingrediente_item_editQuantidade);
        btnRemover = (ImageButton) itemView.findViewById(R.id.ingrediente_item_btnRemover);
    }

}