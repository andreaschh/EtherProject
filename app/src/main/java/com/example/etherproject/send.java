package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.web3j.crypto.Keys.getAddress;


public class send extends AppCompatActivity {
    private BigInteger mGasPrice;

    private BigInteger mGasLimit;

    private sendingEther sendingEther;

    private sendingToken sendingToken;

    private Web3j mWeb3j;
    private Object mNodeUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);


        GetFee();
   
        getWeb3j(mNodeUrl);
        Web3j w3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/1aa32222b470402da4666f91b37613dd"));



        String contractAddress = "0x5B38Da6a701c568545dCfcB03FcB875f56beddC4";


        JavaToken javaToken = JavaToken.load(contractAddress, w3,new DefaultGasProvider());



        try {
            FileInputStream fin = openFileInput("pk.txt");
            DataInputStream din = new DataInputStream(fin);
            InputStreamReader isr = new InputStreamReader(din);
            BufferedReader br = new BufferedReader(isr);

            int i=0;
            String lines[]=new String[3];
            String line1;

            while ((line1 = br.readLine()) != null) {
                lines[i] = line1;
                i++;
            }
            TextView ethaddress=findViewById(R.id.ethaddress);
            ethaddress.setText(lines[0]);
            fin.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClick(View view) {
        Button SendEther=findViewById(R.id.SendEther);
        Button SendToken=findViewById(R.id.SendToken);
        switch (view.getId()) {
            case R.id.SendEther:
                SendEther();
                break;
            case R.id.SendToken:
                SendToken();
                break;

        }
    }

    private void SendToken() {
        sendingToken = new sendingToken(mWeb3j,
                getGasPrice(),
                getGasLimit());

        sendingToken.Send(getToAddress(),getSendTokenAmmount());
    }

    private void SendEther() {
        sendingEther = new sendingEther(mWeb3j,
                getGasPrice(),
                getGasLimit());

        sendingEther.Send(getToAddress(),getSendEtherAmmount());
    }


    private void getWeb3j(Object mNodeUrl){

        new Initiate(mNodeUrl);
        mWeb3j = Initiate.sWeb3jInstance;
    }
    private String getToAddress(){
        EditText sendtoaddress=findViewById(R.id.sendtoaddress);
        return sendtoaddress.getText().toString();
    }

    private void setToAddress(String toAddress){
        EditText sendtoaddress=findViewById(R.id.sendtoaddress);
        sendtoaddress.setText(toAddress);
    }

    private String getSendEtherAmmount(){
        EditText  sendethervalue = (EditText) findViewById(R.id.SendEthValue);
        return sendethervalue.getText().toString();
    }

    private String getSendTokenAmmount(){
        EditText sendtokenvalue = (EditText) findViewById(R.id.SendTokenValue);
        return sendtokenvalue.getText().toString();
    }

    private void setEthBalance(String ethBalance){
        TextView ethbalance=findViewById(R.id.ethbalance);
        ethbalance.setText(ethBalance);
    }

    private String getEthBalance(){
        try {
            return new Balance(mWeb3j,getEthAddress()).getInEther().toString();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getEthAddress(){
        return getAddress(getEthBalance());
    }

    private void setEthAddress(String address) {
        TextView ethaddress=findViewById(R.id.ethaddress);
        ethaddress.setText(address);
    }

    public void GetFee(){
        TextView tv_fee=findViewById(R.id.tv_fee);
        setGasPrice(getGasPrice());
        setGasLimit(getGasLimit());

        BigDecimal fee = BigDecimal.valueOf(mGasPrice.doubleValue()*mGasLimit.doubleValue());
        BigDecimal feeresult = Convert.fromWei(fee.toString(),Convert.Unit.ETHER);
        tv_fee.setText(feeresult.toPlainString() + " ETH");
    }

    private String getGasLimit() {
        TextView tv_gas_limit=findViewById(R.id.tv_gas_limit);
        return tv_gas_limit.getText().toString();
    }

    private String getGasPrice() {
        TextView tv_gas_price=findViewById(R.id.tv_gas_price);
        return tv_gas_price.getText().toString();
    }


    public void backLoadSmartContract(Map<String,String> result) {
        setTokenBalance(result.get("tokenbalance"));
        setTokenName(result.get("tokenname"));
        setTokenSymbol(result.get("tokensymbol"));
        setTokenAddress(result.get("tokenaddress"));
        setTokenSupply(result.get("totalsupply"));
    }

    private void setTokenBalance(String value){
        TextView tokenbalance=findViewById(R.id.tokenbalance);
        tokenbalance.setText(value);
    }

    private void setTokenName(String value){
        TextView tokenname=findViewById(R.id.tokenname);
        tokenname.setText(value);
    }

    private void setTokenSymbol(String value){
        TextView tokensymbol=findViewById(R.id.tokensymbol);
        tokensymbol.setText(value);
    }

    private void setTokenSupply(String value){
        TextView tokensupply=findViewById(R.id.tokensupply);
        tokensupply.setText(value);
    }

    private void setTokenAddress(String value){
        TextView tokenaddress=findViewById(R.id.tokenaddress);
        tokenaddress.setText(value);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListenerGL = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            GetGasLimit(String.valueOf(seekBar.getProgress()*1000+42000));
        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    private String GetGasLimit(String valueOf) {
        TextView tv_gas_limit=findViewById(R.id.tv_gas_limit);
        return tv_gas_limit.getText().toString();
    }

    private void setGasLimit(String gasLimit){
        mGasLimit = BigInteger.valueOf(Long.valueOf(gasLimit));
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListenerGP = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            GetGasPrice(String.valueOf(seekBar.getProgress()+12));
        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) { }
        @Override public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    private String GetGasPrice(String valueOf) {
        TextView tv_gas_price=findViewById(R.id.tv_gas_price);
        return tv_gas_price.getText().toString();
    }

    private void setGasPrice(String gasPrice){
        mGasPrice = Convert.toWei(gasPrice,Convert.Unit.GWEI).toBigInteger();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {
            Intent in = new Intent(this, Settings.class);
            startActivity(in);
        }else if (id == R.id.action_logout) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }else if(id==R.id.action_dashboard)
        {
            Intent in =new Intent(this,Dashboard.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

}