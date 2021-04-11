package io.sen.canteenia.configuration;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PaytmHashConfig {
    private static final String ALGTHM_TYPE_AES = "AES";
    private static final String ALGTHM_CBC_PAD_AES = "AES/CBC/PKCS5PADDING";
    private static final String ALGTHM_PROVIDER_BC = "SunJCE";
    private static final byte[] ivParamBytes = new byte[]{64, 64, 64, 64, 38, 38, 38, 38, 35, 35, 35, 35, 36, 36, 36, 36};

    public PaytmHashConfig() {
    }

    public static String encrypt(String input, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String encryptedValue = "";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        cipher.init(1, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(ivParamBytes));
        byte[] baseEncodedByte = Base64.getEncoder().encode(cipher.doFinal(input.getBytes()));
        encryptedValue = new String(baseEncodedByte);
        return encryptedValue;
    }

    public static String decrypt(String input, String key) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, IOException, BadPaddingException, IllegalBlockSizeException {
        String decryptedValue = "";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        cipher.init(2, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(ivParamBytes));
        byte[] baseDecodedByte = Base64.getDecoder().decode(input);
        decryptedValue = new String(cipher.doFinal(baseDecodedByte));
        return decryptedValue;
    }

    public static String generateSignature(TreeMap<String, String> params, String key) throws Exception {
        return generateSignature(getStringByParams(params), key);
    }

    public static String generateSignature(String params, String key) throws Exception {
        String salt = generateRandomString(4);
        return calculateChecksum(params, key, salt);
    }

    public static boolean verifySignature(TreeMap<String, String> params, String key, String checksum) throws Exception {
        return verifySignature(getStringByParams(params), key, checksum);
    }

    public static boolean verifySignature(String params, String key, String checksum) throws Exception {
        String paytm_hash = decrypt(checksum, key);
        String salt = paytm_hash.substring(paytm_hash.length() - 4);
        String calculatedHash = calculateHash(params, salt);
        return paytm_hash.equals(calculatedHash);
    }

    private static String generateRandomString(int length) {
        String ALPHA_NUM = "9876543210ZYXWVUTSRQPONMLKJIHGFEDCBAabcdefghijklmnopqrstuvwxyz!@#$&_";
        StringBuilder random = new StringBuilder(length);

        for(int i = 0; i < length; ++i) {
            int ndx = (int)(Math.random() * (double)ALPHA_NUM.length());
            random.append(ALPHA_NUM.charAt(ndx));
        }

        return random.toString();
    }

    private static String getStringByParams(TreeMap<String, String> params) {
        if (params == null) {
            return "";
        } else {
            Set<String> keys = params.keySet();
            StringBuilder string = new StringBuilder();
            TreeSet<String> parameterSet = new TreeSet(keys);
            Iterator var4 = parameterSet.iterator();

            while(var4.hasNext()) {
                String paramName = (String)var4.next();
                String value = params.get(paramName) != null && ((String)params.get(paramName)).toLowerCase() != "null" ? (String)params.get(paramName) : "";
                string.append(value).append("|");
            }

            return string.substring(0, string.length() - 1);
        }
    }

    private static String calculateChecksum(String params, String key, String salt) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
        String hashString = calculateHash(params, salt);
        String checksum = encrypt(hashString, key);
        if (checksum != null) {
            checksum = checksum.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        }

        return checksum;
    }

    private static String calculateHash(String params, String salt) throws NoSuchAlgorithmException {
        String finalString = params + "|" + salt;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        Formatter hash = new Formatter();
        byte[] var5 = messageDigest.digest(finalString.getBytes());
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            byte b = var5[var7];
            hash.format("%02x", b);
        }

        return hash.toString().concat(salt);
    }
}
