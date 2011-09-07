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
public class OrderProduct {
    private String code;
    private String barcode;
    private String desc;
    private int quantity;
    private double price;
    private double tax;

    public OrderProduct(String code, String barcode, String desc,
                   double price,
                   double tax, int quantity) {
        this.code = code;
        this.barcode = barcode;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
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
