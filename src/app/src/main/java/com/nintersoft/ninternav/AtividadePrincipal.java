package com.nintersoft.ninternav;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AtividadePrincipal extends AppCompatActivity {

    private EditText enderecoDigitado;
    private WebView[] navegador = new WebView[1];
    private ImageButton go;
    FloatingActionButton abas;
    FloatingActionButton cancelar;
    FloatingActionButton atualizar;
    FloatingActionButton proximo;
    FloatingActionButton anterior;
    private ListView listaAbas;
    private static int abaAtual = 0;
    public static String erro =
            "<html><head>" +
                    "<title>Erro | Ninternav</title>" +
                    "</head><body></br></br><center>Algo deu errado enquanto tentavamos ler a pagina." +
                    "</center></body></html>";
    public static String htmlNovaAba =
            "<html><head>" +
                    "<title>Nova aba | Ninternav</title>" +
                    "</head><body></br></br><center>Esta e a pagina de nova aba. faca o que quiser aqui" +
                    "</center></body></html>";
    private AdaptadorPersonalizado adaptadorPersonalizado;
    ArrayList<ItemPersonalizado> itens;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
                if (listaAbas.getVisibility() == View.VISIBLE){
                    navegador[abaAtual].setVisibility(View.VISIBLE);
                    listaAbas.setVisibility(View.GONE);
                }
                else {
                    navegador[abaAtual].setVisibility(View.GONE);
                    listaAbas.setVisibility(View.VISIBLE);
                }
                proximo.setVisibility(View.GONE);
                anterior.setVisibility(View.GONE);
                atualizar.setVisibility(View.GONE);
                cancelar.setVisibility(View.GONE);
            }
        });

        cancelar = (FloatingActionButton) findViewById(R.id.botaoCancelar);
        cancelar.setVisibility(View.GONE);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegador[abaAtual].stopLoading();
            }
        });
        atualizar = (FloatingActionButton) findViewById(R.id.botaoAtualizar);
        atualizar.setVisibility(View.GONE);
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegador[abaAtual].reload();
            }
        });
        proximo = (FloatingActionButton) findViewById(R.id.botaoPosterior);
        proximo.setVisibility(View.GONE);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navegador[abaAtual].canGoForward())
                        navegador[abaAtual].goForward();
            }
        });
        anterior = (FloatingActionButton) findViewById(R.id.botaoAnterior);
        anterior.setVisibility(View.GONE);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navegador[abaAtual].canGoBack())
                    navegador[abaAtual].goBack();
            }
        });

        enderecoDigitado = (EditText) findViewById(R.id.editText);

        navegador[abaAtual] = (WebView) findViewById(R.id.webView);
        defineConfigAba(navegador[abaAtual]);
        navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");

        go = (ImageButton) findViewById(R.id.go);

        listaAbas = new ListView(this);
        listaAbas.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listaAbas.setVisibility(View.GONE);
        RelativeLayout pai = (RelativeLayout) findViewById(R.id.relativeLayout);
        pai.addView(listaAbas);

        itens = new ArrayList<>();
        itens.add(new ItemPersonalizado(navegador[abaAtual].getTitle(), R.mipmap.ic_favicon));
        adaptadorPersonalizado = new AdaptadorPersonalizado(this, itens);
        listaAbas.setAdapter(adaptadorPersonalizado);

        listaAbas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listaAbas.setVisibility(View.GONE);
                abaAtual = i;
                navegador[abaAtual].setVisibility(View.VISIBLE);
            }
        });

        listaAbas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                itens.remove(i);
                adaptadorPersonalizado.notifyDataSetChanged();

                if (navegador.length != 1) {
                    WebView[] temp = new WebView[navegador.length - 1];
                    int t = 0;
                    for (int k = 0; k < temp.length; ++k){
                        if (k == i) t++;
                        temp[k] = navegador[k+t];
                    }
                    navegador = temp;
                    abaAtual = navegador.length - 1;
                }
                else {
                    navegador = new WebView[1];
                    abaAtual = 0;
                    navegador[abaAtual] = criarAba();

                    RelativeLayout pai = (RelativeLayout) findViewById(R.id.relativeLayout);
                    pai.addView(navegador[abaAtual]);

                    navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");
                    enderecoDigitado.setText(navegador[abaAtual].getTitle());
                }
                return true;
            }
        });

        enderecoDigitado.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!enderecoDigitado.getText().toString().equals("ninternav:nova_aba"))
                        enderecoDigitado.setText(Uri.parse(navegador[abaAtual].getUrl()).getHost());
                    go.setVisibility(View.GONE);
                    abas.setVisibility(View.GONE);
                    if (atualizar.getVisibility() == View.VISIBLE) atualizar.setVisibility(View.GONE);
                    proximo.setVisibility(View.GONE);
                    anterior.setVisibility(View.GONE);
                } else {
                    enderecoDigitado.setText(navegador[abaAtual].getUrl());
                    go.setVisibility(View.VISIBLE);
                    abas.setVisibility(View.VISIBLE);
                    if (cancelar.getVisibility() != View.VISIBLE) atualizar.setVisibility(View.VISIBLE);
                    enderecoDigitado.selectAll();
                    if (navegador[abaAtual].canGoForward()) proximo.setVisibility(View.VISIBLE);
                    if (navegador[abaAtual].canGoBack()) anterior.setVisibility(View.VISIBLE);
                }
            }
        });

        enderecoDigitado.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!enderecoDigitado.getText().toString().isEmpty() &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_NAVIGATE_NEXT)
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    String url = enderecoDigitado.getText().toString();
                    if (!url.contains(".") || url.contains(" ")){
                        if (url.contains(" ")) url = url.replace(' ', '+');
                        url = "https://www.google.com/search?q="
                                + url + "&ie=utf-8&oe=utf-8&client=ninternav&gfe_rd=cr";
                    }
                    switch (url) {
                        case "ninternav:nova_aba":
                            navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");
                            break;
                        default:
                            if (!url.matches("^\\w+?://.*"))
                                url = "http://" + url;
                            navegador[abaAtual].loadUrl(url);
                            break;
                    }
                    enderecoDigitado.setText(url);

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                } else if (enderecoDigitado.getText().toString().isEmpty() &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_NAVIGATE_NEXT)) {
                    enderecoDigitado.setText(navegador[abaAtual].getUrl());

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enderecoDigitado.getText().toString().isEmpty()) {
                    String url = enderecoDigitado.getText().toString();
                    if (!url.contains(".") || url.contains(" ")){
                        if (url.contains(" ")) url = url.replace(' ', '+');
                        url = "https://www.google.com/search?q="
                                + url + "&ie=utf-8&oe=utf-8&client=ninternav&gfe_rd=cr";
                    }
                    switch (url) {
                        case "ninternav:nova_aba":
                            navegador[abaAtual].loadDataWithBaseURL("ninterbt:nova_aba", htmlNovaAba, "text/html", null, "ninterbt:nova_aba");
                            break;
                        default:
                            if (!url.matches("^\\w+?://.*"))
                                url = "http://" + url;
                            navegador[abaAtual].loadUrl(url);
                            break;
                    }
                    enderecoDigitado.setText(url);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    if (enderecoDigitado.isFocused()) enderecoDigitado.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

        else if (id == R.id.action_newtab) {
            navegador[abaAtual].setVisibility(View.GONE);

            WebView[] temp = new WebView[navegador.length + 1];
            System.arraycopy(navegador, 0, temp, 0, navegador.length);

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

        else if (id == R.id.action_tabs){
            if (listaAbas.getVisibility() == View.VISIBLE){
                navegador[abaAtual].setVisibility(View.VISIBLE);
                listaAbas.setVisibility(View.GONE);
            }
            else {
                navegador[abaAtual].setVisibility(View.GONE);
                listaAbas.setVisibility(View.VISIBLE);
            }
        }

        else if (id == R.id.action_exit ){
            for (int i = 0; i < abaAtual; ++i){
                navegador[i].clearHistory();
                navegador[i].clearFormData();
                navegador[i].clearCache(true);
                navegador[i].clearMatches();
                navegador[i].clearSslPreferences();
            }
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    private WebView criarAba() {
        WebView novaAba = new WebView(this);
        novaAba.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                enderecoDigitado.setText(Uri.parse(url).getHost());
                cancelar.setVisibility(View.GONE);
                if (enderecoDigitado.isFocused()) atualizar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!enderecoDigitado.getText().toString().equals("ninternav:nova_aba"))
                    enderecoDigitado.setText(url);
                else{
                    itens.get(abaAtual).setIconeRid(R.mipmap.ic_favicon);
                    adaptadorPersonalizado.notifyDataSetChanged();
                }
                cancelar.setVisibility(View.VISIBLE);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadDataWithBaseURL("ninternav:erro", erro, "text/html", null, "ninternav:erro");
                itens.get(abaAtual).setIconeRid(R.mipmap.ic_favicon);
                adaptadorPersonalizado.notifyDataSetChanged();
                enderecoDigitado.setText(view.getUrl());
            }
        });
        novaAba.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title){
                adaptadorPersonalizado.getItem(abaAtual).setTexto(title);
                adaptadorPersonalizado.notifyDataSetChanged();
            }
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon){
                adaptadorPersonalizado.getItem(abaAtual).setImagem(icon);
                adaptadorPersonalizado.notifyDataSetChanged();
            }
        });
        novaAba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    if (anterior.getVisibility() == View.VISIBLE) anterior.setVisibility(View.GONE);
