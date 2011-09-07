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
public class CheckPayment implements Payment {
    public CheckPayment() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String name;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String routing;
    private String account;
    private String number;

    public CheckPayment(int payId) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from payments_check where payments_check_id = " +
                    payId);
            if (rs.next()) {
                name = rs.getString("payments_check_name");
                street1 = rs.getString("payments_check_street1");
                street2 = rs.getString("payments_check_street2");
                city = rs.getString("payments_check_city");
                state = rs.getString("payments_check_state");
                zip = rs.getString("payments_check_zip");
                country = rs.getString("payments_check_country");
                phone = rs.getString("payments_check_phone");
                routing = rs.getString("payments_check_routing");
                account = rs.getString("payments_check_account");
                number = rs.getString("payments_check_number");
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CheckPayment(String name, String street1, String street2,
                        String city,
                        String state, String zip, String country, String phone,
                        String routing, String account, String number) {
        this.name = name;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
        this.routing = routing;
        this.account = account;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getRouting() {
        return routing;
    }

    public String getAccount() {
        return account;
    }

    public String getNumber() {
        return number;
    }

    public String getPaymentType() {
        return "Check";
    }

    public int insertDB(int orderId) {
        int payId = -1;

        try {
            StoreDB db = new StoreDB();
            db.update("insert into payments_check values(NULL, " + orderId +
                      ", '" +
                      name + "', '" + street1 + "', '" + street2 + "', '" +
                      city +
                      "', '" + zip + "', '" + state + "', '" + country + "', '" +
                      phone + "', '" + routing + "', '" + account + "', '" +
                      number +
                      "', true)");
            payId = db.insertID();
            //db.shutdown();
        } catch (Exception e) {
        }

        return payId;
    }

    private void jbInit() throws Exception {
    }
}
