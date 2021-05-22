package com.mad1.myapplication.security;
//   private static final String KEY = "1Hbfh667adfDEJ78";
import android.util.Base64;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "2Abff781avmPEK73";
    public static String encrypt(String input) throws Exception
    {
        Key key = keyGenerator();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encrypted_value_in_bytes = cipher.doFinal(input.getBytes
                ("UTF-8"));
        String base64_encrypted_value = Base64.encodeToString(encrypted_value_in_bytes,
                Base64.DEFAULT);
        return base64_encrypted_value;
    }

    public static String decrypt(String received_encrypted_value) throws Exception
    {
        Key key = keyGenerator();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] base64_decrypted_value = Base64.decode(received_encrypted_value, Base64.DEFAULT);
        byte [] decrypted_value_in_bytes = cipher.doFinal(base64_decrypted_value);
        String decrypted_val = new String(decrypted_value_in_bytes,"UTF-8");
        return decrypted_val;
    }

    private static Key keyGenerator() throws Exception
    {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[]bytes = KEY.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[]key = digest.digest();
        Key secret_key = new SecretKeySpec(key,ALGORITHM);
        return secret_key;
    }
}
