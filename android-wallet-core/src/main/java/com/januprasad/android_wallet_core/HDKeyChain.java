package com.januprasad.android_wallet_core;

import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.utils.Threading;
import org.bitcoinj.wallet.BasicKeyChain;
import org.bitcoinj.wallet.KeyChain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkState;
import static org.bitcoinj.wallet.DeterministicKeyChain.EXTERNAL_PATH;
import static org.bitcoinj.wallet.DeterministicKeyChain.INTERNAL_PATH;

public class HDKeyChain {

    private final ReentrantLock lock = Threading.lock("KeyChain");
    private final DeterministicHierarchy hierarchy;
    private final DeterministicKey rootKey;

    private DeterministicKey externalKey, internalKey;

    private int createdExternalKeys, createdInternalKeys;
    BasicKeyChain basicKeyChain;

    public HDKeyChain(DeterministicKey rootKey) {
        this.rootKey = rootKey;
        hierarchy = new DeterministicHierarchy(rootKey);
        basicKeyChain =  new BasicKeyChain();
        setExternalKey(EXTERNAL_PATH);
        setInternalKey(INTERNAL_PATH);
    }

    private void setInternalKey(ImmutableList<ChildNumber> internalPath) {
        internalKey = importKeys(internalPath);
    }

    private void setExternalKey(ImmutableList<ChildNumber> externalPath) {
        externalKey = importKeys(externalPath);
    }

    private DeterministicKey importKeys(ImmutableList<ChildNumber> path) {
        DeterministicKey key = hierarchy.get(path, true, false);
        hierarchy.putKey(key);
        basicKeyChain.importKey(key);
        return key;
    }

    public DeterministicKey getWatchingKey() {
        return rootKey.getPubOnly();
    }

    public DeterministicKey getCurrentUnusedKey(KeyChain.KeyPurpose purpose) {
        lock.lock();
        try {
            List<DeterministicKey> keys = null;
            switch (purpose) {
                case RECEIVE_FUNDS:
                case REFUND:
                    keys = getDeterministicKeys(1, externalKey, createdExternalKeys + 1);
                    break;
                case CHANGE:
                    keys = getDeterministicKeys(1, internalKey, createdInternalKeys + 1);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }

            return keys.get(0);
        } finally {
            lock.unlock();
        }
    }

    private List<DeterministicKey> getDeterministicKeys(int numberOfKeys, DeterministicKey parentKey, int index) {
        lock.lock();
        try {
            // Optimization: potentially do a very quick key generation for just the number of keys we need if we
            // didn't already create them, ignoring the configured lookahead size. This ensures we'll be able to
            // retrieve the keys in the following loop, but if we're totally fresh and didn't get a chance to
            // calculate the lookahead keys yet, this will not block waiting to calculate 100+ EC point multiplies.
            // On slow/crappy Android phones looking ahead 100 keys can take ~5 seconds but the OS will kill us
            // if we block for just one second on the UI thread. Because UI threads may need an address in order
            // to render the screen, we need getKeys to be fast even if the wallet is totally brand new and lookahead
            // didn't happen yet.
            //
            // It's safe to do this because when a network thread tries to calculate a Bloom filter, we'll go ahead
            // and calculate the full lookahead zone there, so network requests will always use the right amount.
            List<DeterministicKey> lookahead = maybeLookAhead(parentKey, index, 0, 0);
            basicKeyChain.importKeys(lookahead);
            List<DeterministicKey> keys = new ArrayList<DeterministicKey>(numberOfKeys);
            for (int i = 0; i < numberOfKeys; i++) {

                ImmutableList<ChildNumber> path = HDUtils.append(parentKey.getPath(),
                        new ChildNumber(index - numberOfKeys + i, false));
                System.out.println("bip 44 "+path.toString());
                keys.add(hierarchy.get(path, false, false));
            }
            return keys;
        } finally {
            lock.unlock();
        }
    }
    private List<DeterministicKey> maybeLookAhead(DeterministicKey parent, int issued, int lookaheadSize, int lookaheadThreshold) {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        final int numChildren = hierarchy.getNumChildren(parent.getPath());
        final int needed = issued + lookaheadSize + lookaheadThreshold - numChildren;

        if (needed <= lookaheadThreshold)
            return new ArrayList<DeterministicKey>();



        List<DeterministicKey> result  = new ArrayList<DeterministicKey>(needed);
        long now = System.currentTimeMillis();
        int nextChild = numChildren;
        for (int i = 0; i < needed; i++) {
            DeterministicKey key = HDKeyDerivation.deriveThisOrNextChildKey(parent, nextChild);
            key = key.getPubOnly();
            hierarchy.putKey(key);
            result.add(key);
            nextChild = key.getChildNumber().num() + 1;
        }
        return result;
    }

}
