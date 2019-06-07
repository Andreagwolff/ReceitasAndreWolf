package com.example.receitas.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.receitas.AddEditReceitaActivity;
import com.example.receitas.DAO.IngredienteDAO;
import com.example.receitas.Model.Ingrediente;
import com.example.receitas.R;
import com.example.receitas.ViewHolder.IngredienteViewHolder;

import java.util.List;

public class IngredienteAdapter extends RecyclerView.Adapter<IngredienteViewHolder> {

    private Context context;
    private List<Ingrediente> listaIngredientes;

    public IngredienteAdapter(Context context, List<Ingrediente> listaIngredientes) {
        this.context = context;
        this.listaIngredientes = listaIngredientes;
    }

    @NonNull
    @Override
    public IngredienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingrediente_item, parent, false);
        return new IngredienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredienteViewHolder holder, final int position) {

        holder.editNome.setText(listaIngredientes.get(position).getNome());
        holder.editUnidade.setText(listaIngredientes.get(position).getUnidade());
        holder.editQuantidade.setText(listaIngredientes.get(position).getQuantidade());

        holder.editNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                listaIngredientes.get(position).setNome(holder.editNome.getText().toString());
            }
        });

        holder.editUnidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                listaIngredientes.get(position).setUnidade(holder.editUnidade.getText().toString());
            }
        });

        holder.editQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                listaIngredientes.get(position).setQuantidade(holder.editQuantidade.getText().toString());
            }
        });

        holder.btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder((AddEditReceitaActivity)(context));

                alertDialog.setTitle("Deletar este ingrediente?");
                alertDialog.setMessage("Você tem certeza que gostaria de deletar este ingrediente?");
                alertDialog.setIcon(R.drawable.ic_delete_black_24dp);

                alertDialog.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listaIngredientes.remove(position);
                                notifyDataSetChanged();
                            }
                        }
                );

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaIngredientes.size();
    }

}