//                    if (proximo.getVisibility() == View.VISIBLE) proximo.setVisibility(View.GONE);
//                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if (anterior.getVisibility() == View.GONE)
                        if (navegador[abaAtual].canGoBack())
                            anterior.setVisibility(View.VISIBLE);
                    if (proximo.getVisibility() == View.GONE)
                        if (navegador[abaAtual].canGoForward())
                            proximo.setVisibility(View.VISIBLE);
                    atualizar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (anterior.getVisibility() == View.VISIBLE) anterior.setVisibility(View.GONE);
                            if (proximo.getVisibility() == View.VISIBLE) proximo.setVisibility(View.GONE);
                            atualizar.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
                return false;
            }
        });
        novaAba.getSettings().setLoadsImagesAutomatically(true);
        novaAba.getSettings().setJavaScriptEnabled(true);
        novaAba.getSettings().setBuiltInZoomControls(true);
        novaAba.getSettings().setDisplayZoomControls(false);
        novaAba.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        novaAba.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        itens.add(new ItemPersonalizado("Nova Aba | Ninternav", R.mipmap.ic_favicon));
        adaptadorPersonalizado.notifyDataSetChanged();

        return novaAba;
    }

    private void defineConfigAba(WebView nav) {
        nav.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                enderecoDigitado.setText(Uri.parse(url).getHost());
                cancelar.setVisibility(View.GONE);
                if (enderecoDigitado.isFocused()) atualizar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!enderecoDigitado.getText().toString().equals("ninternav:nova_aba"))
                    enderecoDigitado.setText(url);
                else{
                    itens.get(abaAtual).setIconeRid(R.mipmap.ic_favicon);
                    adaptadorPersonalizado.notifyDataSetChanged();
                }
                cancelar.setVisibility(View.VISIBLE);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadDataWithBaseURL("ninternav:erro", erro, "text/html", null, "ninternav:erro");
                itens.get(abaAtual).setIconeRid(R.mipmap.ic_favicon);
                enderecoDigitado.setText(view.getUrl());
            }
        });
        nav.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title){
                adaptadorPersonalizado.getItem(abaAtual).setTexto(title);
                adaptadorPersonalizado.notifyDataSetChanged();
            }
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon){
                adaptadorPersonalizado.getItem(abaAtual).setImagem(icon);
                adaptadorPersonalizado.notifyDataSetChanged();
            }
        });
        nav.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    if (anterior.getVisibility() == View.VISIBLE) anterior.setVisibility(View.GONE);
