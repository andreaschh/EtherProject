package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    EditText txtPrivateKey;
    Button btnimportkey, btncreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        ViewPager viewPager=findViewById(R.id.viewPager);
        Adapter adapter=new Adapter(this);
        viewPager.setAdapter(adapter);




        btnimportkey = (Button) findViewById(R.id.btnimportkey);
        btncreate = findViewById(R.id.btncreate);


        btnimportkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(MainActivity.this, importprivatekey.class);
                startActivity(in);

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is the login page.You can create a wallet or import a private key to continue", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, wallet.class);
                startActivity(in);

            }
        });

    }
}
