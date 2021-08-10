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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class testing extends AppCompatActivity {
    String abi = "[\n" +
            "\t{\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"total\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"nonpayable\",\n" +
            "\t\t\"type\": \"constructor\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"anonymous\": false,\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"indexed\": true,\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"tokenOwner\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"indexed\": true,\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"spender\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"indexed\": false,\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"tokens\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"Approval\",\n" +
            "\t\t\"type\": \"event\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"anonymous\": false,\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"indexed\": true,\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"from\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"indexed\": true,\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"to\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"indexed\": false,\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"tokens\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"Transfer\",\n" +
            "\t\t\"type\": \"event\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"owner\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"delegate\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"allowance\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"view\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"delegate\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"numTokens\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"approve\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"bool\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"bool\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"nonpayable\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"tokenOwner\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"balanceOf\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"view\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [],\n" +
            "\t\t\"name\": \"decimals\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint8\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"uint8\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"view\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [],\n" +
            "\t\t\"name\": \"name\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"string\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"string\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"view\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [],\n" +
            "\t\t\"name\": \"symbol\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"string\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"string\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"view\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [],\n" +
            "\t\t\"name\": \"totalSupply\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"view\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"receiver\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"numTokens\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"transfer\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"bool\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"bool\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"nonpayable\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"owner\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"address\",\n" +
            "\t\t\t\t\"name\": \"buyer\",\n" +
            "\t\t\t\t\"type\": \"address\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"uint256\",\n" +
            "\t\t\t\t\"name\": \"numTokens\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"transferFrom\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"internalType\": \"bool\",\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"bool\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"stateMutability\": \"nonpayable\",\n" +
            "\t\t\"type\": \"function\"\n" +
            "\t}\n" +
            "]";


    Web3j web3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/1aa32222b470402da4666f91b37613dd"));
        Button btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line = "";
                try {
                    FileInputStream fin = openFileInput("privatekey.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    String line1;
                    if ((line1 = br.readLine()) != null) {
                        line = line1;
                    }
                    TextView txt = findViewById(R.id.txt_tes);
                    txt.setText(line1);

                    String privetkey = txt.getText().toString();

                    Credentials credentials = Credentials.create(privetkey);

                    EditText toAddress = findViewById(R.id.toAddress);
                    String p = toAddress.getText().toString();
                    EditText eth = findViewById(R.id.ethValue);
                    String p2 = eth.getText().toString();
                    if (toAddress.getText().toString().length()==0||eth.getText().toString().length()==0)
                    {
                        toAddress.setError("Receiver Address required");
                        eth.setError("Value required");
                    }else {
                        try {
                            TransactionReceipt receipt = Transfer.sendFunds(web3, credentials, p, BigDecimal.valueOf(Long.parseLong(p2)), Convert.Unit.ETHER).send();
                            StyleableToast.makeText(getApplicationContext(), "Transaction Receipt:" + receipt.getTransactionHash(), Toast.LENGTH_LONG, R.style.customToast).show();

                        } catch (Exception e) {
                            StyleableToast.makeText(getApplicationContext(), "Low Balance", Toast.LENGTH_LONG, R.style.mistakeToast).show();
                        }
                        fin.close();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        Button sendToken = findViewById(R.id.btn_sendToken);

        sendToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/1aa32222b470402da4666f91b37613dd"));
                String line = "";
                try {
                    FileInputStream fin = openFileInput("privatekey.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    String line1;
                    if ((line1 = br.readLine()) != null) {
                        line = line1;
                    }
                    TextView txt_token = findViewById(R.id.txt_token);
                    txt_token.setText("0x"+line1);
                    //sender
                    String privetkey = txt_token.getText().toString();

                    Credentials credentials = Credentials.create(privetkey);
                    EditText tokenaddres = findViewById(R.id.tokenAddres);
                    EditText toaddresstoken = findViewById(R.id.toAddressToken);
                    EditText tokenvalue = findViewById(R.id.tokenValue);


                    if (tokenaddres.getText().toString().length()==0||toaddresstoken.getText().toString().length()==0||tokenvalue.getText().toString().length()==0)
                    {
                        tokenaddres.setError("Token Address required");
                        toaddresstoken.setError("Receiver Address required");
                        tokenvalue.setError("Value required");

                    }else {

                        //Address of the Token
                        String contract_address1 = "0xd9145CCE52D386f254917e481eB44e9943F39138";

                        //recipient address
                        String p3 = toaddresstoken.getText().toString();
                        //amount
                        String p4;
                        p4 = tokenvalue.getText().toString();
                        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
                        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

                        String balance2 = "Balance: " + Convert.fromWei(web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Convert.Unit.ETHER);
                        StyleableToast.makeText(getApplicationContext(), "Low Balance", Toast.LENGTH_LONG, R.style.mistakeToast).show();
                        TextView txt_balancetoken = findViewById(R.id.txt_balancetoken);
                        txt_balancetoken.setText(balance2);

                        BigInteger gasLimit = BigInteger.valueOf(71000);
                        BigInteger gasPrice = new BigInteger(String.valueOf(16));

                        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contract_address1, abi);
                        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                        String hexvalue = Numeric.toHexString(signedMessage);

                        EthSendTransaction ethSendTransaction = null;
                        try {
                            ethSendTransaction = web3.ethSendRawTransaction(hexvalue).sendAsync().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        String transactionHash = ethSendTransaction.getTransactionHash();
                        if (transactionHash != null) {
                            return;
                        }

                        fin.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

    }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_send, menu);
    return true;

}
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent in = new Intent(this, Settings.class);
            startActivity(in);
        } else if(id==R.id.action_dashboard)
        {
            Intent in =new Intent(this,Dashboard.class);
            startActivity(in);

        }else if (id == R.id.action_logout) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}