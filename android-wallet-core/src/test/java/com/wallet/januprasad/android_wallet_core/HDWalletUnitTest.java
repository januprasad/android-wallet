package com.wallet.januprasad.android_wallet_core;

import com.januprasad.android_wallet_core.BitCoinParams;
import com.januprasad.android_wallet_core.CoinType;
import com.januprasad.android_wallet_core.Utils;
import com.januprasad.android_wallet_core.Wallet;
import com.januprasad.android_wallet_core.WalletHD;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;
import org.junit.Test;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HDWalletUnitTest {

    static final CoinType BTC = BitCoinParams.get();


    @Test
    public void createWalletHD() {
        String mnemonic = "base car permit empower jewel wild wagon strategy slice tribe universe combine";
        try {
            Wallet wallet = new Wallet(mnemonic, null);
            WalletHD account = new WalletHD(wallet.getWalletRootKey(BTC), BTC);
            assertEquals("xpub67tVq9TLPPoaJV4WCZLgGLY1ZGY9iPEnTYuSxrFMcpicwKkQPwEz8znhsZ8tozwTjTB9JnqB7Pevyk1CzmspbCTWMmUQpUKgUbQJfU1gk2V",
                    account.getPublicKeySerialized());
        } catch (MnemonicException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void xpubWallet() {
        String xpub = "xpub67tVq9TLPPoaJV4WCZLgGLY1ZGY9iPEnTYuSxrFMcpicwKkQPwEz8znhsZ8tozwTjTB9JnqB7Pevyk1CzmspbCTWMmUQpUKgUbQJfU1gk2V";
        DeterministicKey rootKey = DeterministicKey.deserializeB58(null, xpub);
        WalletHD account = new WalletHD(rootKey, BTC);
        assertEquals("xpub67tVq9TLPPoaJV4WCZLgGLY1ZGY9iPEnTYuSxrFMcpicwKkQPwEz8znhsZ8tozwTjTB9JnqB7Pevyk1CzmspbCTWMmUQpUKgUbQJfU1gk2V", account.getPublicKeySerialized());
        assertEquals("", account.getReceiveAddress());

    }

}