package com.januprasad.android_wallet_core;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.KeyCrypter;
import org.spongycastle.crypto.params.KeyParameter;

import static android.support.v4.util.Preconditions.checkNotNull;

public class WalletHD {

    private final HDKeyChain keys;

    public WalletHD(DeterministicKey rootKey, CoinType coinType) {
        this(new HDKeyChain(rootKey), coinType);
    }

    @SuppressLint("RestrictedApi")
    WalletHD(HDKeyChain keys, CoinType coinType) {
        this.keys = checkNotNull(keys);
    }
}
