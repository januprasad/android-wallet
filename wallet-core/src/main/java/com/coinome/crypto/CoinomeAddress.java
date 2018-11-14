package com.coinome.crypto;

import org.bitcoinj.core.ECKey;

public class CoinomeAddress {
    private String path;
    private ECKey ecKey;

    public CoinomeAddress(ECKey ecKey, String pathAsString) {
        setEcKey(ecKey);
        setPath(pathAsString);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ECKey getEcKey() {
        return ecKey;
    }

    public void setEcKey(ECKey ecKey) {
        this.ecKey = ecKey;
    }
}