//                    if (proximo.getVisibility() == View.VISIBLE) proximo.setVisibility(View.GONE);
//                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if (anterior.getVisibility() == View.GONE)
                        if (navegador[abaAtual].canGoBack())
                            anterior.setVisibility(View.VISIBLE);
                    if (proximo.getVisibility() == View.GONE)
                        if (navegador[abaAtual].canGoForward())
                            proximo.setVisibility(View.VISIBLE);
                    atualizar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (anterior.getVisibility() == View.VISIBLE) anterior.setVisibility(View.GONE);
                            if (proximo.getVisibility() == View.VISIBLE) proximo.setVisibility(View.GONE);
                            atualizar.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
                return false;
            }
        });
        nav.getSettings().setLoadsImagesAutomatically(true);
        nav.getSettings().setJavaScriptEnabled(true);
        nav.getSettings().setBuiltInZoomControls(true);
        nav.getSettings().setDisplayZoomControls(false);
        nav.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    @Override
    public void onBackPressed(){
        if (listaAbas.getVisibility() == View.VISIBLE){
            navegador[abaAtual].setVisibility(View.VISIBLE);
            listaAbas.setVisibility(View.GONE);
        }
        else if (navegador[abaAtual].canGoBack()) navegador[abaAtual].goBack();
        else{
            for (int i = 0; i < abaAtual; ++i){
                navegador[i].clearHistory();
                navegador[i].clearFormData();
                navegador[i].clearCache(true);
                navegador[i].clearMatches();
                navegador[i].clearSslPreferences();
            }
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AtividadePrincipal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nintersoft.ninternav/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AtividadePrincipal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nintersoft.ninternav/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
