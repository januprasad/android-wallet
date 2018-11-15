package com.januprasad.android_wallet_core;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.HDUtils;

import java.util.List;

public class CoinType {
    private static final String BIP_44_KEY_PATH = "44H/%dH/%dH";

    protected String name;
    protected String symbol;
    protected Integer bip44Index;
    protected Integer unitExponent;
    public List<ChildNumber> getBip44Path(int account) {
        String path = String.format(BIP_44_KEY_PATH, bip44Index, account);
        return HDUtils.parsePath(path);
    }
}
