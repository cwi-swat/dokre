package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import java.sql.ResultSet;

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
public class CashPayment implements Payment {
    private double paid;
    private double change;

    public CashPayment(double paid, double change) {
        this.paid = paid;
        this.change = change;
    }

    public CashPayment(int payId) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from payments_cash where payments_cash_id = " +
                    payId);
            if (rs.next()) {
                paid = rs.getDouble("payments_cash_paid");
                change = rs.getDouble("payments_cash_change");
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getPaid() {
        return paid;
    }

    public double getChange() {
        return change;
    }

    public String getPaymentType() {
        return "Cash";
    }

    public int insertDB(int orderId) {
        try {
            StoreDB db = new StoreDB();
            db.update("insert into payments_cash values(NULL, " + orderId +
                      ", " +
                      paid + ", " + change + ", true)");
            int payId = db.insertID();
            //db.shutdown();
            return payId;
        } catch (Exception e) {
        }

        return -1;
    }
}
