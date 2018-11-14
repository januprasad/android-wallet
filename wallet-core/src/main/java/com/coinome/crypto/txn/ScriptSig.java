
package com.coinome.crypto.txn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScriptSig {

    @SerializedName("asm")
    @Expose
    private String asm;
    @SerializedName("hex")
    @Expose
    private String hex;

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

}
