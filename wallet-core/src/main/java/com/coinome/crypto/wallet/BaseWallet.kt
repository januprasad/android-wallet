package com.coinome.crypto.wallet

import com.coinome.crypto.txn.CoinomeTxn
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.MnemonicCode
import com.google.common.base.Preconditions.checkNotNull
import com.google.common.collect.ImmutableMap
import java.util.ArrayList

abstract class BaseWallet protected constructor(mnemonic: String, passphrase: String)   {

    protected lateinit var masterKey: DeterministicKey
    abstract fun publicKeysMap(rootKey: DeterministicKey): ImmutableMap<String, String>
    abstract fun sign(transaction: CoinomeTxn)
    private val EMPTY = " "

    init {
        checkNotNull(mnemonic, ifNotSet("mnemonic"))
        checkNotNull(passphrase, ifNotSet("passphrase"))
        val words = stringToWords(mnemonic)
        val seed = MnemonicCode.toSeed(words, passphrase)
        this.masterKey = HDKeyDerivation.createMasterPrivateKey(seed)
    }

    protected fun ifNotSet(fieldName: String): String {
        val className = this.javaClass.name
        return String.format("No value set for %s in %s", fieldName, className)
    }

    private fun stringToWords(mnemonic: String): List<String> {
        val words = ArrayList<String>()
        for (w in mnemonic.trim { it <= ' ' }.split(EMPTY.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (w.isNotEmpty()) {
                words.add(w)
            }
        }
        return words
    }


}
