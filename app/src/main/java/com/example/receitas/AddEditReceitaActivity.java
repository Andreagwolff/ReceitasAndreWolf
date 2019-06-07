package com.example.receitas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.receitas.Adapter.IngredienteAdapter;
import com.example.receitas.Commom.Constantes;
import com.example.receitas.Commom.RegCons;
import com.example.receitas.DAO.CategoriaDAO;
import com.example.receitas.DAO.ReceitaDAO;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.Ingrediente;
import com.example.receitas.Model.Receita;
import com.example.receitas.Util.Imagens;
import com.example.receitas.Util.MakeToast;
import com.github.angads25.toggle.LabeledSwitch;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddEditReceitaActivity extends AppCompatActivity {

    //Varáveis de Layout
    private MaterialEditText editNome, editRendimento, editModoPreparo, editTempoPreparo;
    private LabeledSwitch switchFavorito;
    private ImageView imgReceita;
    private Button btnConfirmar, btnSelecionarImagem, btnAdicionarIngrediente;
    private RecyclerView recyclerViewIngredientes;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;

    //Variáveis de Layout do AlertDialog
    private Button btnConfirmarDialog, btnCancelarDialog;
    private MaterialEditText editNomeIngrediente, editUnidadeIngrediente, editQuantidadeIngrediente;

    //Variáveis de ingredientes
    private List<Ingrediente> listaIngredientes;
    private IngredienteAdapter listaIngredientesAdapter;

    //Model
    private Receita receita;
    private Categoria categoria;

    //Adicionar - atualizar
    private boolean isAdd;

    private Bitmap bmpReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receita);

        //Inicialização de componentes de Layout
        toolbar = findViewById(R.id.activity_add_edit_receita_toolbar);
        editNome = findViewById(R.id.activity_add_edit_receita_editNome);
        editRendimento = findViewById(R.id.activity_add_edit_receita_editRendimento);
        editModoPreparo = findViewById(R.id.activity_add_edit_receita_editModoPreparo);
        editTempoPreparo = findViewById(R.id.activity_add_edit_receita_editTempoPreparo);
        switchFavorito = findViewById(R.id.activity_add_edit_receita_switchFavorito);
        imgReceita = findViewById(R.id.activity_add_edit_receita_imgReceita);
        btnSelecionarImagem = findViewById(R.id.activity_add_edit_receita_btnSelecionarImagem);
        btnAdicionarIngrediente = findViewById(R.id.activity_add_edit_receita_btnAdicionarIngrediente);
        btnConfirmar = findViewById(R.id.activity_add_edit_receita_btnConfirmar);
        recyclerViewIngredientes = findViewById(R.id.activity_add_edit_receita_recyclerViewIngredientes);

        //Set toolbar
        toolbar.setTitle("  Adicionar/Editar Receita");
        setSupportActionBar(toolbar);

        //Set Recyclerview Ingredientes
        recyclerViewIngredientes.setHasFixedSize(true);
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewIngredientes.setLayoutManager(linearLayoutManager);

        //Inicializa ArrayList Ingredientes
        listaIngredientes = new ArrayList<>();

        //Set Click Listeners
        btnSelecionarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarImagem();
            }
        });

        btnAdicionarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdicionarIngrediente();
            }
        });

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

        //Get Intent
        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey(Constantes.INTENT_RECEITA_MODEL)) {
            receita = (Receita) bundle.getSerializable(Constantes.INTENT_RECEITA_MODEL);

            editNome.setText(receita.getNome());
            editModoPreparo.setText(receita.getModoPreparo());
            editRendimento.setText(receita.getRendimento());
            editTempoPreparo.setText(receita.getTempoPreparo());

            if(receita.getFoto() == null){
                Picasso.with(AddEditReceitaActivity.this).load(R.drawable.blank).into(imgReceita);
            }else{
                bmpReceita = Imagens.converterParaBitmap(receita.getFoto());
                imgReceita.setImageBitmap(Imagens.converterParaBitmap(receita.getFoto()));
            }

            if(receita.getFavorito() == 1){
                switchFavorito.setOn(true);
            }else{
                switchFavorito.setOn(false);
            }

            if(receita.getIngredientes() != null) {
                listaIngredientes = receita.getIngredientes();
                listaIngredientesAdapter = new IngredienteAdapter(AddEditReceitaActivity.this, listaIngredientes);
                recyclerViewIngredientes.setAdapter(listaIngredientesAdapter);
            }

            isAdd = false;
        }else{
            isAdd = true;
        }

        if(bundle.containsKey(Constantes.INTENT_CATEGORIA_MODEL)) {
            categoria = (Categoria) bundle.getSerializable(Constantes.INTENT_CATEGORIA_MODEL);
        }else{
            categoria = new CategoriaDAO(AddEditReceitaActivity.this).listarPorId(this.receita.getIdCategoria());
        }
    }

    private void showDialogAdicionarIngrediente() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddEditReceitaActivity.this);

        //Set cabeçalho
        alertDialog.setTitle("Adicionar Ingrediente");
        alertDialog.setMessage("Inclua um ingrediente na receita:");

        View v = LayoutInflater.from(AddEditReceitaActivity.this).inflate(R.layout.adicionar_ingredientes_layout, null);

        //Inicializa variáveis de Layout
        btnConfirmarDialog = v.findViewById(R.id.adicionar_ingredientes_layout_btnConfirmar);
        btnCancelarDialog = v.findViewById(R.id.adicionar_ingredientes_layout_btnCancelar);
        editNomeIngrediente = v.findViewById(R.id.adicionar_ingredientes_layout_editNome);
        editUnidadeIngrediente = v.findViewById(R.id.adicionar_ingredientes_layout_editUnidade);
        editQuantidadeIngrediente = v.findViewById(R.id.adicionar_ingredientes_layout_editQuantidade);

        alertDialog.setView(v);
        alertDialog.setIcon(R.drawable.ic_update_black_24dp);

        final AlertDialog dialog = alertDialog.show();

        //Click Listeners
        btnConfirmarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (!editNomeIngrediente.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
                    isValid = false;
                }

                if (!editUnidadeIngrediente.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
                    isValid = false;
                }

                if (!editQuantidadeIngrediente.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
                    isValid = false;
                }

                if(isValid){

                    Ingrediente ingrediente = new Ingrediente(
                            editNomeIngrediente.getText().toString(),
                            editUnidadeIngrediente.getText().toString(),
                            editQuantidadeIngrediente.getText().toString()
                    );
                    listaIngredientes.add(ingrediente);

                    listaIngredientesAdapter = new IngredienteAdapter(AddEditReceitaActivity.this, listaIngredientes);
                    recyclerViewIngredientes.setAdapter(listaIngredientesAdapter);

                    dialog.dismiss();

                }else {
                    MakeToast.error("Por favor, preencha todos os campos corretamente!", AddEditReceitaActivity.this);
                }
            }
        });

        btnCancelarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void selecionarImagem() {
        //Abre um 'File Chooser' exibindo somente imagens
        Intent intent = new Intent();
        intent.setType("image/*");

        //Retorna para o metodo onActivityResult
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecione a Imagem"), Constantes.PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Se a requisição foi bem sucedida
        if(requestCode == Constantes.PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null){

            Uri uriImagem = data.getData();

            bmpReceita = Imagens.descodificarUri(AddEditReceitaActivity.this, uriImagem, 400);
            imgReceita.setImageBitmap(bmpReceita);
        }
    }

    private void atualizar() {
        this.receita.setNome(editNome.getText().toString());
        this.receita.setModoPreparo(editModoPreparo.getText().toString());
        this.receita.setRendimento(editRendimento.getText().toString());
        this.receita.setTempoPreparo(editTempoPreparo.getText().toString());

        if(bmpReceita != null){
            this.receita.setFoto(Imagens.converterBmpParaByte(bmpReceita));
        }else{
            this.receita.setFoto(null);
        }

        if (switchFavorito.isOn()){
            this.receita.setFavorito(1);
        }else{
            this.receita.setFavorito(0);
        }

        if(listaIngredientes != null) {
            receita.setIngredientes(listaIngredientes);
        }

        ReceitaDAO receitaDAO = new ReceitaDAO(AddEditReceitaActivity.this);
        receitaDAO.atualizar(this.receita);

        MakeToast.success("Receita atualizada com sucesso!", AddEditReceitaActivity.this);

        finish();
    }

    private void adicionar() {

        boolean isValid = true;

        //Valida formulário
        if (!editNome.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editTempoPreparo.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editRendimento.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editModoPreparo.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if(listaIngredientes.size()<1){
            MakeToast.error("A receita precisa ter ao menos um ingrediente!", AddEditReceitaActivity.this);
            isValid = false;
        }

        if(isValid) {

            int isFavorito = 0;

            if (switchFavorito.isOn()) {
                isFavorito = 1;
            } else {
                isFavorito = 0;
            }

            this.receita = new Receita(
                    categoria.getId(),
                    editNome.getText().toString(),
                    editTempoPreparo.getText().toString(),
                    editRendimento.getText().toString(),
                    editModoPreparo.getText().toString(),
                    null,
                    listaIngredientes,
                    isFavorito
            );

            if (bmpReceita != null) {
                this.receita.setFoto(Imagens.converterBmpParaByte(bmpReceita));
            }

            ReceitaDAO receitaDAO = new ReceitaDAO(AddEditReceitaActivity.this);

            long id = receitaDAO.inserir(this.receita);

            if (id >= 0) {
                MakeToast.success("Receita adicionada com sucesso!" + id, AddEditReceitaActivity.this);
            } else {
                MakeToast.error("Receita não pôde ser adicionada!" + id, AddEditReceitaActivity.this);
            }

            finish();

        }else{
            MakeToast.error("Por favor, preencha todos os campos corretamente!", AddEditReceitaActivity.this);
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