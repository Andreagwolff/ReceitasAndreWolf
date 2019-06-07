package com.example.receitas;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.receitas.Adapter.CategoriaAdapter;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.DAO.ReceitaWebDAO;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Receita;
import com.example.receitas.PageAdapter.FavoritoPageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    //Componentes de Layout
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ViewPager viewPager;

    //Lists
    private List<Receita> receitas;
    private List<Categoria> categorias;

    //Adapter
    private CategoriaAdapter categoriaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Inicialização componentes de layout
        toolbar = findViewById(R.id.activity_home_toolbar);
        fab = findViewById(R.id.activity_home_fab);
        recyclerView = findViewById(R.id.activity_home_recyclerView);
        viewPager = findViewById(R.id.activity_home_viewPager);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        receitas = new ArrayList<>();
        categorias = new ArrayList<>();

        //Toolbar
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //Fab Adicionar Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddEditCategoriaActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.home_menu_search){
            Intent i = new Intent(HomeActivity.this, SearchBarActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.removeAllViews();

        verifcaCategoriasSemReceitas();

        //Listar todas as categorias
        categorias = new CategoriaDAO(HomeActivity.this).listarTudo();

        //Set recyclerView - adapter
        categoriaAdapter = new CategoriaAdapter(categorias, this);
        recyclerView.setAdapter(categoriaAdapter);

        receitas.clear();
        receitas = new ReceitaDAO(HomeActivity.this).listarFavoritos();

        if(receitas.size()>0){
            viewPager.setVisibility(View.VISIBLE);
            FavoritoPageAdapter favoritoPageAdapter = new FavoritoPageAdapter(HomeActivity.this, receitas);
            viewPager.setAdapter(favoritoPageAdapter);
        }else{
            viewPager.setVisibility(View.GONE);
        }

    }

    private void verifcaCategoriasSemReceitas(){

        //Listar todas as categorias
        categorias = new CategoriaDAO(HomeActivity.this).listarTudo();

        for(Categoria categoria: categorias) {

            int quantidadeReceitasWeb = new ReceitaWebDAO(HomeActivity.this).listarQuantidadeReceitasWeb(categoria.getId());
            int quantidadeReceitas = new ReceitaDAO(HomeActivity.this).listarQuantidadeReceitas(categoria.getId());

            if (quantidadeReceitas == 0 && quantidadeReceitasWeb == 0) {
                new CategoriaDAO(HomeActivity.this).excluir(categoria.getId());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
