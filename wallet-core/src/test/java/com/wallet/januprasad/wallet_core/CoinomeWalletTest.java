package com.wallet.januprasad.wallet_core;

import com.coinome.crypto.wallet.bitcoin.BitCoinWallet;

import org.bitcoinj.crypto.MnemonicException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class CoinomeWalletTest {
    final String mnemonic = "obvious exhibit cloud amazing junk love head digital trust clinic fabric choice";
    BitCoinWallet bitcoinWallet;
    @Before
    public void setup() {
        bitcoinWallet = new BitCoinWallet(mnemonic,"");
    }

    @Test
    public void mnemonic_12_digits() throws MnemonicException.MnemonicLengthException {
        assertEquals(12, BitCoinWallet.generateMnemonic().split(" ").length);
    }

    @Test
    public void check_same_master_key1() {
        assertEquals("xprv9s21ZrQH143K4PhLWtZLgcRTSQ49UaEZXEpSaFhwB8zF4CeqexSugU4fCe9n7w1C2e84KaTznprahX8j7XALTymK7wKJKPRxM18dqZADPNp", bitcoinWallet.getMasterKeyString());
    }

    @Test
    public void check_same_master_key2() {
        bitcoinWallet = new BitCoinWallet(mnemonic,"123456");
        assertEquals("xprv9s21ZrQH143K24yQtR9rZh3AYBQkdq7jyqxSzv1PpDMuzqzzbqcjwG8t5abtFgZrGH1yHJrt57Dzo8jkGe86BdQSa5AM8QfwWzPFgBLmjRP", bitcoinWallet.getMasterKeyString());
    }

    @Test
    public void check_same_master_key3() {
        BitCoinWallet bitcoinWallet = new BitCoinWallet(mnemonic,"11220b22aaec7e8c76795f546a238ed2fc79f01290d3dd627bf6ab1aa2ebf730");
        assertEquals("xprv9s21ZrQH143K3NWgQgPf3QhG78n2utco5USzPyDvPXsuSpBY63ZiGhYohKT3KzKBtWd7Ei3RfqzecchNYFWmuWBUV6yrEjQ4s7YkcZYa4q4", bitcoinWallet.getMasterKeyString());
    }

    @Test
    public void wrong_master_key() {
        bitcoinWallet = new BitCoinWallet(mnemonic,"1234567");
        assertNotSame("xprv9s21ZrQH143K24yQtR9rZh3AYBQkdq7jyqxSzv1PpDMuzqzzbqcjwG8t5abtFgZrGH1yHJrt57Dzo8jkGe86BdQSa5AM8QfwWzPFgBLmjRP", bitcoinWallet.getMasterKeyString());
    }

    @Test
    public void master_key_length_111() {
        assertEquals(111, bitcoinWallet.getMasterKeyString().length());
    }



    @Test
    public void public_keys_map_length_2() {
        assertEquals(2, bitcoinWallet.publicKeysMap(bitcoinWallet.generateRootKey()).size());
    }

    @Test
    public void check_address() {
        assertEquals("n2m5RMAbMX5iWHGTWSpcWZcvcyjB8UWfNG", bitcoinWallet.getAddressList(bitcoinWallet.generateRootKey()));
    }

    @Test
    public void sign_txn() {
        assertEquals("H2LsJk1V31QGKPzvyoyZD9Bzx1eLTCH74meeor+rpzzpBFxqOpwLQbqMtAya5TjuZNjMy8tmeMLqKqI40kIxZAA=", bitcoinWallet.signString("hello world",bitcoinWallet.generateRootKey()));
    }

    @Test
    public void transaction_maker() {

    }


}
