package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;

public class wallet extends AppCompatActivity {

    Web3j web3;
    File file;
    String Walletname;
    Credentials credentials;
    TextView  txtaddress;

    private String file1="pk.txt";
    private String file2="privatekey.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/1aa32222b470402da4666f91b37613dd"));

        setupBouncyCastle();
        createWallet();
        }

    public void createWallet() {

        Button btnwallet=findViewById(R.id.createwallet);
        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/1aa32222b470402da4666f91b37613dd"));

        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                StyleableToast.makeText(getApplicationContext(), "You have connected successfully!", Toast.LENGTH_LONG, R.style.customToast).show();
            } else {
                StyleableToast.makeText(getApplicationContext(), "Error while connecting", Toast.LENGTH_LONG, R.style.mistakeToast).show();
            }
        } catch (Exception e) {
            StyleableToast.makeText(getApplicationContext(), "Error while connecting", Toast.LENGTH_LONG, R.style.mistakeToast).show();
        }

        btnwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtaddress = findViewById(R.id.text_address);
                EditText Edtpassword = findViewById(R.id.password);
                final String password = Edtpassword.getText().toString()+"\n";
                EditText Edtpath = findViewById(R.id.walletpath);

                final String etheriumwalletPath = Edtpath.getText().toString()+"\n";
                file = new File(getFilesDir() + etheriumwalletPath);

                if (Edtpath.getText().toString().length() == 0)
                    Edtpath.setError("File name required");
                else {

                    if (!file.mkdirs()) {
                        file.mkdirs();
                    }
                }

                if( Edtpassword.getText().toString().length() == 0)
                    Edtpassword.setError("Password Required");
                else{
                    if (Edtpassword.getText().toString().length()<6)
                        Edtpassword.setError("Password minimum contain 6 characters");
                    else {
                        if (Edtpassword.getText().toString().length()>10)
                            Edtpassword.setError("Password maximum contain 10 characters");
                        try {
                            // generating the etherium wallet
                            Walletname = WalletUtils.generateLightNewWalletFile(password, file);
                            final String walletname=Walletname.getBytes().toString()+"\n";
                            credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);
                            txtaddress.setText("Your public address is:" + credentials.getAddress()+"\n");

                            //Take the Balance of the Account
                            String Balance= "Balance: " + Convert.fromWei(web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString(), Convert.Unit.ETHER);

                            //create the private key and save it into the file 2
                            ECKeyPair ecKeyPair = null;
                            try {
                                ecKeyPair = Keys.createEcKeyPair();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (NoSuchProviderException e) {
                                e.printStackTrace();
                            }
                            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
                            String sPrivatekeyInHex = privateKeyInDec.toString(16);

                            try{
                                FileOutputStream fout = openFileOutput(file2,0);
                                fout.write(sPrivatekeyInHex.getBytes());
                                fout.close();
                            }catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }

                            StyleableToast.makeText(getApplicationContext(), "You have successfully create your wallet!!", Toast.LENGTH_LONG, R.style.customToast).show();
                            try {
                                FileOutputStream fout = openFileOutput(file1,0);
                                fout.write(txtaddress.getText().toString().getBytes());
                                fout.write(etheriumwalletPath.getBytes());
                                fout.write(password.getBytes());
                                fout.write(Balance.getBytes());
                                fout.close();

                            }catch (Exception ex)
                            {
                                ex.printStackTrace();
                                StyleableToast.makeText(getApplicationContext(), "Oops, something went wrong", Toast.LENGTH_LONG, R.style.mistakeToast).show();
                            }
                            Intent in=new Intent(wallet.this,Dashboard.class);
                            startActivity(in);

                        } catch (Exception e) {
                            StyleableToast.makeText(getApplicationContext(), "Oops, something went wrong", Toast.LENGTH_LONG, R.style.mistakeToast).show();

                        }
                    }
                }


            }
        });
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

}




