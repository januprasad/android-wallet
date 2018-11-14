package com.coinome.crypto;


import com.google.common.base.Joiner;
import com.google.common.util.concurrent.Futures;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

public class WalletApp {

    private static final String MNEMONIC = "toilet spray cargo girl car crop original milk census celery grace lonely";

    public static Wallet init(String seedCode) {

        long creationtime = 1409478661L;
        DeterministicSeed seed = null;
        try {
            seed = new DeterministicSeed(seedCode, null, "", creationtime);
            Wallet restoredWallet = Wallet.fromSeed(TestNet3Params.get(), seed);
            return restoredWallet;
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {

        Wallet wallet = WalletApp.init(WalletApp.MNEMONIC);
        NetworkParameters params = wallet.getParams();
        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = null;


        try {
            chain = new BlockChain(params, wallet, blockStore);
            PeerGroup peerGroup = new PeerGroup(params, chain);

            peerGroup.addPeerDiscovery(new DnsDiscovery(params));
            peerGroup.addWallet(wallet);


            peerGroup.start();
            peerGroup.downloadBlockChain();
            peerGroup.stop();

            peerGroup.downloadBlockChain();
            Coin balance = wallet.getBalance();
            System.out.println("CoinomeWallet balance: " + balance);
        } catch (BlockStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println("WALLET: " + wallet.currentReceiveAddress());
        System.out.println("WALLET BALANCE: " + wallet.getBalance().toFriendlyString());

    }
}
