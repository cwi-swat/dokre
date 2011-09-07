package com.baycloud.synpos.util;

import java.util.*;

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
public class Sorter {
    public static Map.Entry[] getSortedHashtableEntries(Hashtable h) {
        return getSortedHashtableEntries(h, true);
    }

    public static Map.Entry[] getSortedHashtableEntries(Hashtable h,
            final boolean asc) {
        Set set = h.entrySet();
        Map.Entry[] entries =
                (Map.Entry[]) set.toArray(
                        new Map.Entry[set.size()]);
        Arrays.sort(entries, new Comparator() {
            public int compare(Object o1, Object o2) {
                Object v1 = ((Map.Entry) o1).getValue();
                Object v2 = ((Map.Entry) o2).getValue();
                return (asc ? ((Comparable) v1).compareTo(v2) :
                        ((Comparable) v2).compareTo(v1));
            }
        });
        return entries;
    }
}
