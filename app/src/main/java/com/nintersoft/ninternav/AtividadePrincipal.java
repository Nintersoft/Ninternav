package com.nintersoft.ninternav;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AtividadePrincipal extends AppCompatActivity {

    private EditText enderecoDigitado;
    private WebView[] navegador = new WebView[1];
    private Button go;
    FloatingActionButton abas;
    private ListView listaAbas;
    private static int abaAtual = 0;
    public static String erro =
            "<html><head>"+
            "<title>Erro | Ninternav</title>"+
            "</head><body></br></br><center>Algo deu errado enquanto tentavamos ler a pagina."+
            "</center></body></html>";
    public static String htmlNovaAba =
            "<html><head>"+
                    "<title>Nova aba | Ninternav</title>"+
                    "</head><body></br></br><center>Esta e a pagina de nova aba. faca o que quiser aqui"+
                    "</center></body></html>";
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> listaDeTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        abas = (FloatingActionButton) findViewById(R.id.botaoAbas);
        abas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                navegador[abaAtual].setVisibility(View.GONE);
                listaAbas.setVisibility(View.VISIBLE);
            }
        });

        enderecoDigitado = (EditText) findViewById(R.id.editText);

        navegador[abaAtual] = (WebView) findViewById(R.id.webView);
        defineConfigAba(navegador[abaAtual]);
        navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");

        go = (Button) findViewById(R.id.go);

        listaAbas = new ListView(this);
        listaAbas.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listaAbas.setVisibility(View.GONE);
        RelativeLayout pai = (RelativeLayout) findViewById(R.id.relativeLayout);
        pai.addView(listaAbas);

        listaDeTexto = new ArrayList<>();
        listaDeTexto.add(navegador[abaAtual].getTitle());
        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_item_ns, listaDeTexto);
        listaAbas.setAdapter(adaptador);
        listaAbas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listaAbas.setVisibility(View.GONE);
                abaAtual = i;
                navegador[abaAtual].setVisibility(View.VISIBLE);
            }
        });

        enderecoDigitado.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (!enderecoDigitado.getText().toString().equals("ninternav:nova_aba"))
                        enderecoDigitado.setText(Uri.parse(navegador[abaAtual].getUrl()).getHost());
                    go.setVisibility(View.GONE);
                    abas.setVisibility(View.GONE);
                }
                else{
                    enderecoDigitado.setText(navegador[abaAtual].getUrl());
                    go.setVisibility(View.VISIBLE);
                    abas.setVisibility(View.VISIBLE);
                }
            }
        });

        enderecoDigitado.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!enderecoDigitado.getText().toString().isEmpty() &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_NAVIGATE_NEXT)
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    String url = enderecoDigitado.getText().toString();
                    switch (url){
                        case "ninternav:nova_aba":
                            navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");
                            break;
                        default:
                            if (!url.matches("^\\w+?://.*"))
                                url = "http://"+url;
                            navegador[abaAtual].loadUrl(url);
                            break;
                    }
                    enderecoDigitado.setText(url);

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                else if (enderecoDigitado.getText().toString().isEmpty() &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_NAVIGATE_NEXT)) {
                    enderecoDigitado.setText(navegador[abaAtual].getUrl());

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enderecoDigitado.getText().toString().isEmpty())
                {
                    String url = enderecoDigitado.getText().toString();
                    switch (url){
                        case "ninternav:nova_aba":
                            navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");
                            break;
                        default:
                            if (!url.matches("^\\w+?://.*"))
                                url = "http://"+url;
                            navegador[abaAtual].loadUrl(url);
                            break;
                    }
                    enderecoDigitado.setText(url);

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                else {
                    if (enderecoDigitado.isFocused()) enderecoDigitado.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atividade_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_newtab){
            navegador[abaAtual].setVisibility(View.GONE);

            WebView[] temp = new WebView[navegador.length + 1];
            for (int i = 0; i < navegador.length; ++i)
                temp[i] = navegador[i];

            navegador = temp;
            abaAtual = navegador.length - 1;
            navegador[abaAtual] = criarAba();

            RelativeLayout pai = (RelativeLayout) findViewById(R.id.relativeLayout);
            pai.addView(navegador[abaAtual]);
            navegador[abaAtual].setVisibility(View.VISIBLE);

            navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");

            enderecoDigitado.setText(navegador[abaAtual].getTitle());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private WebView criarAba (){
        WebView novaAba= new WebView(this);
        novaAba.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url){
                enderecoDigitado.setText(Uri.parse(url).getHost());
                listaDeTexto.set(abaAtual, view.getTitle());
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                if (!enderecoDigitado.getText().toString().equals("ninternav:nova_aba"))
                    enderecoDigitado.setText(url);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                view.loadDataWithBaseURL("ninternav:erro",erro, "text/html", null, "ninternav:erro");
                enderecoDigitado.setText(view.getUrl());
            }
        });
        novaAba.getSettings().setLoadsImagesAutomatically(true);
        novaAba.getSettings().setJavaScriptEnabled(true);
        novaAba.getSettings().setBuiltInZoomControls(true);
        novaAba.getSettings().setDisplayZoomControls(false);
        novaAba.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        novaAba.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                            ViewGroup.LayoutParams.MATCH_PARENT));

        listaDeTexto.add("Nova Aba | Ninternav");
        adaptador.notifyDataSetChanged();

        return novaAba;
    }

    private void defineConfigAba(WebView nav){
        nav.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url){
                enderecoDigitado.setText(Uri.parse(url).getHost());
                listaDeTexto.set(abaAtual, view.getTitle());
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                //if (!enderecoDigitado.getText().toString().equals("ninternav:nova_aba"))
                enderecoDigitado.setText(url);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                view.loadDataWithBaseURL("ninternav:erro",erro, "text/html", null, "ninternav:erro");
                enderecoDigitado.setText(view.getUrl());
            }
        });
        nav.getSettings().setLoadsImagesAutomatically(true);
        nav.getSettings().setJavaScriptEnabled(true);
        nav.getSettings().setBuiltInZoomControls(true);
        nav.getSettings().setDisplayZoomControls(false);
        nav.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }
}
