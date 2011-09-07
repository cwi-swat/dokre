package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.tree.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;

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
public class Product {
    private String code;
    private String barcode;
    private String desc;
    private int quantity;
    private double price;
    private double tax;
    private int id;
    private int catId;

    public Product(int id) {
        this.id = id;

        try {
            StoreDB db = new StoreDB();

            ResultSet rs = db.query(
                    "select * from products where products_id = " + id);
            if (rs.next()) {
                this.code = rs.getString("products_code");
                this.catId = rs.getInt("products_category");
                this.barcode = rs.getString("products_barcode");
                this.desc = rs.getString("products_description");
                this.quantity = rs.getInt("products_quantity");
                this.price = rs.getDouble("products_price");
                this.tax = rs.getDouble("products_tax");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product(String code, String barcode, String desc,
                   double price,
                   double tax, int quantity) {
        this.code = code;
        this.barcode = barcode;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
        this.catId = -1;
        this.id = -1;
    }

    public int save(int catId) {
        try {
            StoreDB db = new StoreDB();

            if (id < 0) {
                String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
                db.update(
                        "insert into products values(NULL, '" +
                        StoreDB.escape(
                                getCode()) +
                        "', '"
                        +
                        catId + "', '" +

                        StoreDB.escape(
                                getBarcode()) +
                        "', '" +
                        StoreDB.escape(getDescription()) +
                        "', " +
                        getQuantity() +
                        ", " +
                        getPrice() +
                        ", " +

                        getTax() +
                        ", '"+NOW+"', '"+NOW+"')");
                this.id = db.insertID();
                this.catId = catId;
            } else {
                String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();

                db.update(
                        "update products set products_code = '" +
                        StoreDB.escape(
                                getCode()) +
                        "', products_category = '"
                        +
                        getCatId() + "', products_barcode = '" +

                        StoreDB.escape(
                                getBarcode()) +
                        "', products_description = '" +
                        StoreDB.escape(getDescription()) +
                        "', products_quantity = " +
                        getQuantity() +
                        ", products_price = " +
                        getPrice() +
                        ", products_tax = " +

                        getTax() +
                        ", last_modified = '"+NOW+"' where products_id = " + id + ")");

            }

            return this.id;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Product getProduct(String barcode) {
        int mode = Synchronizer.getMode();

        switch (mode) {
        case Synchronizer.NEVER_SYNC:
        case Synchronizer.MANUAL_SYNC:
            return getLocalProduct(barcode);
        case Synchronizer.REAL_SYNC:
            Product[] products = ERPClient.searchProduct(barcode, null, null);
            if (products != null) {
                return products[0];
            }
        default:
        }

        return null;
    }

    public static int getProductCategory(String barcode) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select products_category from products where products_barcode = '" +
                    barcode + "'");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Product getLocalProduct(String barcode) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from products where products_barcode = '" +
                    barcode + "'");
            if (rs.next()) {
                /*
                                 boolean local = true;
                 CategoryTreeModel model = new CategoryTreeModel();
                 Category cat = model.getCategory(rs.getInt(6));

                                 if (cat != null && !cat.isLocalCategory()) {
                    local = false;
                                 }*/
                Product prod = new Product(rs.getInt("products_id"));
                return prod;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public int getCatId() {
        return catId;
    }

    public String getCode() {
        return code;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDescription() {
        return desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTax() {
        return tax;
    }
}
