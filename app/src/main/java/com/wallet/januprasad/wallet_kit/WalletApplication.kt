package com.wallet.januprasad.wallet_kit

import android.app.Application
import android.preference.PreferenceManager

class WalletApplication : Application() {

    private val PASSPHRASE_KEY = "passphrase"
    private val MNEMONIC_KEY = "mnemonic"


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: WalletApplication
            private set
    }

    var passphrase: String?
        get() {
            return getKey(PASSPHRASE_KEY)
        }
        set(value: String?) {
            setKey(PASSPHRASE_KEY,value)
        }

    var mnemonic: String?
        get() {
            return getKey(MNEMONIC_KEY)
        }
        set(value: String?) {
            setKey(MNEMONIC_KEY,value)
        }

    private fun getKey(passphrase: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(passphrase, "")
    }



    private fun setKey(passphrase: String, value: String?) {

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString(passphrase, value)
        editor.apply()

    }


}
