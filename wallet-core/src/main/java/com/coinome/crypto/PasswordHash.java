package com.coinome.crypto;

import android.content.Context;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


/**
 * Created by jenuprasad on 5/9/16.
 */
public class PasswordHash {
    public static String generatePasswordHash(Context c, String pin) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (getSalt(c) == null) {
            Log.v(PasswordHash.class.getName(), "Salt is NULL");
            return "0000";
        }
        byte[] storedSalt = getSalt(c);
        return Passwords.hash(pin.toCharArray(), storedSalt);
    }

    private static byte[] getSalt(Context c) {
        return Passwords.getNextSalt();
    }

}
