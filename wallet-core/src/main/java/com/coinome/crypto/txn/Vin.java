
package com.coinome.crypto.txn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vin {

    @SerializedName("txid")
    @Expose
    private String txid;
    @SerializedName("vout")
    @Expose
    private Double vout;
    @SerializedName("scriptSig")
    @Expose
    private ScriptSig scriptSig;
    @SerializedName("sequence")
    @Expose
    private Double sequence;

    private String publicKey;
    private String derivationPath;


    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Double getVout() {
        return vout;
    }

    public void setVout(Double vout) {
        this.vout = vout;
    }

    public ScriptSig getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(ScriptSig scriptSig) {
        this.scriptSig = scriptSig;
    }

    public Double getSequence() {
        return sequence;
    }

    public void setSequence(Double sequence) {
        this.sequence = sequence;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getDerivationPath() {
        return derivationPath;
    }

    public void setDerivationPath(String derivationPath) {
        this.derivationPath = derivationPath;
    }
}
