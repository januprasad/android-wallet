package com.coinome.crypto;


import android.os.Build;
import android.support.annotation.RequiresApi;

import com.coinome.crypto.txn.CoinomeTxn;
import com.coinome.crypto.wallet.bitcoin.BitCoinWallet;
import com.google.gson.Gson;

import org.bitcoinj.params.MainNetParams;

import java.nio.charset.StandardCharsets;

import static com.coinome.crypto.Passwords.toByteArray;

public class Main {
    private static String txn = "{\n" +
            "    \"txid\": \"96534da2f213367a6d589f18d7d6d1689748cd911f8c33a9aee754a80de166be\",\n" +
            "    \"hash\": \"96534da2f213367a6d589f18d7d6d1689748cd911f8c33a9aee754a80de166be\",\n" +
            "    \"version\": 1,\n" +
            "    \"size\": 224,\n" +
            "    \"vsize\": 224,\n" +
            "    \"weight\": 896,\n" +
            "    \"locktime\": 0,\n" +
            "    \"vin\": [\n" +
            "        {\n" +
            "            \"txid\": \"79b0f1bfe96242ce64c178131f19610a9c56704231f543d14d14dd202b46db75\",\n" +
            "            \"vout\": 0,\n" +
            "            \"scriptSig\": {\n" +
            "                \"asm\": \"30450221008f906b9fe728cb17c81deccd6704f664ed1ac920223bb2eca918f066269c703302203b1c496fd4c3fa5071262b98447fbca5e3ed7a52efe3da26aa58f738bd342d31[ALL] 04bca69c59dc7a6d8ef4d3043bdcb626e9e29837b9beb143168938ae8165848bfc788d6ff4cdf1ef843e6a9ccda988b323d12a367dd758261dd27a63f18f56ce77\",\n" +
            "                \"hex\": \"4830450221008f906b9fe728cb17c81deccd6704f664ed1ac920223bb2eca918f066269c703302203b1c496fd4c3fa5071262b98447fbca5e3ed7a52efe3da26aa58f738bd342d31014104bca69c59dc7a6d8ef4d3043bdcb626e9e29837b9beb143168938ae8165848bfc788d6ff4cdf1ef843e6a9ccda988b323d12a367dd758261dd27a63f18f56ce77\"\n" +
            "            },\n" +
            "            \"sequence\": 4294967295\n" +
            "        }\n" +
            "    ],\n" +
            "    \"vout\": [\n" +
            "        {\n" +
            "            \"value\": 0.00128307,\n" +
            "            \"n\": 0,\n" +
            "            \"scriptPubKey\": {\n" +
            "                \"asm\": \"OP_DUP OP_HASH160 dd6cce9f255a8cc17bda8ba0373df8e861cb866e OP_EQUALVERIFY OP_CHECKSIG\",\n" +
            "                \"hex\": \"76a914dd6cce9f255a8cc17bda8ba0373df8e861cb866e88ac\",\n" +
            "                \"reqSigs\": 1,\n" +
            "                \"type\": \"pubkeyhash\",\n" +
            "                \"addresses\": [\n" +
            "                    \"1MBngSqZbMydscpzSoehjP8kznMaHAzh9y\"\n" +
            "                ]\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"hex\": \"010000000175db462b20dd144dd143f5314270569c0a61191f1378c164ce4262e9bff1b079000000008b4830450221008f906b9fe728cb17c81deccd6704f664ed1ac920223bb2eca918f066269c703302203b1c496fd4c3fa5071262b98447fbca5e3ed7a52efe3da26aa58f738bd342d31014104bca69c59dc7a6d8ef4d3043bdcb626e9e29837b9beb143168938ae8165848bfc788d6ff4cdf1ef843e6a9ccda988b323d12a367dd758261dd27a63f18f56ce77ffffffff0133f50100000000001976a914dd6cce9f255a8cc17bda8ba0373df8e861cb866e88ac00000000\",\n" +
            "    \"blockhash\": \"00000000000000000a73bcb9cd0528a8c014740ca5175265368d94718e096336\",\n" +
            "    \"confirmations\": 219354,\n" +
            "    \"time\": 1416390320,\n" +
            "    \"blocktime\": 1416390320\n" +
            "} ";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args){

//        try {
//            String mn = BitCoinWallet.generateMnemonic();
//        } catch (MnemonicException.MnemonicLengthException e) {
//            e.printStackTrace();
//        }






        String mn = "cactus spatial damp canvas coach income wool doll mail radio senior mixed";
        System.out.println(mn);
        String passphrase = "2222";
//            BitCoinWallet bitcoinWallet = new BitCoinWallet(mn, PasswordHash.generatePasswordHash(null, passphrase));
        BitCoinWallet bitcoinWallet = new BitCoinWallet(mn, passphrase, MainNetParams.get());
        try {
            bitcoinWallet.generateRootKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bitcoinWallet.publicKeysMap(bitcoinWallet.generateRootKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        CoinomeTxn coinomeTxn =gson.fromJson(txn, CoinomeTxn.class);
        try {
            bitcoinWallet.signBitcoinTest(coinomeTxn, bitcoinWallet.getAddressList(bitcoinWallet.generateRootKey(),2).get(0).getEcKey());
        } catch (Exception e) {
            e.printStackTrace();
        }


//        bitcoinWallet.getAddressList(bitcoinWallet.externalKey,11);
//        System.out.println();
    }
}
