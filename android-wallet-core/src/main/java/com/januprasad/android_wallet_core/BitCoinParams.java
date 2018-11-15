package com.januprasad.android_wallet_core;

public class BitCoinParams extends CoinType {


    public BitCoinParams() {
        name = "Bitcoin";
        symbol = "BTC";
        bip44Index = 0;
        unitExponent = 8;

    }

    private final static BitCoinParams i = new BitCoinParams();

    public static BitCoinParams get() {
        return i;
    }

}
