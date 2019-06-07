package com.example.receitas.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.receitas.Adapter.ReceitaAdapter;
import com.example.receitas.AddEditReceitaActivity;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Receita;
import com.example.receitas.R;
import com.example.receitas.Util.MakeToast;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.github.rubensousa.floatingtoolbar.FloatingToolbarMenuBuilder;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class ReceitasFragment extends Fragment {

    //Componentes de layout
    private MaterialEditText editPesquisa;
    private ImageView imgFecharPesquisa;
    private LinearLayout layoutPesquisa;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private FloatingToolbar floatingToolbar;

    //Adapter
    private List<Receita> receitas;
    private ReceitaAdapter receitaAdapter;

    private Categoria categoria;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.tab_fragment_receitas, container, false);

        fab = rootView.findViewById(R.id.tab_fragment_receitas_fab);
        editPesquisa = rootView.findViewById(R.id.tab_fragment_receitas_editPesquisa);
        imgFecharPesquisa = rootView.findViewById(R.id.tab_fragment_receitas_imgFecharPesquisa);
        layoutPesquisa = rootView.findViewById(R.id.tab_fragment_receitas_layoutPesquisa);
        floatingToolbar = rootView.findViewById(R.id.tab_fragment_receitas_floatingToolbar);
        recyclerView = rootView.findViewById(R.id.tab_fragment_receitas_recyclerView);

        layoutPesquisa.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        receitas = new ArrayList<>();

        floatingToolbar.setMenu(new FloatingToolbarMenuBuilder(container.getContext())
                .addItem(R.id.fab_receitas_menu_adicionar, R.drawable.ic_add_white_24dp, "Adicionar Receita")
                .addItem(R.id.fab_receitas_menu_filtrar, R.drawable.ic_filter_list_white_24dp, "Filtrar por Ingrediente")
                .build());

        floatingToolbar.attachFab(fab);

        floatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.fab_receitas_menu_adicionar) {
                    Intent intent = new Intent(container.getContext(), AddEditReceitaActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                    intent.putExtras(b);
                    intent.putExtra(Constantes.INTENT_ACTIVITY, container.getContext().getClass().getSimpleName());
                    startActivity(intent);
                    floatingToolbar.hide();
                }else if(id == R.id.fab_receitas_menu_filtrar){
                    layoutPesquisa.setVisibility(View.VISIBLE);
                    floatingToolbar.hide();
                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {}
        });

        imgFecharPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutPesquisa.setVisibility(View.GONE);
                floatingToolbar.show();
            }
        });

        floatingToolbar.attachRecyclerView(recyclerView);

        editPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    setAdapterPesquisar(editable.toString());
                }else{
                    setAdapter();
                }
            }
        });

        return rootView;
    }

    private void setAdapter() {

        if(receitas.size()>0) {
            receitas.clear();
            recyclerView.removeAllViews();
        }

        receitas = new ReceitaDAO(getContext()).listarPorCategoria(categoria.getId());
        receitaAdapter = new ReceitaAdapter(this.receitas, this.categoria, getContext());
        recyclerView.setAdapter(receitaAdapter);
    }

    private void setAdapterPesquisar(String nomeIngrediente) {
        receitas.clear();
        recyclerView.removeAllViews();

        receitas = new ReceitaDAO(getContext()).listarPorIngrediente(this.categoria.getId(), nomeIngrediente);

        receitaAdapter = new ReceitaAdapter(receitas, this.categoria, getContext());
        recyclerView.setAdapter(receitaAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        categoria = (Categoria) getArguments().getSerializable(Constantes.INTENT_CATEGORIA_MODEL);

        setAdapter();
    }
}