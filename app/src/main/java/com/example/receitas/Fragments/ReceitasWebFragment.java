package com.example.receitas.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.receitas.Adapter.ReceitaWebAdapter;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.Commom.RegCons;
import com.example.receitas.DAO.ReceitaWebDAO;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.ReceitaWeb;
import com.example.receitas.R;
import com.example.receitas.Util.MakeToast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.ArrayList;
import java.util.List;

public class ReceitasWebFragment extends Fragment {

    //Componentes de layout
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    //Adapter
    private List<ReceitaWeb> receitasWeb;
    private ReceitaWebAdapter receitaWebAdapter;

    private Categoria categoria;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.tab_fragment_receitas_web, container, false);

        fab = rootView.findViewById(R.id.tab_fragment_receitas_web_fabAdicionar);
        recyclerView = rootView.findViewById(R.id.tab_fragment_receitas_web_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        receitasWeb = new ArrayList<>();

        //Fab Adicionar Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext());
                alertDialog.setTitle("Adicionar Receita da Web");
                alertDialog.setMessage("Preencha os campos abaixo:");

                View v = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.adicionar_receita_web_layout, null);

                final MaterialEditText editNome = v.findViewById(R.id.adicionar_receita_web_layout_editNome);
                final MaterialEditText editUrl = v.findViewById(R.id.adicionar_receita_web_layout_editUrl);
                Button btnConfirmar = v.findViewById(R.id.adicionar_receita_web_layout_btnConfirmar);

                alertDialog.setView(v);
                alertDialog.setIcon(R.drawable.ic_add_black_24dp);

                final AlertDialog dialog = alertDialog.show();

                btnConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isValid = true;

                        if (!editNome.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
                            isValid = false;
                        }

                        if (!editUrl.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
                            isValid = false;
                        }

                        if(isValid) {

                            ReceitaWeb receitaWeb = new ReceitaWeb(
                                    editNome.getText().toString(),
                                    editUrl.getText().toString(),
                                    categoria.getId());
                            if (new ReceitaWebDAO(getContext()).inserir(receitaWeb) >= 1) {
                                MakeToast.success("Receita adicionada com sucesso", getContext());
                            } else {
                                MakeToast.error("A receita não pode ser adicionada neste momento!", getContext());
                            }
                            setAdapter();
                            dialog.dismiss();

                        }else {
                        MakeToast.error("Por favor, preencha todos os campos corretamente!", getContext());
                        }
                    }
                });
            }
        });

        return rootView;
    }

    private void setAdapter(){

        if(receitasWeb.size()>0) {
            receitasWeb.clear();
            recyclerView.removeAllViews();
        }

        this.receitasWeb = new ReceitaWebDAO(getContext()).listarPorCategoria(categoria.getId());

        receitaWebAdapter = new ReceitaWebAdapter(this.receitasWeb, this.categoria, getContext());
        recyclerView.setAdapter(receitaWebAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.categoria = (Categoria) getArguments().getSerializable(Constantes.INTENT_CATEGORIA_MODEL);

        this.setAdapter();
    }
}