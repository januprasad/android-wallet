package com.coinome.crypto;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 * A utility class to hash passwords and check passwords vs hashed values. It uses a combination of hashing and unique
 * salt. The algorithm used is PBKDF2WithHmacSHA1 which, although not the best for hashing password (vs. bcrypt) is
 * still considered robust and <a href="http://security.stackexchange.com/a/6415/12614"> recommended by NIST </a>.
 * The hashed value has 256 bits.
 */
public class Passwords {

    private static final Random random = new SecureRandom();
    private static final int ITERATIONS = 1024;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_SIZE = 20;

    /**
     * Returns a random salt to be used to hash a password.
     * @return a 16 bytes random salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     * @param password the password to be hashed
     * @param salt a 16 bytes salt, ideally obtained with the getNextSalt method
     * @return the hashed password with a pinch of salt
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String hash(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] pwd = cloneArrayAndEraseOriginal(password);
        KeySpec spec = new PBEKeySpec(pwd, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return toHex(f.generateSecret(spec).getEncoded());
    }


    private static char[] cloneArrayAndEraseOriginal(char[] password) {
        char[] pwd = password.clone();
        Arrays.fill(password, Character.MIN_VALUE);
        return pwd;
    }
    
    
    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array       the byte array to convert
     * @return              a length*2 character string encoding the byte array
     */
    public static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    public static byte[] toByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l/2];
        for (int i = 0; i < l; i += 2) {
            data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

}