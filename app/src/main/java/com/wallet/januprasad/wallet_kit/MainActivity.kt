package com.wallet.januprasad.wallet_kit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import com.coinome.crypto.CoinomeAddress
import com.coinome.crypto.wallet.CoinomeWallet
import com.coinome.crypto.wallet.bitcoin.BitCoinWallet
import org.bitcoinj.params.MainNetParams

class MainActivity : AppCompatActivity() {

    private lateinit var textViewLogger: TextView
    private lateinit var buttonCreateWallet: Button
    private lateinit var editTextPasscode: EditText
    private lateinit var editTextPath: EditText
    private lateinit var buttonAddress: Button
    private lateinit var progressBar: FrameLayout


    private var bitCoinWallet: BitCoinWallet? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewLogger = findViewById(R.id.text_view)
        buttonCreateWallet = findViewById(R.id.button)
        buttonAddress = findViewById(R.id.button_generate_address)
        editTextPasscode = findViewById(R.id.edit_text_passphrase)
        editTextPath = findViewById(R.id.edit_text_path)
        progressBar = findViewById(R.id.progress_bar)


        val appInstance = WalletApplication.instance
        var mnemonic: String?


        if (appInstance.mnemonic.isNullOrEmpty()) {
            append("going to create mnemonic enter passphrase to lock inside phone")
            editTextPasscode.visibility = VISIBLE
            buttonCreateWallet.text = "Create Fresh Wallet"
        } else {
            append("mnemonic already saved in phone")
            editTextPasscode.visibility = VISIBLE
            editTextPasscode.hint = "Enter Passphrase to Unlock Wallet"
            buttonCreateWallet.text = "Load Wallet"
        }

        buttonCreateWallet.setOnClickListener {

            if (appInstance.mnemonic.isNullOrEmpty()) {
                showProgress()
                append("wallet generating using this")
                mnemonic = CoinomeWallet.generateMnemonic()

                appInstance.passphrase = editTextPasscode.text.toString()
                appInstance.mnemonic = mnemonic

                bitCoinWallet = BitCoinWallet(mnemonic, editTextPasscode.text.toString(), MainNetParams.get())
                printDetails(bitCoinWallet)
            } else {

                mnemonic = appInstance.mnemonic
                if (editTextPasscode.text.toString() == appInstance.passphrase) {
                    bitCoinWallet = BitCoinWallet(mnemonic, appInstance.passphrase, MainNetParams.get())
                    append(mnemonic)
                    printDetails(bitCoinWallet)
                    showProgress()
                } else
                    append("Wrong passphrase | wallet failed to load")
            }


        }


//        buttonAddress.setOnClickListener {
//            if (bitCoinWallet != null) {
//                append(bitCoinWallet.getAddress(MainNetParams.get(), bitCoinWallet.masterKey))
//            }
//        }


        buttonAddress.setOnClickListener {


            if(editTextPath.text.toString().isNotEmpty()) {
                val rootKey = bitCoinWallet?.generateRootKey(editTextPath.text.toString().toUpperCase())!!
                if (bitCoinWallet != null){
                    bitCoinWallet?.publicKeysMap(rootKey)
                }

                append("generate m/44h/0h/"+editTextPath.text.toString().toLowerCase()+" up to account hardened keys")


                var list: ArrayList<CoinomeAddress> = bitCoinWallet?.getAddressList(rootKey, 10)!!
                val iterate = list.listIterator()
                while (iterate.hasNext()) {
                    val address = iterate.next()
                    append(" at " + address.path + " address " + address.ecKey.toAddress(MainNetParams.get()).toString())
                }
            }

        }


    }

    private fun showProgress() {
        progressBar.visibility = VISIBLE
        Handler().postDelayed({
            progressBar.visibility = GONE
        },3000)
    }

    private fun printDetails(bitcoinWallet: BitCoinWallet?) {
        if (bitcoinWallet != null) {
            append("wallet genreation success")
            append("wallet masterkey :")
            append(bitcoinWallet.masterKeyString)
            buttonCreateWallet.isEnabled = false
            editTextPasscode.isEnabled = false
            editTextPath.visibility = VISIBLE
            buttonAddress.visibility = VISIBLE
        }
    }

    private fun append(s: String?) {
        textViewLogger.append(s)
        textViewLogger.append("\n")
        textViewLogger.append("\n")
    }
}
