package com.januprasad.android_wallet_core;

import org.bitcoinj.core.NetworkParameters;

public abstract class CoinType extends NetworkParameters {

    private static final String BIP_44_KEY_PATH = "44H/%dH/%dH";

    protected String name;
    protected String symbol;
    protected String uriScheme;
    protected Integer bip44Index;
    protected Integer unitExponent;
    protected String addressPrefix;

    @Override
    public String getPaymentProtocolId() {
        throw new RuntimeException("Method not implemented");
    }
}
