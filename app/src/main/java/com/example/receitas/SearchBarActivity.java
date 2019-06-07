package com.example.receitas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import com.example.receitas.Adapter.SearchAdapter;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.Model.Receita;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchBarActivity extends AppCompatActivity {

    //Componentes de layout
    private MaterialEditText editPesquisa;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    //Adapters
    private List<Receita> receitas;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        //Set componentes de layout
        toolbar = findViewById(R.id.activity_search_bar_toolbar);
        editPesquisa = findViewById(R.id.activity_search_bar_editPesquisa);
        recyclerView = findViewById(R.id.activity_search_bar_recyclerView);

        //Toolbar
        toolbar.setTitle("  Pesquisar");
        toolbar.setLogo(R.drawable.ic_search_white_24dp);
        setSupportActionBar(toolbar);

        receitas = new ArrayList<>();

        //Set recyclerview
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        editPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    setAdapter(editable.toString());
                }else{
                    receitas.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(String nomeReceita) {
        receitas.clear();
        recyclerView.removeAllViews();

        ReceitaDAO receitaDAO = new ReceitaDAO(SearchBarActivity.this);
        receitas = receitaDAO.listarPorNome(nomeReceita);

        searchAdapter = new SearchAdapter(receitas, SearchBarActivity.this);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.default_options_menu_voltar){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
