package com.nintersoft.ninternav;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class AtividadePrincipal extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText enderecoDigitado;
    private WebView navegador;
    private Button go;
    public static String erro =
            "<html><head>"+
            "<title>Erro | Ninternav</title>"+
            "</head><body></br></br><center>Algo deu errado enqanto tentavamos ler a pagina."+
            "</center></body></html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        enderecoDigitado = (EditText) findViewById(R.id.editText);
        go = (Button) findViewById(R.id.go);
        navegador = (WebView) findViewById(R.id.webView);
        navegador.setWebViewClient(new meuNavegador());
        navegador.getSettings().setLoadsImagesAutomatically(true);
        navegador.getSettings().setJavaScriptEnabled(true);
        navegador.getSettings().setBuiltInZoomControls(true);
        navegador.getSettings().setDisplayZoomControls(false);
        navegador.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        enderecoDigitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enderecoDigitado.isInEditMode()) return;

                if (!navegador.getUrl().isEmpty()) enderecoDigitado.setText(navegador.getUrl());
            }
        });

        enderecoDigitado.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!enderecoDigitado.getText().toString().isEmpty() &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_NAVIGATE_NEXT)
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    String url = enderecoDigitado.getText().toString();
                    if (!url.matches("^\\w+?://.*"))
                        url = "http://"+url;
                    navegador.loadUrl(url);
                    enderecoDigitado.setText(url);

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                else if (enderecoDigitado.getText().toString().isEmpty() &&
                        (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_NAVIGATE_NEXT)) {
                    enderecoDigitado.setText(navegador.getUrl());

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
                if (!enderecoDigitado.getText().toString().isEmpty()){
                    String url = enderecoDigitado.getText().toString();

                    if (!url.matches("^\\w+?://.*"))
                        url = "http://" + url;
                    navegador.loadUrl(url);
                    enderecoDigitado.setText(url);

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                else {
                    enderecoDigitado.setText(navegador.getUrl());

                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });


    }

    private class meuNavegador extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url){
            enderecoDigitado.setText(Uri.parse(url).getHost());
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            enderecoDigitado.setText(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            navegador.loadData(erro, "text/html", null);
        }
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

        return super.onOptionsItemSelected(item);
    }
}
