package com.example.etherproject;


import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Initiate {

    public static Web3j sWeb3jInstance;

    public Initiate(Object urlNode){
        sWeb3jInstance = Web3j.build(new HttpService((String) urlNode));
    }
}
