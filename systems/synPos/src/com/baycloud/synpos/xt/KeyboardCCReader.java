package com.baycloud.synpos.xt;

import com.baycloud.synpos.util.*;
import jpay.*;

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
public class KeyboardCCReader {
    public static CreditCard parse(String rawStr) {
        // Mastercard
        // %B1111222233334444^PUBLIC/JOHN?;1111222233334444=99121010000000000000?
        //  *---------------- -----------                   ----***
        // Visa
        // %B1111222233334444^PUBLIC/JOHN^9912101xxxxxxxxxxxxx?;1111222233334444=9912101xxxxxxxxxxxxx?
        //  *---------------- ----------- ----                                       ***
        String holderName = null;
        String cardNumber1 = null;
        String cardNumber2 = null;
        String expDate1 = null;
        String expDate2 = null;
        String track1 = null;
        String track2 = null;

        int pos = rawStr.indexOf("?");
        if (pos < 0) {
            return null;
        }

        String track = rawStr.substring(0, pos);
        if (track.startsWith("%")) { //track1
            track1 = track.substring(1);
            track = rawStr.substring(pos + 1);
            if (!track.startsWith(";")) {
                return null;
            }
            pos = track.indexOf("?");
            if (pos < 0) {
                return null;
            }
            track2 = track.substring(1, pos);

        } else if (track.startsWith(";")) {
            track2 = track.substring(1, pos);
        } else {
            return null;
        }

        if (track1 != null && track1.length() > 0) {
            char cardType = track1.charAt(0);
            if (cardType != 'B' && cardType != 'b') {
                return null;
            }
            pos = track1.indexOf("^");

            if (pos < 0) {
                return null;
            }

            cardNumber1 = track1.substring(1, pos);
            if (!checkCardNumber(cardNumber1)) {
                return null;
            }

            int pos2 = track1.indexOf("^", pos + 1);

            if (pos2 < 0) {
                holderName = track1.substring(pos + 1);
            } else {
                holderName = track1.substring(pos + 1, pos2);
                expDate1 = track1.substring(pos2 + 1, pos2 + 5);
                if (!checkExpDate(expDate1)) {
                    return null;
                }

            }
            holderName = formatHolderName(holderName);
        }

        if (track2 != null && track2.length() > 0) {
            pos = track2.indexOf("=");

            if (pos < 0) {
                return null;
            }

            cardNumber2 = track2.substring(0, pos);
            if (!checkCardNumber(cardNumber2)) {
                return null;
            }

            expDate2 = track2.substring(pos + 1, pos + 5);
            if (!checkExpDate(expDate2)) {
                return null;
            }
        }

        if (cardNumber1 != null && cardNumber2 != null &&
            !cardNumber1.equals(cardNumber2)) {
            return null;
        }

        if (expDate1 != null && expDate2 != null && !expDate1.equals(expDate2)) {
            return null;
        }
        String cardNumber = (cardNumber1 != null ? cardNumber1 : cardNumber2);
        String expDate = (expDate1 != null ? expDate1 : expDate2);

        return new CreditCard(track1, track2, holderName,
                              cardNumber, expDate.substring(2),
                              "20" + expDate.substring(0, 2));
    }

    private static boolean checkCardNumber(String num) {
        try {
            return CCValidator.validCC(num);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

        /*
             if (num == null || num.length() != 16) {
          return false;
             }

             for (int i = 0; i < 16; i++) {
          char c = num.charAt(i);
          if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' &&
              c != '5' &&
              c != '6' && c != '7' && c != '8' && c != '9') {
            return false;
          }
             }

             return true;*/
    }

    private static boolean checkExpDate(String date) {

        if (date == null || date.length() != 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            char c = date.charAt(i);
            if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' &&
                c != '5' &&
                c != '6' && c != '7' && c != '8' && c != '9') {
                return false;
            }
        }

        return true;
    }

    private static String formatHolderName(String name) {
        int iPos = name.indexOf('/');
        if (iPos >= 0) {
            return name.substring(iPos + 1).trim() + ' ' +
                    name.substring(0, iPos);
        } else {
            return name.trim();
        }
    }
}
