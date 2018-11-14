package com.januprasad.android_wallet_core;

public class BitCoinParams extends CoinType {


    private final static BitCoinParams i = new BitCoinParams();

    public static BitCoinParams get() {
        return i;
    }

}
