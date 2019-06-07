package com.example.receitas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.receitas.Commom.Constantes;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Util.MakeToast;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddEditCategoriaActivity extends AppCompatActivity {

    //Varáveis de Layout
    private MaterialEditText editNome, editDescricao;
    private Button btnConfirmar;
    private Toolbar toolbar;

    //Adicionar - Atualizar
    private boolean isAdd;

    private Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_categoria);

        //Inicialização de componentes de Layout
        toolbar = findViewById(R.id.activity_add_edit_categoria_toolbar);
        editNome = findViewById(R.id.activity_add_edit_categoria_editNome);
        editDescricao = findViewById(R.id.activity_add_edit_categoria_editDescricao);
        btnConfirmar = findViewById(R.id.activity_add_edit_categoria_btnConfirmar);

        //Set toolbar
        toolbar.setTitle("  Adicionar/Editar Categoria");
        setSupportActionBar(toolbar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdd) {
                    adicionar();
                }else{
                    atualizar();
                }
            }
        });

        if(getIntent().getExtras() != null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            categoria = (Categoria) bundle.getSerializable(Constantes.INTENT_CATEGORIA_MODEL);

            editNome.setText(categoria.getNome());
            editDescricao.setText(categoria.getDescricao());

            isAdd = false;
        }else{
            isAdd = true;
        }
    }

    private void atualizar() {
        this.categoria.setNome(editNome.getText().toString());
        this.categoria.setDescricao(editDescricao.getText().toString());

        CategoriaDAO categoriaDAO = new CategoriaDAO(AddEditCategoriaActivity.this);
        categoriaDAO.atualizar(this.categoria);

        finish();
    }

    private void adicionar() {

        boolean isValid = true;

        if (editNome.getText().toString().length()<3) {
            isValid = false;
        }

        if(isValid){

            this.categoria = new Categoria(editNome.getText().toString(), editDescricao.getText().toString());
            final CategoriaDAO categoriaDAO = new CategoriaDAO(AddEditCategoriaActivity.this);

            final int id = (int) categoriaDAO.inserir(this.categoria);

            if (id >= 0){
                this.categoria.setId(id);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddEditCategoriaActivity.this);

                alertDialog.setTitle("Adicionar Receita");
                alertDialog.setMessage("Você precisa adicionar uma receita para que esta categoria esteja visível. Deseja fazer isto?");
                alertDialog.setIcon(R.drawable.ic_add_black_24dp);
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AddEditCategoriaActivity.this,ReceitasTabbedActivity.class);
                                Bundle b = new Bundle();
                                b.putSerializable(Constantes.INTENT_CATEGORIA_MODEL, categoria);
                                intent.putExtras(b);
                                startActivity(intent);
                                finish();
                            }
                        }
                );

                alertDialog.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                categoriaDAO.excluir(id);
                                finish();
                            }
                        }
                );

                alertDialog.show();
            }else{
                MakeToast.warning("Categoria não pôde ser adicionada!", AddEditCategoriaActivity.this);
            }

        }else {
            MakeToast.error("Por favor, preencha todos os campos corretamente!", AddEditCategoriaActivity.this);
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
