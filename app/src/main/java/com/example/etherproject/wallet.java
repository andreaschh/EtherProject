package com.example.etherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.io.File;
import java.math.BigDecimal;
import java.security.Provider;
import java.security.Security;

public class wallet extends AppCompatActivity {

    Web3j web3;
    File file;
    String Walletname;
    Credentials credentials;
    TextView  txtaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#90ee90"));
        getSupportActionBar().setBackgroundDrawable(cd);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/1aa32222b470402da4666f91b37613dd"));

        setupBouncyCastle();

        txtaddress = findViewById(R.id.text_address);

        EditText Edtpath = findViewById(R.id.walletpath);
        final String etheriumwalletPath = Edtpath.getText().toString();
        file = new File(getFilesDir() + etheriumwalletPath);


        if (Edtpath.getText().toString().length() == 0)
            Edtpath.setError("File name required");
        else {

                if (!file.mkdirs()) {
                    file.mkdirs();

                } else {
                    Toast.makeText(getApplicationContext(), "Directory already created", Toast.LENGTH_LONG).show();

                }
            }

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
            Intent in = new Intent(this, com.example.etherproject.send.class);
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
    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            return;
        }

        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

    }

    public void createWallet(View v)  {

        web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/1aa32222b470402da4666f91b37613dd"));

        Toast.makeText(getApplicationContext()," Now Connecting to Ethereum network",Toast.LENGTH_LONG).show();
        try {
            //if the client version has an error the user will not gain access if successful the user will get connnected
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"Error while connecting",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Error while connecting",Toast.LENGTH_LONG).show();
        }


        EditText Edtpassword = findViewById(R.id.password);
        final String password = Edtpassword.getText().toString();  // this will be your etherium password

        if( Edtpassword.getText().toString().length() == 0 )
            Edtpassword.setError( "Password Required" );
        else {
            try {
                // generating the etherium wallet
                Walletname = WalletUtils.generateLightNewWalletFile(password, file);
                credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);

                txtaddress.setText("Your public address is:\n"+ credentials.getAddress());


                //Bundle to pass public address to Dashboard
                Intent in=new Intent(this,Dashboard.class);
                String pk=txtaddress.getText().toString();
                Bundle bundle=new Bundle();
                bundle.putString("pk", pk);
                in.putExtras(bundle);
                startActivity(in);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        }


    }

    }


