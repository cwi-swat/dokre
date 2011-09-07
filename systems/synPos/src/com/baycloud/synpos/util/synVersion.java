package com.baycloud.synpos.util;

/**
 * <p>Title: synPOS</p>
 *
 * <p>Description: synPOS is a desktop POS (Point Of Sale) client for online
 * ERP/eCommerce systems. Released under the GNU General Public License.
 * Absolutely no warranty. Use at your own risk.</p>
 *
 * <p>Copyright: Copyright (c) 2006 synPOS.com</p>
 *
 * <p>Website: www.synpos.com</p>
 *
 * @author H.Q.
 * @version 0.9.4
 */
public class synVersion {
    public static int compare(String ver1, String ver2) throws Exception {
        if (ver1 != null && ver2 != null) {
            String[] digits1 = ver1.split("\\.");
            String[] digits2 = ver2.split("\\.");

            if (digits1 != null && digits1.length == 3 && digits2 != null &&
                digits2.length == 3) {
                try {
                    int mainVer1 = Integer.parseInt(digits1[0]);
                    int minorVer1 = Integer.parseInt(digits1[1]);
                    int maintainVer1 = Integer.parseInt(digits1[2]);
                    int mainVer2 = Integer.parseInt(digits2[0]);
                    int minorVer2 = Integer.parseInt(digits2[1]);
                    int maintainVer2 = Integer.parseInt(digits2[2]);
                    if (mainVer1 > mainVer2 ||
                        (mainVer1 == mainVer2 &&
                         minorVer1 > minorVer2) ||
                        (mainVer1 == mainVer2 &&
                         minorVer1 == minorVer2 &&
                         maintainVer1 >
                         maintainVer2)) {
                        return 1;
                    } else if (mainVer1 ==
                               mainVer2 &&
                               minorVer1 ==
                               minorVer2 &&
                               maintainVer1 ==
                               maintainVer2) {
                        return 0;
                    } else {
                        return -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        throw new Exception("Invalid synPOS version.");
    }
}
