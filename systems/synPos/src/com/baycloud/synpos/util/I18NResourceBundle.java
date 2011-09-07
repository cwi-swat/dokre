package com.baycloud.synpos.util;

import java.util.*;
import java.io.*;

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
public class I18NResourceBundle {
    public static final String I18N_ENCODING = "UTF-8";

    /**
     * Maximum length of one branch of the resource search path tree.
     * Used in getBundle.
     */
    private static final int MAX_BUNDLES_SEARCHED = 3;

    /**
     * Collection of resource bundles
     */
    private static Hashtable bundles = new Hashtable();

    /**
     * Collection of resource strings
     */
    private Properties properties = new Properties();

    /**
     *  @return a resource bundle
     */
    public static I18NResourceBundle getBundle(String baseName, Locale locale) {
        Vector names = calculateBundleNames(baseName, locale);

        for (int n = names.size(), i = n; i > 0; i--) {
            String bundleName = (String) names.elementAt(i - 1);
            Object bundle = bundles.get(bundleName);

            if (bundle != null) {
                return (I18NResourceBundle) bundle;
            }
        }
        // load
        for (int n = names.size(), i = n; i > 0; i--) {
            String bundleName = (String) names.elementAt(i - 1);
            String fileName = "i18n/" + bundleName + ".txt";
            try {
                I18NResourceBundle bundle = new I18NResourceBundle(fileName);
                bundles.put(bundleName, bundle);
                return bundle;
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        return null;
    }

    private static Vector calculateBundleNames(String baseName, Locale locale) {
        final Vector result = new Vector(MAX_BUNDLES_SEARCHED);
        final String language = locale.getLanguage();
        final int languageLength = language.length();
        final String country = locale.getCountry();
        final int countryLength = country.length();
        final String variant = locale.getVariant();
        final int variantLength = variant.length();

        result.addElement(baseName);

        if (languageLength + countryLength + variantLength == 0) {
            //The locale is "", "", "".
            return result;
        }
        final StringBuffer temp = new StringBuffer(baseName);
        temp.append('_');
        temp.append(language);
        if (languageLength > 0) {
            result.addElement(temp.toString());
        }

        if (countryLength + variantLength == 0) {
            return result;
        }
        temp.append('_');
        temp.append(country);
        if (countryLength > 0) {
            result.addElement(temp.toString());
        }

        if (variantLength == 0) {
            return result;
        }
        temp.append('_');
        temp.append(variant);
        result.addElement(temp.toString());

        return result;
    }

    public I18NResourceBundle(String bundleName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(bundleName), I18N_ENCODING));

        String entry;

        while ((entry = reader.readLine()) != null) {
            String[] tokens = entry.split("=");
            if (tokens != null && tokens.length == 2) {
                properties.setProperty(tokens[0], tokens[1]);
            }
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }
}
