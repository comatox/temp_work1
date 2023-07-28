package com.skshieldus.esecurity.common.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

    public String encrypt(String text) {
        Cipher cipher;
        String st = null;

        try {
            cipher = getCipher("u(#3", "x4*R", "^f2s", "v5o#", true);
            st = toHexString(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return st;
    }

    public String decrypt(String text) {
        Cipher cipher;
        String st = null;

        try {
            cipher = getCipher("u(#3", "x4*R", "^f2s", "v5o#", false);
            st = new String(cipher.doFinal(hexToByte(text)), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return st;
    }

    private Cipher getCipher(
        String synchro1, String synchro2,
        String synchro3, String synchro4, boolean isEncryptMode
    ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        byte raw[] = (synchro1 + synchro2 + synchro3 + synchro4).getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        if (isEncryptMode) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
        else {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
        return cipher;
    }

    private byte[] hexToByte(String hex) {
        byte bts[] = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }

    private String toHexString(byte bytes[]) {
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
            retString.append(Integer.toHexString(256 + (bytes[i] & 0xff))
                .substring(1));
        return retString.toString();
    }

}
