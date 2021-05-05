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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Settings extends AppCompatActivity {
    Web3j web3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/1aa32222b470402da4666f91b37613dd"));
        TextView privatekey=(TextView)findViewById(R.id.exportprivatekey);
        Button export=(Button)findViewById(R.id.btnptivatekey);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line="";
                try {
                    FileInputStream fin = openFileInput("privatekey.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    String line1;
                    if ((line1 = br.readLine()) != null) {
                        line = line1;
                    }
                    privatekey.setText("Your private key is:\n"+"0x"+line1);

                    fin.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    Button removewallet=findViewById(R.id.removewallet);

    removewallet.setOnClickListener(new View.OnClickListener() {
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
                String p=lines[0];
                String p1=lines[1];
                String p2=lines[2];

                lines[0]=null;
                lines[1]=null;
                lines[2]=null;
                StyleableToast.makeText(getApplicationContext(), "Wallet has been removed successfully", Toast.LENGTH_LONG, R.style.customToast).show();

                fin.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent in=new Intent(Settings.this, wallet.class);
            startActivity(in);

        }
    });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent in = new Intent(this, com.example.etherproject.testing.class);
            startActivity(in);
        }else if (id == R.id.action_logout) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        } else if(id==R.id.action_dashboard)
        {
            Intent in =new Intent(this,Dashboard.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}