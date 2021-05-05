package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Dashboard extends AppCompatActivity {
    Web3j web3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/1aa32222b470402da4666f91b37613dd"));
        Button exportpublic=findViewById(R.id.exportprivatekey);
        TextView txt_public=findViewById(R.id.txt_publicaddress);
        exportpublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fin = openFileInput("pk.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    int i = 0;
                    String lines[] = new String[4];
                    String line1;

                    while ((line1 = br.readLine()) != null) {
                        lines[i] = line1;
                        i++;
                    }
                    txt_public.setText(lines[0]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //retrieve balance
        Button btnbalance=findViewById(R.id.btn_balance);

        TextView txtbalance=findViewById(R.id.txt_balance);
        btnbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fin = openFileInput("pk.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    int i = 0;
                    String lines[] = new String[4];
                    String line1;

                    while ((line1 = br.readLine()) != null) {
                        lines[i] = line1;
                        i++;
                    }
                    txtbalance.setText(lines[3]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
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
            Intent in = new Intent(this, testing.class);
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