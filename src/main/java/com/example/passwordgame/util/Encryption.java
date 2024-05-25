// Utility class to encrypt and decrypt strings. Key is static, so not exactly secure
// However, in this use case it should be adequate
// Encryption algorithm: AES
package com.example.passwordgame.util;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class Encryption {
    // Private constructor to prevent instantiation
    private Encryption() {}

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    // Bad idea to hardcode
    private static final String KEY = "It's a random string key :) 8483";

    // Returns an encrypted String using AES Encryption
    public static String encrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM); // Create AES Cipher object

            // Create key
            byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            // Generate a random IV
            byte[] ivBytes = new byte[cipher.getBlockSize()];
            SecureRandom random = new SecureRandom();
            random.nextBytes(ivBytes);

            // Initialize cipher to encrypt string with key and IV
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));
            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));

            // Add IV and encrypted data and encode to Base64
            byte[] combined = new byte[ivBytes.length + encrypted.length];

            // Copy IV encrypted data and
            System.arraycopy(ivBytes, 0, combined, 0, ivBytes.length);
            System.arraycopy(encrypted, 0, combined, ivBytes.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch(Exception e) {
            e.printStackTrace();
            return null; // Return empty String if error occurs
        }
    }

    // Returns a decrypted String using Twofish
    public static String decrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            // Create key
            byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            // Decode and extract IV and encrypted data
            byte[] combined = Base64.getDecoder().decode(str);
            byte[] ivBytes = new byte[cipher.getBlockSize()];
            System.arraycopy(combined, 0, ivBytes, 0, cipher.getBlockSize());
            byte[] encrypted = new byte[combined.length - cipher.getBlockSize()];
            System.arraycopy(combined, cipher.getBlockSize(), encrypted, 0, encrypted.length);

            // Initialize cipher to decrypt with key and IV
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

            // Decrypt str
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return empty String if error occurs
        }
    }
}