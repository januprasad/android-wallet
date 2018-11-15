package com.januprasad.android_wallet_core;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static ArrayList<String> parseMnemonic(String mnemonicString) {
        ArrayList<String> seedWords = new ArrayList<>();
        for (String word : mnemonicString.trim().split(" ")) {
            if (word.isEmpty()) continue;
            seedWords.add(word);
        }
        return seedWords;
    }
    public static String join(String d, List<String> listArray) {
        StringBuilder sb = new StringBuilder();
        for (String s : listArray) {
            sb.append(s);
            sb.append(d);
        }
        return sb.toString().trim();
    }
    public static List<String> stringToWords(String mnemonic) {
        List<String> words = new ArrayList<>();
        for (String w : mnemonic.trim().split(" ")) {
            if (w.length() > 0) {
                words.add(w);
            }
        }
        return words;
    }

}
