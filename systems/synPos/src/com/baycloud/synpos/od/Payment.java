package com.baycloud.synpos.od;

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
public interface Payment {
    public static final String[] PAYMENT_METHODS = {"Cash", "Check",
            "Credit/Debit"};
    public static final String[] PAYMENT_TABLES = {"payments_cash",
                                                  "payments_check",
                                                  "payments_credit"};

    public int insertDB(int orderId);

    public String getPaymentType();
}
