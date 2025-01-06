package xruisu.project.mpc.data.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHandler {

    private static final byte[] KEY = "1234567890123456".getBytes();
    private static final String ALGORITHM = "AES";

    protected static byte[] encrypt(String data) throws Exception {
        SecretKey key = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    protected static String decrypt(byte[] encryptedData) throws Exception {
        SecretKey key = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }
}
