package arisryan.mobileeg.activity;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Aris Riyanto on 7/12/2017.
 */

public class Symmetric {
    private static int key_size = 32;
    private static int NONCE_SIZE = 12;
    private static int TAG_SIZE = 16;
    private static byte VERSION = 0x01;
    private static int HEADER_LENGTH = 29;
    //public static String salt = "44071f6d181561670bda728d43fb79b443bb805afdebaf98622b5165e01b15fb";
    //public static String jwtKey = "1YfnFYwdFp09AejiV2gvfU5+WRjn8exPCMP6KJPgssc=";

    public static byte[] GenerateKey()
    {
        SecureRandom sr = new SecureRandom();
        byte[] key = new byte[key_size];
        sr.nextBytes(key);
        return key;
    }

    public static String GenerateKeyString()
    {
        byte[] key = GenerateKey();
        String keystring = Base64.encodeToString(key,Base64.DEFAULT);
        return keystring;
    }

        public static String Encrypt(String key, String plaintext) {
        try {
            byte[] keybyte = Base64.decode(key, Base64.DEFAULT);
            byte[] plaintext1 = plaintext.getBytes(StandardCharsets.UTF_8);
            //Asymmetric toing = new Asymmetric();
            byte[] nonce = GenerateNonce();

            ByteArrayOutputStream ms = new ByteArrayOutputStream();
            IvParameterSpec iv = new IvParameterSpec(nonce);
            SecretKeySpec skeySpec = new SecretKeySpec(keybyte, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NOPADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(plaintext1);

            //ByteArrayInputStream ms1 =  new ByteArrayInputStream(encrypted1);
            //CipherInputStream cis = new CipherInputStream(ms1, cipher);
            CipherOutputStream cis1 = new CipherOutputStream(ms, cipher);
            cis1.write(plaintext1,0,plaintext1.length);
            cis1.flush();
            byte[] enkrip = ms.toByteArray();
            //byte[] encrypted = ms.toByteArray();

            //gettag
            byte[] gettag  = Tag();
            byte[] authenticationTag = getTag(keybyte, enkrip, iv.getIV(), gettag);
            String auth = Base64.encodeToString(authenticationTag, Base64.DEFAULT);
            Log.d("tag", auth);
            //end gettag

            byte[] record = new byte[HEADER_LENGTH + enkrip.length];

            int offset = 1;
            System.arraycopy(nonce, 0, record, offset, NONCE_SIZE);

            offset += NONCE_SIZE;
            System.arraycopy(authenticationTag, 0, record, offset, TAG_SIZE);

            offset += TAG_SIZE;
            System.arraycopy(enkrip, 0, record, offset, enkrip.length);

            String result = Base64.encodeToString(record, Base64.DEFAULT);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String Decrypt(String key, String cipher)
    {
        try {
            byte[] record = Base64.decode(cipher, Base64.DEFAULT);
            if(record[0] != VERSION)
            {
                int offset =1;
                byte[] nonce = new byte[NONCE_SIZE];
                System.arraycopy(record, 1, nonce, 0, NONCE_SIZE);

                offset += NONCE_SIZE;
                byte[] tag = new byte[TAG_SIZE];
                System.arraycopy(record, offset, tag, 0, TAG_SIZE);

                offset += TAG_SIZE;
                byte[] chipertext = new byte[record.length - HEADER_LENGTH];
                System.arraycopy(record, offset, chipertext, 0, record.length - HEADER_LENGTH);

                IvParameterSpec iv = new IvParameterSpec(nonce);
                SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
                Cipher plain = Cipher.getInstance("AES/GCM/NOPADDING");
                plain.init(Cipher.DECRYPT_MODE, skeySpec, iv);

                byte[] decrypted = plain.doFinal(chipertext);
                String result = new String(decrypted, StandardCharsets.UTF_8);
                return result;
            }
            else
            {

            }

        }
       catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
    }

    private static byte[] getTag(byte[] key, byte[] cipherText, byte[] iv,
                                 byte[] authenticatedData) throws GeneralSecurityException {
        byte[] al = new byte[8];
        int value = authenticatedData.length * 8;
        for (int q = 24, i = 4; q >= 0; q -= 8, i++) {
            al[i] = (byte) (value >>> q);
        }
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, 0, 16, "RAW"));
        mac.update(authenticatedData);
        mac.update(iv);
        mac.update(cipherText);
        mac.update(al);
        byte[] tag = new byte[TAG_SIZE];
        System.arraycopy(mac.doFinal(), 0, tag, 0, tag.length);
        return tag;
    }


    public static byte[] GenerateNonce()
    {
        java.util.Random ra = new java.util.Random();
        //Sodium.randombytes_buf(nonce, nonce.length);
        byte[] nonce = new byte[NONCE_SIZE];
        ra.nextBytes(nonce);
        return nonce;
    }

    private static byte[] Tag()
    {
        /*
        byte[] tag = new byte[TAG_SIZE];
        Sodium.randombytes_buf(tag, tag.length);
        */
        java.util.Random ra = new java.util.Random();
        byte[] tag = new byte[TAG_SIZE];
        ra.nextBytes(tag);
        return tag;
    }

}
