
package com.coinome.crypto.txn;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinomeTxn {

    @SerializedName("txid")
    @Expose
    private String txid;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("version")
    @Expose
    private Double version;
    @SerializedName("size")
    @Expose
    private Double size;
    @SerializedName("vsize")
    @Expose
    private Double vsize;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("locktime")
    @Expose
    private Double locktime;
    @SerializedName("vin")
    @Expose
    private List<Vin> vin = null;
    @SerializedName("vout")
    @Expose
    private List<Vout> vout = null;
    @SerializedName("hex")
    @Expose
    private String hex;
    @SerializedName("blockhash")
    @Expose
    private String blockhash;
    @SerializedName("confirmations")
    @Expose
    private Double confirmations;
    @SerializedName("time")
    @Expose
    private Double time;
    @SerializedName("blocktime")
    @Expose
    private Double blocktime;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getVsize() {
        return vsize;
    }

    public void setVsize(Double vsize) {
        this.vsize = vsize;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLocktime() {
        return locktime;
    }

    public void setLocktime(Double locktime) {
        this.locktime = locktime;
    }

    public List<Vin> getVin() {
        return vin;
    }

    public void setVin(List<Vin> vin) {
        this.vin = vin;
    }

    public List<Vout> getVout() {
        return vout;
    }

    public void setVout(List<Vout> vout) {
        this.vout = vout;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getBlockhash() {
        return blockhash;
    }

    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }

    public Double getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Double confirmations) {
        this.confirmations = confirmations;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getBlocktime() {
        return blocktime;
    }

    public void setBlocktime(Double blocktime) {
        this.blocktime = blocktime;
    }

}
