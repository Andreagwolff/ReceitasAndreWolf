package com.example.receitas.PageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.receitas.Commom.Constantes;
import com.example.receitas.Fragments.ReceitasFragment;
import com.example.receitas.Fragments.ReceitasWebFragment;
import com.example.receitas.Model.Categoria;

public class ReceitasPageAdapter extends FragmentStatePagerAdapter {

    private int numeroTabs;
    private Categoria categoria;

    public ReceitasPageAdapter(FragmentManager fm, int numeroTabs, Categoria categoria) {
        super(fm);
        this.numeroTabs = numeroTabs;
        this.categoria = categoria;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);

        switch (position) {
            case 0:
                ReceitasFragment receitasFragment = new ReceitasFragment();
                receitasFragment.setArguments(bundle);
                return receitasFragment;
            case 1:
                ReceitasWebFragment receitasWebFragment = new ReceitasWebFragment();
                receitasWebFragment.setArguments(bundle);
                return receitasWebFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numeroTabs;
    }
}
