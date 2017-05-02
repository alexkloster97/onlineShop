package by.bsuir.shop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password encrypter class
 */
public class PasswordEncrypter {

    /**
     * Encrypt string
     *
     * @param toEncrypt                     string to encrypt
     * @return                              encrypted string
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String encrypt(String toEncrypt) throws NoSuchAlgorithmException {
        byte[] bytes = computeHash(toEncrypt);
        StringBuffer sb = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++){
            int v = bytes[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }

        return sb.toString().toUpperCase();
    }

    /**
     * Encrypt string in SHA-1
     *
     * @param toEncrypt                     string to encrypt
     * @return                              encrypted byte array
     * @throws java.security.NoSuchAlgorithmException
     */
    private static byte[] computeHash(String toEncrypt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest =null;
        messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(toEncrypt.getBytes());

        return  messageDigest.digest();
    }
}
