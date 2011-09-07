package com.baycloud.synpos.util;

import javax.crypto.spec.*;
import javax.crypto.*;

/**
 * <p>Title: synPOS</p>
 *
 * <p>Description: synPOS is a desktop POS (Point Of Sale) client for online
 * ERP, eCommerce, and CRM systems. Released under the GNU General Public
 * License. Absolutely no warranty. Use at your own risk.</p>
 *
 * <p>Copyright: Copyright (c) 2006 synPOS.com</p>
 *
 * <p>Website: www.synpos.com</p>
 *
 * @author H.Q.
 * @version 0.9.1
 */
public class Registration {
    public static boolean isValid(String email, String code) {
        try {
            String pass = "stojoman";
            byte[] key = pass.getBytes();
            DESKeySpec spec = new DESKeySpec(key);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            SecretKey secret = factory.generateSecret(spec);
            Cipher inCipher = Cipher.getInstance("DES/ECB/NoPadding");
            inCipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] tempIn = inCipher.doFinal(code.getBytes());
            String decrypted = new String(tempIn, "UTF-8").trim();
            if (decrypted.endsWith(email)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
