package com.example.etherproject;

import android.os.AsyncTask;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class sendingToken {

    private Credentials mCredentials;
    private Web3j mWeb3j;
    private String fromAddress;
    private String mValueGasPrice;
    private String mValueGasLimit;

    //private CBSendingEther cbSendingEther;
    private CBSendingToken cbSendingToken;



    public sendingToken(Web3j web3j, String gasPrice, String gasLimit) {
        mWeb3j=web3j;
        mValueGasPrice = gasPrice;
        mValueGasLimit = gasLimit;
    }

    private BigInteger getGasPrice(){
        return BigInteger.valueOf(Long.valueOf(mValueGasPrice));
    }

    private BigInteger getGasLimit(){
        return BigInteger.valueOf(Long.valueOf(mValueGasLimit));
    }


    public void Send(String toAddress, String valueAmmount) {
        new SendToken().execute(toAddress, valueAmmount);
    }

    private class SendToken extends AsyncTask<String,Void,TransactionReceipt> {

        @Override
        protected TransactionReceipt doInBackground(String... value) {

            BigInteger ammount = BigInteger.valueOf(Long.parseLong(value[2]));

            System.out.println(getGasPrice());
            System.out.println(getGasLimit());

            JavaToken token = JavaToken.load(value[0], mWeb3j, getGasPrice(), getGasLimit());
            try {
                TransactionReceipt result = token.transfer(value[1], ammount).send();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(TransactionReceipt result) {
            super.onPostExecute(result);
            cbSendingToken.backSendToken(result);
        }
    }


}
