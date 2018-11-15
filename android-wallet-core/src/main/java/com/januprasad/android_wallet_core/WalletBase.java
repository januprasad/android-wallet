package com.januprasad.android_wallet_core;

import org.bitcoinj.utils.Threading;

import java.util.concurrent.locks.ReentrantLock;

public abstract class WalletBase implements WalletAccount {
    protected final ReentrantLock lock = Threading.lock("AbstractWallet");
    protected final CoinType type;
    WalletBase(CoinType coinType){
        this.type = coinType;
    }
}
