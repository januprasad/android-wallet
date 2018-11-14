
package com.coinome.crypto.txn;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScriptPubKey {

    @SerializedName("asm")
    @Expose
    private String asm;
    @SerializedName("hex")
    @Expose
    private String hex;
    @SerializedName("reqSigs")
    @Expose
    private Double reqSigs;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Double getReqSigs() {
        return reqSigs;
    }

    public void setReqSigs(Double reqSigs) {
        this.reqSigs = reqSigs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

}
