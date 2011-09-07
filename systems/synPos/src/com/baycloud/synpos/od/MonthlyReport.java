package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.I18N;

import java.util.*;
import java.sql.Timestamp;
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
public class MonthlyReport {
    private int month;
    private int year;

    public MonthlyReport(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public Hashtable getTotals() {
        Hashtable totals = new Hashtable();

        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month + 1, 1, 0, 0, 0);

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select sum(orders_total) as sales_total, sum(orders_tax) as tax_total from orders where orders.orders_status = false and orders.date_purchased > '" +
                                    (new Timestamp(start.getTimeInMillis())) +
                                    "' and orders.date_purchased < '" +
                                    (new Timestamp(end.getTimeInMillis())) +
                                    "'");

            while (rs.next()) {
                totals.put(I18N.getLabelString("Sales Total"), new Double(rs.getDouble("sales_total")));
                totals.put(I18N.getLabelString("Tax Total"), new Double(rs.getDouble("tax_total")));
            }
            rs = db.query("select sum(orders_products.orders_quantity) as orders_quantity from orders_products, orders where orders.orders_status = false and orders.orders_id = orders_products.orders_id and orders_products.orders_price > 0 and orders.date_purchased > '" +
                          (new Timestamp(start.getTimeInMillis())) +
                          "' and orders.date_purchased < '" +
                          (new Timestamp(end.getTimeInMillis())) + "'");

            while (rs.next()) {
                totals.put(I18N.getLabelString("Number of Items"),
                           new Integer(rs.getInt("orders_quantity")));
            }

        } catch (Exception e) {

        }

        return totals;
    }

    public Hashtable getPaymentTotals() {
        Hashtable totals = new Hashtable();

        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month + 1, 1, 0, 0, 0);

        try {
            StoreDB db = new StoreDB();

            ResultSet rs = db.query("select sum(orders_total) as sales_total,  payment_method from orders where orders.orders_status = false and orders.date_purchased > '" +
                                    (new Timestamp(start.getTimeInMillis())) +
                                    "' and orders.date_purchased < '" +
                                    (new Timestamp(end.getTimeInMillis())) +
                                    "' group by payment_method");

            while (rs.next()) {
                totals.put(rs.getString("payment_method"),
                           new Double(rs.getDouble("sales_total")));
            }
        } catch (Exception e) {

        }

        return totals;
    }

    public Hashtable getUserTotals() {
        Hashtable totals = new Hashtable();

        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month + 1, 1, 0, 0, 0);

        try {
            StoreDB db = new StoreDB();

            ResultSet rs = db.query("select sum(orders_total) as sales_total,  users_username from orders, users where orders.orders_status = false and orders.users_id = users.users_id and orders.date_purchased > '" +
                                    (new Timestamp(start.getTimeInMillis())) +
                                    "' and orders.date_purchased < '" +
                                    (new Timestamp(end.getTimeInMillis())) +
                                    "' group by users_username");

            while (rs.next()) {
                totals.put(rs.getString("users_username"),
                           new Double(rs.getDouble("sales_total")));
            }
        } catch (Exception e) {

        }

        return totals;
    }

    public Hashtable getStationTotals() {
        Hashtable totals = new Hashtable();

        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month + 1, 1, 0, 0, 0);

        try {
            StoreDB db = new StoreDB();

            ResultSet rs = db.query("select sum(orders_total) as sales_total,  stations_name from orders, stations where orders.orders_status = false and orders.stations_id = stations.stations_id and orders.date_purchased > '" +
                                    (new Timestamp(start.getTimeInMillis())) +
                                    "' and orders.date_purchased < '" +
                                    (new Timestamp(end.getTimeInMillis())) +
                                    "' group by stations_name");

            while (rs.next()) {
                totals.put(rs.getString("stations_name"),
                           new Double(rs.getDouble("sales_total")));
            }
        } catch (Exception e) {

        }

        return totals;
    }

    public Hashtable getBestSellersByDollar() {
        Hashtable totals = new Hashtable();

        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month + 1, 1, 0, 0, 0);

        try {
            StoreDB db = new StoreDB();

            ResultSet rs = db.query("select sum(orders_products.orders_price * (1 + orders_products.orders_tax / 100.0) * orders_products.orders_quantity) as products_total,  orders_products.products_description,  orders_products.products_barcode from orders_products, orders where orders.orders_status = false and orders.orders_id = orders_products.orders_id and orders_products.orders_price > 0 and orders.date_purchased > '" +
                                    (new Timestamp(start.getTimeInMillis())) +
                                    "' and orders.date_purchased < '" +
                                    (new Timestamp(end.getTimeInMillis())) +
                                    "' group by orders_products.products_barcode, orders_products.products_description order by products_total desc limit 5");

            while (rs.next()) {
                totals.put(rs.getString("products_barcode") + " " +
                           rs.getString("products_description"),
                           new Double(rs.getDouble("products_total")));
            }
        } catch (Exception e) {

        }

        return totals;
    }

    public Hashtable getBestSellersByQuantity() {
        Hashtable totals = new Hashtable();

        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month + 1, 1, 0, 0, 0);

        try {
            StoreDB db = new StoreDB();

            ResultSet rs = db.query("select sum(orders_products.orders_quantity) as products_total,  orders_products.products_description,  orders_products.products_barcode from orders_products, orders where orders.orders_status = false and orders.orders_id = orders_products.orders_id and orders_products.orders_price > 0 and orders.date_purchased > '" +
                                    (new Timestamp(start.getTimeInMillis())) +
                                    "' and orders.date_purchased < '" +
                                    (new Timestamp(end.getTimeInMillis())) +
                                    "' group by orders_products.products_barcode, orders_products.products_description order by products_total desc limit 5");

            while (rs.next()) {
                totals.put(rs.getString("products_barcode") + " " +
                           rs.getString("products_description"),
                           new Integer(rs.getInt("products_total")));
            }
        } catch (Exception e) {

        }

        return totals;
    }
}
