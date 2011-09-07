package com.baycloud.synpos.util;

import com.baycloud.synpos.od.Configuration;

import java.util.*;

/**
 * <p>Title: synPOS</p>
 *
 * <p>Description: synPOS is a desktop POS (Point Of Sale) client for online
 * ERP/eCommerce systems. Released under the GNU General Public
 * License. Absolutely no warranty. Use at your own risk.</p>
 *
 * <p>Copyright: Copyright (c) 2006 synPOS.com</p>
 *
 * <p>Website: www.synpos.com</p>
 *
 * @author H.Q.
 * @version 0.9.3
 */
public class I18N {
    private static Locale myLocale = null;

    public static Locale getLocale() {
        return myLocale;
    }

    public static String getMessageString(String str) {
        I18NResourceBundle bundle = I18NResourceBundle.getBundle("Messages",
                myLocale);

        if (bundle != null) {
            return bundle.getString(str);
        } else {
            return null;
        }
    }

    public static String getLabelString(String str) {
        I18NResourceBundle bundle = I18NResourceBundle.getBundle("Labels",
                myLocale);

        if (bundle != null) {
            return bundle.getString(str);
        } else {
            return null;
        }
    }

    public static String getButtonString(String str) {
        I18NResourceBundle bundle = I18NResourceBundle.getBundle("Buttons",
                myLocale);

        if (bundle != null) {
            return bundle.getString(str);
        } else {
            return null;
        }
    }

    static {
        String lang = Configuration.get("locale.language");

        if (lang == null) {
            lang = "";
        }

        String country = Configuration.get("locale.country");

        if (country == null) {
            country = "";
        }

        String variant = Configuration.get("locale.variant");

         if (variant == null) {
             variant = "";
        }
        myLocale = new Locale(lang, country, variant);
    }
}
