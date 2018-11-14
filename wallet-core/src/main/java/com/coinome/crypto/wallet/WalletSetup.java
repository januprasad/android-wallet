package com.coinome.crypto.wallet;

import com.coinome.crypto.wallet.wallettype.WalletType;

import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class WalletSetup {

    private final DeterministicKey masterKey;
    private static final int ENTROPY_BITS = 128;

    protected WalletSetup(String mnemonic, String passphrase) {
        checkNotNull(mnemonic, ifNotSet("mnemonic"));
        checkNotNull(passphrase, ifNotSet("passphrase"));
        List<String> words = stringToWords(mnemonic);
        byte seed[] = MnemonicCode.toSeed(words, passphrase);
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

    protected String ifNotSet(String fieldName) {
        String className = this.getClass().getName();
        return String.format("No value set for %s in %s", fieldName, className);
    }

    private static String join(String d, List<String> listArray) {
        StringBuilder sb = new StringBuilder();
        for (String s : listArray) {
            sb.append(s);
            sb.append(d);
        }
        return sb.toString().trim();
    }



}
