package com.example.etherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
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
    TextView txtaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        txtaddress = findViewById(R.id.text_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#90ee90"));
        getSupportActionBar().setBackgroundDrawable(cd);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//enter your own infura api key below
        web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/1aa32222b470402da4666f91b37613dd"));

        setupBouncyCastle();

        //  this is the pathname for the file that will be created and stores the wallet details
        EditText Edtpath = findViewById(R.id.walletpath);
        final String etheriumwalletPath = Edtpath.getText().toString();

        file = new File(getFilesDir() + etheriumwalletPath);// the etherium wallet location
        //create the directory if it does not exist
        if (!file.mkdirs()) {
            file.mkdirs();
        } else {
            Toast.makeText(getApplicationContext(), "Directory already created", Toast.LENGTH_LONG).show();

        }

    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up a provider  when it's used for the first time.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            return;
        }
        //There is a possibility  the bouncy castle registered by android may not have all ciphers
        //so we  substitute with the one bundled in the app.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

    }

    public void createWallet(View v)  {

        EditText Edtpassword = findViewById(R.id.password);
        final String password = Edtpassword.getText().toString();  // this will be your etherium password
        try {
            // generating the etherium wallet
            Walletname = WalletUtils.generateLightNewWalletFile(password, file);
            credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);
            txtaddress.setText(getString(R.string.your_address) + credentials.getAddress());
            

        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

        }

       //Intent in=new Intent(wallet.this,Dashboard.class);
        //startActivity(in);
    }


}