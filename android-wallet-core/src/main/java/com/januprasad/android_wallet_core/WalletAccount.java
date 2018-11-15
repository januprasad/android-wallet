package com.januprasad.android_wallet_core;

public interface WalletAccount {
    Value getBalance();

    /**
     * Returns the address used for change outputs. Note: this will probably go away in future.
     */
    BitCoinAddress getChangeAddress();

    /**
     * Get current receive address, does not mark it as used.
     */
    BitCoinAddress getReceiveAddress();


}
