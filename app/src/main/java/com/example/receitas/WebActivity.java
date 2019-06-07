package com.example.receitas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.receitas.Commom.Constantes;
import com.example.receitas.Model.Categoria;
import com.example.receitas.Model.ReceitaWeb;
import com.example.receitas.Util.MakeToast;

public class WebActivity extends AppCompatActivity {

    private WebView webview;
    private ReceitaWeb receitaWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webview = findViewById(R.id.activity_web_webView);

        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey(Constantes.INTENT_RECEITA_WEB_MODEL)) {
            receitaWeb = (ReceitaWeb) bundle.getSerializable(Constantes.INTENT_RECEITA_WEB_MODEL);

            webview.setWebViewClient(new WebViewClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
            webview.loadUrl(receitaWeb.getUrl());

        }else{
            MakeToast.error("Não foi possível abrir esta pagina neste momento!", WebActivity.this);
            finish();
        }

    }
}