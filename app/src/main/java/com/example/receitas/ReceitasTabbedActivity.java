package com.example.receitas;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.DAO.ReceitaWebDAO;
import com.example.receitas.PageAdapter.ReceitasPageAdapter;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.Model.Categoria;

public class ReceitasTabbedActivity extends AppCompatActivity {

    //Componentes de layout
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //Adapters
    private ReceitasPageAdapter adapter;

    //Model
    private Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas_tabbed);

        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey(Constantes.INTENT_CATEGORIA_MODEL)) {

            categoria = (Categoria) bundle.getSerializable(Constantes.INTENT_CATEGORIA_MODEL);

            //Set componentes layout
            toolbar = findViewById(R.id.activity_receitas_tabbed_toolbar);
            viewPager = findViewById(R.id.activity_receitas_tabbed_pager);
            tabLayout = findViewById(R.id.activity_receitas_tabbed_tab_layout);

            //Set toolbar
            toolbar.setTitle(categoria.getNome());
            setSupportActionBar(toolbar);

            //Set tablayout
            tabLayout.addTab(tabLayout.newTab().setText("Receitas"));
            tabLayout.addTab(tabLayout.newTab().setText("Receitas Web"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //Set adapter / viewpager
            adapter = new ReceitasPageAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount(), categoria);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });
        }
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