package com.coinome.crypto.bips;

import android.annotation.SuppressLint;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.HDUtils;

import java.util.List;

public class BitcoinBIP44 {
    //    private static final String BIP44_PATH = "44/%d/0";
    private static final String BIP44_PATH = "44H/%dH/0H/0";
    private static final String BIP44_PATH_STD = "44H/%dH/";
    private static Integer bip44CoinType = 0;

    public static List<ChildNumber> getBip44Path() {
        @SuppressLint("DefaultLocale") String keyPath = String.format(BIP44_PATH, getBip44CoinType());
        return HDUtils.parsePath(keyPath);
    }

    public static List<ChildNumber> getBip44Path(String path) throws Exception {
        if (path != null && path.isEmpty()) {
            String keyPath = String.format(BIP44_PATH_STD.concat(path), getBip44CoinType());
            return HDUtils.parsePath(keyPath);
        } else return null;
    }

    private static Integer getBip44CoinType() {
        return bip44CoinType;
    }
}
