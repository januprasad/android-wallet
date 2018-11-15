package com.wallet.januprasad.android_wallet_core;

import com.januprasad.android_wallet_core.Wallet;

import junit.framework.Assert;

import org.bitcoinj.crypto.MnemonicException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WalletUnitTest {
    @Test
    public void create_test_wallet()
    {

        try {
            Wallet wallet = new Wallet(Wallet.generateMnemonic(),null);
            assertEquals("",wallet.masterKey());
        } catch (MnemonicException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void check_same_master_key1() {
        try {
            final String mnemonic = "obvious exhibit cloud amazing junk love head digital trust clinic fabric choice";
            Wallet wallet = new Wallet(mnemonic,null);
            assertEquals("xprv9s21ZrQH143K4PhLWtZLgcRTSQ49UaEZXEpSaFhwB8zF4CeqexSugU4fCe9n7w1C2e84KaTznprahX8j7XALTymK7wKJKPRxM18dqZADPNp", wallet.masterKey());
        } catch (MnemonicException e) {
            e.printStackTrace();
        }


       }

}