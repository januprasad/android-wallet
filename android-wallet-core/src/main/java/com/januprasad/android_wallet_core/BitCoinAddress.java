package com.januprasad.android_wallet_core;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.WrongNetworkException;
import org.bitcoinj.crypto.DeterministicKey;

public class BitCoinAddress extends Address implements GenericAddress {

    public BitCoinAddress(NetworkParameters params, int version, byte[] hash160) throws WrongNetworkException {
        super(params, version, hash160);
    }

    @Override
    public CoinType getType() {
        return null;
    }

    public static BitCoinAddress from(CoinType type, DeterministicKey currentUnusedKey) {


        return null;

    }
}
