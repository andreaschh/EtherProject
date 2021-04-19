package com.example.etherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Dashboard extends AppCompatActivity {
    Web3j web3;
    TextView  txt_balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#90ee90"));
        getSupportActionBar().setBackgroundDrawable(cd);

        //Intent getIntent=getIntent();
        TextView address=(TextView)findViewById(R.id.address);
        //text.setText(getIntent.getStringExtra("pk"));
        String line="";
        try {
            FileInputStream fin = openFileInput("pk.txt");
            DataInputStream din = new DataInputStream(fin);
            InputStreamReader isr = new InputStreamReader(din);
            BufferedReader br = new BufferedReader(isr);

            String line1;
            if ((line1 = br.readLine()) != null) {
                line = line1;
            }
            address.setText(line1);
            //Log.d("patates2",line1);
            fin.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        retrieveBalance();

    }


    public void retrieveBalance ()  {
        txt_balance = findViewById(R.id.text_balance);
        Button btnbalance=findViewById(R.id.buttonbalance);

        btnbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line="";
                try {
                    FileInputStream fin = openFileInput("pk.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    String line1;
                    if ((line1 = br.readLine()) != null) {
                        line = line1;
                    }
                    txt_balance.setText(line1);
                    //Log.d("patates2",line1);
                    fin.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                txt_balance.setText("ksdkds"+ txt_balance);
                //get wallet's balance

                try {
                    EthGetBalance balanceWei = web3.ethGetBalance(String.valueOf(txt_balance), DefaultBlockParameterName.LATEST).
                            sendAsync().get();
                    TextView txtbalance=findViewById(R.id.text_balance);
                    txtbalance.setText(getString(R.string.your_balance) + balanceWei.getBalance());
                }

                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Balance failed", Toast.LENGTH_LONG).show();

                }


            }
        });
        Log.d("patates2", String.valueOf(txt_balance));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    public  boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {
            Intent in = new Intent(this, send.class);
            startActivity(in);
        } else if (id == R.id.action_settings) {
            Intent in = new Intent(this, Settings.class);
            startActivity(in);
        }else if (id == R.id.action_logout) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}