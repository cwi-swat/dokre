package com.baycloud.synpos.od;

import java.util.*;
import com.baycloud.synpos.xt.Synchronizer;
import java.sql.ResultSet;
import com.baycloud.synpos.xt.*;

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
 * @version 0.9.2
 */
public class Customer {
    int id;
    String code;
    String firstName;
    String lastName;
    String title;
    String birthDate;
    String address1;
    String address2;
    String city;
    String state;
    String zip;
    String phone;
    String fax;
    String email;
    Date dateCreated;
    Date lastModified;
    boolean isSynchronized;

    public Customer(String code, String firstName, String lastName,
                    String title,
                    String birthDate, String address1,
                    String address2, String city, String state, String zip,
                    String phone, String fax, String email, Date dateCreated,
                    Date lastModified) {
        this.id = -1;
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.birthDate = birthDate;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
        this.isSynchronized = true; //((Synchronizer.getMode() == Synchronizer.NEVER_SYNC || code == null || code.equals("")) ? true : false);
    }

    public Customer(int id) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from customers where customers_id = " +
                    id);
            if (rs.next()) {
                this.id = id;
                this.code = rs.getString("customers_code");
                this.firstName = rs.getString("firstname");
                this.lastName = rs.getString("lastname");
                this.title = rs.getString("title");
                this.birthDate = rs.getString("birthdate");
                this.address1 = rs.getString("address1");
                this.address2 = rs.getString("address2");
                this.city = rs.getString("city");
                this.state = rs.getString("state");
                this.zip = rs.getString("zip");
                this.phone = rs.getString("phone");
                this.fax = rs.getString("fax");
                this.email = rs.getString("email");
                this.dateCreated = rs.getDate("date_created");
                this.lastModified = rs.getDate("last_modified");
                this.isSynchronized = rs.getBoolean("synchronized");
            }

            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String firstName, String lastName,
                       String title,
                       String birthDate, String address1,
                       String address2, String city, String state, String zip,
                       String phone, String fax, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.birthDate = birthDate;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.lastModified = new Date();
        this.isSynchronized = true; //((Synchronizer.getMode() == Synchronizer.NEVER_SYNC || code == null || code.equals("")) ? true : false);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Customer[] getSyncCustomers() {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from customers where synchronized = false");
            Vector temp = new Vector();

            while (rs.next()) {
                int custId = rs.getInt("customers_id");
                temp.add(new Customer(custId));
            }

            Customer[] customers = new Customer[temp.size()];

            for (int i = 0; i < customers.length; i++) {
                customers[i] = (Customer) temp.elementAt(i);
            }

            return customers;
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Customer[] getCustomer(String str, String by) {
        return getLocalCustomer(str, by);
        /*
                int mode = Synchronizer.getMode();

                switch (mode) {
                case Synchronizer.NEVER_SYNC:
                case Synchronizer.MANUAL_SYNC:
                    return getLocalCustomer(str, by);
                case Synchronizer.REAL_SYNC:
                    if (by.equals("email")) {
                        return ERPClient.searchCustomer(null, null, null, null, null, null, null, null, null, null, null, null,
                                                        str);
                    } else if (by.equals("phone")) {
                        return ERPClient.searchCustomer(null, null, null, null, null, null, null, null, null, null,
                                                        str, null, null);
                    }
                default:
                }
                return null;
         */
    }

    public static Customer[] getLocalCustomer(String str, String by) {
        Vector temp = new Vector();
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from customers where " + by +
                                    " = '" +
                                    str + "'");
            while (rs.next()) {
                temp.add(new Customer(rs.getInt("customers_id")
                         ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!temp.isEmpty()) {
            Customer[] cust = new Customer[temp.size()];

            for (int i = 0; i < temp.size(); i++) {
                cust[i] = (Customer) temp.elementAt(i);
            }

            return cust;

        } else {
            return null;
        }
    }

    public void save() {
        try {
            StoreDB db = new StoreDB();
            String synced = "true"; //(isSynchronized ? "true" : "false");
            //ResultSet rs = db.query("select * from customers where email ='" + email + "'");

            if (this.id < 0) {
                String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
                db.update("insert into customers values (NULL, '" + code +
                          "','" + firstName +
                          "', '" + lastName + "', '" + title + "', '" +
                          birthDate + "', '" + address1 + "', '" + address2 +
                          "', '" + city + "', '" + state + "', '" + zip +
                          "', '" + phone + "', '" + fax + "', '" + email +
                          "', '"+NOW+"', '"+NOW+"', " + synced + ")");

                this.id = db.insertID();
            } else {
                String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();

                db.update("update customers set customers_code = '" + code +
                          "', firstname = '" + firstName +
                          "', lastname = '" + lastName + "', title = '" + title +
                          "', birthdate = '" + birthDate + "', address1 = '" +
                          address1 + "', address2 = '" + address2 +
                          "', city = '" + city + "', state ='" + state +
                          "', zip ='" + zip + "', phone = '" + phone +
                          "', fax = '" + fax +  "', email = '" + email +
                          "', last_modified = '"+NOW+"', synchronized = " + synced +
                          " where customers_id = '" +
                          this.id +
                          "'"); ;
            }

            //sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }


    public void sync() {
        try {
            if (code != null && !code.equals("")) {
                String custCode = ERPClient.addCustomer(this);

                if (custCode != null) {
                    this.code = custCode;

                    StoreDB db = new StoreDB();
                    db.update(
                            "update customers set customers_code = '" +
                            custCode +
                            "', synchronized = true where customers_id = " +
                            id);
                    this.isSynchronized = true;
                    //db.shutdown();
                }
            } else {
                String custCode = ERPClient.updateCustomer(this);

                if (custCode != null) {
                    this.code = custCode;

                    StoreDB db = new StoreDB();
                    db.update(
                            "update customers set customers_code = '" +
                            custCode +
                            "', synchronized = true where customers_id = " +
                            id);
                    this.isSynchronized = true;
                    //db.shutdown();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public String getCode() {
        return code;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getTitle() {
        return this.title;

    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public String getAddress1() {
        return this.address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public String getCity() {
        return this.city;

    }

    public String getState() {
        return this.state;
    }

    public String getZip() {
        return this.zip;
    }

    public String getPhone() {
        return this.phone;

    }

    public String getFax() {
        return this.fax;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public Date getLastModified() {
        return this.lastModified;
    }
}
