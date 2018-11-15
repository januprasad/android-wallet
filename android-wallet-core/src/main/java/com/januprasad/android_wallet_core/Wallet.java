package com.januprasad.android_wallet_core;

import android.support.annotation.Nullable;

import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.januprasad.android_wallet_core.Utils.join;

public class Wallet {
    private final DeterministicSeed seed;
    private final DeterministicKey masterKey;
    private final LinkedHashMap<CoinType, ArrayList<WalletAccount>> accountsByType;
    private final LinkedHashMap<String, WalletAccount> accounts;
    private static final int ENTROPY_BITS = 128;

    public Wallet(String mnemonic, String password) throws MnemonicException {
        this(Utils.parseMnemonic(mnemonic), password);
    }

    public Wallet(List<String> mnemonic) throws MnemonicException {
        this(mnemonic, null);
    }

    public Wallet(List<String> mnemonic, @Nullable String password) throws MnemonicException {
        MnemonicCode.INSTANCE.check(mnemonic);
        password = password == null ? "" : password;

        seed = new DeterministicSeed(mnemonic, null, password, 0);
        masterKey = HDKeyDerivation.createMasterPrivateKey(seed.getSeedBytes());
        accountsByType = new LinkedHashMap<>();
        accounts = new LinkedHashMap<>();
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

    private  DeterministicKey masterKey() {
        return masterKey;
    }

    public DeterministicKey getWalletRootKey(CoinType coinType) {
        if(coinType.name.equalsIgnoreCase(BitCoinParams.get().name))
        {
            DeterministicHierarchy hierarchy = new DeterministicHierarchy(masterKey());
            return hierarchy.get(coinType.getBip44Path(0), false, true);
        }

        return null;
    }
}
