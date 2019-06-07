package com.example.receitas.PageAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.receitas.AddEditReceitaActivity;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Receita;
import com.example.receitas.R;
import com.example.receitas.Util.Imagens;

import java.util.List;

public class FavoritoPageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Receita> receitas;

    public FavoritoPageAdapter(Context context, List<Receita> receitas) {
        this.context = context;
        this.receitas = receitas;
    }

    @Override
    public int getCount() {
        return receitas.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.favorito_layout, null);

        ImageView imgReceita = view.findViewById(R.id.favorito_layout_imgReceita);
        TextView txtNome = view.findViewById(R.id.favorito_layout_txtNome);

        imgReceita.setImageBitmap(Imagens.converterParaBitmap(receitas.get(position).getFoto()));
        txtNome.setText(receitas.get(position).getNome());

        imgReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categoria categoria = new CategoriaDAO(context).listarPorId(receitas.get(position).getIdCategoria());

                Intent intent = new Intent(context,AddEditReceitaActivity.class);
                Bundle b = new Bundle();
                b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                b.putSerializable(Constantes.INTENT_RECEITA_MODEL, receitas.get(position));
                intent.putExtra(Constantes.INTENT_ACTIVITY, context.getClass().getSimpleName());
                intent.putExtras(b);
                context.startActivity(intent);

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
