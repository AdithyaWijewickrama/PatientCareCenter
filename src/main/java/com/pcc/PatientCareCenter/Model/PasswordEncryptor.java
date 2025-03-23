package com.pcc.PatientCareCenter.Model;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordEncryptor {
    public static final String MASTER="YourSecureMasterPassword123!";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 16;

    public static String encrypt(String password) throws Exception {
        return encrypt(password,MASTER);
    }

    public static String decrypt(String encryptedData) throws Exception {
        return decrypt(encryptedData,MASTER);
    }

    public static String encrypt(String password, String masterPassword) throws Exception {
        SecureRandom secureRandom = new SecureRandom();

        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        byte[] iv = new byte[IV_LENGTH];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encrypted = cipher.doFinal(password.getBytes());

        return Base64.getEncoder().encodeToString(salt) + ":" +
                Base64.getEncoder().encodeToString(iv) + ":" +
                Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData, String masterPassword) throws Exception {
        String[] parts = encryptedData.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] iv = Base64.getDecoder().decode(parts[1]);
        byte[] encrypted = Base64.getDecoder().decode(parts[2]);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);
    }
    public static void main(String[] args) throws Exception {
        String masterPassword = "YourSecureMasterPassword123!"; // Store securely!
        String originalPassword = "1234";

        // Encrypt
        String encrypted;
        encrypted = encrypt(originalPassword, masterPassword);
        System.out.println("Encrypted: " + encrypted);
        encrypted = encrypt(originalPassword, masterPassword);
        System.out.println("Encrypted: " + encrypted);
        // Decrypt
        String decrypted = decrypt(encrypted, masterPassword);
        System.out.println("Decrypted: " + decrypted);
    }
}