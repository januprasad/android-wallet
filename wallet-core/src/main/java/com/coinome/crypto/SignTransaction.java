package com.coinome.crypto;

import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;

public class SignTransaction {

    protected TransactionSignature sign(Transaction transaction, ECKey key) {
        Script script = transaction.getInput(0).getConnectedOutput().getScriptPubKey();
        return transaction.calculateSignature(0, key, script, Transaction.SigHash.ALL, false);
    }


    public static void main(String args[]){
// message (hash) to be signed with private key
        String msg = "15953935a135031bfec37d36a9d662aea43e1deb0ea463d6932ac6e537cb3e81";

        // an example of WiF for private key (taken from 'Mastering Bitcoin')
        String wif ="KxFC1jmwwCoACiCAWZ3eXa96mBM6tb3TYzGmf6YwgdGWZgawvrtJ";

        // creating a key object from WiF
        DumpedPrivateKey dpk = DumpedPrivateKey.fromBase58(null, wif);
        ECKey key = dpk.getKey();
        System.out.println(key.getPrivateKeyAsHex());  // true
        // checking our key object
        NetworkParameters main =  MainNetParams.get();
        String check = key.getPrivateKeyAsWiF(main);
        System.out.println(wif.equals(check));  // true

        // creating Sha object from string
        Sha256Hash hash = Sha256Hash.wrap(msg);

        // creating signature
        ECKey.ECDSASignature sig = key.sign(hash);

        // encoding
        byte[] res = sig.encodeToDER();

        // converting to hex
        String hex = bytesToHex(res);

        System.out.println(hex);  // 304502210081B528....

    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();


    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
