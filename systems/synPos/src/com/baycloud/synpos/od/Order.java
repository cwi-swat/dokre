package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;

import java.sql.*;
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
public class Order {
    private int orderId;
    private OrderProduct[] products;
    private double total;
    private double tax;
    private Timestamp time;
    private Payment payment;
    private User user;
    private Station station;
    private boolean isCancelled;
    private boolean isSynchronized;
    private Customer customer;
    private String orderCode;

    public Order(int orderId) {
        this.orderId = orderId;

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from orders where orders_id = " +
                                    orderId);

            if (rs.next()) {
                total = rs.getDouble("orders_total");
                tax = rs.getDouble("orders_tax");
                time = rs.getTimestamp("date_purchased");
                int userId = rs.getInt("users_id");
                user = new User(userId);
                int stationId = rs.getInt("stations_id");
                station = new Station(stationId);
                isCancelled = rs.getBoolean("orders_status");
                isSynchronized = rs.getBoolean("synchronized");
                orderCode = rs.getString("orders_code");
                String payType = rs.getString("payment_method");
                int payId = rs.getInt("payment_id");

                if (payType.equals("Cash")) {
                    payment = new CashPayment(payId);
                } else if (payType.equals("Check")) {
                    payment = new CheckPayment(payId);
                } else if (payType.equals("Credit/Debit")) {
                    payment = new CreditPayment(payId);
                }

                int custId = rs.getInt("customers_id");

                if (custId < 0) {
                    customer = null;
                } else {
                    customer = new Customer(custId);
                }

                Vector temp = new Vector();
                rs = db.query(
                        "select * from orders_products where orders_id = " +
                        orderId);
                while (rs.next()) {
                    OrderProduct product = new OrderProduct(rs.getString(
                            "products_code"), rs.getString(
                                    "products_barcode"),
                            rs.getString(
                                    "products_description"),
                            rs.getDouble("orders_price"),
                            rs.getDouble("orders_tax"),
                            rs.getInt("orders_quantity"));
                    temp.add(product);
                }

                products = new OrderProduct[temp.size()];

                for (int i = 0; i < products.length; i++) {
                    products[i] = (OrderProduct) temp.elementAt(i);
                }
            }

            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Order[] getSyncOrders() {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from orders where synchronized = false");
            Vector temp = new Vector();

            while (rs.next()) {
                int orderId = rs.getInt("orders_id");
                temp.add(new Order(orderId));
            }

            Order[] orders = new Order[temp.size()];

            for (int i = 0; i < orders.length; i++) {
                orders[i] = (Order) temp.elementAt(i);
            }

            return orders;
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getPaymentTable(String payType) {
        for (int i = 0; i < Payment.PAYMENT_METHODS.length; i++) {
            if (Payment.PAYMENT_METHODS[i].equals(payType)) {
                return Payment.PAYMENT_TABLES[i];
            }
        }

        return null;
    }

    public void cancel() {
        try {
            StoreDB db = new StoreDB();
            isCancelled = true;
            isSynchronized = (Synchronizer.getMode() ==
                              Synchronizer.NEVER_SYNC ?
                              true : !isSynchronized);

            db.update("update orders set orders_status = true, synchronized = " +
                      (isSynchronized ? "true" : "false") +
                      " where orders_id = " +
                      orderId);

            if (Synchronizer.getMode() != Synchronizer.REAL_SYNC) {
                for (int i = 0; i < products.length; i++) {
                    if (products[i].getQuantity() != 0) {
                        //if(products[i].isLocal()) {
                        db.update(
                                "update products set products_quantity = (products_quantity + " +
                                products[i].getQuantity() +
                                ") where products_barcode = '" +
                                products[i].getBarcode() + "'");
                        //}
                    }
                }
            }

            //update payment??
            //db.shutdown();

            if (Synchronizer.getMode() != Synchronizer.NEVER_SYNC &&
                !isSynchronized) {
                sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sync() {
        try {
            if (!isCancelled) {
                String orderCode = ERPClient.addOrder(this);
                if (orderCode != null) {
                    StoreDB db = new StoreDB();
                    db.update(
                            "update orders set synchronized = true, orders_code ='" +
                            orderCode +
                            "' where orders_id = " +
                            orderId);
                    isSynchronized = true;
                }
            } else {
                if (ERPClient.cancelOrder(this)) {
                    StoreDB db = new StoreDB();
                    db.update(
                            "update orders set synchronized = true where orders_id = " +
                            orderId);
                    isSynchronized = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Order createOrder(OrderProduct[] products, double total,
                                    double tax,
                                    Payment payment, int userId,
                                    Customer customer) {
        try {
            String stationId = Configuration.get("station.id");
            String synced = (Synchronizer.getMode() == Synchronizer.NEVER_SYNC ?
                             "true" : "false");
            StoreDB db = new StoreDB();

            int custId = -1;

            if (customer != null) {
                custId = customer.getId();
            }
            String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
            db.update("insert into orders values(NULL, '', '" +
                      StoreDB.escape(payment.getPaymentType()) + "', 0, " +
                      userId +
                      "," + stationId + ", '"+NOW+"', " + total + "," + tax +
                      ", false, " + synced + ", " +
                      custId + ")");
            int orderId = db.insertID();
            int payId = payment.insertDB(orderId);
            db.update("update orders set payment_id = " + payId +
                      " where orders_id = " + orderId);

            for (int i = 0; i < products.length; i++) {
                if (products[i].getQuantity() != 0) {
                    db.update("insert into orders_products values(NULL, " +
                              orderId + ", '" +
                              StoreDB.escape(products[i].getCode()) +
                              "', '" + StoreDB.escape(products[i].getBarcode()) +
                              "','" +
                              StoreDB.escape(products[i].getDescription()) +
                              "'," + products[i].getQuantity() + "," +
                              products[i].getPrice() + "," + products[i].getTax() +
                              ")");
                    if (Synchronizer.getMode() != Synchronizer.REAL_SYNC) {
                        db.update(
                                "update products set products_quantity = (products_quantity - " +
                                products[i].getQuantity() +
                                ") where products_barcode = '" +
                                products[i].getBarcode() + "'");
                    }
                }
            }

            //db.shutdown();
            return new Order(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getNextId() {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select max(orders_id) from orders");

            if (rs.next()) {
                return (rs.getInt(1) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Timestamp getTime() {
        return time;
    }

    public Payment getPayment() {
        return payment;
    }

    public OrderProduct[] getProducts() {
        return products;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public double getTotal() {
        return total;
    }

    public double getTax() {
        return tax;
    }

    public User getUser() {
        return user;
    }

    public Station getStation() {
        return station;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCode() {
        return orderCode;
    }

    public int getId() {
        return orderId;
    }
}
