package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText txtPrivateKey;
    Button btnimportkey,btncreate;

    //DatabaseReference reff;
    //FirebaseAuth fAuth;
    //importkey key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#90ee90"));
        getSupportActionBar().setBackgroundDrawable(cd);

        txtPrivateKey=(EditText)findViewById(R.id.txtPrivateKey);
        btnimportkey=(Button)findViewById(R.id.btnimportkey);
        btncreate=findViewById(R.id.btncreate);


        btnimportkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this,"sdadsdsas",Toast.LENGTH_LONG).show();

                Intent in=new Intent(MainActivity.this,Dashboard.class);
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
            Intent in=new Intent(MainActivity.this,wallet.class);
            startActivity(in);

        }
    });
    }
}
