package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;


public class Dashboard extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {
            Intent in = new Intent(this, send.class);
            startActivity(in);
        } else if (id == R.id.action_settings) {
            Intent in = new Intent(this, Settings.class);
            startActivity(in);
        } else if (id == R.id.action_logout) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

}