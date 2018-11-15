package com.januprasad.android_wallet_core;

import android.annotation.SuppressLint;

import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.KeyChain;

import static android.support.v4.util.Preconditions.checkNotNull;
import static org.bitcoinj.wallet.KeyChain.KeyPurpose.CHANGE;
import static org.bitcoinj.wallet.KeyChain.KeyPurpose.RECEIVE_FUNDS;

public class WalletHD extends WalletBase {

    private final HDKeyChain keys;

    public WalletHD(DeterministicKey rootKey, CoinType coinType) {
        this(new HDKeyChain(rootKey), coinType);
    }

    @SuppressLint("RestrictedApi")
    WalletHD(HDKeyChain keys, CoinType coinType) {
        super(coinType);
        this.keys = checkNotNull(keys);
    }

    @Override
    public Value getBalance() {
        return null;
    }

    @Override
    public BitCoinAddress getChangeAddress() {
        return currentAddress(CHANGE);
    }

    private BitCoinAddress currentAddress(KeyChain.KeyPurpose keyPurpose) {

        lock.lock();
        try {
            return BitCoinAddress.from(type, keys.getCurrentUnusedKey(keyPurpose));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BitCoinAddress getReceiveAddress() {
        return currentAddress(RECEIVE_FUNDS);
    }

    public String getPublicKeySerialized() {
        // Change the path of the key to match the BIP32 paths i.e. 0H/<account index>H
        DeterministicKey key = keys.getWatchingKey();
        ImmutableList<ChildNumber> path = ImmutableList.of(key.getChildNumber());
        key = new DeterministicKey(path, key.getChainCode(), key.getPubKeyPoint(), null, null);
        return key.serializePubB58();
    }

}
