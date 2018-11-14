package com.coinome.crypto.wallet.bitcoin;


import android.renderscript.Script;

import com.coinome.crypto.CoinomeAddress;
import com.coinome.crypto.bips.BitcoinBIP44;
import com.coinome.crypto.txn.CoinomeTxn;
import com.coinome.crypto.txn.Vin;
import com.coinome.crypto.txn.Vout;
import com.coinome.crypto.wallet.BaseWallet;
import com.coinome.crypto.wallet.CoinomeWallet;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.params.UnitTestParams;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptChunk;
import org.bitcoinj.signers.LocalTransactionSigner;
import org.bitcoinj.signers.MissingSigResolutionSigner;
import org.bitcoinj.signers.TransactionSigner;
import org.bitcoinj.wallet.BasicKeyChain;
import org.bitcoinj.wallet.DecryptingKeyBag;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.RedeemData;
import org.spongycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;


public class BitCoinWallet extends CoinomeWallet {

    private DeterministicKey rootKey;
    public DeterministicKey externalKey;
    private DeterministicKey internalKey;
    private DeterministicHierarchy hierarchy;
    private NetworkParameters params;

    public BitCoinWallet(String mnemonic, String passphrase, NetworkParameters networkParameters) {
        super(mnemonic, passphrase);
        this.params = networkParameters;

    }

    public String getMasterKeyString() {
        Preconditions.checkNotNull(masterKey, ifNotSet("masterKey"));
        return masterKey.serializePrivB58(MainNetParams.get());
    }


    public DeterministicKey generateRootKey() throws Exception {
        Preconditions.checkNotNull(masterKey, ifNotSet("masterKey"));
        hierarchy = new DeterministicHierarchy(masterKey);

        /**
         * this can also be use for create key/ from bitcoinj
         */
        List<ChildNumber> keyPath = getBip44Path();
        return hierarchy.get(keyPath, false, true);
    }

    @Override
    public ImmutableMap<String, String> publicKeysMap(DeterministicKey rootKey) {
        // bitcoin keys - public
        Preconditions.checkNotNull(rootKey, ifNotSet("rootKey"));
        externalKey = HDKeyDerivation.deriveChildKey(rootKey, ChildNumber.ZERO);
        internalKey = HDKeyDerivation.deriveChildKey(rootKey, ChildNumber.ONE);
        return ImmutableMap.of(
                "internal", internalKey.serializePubB58(MainNetParams.get()),
                "external", externalKey.serializePubB58(MainNetParams.get())
//                "internal", internalKey.getPublicKeyAsHex(),
//                "external", externalKey.getPublicKeyAsHex()
        );

    }


    public ArrayList<CoinomeAddress> getAddressList(DeterministicKey cKey, int count) {

        ArrayList<CoinomeAddress> addresses = new ArrayList<>();
        ECKey ecKey;
        for (int i = 0; i < count; i++) {

            DeterministicKey dk = HDKeyDerivation.deriveChildKey(cKey, new ChildNumber(i, false));
            // compressed WIF private key format
            if (dk.hasPrivKey()) {
//            byte[] prepended0Byte = ArrayUtils.addAll(new byte[1], dk.getPrivKeyBytes());
                byte[] getPrivKeyBytes = dk.getPrivKeyBytes();
                byte[] prepended0Byte = new byte[1 + getPrivKeyBytes.length];
                prepended0Byte[0] = 0;
                System.arraycopy(getPrivKeyBytes, 0, prepended0Byte, 1, getPrivKeyBytes.length);

                ecKey = ECKey.fromPrivate(new BigInteger(prepended0Byte), true);
            } else {
                ecKey = ECKey.fromPublicOnly(dk.getPubKey());
            }
            CoinomeAddress address = new CoinomeAddress(ecKey, dk.getPathAsString());
            addresses.add(address);
            System.out.println("pub "+ecKey.getPublicKeyAsHex());
            System.out.println("priv "+ecKey.getPrivateKeyAsWiF(params));
            System.out.println("add "+ecKey.toAddress(params).toString() + " " + dk.getPathAsString());
            System.out.println("----");
        }

        return addresses;
    }

//    int index = 0;
//
//    public String getAddress(DeterministicKey cKey) {
//
//        ECKey ecKey;
//
//        DeterministicKey dk = HDKeyDerivation.deriveChildKey(cKey, new ChildNumber(index, false));
//        // compressed WIF private key format
//        if (dk.hasPrivKey()) {
////            byte[] prepended0Byte = ArrayUtils.addAll(new byte[1], dk.getPrivKeyBytes());
//            byte[] getPrivKeyBytes = dk.getPrivKeyBytes();
//            byte[] prepended0Byte = new byte[1 + getPrivKeyBytes.length];
//            prepended0Byte[0] = 0;
//            System.arraycopy(getPrivKeyBytes, 0, prepended0Byte, 1, getPrivKeyBytes.length);
//
//            ecKey = ECKey.fromPrivate(new BigInteger(prepended0Byte), true);
//        } else {
//            ecKey = ECKey.fromPublicOnly(dk.getPubKey());
//        }
//        System.out.println(ecKey.toAddress(UnitTestParams.get()).toString() + " " + dk.getPathAsString());
//
//        index++;
//        return dk.getPathAsString() + " " + ecKey.toAddress(UnitTestParams.get()).toString();
//    }


    public String getAddressList(DeterministicKey rootKey) {
        BasicKeyChain chain = new BasicKeyChain();
        chain.importKeys(ImmutableList.of(rootKey));
        ECKey key1 = chain.getKey(KeyChain.KeyPurpose.RECEIVE_FUNDS);
        System.out.println(key1.toAddress(UnitTestParams.get()).toString());
        return key1.toAddress(UnitTestParams.get()).toString();
    }

    /**
     * Will change this fn
     *
     * @param msg
     * @param rootKey
     * @return
     */
    public String signString(String msg, DeterministicKey rootKey) {
        BasicKeyChain chain = new BasicKeyChain();
        chain.importKeys(ImmutableList.of(rootKey));
        ECKey key1 = chain.getKey(KeyChain.KeyPurpose.RECEIVE_FUNDS);
        return key1.signMessage(msg);
    }

    public void signBitcoinTest(CoinomeTxn transactionObject, ECKey key)
    {

        Transaction transaction = new Transaction(params);

    }


    private static String HexSerialize(org.bitcoinj.core.Transaction transaction) {
        final StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            transaction.bitcoinSerialize(os);
            byte[] bytes = os.toByteArray();
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }

            return sb.toString();
        } catch (IOException e) {
            return "Couldn't serialize to hex string.";
        } finally {
            formatter.close();
        }
    }


    public void verifyTxn(String hash, ECKey ecKey) {


    }

    private List<ChildNumber> getBip44Path() throws Exception {
        /**
         * Currently bitcoin bip path only
         */

//        return ImmutableList.of(ChildNumber.ZERO);
        return BitcoinBIP44.getBip44Path();
    }

    @Override
    public void sign(CoinomeTxn transaction) {

    }
}
