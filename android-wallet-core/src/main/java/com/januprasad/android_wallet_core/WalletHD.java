package com.januprasad.android_wallet_core;

import android.support.annotation.Nullable;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.KeyCrypter;

import static android.support.v4.util.Preconditions.checkNotNull;

public class WalletHD {

    public WalletHD(DeterministicKey rootKey, CoinType coinType,
                          @Nullable KeyCrypter keyCrypter, @Nullable KeyParameter key) {
        this(new HDKeyChain(rootKey, keyCrypter, key), coinType);
    }

    WalletHD(HDKeyChain keys, CoinType coinType) {
        this(KeyUtils.getPublicKeyId(coinType, keys.getRootKey().getPubKey()), keys, coinType);
    }

    WalletHD(String id, HDKeyChain keys, CoinType coinType) {
        super(checkNotNull(coinType), id);
        this.keys = checkNotNull(keys);
    }
}
