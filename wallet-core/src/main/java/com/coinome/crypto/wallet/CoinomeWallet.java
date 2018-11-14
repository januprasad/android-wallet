package com.coinome.crypto.wallet;


import com.coinome.crypto.txn.CoinomeTxn;
import com.google.common.collect.ImmutableMap;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class CoinomeWallet {

    protected final DeterministicKey masterKey;
//    protected DeterministicKey walletKey;
    private static final int ENTROPY_BITS = 128;
    protected byte seed[];
    public abstract ImmutableMap<String, String> publicKeysMap(DeterministicKey rootKey);

    abstract public void sign(CoinomeTxn transaction);

    protected CoinomeWallet(String mnemonic, String passphrase) {
        checkNotNull(mnemonic, ifNotSet("mnemonic"));
        checkNotNull(passphrase, ifNotSet("passphrase"));
        List<String> words = stringToWords(mnemonic);
        seed = MnemonicCode.toSeed(words, passphrase);
        this.masterKey = HDKeyDerivation.createMasterPrivateKey(seed);
    }

    public static String generateMnemonic() throws MnemonicException.MnemonicLengthException {
        int entropyLen = ENTROPY_BITS / 8;
        byte[] entropy = generateEntropy(entropyLen);
        List<String> words = MnemonicCode.INSTANCE.toMnemonic(entropy);
        String mnemonic = join(" ", words);
        return mnemonic;
    }

    private static byte[] generateEntropy(int entropyLen) {
        byte[] entropy = new byte[entropyLen];
        SecureRandom random = new SecureRandom();
        random.nextBytes(entropy);
        return entropy;
    }

    private List<String> stringToWords(String mnemonic) {
        List<String> words = new ArrayList<>();
        for (String w : mnemonic.trim().split(" ")) {
            if (w.length() > 0) {
                words.add(w);
            }
        }
        return words;
    }



    private static String join(String d, List<String> listArray) {
        StringBuilder sb = new StringBuilder();
        for (String s : listArray) {
            sb.append(s);
            sb.append(d);
        }
        return sb.toString().trim();
    }



    public static class NotImplemented extends Throwable {
        private final String className;

        public NotImplemented(String className) {
            this.className = className;
        }

        public String getMessage() {
            return String.format("CoinomeWallet class '%s' is not implemented", className);
        }
    }

    protected String ifNotSet(String fieldName) {
        String className = this.getClass().getName();
        return String.format("No value set for %s in %s", fieldName, className);
    }


}
