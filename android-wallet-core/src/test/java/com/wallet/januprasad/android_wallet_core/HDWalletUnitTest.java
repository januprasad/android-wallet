package com.wallet.januprasad.android_wallet_core;

import com.januprasad.android_wallet_core.BitCoinParams;
import com.januprasad.android_wallet_core.CoinType;
import com.januprasad.android_wallet_core.WalletHD;

import org.bitcoinj.crypto.DeterministicKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HDWalletUnitTest {

    static final CoinType BTC = BitCoinParams.get();

    @Test
    public void xpubWallet() {
        String xpub = "xpub67tVq9TLPPoaHVSiYu8mqahm3eztTPUts6JUftNq3pZF1PJwjknrTqQhjc2qRGd6vS8wANu7mLnqkiUzFZ1xZsAZoUMi8o6icMhzGCAhcSW";
        DeterministicKey key = DeterministicKey.deserializeB58(null, xpub);
        WalletHD account = new WalletHD(key, BTC);
        assertEquals("1KUDsEDqSBAgxubSEWszoA9xscNRRCmujM", account.getReceiveAddress().toString());
    }

}