package com.baycloud.synpos.util;

import java.net.URL;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.io.BufferedReader;

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
public class HTTP {
    public static String post(String urlStr, String content, String type,
                              String username, String password) throws
            Exception {
        URL url = new URL(urlStr);
        URLConnection urlConn = url.openConnection();
        urlConn.setDoInput(true);
        //  we want to do output.
        urlConn.setDoOutput(true);
        // No caching, we want the real thing.
        urlConn.setUseCaches(false);
        // Specify the content type.
        if (type.equals("xml")) {
            urlConn.setRequestProperty("Content-Type",
                                       "text/xml; charset=utf-8");
        } else if (type.equals("form")) {
            urlConn.setRequestProperty("Content-Type",
                                       "application/x-www-form-urlencoded");
        }

        if (username != null && password != null) {
            String auth = "Auth=" + username + ":" + password;
            urlConn.setRequestProperty("Cookie", auth);
        }

        // Send POST output.
        DataOutputStream printout = new DataOutputStream(urlConn.
                getOutputStream());
        printout.writeBytes(content);
        printout.flush();
        printout.close();
        // Get response data.

        BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.
                getInputStream()));
        String str;
        String response = "";

        while (null != (str = input.readLine())) {
            response += str;
        }

        input.close();

        return response;
    }
}
