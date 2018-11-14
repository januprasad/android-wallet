
package com.coinome.crypto.txn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.params.TestNet3Params;

public class Vout {

    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("n")
    @Expose
    private Double n;
    @SerializedName("scriptPubKey")
    @Expose
    private ScriptPubKey scriptPubKey;

    public Vout(TestNet3Params testNet3Params, Double vout, Sha256Hash hash) {


    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getN() {
        return n;
    }

    public void setN(Double n) {
        this.n = n;
    }

    public ScriptPubKey getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(ScriptPubKey scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

}
