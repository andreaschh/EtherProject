package com.example.etherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;


public class Dashboard extends AppCompatActivity {
    Web3j web3;
    TextView  txt_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#90ee90"));
        getSupportActionBar().setBackgroundDrawable(cd);

        Intent getIntent=getIntent();
        TextView text=(TextView)findViewById(R.id.address);
        text.setText(getIntent.getStringExtra("pk"));

        txt_balance = findViewById(R.id.text_balance);
    }

    public void retrieveBalance (View v)  {



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
        }else if (id == R.id.action_logout) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}